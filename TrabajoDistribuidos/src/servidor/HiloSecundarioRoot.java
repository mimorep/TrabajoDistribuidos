package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import sistema.Sistema;

//Clase encargada de atender a usuarios Root
public class HiloSecundarioRoot implements Runnable {

	private Socket cliente;
	private Sistema s;
	
	public HiloSecundarioRoot(Socket c, Sistema s) {
		this.cliente = c;
		this.s = s;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		InetAddress inet = this.cliente.getInetAddress();
		//SIMPRE QUE ABRAMOS OUT/INPUTS TENEMOS QUE CREAR PRIMERO LOS OUT Y LUEGO LOS IN, si no estaremos causando un deadlock
		try(BufferedReader bf = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));
				Writer w =  new OutputStreamWriter(this.cliente.getOutputStream());){
			String leido, orden, permisos, nombre, pwd, biblio;
			//se mete todo este proceso en un bucle para que pueda realizar mas de una sola accion, con el mismo Socket
			while(true) {
				if((leido = bf.readLine()) != null) {
					String[] mandato = leido.split(":");
					if(mandato[0].equals("aniadir")) {
						orden = mandato[0];
						permisos = mandato[1];
						nombre = mandato[2];
						pwd = mandato[3];
						biblio = mandato[4];
						if(!s.autenticarse(biblio, nombre, pwd)) {
							//si no esta lo aniadirmos
							s.aniadir(biblio, permisos+nombre, pwd);
							w.write("Aniadio\r\n");
							w.flush();
						}else {
							w.write("no aniadido \r\n");
							w.flush();
						}
					}else { //si no es por que estamos en el eliminar
						orden = mandato[0];
						nombre = mandato[1];
						pwd = mandato[2];
						biblio = mandato[3];
						if(s.autenticarse(biblio, nombre, pwd)) { //comprobamos primero si esta para eliminarlo
							s.eliminar(biblio, nombre);
							w.write("eliminado\r\n");
							w.flush();
						}
					}
					
				}
			}

			
		}catch(SocketException ee) {
			if(inet != null) {
				System.err.println("El cliente " + inet.toString() + " se ha desconectado");
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
