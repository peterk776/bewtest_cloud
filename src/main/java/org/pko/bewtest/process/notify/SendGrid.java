package org.pko.bewtest.process.notify;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.pko.bewtest.configuration.AppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * EmailService
 *
 * @author Peter Kolarik
 * @date 14.2.2022
 */

@Component
public class SendGrid implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendGrid.class);

    @Autowired
    AppConfiguration appConfiguration;

    @Override
    public void sendEmail(MailContext mailContext) {
        String toAddress = mailContext.getSendTo();
        try {
            Email from = new Email(appConfiguration.getEmailSender());
            Email to = new Email(toAddress);
            Content content = new Content("text/plain", mailContext.getContent());
            Mail mail = new Mail(from, mailContext.getSubject(), to, content);

            com.sendgrid.SendGrid sg = new com.sendgrid.SendGrid(appConfiguration.getEak());
            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            LOGGER.info("Notification email sent to {}, with status code {}", toAddress, response.getStatusCode());
        } catch (IOException ex) {
            LOGGER.warn("Unable to send notification email to {}", toAddress, ex);
        }
    }
}
