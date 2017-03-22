package com.oz.onestong.tools.mail;


public class TestMail {
	public static void main(String[] args) {
		 MailSenderInfo mailInfo = new MailSenderInfo();
	     mailInfo.setMailServerHost("mx.mc2.cn");
	     mailInfo.setMailServerPort("587");
	     mailInfo.setValidate(true);
	     mailInfo.setUserName("noreply_onestong@mc2.cn");
	     mailInfo.setPassword("Noreply2014WH");
	     mailInfo.setFromAddress("noreply_onestong@mc2.cn");
	     mailInfo.setToAddress("1462927100@qq.com");
	     mailInfo.setSubject("onestong系统管理员");

	    
	     MailHelper sms = new MailHelper();
	     sms.setMailInfo(mailInfo);
	     String contentString=sms.GetSendMsg("武汉xx动力公司", "whxx_sdonli", "123456_sfsfs", 1000, "2015/12/01", "2017/12/01");
	     mailInfo.setContent(contentString);
	     //sms.sendTextMail(mailInfo);
	     sms.start();

	}
}
