package bd;

import java.util.List;

//clase que va a representar cada una de las bibliotecas de mi sistema
public class Biblioteca {
	
	private String nombre;
	private String localizacion;
	private List<Usuario> usuarios; //lista donde tendremos los usuarios que estan registrados en esa universidad
	
	//constructor con parametros de biblioteca
	public Biblioteca(String nombre, String localizacion, List<Usuario> usuarios) {
		this.nombre = nombre;
		this.localizacion = localizacion;
		this.usuarios = usuarios;
	}
	//obtiene el nombre de la biblioteca
	public String getNombre() {
		return nombre;
	}
	//actualiza el nombre de la biblioteca
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	//obtiene la localizacion de la biblioteca
	public String getLocalizacion() {
		return localizacion;
	}
	//actualiza la locaclizacion de la biblioteca
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	//obtiene los usuarios de la biblioteca
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	//actualiza los usuarios de la biblioteca
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	//redefinicion del metodo toString
	public String toString() {
		String r;
		r = "Nombre: " + this.getNombre() + "\nLocalizacion: " + this.getLocalizacion();
		
		for(int i=0;i<this.usuarios.size();i++) {
			r = r +"\n"+ usuarios.get(i).toString();
		}
		
		return r;
	}
	
}
