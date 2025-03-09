package com.ithe.huabaiplayer.common.service;

import com.ithe.huabaiplayer.common.ErrorCode;
import com.ithe.huabaiplayer.common.exception.BusinessException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @ClassName MailService
 * @Author hua bai
 * @Date 2025/2/12 10:12
 **/
@Service
@Slf4j
public class MailService {
    /**
     * 注入邮件工具类
     */
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    private void checkMail(String to, String subject, String text) {
        if (StringUtils.isEmpty(to)) {
            throw new RuntimeException("邮件收信人不能为空");
        }
        if (StringUtils.isEmpty(subject)) {
            throw new RuntimeException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(text)) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }

    public void sendTextMailMessage(String to, String subject, String text) {
        try {
            //true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            //邮件发信人
            mimeMessageHelper.setFrom(sendMailer);
            //邮件收信人  1或多个
            mimeMessageHelper.setTo(to.split(","));
            //邮件主题
            mimeMessageHelper.setSubject(subject);
            //邮件内容
            mimeMessageHelper.setText(text);
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());
            //发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            log.info("发送邮件成功");
        } catch (MessagingException e) {
            log.error("发送邮件失败", e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "发送邮件失败");
        }
    }

    // 发送HTML邮件

    /**
     * @param to      收件人
     * @param subject 主题
     * @param text    替换了为${code}的验证码
     */
    public void sendHtmlMailMessage(String to, String subject, String text) {
        checkMail(to, subject, text);
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 发件人邮箱
            helper.setFrom(sendMailer);
            // 收件人邮箱
            helper.setTo(to);
            // 邮件主题
            helper.setSubject(subject);
            helper.setText(text, true);
            // 发送邮件
            javaMailSender.send(message);
            log.info("验证码已发送至 {}", to);
        } catch (MessagingException e) {
            log.error("发送邮件时出错: {}", e.getMessage());
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "发送邮件失败");
        }
    }

    public void sendVerificationEmail(String toEmail, String verificationCode) {

    }


}
