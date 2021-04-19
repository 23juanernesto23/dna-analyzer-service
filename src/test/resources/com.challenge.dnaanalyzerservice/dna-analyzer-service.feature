Feature: Dna Analyzer Service


  Background:
    Given Elimino datos base de datos


  Scenario: Consulta de secuencias de adn
    Given Consulto adn y guardo en base de datos
      | dnaRequest                                                      | status   |
      | ["TCTCCA","CAGGGG","GATTAA","AGACGA","CTGCAT","FCGGRA"]         | 200      |
      | ["TTGTGA","CAGTGC","TTATCT","AGCTCG","CGTCAA","TCACTG"]         | 403      |
      | ["TCGTGA","TAGTGC","TTATCT","TGGCCG","TGCTAA","ACACTG"]         | 200      |
      | ["TTGCGA","CAGTGC","TTCTGT","AGCACG","CCTCAA","TCACTG"]         | 403      |
      | ["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]         | 403      |
    Then Valido ratio
      | count_mutant_dna  | count_human_dna | ratio |
      | 2                 | 3               | 0.66 |
