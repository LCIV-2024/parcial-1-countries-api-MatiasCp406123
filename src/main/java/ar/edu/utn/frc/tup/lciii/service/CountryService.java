package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entity.CountyEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {
        @Autowired
        private  CountryRepository countryRepository;
        @Autowired
        private  RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .code(((String)countryData.get("cca3")) )
                        .borders((List<String>) countryData.get("borders"))
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

        public List<CountryDTO>getCountryDtoList(){
                List<CountryDTO>countryDTOList=new ArrayList<>();
                List<Country>countryList=getAllCountries();
                for(Country country:countryList){
                        Country con=new Country();
                        con.setCode(country.getCode());
                        con.setName(country.getName());
                        CountryDTO countryDTO=this.mapToDTO(con);
                        countryDTOList.add(countryDTO);
                }
                return countryDTOList;
        }
        public List<CountryDTO> getByFiltro(String code) {

                List<CountryDTO> countryDTOList = this.getCountryDtoList().stream()
                        .filter(countryDTO -> code.equals(countryDTO.getCode()))
                        .toList();
                return countryDTOList;
        }
        public List<CountryDTO> getByFiltroName(String name) {

                List<CountryDTO> countryDTOList = this.getCountryDtoList().stream()
                        .filter(countryDTO -> name.equals(countryDTO.getName()))
                        .toList();
                return countryDTOList;
        }
        public List<CountryDTO>getByContinent(String continent){
                List<CountryDTO>countryDTOList=new ArrayList<>();
                List<Country>countryList=this.getAllCountries()
                        .stream().filter(country -> continent.equals(country.getRegion()))
                        .toList();
                for(Country country:countryList){
                        Country con=new Country();
                        con.setCode(country.getCode());
                        con.setName(country.getName());
                        CountryDTO countryDTO=this.mapToDTO(con);
                        countryDTOList.add(countryDTO);
                }
                return countryDTOList;
        }
        public List<CountryDTO>getByLanguage(String language){
                List<CountryDTO>countryDTOList=new ArrayList<>();
                List<Country>countryList=this.getAllCountries()
                        .stream().filter(country -> language.equals(country.getLanguages()))
                        .toList();
                for(Country country:countryList){
                        Country con=new Country();
                        con.setCode(country.getCode());
                        con.setName(country.getName());
                        CountryDTO countryDTO=this.mapToDTO(con);
                        countryDTOList.add(countryDTO);
                }
                return countryDTOList;
        }
        public List<CountryDTO>getByBorders(){
                List<Country>countryList=this.getAllCountries();
                Country countryMayorFrontera = null;
                int maxBorders = 0;
                List<CountryDTO>countryDTOList=new ArrayList<>();
                for (Country country : countryList) {
                        if (country.getBorders() != null && country.getBorders().size() > maxBorders) {
                                maxBorders = country.getBorders().size();
                                countryMayorFrontera = country;
                                CountryDTO countryDTO=this.mapToDTO(countryMayorFrontera);
                                countryDTOList.add(countryDTO);
                        }
                }


                return  countryDTOList.stream().findFirst().stream().toList();
        }
        public List<CountryDTO>getByNumber(int number){
                if(number>10){
                        throw new IllegalArgumentException("el numero no debe ser mayo a 10");
                }
                List<Country>countryList=this.getAllCountries();
                List<CountryDTO>countryDTOList=new ArrayList<>();
                int limit = Math.min(number, countryList.size());

                for (int i = 0; i < limit; i++) {
                        Country country = countryList.get(i);
                        CountryDTO countryDTO = this.mapToDTO(country);
                        countryDTOList.add(countryDTO);
                }




                Collections.shuffle(countryList);
                return  countryDTOList;
        }


}