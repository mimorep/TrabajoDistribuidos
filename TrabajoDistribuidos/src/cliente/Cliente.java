package cliente;

import interfazUsuario.InterfazBienvenida;
import interfazUsuario.InterfazInicio;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		InterfazInicio i = new InterfazInicio();
		i.run();
		int n = i.getImagen();
		while(n==9999) {
			n = i.getImagen();
		}
		InterfazBienvenida ii = new InterfazBienvenida(i.getImagen());
		ii.run(i.getImagen());
		/*
		ii = i.getInterfazBienvenida();
		String usuario = "", pwd = "";
		
		usuario = i.obtenerUsuario();
		//necesito recojer los datos del usuario para mandarlos al servidor donde estara el map
		
		System.out.println("El usuario es: " + usuario);
		System.out.println("La contrasenia es: " + pwd);
		*/
		
		//aqui se desarrollara el cliente
	}

}
