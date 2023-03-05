package com.example.apiproject.pharmacy.service;

import com.example.apiproject.api.dto.DocumentDto;
import com.example.apiproject.api.dto.KakaoApiResponseDto;
import com.example.apiproject.api.service.KakaoAddressSearchService;
import com.example.apiproject.direction.dto.OutputDto;
import com.example.apiproject.direction.entity.Direction;
import com.example.apiproject.direction.service.Base62Service;
import com.example.apiproject.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacyRecommendationService {
    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    private final Base62Service base62Service;

    @Value("${pharmacy.recommendation.base.url}")
    private String baseUrl;

    public List<OutputDto> recommendPharmacies(String address) {
        KakaoApiResponseDto kakaoApiResponse = kakaoAddressSearchService.requestAddressSearch(address);

        if (kakaoApiResponse == null || CollectionUtils.isEmpty(kakaoApiResponse.getDocuments())) {
            log.error("[recommendPharmacies fail] input address: {}", address);
            return Collections.emptyList();
        }

        DocumentDto document = kakaoApiResponse.getDocuments().get(0);
//        List<Direction> directions = directionService.buildDirections(document);
        List<Direction> directions = directionService.buildDirectionsByCategoryApi(document);

        return directionService.saveAll(directions)
                .stream()
                .map(this::of)
                .collect(Collectors.toList());
    }


    public OutputDto of(Direction direction) {
        return OutputDto.builder()
                .pharmacyAddress(direction.getTargetAddress())
                .pharmacyName(direction.getTargetPharmacyName())
                .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
