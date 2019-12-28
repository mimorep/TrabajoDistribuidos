package servidor;

import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//Clase que hereda de timerTask que va a ser la encargada de enviar un correo para avisar de que una reserva va a finalizar
public class EmailSender extends TimerTask {
	
	private String destinatario, universidad;
	private int sitio;
	
	public EmailSender(String destinatario, String universidad, int sitio) {
		this.destinatario = destinatario;
		this.universidad = universidad;
		this.sitio = sitio;
	}
	//clase encargada de mandar un mail informando de que la reserva se va a acabar
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Properties propiedad = new Properties();
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.starttls.enable", "true");
		propiedad.setProperty("mail.smtp.port", "587");
		propiedad.setProperty("mail.smtp.auth", "true");
		
		Session sesion = Session.getDefaultInstance(propiedad);
		
		String sender = "senderdistribuidos@gmail.com";
		String pwd = "aprobar1234567890";
		String asunto = "Su reseva va a finalizar";
		String body = "Le informamos que su siguiente reserva va a finalizar: \r\n\r\nUniversidad: " + this.universidad +"\r\n" + "Sitio: " + this.sitio + "\r\n";
		
		MimeMessage email = new MimeMessage(sesion);
		
		try {
			
			email.setFrom(new InternetAddress(sender));
			email.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			email.setSubject(asunto);
			email.setText(body);
			
			Transport transporte = sesion.getTransport("smtp");
			transporte.connect(sender, pwd);
			transporte.sendMessage(email, email.getRecipients(Message.RecipientType.TO));
			transporte.close();
			
		}catch(AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}

