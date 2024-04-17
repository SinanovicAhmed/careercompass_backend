package com.careercompass.careercompass.mappers;

import com.careercompass.careercompass.dto.CityResponseDTO;
import com.careercompass.careercompass.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityMapperTest {
    CityMapper cityMapper;

    @BeforeEach
    void setUp() {
        cityMapper = new CityMapper();
    }

    @Test
    public void testMapListToCityResponseDTO() {
        City city1 = new City(1, "Sarajevo", null);
        City city2 = new City(2, "Zenica", null);

        List<City> cityList = new ArrayList<>();
        cityList.add(city1);
        cityList.add(city2);

        CityResponseDTO cityResponseDTO1 = new CityResponseDTO("Sarajevo");
        CityResponseDTO cityResponseDTO2 = new CityResponseDTO("Zenica");

        List<CityResponseDTO> expectedDTOList = new ArrayList<>();
        expectedDTOList.add(cityResponseDTO1);
        expectedDTOList.add(cityResponseDTO2);

        assertEquals(expectedDTOList, cityMapper.mapListToCityResponseDetail(cityList));
    }

    @Test
    public void testMapListToCityResponseDTO_nullInput() {
        assertNull(cityMapper.mapListToCityResponseDetail(null));
    }

    @Test
    public void testMapToCityResponseDTO() {
        City city = new City(1, "Sarajevo", null);
        CityResponseDTO cityResponseDTO = new CityResponseDTO("Sarajevo");


        assertEquals(cityResponseDTO, cityMapper.mapToCityResponseDetail(city));
    }

    @Test
    public void testMapToCityResponseDTO_nullInput() {
        assertNull(cityMapper.mapToCityResponseDetail(null));
    }

}