package com.piag.uitests.process.notify;

/**
 * MailContext
 *
 * @author Peter Kolarik
 * @date 15.2.2022
 */
public class MailContext {

    private final String sendTo;
    private final String subject;
    private final String content;

    public MailContext(String sendTo, String subject, String content) {
        this.sendTo = sendTo;
        this.subject = subject;
        this.content = content;
    }

    public String getSendTo() {
        return sendTo;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
