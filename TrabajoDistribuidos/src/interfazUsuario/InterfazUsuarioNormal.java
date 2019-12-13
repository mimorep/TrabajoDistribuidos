package interfazUsuario;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

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
	
	private Socket cliente;
	private JLabel lbError;

	public InterfazUsuarioNormal(int imagen, Socket cliete) {
		
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
		tFSitio.setBounds(301, 225, 288, 28);
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
		
		
		bReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reload();
			}
		});
		
	}
	public void run(int n, Socket c) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazUsuarioNormal frame = new InterfazUsuarioNormal(n, c);
					frame.setVisible(true);
					frame.obtenerSitios(); //para cargarlos de primeras
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//metodo encargado de reservar un sitio
	public void reservarSitio() {
		//metodo que obtendra el numero que se introduce en el TF, para mandarlo al servidor he intentar reservar el sitio
	}
	
	//metodo encargado de actualizar la lista de reservas
	public void reload() {
		obtenerSitios();
	}
	public void obtenerSitios() {
		//a�adir una comunicacion con el servidor para que cada vez que se pulse el servidor serialize el objeto
		
		String envio = "";
		Sitios s = null;
		
		DataOutputStream outSocket = null;
		InputStream inSocket = null;
		OutputStream outFichero = null;
		String ruta = "";
		
		try {
			outSocket = new DataOutputStream(this.cliente.getOutputStream());
			inSocket = this.cliente.getInputStream();
			
			if(imagen == 0) {
				ruta = "TrabajoDistribuidos\\src\\interfazUsuario\\SitiosUR.txt"; //ruta donde el cliente guardara el objeto serializado
			}else if(imagen == 1) {
				ruta = "TrabajoDistribuidos\\src\\interfazUsuario\\SitiosSA.txt"; //ruta donde el cliente guardara el objeto serializado
			}
			
			outFichero = new FileOutputStream(ruta); //ruta donde el cliente guardara el objeto serializado
			
			envio = "serializar:" + this.imagen + ":" + "\r\n";
			outSocket.writeBytes(envio);
			outSocket.flush();
			
			//ahora tenemos que leer el objeto serializado
			byte buff[] = new byte[1024*32];
			int leidos = inSocket.read(buff);
			while(leidos != -1) {
				outFichero.write(buff, 0, leidos);
				leidos = inSocket.read(buff);
			}
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
