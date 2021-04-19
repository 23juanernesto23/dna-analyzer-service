package com.challenge.dnaanalyzerservice.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Dna {
    private List<String> dna;
    private boolean mutant;
    private int mutantDnaBaseLength;
}
