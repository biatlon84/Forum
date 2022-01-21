package com.biathlon84.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

@Controller
public class SendMailController {
    @Autowired
    private JavaMailSender javaMailSender;
    //SpringTemplateEngine templateEngine =  new SpringTemplateEngine();
    @Autowired
    SpringTemplateEngine templateEngine;
    @GetMapping("/mailsend")
    public String sender2(Model model) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
//        helper.setTo("biathlon44@yahoo.com");
//        helper.setFrom("modulyatop@yandex.ru","personal");
        helper.setTo("kwas@q.com");
        helper.setFrom("kwas@q.com","personal");
        helper.setSubject("subject");
        helper.setText("<h1>text</h1>",true);
        javaMailSender.send(message);


        final Context ctx = new Context();
        ctx.setVariable("name", "recipientName");
        ctx.setVariable("actUsername","kwas from java");
        ctx.setVariable("actId","ididid from java");
        final String htmlContent = this.templateEngine.process("messages/activation_message.html", ctx);


        System.out.println(htmlContent);
        return "sendermail";
    }

}
