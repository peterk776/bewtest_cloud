package org.pko.bewtest.handler;

import org.pko.bewtest.configuration.ConfigurationHandler;
import org.pko.bewtest.configuration.TestConfigurationData;
import org.pko.bewtest.execute.TestSuite;

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
