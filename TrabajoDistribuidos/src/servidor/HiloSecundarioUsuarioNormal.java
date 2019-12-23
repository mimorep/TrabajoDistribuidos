package servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Timer;

import bd.Usuario;
import sistema.Sistema;

public class HiloSecundarioUsuarioNormal implements Runnable {
	
	private Socket cliente;
	private Sitios sUR, sSA;
	private Sistema s;
	private Usuario u;
	
	public HiloSecundarioUsuarioNormal(Socket c, Sistema s, Sitios sUR, Sitios sSA, Usuario u) {
		// TODO Auto-generated constructor stub
		this.cliente = c;
		this.s = s;
		this.sUR = sUR;
		this.sSA = sSA;
		this.u = u;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		InetAddress inet = this.cliente.getInetAddress();
		try (BufferedReader bf = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));){
			String leido;
			
			while(true) {
				
				if((leido = bf.readLine()) != null) {
					String[] mandato = leido.split(":");
					
					if(mandato[0].equals("serializar")) {
						//en este punto queremos serializar y mandar el objeto al cliente
						File fichero;
						if(mandato[1].equals("0")) {
							//es por que estamos en la UR
							serializarSitios(sUR);
							//ahora temos que mandarlos
							fichero = new File("TrabajoDistribuidos\\src\\servidor\\SitiosUR.txt"); //creamos el obj para representar lo serializado
							
							OutputStream outSocket = null;
							InputStream inFich = null;
							//simpre el out antes que el in para evitar interbloqueos
							try{
								outSocket = this.cliente.getOutputStream();
								inFich = new FileInputStream(fichero);
								
								//leemos del fichero y lo vamos mandando
								byte buff[] = new byte[1024*32];
								int leidos = inFich.read(buff);
								while(leidos != -1) {
									outSocket.write(buff, 0, leidos);
									leidos = inFich.read(buff);
								}
								outSocket.flush();
							}catch(IOException e) {
								e.printStackTrace();
							}finally {
								if(inFich != null) {
									try {
										inFich.close();
									}catch(IOException e) {
										e.printStackTrace();
									}
								}
							}
						}else if(mandato[1].equals("1")){
							//es por que estamos en la US
							serializarSitios(sSA);
							
							//ahora tenemos que mandarlos
							fichero = new File("TrabajoDistribuidos\\src\\servidor\\SitiosSA.txt");
							OutputStream outSocket = null;
							InputStream inFich = null;
							
							try{
								outSocket = this.cliente.getOutputStream();
								inFich = new FileInputStream(fichero);
								
								//leemos del fichero y lo vamos mandando
								byte buff[] = new byte[1024*32];
								int leidos = inFich.read(buff);
								while(leidos != -1) {
									outSocket.write(buff, 0, leidos);
									leidos = inFich.read(buff);
								}
								outSocket.flush();
							}catch(IOException e) {
								e.printStackTrace();
							}finally {
								if(inFich != null) {
									inFich.close();
								}
							}
						}
					}else if(mandato[0].equals("reservar")) {
						if(mandato[1].equals("0")) {
							//es por que estamos en la UR
							DataOutputStream outSocket = null;
							try {
								String respuesta = "";
								outSocket = new DataOutputStream(this.cliente.getOutputStream());
								int sitio = Integer.parseInt(mandato[2]);
								//Lo primero es comprobar que ese sitio existe
								if(0<sitio && sitio<this.sUR.getSitios().size()) {
									//rango valido
									//antes de esto debemos mirar si el usuario tiene ya una reserva
									if(this.sUR.tienereserva(u)) {
										//si tiene reserva
										respuesta = "tiene reserva\r\n";
									}else {
										//si no tiene reserva
										if(this.sUR.estaOcupado(sitio)) {
											//si el sitio esta ocupado
											respuesta = "ocupado\r\n";
										}else {
											//si el sitio no esta ocupado
											respuesta = "OK \r\n";
											this.sUR.reservarSitio(sitio, u);
											//aqui añadiremos un timer para que se libere el sitio a los 30 mins
											Timer timer = new Timer();
											Calendar c = Calendar.getInstance();
											c.add(Calendar.SECOND, 10);
											//dentro de 10000 ms avisame 1 vez
											timer.schedule(new AutoLiberarSitio(sUR, sitio), c.getTime());
										}
									}
								}else {
									//rango no valido
									respuesta = "ocupado\r\n";
								}
								outSocket.writeBytes(respuesta);
								outSocket.flush();
							}catch(IOException e) {
								e.printStackTrace();
							}
							
						}else if(mandato[1].equals("1")) {
							//es por que estamos en la US
							DataOutputStream outSocket = null;
							try {
								String respuesta = "";
								outSocket = new DataOutputStream(this.cliente.getOutputStream());
								int sitio = Integer.parseInt(mandato[2]);
								if(0<sitio && sitio<this.sSA.getSitios().size()) {
									//rango valido
									//antes de esto debemos mirar si el usuario tiene ya una reserva
									if(this.sSA.tienereserva(u)) {
										//si tiene reserva
										respuesta = "tiene reserva\r\n";
									}else {
										//si no tiene reserva
										if(this.sSA.estaOcupado(sitio)) {
											//si el sitio esta ocupado
											respuesta = "ocupado\r\n";
										}else {
											//si el sitio no esta ocupado
											respuesta = "OK \r\n";
											this.sSA.reservarSitio(sitio, u);
											Timer timer = new Timer();
											Calendar c = Calendar.getInstance();
											c.add(Calendar.SECOND, 10);
											//dentro de 10000 ms avisame 1 vez
											timer.schedule(new AutoLiberarSitio(sSA, sitio), c.getTime());
										}
									}
								}else {
									//rango no valido
									respuesta = "ocupado\r\n";							
								}

								outSocket.writeBytes(respuesta);
								outSocket.flush();
							}catch(IOException e) {
								e.printStackTrace();
							}
						}
					}else if(mandato[0].equals("liberar")) {
						//si hemos llegado aqui es por que si o si tiene un sitio que podemos liberar
						if(mandato[1].equals("0")) {
							//estamos en la UR
							//buscamos el usuario en la lista y ponemos su sitio a vacio
							for(int i=0;i<this.sUR.getSitios().size();i++) {
								if(this.sUR.getSitios().get(i).getCuasi().equals(u.getCuasi())) {
									//si coinciden las cuasis ponemos el sitio a vacio
									Usuario usuarioVacio = new Usuario("vacio", "", false, false);
									this.sUR.getSitios().put(i, usuarioVacio);
								}
							}
						}else if(mandato[1].equals("1")){
							//estamos en la US
							//buscamos el usuario en la lista y ponemos su sitio a vacio
							for(int i=0;i<this.sSA.getSitios().size();i++) {
								if(this.sSA.getSitios().get(i).getCuasi().equals(u.getCuasi())) {
									//si coinciden las cuasis ponemos el sitio a vacio
									Usuario usuarioVacio = new Usuario("vacio", "", false, false);
									this.sSA.getSitios().put(i, usuarioVacio);
								}
							}
						}
					}
					
	 			}
			}
		}catch(SocketException ee) {
			if(inet != null) {
				System.err.println("El cliente " + inet.toString() + " se ha desconectado");
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void serializarSitios(Sitios s) {
		String nombreArchivo = (s.getNombre().equals("sitiosUR"))? "TrabajoDistribuidos\\src\\servidor\\SitiosUR.txt":"TrabajoDistribuidos\\src\\servidor\\SitiosSA.txt";
		
		try (FileOutputStream f = new FileOutputStream(nombreArchivo);
				ObjectOutputStream oos = new ObjectOutputStream(f)){
			oos.writeObject(s);
			oos.flush();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

		
		
	