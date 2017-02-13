package org.ost.edge.onestong.tools.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailHelper extends Thread {
	public static final String MAIL_SERVER_HOST = "99nn208.mc2.cn";
	public static final String MAIL_SERVER_PORT = "587";
	public static final boolean MAIL_VALIDATE = true;
	public static final String MAIL_USERNAME = "noreply_onestong@mc2.cn";
	public static final String MAIL_PASSWORD = "Noreply2014WH";
	public static final String MAIL_FROM_ADDRESS = "noreply_onestong@mc2.cn";
	public static final String MAIL_SUBJECT = "OneSTong易事通邀请注册通知（此为系统邮件，请勿回复!）";
	
	private MailSenderInfo mailInfo;

	/**
	 * @return the _mailInfo
	 */
	public MailSenderInfo getMailInfo() {
		return mailInfo;
	}

	/**
	 * @param _mailInfo
	 *            the _mailInfo to set
	 */
	public void setMailInfo(MailSenderInfo _mailInfo) {
		this.mailInfo = _mailInfo;
	}

	public boolean sendTextMail(MailSenderInfo mailInfo) {
		EmailAutherticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			authenticator = new EmailAutherticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setSentDate(new Date());
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean sendHtmlMail(MailSenderInfo mailInfo) {
		EmailAutherticator authenticator = null;
		Properties pro = mailInfo.getProperties();

		if (mailInfo.isValidate()) {
			authenticator = new EmailAutherticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}

		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setSentDate(new Date());
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			mailMessage.setContent(mainPart);
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public String GetSendMsg(String userName, String telString,String password) {
		StringBuilder msgBuilder = new StringBuilder();
		if (mailInfo != null) {
			msgBuilder.append(userName + ",<br> 您已被授权使用OneSTong易事通，请尽快完成下载和登录。以下是您的账户初始信息：");
			msgBuilder.append("<br>");
			msgBuilder.append("<br>");
			// msgBuilder.append(System.getProperty("line.separator"));
			msgBuilder.append("你的登录账号是：" + mailInfo.getToAddress());
			msgBuilder.append("<br>");
			msgBuilder.append("你的登录密码是:"+password);
			msgBuilder.append("<br>");
			msgBuilder.append("你的注册姓名是:" + userName);
			msgBuilder.append("<br>");
			msgBuilder.append("你的注册电话是:" + telString);
			msgBuilder.append("<br>");
			msgBuilder.append("<a href='http://mc2.onestong.net'><p>点击下载易事通应用</p></a>");
		}

		return msgBuilder.toString();
	}
	public String GetSendMsg(String domainUser, String accountName,String accountPassword,Integer sum,String dateStart,String dateEnd) {
		StringBuilder msgBuilder = new StringBuilder();
		if (mailInfo != null) {
			msgBuilder.append(domainUser + ",<br> 您已被授权使用OneSTong易事通考勤系统。以下是您的账户初始信息：");
			msgBuilder.append("<br>");
			msgBuilder.append("<br>");
			// msgBuilder.append(System.getProperty("line.separator"));
			msgBuilder.append("你的登录账号是：" + accountName);
			msgBuilder.append("<br>");
			msgBuilder.append("你的登录密码是:").append(accountPassword);
			msgBuilder.append("<br>");
			msgBuilder.append("购买激活码数量: " + sum).append("个");
			msgBuilder.append("<br>");
			msgBuilder.append("有效期开始:" + dateStart);
			msgBuilder.append("<br>");
			msgBuilder.append("有效期结束:" + dateEnd);
			msgBuilder.append("<br>");
			msgBuilder.append("<a href='http://app.onestong.com'><p>点击下载易事通应用</p></a>");
		}

		return msgBuilder.toString();
	}

	@Override
	public void run() {
		super.run();
		if (mailInfo != null)
			sendHtmlMail(mailInfo);
	}
	
	public static void sendMail(String mailTo, String userName, String telString ,String password) {
	     MailSenderInfo mailInfo = new MailSenderInfo();
	     mailInfo.setMailServerHost(MAIL_SERVER_HOST);
	     mailInfo.setMailServerPort(MAIL_SERVER_PORT);
	     mailInfo.setValidate(MAIL_VALIDATE);
	     mailInfo.setUserName(MAIL_USERNAME);
	     mailInfo.setPassword(MAIL_PASSWORD);
	     mailInfo.setFromAddress(MAIL_FROM_ADDRESS);
	     mailInfo.setSubject(MAIL_SUBJECT);
	     
	     mailInfo.setToAddress(mailTo);
	     MailHelper sms = new MailHelper();
	     sms.setMailInfo(mailInfo);
	     String contentString=sms.GetSendMsg(userName, telString,password);
	     mailInfo.setContent(contentString);
	     //sms.sendTextMail(mailInfo);
	     sms.start();
	}
}