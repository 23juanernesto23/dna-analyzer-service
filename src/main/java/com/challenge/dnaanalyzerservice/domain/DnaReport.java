package com.challenge.dnaanalyzerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DnaReport {

    private long count_mutant_dna;
    private long count_human_dna;
    private double ratio;

}
