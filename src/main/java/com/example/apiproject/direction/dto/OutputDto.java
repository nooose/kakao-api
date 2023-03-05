package com.example.apiproject.direction.dto;

import com.example.apiproject.direction.entity.Direction;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Builder
public class OutputDto {

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/line/map/";

    private String pharmacyName;
    private String pharmacyAddress;
    private String directionUrl;
    private String roadViewUrl;
    private String distance;

    public static OutputDto of(Direction direction) {
        String params = String.join(",", direction.getTargetPharmacyName(),
                String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));

        return OutputDto.builder()
                .pharmacyAddress(direction.getTargetAddress())
                .pharmacyName(direction.getTargetPharmacyName())
                .directionUrl(UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + params).toUriString())
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
