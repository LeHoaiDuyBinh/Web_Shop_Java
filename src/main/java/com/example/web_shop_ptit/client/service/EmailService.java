package com.example.web_shop_ptit.client.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

public class EmailService {
    public static String randomVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public static void sendVerificationCode(String recipientEmail, String verificationCode) {
        // Thông tin tài khoản email của bạn
        final String username = "ptithcmnckh@gmail.com";
        final String password = "rzdjfijivpqmknje";
        //rzdj fiji vpqm knje

        // Cấu hình properties cho việc gửi email
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tạo một phiên gửi email
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo một đối tượng MimeMessage
            Message message = new MimeMessage(session);

            // Thiết lập người gửi
            message.setFrom(new InternetAddress(username));

            // Thiết lập người nhận
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Thiết lập chủ đề
            message.setSubject("Xác minh tài khoản");

            // Thiết lập nội dung email
            message.setText("Mã xác minh của bạn là: " + verificationCode);

            // Gửi email
            Transport.send(message);

            System.out.println("Đã gửi email thành công!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}