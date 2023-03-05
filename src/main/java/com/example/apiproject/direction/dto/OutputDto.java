package com.example.apiproject.direction.dto;

import com.example.apiproject.direction.entity.Direction;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Builder
public class OutputDto {

    private String pharmacyName;
    private String pharmacyAddress;
    private String directionUrl;
    private String roadViewUrl;
    private String distance;
}
