package servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import sistema.Sistema;

public class HiloSecundarioBibliotecario implements Runnable {
	
	//este hilo va ha serializar la lista y la va a enviar
	private Socket cliente;
	private Sitios sUR, sSA;
	private Sistema s;
	
	public HiloSecundarioBibliotecario(Socket cliente, Sistema s, Sitios sUR, Sitios sSA) {
		this.cliente = cliente;
		this.s = s;
		this.sUR = sUR;
		this.sSA = sSA;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
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
								inFich = new FileInputStream(fichero);
								outSocket = this.cliente.getOutputStream();
								//leemos del fichero y lo vamos mandando
								byte buff[] = new byte[1024*32];
								int leidos = inFich.read(buff);
								while(leidos != -1) {
									outSocket.write(buff, 0, leidos);
									leidos = inFich.read(buff);
								}
								//si sale del bucle es por que hemos terminado de enviar lo que queriamos
								//si mandamos aqui un -1 estaremos marcando que se ha terminado de enviar sin necesidad de cerrar el Socket asi lo podremos reusar para seguir mandando peticiones
							}catch(IOException e) {
								e.printStackTrace();
							}finally {
								if(outSocket != null||inFich != null) {
									try {
										outSocket.close();
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
							}catch(IOException e) {
								e.printStackTrace();
							}finally {
								if(outSocket != null||inFich != null) {
									try {
										outSocket.close();
										outSocket = this.cliente.getOutputStream();
										inFich.close();
									}catch(IOException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}else if(mandato[0].equals("liberar")) {
						//falta implementacion
					}
	 			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void serializarSitios(Sitios s) {
		String nombreArchivo = (s.getNombre().equals("sitiosUR"))? "TrabajoDistribuidos\\src\\servidor\\SitiosUR.txt":"TrabajoDistribuidos\\src\\servidor\\SitiosSA.txt";
		
		try (FileOutputStream f = new FileOutputStream(nombreArchivo);
				ObjectOutputStream oos = new ObjectOutputStream(f)){
			oos.writeObject(s);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
