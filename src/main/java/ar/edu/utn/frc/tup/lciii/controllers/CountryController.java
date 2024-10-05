package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.RequestDto;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryController {
    @Autowired
    private  CountryService countryService;

    @GetMapping("/api/countries")
    public ResponseEntity<List<CountryDTO>> countryDTOList(@RequestParam(required = false) String code,@RequestParam(required = false)String name) {
        if (code != null) {
            return ResponseEntity.ok(countryService.getByFiltro(code));
        }
        else if(name!=null){
            return ResponseEntity.ok(countryService.getByFiltroName(name));
        }
        List<CountryDTO> countryDTOList = countryService.getCountryDtoList();
        return ResponseEntity.ok(countryDTOList);
    }
    @GetMapping("/api/countries/{continent}/continent")
    public ResponseEntity<List<CountryDTO>>getCountryByContinent(@PathVariable(required = true) String continent){
        List<CountryDTO>countryDTOList=countryService.getByContinent(continent);
        return ResponseEntity.ok(countryDTOList);
    }
    @GetMapping("/api/countries/{language}/language")
    public ResponseEntity<List<CountryDTO>>getCountryByLanguage(@PathVariable(required = true)String language){
        List<CountryDTO>countryDTOList=countryService.getByLanguage(language);
        return ResponseEntity.ok(countryDTOList);
    }
    @GetMapping("/api/countries/most-borders")
    public ResponseEntity<List<CountryDTO>>getbBIGFrontier(){
        List<CountryDTO>countryDTOList=countryService.getByBorders();
        return ResponseEntity.ok(countryDTOList);
    }
    @PostMapping("/api/countries")
    public ResponseEntity<List<CountryDTO>>postByNumber(@RequestBody RequestDto requestDto){
        List<CountryDTO>countryDTOList=countryService.getByNumber(requestDto.getAmountOfCountryToSave());
        return ResponseEntity.ok(countryDTOList);
    }

}