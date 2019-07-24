package com.qf.listener;

import com.qf.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/19 19:36
 */
@Component
public class MyrabbitHandler {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @RabbitListener(queues = "email_queue")
    public void handle(Email email){

        executorService.submit(() -> {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            try {
                messageHelper.setSubject(email.getSubject());
                messageHelper.setFrom(fromEmail);
                messageHelper.setTo(email.getTo());
                messageHelper.setText(email.getContent());
                messageHelper.setSentDate(new Date());

                javaMailSender.send(mimeMessage);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

    }
}
