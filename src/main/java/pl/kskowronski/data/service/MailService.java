package pl.kskowronski.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendMail(String to,
                         String cc,
                         String subject,
                         String text,
                         boolean isHtmlContent) throws MessagingException {

//        String additionalText = "<br><br>Poniżej instrukcje: <br>" +
//                "1. <a href=\"https://i.naprzod.pl/inaprzod/webinaria/instrukcje%20IT/01%20aktualizacja%20dokumentow%20pracownika.pdf\">Aktualizacja dokumentów pracownika. </a>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("obcokrajowcy@rekeep.pl");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setCc(cc);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }

    public void sendMailTest(
                         String subject,
                         String text,
                         boolean isHtmlContent) throws MessagingException {

//        String additionalText = "<br><br>Poniżej instrukcje: <br>" +
//                "1. <a href=\"https://i.naprzod.pl/inaprzod/webinaria/instrukcje%20IT/01%20aktualizacja%20dokumentow%20pracownika.pdf\">Aktualizacja dokumentów pracownika. </a>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("obcokrajowcy@rekeep.pl");
        mimeMessageHelper.setTo("klaudiusz.skowronski@rekeep.pl");
        mimeMessageHelper.setCc("claude-plos@o2.pl");
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }
}
