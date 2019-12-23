package interfazUsuario;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class InterfazBienvenida extends JFrame {

	private JPanel contentPane;
	private JTextField tFUsuario;
	private JLabel lblContrasea;
	private JButton btnIniciarSesion;
	private JPasswordField pFContrasenia;
	private JLabel lblNewLabel;
	private JLabel lbError;
	
	private int universidad;
	private String usuario;

	/**
	 * Create the frame.
	 */
	public InterfazBienvenida(int imagen) {
		
		universidad = imagen;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(InterfazBienvenida.class.getResource("/imagenes/libro.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tFUsuario = new JTextField();
		tFUsuario.setBounds(233, 241, 309, 34);
		contentPane.add(tFUsuario);
		tFUsuario.setColumns(10);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario");
		lblNombreDeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreDeUsuario.setBounds(233, 199, 187, 14);
		contentPane.add(lblNombreDeUsuario);
		
		lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContrasea.setBounds(233, 286, 116, 14);
		contentPane.add(lblContrasea);
		
		btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnIniciarSesion.setBounds(233, 422, 130, 23);
		contentPane.add(btnIniciarSesion);
		
		this.btnIniciarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//aniadir el lanzar interfaz de opciones
				mandarDatosAlServer();
			}
		});
		
		pFContrasenia = new JPasswordField();
		pFContrasenia.setBounds(233, 334, 309, 34);
		contentPane.add(pFContrasenia);
		
		lblNewLabel = new JLabel("");
		if(imagen == 0) {
			lblNewLabel.setIcon(new ImageIcon(InterfazBienvenida.class.getResource("/imagenes/logoUni.jpg")));
			lblNewLabel.setBounds(270, 11, 225, 166);
		}else if(imagen == 1) {
			lblNewLabel.setIcon(new ImageIcon(InterfazBienvenida.class.getResource("/imagenes/logoSalamanca.jpg")));
			lblNewLabel.setBounds(270, 11, 260, 170);
		}
		contentPane.add(lblNewLabel);
		
		lbError = new JLabel("Usuario o contrase\u00F1a incorrecto");
		lbError.setForeground(Color.RED);
		lbError.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbError.setBounds(233, 381, 309, 30);
		contentPane.add(lbError);
		lbError.setVisible(false);
	}
	public void run(int n) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazBienvenida frame = new InterfazBienvenida(n);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//metodo que almacena en los atributos que se pasan como parametros los valores de usuario y contrasenia
	public void getCuasipwd(String usuario, String pwd) {
		usuario = this.tFUsuario.getText();
		pwd = this.pFContrasenia.getText();
	}
	public String getUsuario() {
		return this.tFUsuario.getText();
	}
	public String getPwd() {
		return this.pFContrasenia.getText();
	}
	//metodo que se va a encargar de mandar tanto el usuario como la contrasenia al servidor
	public void mandarDatosAlServer() {
		Socket cliente = null;
		this.usuario  = this.getUsuario();
		DataOutputStream outSocket;
		DataInputStream inSocket;
		try {
			//no podemos hacer un try with resources para evitar que se nos cierre el Socket cuando se llaman a las demas interfaces
			cliente = new Socket("localhost", 7777);
			//clienteObjetos = new Socket("localhost", 7777);
			outSocket = new DataOutputStream(cliente.getOutputStream());
			inSocket = new DataInputStream(cliente.getInputStream());
			
			String send = "";
			if(this.universidad == 0) {
				send = "Universidad de la Rioja:" + this.getUsuario() + ":" + this.getPwd() + "\r\n"; //lo mandamos separado con un espacio para que el servidor lo splitee
			}else if(this.universidad == 1) {
				send = "Universidad de Salamanca:" + this.getUsuario() + ":" + this.getPwd() + "\r\n"; //lo mandamos separado con un espacio para que el servidor lo splitee
			}
			outSocket.writeBytes(send);
			outSocket.flush();
			//hay que continuar por que este recivira una respuesta del server y con ella lanzara una interfaz u otra
			String s = inSocket.readLine();//leemos lo que nos manda el server
			
			if(s.contains("isroot")) {
				
				System.out.println("Es un root");
				InterfazRoot ir = new InterfazRoot(this.universidad, cliente, this.usuario);
				ir.run(this.universidad, cliente, this.usuario);
				this.setVisible(false);
				
			}else if(s.contains("isbiblio")){
				
				System.out.println("es un bilio");
				InterfazBibliotecario ib = new InterfazBibliotecario(this.universidad, cliente, this.usuario);
				ib.run(this.universidad, cliente, this.usuario);
				this.setVisible(false);
				
			}else if(s.contains("notvalidated")){
				
				//mostar label de que no estas en la BD
				System.out.println("NO ESTAS EN EL SISTEMA");
				this.lbError.setVisible(true);
				
			}else {
				System.out.println("es normal");
				InterfazUsuarioNormal iu  = new InterfazUsuarioNormal(this.universidad, cliente, this.usuario);
				iu.run(this.universidad, cliente, this.usuario);
				this.setVisible(false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//no hay que cerrar el Socket se encargara de ello la siguiente interfaz
	}
}
