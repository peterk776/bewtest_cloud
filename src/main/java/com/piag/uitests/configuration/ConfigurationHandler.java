package com.piag.uitests.configuration;

import java.util.List;

/**
 * ConfigurationHandler
 *
 * @author Peter Kolarik
 * @date 4.2.2022
 */
public interface ConfigurationHandler<T extends TestConfigurationData> {

    List<T> getConfigurations();

    T addConfiguration(String json);

    T removeConfiguration(String endpointUrl, String companyEid, String positionId);
}
