package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

import sistema.Sistema;

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
		try(BufferedReader bf = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));
				Writer w = new OutputStreamWriter(this.cliente.getOutputStream())){
			
			String leido, orden, permisos, nombre, pwd, biblio;
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
						w.write("Aniadio \r\n");
					}else {
						w.write("no aniadido \r\n");
					}
				}else { //si no es por que estamos en el eliminar
					orden = mandato[0];
					nombre = mandato[1];
					pwd = mandato[2];
					biblio = mandato[3];
					if(s.autenticarse(biblio, nombre, pwd)) { //comprobamos primero si esta para eliminarlo
						s.eliminar(biblio, nombre);
						w.write("eliminado \r\n");
					}
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
