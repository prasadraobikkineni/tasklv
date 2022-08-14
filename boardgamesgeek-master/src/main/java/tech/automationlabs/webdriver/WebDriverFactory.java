package tech.automationlabs.webdriver;

import static tech.automationlabs.config.ApplicationProperties.applicationProperties;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverFactory {
	private static final Logger LOG = LoggerFactory.getLogger(WebDriverFactory.class);

	private int IMPLICIT_TIMEOUT_SECONDS = 10;

	public static final WebDriverFactory webDriverFactory = new WebDriverFactory();

	private WebDriverFactory() {
	}

	private WebDriver driver;

	public WebDriver getDriver() {
		if (driver == null) {
			try {
				LOG.debug("Initializing web driver...");
				driver = getLocalInstance();
				driver.manage().timeouts().implicitlyWait(IMPLICIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				LOG.debug("Web Driver initialized!");
			} catch (Exception e) {
				LOG.error("Could not not create driver", e);
				throw new RuntimeException(e);
			}
		}
		return driver;
	}

	public WebDriverWait getWait(int seconds) {
		return new WebDriverWait(getDriver(), seconds);
	}

	private WebDriver getLocalInstance() throws IOException {
		System.setProperty("webdriver.chrome.driver", getDriverURL());
		ChromeOptions options = new ChromeOptions();
		if (applicationProperties.isRunTestsInHeadlessMode()) {
			options.addArguments("--no-sandbox");
			options.addArguments("--headless");
		}
		return new ChromeDriver(options) {
			public void destroy() {
				closeImpl();
			}

			public void close() {
				closeImpl();
			}

			private void closeImpl() {
				// in developer mode, leave the browser open after the tests finish to allow
				// some debugging etc
				if (!applicationProperties.isDeveloperMode()) {
					driver.quit();
				}
			}
		};
	}

	private String getDriverURL() throws IOException {
		String driverName = null;
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("windows")) {
			driverName = "chromedriver.exe";
		} else if (osName.contains("mac")) {
			driverName = "mac/chromedriver";
		} else {
			driverName = "chromedriver";
		}

		return applicationProperties.getDriversDir() + driverName;
	}
}
