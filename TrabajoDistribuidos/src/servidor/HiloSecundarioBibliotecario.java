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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import bd.Usuario;
import sistema.Sistema;

//clase encargada de atender a los clientes bibliotecarios
public class HiloSecundarioBibliotecario implements Runnable {
	
	//este hilo va ha serializar la lista y la va a enviar
	private ServerSocket servidor;
	private Socket cliente, clienteObjetos;
	private Sitios sUR, sSA;
	private Sistema s;
	
	public HiloSecundarioBibliotecario(ServerSocket servidor , Socket cliente, Sistema s, Sitios sUR, Sitios sSA) {
		this.servidor = servidor;
		this.cliente = cliente;
		this.s = s;
		this.sUR = sUR;
		this.sSA = sSA;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		InetAddress inet = this.cliente.getInetAddress();
		//SIMPRE QUE ABRAMOS OUT/INPUTS TENEMOS QUE CREAR PRIMERO LOS OUT Y LUEGO LOS IN, si no estaremos causando un deadlock
		try (BufferedReader bf = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));){
			String leido;
			//va todo en un bucle infinito para que se pueda realizar mas de una accion
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
							InputStream inFich = null;
							OutputStream outSocket = null;
							try{
								//siempre tenemos que declararlo en este orden, si no podriamos obtener un deadlock
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
								//si sale del bucle es por que hemos terminado de enviar lo que queriamos
							}catch(IOException e) {
								e.printStackTrace();
							}finally {
								if(inFich != null) {
									try {
										//no cerramos nada del socket para poder reutilizarlo
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
							InputStream inFich = null;
							OutputStream outSocket = null;
							try{
								inFich = new FileInputStream(fichero);
								outSocket = this.cliente.getOutputStream();
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
								if(outSocket != null||inFich != null) {
									try {
										//no cerramos nada del socket para poder reutilizarlo
										inFich.close();
									}catch(IOException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}else if(mandato[0].equals("liberar")) {
						String respuesta = "";
						int sitio = Integer.parseInt(mandato[2]);
						if(mandato[1].equals("0")) {
							//estamos en la UR
							if(0<sitio && sitio<this.sUR.getSitios().size()) {
								//dentro de rango 
								if(this.sUR.estaOcupado(sitio)) {
									//si esta ocupado lo eliminamos
									respuesta = "OK\r\n";
									Usuario usuarioVacio = new Usuario("vacio", "", false, false);
									this.sUR.getSitios().put(sitio, usuarioVacio);
								}else {
									//si no lo esta informamos mandando error
									respuesta = "error\r\n";
								}
							}else {
								//fuera de rango
								respuesta = "error\r\n";
							}
						}else if(mandato[1].equals("1")) {
							//estamos en al US
							if(0<sitio && sitio<this.sSA.getSitios().size()) {
								//dentro de rango
								if(this.sSA.estaOcupado(sitio)) {
									//si esta ocupado lo eliminamos
									respuesta = "OK\r\n";
									Usuario usuarioVacio = new Usuario("vacio", "", false, false);
									this.sSA.getSitios().put(sitio, usuarioVacio);
								}else {
									//si no lo esta informamos mandando error
									respuesta = "error\r\n";
								}
							}else {
								//fuera de rango
								respuesta = "error\r\n";
							}
						}
						DataOutputStream outSocket = null;
						try {
							outSocket = new DataOutputStream(this.cliente.getOutputStream());
							outSocket.writeBytes(respuesta);
							outSocket.flush();
							
						}catch(IOException e) {
							e.printStackTrace();
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
	//metodo encargado de serializar una clase Sitios que se le esta pasando como parametro
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
