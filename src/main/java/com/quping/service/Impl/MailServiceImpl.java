package com.quping.service.Impl;

import com.quping.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;

/**
 * @description: 发送邮件服务
 * @author: Punny
 * @date: 2024/10/31 10:04
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendTo(String addr, String msg) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
            helper.setFrom("2472207427@qq.com","quping");
            helper.setTo(addr);
            helper.setSubject("【趣评】验证码");
            helper.setText(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);
    }
}
