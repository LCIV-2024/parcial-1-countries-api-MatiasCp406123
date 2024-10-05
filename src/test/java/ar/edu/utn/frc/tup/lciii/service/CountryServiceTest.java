package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entity.CountyEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CountryServiceTest {
    @SpyBean
    private CountryService countryService;
    @MockBean
    private CountryRepository countryRepository;

    @Test
    void getCountryDtoList() {
        List<Country> countryList=new ArrayList<>();
        Country country=new Country();
        country.setCode("arg");
        country.setName("Argentina");
        Country con1=new Country();
        con1.setName("China");
        con1.setCode("chn");
        countryList.add(country);
        countryList.add(con1);
        Mockito.when(countryService.getAllCountries()).thenReturn(countryList);

        List<CountryDTO>countryDTOList=countryService.getCountryDtoList();
        Assertions.assertNotNull(countryDTOList);
        Assertions.assertEquals(countryList.size(),countryDTOList.size());
        Assertions.assertEquals("arg",countryDTOList.get(0).getCode());
    }

    @Test
    void getByFiltro() {
        List<Country> countryList=new ArrayList<>();
        Country country=new Country();
        country.setCode("arg");
        country.setName("Argentina");
        countryList.add(country);
        Mockito.when(countryService.getAllCountries()).thenReturn(countryList);
        List<CountryDTO>countryDTOList=countryService.getByFiltro("arg");
        Assertions.assertNotNull(countryDTOList);
        Assertions.assertEquals(countryList.size(),countryDTOList.size());
        Assertions.assertEquals("arg",countryDTOList.get(0).getCode());

    }

    @Test
    void getByFiltroName() {
        List<Country> countryList=new ArrayList<>();
        Country country=new Country();
        country.setCode("arg");
        country.setName("Argentina");
        countryList.add(country);
        Mockito.when(countryService.getAllCountries()).thenReturn(countryList);
        List<CountryDTO>countryDTOList=countryService.getByFiltroName("Argentina");
        Assertions.assertNotNull(countryDTOList);
        Assertions.assertEquals(countryList.size(),countryDTOList.size());
        Assertions.assertEquals("Argentina",countryDTOList.get(0).getName());
    }

    @Test
    void getByContinent() {
        List<Country> countryList=new ArrayList<>();
        Country country=new Country();
        country.setCode("arg");
        country.setName("Argentina");
        country.setRegion("America");
        Country con=new Country();
        con.setCode("chi");
        con.setName("chile");
        con.setRegion("America");
        countryList.add(country);
        Mockito.when(countryService.getAllCountries()).thenReturn(countryList);
        List<CountryDTO>countryDTOList=countryService.getByContinent("America");
        Assertions.assertNotNull(countryDTOList);
        Assertions.assertEquals(countryList.size(),countryDTOList.size());
        Assertions.assertEquals("Argentina",countryDTOList.get(0).getName());
    }

    @Test
    void getByLanguage() {
        Map<String,String> language=new HashMap<>();
        List<Country> countryList=new ArrayList<>();
        Country country=new Country();
        country.setCode("arg");
        country.setName("Argentina");
        country.setRegion("America");
        country.setLanguages(language);
        Country con=new Country();
        con.setCode("chi");
        con.setName("chile");
        con.setRegion("America");
        con.setLanguages(language);
        countryList.add(country);
        Mockito.when(countryService.getAllCountries()).thenReturn(countryList);
        List<CountryDTO>countryDTOList=countryService.getByLanguage("Spanish");
        Assertions.assertNotNull(countryDTOList);
        Assertions.assertEquals(2,countryDTOList.size());
        Assertions.assertEquals("Argentina",countryDTOList.get(0).getName());
    }

    @Test
    void getByBorders() {
    }

    @Test
    void getByNumber() {
        List<Country> countryList=new ArrayList<>();
        Country country=new Country();
        country.setCode("arg");
        country.setName("Argentina");
        country.setRegion("America");
        Country con=new Country();
        con.setCode("chi");
        con.setName("chile");
        con.setRegion("America");
        countryList.add(country);
        CountyEntity countyEntity=new CountyEntity();
        countyEntity.setPopulation(100);
        countyEntity.setName(con.getName());
        countyEntity.setCode(con.getCode());
        Mockito.when(countryService.getAllCountries()).thenReturn(countryList);
        Mockito.when(countryRepository.save(countyEntity)).thenReturn(countyEntity);
        List<CountryDTO>countryDTOList=countryService.getByNumber(2);
        Assertions.assertNotNull(countryDTOList);
        Assertions.assertEquals(1,countryDTOList.size());
        Assertions.assertEquals("Argentina",countryDTOList.get(0).getName());

    }
}