package org.pko.bewtest.testcases;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.pko.bewtest.configuration.TestConfigurationData;
import org.pko.bewtest.configuration.TestFieldData;
import org.pko.bewtest.configuration.bewerber.BewerberTestConfigurationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * SeleniumTestsUtil
 *
 * @author Peter Kolarik
 * @date 9.2.2022
 */
public class SeleniumTestsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumTestsUtil.class);

    public static RemoteWebDriver initDriver(BewerberTestConfigurationData configData, String containerUrl) {
        String appUrl = configData.getEndpointUrl() + "?companyEid=" + configData.getCompanyEid() + "#position,id=" + configData.getPositionId();
        try {
            ChromeOptions opt = new ChromeOptions();
            opt.setCapability(CapabilityType.SUPPORTS_NETWORK_CONNECTION, true);
            RemoteWebDriver driver = new RemoteWebDriver(new URL(containerUrl), opt);
            // wait to init driver
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            LOGGER.info("Connecting to " + appUrl);
            driver.get(appUrl);
            return driver;
        } catch (Exception e) {
            LOGGER.warn("Unable to get remote driver instance {} for app url {} ", containerUrl, appUrl, e);
            return null;
        }
    }

    public static void fillFormFields(WebDriver driver, TestConfigurationData configData) {
        configData.getFieldDataList().forEach(fieldData -> fillFieldConsumer.accept(fieldData, driver));
    }

    private static final BiConsumer<TestFieldData, WebDriver> fillFieldConsumer = (data, driver) -> {
        final WebElement element = driver.findElement(By.name(data.getName()));
        switch (data.getType()) {
            case COMBOBOX:
                element.sendKeys(data.getDefaultValue(), Keys.ENTER, Keys.TAB);
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                break;
            default:
                element.sendKeys(data.getDefaultValue());
                break;
        }
    };

    public static WebDriver windowResize(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        LOGGER.info("window resize ...");
        driver.manage().window().setSize(new Dimension(1280, 1024));
        LOGGER.info("loading form ...");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }
}
