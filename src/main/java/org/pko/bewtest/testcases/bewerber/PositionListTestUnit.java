package org.pko.bewtest.testcases.bewerber;

import org.openqa.selenium.WebDriver;
import org.pko.bewtest.configuration.bewerber.BewerberTestConfigurationData;
import org.pko.bewtest.testcases.TestUnit;
import org.pko.bewtest.testcases.TestUnitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PositionListTest
 *
 * @author Peter Kolarik
 * @date 9.2.2022
 */
public class PositionListTestUnit implements TestUnit<BewerberTestConfigurationData>  {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionListTestUnit.class);

    @Override
    public String name() {
        return "Bewerber - Positions list - get first position test";
    }

    @Override
    public TestUnitResult execute(WebDriver driver, BewerberTestConfigurationData data) {
        LOGGER.info("{} execution starts", name());
        // todo implement
        LOGGER.info("{} execution ends", name());
        return TestUnitResult.asContinue(driver);
    }
}
