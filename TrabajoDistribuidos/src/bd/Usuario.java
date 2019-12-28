package bd;

import java.io.Serializable;

//Clase que representa cada uno de los usuarios de la BD, ademas serializa para poder ser enviada 
public class Usuario implements Serializable{
	
	private String cuasi;
	private String pwd;
	private Boolean esRoot;
	private Boolean esbibliotecario;
	
	//conctructor con parametros de Usuario
	public Usuario(String cuasi, String pwd, Boolean esRoot, Boolean esbibliotecario) {
		this.cuasi = cuasi;
		this.pwd = pwd;
		this.esRoot = esRoot;
		this.esbibliotecario = esbibliotecario;
	}
	//obtiene si es usuario root
	public Boolean getEsRoot() {
		return esRoot;
	}
	//obtiene si es un bibliotecario
	public Boolean getEsbibliotecario() {
		return esbibliotecario;
	}
	//obtiene la cuasi del usuario
	public String getCuasi() {
		return cuasi;
	}
	//obtiene la contrasenia del usuario
	public String getPwd() {
		return pwd;
	}
	//redefinicion del metodo toString
	public String toString() {
		String r = null;
		r = "\nCUASI: " + this.getCuasi() + "\npwd: " + this.getPwd() + "\nesRoot: " + this.getEsRoot() + "\nesBibliotecario: " + this.getEsbibliotecario();
		
		return r;
	}
	

}


