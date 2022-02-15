package org.pko.bewtest.execute;

import com.google.common.collect.Lists;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.pko.bewtest.configuration.bewerber.BewerberTestConfigurationData;
import org.pko.bewtest.data.BewerberTestResult;
import org.pko.bewtest.data.TestResult;
import org.pko.bewtest.testcases.SeleniumTestsUtil;
import org.pko.bewtest.testcases.TestUnit;
import org.pko.bewtest.testcases.TestUnitResult;
import org.pko.bewtest.testcases.bewerber.BewerbungResultTestUnit;
import org.pko.bewtest.testcases.bewerber.BewerbungSendUnit;
import org.pko.bewtest.testcases.bewerber.PositionFormTestUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * BewerberTestUnit
 *
 * @author Peter Kolarik
 * @date 3.2.2022
 */
@Component
public class SelectPositionCreateBewerbungTestSuite implements TestSuite<BewerberTestConfigurationData> {
    @Value("${standalone.chrome.container.url}")
    private String standaloneContainerUrl;

    public static final String BEWERBUNG_OK_TEXT = "Vielen Dank für Ihre Bewerbung";

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectPositionCreateBewerbungTestSuite.class);

    private final List<TestUnit<BewerberTestConfigurationData>> testUnits = Lists.newArrayList(
            // new PositionListTestUnit(),
            new PositionFormTestUnit(),
            new BewerbungSendUnit(),
            new BewerbungResultTestUnit()
    );

    @Override
    public TestResult<BewerberTestConfigurationData> execute(BewerberTestConfigurationData configData) {
        LOGGER.info("Executing test suite {}, for url {}, companyEid {}, positionId {} ", SelectPositionCreateBewerbungTestSuite.class.getName(), configData.getEndpointUrl(), configData.getCompanyEid(), configData.getPositionId());
        RemoteWebDriver driver = null;
        try {
            // init driver...
            driver = SeleniumTestsUtil.initDriver(configData, standaloneContainerUrl);
            if (driver == null)
                return new BewerberTestResult<>( configData, TestResult.State.ERROR, "Unable to initialize RemoteWebDriver");

            // window resize
            SeleniumTestsUtil.windowResize(driver);

            for (TestUnit<BewerberTestConfigurationData> testUnit : testUnits) {
                TestUnitResult tur = testUnit.execute(driver, configData);
                if (tur.getResultToCheck() != null && BEWERBUNG_OK_TEXT.equalsIgnoreCase(tur.getResultToCheck())) {
                    return new BewerberTestResult<>( configData, TestResult.State.OK, "ok");
                }
            }
            return new BewerberTestResult<>( configData, TestResult.State.FAILED_TEST,"UI test not successful");
        } catch (NoSuchElementException nse) {
            LOGGER.warn("UI Selenium Test failure - expected element not found {}", nse.getMessage());
            return new BewerberTestResult<>( configData, TestResult.State.FAILED_TEST,"UI test not successful: " + nse.getMessage());
        } catch (Exception e) {
            LOGGER.warn("Problem executing test", e);
            return new BewerberTestResult<>( configData, TestResult.State.ERROR,"UI test not successful: " + e);
        } finally {
            if (driver != null)
                driver.quit();
            LOGGER.info("Driver closed");
            LOGGER.info("End of execution test suite {}, for url {}, companyEid {}, positionId {} ", SelectPositionCreateBewerbungTestSuite.class.getName(), configData.getEndpointUrl(), configData.getCompanyEid(), configData.getPositionId());
        }

    }

    @Override
    public Iterator<TestUnit<BewerberTestConfigurationData>> iterator() {
        return testUnits.iterator();
    }

    @Override
    public void forEach(Consumer<? super TestUnit<BewerberTestConfigurationData>> action) {
        testUnits.forEach(action);
    }

    @Override
    public Spliterator<TestUnit<BewerberTestConfigurationData>> spliterator() {
        return testUnits.spliterator();
    }
}
