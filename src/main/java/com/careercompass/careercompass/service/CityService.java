package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.CityResponseDTO;
import com.careercompass.careercompass.mappers.CityMapper;
import com.careercompass.careercompass.model.City;
import com.careercompass.careercompass.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CityService {
    private CityRepository cityRepository;
    private CityMapper cityMapper;

    public List<CityResponseDTO> getAll() {
        List<City> cities = cityRepository.findAll();

        return cityMapper.mapListToCityResponseDetail(cities);
    }
}
