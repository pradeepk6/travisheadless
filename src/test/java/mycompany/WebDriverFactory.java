package mycompany;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

final class WebDriverFactory {

    private WebDriverFactory() {

    }

    static EventFiringWebDriver create() {
        String webDriverProperty = getProperty("webdriver");
        boolean headless = getProperty("webdriver.headless").contentEquals("1");

        if (webDriverProperty == null || webDriverProperty.isEmpty()) {
            //throw new IllegalStateException("The webdriver system property must be set");
        }

        try {
            return new EventFiringWebDriver(
                    Drivers.valueOf(webDriverProperty.toUpperCase()).newDriver(headless));
        } catch (IllegalArgumentException e) {
            String msg = format("The webdriver system property '%s' did not match any " +
                            "existing browser or the browser was not supported on your operating system. " +
                            "Valid values are %s",
                    webDriverProperty, stream(Drivers
                            .values())
                            .map(Enum::name)
                            .map(String::toLowerCase)
                            .collect(toList()));

            throw new IllegalStateException(msg, e);
        }
    }

    private enum Drivers {
        FIREFOX {
            @Override
            public WebDriver newDriver(boolean headless) {
                FirefoxOptions options = new FirefoxOptions();
                options.setLogLevel(FirefoxDriverLogLevel.ERROR);
                if (headless) options.addArguments("-headless");
                return new FirefoxDriver(options);
            }
        }, CHROME {
            @Override
            public WebDriver newDriver(boolean headless) {
                ChromeOptions options = new ChromeOptions();
                if (headless) options.addArguments("-headless");
                return new ChromeDriver(options);
            }
        };

        public abstract WebDriver newDriver(boolean headless);

    }
}
