package clases;

public class Extras implements Comparable<Extras> {
	private TipoExtras tipo;
	private int Id;
	private int compra;
	private int venta;
	//private String url;//para las imagenes
	
	
	public Extras(int Id, TipoExtras tipo,  int compra, int venta) {
		this.Id = Id;
		this.tipo = tipo;
		this.compra = compra;
		this.venta = venta;
		//this.url = url;
	}

	public TipoExtras getTipo() {
		return tipo;
	}

	public void setTipo(TipoExtras tipo) {
		this.tipo = tipo;
	}
	
	public int getId() {
		return Id;
	}


	public void setId(int id) {
		Id = id;
	}


	public int getCompra() {
		return compra;
	}


	public void setCompra(int compra) {
		this.compra = compra;
	}


	public int getVenta() {
		return venta;
	}


	public void setVenta(int venta) {
		this.venta = venta;
	}
	
	/*public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}*/
	
	 @Override
	    public int compareTo(Extras otrosExtras) {//Comparamos por tipo
	        return this.tipo.compareTo(otrosExtras.tipo);
	    }
	
}
