package org.pko.bewtest.testcases.bewerber;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.pko.bewtest.configuration.bewerber.BewerberTestConfigurationData;
import org.pko.bewtest.testcases.TestUnit;
import org.pko.bewtest.testcases.TestUnitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BewerbungResultTest
 *
 * @author Peter Kolarik
 * @date 9.2.2022
 */
public class BewerbungResultTestUnit implements TestUnit<BewerberTestConfigurationData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BewerbungResultTestUnit.class);

    @Override
    public String name() {
        return "Bewerber - Bewerbung sent result test unit";
    }

    @Override
    public TestUnitResult execute(WebDriver driver, BewerberTestConfigurationData data) {
        LOGGER.info("{} execution starts", name());
        String checkText = driver.findElement(By.xpath("//div[@class=\"BW-WebPositionPage\"]/div/h2")).getText();
        LOGGER.info("{} execution ends", name());
        return TestUnitResult.asTerminal(driver, checkText);
    }
}
