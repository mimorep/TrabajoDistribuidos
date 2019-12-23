package servidor;

import java.util.TimerTask;

import bd.Usuario;

public class AutoLiberarSitio extends TimerTask {

	private Sitios s;
	private int posicion;
	
	public AutoLiberarSitio(Sitios s ,int n) {
		// TODO Auto-generated constructor stub
		this.s = s;
		posicion = n;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Usuario usuarioVacio = new Usuario("vacio", "", false, false);
		this.s.getSitios().put(this.posicion, usuarioVacio);
	}

}
