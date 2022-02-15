package org.pko.bewtest.execute;

import org.pko.bewtest.configuration.TestConfigurationData;
import org.pko.bewtest.data.TestResult;
import org.pko.bewtest.testcases.TestUnit;

/**
 * TestExecutor
 *
 * @author Peter Kolarik
 * @date 3.2.2022
 */
public interface TestSuite<T extends TestConfigurationData> extends Iterable<TestUnit<T>> {

    TestResult<T> execute(T configData);
}
