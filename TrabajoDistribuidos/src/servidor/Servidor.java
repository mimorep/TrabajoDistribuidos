package servidor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

import bd.Usuario;
import sistema.Sistema;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sistema s = new Sistema();
		Sitios sitiosUR = new Sitios("sitiosUR"); //La Rioja usar symcrono
		Sitios sitiosSA = new Sitios("sitiosSA");	//Salamanca
		
		//construimso los mapas con todos los sitios de la bibloteca y con el usuario vacio para marcar que esta el sitio disponible
		Usuario u = new Usuario("vacio", "", false, false);
		for(int i=0;i<67;i++) {
			sitiosUR.addUsuario(i, u);;
		}
		for(int j=0;j<170;j++) {
			sitiosSA.addUsuario(j, u);;
		}
		
//		serializarSitios(sitiosUR);
//		serializarSitios(sitiosSA);
		
		try(ServerSocket server = new ServerSocket(7777);){
			
			ExecutorService pool = Executors.newCachedThreadPool(); //crea tantos hilo como se necesita
			while(true) { //aqui es donde se estan atendiendo las peticiones, es decir donde estara el pool de hilos
				Socket cliente = server.accept();
				Socket clienteObjetos = server.accept();
				//Socket clienteObjetos = null; //solo usaremos este en caso de que se vaya a serializar un objeto
				pool.execute(new Hilo(cliente, clienteObjetos, s, sitiosUR, sitiosSA));
			}
			//pool.shutdown();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//metodo al que llamaremos cada vez que queramos serializar un objeto del tipo Sitios
//	public static void serializarSitios(Sitios s) {
//		String nombreArchivo = (s.getNombre().equals("sitiosUR"))? "SitiosUR.txt":"SitiosSA.txt";
//		
//		try (FileOutputStream f = new FileOutputStream(nombreArchivo);
//				ObjectOutputStream oos = new ObjectOutputStream(f)){
//			oos.writeObject(s);
//			
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}

}
