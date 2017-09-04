import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Pavel on 07.04.2017.
 */

/*

https://support.google.com/mail/answer/7126229?p=BadCredentials&visit_id=1-636398494981305577-1471690966&rd=2#cantsignin

https://myaccount.google.com/lesssecureapps?pli=1

https://accounts.google.com/DisplayUnlockCaptcha

 */
public class EmailService {
    private String from;
    private String password;
    private Properties props = new Properties();
    public EmailService(String from,String fromPassword) {
        this.from=from;
        this.password=fromPassword;

        // Setup mail server
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");//
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");//!!
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        // Get the default Session object.
    }

    public void sendMessage(String to,String title,String messageForUser)
    {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(title);

            // Now set the actual message
            message.setText(messageForUser);

            // Send message
            Transport.send(message);

            System.out.println("Sent all message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
