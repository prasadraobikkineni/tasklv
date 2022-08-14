package tech.automationlabs.steps;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static tech.automationlabs.pageobjects.BoardgamePage.boardgamePage;
import static tech.automationlabs.pageobjects.MainPage.mainPage;
import static tech.automationlabs.xml.BggApiClient.bggApiClient;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

	private static final Logger LOG = LoggerFactory.getLogger(StepDefinitions.class);

	@Given("I am at the BoardGamesGeek home page")
	public void iAmTheBoardGamesGeekHomePage() {
		mainPage.navigate();
	}

	@When("I accept the Privacy Policy")
	public void iAcceptThePrivacyPolicy() {
		mainPage.acceptPrivacyPolicy();
	}

	@When("I click the first game in the Hotness section")
	public void iClickTheFirstGameInTheHotnessSection() {
		mainPage.selectTheFirstGameInHotnessSection();
	}

	@Then("Language Dependence displayed on the page will be the same as the one retrieved from the REST API")
	public void languageDependanceOnThePageWillBeTheSameAsTheOneRetrievedFromTheAPI() {
		String uiTopOptions = boardgamePage.getLanguageDependenceTopOptions();
		List<String> apiTopOptions = bggApiClient.getLanguageDependenceTopOptions(boardgamePage.getGameId());
		LOG.debug("uiTopOptions: " + uiTopOptions + "; apiTopOptions: " + apiTopOptions);
		if (uiTopOptions.contains("no votes")) {
			assertThat("'No Votes displayed in UI but got Votes from the API: '" + apiTopOptions, apiTopOptions.size(), is(0));
		} else {
			for (String apiTopOption : apiTopOptions) {
				assertTrue("Option returned in the API is not displayed:  " + apiTopOption, uiTopOptions.contains(apiTopOption));
			}
		}
	}
}
