package com.challenge.dnaanalyzerservice.utils;

import com.challenge.dnaanalyzerservice.api.DNARequest;
import com.challenge.dnaanalyzerservice.domain.Dna;
import com.challenge.dnaanalyzerservice.domain.Matrix;
import com.challenge.dnaanalyzerservice.domain.NitrogenousBase;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Controller
public class DnaManager {

    public int countMutants(Dna dna) {
        List<String> dnaSecuences = new ArrayList<>();
        dnaSecuences.addAll(dna.getDna());

        Matrix matrix = buildMatrix(dna);

        dnaSecuences.addAll(getVerticallDnaSecuences(matrix));
        dnaSecuences.addAll(getDiagonals(matrix, dna.getMutantDnaBaseLength()));

        return countSecuencesOnDna(dnaSecuences, dna.getMutantDnaBaseLength());
    }

    public boolean isValidDnaChain(DNARequest dnaRequest) {
        int firstDnaSecuenceLength = dnaRequest.getDna()[0].length();
        Predicate<String> predicate = dnaSecuence -> dnaSecuence.length() == firstDnaSecuenceLength && dnaSecuence.length() == dnaRequest.getDna().length ;
        return Arrays.stream(dnaRequest.getDna()).allMatch(predicate);
    }

    public int countSecuencesOnDna(List<String> dna, int mutantDnaBaseLength) {
        int count = 0;
        for (String str : dna) {
            Predicate<NitrogenousBase> predicate = nitrogenousBase -> str.contains(getDnaSecuence(mutantDnaBaseLength, nitrogenousBase));
            if (Arrays.stream(NitrogenousBase.values()).anyMatch(predicate))
                count++;
        }
        return count;
    }

    //"$4s"
    String getDnaSecuence(int mutantDnaBaseLength, NitrogenousBase nitrogenousBase) {
        String format = "%" + mutantDnaBaseLength + "s";
        return String.format(format, nitrogenousBase.name()).replace(" ", nitrogenousBase.name());
    }

    public List<String> getVerticallDnaSecuences(Matrix matrix) {

        List<String> verticallDnaSecuences = new ArrayList<>();

        int rows = matrix.getRows();
        int columns = matrix.getColumns();

        for (int col = 0; col < columns; col++) {
            StringBuilder sb = new StringBuilder();
            for (int row = 0; row < rows; row++)
                sb.append(matrix.getMatrix()[row][col]);

            verticallDnaSecuences.add(sb.toString());
        }
        return verticallDnaSecuences;
    }

    public List<String> getDiagonals(Matrix matrix, int mutantDnaBaseLength) {
        int rows = matrix.getRows();
        int columns = matrix.getColumns();

        List<String> diagonals = new ArrayList<>();

        for (int a = 0; a <= rows - 2; a++)
            if (a <= rows - mutantDnaBaseLength) {
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                for (int j = 0, i = a; i <= rows - 1; j++, i++) {
                    char diagonal = matrix.getMatrix()[i][j];
                    char antiDiagonal = matrix.getMatrix()[i][columns - j - 1];
                    sb.append(diagonal);
                    sb2.append(antiDiagonal);
                }
                diagonals.add(sb.toString());
                diagonals.add(sb2.toString());
            } else {
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                for (int j = 0, i = a; i >= 0; j++, i--) {
                    char antiDiagonal = matrix.getMatrix()[i][j];
                    char diagonal = matrix.getMatrix()[i][columns - j - 1];
                    sb.append(antiDiagonal);
                    sb2.append(diagonal);
                }
                diagonals.add(sb.toString());
                diagonals.add(sb2.toString());
            }
        return diagonals;
    }

    public Matrix buildMatrix(Dna dna) {
        int rows = dna.getDna().size();
        int columns = dna.getDna().get(0).length();
        Matrix matrix = new Matrix(rows, columns, new char[rows][columns]);

        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++)
                matrix.getMatrix()[i][j] = dna.getDna().get(i).charAt(j);

        return matrix;
    }

}
