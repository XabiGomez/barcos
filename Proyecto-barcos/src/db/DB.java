package db;

import java.io.InputStream;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import clases.Barco;
import clases.Extras;
import clases.Usuario;


public class DB {
	private static Logger logger = Logger.getLogger("DataBase");
	
	//private Properties properties;
	//private String driver;
	//private String connection;
	
	/* public DB() {
	        properties = new Properties();
	        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
	            properties.load(input);
	            driver = properties.getProperty("driver");
	        } catch (Exception e) {
	            logger.log(Level.SEVERE, "Error al cargar el archivo de propiedades", e);
	        }
	    }*/

	public boolean init(String nombredb) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/db/"+nombredb);
			Statement stmt = conn.createStatement();
			logger.log( Level.INFO, "Conectada base de datos " + nombredb );
			stmt = conn.createStatement();
			stmt.setQueryTimeout( 10 );
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS usuario (nombre STRING, contrasena STRING, dinero INTEGER)");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS compras (usuario STRING, producto INTEGER, precio INTEGER, fecha STRING)");

			return true;
		} catch (ClassNotFoundException | SQLException e) {
			logger.log( Level.SEVERE, "Error en conexión de base de datos " + nombredb, e );
			return false;
		}
	}

	public ArrayList<Integer> sacarIdsUsuario(String nombredb, Usuario u){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/db/"+nombredb);
			Statement stmt = conn.createStatement();
			ArrayList<Integer> dev = new ArrayList<>();
			String sql = "SELECT producto FROM compras where usuario = '" + u.getNombre() + "'";
			logger.log(Level.INFO, "Statement: " + sql);
			ResultSet rs = stmt.executeQuery("SELECT producto FROM compras where usuario = '" + u.getNombre() + "'");
			while( rs.next() ) { 
			int id = rs.getInt("producto");
			dev.add( id );
			}
			rs.close();
			stmt.close();
			conn.close(); 
			return dev;
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			e.printStackTrace();
			}
		return null;
	
	}
	
	public void guardarUsuario(String nombredb, String nombre, String contrasena,  int dinero){
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/db/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("INSERT INTO usuario VALUES ('%s', '%s', %d)", nombre, contrasena, dinero);
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}
	
	public void borrarDBUsuario(String nombredb,String nombre){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/db/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("DELETE FROM usuario where nombre = '"+ nombre+"';");
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}
	
	public ArrayList<Usuario> sacarUsuarios(String nombredb){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/db/"+nombredb);
			Statement stmt = conn.createStatement();
			ArrayList<Usuario> dev = new ArrayList<>();
			String sql = "SELECT * FROM usuario";
			logger.log(Level.INFO, "Statement: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while( rs.next() ) { 
				String nombre = rs.getString("nombre");
				String contrasena = rs.getString("contrasena");
				int dinero = rs.getInt("dinero");
				dev.add( new Usuario( nombre, contrasena, dinero) );
			}
			rs.close();
			stmt.close();
			conn.close(); 
			return dev;
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			e.printStackTrace();
			}
			return null;
		
	}
	
	public Usuario buscarUsuario(String nombredb, String nombre) {
		String sql = "select * from usuario where nombre = '" + nombre + "'";
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/db/"+nombredb);
			Statement stmt = conn.createStatement();
			logger.log(Level.INFO, "Lanzada consulta a base de datos: " + sql);
			ResultSet rs = stmt.executeQuery( sql );
			if (rs.next()) {
				Usuario usuario = new Usuario(
					rs.getString("nombre"), rs.getString("contrasena"), rs.getInt("dinero") );
				rs.close();
				return usuario;
			} else {
				rs.close();
				return null;
			}
		} catch (SQLException e) {
			logger.log( Level.SEVERE, "Error en búsqueda de base de datos: " + sql, e );
			return null;
		}
	}
	
	public void guardarDBCompra(String nombredb, Usuario usuario, int producto, int precio, String fecha){
		try {
	
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/db/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("INSERT INTO compras VALUES ('%s', %d, %d, '%s')", usuario.getNombre(), producto, precio, fecha);
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
	
	}

	
	public void cambiarContrasena(String nombredb,String nombre, String contrasena, String nuevaContra){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/db/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("UPDATE usuario SET contrasena = '"+nuevaContra+"' where nombre = '"+ nombre+"' AND contrasena= '"+contrasena+"';");
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
		
	}

	public void cambiarDinero(String nombredb,String nombre, int newDinero, int dineroActual){
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:src/db/"+nombredb);
			Statement stmt = conn.createStatement();
			String sql = String.format("UPDATE usuario SET dinero = '"+(newDinero + dineroActual)+"' where nombre = '"+ nombre+"';");
			logger.log(Level.INFO, "Statement: " + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close(); 
			} catch (SQLException e) {
			System.out.println("No se ha podido cargar el driver de la base de datos");
			}
	
	}
}
