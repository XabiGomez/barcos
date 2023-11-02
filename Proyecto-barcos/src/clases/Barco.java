package clases;

public class Barco implements Comparable<Barco>{
	private TipoBarco tipo;
	private String modelo;
	private MarcaBarco marca;
	private int id;
	private int precio;
	//private String url;//para las imagenes
	
	public Barco(TipoBarco tipo, MarcaBarco marca, String modelo,int id, int precio) {
		this.tipo = tipo;
		this.modelo = modelo;
		this.marca = marca;
		this.id = id;
		this.precio = precio;
		//this.url = url;
	}

	public TipoBarco getTipo() {
		return tipo;
	}

	public void setTipo(TipoBarco tipo) {
		this.tipo = tipo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Barco [tipo=" + tipo + ", marca=" + marca + ", modelo=" + modelo + ", id=" + id + ", precio=" + precio + "]";
	}

	public MarcaBarco getMarca() {
		return marca;
	}

	public void setMarca(MarcaBarco marca) {
		this.marca = marca;
	}
	
	/*public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}*/
	
	 @Override
	    public int compareTo(Barco otroBarco) {//Comparamos por marca
	        return this.marca.compareTo(otroBarco.marca);
	    }
}
