package com.careercompass.careercompass.service;

import com.careercompass.careercompass.dto.CityResponseDTO;
import com.careercompass.careercompass.mappers.CityMapper;
import com.careercompass.careercompass.model.City;
import com.careercompass.careercompass.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceTest {
    @InjectMocks
    private CityService cityService;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CityMapper cityMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_whenListIsReturned_returnCityDTO () {
        City city = Mockito.mock(City.class);
        CityResponseDTO cityDTO = Mockito.mock(CityResponseDTO.class);
        List<City> cities = Collections.singletonList(city);
        List<CityResponseDTO> citiesDTO = Collections.singletonList(cityDTO);

        Mockito.when(city.getName()).thenReturn("City");
        Mockito.when(cityDTO.getName()).thenReturn("CityDTO");
        Mockito.when(cityRepository.findAll()).thenReturn(cities);
        Mockito.when(cityMapper.mapListToCityResponseDetail(cities)).thenReturn(citiesDTO);

        assertEquals(citiesDTO, cityService.getAll());
        Mockito.verify(cityRepository, Mockito.times(1)).findAll();
        Mockito.verify(cityMapper, Mockito.times(1)).mapListToCityResponseDetail(cities);
    }
}