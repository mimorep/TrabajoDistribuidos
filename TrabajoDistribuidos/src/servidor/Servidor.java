package servidor;

import sistema.Sistema;
import interfazUsuario.*;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//vamos a tener un Map que tenga los sitios numerados junto con los usuarios que han reservado el sitio
		
		
		Sistema s = new Sistema(); //creamos e inicializamos la BD
		
		/*
		System.out.println(s.autenticarse("Universidad de la Rioja", "mimorep", "mp"));
		s.aniadir("Universidad de la Rioja", "nuevo", "no");
		System.out.println(s.autenticarse("Universidad de la Rioja", "nuevo", "no"));
		*/
		s.eliminar("Universidad de la Rioja", "nuevo");
		
		
		
		//el servidor se va a quedar esperando y se va a encargar de mostrarle la interfaz a cada uno de los clientes que le vayan pidiendo actuar.
		
		//aqui se desarrollara el servidor
	}

}
