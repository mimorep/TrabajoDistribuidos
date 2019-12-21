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

public class HiloSecundarioUsuarioNormal implements Runnable {
	
	private Socket cliente;
	private Sitios sUR, sSA;
	private Sistema s;
	
	public HiloSecundarioUsuarioNormal(Socket c, Sistema s, Sitios sUR, Sitios sSA) {
		// TODO Auto-generated constructor stub
		this.cliente = c;
		this.sUR = sUR;
		this.sSA = sSA;
		this.s = s;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		BufferedReader bf = null;
		try {
			
			 bf = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));
			
			
			String leido;
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
						
						//simpre el out antes que el in para evitar interbloqueos
						try(OutputStream outSocket = this.cliente.getOutputStream();
								InputStream inFich = new FileInputStream(fichero);){
							//leemos del fichero y lo vamos mandando
							byte buff[] = new byte[1024*32];
							int leidos = inFich.read(buff);
							while(leidos != -1) {
								outSocket.write(buff, 0, leidos);
								leidos = inFich.read(buff);
							}
						}		
					}else if(mandato[1].equals("1")){
						//es por que estamos en la US
						serializarSitios(sSA);
						
						//ahora tenemos que mandarlos
						fichero = new File("TrabajoDistribuidos\\src\\servidor\\SitiosSA.txt");
						try(InputStream inFich = new FileInputStream(fichero);
								OutputStream outSocket = this.cliente.getOutputStream()){
							
							//leemos del fichero y lo vamos mandando
							byte buff[] = new byte[1024*32];
							int leidos = inFich.read(buff);
							while(leidos != -1) {
								outSocket.write(buff, 0, leidos);
								leidos = inFich.read(buff);
							}
						}
					}
				}else if(mandato[0].equals("reservar")) {
					//falta implementacion
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

		
		
	