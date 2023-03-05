package com.example.apiproject.direction.service;

import com.example.apiproject.api.dto.DocumentDto;
import com.example.apiproject.api.service.KakaoCategorySearchService;
import com.example.apiproject.direction.entity.Direction;
import com.example.apiproject.direction.repository.DirectionRepository;
import com.example.apiproject.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIUS_KM = 10.0;
    private static final double EARTH_RADIUS = 6371;

    private final PharmacySearchService pharmacySearchService;
    private final DirectionRepository directionRepository;
    private final KakaoCategorySearchService kakaoCategorySearchService;
    private final Base62Service base62Service;

    @Transactional
    public List<Direction> saveAll(List<Direction> directions) {
        if (CollectionUtils.isEmpty(directions)) {
            return Collections.emptyList();
        }

        return directionRepository.saveAll(directions);
    }

    public String findDirectionUrlById(String encodedId) {
        Long decodedId = base62Service.decodeDirectionId(encodedId);
        Direction direction = directionRepository.findById(decodedId).orElse(null);
        return String.join(",", direction.getTargetPharmacyName(),
                String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));
    }

    public List<Direction> buildDirections(DocumentDto documentDto) {
        if (documentDto == null) {
            return Collections.emptyList();
        }

        return pharmacySearchService.searchPharmacies()
                .stream()
                .map(pharmacy -> Direction.builder()
                        .inputAddress(documentDto.getAddressName())
                        .inputLatitude(documentDto.getLatitude())
                        .inputLongitude(documentDto.getLongitude())
                        .targetPharmacyName(pharmacy.getPharmacyName())
                        .targetAddress(pharmacy.getPharmacyAddress())
                        .targetLatitude(pharmacy.getLatitude())
                        .targetLongitude(pharmacy.getLongitude())
                        .distance(calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                pharmacy.getLatitude(), pharmacy.getLongitude()))
                        .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    private double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        latitude1 = Math.toRadians(latitude1);
        longitude1 = Math.toRadians(longitude1);
        latitude2 = Math.toRadians(latitude2);
        longitude2 = Math.toRadians(longitude2);

        return EARTH_RADIUS * Math.acos(Math.sin(latitude1) * Math.sin(latitude2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.cos(longitude1 - longitude2));
    }

    public List<Direction> buildDirectionsByCategoryApi(DocumentDto inputDocumentDto) {
        if(inputDocumentDto == null) {
            return Collections.emptyList();
        }

        return kakaoCategorySearchService
                .requestPharmacyCategorySearch(inputDocumentDto.getLatitude(), inputDocumentDto.getLongitude(), RADIUS_KM)
                .getDocuments()
                .stream()
                .map(resultDocumentDto ->
                        Direction.builder()
                                .inputAddress(inputDocumentDto.getAddressName())
                                .inputLatitude(inputDocumentDto.getLatitude())
                                .inputLongitude(inputDocumentDto.getLongitude())
                                .targetPharmacyName(resultDocumentDto.getPlaceName())
                                .targetAddress(resultDocumentDto.getAddressName())
                                .targetLatitude(resultDocumentDto.getLatitude())
                                .targetLongitude(resultDocumentDto.getLongitude())
                                .distance(resultDocumentDto.getDistance() * 0.001)
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }
}
