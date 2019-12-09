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
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class InterfazRoot extends JFrame {

	private JPanel contentPane;
	private JTextField tFNombre;
	private JTextField tFConstrasenia;
	private JLabel lblNewLabel;
	
	private JRadioButton rdbRoot;
	private JRadioButton rdbBibliotecario;
	private JLabel lblNombreUsuario;
	private JLabel lblContrasea;
	private JButton Vacio;
	
	private int imagen;
	private String permisos;
	private Socket cliente;
	

	/**
	 * Create the frame.
	 */
	public InterfazRoot(int imagen, Socket cliente) {
		
		this.imagen = imagen;
		this.cliente = cliente;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(InterfazRoot.class.getResource("/imagenes/libro.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAadirUsuario = new JButton("A\u00F1adir Usuario");
		btnAadirUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAadirUsuario.setBounds(234, 220, 213, 23);
		contentPane.add(btnAadirUsuario);
		
		btnAadirUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarAniadir();
			}
		});
		
		JButton btnEliminarUsuario = new JButton("Eliminar Usuario");
		btnEliminarUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEliminarUsuario.setBounds(234, 269, 213, 23);
		contentPane.add(btnEliminarUsuario);
		
		btnEliminarUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarEliminar();
			}
		});
		
		rdbRoot = new JRadioButton("Usuario Root");
		rdbRoot.setBounds(522, 174, 109, 23);
		rdbRoot.setVisible(false);
		contentPane.add(rdbRoot);
		
		rdbRoot.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ponerRoot();
			}
		});
		
		rdbBibliotecario = new JRadioButton("Bibliotecario/a");
		rdbBibliotecario.setBounds(633, 174, 109, 23);
		rdbBibliotecario.setVisible(false);
		contentPane.add(rdbBibliotecario);
		
		rdbBibliotecario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ponerBibliotecario();
			}
		});
		
		tFNombre = new JTextField();
		tFNombre.setBounds(522, 223, 169, 20);
		contentPane.add(tFNombre);
		tFNombre.setColumns(10);
		tFNombre.setVisible(false);
		
		tFConstrasenia = new JTextField();
		tFConstrasenia.setBounds(522, 272, 169, 20);
		contentPane.add(tFConstrasenia);
		tFConstrasenia.setColumns(10);
		tFConstrasenia.setVisible(false);
		
		lblNombreUsuario = new JLabel("Nombre Usuario");
		lblNombreUsuario.setBounds(522, 204, 109, 14);
		contentPane.add(lblNombreUsuario);
		lblNombreUsuario.setVisible(false);
		
		lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(522, 254, 169, 14);
		contentPane.add(lblContrasea);
		lblContrasea.setVisible(false);
		
		lblNewLabel = new JLabel("");
		if(imagen == 0) {
			lblNewLabel.setIcon(new ImageIcon(InterfazBienvenida.class.getResource("/imagenes/logoUni.jpg")));
			lblNewLabel.setBounds(270, 11, 225, 166);
		}else if(imagen == 1) {
			lblNewLabel.setIcon(new ImageIcon(InterfazBienvenida.class.getResource("/imagenes/logoSalamanca.jpg")));
			lblNewLabel.setBounds(270, 11, 260, 170);
		}
		contentPane.add(lblNewLabel);
		
		Vacio = new JButton("Vacio");
		Vacio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Vacio.setBounds(522, 319, 169, 23);
		Vacio.setVisible(false);
		contentPane.add(Vacio);
		
		Vacio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarAniadir();
			}
		});
	}
	
	public void run(int n, Socket c) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazRoot frame = new InterfazRoot(n, c);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void mostrarAniadir() {

		this.rdbRoot.setVisible(true);
		this.rdbBibliotecario.setVisible(true);
		this.lblNombreUsuario.setVisible(true);
		this.lblContrasea.setVisible(true);
		this.Vacio.setVisible(true);
		this.tFNombre.setVisible(true);
		this.tFConstrasenia.setVisible(true);
		
		this.Vacio.setText("Añadir este usuario");
		
	}
	public void mostrarEliminar() {
		
		this.rdbRoot.setVisible(false);
		this.rdbBibliotecario.setVisible(false);
		this.lblNombreUsuario.setVisible(true);
		this.lblContrasea.setVisible(false);
		this.Vacio.setVisible(true);
		this.tFNombre.setVisible(true);
		this.tFConstrasenia.setVisible(false);
		
		this.Vacio.setText("Eliminar este usuario");
	}
	public void ponerRoot() {
		this.permisos = "root";
	}
	public void ponerBibliotecario() {
		this.permisos = "bibliotecario";
	}
	public void eliminarAniadir() {
		//aqui usaremos el Socket que se nos pasa como parametro, no necesitamos crearlo de nuevo por que cuando se nos pasa ya esta creado y lo pasamos como parametro para que sea mas eficiente
		String content = this.Vacio.getText();
		String envio = "";
		try(DataOutputStream outSocket = new DataOutputStream(this.cliente.getOutputStream());
				DataInputStream inSocket = new DataInputStream(this.cliente.getInputStream())){
			String biblio = (this.imagen == 0)? "Universidad de la Rioja":"Universidad de Salamanca";
			if(content.contains("Eliminar")) {
				//caso de eliminar
				envio = "eliminar:" + this.tFNombre.getText() + biblio+ "\r\n";
			}else if(content.contains("adir")) {
				//caso de añadir
				envio = "aniadir:" + this.permisos + ":" + this.tFNombre.getText() + ":" + this.tFConstrasenia.getText() +biblio + "\r\n"; //creamos la cadena que se va a enviar
			}
			outSocket.writeBytes(envio);
			outSocket.flush();
			//ahora leemos para ver si se ha aniadido con exito
			System.out.println(inSocket.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
