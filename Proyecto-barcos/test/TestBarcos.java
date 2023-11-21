import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clases.Barco;
import clases.MarcaBarco;
import clases.TipoBarco;
import ventanas.VentanaBarcos;


public class TestBarcos {
	public VentanaBarcos vent;
	public List<Barco> extrasList;

	@Before
	public void setUp() throws Exception {
		vent = new VentanaBarcos();
		extrasList = VentanaBarcos.barcos;
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLeerCSV() throws IOException {
			assertNotNull(extrasList);
	        assertEquals(8, extrasList.size());

	        assertEquals(TipoBarco.VELERO, extrasList.get(0).getTipo());
	        assertEquals(1, extrasList.get(0).getId());
	        assertEquals(MarcaBarco.HALLBERGRASSY, extrasList.get(0).getMarca());
	        assertEquals("43MkI", extrasList.get(0).getModelo());
	        assertEquals(35000, extrasList.get(0).getPrecio());
	}
}
