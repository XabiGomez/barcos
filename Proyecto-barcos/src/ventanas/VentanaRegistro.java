package ventanas;

import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//Todavia no es funcional hasta que no este implementado la bsa de datos de usuarios

public class VentanaRegistro extends JFrame{
	private static final long serialVersionUID = 1L;

	public VentanaRegistro() {
		setSize(500, 600);
		this.setTitle("The Boat Shop");
		
		JPanel panelCentral = new JPanel();
		getContentPane().add( panelCentral, BorderLayout.CENTER );
		panelCentral.setLayout(null);
		
		JLabel nuFoto = new JLabel();
		try {
			Image img = ImageIO.read(getClass().getResource("logoBarco.PNG"));
			int anchoEscalado = 250;
	        int altoEscalado = 250;
	        Image imgEscalada = img.getScaledInstance(anchoEscalado, altoEscalado, Image.SCALE_SMOOTH);
	        
	        nuFoto.setIcon(new ImageIcon(imgEscalada));
			int anchoImagen = img.getWidth(null);
	        int altoImagen = img.getHeight(null);
	        
	        nuFoto.setBounds(130, -55, anchoImagen, altoImagen);
		} catch (Exception ex) {
			System.out.println(ex);	
		}
		panelCentral.add(nuFoto);
			
		JLabel huecoNombre = new JLabel("Usuario: ");
		huecoNombre.setBounds(100, 275, 200, 31);
		panelCentral.add(huecoNombre);
		
		JTextField textoNombre = new JTextField();
		textoNombre.setBounds(190, 275, 200, 31);
		panelCentral.add(textoNombre);
		
		JLabel huecoContrasena = new JLabel("Contrasena: ");
		huecoContrasena.setBounds(100, 325, 200, 31);
		panelCentral.add(huecoContrasena);
		
		JTextField textoContrasena = new JPasswordField();
		textoContrasena.setBounds(190, 325, 200, 31);
		panelCentral.add(textoContrasena);
				
		JButton iniciarSesion = new JButton("Iniciar sesion");
		iniciarSesion.setFont(new Font("Tahoma", Font.PLAIN, 17));
		iniciarSesion.setBounds(150, 390, 200, 37);
		panelCentral.add(iniciarSesion);
		
		JButton registrar = new JButton("Registrarse");
		registrar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		registrar.setBounds(150, 450, 200, 37);
		panelCentral.add(registrar);
	}
}
