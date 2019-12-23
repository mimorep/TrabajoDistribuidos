package servidor;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import bd.Usuario;

public class Sitios implements Serializable {
	
	static final long serialVersionUID = 7;
	private String nombre;
	
	public Sitios(String nombre) {
		this.nombre = nombre;
	}
	
	private ConcurrentHashMap<Integer, Usuario> sitios = new ConcurrentHashMap<Integer, Usuario>();
	
	public synchronized void reservarSitio(int n, Usuario u) { //igual necesario hacer este metodo sincrono para que varios usuarios no puedan reservar el mismo sitio
		this.sitios.put(n, u);
	}
	public Boolean estaOcupado(int i) {
		Boolean resultado = false;
		String nombre = this.sitios.get(i).getCuasi();
		if(!nombre.equals("vacio")) {
			//si es distinto de vacio es por que el sitio esta ocupado
			resultado = true;
		}
		return resultado;
	}
	public Boolean tienereserva(Usuario u) {
		Boolean respuesta = false;
		for(int i=0;i<this.sitios.size();i++) {
			if(this.sitios.get(i).getCuasi().equals(u.getCuasi())) {
				//si coincide la cuasi es por que ya tiene reserva
				respuesta = true;
			}
		}
		return respuesta;
	}
	public Usuario getUsuario(int n) {
		return this.sitios.get(n);
	}
	public int size() {
		return this.sitios.size();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ConcurrentHashMap<Integer, Usuario> getSitios(){
		return this.sitios;
	}
	
}
