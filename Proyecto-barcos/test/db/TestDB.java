package db;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clases.Usuario;

public class TestDB {

	 private static final String NOMBRE_DB_TEST = "test.db";
	    private DB db;

	    @Before
	    public void setUp() {
	        db = new DB();
	        db.init(NOMBRE_DB_TEST);
	    }
	    
	    @After
	    public void tearDown() {
	        File file = new java.io.File("src/db/" + NOMBRE_DB_TEST);
	        file.delete();
	    }

	    @Test
	    public void testBuscarUsuario() {
	        String nombre = "testusuario";
	        String contrasena = "tcontra";
	        int dinero = 100;
	        db.guardarUsuario(NOMBRE_DB_TEST, nombre, contrasena, dinero);
	        Usuario usuario = db.buscarUsuario(NOMBRE_DB_TEST, nombre);

	        assertNotNull(usuario);
	        assertEquals(nombre, usuario.getNombre());
	        assertEquals(contrasena, usuario.getContrasena());
	        assertEquals(dinero, usuario.getDinero());
	    }

	    @Test
	    public void testSacarUsuarios() {
	        db.guardarUsuario(NOMBRE_DB_TEST, "usuario1", "contra", 50);
	        db.guardarUsuario(NOMBRE_DB_TEST, "usuario2", "oculto", 75);
	        var usuarios = db.sacarUsuarios(NOMBRE_DB_TEST);

	        assertNotNull(usuarios);
	        assertTrue(usuarios.size() >= 2);
	        boolean usuario1Encontrado = false;
	        boolean usuario2Encontrado = false;

	        for (Usuario usuario : usuarios) {
	            if ("usuario1".equals(usuario.getNombre())) {
	                assertEquals(50, usuario.getDinero());
	                usuario1Encontrado = true;
	            } else if ("usuario2".equals(usuario.getNombre())) {
	                assertEquals("oculto", usuario.getContrasena());
	                usuario2Encontrado = true;
	            }
	        }
	        assertTrue(usuario1Encontrado);
	        assertTrue(usuario2Encontrado);
	    }
	   
}
