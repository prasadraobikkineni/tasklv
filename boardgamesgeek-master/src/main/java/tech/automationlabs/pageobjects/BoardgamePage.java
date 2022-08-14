package tech.automationlabs.pageobjects;

import static tech.automationlabs.webdriver.WebDriverFactory.webDriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BoardgamePage {
	public static final BoardgamePage boardgamePage = new BoardgamePage();

	private BoardgamePage() {
	}

	public String getLanguageDependenceTopOptions() {
		WebElement languageDependencyElement = webDriverFactory.getDriver()
				.findElement(By.xpath("//div[contains(text(),'Language Dependence')]/following-sibling::div/span/span"));
		return languageDependencyElement.getText();
	}

	public String getGameId() {
		String url = webDriverFactory.getDriver().getCurrentUrl();
		return extractGameId(url);
	}

	private String extractGameId(String url) {
		String delim = "/boardgame/";
		int pos = url.lastIndexOf(delim) + delim.length();
		return url.substring(pos, url.lastIndexOf("/"));
	}
}
