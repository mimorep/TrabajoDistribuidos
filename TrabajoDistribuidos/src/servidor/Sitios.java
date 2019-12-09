package servidor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import bd.Usuario;

public class Sitios implements Serializable {
	
	static final long serialVersionUID = 7;
	
	private Map<Integer, Usuario> sitios = new HashMap<Integer, Usuario>();
	
	public void addUsuario(int n, Usuario u) {
		this.sitios.put(n, u);
	}
	public Boolean estaOcupado(int i) {
		Boolean resultado = false;
		if(this.sitios.get(i) != null) {
			//si es distinto de null es por que el sitio esta ocupado
			resultado = true;
		}
		return resultado;
	}
	public Usuario getUsuario(int n) {
		return this.sitios.get(n);
	}
	public int size() {
		return this.sitios.size();
	}
	
}
