package haitran.example.mail.service;

import haitran.example.mail.domain.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


@Service
public class MailService {
    private JavaMailSender mailSender;

    @Autowired
    public MailService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean prepareAndSend(Mail mail) {
        MimeMessagePreparator messagePreparator = createEmail(mail.getRecipient(), mail.getMessage());
        System.out.println("send email \n");
        try{
            mailSender.send(messagePreparator);
        } catch (MailException e)
        {
            System.out.println("Mail Exception: False to send !\n");
            return false;
            // runtime exception; compiler will not force you to handle it
        }
        return true;
    }

    static MimeMessagePreparator createEmail(String recipient, String message) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("sample@haitran.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject("Sample mail subject");
            messageHelper.setText(message);
        };
    }
}
