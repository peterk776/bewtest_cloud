package com.piag.uitests.testcases;

import org.openqa.selenium.WebDriver;
import com.piag.uitests.configuration.TestConfigurationData;

/**
 * TestUnit
 *
 * @author Peter Kolarik
 * @date 9.2.2022
 */
public interface TestUnit<T extends TestConfigurationData> {

    String name();

    TestUnitResult execute(WebDriver driver, T data);
}
