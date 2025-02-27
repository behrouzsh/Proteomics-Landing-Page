package eh3.uc.edu.service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.json.simple.JSONObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//import org.json.JSONObject;

/**
 * Created by behrouz on 07/05/17.
 */

@Service
public class EmailService {
    public static void sendEmail(String args) throws MessagingException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
        mailMsg.setFrom("postdoc.ucincinnati@gmail.com");
        mailMsg.setTo("behrouz.shamsaei@uc.edu");
        mailMsg.setSubject("LINCS Proteomics Contact Email");
        mailMsg.setText(args);
        mailSender.send(mimeMessage);
        System.out.println("---Done---");
    }
}

