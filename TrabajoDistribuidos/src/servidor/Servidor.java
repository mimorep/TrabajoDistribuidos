package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
			
			while(true) {
				try(Socket cliente = server.accept();
					BufferedReader bf = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
						Writer w = new OutputStreamWriter(cliente.getOutputStream())){
					
					String linea, usuario = "", pwd = "", universidad = "",respuesta;
					if((linea = bf.readLine()) != null) {
						String[] usuarioPWD = linea.split(":");
						universidad = usuarioPWD[0];
						usuario = usuarioPWD[1];
						pwd = usuarioPWD[2];
					}
					System.out.println("Universidad: " + universidad);
					System.out.println("Usuario: " + usuario);
					System.out.println("Contrasenia: " + pwd);
					
					if(s.autenticarse(universidad, usuario, pwd)) {
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
