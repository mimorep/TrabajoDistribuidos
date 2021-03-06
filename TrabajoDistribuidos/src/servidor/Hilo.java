package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import bd.Usuario;
import sistema.Sistema;

//clase encargada de atender a todos los clientes que contacten con el servidor
public class Hilo implements Runnable{
	
	private ServerSocket servidor;
	private Socket cliente;
	private Sitios sUR, sSA;
	private Sistema s;
	private Usuario u;
	
	public Hilo(ServerSocket servidor ,Socket cliente, Sistema s, Sitios sUR, Sitios sSA) {
		
		this.servidor = servidor;
		this.cliente = cliente;
		this.s = s;
		this.sUR = sUR;
		this.sSA = sSA;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		BufferedReader bf;
		Writer w;
				
		try{ //usar printWiter para evitar a�aidr el salto
			
			bf = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			w = new OutputStreamWriter(cliente.getOutputStream());
			
			String linea, usuario = "", pwd = "", universidad = "",respuesta;
			if((linea = bf.readLine()) != null) {
				
				//cuando entramos aqui tenemos ya la linea
				String[] usuarioPWD = linea.split(":");
				universidad = usuarioPWD[0];
				usuario = usuarioPWD[1];
				pwd = usuarioPWD[2];
			}
			System.out.println("Universidad: " + universidad);
			System.out.println("Usuario: " + usuario);
			System.out.println("Contrasenia: " + pwd);
			
			if(s.autenticarse(universidad, usuario, pwd)) { //realiza la autenticacion del usuario
				if(usuario.contains("root")) {
					respuesta = "isroot \r\n";
					//tenemos que mandar la respuesta justo despues por que si no el server se va ha quedar esperando a qeu el cliente mande algo, pero el cliente se va a quedar esperando a que el server le responda  --> interbloqueo
					w.write(respuesta);
					w.flush();
					HiloSecundarioRoot hr = new HiloSecundarioRoot(cliente, s); //llamamos al hilo secuandario para que realize el eliminar/aniadir
					hr.run();
					//creariamos el hilo secundario
					
				}else if(usuario.contains("bibliotecari")) {
					u = new Usuario(usuario, pwd, false, true);
					respuesta = "isbiblio \r\n";
					w.write(respuesta);
					w.flush();
					//creariamos el hilo secundario
					HiloSecundarioBibliotecario hb = new HiloSecundarioBibliotecario(servidor, cliente, s, sUR, sSA);
					hb.run();
				}else {
					u = new Usuario(usuario, pwd, false, false);
					respuesta = "isnormal \r\n";
					w.write(respuesta);
					w.flush();
					//creariamos el hilo secundario
					HiloSecundarioUsuarioNormal hn = new HiloSecundarioUsuarioNormal(cliente, s, sUR, sSA, u);
					hn.run();
				}
			}else {
				//caso en el que el usuario no esta registrado en la BD
				respuesta = "notvalidated \r\n";
				w.write(respuesta);
				w.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
