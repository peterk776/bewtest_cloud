package org.pko.bewtest.data;

import org.pko.bewtest.configuration.TestConfigurationData;

import java.io.Serializable;

/**
 * TestResultIf
 * 
 * @date 11.2.2022
 * @author Peter Kolarik
 */
public interface TestResult<T extends TestConfigurationData> extends Serializable {

    TestInputData getTestData();

    String getTarget();

    State getState();

    String getMessage();

    String getResponsible();

    enum State {
        OK,
        FAILED_TEST,
        ERROR
    }
}
