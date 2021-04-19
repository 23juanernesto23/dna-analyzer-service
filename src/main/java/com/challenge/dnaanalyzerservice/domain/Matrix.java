package com.challenge.dnaanalyzerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Matrix {
    int rows;
    int columns;
    private char[][] matrix;
}
