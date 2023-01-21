package com.example.apiproject.pharmacy.dto;

import com.example.apiproject.api.dto.DocumentDto;
import com.example.apiproject.direction.entity.Direction;
import com.example.apiproject.pharmacy.entity.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PharmacyDto {

    private Long id;
    private String pharmacyName;
    private String pharmacyAddress;
    private double latitude;
    private double longitude;

    public static PharmacyDto from(Pharmacy pharmacy) {
        return PharmacyDto.builder()
                .id(pharmacy.getId())
                .pharmacyAddress(pharmacy.getPharmacyAddress())
                .pharmacyName(pharmacy.getPharmacyName())
                .latitude(pharmacy.getLatitude())
                .longitude(pharmacy.getLongitude())
                .build();
    }

    public Direction toDirection(DocumentDto documentDto, double distance) {
        return Direction.builder()
                .inputAddress(documentDto.getAddressName())
                .inputLatitude(documentDto.getLatitude())
                .inputLongitude(documentDto.getLongitude())
                .targetPharmacyName(pharmacyName)
                .targetAddress(pharmacyAddress)
                .targetLatitude(latitude)
                .targetLongitude(longitude)
                .distance(distance)
                .build();
    }
}
