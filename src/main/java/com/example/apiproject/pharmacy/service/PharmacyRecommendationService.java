package com.example.apiproject.pharmacy.service;

import com.example.apiproject.api.dto.DocumentDto;
import com.example.apiproject.api.dto.KakaoApiResponseDto;
import com.example.apiproject.api.service.KakaoAddressSearchService;
import com.example.apiproject.direction.entity.Direction;
import com.example.apiproject.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendPharmacies(String address) {
        KakaoApiResponseDto kakaoApiResponse = kakaoAddressSearchService.requestAddressSearch(address);

        if (kakaoApiResponse == null || CollectionUtils.isEmpty(kakaoApiResponse.getDocuments())) {
            log.error("[recommendPharmacies fail] input address: {}", address);
            return;
        }

        DocumentDto document = kakaoApiResponse.getDocuments().get(0);
//        List<Direction> directions = directionService.buildDirections(document);
        List<Direction> directions = directionService.buildDirectionsByCategoryApi(document);

        directionService.saveAll(directions);
    }
}
