package bd;

public class Usuario {
	
	private String cuasi;
	private String pwd;
	private Boolean esRoot;
	private Boolean esbibliotecario;
	
	public Usuario(String cuasi, String pwd, Boolean esRoot, Boolean esbibliotecario) {
		this.cuasi = cuasi;
		this.pwd = pwd;
		this.esRoot = esRoot;
		this.esbibliotecario = esbibliotecario;
	}

	public Boolean getEsRoot() {
		return esRoot;
	}

	public Boolean getEsbibliotecario() {
		return esbibliotecario;
	}

	public String getCuasi() {
		return cuasi;
	}

	public void setCuasi(String cuasi) {
		this.cuasi = cuasi;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String toString() {
		String r = null;
		r = "\nCUASI: " + this.getCuasi() + "\npwd: " + this.getPwd() + "\nesRoot: " + this.getEsRoot() + "\nesBibliotecario: " + this.getEsbibliotecario();
		
		return r;
	}
	

}


