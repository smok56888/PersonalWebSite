package com.smok.web.utils;

import org.apache.commons.lang.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 * Created by smok on 2015/12/28.
 */
public class EmailUtil {

    /**
     * 发送邮件
     *
     * @param userName    发送帐号用户名
     * @param password    发送帐号密码
     * @param mailto      收件人，多个以半角逗号分隔
     * @param mailcc      抄送，多个以半角逗号分隔
     * @param mailSubject 邮件主题
     * @param mailBody    邮件正文
     * @param attachment  附件，数组形式，支持多个
     * @throws Exception
     */
    public static void SendEmail(String userName, String password, String mailto, String mailcc, String mailSubject, String mailBody, String[] attachment) throws Exception {
        Email.entity(userName, password, mailto, mailcc, mailSubject, mailBody, attachment).send();
    }

    private static class Email {

        private String SMTP_HOST = null;
        private static String needAuth = "true"; // 需要身份验证
        private MimeMessage mimeMsg; // 邮件对象
        private String sendUserName; // 发件人的用户名
        private String sendUserPass; // 发件人密码
        private Session session;
        private Properties props;
        private Multipart mp;// 附件添加的组件
        private List<FileDataSource> files = new LinkedList<FileDataSource>();// 存放附件文件

        private void init() {
            if (props == null) {
                props = System.getProperties();
            }
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.auth", needAuth);
            session = Session.getDefaultInstance(props, null);
            // 置true可以在控制台（console)上看到发送邮件的过程
            // session.setDebug(true);
            // 用session对象来创建并初始化邮件对象
            mimeMsg = new MimeMessage(session);
            // 生成附件组件的实例
            mp = new MimeMultipart();
        }

        private Email(String sendUserName, String sendUserPass, String to, String cc, String mailSubject, String mailBody, String[] attachmentPaths) {
            this.SMTP_HOST = getSmtpHost(sendUserName);
            init();
            this.sendUserName = sendUserName;
            this.sendUserPass = sendUserPass;
            setFrom(sendUserName);
            setTo(to);
            setCC(cc);
            setBody(mailBody);
            setSubject(mailSubject);
            if (attachmentPaths != null && attachmentPaths.length > 0) {
                for (String attachment : attachmentPaths) {
                    addFileAffix(attachment);
                }
            }

        }

        /**
         * 邮件实体
         *
         * @param sendUserName    发件邮件地址
         * @param sendUserPass    发件邮箱密码
         * @param to              收件人，多个邮箱地址以半角逗号分隔
         * @param cc              抄送，多个邮箱地址以半角逗号分隔
         * @param mailSubject     邮件主题
         * @param mailBody        邮件正文
         * @param attachmentPaths 附件路径
         * @return
         */
        public static Email entity(String sendUserName, String sendUserPass, String to, String cc, String mailSubject, String mailBody,
                                   String[] attachmentPaths) {
            return new Email(sendUserName, sendUserPass, to, cc, mailSubject, mailBody, attachmentPaths);
        }

        /**
         * 设置邮件主题
         *
         * @param mailSubject
         * @return
         */
        private boolean setSubject(String mailSubject) {
            try {
                mimeMsg.setSubject(mailSubject);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        /**
         * 设置邮件内容,并设置其为文本格式或HTML文件格式，编码方式为UTF-8
         *
         * @param mailBody
         * @return
         */
        private boolean setBody(String mailBody) {
            try {
                BodyPart bp = new MimeBodyPart();
                bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=UTF-8>" + mailBody, "text/html;charset=UTF-8");
                // 在组件上添加邮件文本
                mp.addBodyPart(bp);
            } catch (Exception e) {
                System.err.println("设置邮件正文时发生错误！" + e);
                return false;
            }
            return true;
        }

        /**
         * 增加发送附件
         *
         * @param filename 邮件附件的地址，只能是本机地址而不能是网络地址，否则抛出异常
         * @return
         */
        public boolean addFileAffix(String filename) {
            try {
                if (StringUtils.isNotEmpty(filename)) {
                    BodyPart bp = new MimeBodyPart();
                    FileDataSource fileds = new FileDataSource(filename);
                    bp.setDataHandler(new DataHandler(fileds));
                    bp.setFileName(MimeUtility.encodeText(fileds.getName(), "utf-8", null)); // 解决附件名称乱码
                    mp.addBodyPart(bp);// 添加附件
                    files.add(fileds);
                }
            } catch (Exception e) {
                System.err.println("增加邮件附件：" + filename + "发生错误！" + e);
                return false;
            }
            return true;
        }

        /**
         * 删除附件
         *
         * @return
         */
        public boolean delFileAffix() {
            try {
                FileDataSource fileds = null;
                for (Iterator<FileDataSource> it = files.iterator(); it.hasNext(); ) {
                    fileds = it.next();
                    if (fileds != null && fileds.getFile() != null) {
                        fileds.getFile().delete();
                    }
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        /**
         * 设置发件人地址
         *
         * @param from 发件人地址
         * @return
         */
        private boolean setFrom(String from) {
            try {
                mimeMsg.setFrom(new InternetAddress(from));
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        /**
         * 设置收件人地址
         *
         * @param to 收件人的地址
         * @return
         */
        private boolean setTo(String to) {
            if (to == null)
                return false;
            try {
                mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        /**
         * 设置抄送
         *
         * @param cc
         * @return
         */
        private boolean setCC(String cc) {
            if (cc == null) {
                return false;
            }
            try {
                mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        /**
         * 发送邮件
         *
         * @return
         */
        public boolean send() throws Exception {
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            System.out.println("正在发送邮件....");
            Transport transport = session.getTransport("smtp");
            // 连接邮件服务器并进行身份验证
            transport.connect(SMTP_HOST, sendUserName, sendUserPass);
            // 发送邮件
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            System.out.println("发送邮件成功！");
            transport.close();
            return true;
        }
    }

    private static String getSmtpHost(String emailAddress) {
        if (StringUtils.isEmpty(emailAddress)) {
            return null;
        }
        String[] splitInfos = emailAddress.split("@");
        if (splitInfos == null || splitInfos.length < 2) {
            return null;
        }
        String suffix = splitInfos[1];
        if (SMTP_HOST_MAP.containsKey(suffix.toLowerCase())) {
            return SMTP_HOST_MAP.get(suffix.toLowerCase());
        }
        return null;
    }

    private static Map<String, String> SMTP_HOST_MAP = new HashMap<String, String>();

    static {
        SMTP_HOST_MAP.put("sina.com", "smtp.sina.com.cn");
        SMTP_HOST_MAP.put("vip.sina.com", "smtp.vip.sina.com.cn");
        SMTP_HOST_MAP.put("sohu.com", "smtp.sohu.com");
        SMTP_HOST_MAP.put("126.com", "smtp.126.com");
        SMTP_HOST_MAP.put("139.com", "smtp.139.com");
        SMTP_HOST_MAP.put("163.com", "smtp.163.com");
        SMTP_HOST_MAP.put("qq.com", "smtp.qq.com");
        SMTP_HOST_MAP.put("exmail.qq.com", "smtp.exmail.qq.com");
        SMTP_HOST_MAP.put("yahoo.com", "smtp.mail.yahoo.com");
        SMTP_HOST_MAP.put("yahoo.com.cn", "smtp.mail.yahoo.com.cn");
        SMTP_HOST_MAP.put("live.com", "smtp.live.com");
        SMTP_HOST_MAP.put("gmail.com", "smtp.gmail.com");
        SMTP_HOST_MAP.put("263.net", "smtp.263.net");
        SMTP_HOST_MAP.put("263.net.cn", "smtp.263.net.cn");
        SMTP_HOST_MAP.put("x263.net", "smtp.x263.net");
        SMTP_HOST_MAP.put("21cn.com", "smtp.21cn.com");
        SMTP_HOST_MAP.put("foxmail.com", "smtp.foxmail.com");
        SMTP_HOST_MAP.put("china.com", "smtp.china.com");
        SMTP_HOST_MAP.put("tom.com", "smtp.tom.com");
        SMTP_HOST_MAP.put("etang.com", "smtp.etang.com");
    }

}
