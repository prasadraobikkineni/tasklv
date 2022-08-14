package tech.automationlabs;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "features/", plugin = { "pretty", "summary" }, monochrome = true, snippets = CucumberOptions.SnippetType.CAMELCASE)
public class CucumberMain {

}
