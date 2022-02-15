package org.pko.bewtest.configuration.bewerber;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.pko.bewtest.configuration.AbstractConfigurationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * BewerberConfigurationHandler
 *
 * @author Peter Kolarik
 * @date 4.2.2022
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BewerberConfigurationHandler extends AbstractConfigurationHandler<BewerberTestConfigurationData> {

    // some kind of cache to avoid work with filesystem's json files, but consider that.
    // if there won't be a lot of configurations should not be a problem, so we have it stateless
    private final Queue<BewerberTestConfigurationData> configurations = new ConcurrentLinkedQueue<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(BewerberConfigurationHandler.class);

    @Value("${configuration.bew.file.path}")
    private String configBewFilePath;

    @PostConstruct
    protected void init() {
        readConfiguration(configBewFilePath);
    }

    @Override
    public List<BewerberTestConfigurationData> getConfigurations() {
        return ImmutableList.copyOf(configurations.iterator());
    }

    @Override
    public BewerberTestConfigurationData addConfiguration(String json) {
        return addPersistConfiguration(configBewFilePath, json);
    }

    @Override
    public BewerberTestConfigurationData removeConfiguration(String endpointUrl, String companyEid, String positionId) {
        for (Iterator<BewerberTestConfigurationData> iterator = configurations.iterator(); iterator.hasNext();) {
            BewerberTestConfigurationData testData = iterator.next();
            if (testData.getEndpointUrl().equals(endpointUrl) && testData.getCompanyEid().equals(companyEid) && testData.getPositionId().equals(positionId)) {
                LOGGER.info("Removing bewerber configuration {}", testData);
                iterator.remove();
                persistConfiguration(configBewFilePath);
                return testData;
            }
        }
        LOGGER.info("Unable to remove configuration for {}, {}, {}. Configuration not found", endpointUrl, companyEid, positionId);
        return null;
    }

    protected BewerberTestConfigurationData resolveAndAddConfigurationData(Gson gsonBuilder, JsonElement jsonElement) {
        final BewerberTestConfigurationData data = gsonBuilder.fromJson(jsonElement, BewerberTestConfigurationData.class);
        if (data != null)
            configurations.add(data);
        return data;
    }

    // todo delete


}
