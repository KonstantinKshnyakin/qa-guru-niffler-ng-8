package guru.qa.niffler.jupiter.extension;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import guru.qa.niffler.config.Config;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class BrowserExtension implements
    BeforeEachCallback,
    AfterEachCallback,
    TestExecutionExceptionHandler,
    LifecycleMethodExecutionExceptionHandler {

    static {
        String browser = System.getenv("BROWSER");
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }

        Configuration.browser = browser;
        Configuration.timeout = 8000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.remote = Config.getInstance().selenoidUrl();

        MutableCapabilities capabilities;
        if ("chrome".equals(browser)) {
            Configuration.browserVersion = "127.0";
            capabilities = new ChromeOptions()
                .addArguments("--no-sandbox");
        } else if ("firefox".equals(browser)) {
            Configuration.browserVersion = "125.0";
            capabilities = new FirefoxOptions();
        } else {
            throw new IllegalArgumentException("Браузер не поддерживается: " + browser);
        }
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        capabilities.setCapability("selenoid:options", selenoidOptions);
        Configuration.browserCapabilities = capabilities;
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Selenide.closeWebDriver();
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        SelenideLogger.addListener("Allure-selenide", new AllureSelenide()
            .savePageSource(false)
            .screenshots(false)
        );
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        doScreenshot();
        throw throwable;
    }

    @Override
    public void handleBeforeEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        doScreenshot();
        throw throwable;
    }

    @Override
    public void handleAfterEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        doScreenshot();
        throw throwable;
    }

    private void doScreenshot() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Allure.addAttachment(
                "Screen on fail",
                new ByteArrayInputStream(
                    ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES)
                )
            );
            String source = WebDriverRunner.source();
            if (source != null) {
                Allure.addAttachment("Page source", "text/html", source, "html");
            }
        }
    }
}
