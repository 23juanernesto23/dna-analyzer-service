package com.challenge.dnaanalyzerservice;


import com.challenge.dnaanalyzerservice.api.DNARequest;
import com.challenge.dnaanalyzerservice.api.DnaAnalyzerController;
import com.challenge.dnaanalyzerservice.domain.DnaReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.bson.Document;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DnaAnalyzerServiceSteps {

	@Autowired
	DnaAnalyzerController restController;

	private Scenario scenario;

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final String DNA_COLLECTION_NAME = "dnaVerified";

	List<DNARequest> dnaRequestList;
	Gson gson = new Gson();

	@Before
	public void before(Scenario scenario) {
		this.scenario = scenario;
	}

	@Given("Elimino datos base de datos")
	public void eliminoDatosBaseDeDatos() {
		dnaRequestList = new ArrayList<>();
		mongoTemplate.getCollection(DNA_COLLECTION_NAME).deleteMany(new Document());
	}

	@Given("Consulto adn y guardo en base de datos")
	public void consulto_adn(DataTable dnaRequestTable) {

		dnaRequestTable.asMaps().stream().map(row -> {

			String[] p = gson.fromJson(row.get("dnaRequest"), String[].class);
			DNARequest dnaRequest = new DNARequest();
			dnaRequest.setDna(p);

			Assert.assertEquals(restController.isMutant(dnaRequest).getStatusCode().value(), Integer.valueOf(row.get("status")).intValue());

			return dnaRequest;
		}).collect(Collectors.toList());

	}


	@Then("Valido ratio")
	public void valido_ratio(DataTable ratioDataTable) {

		DnaReport dnaReport = restController.stats().getBody();

		ratioDataTable.asMaps().stream().map(row -> {

			Assert.assertEquals(dnaReport.getCount_human_dna(), Long.valueOf(row.get("count_human_dna")).longValue());
			Assert.assertEquals(dnaReport.getCount_mutant_dna(), Long.valueOf(row.get("count_mutant_dna")).longValue());
			Assert.assertEquals(dnaReport.getRatio(), Double.valueOf(row.get("ratio")).doubleValue(), 1);
			return row;
		}).collect(Collectors.toList());
	}


}
