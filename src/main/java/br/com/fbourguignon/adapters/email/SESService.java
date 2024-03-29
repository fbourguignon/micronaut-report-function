package br.com.fbourguignon.adapters.email;

import br.com.fbourguignon.core.ports.EmailSender;
import io.micronaut.context.annotation.Property;
import io.micronaut.email.Attachment;
import io.micronaut.email.BodyType;
import io.micronaut.email.Email;
import io.micronaut.email.ses.SesEmailSender;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class SESService implements EmailSender {

    @Property(name = "email.sender.from")
    private String emailFrom;

    private final SesEmailSender sesEmailSender;

    @Inject
    public SESService(SesEmailSender sesEmailSender) {
        this.sesEmailSender = sesEmailSender;
    }

    @Override
    public void sendEmail(String emailTo, String subject, String body, byte[] attachment, String filename) {
        Attachment report = Attachment
                .builder()
                .filename(filename)
                .content(attachment)
                .contentType("application/pdf")
                .build();

        Email email = Email.builder()
                .from(emailFrom)
                .subject(subject)
                .to(emailTo)
                .body(body, BodyType.HTML)
                .attachment(report)
                .build();

        sesEmailSender.send(email);
    }
}
