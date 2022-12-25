package jmaster.io.project2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Async // tách luồng, chạy riêng, ko đợi luồng trước chạy xong
    public void sendBirthdate(String to, String name) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    StandardCharsets.UTF_8.name());

            //load template email with content
            Context context = new Context();
            context.setVariable("name", name);
            String body = templateEngine.process("email/brithdate.html", context);

            //send mail
            helper.setTo(to); // email gui toi
            helper.setText(body, true); // noi dung mail
            helper.setSubject("HPBD"); // tieu de mail
            helper.setFrom("yourgmail@gmail.com");

            javaMailSender.send(message);
        } catch (MessagingException e) {

            log.error("Email sent with error: " + e.getMessage());

        }
    }

    public void sendTest() {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    StandardCharsets.UTF_8.name());

            //load template email with content
            Context context = new Context();
            context.setVariable("name", "noi dung truyen vao");
            String body = templateEngine.process("email/guimail.html", context);

            //send mail
            helper.setTo("vvancuong21@gamil.com"); // email gui toi
            helper.setText(body, true); // noi dung mail
            helper.setSubject("Test email from spring boot");
            helper.setFrom("yourgmail@gmail.com");

            javaMailSender.send(message);
        } catch (MessagingException e) {

            log.error("Email sent with error: " + e.getMessage());

        }
    }
}
