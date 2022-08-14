package tech.automationlabs.webdriver;

import static tech.automationlabs.webdriver.WebDriverFactory.webDriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WebDriverWrapper {
	public static final WebDriverWrapper webDriverWrapper = new WebDriverWrapper();

	private WebDriverWrapper() {
	}

	public void get(String url) {
		webDriverFactory.getDriver().get(url);
		waitUntilReadyStateIsComplete();
	}

	private void waitUntilReadyStateIsComplete() {
		webDriverFactory.getWait(10)
				.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	}

	public void click(String xpath) {
		int attempts = 10;
		WebDriverException ex = null;
		while (attempts-- > 0) {
			try {
				WebElement element = webDriverFactory.getWait(10).until(ExpectedConditions.elementToBeClickable((By.xpath(xpath))));
				element.click();
				waitUntilReadyStateIsComplete();
				return;
			} catch (WebDriverException e) {
				sleep(1);
				ex = e;
			}
		}
		if (ex != null) {
			throw ex;
		}
	}

	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e1) {
		}
	}
}
