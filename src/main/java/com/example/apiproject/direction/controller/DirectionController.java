package com.example.apiproject.direction.controller;

import com.example.apiproject.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Controller
public class DirectionController {
    private final DirectionService directionService;

    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable String encodedId) {
        String directionUrl = directionService.findDirectionUrlById(encodedId);
        return "redirect:" + UriComponentsBuilder.fromHttpUrl(directionUrl)
                .toUriString();
    }
}
