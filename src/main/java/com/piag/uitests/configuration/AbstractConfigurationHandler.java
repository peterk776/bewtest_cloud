package com.piag.uitests.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConfigurationHandler
 *
 * @author Peter Kolarik
 * @date 1.2.2022
 */

public abstract class AbstractConfigurationHandler<T extends TestConfigurationData> implements ConfigurationHandler<T> {

    /**
     * Path to json file
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConfigurationHandler.class);

    protected void readConfiguration(String configFilePath)  {
        LOGGER.info("Reading test configurations for {} ...", configFilePath);
        try {
            if (Strings.isNullOrEmpty(configFilePath)) {
                LOGGER.warn("There is no file path for tests configured!");
                return;
            }

            final Path path = Paths.get(configFilePath);
            if (Files.exists(path) && Files.isReadable(path)) {
                final Gson gsonBuilder = new GsonBuilder().create();
                JsonArray array = gsonBuilder.fromJson(Files.newBufferedReader(path), JsonArray.class);
                if (array == null) {
                    LOGGER.warn("Invalid configuration in {}", configFilePath);
                    return;
                }
                for (JsonElement jsonElement : array) {
                    resolveAndAddConfigurationData(gsonBuilder, jsonElement);
                }
            } else {
                LOGGER.warn("File in path {} doesn't exists or no rights to access!", configFilePath);
            }
        } catch (IOException e) {
            LOGGER.warn("Unable to read test configurations", e);
        } finally {
            LOGGER.debug("Reading test configurations for {} ends with total configurations {}", configFilePath, getConfigurations().size());
        }
    }

    protected T addPersistConfiguration(String configFilePath, String json)  {
        LOGGER.info("Adding test configurations for {} ...", configFilePath);
        try {
            if (Strings.isNullOrEmpty(configFilePath)) {
                LOGGER.warn("There is no file path for tests configured!");
                return null;
            }

            if (Strings.isNullOrEmpty(json)) {
                LOGGER.warn("Invalid input json configuration!");
                return null;
            }

            final Path path = Paths.get(configFilePath);
            final Gson gsonBuilder = new GsonBuilder().create();
            JsonArray array = gsonBuilder.fromJson(Files.newBufferedReader(path), JsonArray.class);
            if (array == null) {
                LOGGER.warn("Invalid json configuration. Unable to persist");
                LOGGER.debug("Invalid json {}", json);
                return null;
            }
            // add elements to config collection

            T t = resolveAndAddConfigurationData(gsonBuilder, gsonBuilder.fromJson(json, JsonElement.class));
            if (t == null) {
                LOGGER.warn("Unable to resolve configuration from json.");
                return null;
            }

            JsonArray newArray = configToJson(gsonBuilder);

            Files.write(path, gsonBuilder.toJson(newArray).getBytes(StandardCharsets.UTF_8));
            LOGGER.info("Test configurations {} saved in {}", t, configFilePath);
            return t;
        } catch (IOException e) {
            LOGGER.warn("Unable to write test configurations", e);
            return null;
        } finally {
            LOGGER.debug("Persisting test configurations to {} ends.", configFilePath);
        }
    }

    protected void persistConfiguration(String configFilePath)  {
        LOGGER.info("Writing test configurations for {} ...", configFilePath);
        try {
            if (Strings.isNullOrEmpty(configFilePath)) {
                LOGGER.warn("There is no file path for tests configured!");
                return;
            }

            final Path path = Paths.get(configFilePath);
            final Gson gsonBuilder = new GsonBuilder().create();
            JsonArray newArray = configToJson(gsonBuilder);
            Files.write(path, gsonBuilder.toJson(newArray).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOGGER.warn("Unable to write test configurations", e);
            return;
        } finally {
            LOGGER.debug("Persisting test configurations to {} ends.", configFilePath);
        }
    }

    private JsonArray configToJson(Gson gsonBuilder) {
        JsonArray newArray = new JsonArray(100);
        // iterate through all and save into new json
        getConfigurations().stream()
                .map(gsonBuilder::toJsonTree)
                .forEach(newArray::add);
        return newArray;
    }

    protected abstract void init();

    protected abstract T resolveAndAddConfigurationData(Gson gsonBuilder, JsonElement jsonElement);
}
