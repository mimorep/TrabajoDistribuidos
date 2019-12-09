package servidor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bd.Usuario;
import sistema.Sistema;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sistema s = new Sistema();
		Sitios sitiosUR = new Sitios(); //La Rioja
		Sitios sitiosSA = new Sitios();	//Salamanca
		
		//construimso los mapas con todos los sitios de la bibloteca y con el usuario vacio para marcar que esta disponible
		Usuario u = new Usuario("vacio", "", false, false);
		for(int i=0;i<67;i++) {
			sitiosUR.addUsuario(i, u);;
		}
		for(int j=0;j<170;j++) {
			sitiosSA.addUsuario(j, u);;
		}
		
		try (FileOutputStream f = new FileOutputStream("SitiosUR.txt");
				ObjectOutputStream oos = new ObjectOutputStream(f)){
			oos.writeObject(sitiosUR);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try(ServerSocket server = new ServerSocket(7777);){
			
			ExecutorService pool = Executors.newCachedThreadPool(); //crea tantos hilo como se necesita
			while(true) { //aqui es donde se estan atendiendo las peticiones, es decir donde estara el pool de hilos
				Socket cliente = server.accept();
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
