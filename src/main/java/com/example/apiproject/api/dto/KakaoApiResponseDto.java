package com.example.apiproject.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KakaoApiResponseDto {

    private MetaDto meta;
    private List<DocumentDto> documents;
}
