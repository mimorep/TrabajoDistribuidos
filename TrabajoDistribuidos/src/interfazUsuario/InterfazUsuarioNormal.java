package interfazUsuario;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import servidor.Sitios;

public class InterfazUsuarioNormal extends JFrame {

	private JPanel contentPane;
	private JLabel logo;
	
	private int imagen;
	private JTextField tFSitio;
	private JTextArea textArea;
	private JButton bReload;
	private JButton btnLiberar;
	
	private Socket cliente;
	private JLabel lbError;
	private JLabel lbReserva;
	private int timeout;
	private JLabel lbLogin;
	private JTextField tfEmail;

	public InterfazUsuarioNormal(int imagen, Socket cliete, String usuario) {
		
		this.imagen = imagen;
		this.cliente = cliete;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(InterfazUsuarioNormal.class.getResource("/imagenes/libro.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tFSitio = new JTextField();
		tFSitio.setBounds(301, 225, 46, 28);
		contentPane.add(tFSitio);
		tFSitio.setColumns(10);
		
		JLabel lblSitioAReservar = new JLabel("Sitio a reservar");
		lblSitioAReservar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSitioAReservar.setBounds(303, 184, 167, 14);
		contentPane.add(lblSitioAReservar);
		
		JButton btnReservar = new JButton("Reservar");
		btnReservar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReservar.setBounds(301, 275, 133, 23);
		contentPane.add(btnReservar);
		
		btnReservar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reservarSitio();
				reload();
			}
		});
		
		lbError = new JLabel("EL SITIO QUE INTENTAS RESERVAR NO EXISTE O YA EST\u00C1 RESERVADO");
		lbError.setForeground(Color.RED);
		lbError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbError.setBounds(263, 333, 476, 14);
		lbError.setVisible(false);
		contentPane.add(lbError);
		
		logo = new JLabel("");
		if(imagen == 0) {
			logo.setIcon(new ImageIcon(InterfazBienvenida.class.getResource("/imagenes/logoUni.jpg")));
			logo.setBounds(270, 11, 225, 166);
		}else if(imagen == 1) {
			logo.setIcon(new ImageIcon(InterfazBienvenida.class.getResource("/imagenes/logoSalamanca.jpg")));
			logo.setBounds(270, 11, 260, 170);
		}
		contentPane.add(logo);
		
		textArea = new JTextArea();
		textArea.setBounds(52, 0, 200, 263);
		contentPane.add(textArea);
		
		JScrollPane sb = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sb.setBounds(50, 148, 200, 263);
		contentPane.add(sb);
		
		bReload = new JButton("");
		bReload.setIcon(new ImageIcon(InterfazUsuarioNormal.class.getResource("/imagenes/reload2.jpg")));
		bReload.setBounds(135, 116, 28, 28);
		contentPane.add(bReload);
		
		lbReserva = new JLabel("YA TIENES UNA RESERVA A TU NOMBRE");
		lbReserva.setForeground(Color.RED);
		lbReserva.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbReserva.setBounds(263, 371, 321, 14);
		lbReserva.setVisible(false);
		contentPane.add(lbReserva);
		
		btnLiberar = new JButton("Liberar");
		btnLiberar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLiberar.setBounds(592, 367, 89, 23);
		btnLiberar.setVisible(false);
		contentPane.add(btnLiberar);
		
		lbLogin = new JLabel("Login: " + usuario);
		lbLogin.setBounds(619, 33, 108, 14);
		contentPane.add(lbLogin);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(490, 225, 210, 28);
		contentPane.add(tfEmail);
		tfEmail.setColumns(10);
		
		JLabel lblSiQuieresEnterarte = new JLabel("Email:");
		lblSiQuieresEnterarte.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSiQuieresEnterarte.setBounds(490, 186, 46, 14);
		contentPane.add(lblSiQuieresEnterarte);
		
		JLabel lblRellenaEsteCampo = new JLabel("Rellena este campo si quires recibir un mensaje");
		lblRellenaEsteCampo.setBounds(490, 256, 278, 14);
		contentPane.add(lblRellenaEsteCampo);
		
		JLabel lblCuandoTuReserva = new JLabel("cuando tu reserva este a punto de finalizar");
		lblCuandoTuReserva.setBounds(490, 281, 278, 14);
		contentPane.add(lblCuandoTuReserva);
		
		btnLiberar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				liberarSitio();
				reload();
			}
		});
		
		bReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reload();
			}
		});
		
	}
	public void run(int n, Socket c, String u) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazUsuarioNormal frame = new InterfazUsuarioNormal(n, c, u);
					frame.setVisible(true);
					frame.obtenerSitios(); //para cargarlos de primeras
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//metodo que se encarga de liberar una reserva si el usuario lo desea
	public void liberarSitio() {
		String envio = "";
		
		DataOutputStream outSocket = null;
		
		try {
			outSocket = new DataOutputStream(this.cliente.getOutputStream());
			envio = "liberar:"+ this.imagen + ":" + "\r\n";
			
			outSocket.writeBytes(envio);
			outSocket.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
		this.lbReserva.setVisible(false);
		this.btnLiberar.setVisible(false);
		
	}
	//metodo encargado de reservar un sitio
	public void reservarSitio() {
		lbError.setVisible(false);
		//metodo que obtendra el numero que se introduce en el TF, para mandarlo al servidor he intentar reservar el sitio
		String envio = "";
		int sitio = Integer.parseInt(tFSitio.getText());
		String email = "";
		if(tfEmail.getText() != null && tfEmail.getText() != "") {
			email = tfEmail.getText();
		}
	
		DataOutputStream outSocket = null;
		DataInputStream inSocket = null;
		try {
			this.cliente.setSoTimeout(0); //ponemos el timeout al maximo
			
			outSocket = new DataOutputStream(this.cliente.getOutputStream());
			inSocket = new DataInputStream(this.cliente.getInputStream());
			envio = "reservar:" +this.imagen + ":" + sitio + ":" + email + ":" +"\r\n";
			
			outSocket.writeBytes(envio);
			outSocket.flush();
			
			//ahora tenemos que leer la respuesta del servidor para ver si se ha podido hacer la reserva
			
			String resultado = inSocket.readLine();
			if(resultado.equals("OK")) {
				//si llegamos a este punto es por que hemos logrado reservar los sitios
				this.lbError.setVisible(false);
			}else if(resultado.equals("ocupado")){
				lbError.setVisible(true);
			}else if(resultado.equals("tiene reserva")) {
				this.lbReserva.setVisible(true);
				this.btnLiberar.setVisible(true);
			}
		}catch(SocketException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//metodo encargado de actualizar la lista de reservas
	public void reload() {
		obtenerSitios();
	}
	public void obtenerSitios() {
		//añadir una comunicacion con el servidor para que cada vez que se pulse el servidor serialize el objeto
		
		String envio = "";
		Sitios s = null;
		
		DataOutputStream outSocket = null;
		InputStream inSocket = null;
		OutputStream outFichero = null;
		String ruta = "";
		
		try {
			outSocket = new DataOutputStream(this.cliente.getOutputStream());
			
			if(imagen == 0) {
				ruta = "TrabajoDistribuidos\\src\\interfazUsuario\\SitiosUR.txt"; //ruta donde el cliente guardara el objeto serializado
			}else if(imagen == 1) {
				ruta = "TrabajoDistribuidos\\src\\interfazUsuario\\SitiosSA.txt"; //ruta donde el cliente guardara el objeto serializado
			}
			
			outFichero = new FileOutputStream(ruta); //ruta donde el cliente guardara el objeto serializado
			
			envio = "serializar:" + this.imagen + ":" + "\r\n";
			outSocket.writeBytes(envio);
			outSocket.flush();
			
			
			inSocket = this.cliente.getInputStream();
			this.cliente.setSoTimeout(100); //con esto evitaremos tener que cerrar el socket cada vez, asi sera mas eficiente el programa
			
			//ahora tenemos que leer el objeto serializado
			byte buff[] = new byte[1024*32];
			int leidos = inSocket.read(buff);
			while(leidos != -1) {
				outFichero.write(buff, 0, leidos);
				leidos = inSocket.read(buff);
			}
		}catch(SocketTimeoutException ee) {
			System.out.println("Timepo de espera agotado \r\n");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		try(FileInputStream f = new FileInputStream(ruta);
				ObjectInputStream ois = new ObjectInputStream(f)){
			
			s = (Sitios) ois.readObject(); //cojemos la lista de sitios
			//manipulamos la lista de sitios como queramos
			String fin = "";
			for(int i=0;i<s.size();i++) {
				String texto = "Sitio "+ i +": " + s.getUsuario(i).getCuasi() + "\r\n";
				System.out.println(texto); //mostramos todos los usuarios
				fin = fin + texto;
			}
			this.textArea.setText(fin);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
