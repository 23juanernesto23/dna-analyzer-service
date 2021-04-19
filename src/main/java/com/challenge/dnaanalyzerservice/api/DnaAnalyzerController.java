package com.challenge.dnaanalyzerservice.api;

import com.challenge.dnaanalyzerservice.domain.DnaReport;
import com.challenge.dnaanalyzerservice.domain.Matrix;
import com.challenge.dnaanalyzerservice.domain.Dna;
import com.challenge.dnaanalyzerservice.repository.service.DnaService;
import com.challenge.dnaanalyzerservice.utils.DnaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        Dna dna = Dna.builder().dna(Arrays.asList(dnaRequest.getDna())).mutantDnaBaseLength(mutantDnaBaseLength).build();

        Boolean dnaDb = dnaService.exist(dna);
        if(Objects.nonNull(dnaDb))
            if(dnaDb)
                return new ResponseEntity<>(HttpStatus.OK) ;
            else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        List<String> dnaSecuences = new ArrayList<>();
        dnaSecuences.addAll(dna.getDna());

        Matrix matrix = dnaManager.buildMatrix(dna);

        dnaSecuences.addAll(dnaManager.getVerticallDnaSecuences(matrix));
        dnaSecuences.addAll(dnaManager.getDiagonals(matrix, mutantDnaBaseLength));

        int count = dnaManager.countSecuencesOnDna(dnaSecuences, dna.getMutantDnaBaseLength());

        if (count > 1)
            dna.setMutant(true);
        dnaService.save(dna);
        if (count > 1)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
