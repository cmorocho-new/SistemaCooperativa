package ec.edu.ups.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

    /**
     * Metodo que envia el un correo
     * @param destinatario
     * @param asunto
     * @param cuerpo
     * @throws MessagingException
     */
    public static void enviarCorreo(String destinatario, String asunto, String cuerpo)  throws MessagingException {
        // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
        String remitente = "scooperativa2020";   //Para la dirección nomcuenta@gmail.com

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", "siscooprosofappdis032020"); //La clave de la cuenta
        props.put("mail.smtp.auth", "true");  //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
        props.put("mail.smtp.timeout", 1000);

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remitente));
        message.addRecipients(Message.RecipientType.TO, destinatario);  //Se podrían añadir varios de la misma manera
        message.setSubject(asunto);
        message.setText(cuerpo, "utf-8", "html");
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", remitente, "siscooprosofappdis032020");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
