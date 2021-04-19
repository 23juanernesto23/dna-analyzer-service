package com.challenge.dnaanalyzerservice;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(features ="src/test/resources",
		glue = "com.challenge.dnaanalyzerservice",
		plugin ={"pretty", "html:build/cucumber", "json:report/DnaAnalyzerServiceFeature.json"},
		monochrome = true)

public class DnaAnalyzerServiceStepsRunner {
}
