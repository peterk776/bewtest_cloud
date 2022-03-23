package com.piag.uitests.process;

import com.piag.uitests.configuration.TestConfigurationData;
import com.piag.uitests.data.BewerberTestResult;
import com.piag.uitests.data.TestResult;
import com.piag.uitests.data.TestResultResponse;
import com.piag.uitests.process.notify.EmailService;
import com.piag.uitests.process.notify.EmailServiceFactory;
import com.piag.uitests.process.notify.MailContext;
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

    public <T extends TestConfigurationData>  void process(TestResultResponse<T> result) {

        result.getResults().stream()
                .filter(testResult -> TestResult.State.OK != testResult.getState())
                .forEach(this::sendEmail);
    }

    private <T extends TestConfigurationData> void sendEmail(TestResult<T> testResult) {
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
