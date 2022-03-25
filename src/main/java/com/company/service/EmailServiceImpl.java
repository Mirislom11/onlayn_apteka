package com.company.service;

import com.company.entity.EmailEntity;
import com.company.enums.VerificationRole;
import com.company.repository.EmailRepository;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail (EmailEntity email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(email.getContent());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setTo(email.getToAccount());
        mailSender.send(simpleMailMessage);
    }

    public EmailEntity createEmail (String toAccount, String title, String content) {
        EmailEntity email = new EmailEntity();
        email.setContent(content);
        email.setSubject(title);
        email.setToAccount(toAccount);
        email.setFromAccount("mirislomzoirov2003@gmail.com");
        emailRepository.save(email);
        return email;
    }
    public StringBuilder createBody(Integer id, VerificationRole verificationRole) {
        String responseId = JwtUtil.createJwt(id);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello it is apteka.uz\n");
        stringBuilder.append("Please verify your account\n");
        stringBuilder.append("http://192.168.43.4:8080/api/auth/verification/"+verificationRole+"/" + responseId);
        return stringBuilder;
    }
}
