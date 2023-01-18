package com.example.apiproject.api.service

import com.example.apiproject.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "address 파라미터 값이 null 이면, requestAddressSearch 메서드는 null 을 리턴한다."() {
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "주소값이 valid 하다면, requestAddressSearch 메서드는 정상적으로 document 를 반환한다."() {
        given:
        def address = "서울 송파구 동남로 193"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documents.size() > 0
        result.meta.totalCount > 0
        result.documents.get(0).addressName != null
    }
}