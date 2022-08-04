package com.aura.qamm.util;

import com.aura.qamm.dto.Email;
import com.aura.qamm.model.MailServer;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public interface EmailSender {
    public static Boolean sendEmail(Email email, MailServer mailServer) {
        Boolean result = false;

        if (email != null) {
            if (email.getBody().isEmpty() || email.getTo().isEmpty() ||
                    email.getFrom().isEmpty() || email.getSubject().isEmpty()) {
                return false;
            }
        }

        String to = email.getTo();
        String from = email.getFrom();
        String host = mailServer.getHost();

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email.getFrom()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            System.out.println("Assembled successfully....");

            Transport transport = session.getTransport("smtp");
            //transport.connect(host, 25, "root", "SparkTakos69");
            transport.connect();
            System.out.println("Connected successfully....");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            //System.out.println("Closed successfully....");

            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

        return true;
    }
}
