package servidor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bd.Usuario;
import sistema.Sistema;

//Clase encargada de atender a los clientes lanzando siempre un hilo para cada uno 
public class Servidor {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sistema s = new Sistema();
		Sitios sitiosUR = new Sitios("sitiosUR"); //La Rioja usar symcrono
		Sitios sitiosSA = new Sitios("sitiosSA");	//Salamanca
		
		//construimso los mapas con todos los sitios de la bibloteca y con el usuario vacio para marcar que esta el sitio disponible
		Usuario u = new Usuario("vacio", "", false, false);
		for(int i=0;i<67;i++) {
			sitiosUR.reservarSitio(i, u);
		}
		for(int j=0;j<170;j++) {
			sitiosSA.reservarSitio(j, u);
		}
		
//		serializarSitios(sitiosUR);
//		serializarSitios(sitiosSA);
		InetAddress i = null;
		
		try(ServerSocket server = new ServerSocket(7777);){
			
			ExecutorService pool = Executors.newCachedThreadPool(); //crea tantos hilo como se necesita
			while(true) { //aqui es donde se estan atendiendo las peticiones, es decir donde estara el pool de hilos
				Socket cliente = server.accept();
				i = cliente.getInetAddress();
				//Socket clienteObjetos = null; //solo usaremos este en caso de que se vaya a serializar un objeto
				pool.execute(new Hilo(server, cliente, s, sitiosUR, sitiosSA));
			}
			//pool.shutdown();
			
		}catch(SocketException ee) {
			if(i != null) {
				System.err.println("El cliente se ha desconectado" + i.toString());
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
