package com.piag.uitests.process.notify;

import com.piag.uitests.configuration.AppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * SendSmtp
 *
 * @author Peter Kolarik
 * @date 15.2.2022
 */

@Component
public class SendSmtp implements EmailService{

    @Autowired
    AppConfiguration appConfiguration;

    private static final Logger LOGGER = LoggerFactory.getLogger(SendSmtp.class);

    @Override
    public void sendEmail(MailContext context) {
        try {
            Session session = Session.getInstance(appConfiguration.getSmtpEmailProperties());
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(appConfiguration.getEmailSender()));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(context.getSendTo()));
            message.setSubject(context.getSubject());
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(context.getContent(), "text/plain; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (Exception e) {
            LOGGER.warn("Unable to send email with smtp", e);
        }
    }
}
