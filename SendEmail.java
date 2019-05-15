package com.qhit.utils;


//import com.sun.mail.util.MailSSLSocketFactory;

import com.sun.mail.util.MailSSLSocketFactory;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * Created by 16682 on 2019/3/26.
 */
public class SendEmail {

    public static void main(String[] args) throws GeneralSecurityException {
        emailUtil("1013660270@qq.com");
    }



    public static Map<String, Object> emailUtil(String Addressee) throws GeneralSecurityException {
        boolean flag = true;
        Map<String,Object> map = new HashMap();
        String  yanzhengma="";
        // 发件人电子邮箱
        String from = Addressee;
        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            @Override
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("1013660270@qq.com", "totcuivyxbhubdjc"); //发件人邮件用户名、密码
            }
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(Addressee));

            // Set Subject: 头部头字段
            message.setSubject("你的邮箱请注意查收！");

            //  生成一个六位数验证码格式是由数字大小写字母组成
            String [] arr = {"0", "1", "2", "3","4" ,"5" , "6", "7", "8","9" ,
                    "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            for(int i =0;i<6;i++){
                // 生成一个数组长度-1随机数
                int j = (int) Math.round(Math.random()*(arr.length-1));
                yanzhengma += arr[j];
            }
            // 设置消息体
            message.setText("主人邮箱有新消息，您的验证码是:"+yanzhengma);

            // 发送消息
            Transport.send(message);
            System.out.println("邮件已经发送！！！！");

        }catch (MessagingException mex) {
            mex.printStackTrace();
            flag = false;
        }finally {
            map.put("flag",flag);
            map.put("yanzhengma",yanzhengma);
            return map;
        }

  }

}
