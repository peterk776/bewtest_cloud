package org.pko.bewtest.testcases.bewerber;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.pko.bewtest.configuration.bewerber.BewerberTestConfigurationData;
import org.pko.bewtest.testcases.TestUnit;
import org.pko.bewtest.testcases.TestUnitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * BewerbungSendUnit
 *
 * @author Peter Kolarik
 * @date 9.2.2022
 */
public class BewerbungSendUnit implements TestUnit<BewerberTestConfigurationData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BewerbungSendUnit.class);

    @Override
    public String name() {
        return "Bewerber - Send bewerbung test unit";
    }

    @Override
    public TestUnitResult execute(WebDriver driver, BewerberTestConfigurationData data) {
        driver.findElement(By.xpath("//div[@data-uin=\"btn-Jetzt_bewerben\"]")).click();
        LOGGER.info("waiting for bewerbung result with timeout 10 seconds ...");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return TestUnitResult.asContinue(driver);
    }
}
