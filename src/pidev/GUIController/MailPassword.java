/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import pidev.Entities.User;

/**
 *
 * @author acer
 */
public class MailPassword {
    public static void sendMail(String recepient, int code) throws Exception
    {   System.out.println("preparation d'envoie du mail");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host","smtp.gmail.com" );
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        String myAccountEmail = "imeeennne@gmail.com";
        String password = "12345.imen";
        
        Session session= Session.getInstance(properties, new Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(myAccountEmail,password);
           }
        });

        Message message = prepareMessage(session,myAccountEmail,recepient, code);
        
        Transport.send(message);
        System.out.println("mail envoyée avec succés");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, int code) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Récupération du mot de passe");
            message.setText("Bonjour Madame/Monsieur ,\n Veuillez entrez le code suivant pour récupérer votre mot de passe "+code);
           
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
       
    }
    
}
