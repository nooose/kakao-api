package com.example.apiproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DocumentDto {

    @JsonProperty("address_name")
    private String addressName;
    @JsonProperty("y")
    private double latitude;

    @JsonProperty("x")
    private double longitude;
}
