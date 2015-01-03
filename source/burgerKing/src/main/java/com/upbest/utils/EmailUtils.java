package com.upbest.utils;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailUtils {
	private JavaMailSenderImpl mailSender;
	
	public EmailUtils(JavaMailSenderImpl mailSender){
		this.mailSender = mailSender;
	}
	
	/**
	 * 只发送带有附件的邮件
	 * @throws Exception 
	 */
	public void sendEmailWithAttachmentOnly(String toUser,String subject,File attachment) throws Exception{
        // 建立邮件讯息
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
        
        // 设定收件人、寄件人、主题与内文
        messageHelper.setTo(toUser);
        messageHelper.setFrom(mailSender.getUsername());
        messageHelper.setSubject(subject);
        messageHelper.setText("");
        messageHelper.addAttachment(attachment.getName(), attachment);
        
        
        // 传送邮件
        mailSender.send(mimeMessage);
	}
	
	/**
	 * 只发送带有附件的邮件
	 * @throws Exception 
	 */
	public void sendEmailWithAttachmentOnly(String toUser,String subject,String attachmentFileName,InputStreamSource attachment) throws Exception{
		sendEmailWithAttachment(toUser, subject, "", attachmentFileName, attachment);
	}

	public void sendEmailWithAttachmentOnly(String[] eamils, String subject,
			String attachmentFileName, ByteArrayResource resource) throws Exception {
		if(eamils != null){
			for (String email : eamils) {
				if(!StringUtils.isEmpty(email) && email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")){
					sendEmailWithAttachmentOnly(email, subject,attachmentFileName, resource);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param eamils 
	 * @param subject 主题
	 * @param text	正文
	 * @param attachmentFileName 附件名称
	 * @param resource		附件资源
	 * @throws Exception
	 */
	public void sendEmailWithAttachment(String[] eamils, String subject,String text,
			String attachmentFileName, ByteArrayResource resource) throws Exception {
		if(eamils != null){
			for (String email : eamils) {
				if(!StringUtils.isEmpty(email) && email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")){
					sendEmailWithAttachment(email, subject,text,attachmentFileName, resource);
				}
			}
		}
	}

	public void sendEmailWithAttachment(String email, String subject,
			String text, String attachmentFileName, InputStreamSource resource) throws Exception {
		// 建立邮件讯息
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
        
        // 设定收件人、寄件人、主题与内文
        messageHelper.setTo(email);
        messageHelper.setFrom(mailSender.getUsername());
        messageHelper.setSubject(subject);
        messageHelper.setText(text);
        messageHelper.addAttachment(attachmentFileName, resource);
        
        // 传送邮件
        mailSender.send(mimeMessage);
	}
}
