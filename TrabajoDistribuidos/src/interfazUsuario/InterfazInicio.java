package interfazUsuario;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class InterfazInicio extends JFrame {
	

	private JPanel contentPane;
	private JButton bEntrar;
	private JComboBox<String> cBUniversidades;
	
	private int imagen = 9999; 

	/**
	 * Create the frame.
	 */
	public InterfazInicio() {
		setResizable(false);
		setExtendedState(MAXIMIZED_BOTH);
		setIconImage(Toolkit.getDefaultToolkit().getImage(InterfazInicio.class.getResource("/imagenes/libro.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		bEntrar = new JButton("Entrar");
		bEntrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		bEntrar.setBackground(new Color(30, 144, 255));
		bEntrar.setBounds(347, 294, 89, 23);
		contentPane.add(bEntrar);
		
		this.bEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lanzarInterfaz();
			}
		});
		
		
		cBUniversidades = new JComboBox();
		cBUniversidades.setBounds(252, 186, 254, 20);
		contentPane.add(cBUniversidades);
		cBUniversidades.addItem("Universidad de la Rioja");
		cBUniversidades.addItem("Universidad de Salamanca");
		
	}
	
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazInicio frame = new InterfazInicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		int n = this.getImagen();
		while(n==9999) {
			n = this.getImagen();
		}
	}
	public void lanzarInterfaz() {
		int n = 0;
		String nombre = (String)this.cBUniversidades.getSelectedItem();
		if(nombre.contains("Rioja")) {
			n = 0;
			InterfazBienvenida ib = new InterfazBienvenida(n);
			ib.run(n);
		}else if(nombre.contains("Salamanca")){
			n = 1;
			InterfazBienvenida ibb = new InterfazBienvenida(n);
			ibb.run(n);
		}
		this.setVisible(false);
	}
	public Integer getImagen() {
		return this.imagen;
	}
	public void setImagen(int n) {
		this.imagen = n;
	}
	
}
