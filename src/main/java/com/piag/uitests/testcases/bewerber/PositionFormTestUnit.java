package com.piag.uitests.testcases.bewerber;

import com.piag.uitests.testcases.SeleniumTestsUtil;
import com.piag.uitests.testcases.TestUnit;
import com.piag.uitests.testcases.TestUnitResult;
import org.openqa.selenium.WebDriver;
import com.piag.uitests.configuration.bewerber.BewerberTestConfigurationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * PositionFormTest
 *
 * @author Peter Kolarik
 * @date 9.2.2022
 */
public class PositionFormTestUnit implements TestUnit<BewerberTestConfigurationData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionFormTestUnit.class);

    @Override
    public String name() {
        return "Bewerber - Bewerbung form test";
    }

    @Override
    public TestUnitResult execute(WebDriver driver, BewerberTestConfigurationData data) {
        LOGGER.info("{} execution starts", name());
        LOGGER.info("loading form ...");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        LOGGER.info("finding first element ...");
        SeleniumTestsUtil.fillFormFields(driver, data);
        LOGGER.info("all elements filled");
        LOGGER.info("{} execution ends", name());
        return TestUnitResult.asContinue(driver);
    }
}
