package cliente;

import interfazUsuario.InterfazInicio;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InterfazInicio i = new InterfazInicio();
		i.run();
		String usuario = "", pwd = "";
		
		while(usuario == "" || usuario == null) {
			usuario = i.obtenerUsuario();
		}
		
		System.out.println("El usuario es: " + usuario);
		System.out.println("La contrasenia es: " + pwd);
		
		//aqui se desarrollara el cliente
	}

}
