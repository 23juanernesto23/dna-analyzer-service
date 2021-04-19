package com.challenge.dnaanalyzerservice;


import com.challenge.dnaanalyzerservice.api.DnaAnalyzerController;
import com.challenge.dnaanalyzerservice.config.MongoConfig;
import com.challenge.dnaanalyzerservice.repository.service.DnaService;
import com.challenge.dnaanalyzerservice.utils.DnaManager;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import io.cucumber.spring.CucumberContextConfiguration;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@SpringBootTest
@CucumberContextConfiguration
public class CucumberConfig {
/*
	@Autowired
	MongoConfig mongoConfig;

	@Value("${dna.mutant.base.length}")
	private int mutantDnaBaseLength;

	@Bean
	public MongoTemplate mongoTemplate() {

		ConnectionString connectionString = new ConnectionString(mongoConfig.getUri());

		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				pojoCodecRegistry);
		MongoClientSettings clientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.codecRegistry(codecRegistry)
				.build();
		return new MongoTemplate(MongoClients.create(clientSettings),mongoConfig.getName());
	}

	@Bean
	DnaAnalyzerController dnaAnalyzerController(@Autowired DnaService dnaService, @Autowired DnaManager dnaManager){
		return new DnaAnalyzerController();
	}*/

}
