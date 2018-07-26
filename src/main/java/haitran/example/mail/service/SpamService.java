package haitran.example.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SpamService {
    private JavaMailSender mailSender;
    private Thread spamThread;

    @Autowired
    public SpamService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void spam(String recipient, String message) {
        MimeMessagePreparator messagePreparator = MailService.createEmail(recipient, message);
        spamThread = new Thread(() -> {
            while (true) {
                try {
                    mailSender.send(messagePreparator);
                    System.out.println("Please wait 10 seconds for next email.\n");
                    Thread.sleep(10000);
                } catch (MailException e) {
                    System.out.println("Mail Exception: False to send !\n");
                    // runtime exception; compiler will not force you to handle it
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }
        });
        spamThread.start();
    }

    public void cancelSpam() {
        if(spamThread != null) spamThread.interrupt();
    }
}
