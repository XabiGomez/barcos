import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clases.Extras;
import clases.TipoExtras;
import ventanas.VentanaExtras;

public class TestExtras {
	
	public VentanaExtras vent;
	public List<Extras> extrasList;

	@Before
	public void setUp() throws Exception {
		vent = new VentanaExtras();
		extrasList = VentanaExtras.extras;
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLeerCSV() throws IOException {
			assertNotNull(extrasList);
	        assertEquals(10, extrasList.size());

	        assertEquals(TipoExtras.VELA, extrasList.get(0).getTipo());
	        assertEquals(153, extrasList.get(0).getId());
	        assertEquals(250, extrasList.get(0).getCompra());
	        assertEquals(175, extrasList.get(0).getVenta());
	}


}
