package com.suny.utils;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;


/**
 * Created by 孙建荣 on 17-9-8.上午9:33
 */
@Service
public class MailSender implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl mailSender;

    private final VelocityEngine velocityEngine;

    private final MailSetting mailSetting;

    @Autowired
    public MailSender(VelocityEngine velocityEngine, MailSetting mailSetting) {
        this.velocityEngine = velocityEngine;
        this.mailSetting = mailSetting;
    }

    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String, Object> model) {
        try {
            String nick = MimeUtility.encodeText("问答邮件通知");
            InternetAddress from = new InternetAddress(nick + "<mm1380013800@vip.qq.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(result, true);
            mailSender.send(mimeMessage);
            return true;
        } catch (UnsupportedEncodingException e) {
            logger.error("发送邮件失败", e.getMessage());
            return false;
        } catch (javax.mail.MessagingException e) {
            logger.error("消息发送异常", e.getMessage());
            return false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(mailSetting.getUsername());
        mailSender.setPassword(mailSetting.getPassword());
        mailSender.setHost(mailSetting.getHost());
        mailSender.setPort(mailSetting.getPort());
        mailSender.setProtocol(mailSetting.getProtocol());
        mailSender.setDefaultEncoding(mailSetting.getDefaultEncoding());
        Session session = Session.getDefaultInstance(mailSender.getJavaMailProperties(), new Authenticator() {
            // 身份认证
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSender.getUsername(), mailSender.getPassword());
            }
        });
        mailSender.setSession(session);
        Properties javaProperties = new Properties();
//        javaProperties.put("mail.smtp.ssl.enable", true);
        javaProperties.put("mail.stmp.auth",true);
        javaProperties.put("mail.stmp.starttls.enable",true);

        mailSender.setJavaMailProperties(javaProperties);
    }


}





















