package com.biathlon84.forum.user.email;

import com.biathlon84.forum.model.entity.EmailMessage;
import com.biathlon84.forum.user.UserServiceDAO;
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
import java.io.UnsupportedEncodingException;


@Service
@Slf4j
public class SenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Autowired
    UserServiceDAO userService;

    @Async
    public String sendEmail(EmailMessage sqlMessage,String name, boolean real) throws MessagingException, UnsupportedEncodingException {
        String textHtml = "";
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//        helper.setTo("biathlon44@yahoo.com");
//        helper.setFrom("modulyatop@yandex.ru","personal");
//        helper.setSubject("subject");
//        helper.setText("<h1>text</h1>",true);
//        javaMailSender.send(message);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true); //multipart 'true'

        try {
             textHtml = tryParseAndSendEmail(sqlMessage,helper,name);
             if (real) {
                 javaMailSender.send(mimeMessage);
             }
        } catch (Exception e) {
            handleException(e);
        }
        return textHtml;
    }

    public String tryParseAndSendEmail(EmailMessage sqlMessage, MimeMessageHelper helper,String name) throws MessagingException, UnsupportedEncodingException {
        helper.setFrom(sqlMessage.getSubject(),sqlMessage.getSubject());
        helper.setTo(sqlMessage.getRecipient());
        helper.setSubject(sqlMessage.getSubject());
        String textHtml = PrepareHtml(sqlMessage, name);
        helper.setText(textHtml,true);
        return textHtml;
    }
    public String PrepareHtml(EmailMessage sqlMessage, String name){
        final Context ctx = new Context();
        ctx.setVariable("actUsername",name);
        ctx.setVariable("actId",sqlMessage.getContent());
        final String htmlContent = this.templateEngine.process("messages/activation_message.html", ctx);
        return htmlContent;
    }

    private void handleException(Exception e) {
        log.error("Mail Send Exception - smtp service unavailable");
        e.printStackTrace();
        throw new RuntimeException();
    }
}
