package com.piag.uitests.testcases;

import org.openqa.selenium.WebDriver;

/**
 * TestUnitResult
 *
 * @author Peter Kolarik
 * @date 9.2.2022
 */
public class TestUnitResult {
    private final String resultToCheck;
    private final WebDriver driver;

    private TestUnitResult(WebDriver driver, String resultToCheck) {
        this.driver = driver;
        this.resultToCheck = resultToCheck;
    }

    public static TestUnitResult asContinue(WebDriver driver) {
        return new TestUnitResult(driver, null);
    }

    public static TestUnitResult asTerminal(WebDriver driver, String testExpression) {
        return new TestUnitResult(driver, testExpression);
    }

    public String getResultToCheck() {
        return resultToCheck;
    }
}
