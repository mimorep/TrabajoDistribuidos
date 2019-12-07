package interfazUsuario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

public class InterfazBienvenida extends JFrame {

	private JPanel contentPane;
	private JTextField tFUsuario;
	private JLabel lblContrasea;
	private JButton btnIniciarSesion;
	private JPasswordField pFContrasenia;
	private JLabel lblNewLabel;
	
	//atributos externos al diseño
	private String usuario = "", pwd = "";


	/**
	 * Create the frame.
	 */
	public InterfazBienvenida(int imagen) {
		
		
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
		btnIniciarSesion.setBounds(233, 407, 130, 23);
		contentPane.add(btnIniciarSesion);
		
		this.btnIniciarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getCuasipwd(usuario, pwd);
				//aniadir el lanzar interfaz de opciones
				
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
	public String getusuario() {
		return this.usuario;
	}
	public String getpwd() {
		return this.pwd;
	}
}
