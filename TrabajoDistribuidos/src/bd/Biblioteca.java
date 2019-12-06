package bd;

import java.util.List;

public class Biblioteca {
	
	private String nombre;
	private String localizacion;
	private List<Usuario> usuarios; //lista donde tendremos los usuarios que estan registrados en esa universidad
	
	public Biblioteca(String nombre, String localizacion, List<Usuario> usuarios) {
		this.nombre = nombre;
		this.localizacion = localizacion;
		this.usuarios = usuarios;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public String toString() {
		String r;
		r = "Nombre: " + this.getNombre() + "\nLocalizacion: " + this.getLocalizacion();
		
		for(int i=0;i<this.usuarios.size();i++) {
			r = r +"\n"+ usuarios.get(i).toString();
		}
		
		return r;
	}
	
}
