package com.example.apiproject.direction.dto;

import com.example.apiproject.direction.entity.Direction;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutputDto {
    private String pharmacyName;
    private String pharmacyAddress;
    private String directionUrl;
    private String roadViewUrl;
    private String distance;

    public static OutputDto of(Direction direction) {
        return OutputDto.builder()
                .pharmacyAddress(direction.getTargetAddress())
                .pharmacyName(direction.getTargetPharmacyName())
                .directionUrl("todo")
                .roadViewUrl("todo")
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
