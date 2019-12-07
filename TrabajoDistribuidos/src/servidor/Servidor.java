package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import bd.Usuario;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//vamos a tener un Map que tenga los sitios numerados junto con los usuarios que han reservado el sitio
		
		try(ServerSocket server = new ServerSocket(7777);){
			
			while(true) {
				try(Socket cliente = server.accept();
					BufferedReader bf = new BufferedReader(new InputStreamReader(cliente.getInputStream()));){
					
					String linea, usuario = "", pwd = "";
					if((linea = bf.readLine()) != null) {
						String[] usuarioPWD = linea.split(" ");
						usuario = usuarioPWD[0];
						pwd = usuarioPWD[1];
					}
					System.out.println("Usuario: " + usuario);
					System.out.println("Contrasenia: " + pwd);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		//el servidor se va a quedar esperando y se va a encargar de mostrarle la interfaz a cada uno de los clientes que le vayan pidiendo actuar.
		
		//aqui se desarrollara el servidor
	}

}
