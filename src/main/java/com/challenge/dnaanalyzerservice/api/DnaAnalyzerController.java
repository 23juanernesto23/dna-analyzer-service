package com.challenge.dnaanalyzerservice.api;

import com.challenge.dnaanalyzerservice.domain.Dna;
import com.challenge.dnaanalyzerservice.domain.DnaReport;
import com.challenge.dnaanalyzerservice.repository.service.DnaService;
import com.challenge.dnaanalyzerservice.utils.DnaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/")
public class DnaAnalyzerController {

    @Autowired
    DnaService dnaService;

    @Autowired
    DnaManager dnaManager;

    @Value("${dna.mutant.base.length}")
    private int mutantDnaBaseLength;

    @GetMapping("stats")
    public ResponseEntity<DnaReport> stats(){
        return new ResponseEntity<>(dnaService.obtaintStats(), HttpStatus.OK);
    }

    @PostMapping("mutant/")
    public ResponseEntity<String> isMutant(@RequestBody DNARequest dnaRequest) {

        if (!dnaManager.isValidDnaChain(dnaRequest))
            return new ResponseEntity<>("There is a problem with the length of the DNA ...", HttpStatus.BAD_REQUEST);

        Dna dna = Dna.builder()
                .dna(Arrays.asList(dnaRequest.getDna()))
                .mutantDnaBaseLength(mutantDnaBaseLength)
                .build();

        Boolean dnaDb = dnaService.exist(dna);
        if(Objects.nonNull(dnaDb))
            if(dnaDb)
                return new ResponseEntity<>(HttpStatus.OK) ;
            else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        int countMutants = dnaManager.countMutants(dna);

        if (countMutants > 1)
            dna.setMutant(true);
        dnaService.save(dna);
        if (countMutants > 1)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}


