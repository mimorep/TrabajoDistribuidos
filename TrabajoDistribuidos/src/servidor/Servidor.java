package servidor;

import sistema.Sistema;
import interfazUsuario.*;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Sistema s = new Sistema(); //creamos e inicializamos la BD
		InterfazInicio i = new InterfazInicio();
		i.run();
		
		
		
		//el servidor se va a quedar esperando y se va a encargar de mostrarle la interfaz a cada uno de los clientes que le vayan pidiendo actuar.
		
		//aqui se desarrollara el servidor
	}

}
