package com.challenge.dnaanalyzerservice.repository.service;

import com.challenge.dnaanalyzerservice.domain.DnaReport;
import com.challenge.dnaanalyzerservice.repository.DnaRepository;
import com.challenge.dnaanalyzerservice.domain.Dna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DnaService {

    @Autowired
    DnaRepository dnaRepository;


    public Boolean exist(Dna dna) {
        return dnaRepository.findOne(dna);
    }

    public void save(Dna dna) {
        dnaRepository.insertOne(dna);
    }

    public DnaReport obtaintStats() {
        return dnaRepository.buildReport();
    }
}
