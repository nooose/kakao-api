package com.example.apiproject.pharmacy.service

import com.example.apiproject.pharmacy.cache.PharmacyRedisTemplateService
import com.example.apiproject.pharmacy.entity.Pharmacy
import spock.lang.Specification

class PharmacySearchServiceTest extends Specification {

    private PharmacySearchService pharmacySearchService

    private PharmacyRepositoryService pharmacyRepositoryService = Mock()
    private PharmacyRedisTemplateService pharmacyRedisTemplateService = Mock()

    private List<Pharmacy> pharmacies


    def setup() {
        pharmacySearchService = new PharmacySearchService(pharmacyRepositoryService, pharmacyRedisTemplateService)

        this.pharmacies = List.of(
                Pharmacy.builder()
                        .id(1L)
                        .pharmacyName("호수온누리약국")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build(),
                Pharmacy.builder()
                        .id(2L)
                        .pharmacyName("돌곶이온누리약국")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build())
    }

    def "Redis 장애 시 DB를 이용하여 약국 데이터 조회"() {
        when:
        pharmacyRedisTemplateService.findAll() >> []
        pharmacyRepositoryService.findAll() >> this.pharmacies

        def result = pharmacySearchService.searchPharmacies()

        then:
        result.size() == 2
    }
}
