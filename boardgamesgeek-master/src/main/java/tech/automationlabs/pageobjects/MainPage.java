package tech.automationlabs.pageobjects;

import static tech.automationlabs.config.ApplicationProperties.applicationProperties;
import static tech.automationlabs.webdriver.WebDriverWrapper.webDriverWrapper;

public class MainPage {
	public static final MainPage mainPage = new MainPage();

	private MainPage() {
	}

	public void navigate() {
		webDriverWrapper.get(applicationProperties.getSystsemUnderTestUrl());
	}

	public void acceptPrivacyPolicy() {
		webDriverWrapper.click("//button[contains(text(), \"I'm OK with that\")]");
	}

	public void selectTheFirstGameInHotnessSection() {
		webDriverWrapper.click("//gg-home-game-explorer-row-hotness-items//li[1]//a");
	}
}
