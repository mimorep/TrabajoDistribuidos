package interfazUsuario;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import servidor.Sitios;

public class InterfazBibliotecario extends JFrame {

	private JPanel contentPane;
	private JTextField tFPosicion;
	private JLabel logo;
	
	private int imagen;
	private JLabel lblNSitio;
	private JButton btnLiberar;

	/**
	 * Create the frame.
	 */
	public InterfazBibliotecario(int imagen) {
		this.imagen = imagen;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(InterfazBibliotecario.class.getResource("/imagenes/libro.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btSitios = new JButton("Ver sitios");
		btSitios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btSitios.setBounds(272, 188, 176, 23);
		contentPane.add(btSitios);
		
		btSitios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Sitios s = obtenerSitios();
			}
		});
		
		JButton btLiberar = new JButton("Liberar Sitio");
		btLiberar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btLiberar.setBounds(272, 257, 176, 23);
		contentPane.add(btLiberar);
		
		btLiberar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mostrarLiberar();
			}
		});
		
		tFPosicion = new JTextField();
		tFPosicion.setBounds(272, 356, 176, 20);
		contentPane.add(tFPosicion);
		tFPosicion.setVisible(false);
		tFPosicion.setColumns(10);
		
		lblNSitio = new JLabel("N\u00AA Sitio");
		lblNSitio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNSitio.setBounds(272, 328, 69, 14);
		lblNSitio.setVisible(false);
		contentPane.add(lblNSitio);
		
		btnLiberar = new JButton("Liberarlo");
		btnLiberar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLiberar.setBounds(272, 400, 133, 23);
		btnLiberar.setVisible(false);
		contentPane.add(btnLiberar);
		
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
	public void run(int n) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazBibliotecario frame = new InterfazBibliotecario(n);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void mostrarLiberar() {
		this.lblNSitio.setVisible(true);
		this.btnLiberar.setVisible(true);
		this.tFPosicion.setVisible(true);
	}
	public Sitios obtenerSitios() {
		Sitios s = null;
		String ruta = "";
		if(imagen == 0) {
			ruta = "SitiosUR.txt";
		}else if(imagen == 1) {
			ruta = "SitiosSA.txt";
		}
		try(FileInputStream f = new FileInputStream(ruta);
				ObjectInputStream ois = new ObjectInputStream(f)){
			
			s = (Sitios) ois.readObject(); //cojemos la lista de sitios
			//manipulamos la lista de sitios como queramos
			for(int i=0;i<s.size();i++) {
				System.out.println(s.getUsuario(i).getCuasi()); //mostramos todos los usuarios
			}
			
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
		
		return s;
	}

}