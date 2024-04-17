package com.careercompass.careercompass.controller;

import com.careercompass.careercompass.dto.CityResponseDTO;
import com.careercompass.careercompass.service.CityService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/city")
public class CityController {
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityResponseDTO>> getAll() {
        return new ResponseEntity<>(cityService.getAll(), HttpStatus.OK);
    }
}
