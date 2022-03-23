package com.piag.uitests.handler;

import com.piag.uitests.configuration.ConfigurationHandler;
import com.piag.uitests.configuration.TestConfigurationData;
import com.piag.uitests.execute.TestSuite;

/**
 * TestHandler
 *
 * @author Peter Kolarik
 * @date 9.2.2022
 */
public interface TestHandler<T extends TestConfigurationData> {

    ConfigurationHandler<T> configuration();

    TestSuite<T> executor();
}
