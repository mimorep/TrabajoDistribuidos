package sistema;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bd.Biblioteca;
import bd.Usuario;

public class Sistema {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File("src/xml/BDUsuarios.xml"));
			
			List<Biblioteca> listaBibliotecas = new ArrayList<Biblioteca>();
			Element raiz = doc.getDocumentElement(); //obtenemos el nodo raiz
			NodeList hijos = raiz.getElementsByTagName("biblioteca"); //obtenemos una lista con todas las bibliotecas
			
			for(int i=0;i<hijos.getLength();i++) {
				Biblioteca b;
				List<bd.Usuario> usuarios = new ArrayList<bd.Usuario>(); //lista donde vamos a almacenar los usuarios
				
				//primero comprobamos que es un nodo
				if(hijos.item(i).getNodeType() == Node.ELEMENT_NODE) {
					
					String n = null, l = null;
					Element hijo = (Element) hijos.item(i);
					NodeList nombre = hijo.getElementsByTagName("nombre");
					NodeList localizacion = hijo.getElementsByTagName("localizacion");
					NodeList usuarioss = hijo.getElementsByTagName("usuario");
					if(nombre.getLength() == 1) { //si no es uno es por que hay algun error
						n = ((Element)nombre.item(0)).getTextContent();
					}
					if(localizacion.getLength() == 1) {
						l = ((Element)localizacion.item(0)).getTextContent();
					}
					for(int j=0;j<usuarioss.getLength();j++) {
						String cuasi, pwd;
						Boolean root, bibliotecario;
						cuasi = ((Element) usuarioss.item(j)).getAttribute("cuasi").toString();
						pwd = ((Element) usuarioss.item(j)).getAttribute("pwd").toString();
						root = (cuasi.contains("root"))? true:false;
						bibliotecario = (cuasi.contains("bibliotecari"))? true:false;
						//creamos el usuario con los datos anteriores
						bd.Usuario u = new Usuario(cuasi, pwd, root, bibliotecario);
						
						usuarios.add(u); //aniadimos el ususario de la biblioteca
					}
					if(n!=null && l != null) {
						b = new Biblioteca(n, l, usuarios);
						listaBibliotecas.add(b); //aniadimos la biblioteca a la lista de bibliotecas
					}
					
				}//si no es un nodo no hacemos nada con el
			}
			
			for(int i=0;i<listaBibliotecas.size();i++) {
				System.out.println("--------------------------------");
				System.out.println(listaBibliotecas.get(i).toString());
			}
			
		}catch(ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
