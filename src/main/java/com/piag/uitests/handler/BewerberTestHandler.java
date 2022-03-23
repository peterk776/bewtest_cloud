package com.piag.uitests.handler;

import com.piag.uitests.configuration.ConfigurationHandler;
import com.piag.uitests.configuration.bewerber.BewerberConfigurationHandler;
import com.piag.uitests.configuration.bewerber.BewerberTestConfigurationData;
import com.piag.uitests.execute.SelectPositionCreateBewerbungTestSuite;
import com.piag.uitests.execute.TestSuite;
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
