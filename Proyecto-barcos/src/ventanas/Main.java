package ventanas;

import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistro vent = new VentanaRegistro();
					//VentanaPrincipal vent = new VentanaPrincipal();
					vent.setVisible(true);
					vent.setLocation(550,150);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}

}
