package com.example.apiproject.pharmacy.service;

import com.example.apiproject.pharmacy.cache.PharmacyRedisTemplateService;
import com.example.apiproject.pharmacy.dto.PharmacyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacySearchService {
    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    public List<PharmacyDto> searchPharmacies() {
        List<PharmacyDto> pharmacies = pharmacyRedisTemplateService.findAll();
        if (!pharmacies.isEmpty()) {
            return pharmacies;
        }

        return pharmacyRepositoryService.findAll()
                .stream()
                .map(PharmacyDto::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
