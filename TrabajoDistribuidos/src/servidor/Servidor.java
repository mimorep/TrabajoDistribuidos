package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bd.Usuario;
import sistema.Sistema;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sistema s = new Sistema();
		Map<Integer, Usuario> sitiosUR = new HashMap<Integer, Usuario>(); //La Rioja
		Map<Integer, Usuario> sitiosS = new HashMap<Integer, Usuario>();	//Salamanca
		
		//construimso los mapas con todos los sitios de la bibloteca y con el usuario a null
		Usuario u = null;
		for(int i=0;i<67;i++) {
			sitiosUR.put(i, u);
		}
		for(int j=0;j<170;j++) {
			sitiosS.put(j, u);
		}
		
		
		try(ServerSocket server = new ServerSocket(7777);){
			
			ExecutorService pool = Executors.newCachedThreadPool(); //crea tantos hilo como se necesita
			while(true) { //aqui es donde se estan atendiendo las peticiones, es decir donde estara el pool de hilos
				final Socket cliente = server.accept();
				pool.execute(new Hilo(cliente, s));
			}
			//pool.shutdown();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//el servidor se va a quedar esperando y se va a encargar de mostrarle la interfaz a cada uno de los clientes que le vayan pidiendo actuar.
		
		//aqui se desarrollara el servidor
	}

}
