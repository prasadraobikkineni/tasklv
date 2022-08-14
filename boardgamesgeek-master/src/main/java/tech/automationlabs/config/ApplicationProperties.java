package tech.automationlabs.config;

import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationProperties.class);

	private Properties props = new Properties();

	public static final ApplicationProperties applicationProperties = new ApplicationProperties();

	private ApplicationProperties() {
		load(getClasspathResource("app.properties"));
	}

	public String getProperty(String property) {
		return getString(property);
	}

	public boolean isRunTestsInHeadlessMode() {
		return getBoolean("RUN_TESTS_IN_HEADLESS_MODE");
	}

	public String getDriversDir() {
		String driversDir = getString("DRIVERS_DIR");
		if (!driversDir.endsWith("/")) {
			driversDir += "/";
		}
		return driversDir;
	}

	public String getSystsemUnderTestUrl() {
		return getString("SUT_URL");
	}

	public boolean isDeveloperMode() {
		return getBoolean("DEVELOPER_MODE");
	}

	private boolean getBoolean(String property) {
		return Boolean.parseBoolean(getString(property));
	}

	private String getString(String key) {
		String value = System.getProperty(key);
		if (!StringUtils.isBlank(value)) {
			return value;
		}

		value = System.getenv(key);
		if (!StringUtils.isBlank(value)) {
			return value;
		}
		return props.getProperty(key);
	}

	public void load(InputStream inputStream) {
		if (inputStream != null) {
			try {
				Properties properties = new Properties();
				properties.load(inputStream);
				Enumeration<Object> keys = properties.keys();
				while (keys.hasMoreElements()) {
					Object key = keys.nextElement();
					Object value = properties.get(key);
					LOG.trace(format("Adding property %s=%s", key.toString(), value.toString()));
					props.put(key, value);
				}
			} catch (IOException e) {
				LOG.warn("Error loading properties", e);
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private InputStream getClasspathResource(String path) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		return loader.getResourceAsStream(path);
	}
}
