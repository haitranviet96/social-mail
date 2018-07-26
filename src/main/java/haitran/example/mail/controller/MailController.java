package haitran.example.mail.controller;

import haitran.example.mail.domain.Mail;
import haitran.example.mail.service.MailService;
import haitran.example.mail.service.SpamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class implements a REST API for our Multiplication application.
 */
@RestController
@RequestMapping("/mail")
final class MailController {

    @Autowired
    private final MailService mailService;

    @Autowired
    private final SpamService spamService;

    @Autowired
    public MailController(final MailService mailService, final SpamService spamService) {
        this.mailService = mailService;
        this.spamService = spamService;
    }

    @PostMapping("/send")
    boolean sendEmail(@RequestBody Mail mail) {
        return mailService.prepareAndSend(mail);
    }

    @PostMapping("/spam")
    boolean spamEmail(@RequestBody Mail mail) {
        spamService.spam(mail.getRecipient(), mail.getMessage());
        return true;
    }

    @GetMapping("/cancel")
    void cancelSpamEmail() {
        spamService.cancelSpam();
    }
}