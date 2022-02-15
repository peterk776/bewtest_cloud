package org.pko.bewtest.handler;

import org.pko.bewtest.configuration.ConfigurationHandler;
import org.pko.bewtest.configuration.bewerber.BewerberConfigurationHandler;
import org.pko.bewtest.configuration.bewerber.BewerberTestConfigurationData;
import org.pko.bewtest.execute.SelectPositionCreateBewerbungTestSuite;
import org.pko.bewtest.execute.TestSuite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * BewerberTestHandler
 *
 * @author Peter Kolarik
 * @date 9.2.2022
 */
@Component
public class BewerberTestHandler implements TestHandler<BewerberTestConfigurationData>{

    @Autowired
    SelectPositionCreateBewerbungTestSuite executor;

    @Autowired
    BewerberConfigurationHandler configurationHandler;

    @Override
    public ConfigurationHandler<BewerberTestConfigurationData> configuration() {
        return configurationHandler;
    }

    @Override
    public TestSuite<BewerberTestConfigurationData> executor() {
        return executor;
    }
}
