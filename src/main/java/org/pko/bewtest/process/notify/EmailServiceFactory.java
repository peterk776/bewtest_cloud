package org.pko.bewtest.process.notify;

import com.google.common.base.Strings;
import org.pko.bewtest.configuration.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * EmailServiceFactory
 *
 * @author Peter Kolarik
 * @date 15.2.2022
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EmailServiceFactory {

    @Autowired
    SendGrid sendGrid;
    @Autowired
    SendSmtp sendSmtp;
    @Autowired
    AppConfiguration appConfiguration;

    public EmailService getEmailService() {
        return (Strings.isNullOrEmpty(appConfiguration.getEak())) ? sendSmtp : sendGrid;
    }
}
