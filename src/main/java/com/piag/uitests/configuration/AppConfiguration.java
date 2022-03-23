package com.piag.uitests.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * AppConfiguration
 *
 * @author Peter Kolarik
 * @date 14.2.2022
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AppConfiguration {

    @Value("${configuration.app.file.path}")
    private String configAppFilePath;

    private final Properties props = new Properties();

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);

    @PostConstruct
    private void init() {
        if (configAppFilePath != null && Files.exists(Paths.get(configAppFilePath)) && Files.isReadable(Paths.get(configAppFilePath))) {
            try (FileInputStream fis = new FileInputStream(configAppFilePath)) {
                props.load(fis);
            } catch (FileNotFoundException e) {
                LOGGER.warn("App configuration file {} not found or not accessible.", configAppFilePath);
            } catch (IOException e) {
                LOGGER.warn("Unable to read app configuration file {}.", configAppFilePath, e);
            }
        }
    }

    public String getEak() {
        return props.getProperty("email.apikey");
    }

    public String getEmailSender() {
        return props.getProperty("email.sender");
    }

    public Properties getSmtpEmailProperties() {
        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.auth", props.get("mail.smtp.auth"));
        mailProps.put("mail.smtp.starttls.enable", props.get("mail.smtp.starttls.enable"));
        mailProps.put("mail.smtp.host", props.get("mail.smtp.host"));
        mailProps.put("mail.smtp.port", props.get("mail.smtp.port"));
        mailProps.put("mail.smtp.ssl.trust", props.get("mail.smtp.ssl.trust"));
        return mailProps;
    }
}
