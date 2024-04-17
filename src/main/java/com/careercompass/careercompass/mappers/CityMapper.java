package com.careercompass.careercompass.mappers;

import com.careercompass.careercompass.dto.CityResponseDTO;
import com.careercompass.careercompass.model.City;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityMapper {
    public List<CityResponseDTO> mapListToCityResponseDetail(List<City> cities) {
        if(cities == null) {
            return null;
        }

        return cities.stream()
                .map(this::mapToCityResponseDetail)
                .collect(Collectors.toList());
    }

    public CityResponseDTO mapToCityResponseDetail(City city) {
        if(city == null) {
            return null;
        }

        CityResponseDTO cityResponseDTO = new CityResponseDTO();
        cityResponseDTO.setName(city.getName());
        return cityResponseDTO;
    }
}
