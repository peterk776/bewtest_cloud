package org.pko.bewtest.process;

import org.pko.bewtest.configuration.TestConfigurationData;
import org.pko.bewtest.data.BewerberTestResult;
import org.pko.bewtest.data.TestResult;
import org.pko.bewtest.data.TestResultResponse;
import org.pko.bewtest.process.notify.EmailService;
import org.pko.bewtest.process.notify.EmailServiceFactory;
import org.pko.bewtest.process.notify.MailContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TestResultProcessor
 *
 * @author Peter Kolarik
 * @date 11.2.2022
 */
@Component
public final class TestResultProcessor<T extends TestConfigurationData> {

    @Autowired
    EmailServiceFactory emailServiceFactory;

    public void process(TestResultResponse<T> result) {

        result.getResults().stream()
                .filter(testResult -> TestResult.State.OK != testResult.getState())
                .forEach(this::sendEmail);
    }

    private void sendEmail(TestResult<T> testResult) {
        String subject;
        if (testResult instanceof BewerberTestResult) {
            subject = (testResult.getTarget() == null ) ? "Bewerbung test failed notification" : ("Bewerbung test failed notification, test " + testResult.getTarget());
        } else {
            subject = "Test failed notification";
        }

        MailContext mailContext = new MailContext(testResult.getResponsible(), subject, testResult.getMessage());
        EmailService emailService = emailServiceFactory.getEmailService();
        emailService.sendEmail(mailContext);
    }
}
