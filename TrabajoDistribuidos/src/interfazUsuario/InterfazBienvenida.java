package interfazUsuario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

public class InterfazBienvenida extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblContrasea;
	private JButton btnIniciarSesion;
	private JPasswordField passwordField;
	private JLabel lblNewLabel;


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
		
		textField = new JTextField();
		textField.setBounds(233, 241, 309, 34);
		contentPane.add(textField);
		textField.setColumns(10);
		
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
		
		passwordField = new JPasswordField();
		passwordField.setBounds(233, 334, 309, 34);
		contentPane.add(passwordField);
		
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
}
