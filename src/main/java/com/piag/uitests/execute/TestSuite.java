package com.piag.uitests.execute;

import com.piag.uitests.data.TestResult;
import com.piag.uitests.testcases.TestUnit;
import com.piag.uitests.configuration.TestConfigurationData;

/**
 * TestExecutor
 *
 * @author Peter Kolarik
 * @date 3.2.2022
 */
public interface TestSuite<T extends TestConfigurationData> extends Iterable<TestUnit<T>> {

    TestResult<T> execute(T configData);
}
