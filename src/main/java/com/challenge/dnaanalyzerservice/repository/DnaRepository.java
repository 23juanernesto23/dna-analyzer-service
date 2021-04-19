package com.challenge.dnaanalyzerservice.repository;

import com.challenge.dnaanalyzerservice.domain.Dna;
import com.challenge.dnaanalyzerservice.domain.DnaReport;
import com.mongodb.client.model.Filters;
import lombok.Data;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@Data
public class DnaRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String DNA_COLLECTION_NAME = "dnaVerified";

    public void insertOne(Dna dna) {
        mongoTemplate.getCollection(DNA_COLLECTION_NAME)
                .insertOne(new Document("dna", dna.getDna())
                        .append("mutantDnaBaseLength", dna.getMutantDnaBaseLength())
                        .append("isMutant", dna.isMutant())
                        .append("date", LocalDateTime.now()));
    }

    public Boolean findOne(Dna dna){
        Bson dnaFilter = Filters.eq("dna",dna.getDna());
        Bson baseLengthFilter = Filters.eq("mutantDnaBaseLength", dna.getMutantDnaBaseLength());
        Document document = mongoTemplate
                .getCollection(DNA_COLLECTION_NAME)
                .find(Filters.and(dnaFilter, baseLengthFilter))
                .first();
        if(document != null)
           return document.getBoolean("isMutant");
        return null;
    }

    public DnaReport buildReport() {

        Bson mutantFilter = Filters.eq("isMutant",true);
        Bson humanFilter = Filters.eq("isMutant",false);
        long humans = mongoTemplate.getCollection(DNA_COLLECTION_NAME).countDocuments(humanFilter);
        long mutants = mongoTemplate.getCollection(DNA_COLLECTION_NAME).countDocuments(mutantFilter);
        double ratio = Double.valueOf(mutants)/ Double.valueOf(humans);
        return DnaReport.builder().count_human_dna(humans).count_mutant_dna(mutants).ratio(ratio).build();
    }
}
