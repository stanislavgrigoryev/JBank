package com.jbank.authservice.service;

import com.jbank.authservice.properties.EmailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor()
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Integer LENGTH = 6;
    private static final Random RANDOM = new SecureRandom();


    public void sendVerificationEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

        messageHelper.setFrom(emailProperties.getSupport());
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(text, true);
        mailSender.send(message);
    }

    public static String generateVerificationCode() {
        StringBuilder length = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            length.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return length.toString();
    }
}
