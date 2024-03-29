package clases;

import java.util.Date;

public class Compra {
	private Usuario usuario;
	private int producto;
	private float precio;
	private Date fecha;
	
	public Compra(Usuario usuario, int producto, float precio, Date fecha) {
		super();
		this.usuario = usuario;
		this.producto = producto;
		this.precio = precio;
		this.fecha = fecha;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public int getProducto() {
		return producto;
	}
	public void setProducto(int producto) {
		this.producto = producto;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
