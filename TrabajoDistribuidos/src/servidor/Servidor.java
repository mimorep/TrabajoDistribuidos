package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import sistema.Sistema;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sistema s = new Sistema();
		
		//vamos a tener un Map que tenga los sitios numerados junto con los usuarios que han reservado el sitio
		
		try(ServerSocket server = new ServerSocket(7777);){
			
			while(true) {
				try(Socket cliente = server.accept();
					BufferedReader bf = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
						Writer w = new OutputStreamWriter(cliente.getOutputStream())){
					
					String linea, usuario = "", pwd = "", respuesta;
					if((linea = bf.readLine()) != null) {
						String[] usuarioPWD = linea.split(" ");
						usuario = usuarioPWD[0];
						pwd = usuarioPWD[1];
					}
					System.out.println("Usuario: " + usuario);
					System.out.println("Contrasenia: " + pwd);
					
					if(s.autenticarse("Universidad de la Rioja", usuario, pwd)) {
						if(usuario.contains("root")) {
							respuesta = "isroot";
						}else if(usuario.contains("bibliotecari")) {
							respuesta = "isbiblio";
						}else {
							respuesta = "isnormal";
						}
					}else {
						respuesta = "notvalidated";
					}
				
					w.write(respuesta);
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
