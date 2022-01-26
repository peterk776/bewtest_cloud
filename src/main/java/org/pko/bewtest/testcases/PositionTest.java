package org.pko.bewtest.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.pko.bewtest.data.TestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * PositionTest
 * Running standalone app with browser in docker container just candidate form.
 *
 * @author Peter Kolarik
 * @date 21.1.2022
 */
public class PositionTest {

    public static final String BEWERBUNG_OK_TEXT = "Vielen Dank für Ihre Bewerbung";

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionTest.class);

    public static TestStatus execute(String remoteWebDriverUrl, String appUrl) {
        WebDriver driver = null;
        try {
            ChromeOptions opt = new ChromeOptions();
            opt.setCapability(CapabilityType.SUPPORTS_NETWORK_CONNECTION, true);
            driver = new RemoteWebDriver(new URL(remoteWebDriverUrl), opt);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            LOGGER.info("Connecting to " + appUrl);
            driver.get(appUrl);
            LOGGER.info("Driver initialized");
            boolean result = doTest(driver);
            if (result) {
                LOGGER.info("Bewerbung sending test - OK");
                return TestStatus.okStatus();
            }
            else {
                LOGGER.warn("Bewerbung sending test - failed");
                return TestStatus.failedStatus("bewerbung not loaded");
            }
        } catch (Exception e) {
            LOGGER.warn("Problem running selenium test", e);
            return TestStatus.failedStatus(e.toString());
        } finally {
            if (driver != null)
                driver.quit();
            LOGGER.info("Driver closed");
        }
    }

    private static boolean doTest(WebDriver driver) throws InterruptedException {
        // wait to load list of positions
        LOGGER.info("doTest starts");
        TimeUnit.SECONDS.sleep(3);
        LOGGER.info("window resize ...");
        driver.manage().window().setSize(new Dimension(1102, 876));
        LOGGER.info("loading form ...");
        TimeUnit.SECONDS.sleep(5);
        LOGGER.info("finding first element ...");
        driver.findElement(By.name("59d0b92e-9d7f-4089-9098-3d9162461259")).sendKeys("Herr", Keys.ENTER, Keys.TAB);
        TimeUnit.SECONDS.sleep(1);
        LOGGER.info("find and fill rest of form ...");
        driver.findElement(By.name("d31760f2-7219-40be-85c6-834f192408e3")).sendKeys("Tester");
        driver.findElement(By.name("51ecd5db-195f-4134-aab3-fcb57566d46c")).sendKeys("Iftester");
        driver.findElement(By.name("41475411-8483-490b-943f-287263a83bce")).sendKeys("pkolarik@pi-ag.com");
        LOGGER.info("click bewerben button ...");
        driver.findElement(By.xpath("//div[@data-uin=\"btn-Jetzt_bewerben\"]")).click();
        LOGGER.info("waiting for bewerbung result with timeout 15 seconds ...");
        TimeUnit.SECONDS.sleep(15);
        String checkText = driver.findElement(By.xpath("//div[@class=\"BW-WebPositionPage\"]/div/h2")).getText();
        LOGGER.info("doTest ends");
        return BEWERBUNG_OK_TEXT.equalsIgnoreCase(checkText);
    }
}
