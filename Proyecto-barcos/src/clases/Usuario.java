package clases;


public class Usuario {
	private String nombre;
	private String contrasena;
	private int dinero;

	public Usuario(String nombre, String contrasena, int dinero) {
		super();
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.dinero = dinero;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public int getDinero() {
		return dinero;
	}
	
	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", contrasena=" + contrasena + ", dinero=" + dinero + "]";
	}
	

}
