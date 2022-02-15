package org.pko.bewtest.process.notify;

/**
 * EmailService
 *
 * @author Peter Kolarik
 * @date 15.2.2022
 */
public interface EmailService {
    void sendEmail(MailContext context);
}
