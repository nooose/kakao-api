package com.example.apiproject.pharmacy.service

import com.example.apiproject.AbstractIntegrationContainerBaseTest
import com.example.apiproject.pharmacy.entity.Pharmacy
import com.example.apiproject.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    PharmacyRepositoryService pharmacyRepositoryService

    @Autowired
    PharmacyRepository pharmacyRepository

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository update - dirty checking 성공"() {
        given:
        String inputAddress = "서울 특별시 송파구 가락동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "누스 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .build()

        when:
        pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddress(pharmacy.getId(), modifiedAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == modifiedAddress
    }
}

