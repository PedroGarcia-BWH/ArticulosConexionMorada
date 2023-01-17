package es.uca.articulosconexionmorada.bean;

public class Persona {
 
	public String nombre;
	public String apellidos;
	public String documento;
	
	public Persona() {
		
	}
	
	public Persona(String nombre, String apellidos, String documento) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.documento = documento;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	
}
