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

public class InterfazBibliotecario extends JFrame {

	private JPanel contentPane;
	private JTextField tFPosicion;
	private JLabel logo;
	
	private int imagen;
	private JLabel lblNSitio;
	private JButton btnLiberar;
	private JTextArea textArea;
	
	private Socket cliente, clienteObjetos;
	private JButton btSitios;
	private JLabel lbLogin;
	private JLabel lbLiberar;

	/**
	 * Create the frame.
	 */
	public InterfazBibliotecario(int imagen, Socket cliente, String usuario) {
		this.imagen = imagen;
		this.cliente = cliente;		
		//this.clienteObjetos = clienteObjetos;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(InterfazBibliotecario.class.getResource("/imagenes/libro.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btSitios = new JButton("Ver sitios");
		btSitios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btSitios.setBounds(272, 188, 176, 23);
		contentPane.add(btSitios);
		
		btSitios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				obtenerSitios();
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
		
		btnLiberar = new JButton("Liberar");
		btnLiberar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLiberar.setBounds(272, 400, 133, 23);
		btnLiberar.setVisible(false);
		contentPane.add(btnLiberar);
		
		btnLiberar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				liberarSitio();
				obtenerSitios();
			}
		});
		
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
		textArea.setBounds(518, 189, 200, 263);
		textArea.setEditable(false);
		contentPane.add(textArea);
		
		JScrollPane sb = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sb.setBounds(518, 189, 200, 263);
		contentPane.add(sb);
		
		lbLogin = new JLabel("Login: " + usuario);
		lbLogin.setBounds(585, 34, 151, 14);
		contentPane.add(lbLogin);
		
		lbLiberar = new JLabel("");
		lbLiberar.setBounds(136, 438, 372, 14);
		lbLiberar.setVisible(false);
		contentPane.add(lbLiberar);
		
		
		
	}
	public void run(int n, Socket c, String u) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazBibliotecario frame = new InterfazBibliotecario(n, c, u);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void liberarSitio() {
		this.lbLiberar.setVisible(false);
		
		String envio = "";
		int sitio = Integer.parseInt(this.tFPosicion.getText());
		
		DataOutputStream outSocket = null;
		DataInputStream inSocket = null;
		
		try {
			outSocket = new DataOutputStream(this.cliente.getOutputStream());
			inSocket = new DataInputStream(this.cliente.getInputStream());
			
			envio = "liberar:" + this.imagen + ":" +  sitio + ":\r\n";
			outSocket.writeBytes(envio);
			outSocket.flush();
			
			String respuesta = inSocket.readLine();
			Font f = new Font("f", 3571, 14);
			if(respuesta.equals("OK")) {
				this.lbLiberar.setText("LIBERADO CON EXITO");
				this.lbLiberar.setForeground(Color.blue);
				this.lbLiberar.setFont(f);
				this.lbLiberar.setVisible(true);
			}else if(respuesta.equals("error")) {
				this.lbLiberar.setText("ERROR AL RESERVAR, puede que estuviera ya vacio");
				this.lbLiberar.setForeground(Color.red);
				this.lbLiberar.setFont(f);
				this.lbLiberar.setVisible(true);
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	public void mostrarLiberar() {
		this.lblNSitio.setVisible(true);
		this.btnLiberar.setVisible(true);
		this.tFPosicion.setVisible(true);
	}
	public void obtenerSitios() {
		//para este metodo tenemos que usar otro socket, que sera usado para el envio del objeto serializado
		
		this.btSitios.setText("Actualizar lista de sitios");
		//añadir una comunicacion con el servidor para que cada vez que se pulse el servidor serialize el objeto
		
		String envio = "";
		Sitios s = null;
		
		DataOutputStream outSocket = null;
		InputStream inSocket = null;
		OutputStream outFichero = null;
		String ruta = "";
		
		try {
			outSocket = new DataOutputStream(this.cliente.getOutputStream());
			//de esta forma tenemos separandas la in de la out
			
			if(imagen == 0) {
				ruta = "TrabajoDistribuidos\\src\\interfazUsuario\\SitiosUR.txt"; //ruta donde el cliente guardara el objeto serializado
			}else if(imagen == 1) {
				ruta = "TrabajoDistribuidos\\src\\interfazUsuario\\SitiosSA.txt"; //ruta donde el cliente guardara el objeto serializado
			}
			
			outFichero = new FileOutputStream(ruta); //ruta donde el cliente guardara el objeto serializado
			
			envio = "serializar:" + this.imagen + ":" + "\r\n";
			outSocket.writeBytes(envio);
			outSocket.flush();
			
			//hacemos esto para que cada vez que se pulse el socket se regenere
			//this.clienteObjetos = new Socket("localhost", 7777);
			inSocket = this.cliente.getInputStream();
			this.cliente.setSoTimeout(100); //con esta orden limitamos el tiempo de espera asi no sera necesario cerrar el socket en cada iteracion y el programa sera mas eficiente
			
			//ahora tenemos que leer el objeto serializado
			byte buff[] = new byte[1024*32];
			int leidos = inSocket.read(buff);
			while(leidos != -1) {
				outFichero.write(buff, 0, leidos);
				leidos = inSocket.read(buff); //se nos esta quedando aqui bloqueado
			}
		}catch(SocketTimeoutException ee) {
			System.out.println("tiempo de espera agotado \r\n");
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
