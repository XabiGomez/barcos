package ventanas;

import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import clases.Usuario;
import db.DB;


public class VentanaRegistro extends JFrame{
	private static final long serialVersionUID = 1L;
	public static Usuario usuarioRegistrado;
	private static VentanaRegistro vent = new VentanaRegistro();
	private VentanaPrincipal ventanaPrincipal;

	public VentanaRegistro() {
		DB db = new DB();
		db.init("usuario.db"); 
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
		
		iniciarSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean saltaError = true;
				ArrayList<Usuario> listau = db.sacarUsuarios("usuario.db");
				if (textoNombre.getText().isBlank() || textoContrasena.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Por favor ingrese sus credenciales", "Iniciar sesion - Error",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					for (Usuario u : listau) {
						if (u.getNombre().equals(textoNombre.getText())
								&& u.getContrasena().equals(textoContrasena.getText())) {
							saltaError = false;
							vent.setVisible(false);
							usuarioRegistrado = u;
							 if (ventanaPrincipal == null) {
			                        ventanaPrincipal = new VentanaPrincipal();
			                        ventanaPrincipal.setLocationRelativeTo(null);
			                        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			                        ventanaPrincipal.setResizable(false);
			                    }

			                    ventanaPrincipal.setVisible(true);

			                    vent.setVisible(false);
			                    dispose();
							
						}	
					}
					if (saltaError == true) {
					JOptionPane.showMessageDialog(null, "Las credenciales introducidas son erroneas", "Iniciar sesion - Error",
							JOptionPane.INFORMATION_MESSAGE);	
					}
				}

			}
		});

		registrar.addActionListener(new ActionListener() {

			@SuppressWarnings("unlikely-arg-type")
			@Override
			public void actionPerformed(ActionEvent hg) {
				ArrayList<Usuario> listau = db.sacarUsuarios("usuario.db");
				String nombreUsuario = textoNombre.getText();
				
				if (nombreUsuario.isBlank() || textoContrasena.getText().isBlank()) {
					JOptionPane.showMessageDialog(null,
							"Por favor ponga el nombre de usuario que desea crear y/o su contraseña",
							"Crear usuario nuevo - Error", JOptionPane.INFORMATION_MESSAGE);
				} else if (existeUsuario(listau, nombreUsuario)) {
					JOptionPane.showMessageDialog(null, "Este usuario ya existe", "Crear usuario nuevo - Error",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					int opcion = JOptionPane.showConfirmDialog(null,
							"�Estas seguro de que quieres crear un usuario con nombre: " + nombreUsuario
									+ " y contrase�a: " + textoContrasena.getText() + "?",
							"Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (opcion == 0) {
						db.guardarUsuario("usuario.db", nombreUsuario, textoContrasena.getText(), 0);
					}
					if (opcion == 1) {

					}

				}

			}
			
			private boolean existeUsuario(ArrayList<Usuario> listaUsuarios, String nombreUsuario) {
		        for (Usuario usuario : listaUsuarios) {
		            if (usuario.getNombre().equals(nombreUsuario)) {
		                return true; 
		            }
		        }
		        return false; 
		    }
		});
	
		
	}
	
}
