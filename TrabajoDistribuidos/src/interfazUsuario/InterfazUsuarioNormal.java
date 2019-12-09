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
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class InterfazUsuarioNormal extends JFrame {

	private JPanel contentPane;
	private JLabel logo;
	
	private int imagen;
	private JTextField tFSitio;
	/**
	 * Create the frame.
	 */
	public InterfazUsuarioNormal(int imagen) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(InterfazUsuarioNormal.class.getResource("/imagenes/libro.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tFSitio = new JTextField();
		tFSitio.setBounds(223, 223, 288, 28);
		contentPane.add(tFSitio);
		tFSitio.setColumns(10);
		
		JLabel lblSitioAReservar = new JLabel("Sitio a reservar");
		lblSitioAReservar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSitioAReservar.setBounds(223, 187, 167, 14);
		contentPane.add(lblSitioAReservar);
		
		JButton btnReservar = new JButton("Reservar");
		btnReservar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReservar.setBounds(223, 280, 133, 23);
		contentPane.add(btnReservar);
		
		btnReservar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reservarSitio();
			}
		});
		
		JLabel lbError = new JLabel("EL SITIO QUE INTENTAS RESERVAR NO EXISTE O YA EST\u00C1 RESERVADO");
		lbError.setForeground(Color.RED);
		lbError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbError.setBounds(223, 339, 476, 14);
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
	}
	public void reservarSitio() {
		//metodo que obtendra el numero que se introduce en el TF, para mandarlo al servidor he intentar reservar el sitio
	}
}
