package ventanas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import db.DB;

public class VentanaCuenta extends JFrame {
	
	private JToolBar barra;
	
	public VentanaCuenta() {
		VentanaRegistro vr = new VentanaRegistro();
		System.out.println(vr.usuarioRegistrado.getNombre());
		
		DB db = new DB();
		setSize(500, 600);
		barra = new JToolBar();
		
		JButton bAtras = new JButton("Atras");
		barra.add(bAtras);
		
		JPanel panelSup = new JPanel();
		panelSup.setLayout(new BorderLayout());
		panelSup.add(barra, BorderLayout.NORTH);
		getContentPane().add( panelSup, BorderLayout.NORTH );
		
		JPanel panelCentral = new JPanel();
		getContentPane().add( panelCentral, BorderLayout.CENTER );
		panelCentral.setLayout(null);
		
		JButton btnBorrar = new JButton("Borrar cuenta");
		btnBorrar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnBorrar.setBounds(150, 400, 200, 37);
		panelCentral.add(btnBorrar);
		
		JButton btnContrasena = new JButton("Cambiar contrasena");
		btnContrasena.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnContrasena.setBounds(150, 280, 200, 37);
		panelCentral.add(btnContrasena);
		
		JButton btnDinero = new JButton("Añadir dinero");
		btnDinero.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnDinero.setBounds(150, 340, 200, 37);
		panelCentral.add(btnDinero);
		
		JLabel lblCuenta = new JLabel("Tu cuenta");
		lblCuenta.setBounds(180, 20, 133, 31);
		lblCuenta.setFont(new Font("Tahoma", Font.BOLD, 25));
		panelCentral.add(lblCuenta);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNombre.setBounds(100, 100, 96, 14);
		panelCentral.add(lblNombre);
		
		JTextField textNombre = new JTextField();
		textNombre.setEditable(false);
		textNombre.setBounds(250, 100, 96, 20);
		textNombre.setText(vr.usuarioRegistrado.getNombre());
		panelCentral.add(textNombre);

		JLabel lblContrasena = new JLabel("Contrasena");
		lblContrasena.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblContrasena.setBounds(100, 150, 96, 14);
		panelCentral.add(lblContrasena);
		
		JTextField textContrasena = new JTextField();
		textContrasena.setEditable(false);
		textContrasena.setBounds(250, 150, 96, 20);
		textContrasena.setText(vr.usuarioRegistrado.getContrasena());
		panelCentral.add(textContrasena);

		JLabel lblDinero = new JLabel("Dinero");
		lblDinero.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDinero.setBounds(100, 200, 96, 14);
		panelCentral.add(lblDinero);
		JTextField textDinero = new JTextField();
		textDinero.setEditable(false);
		textDinero.setBounds(250, 200, 96, 20);
		textDinero.setText(Integer.toString(vr.usuarioRegistrado.getDinero()) + " €");
		panelCentral.add(textDinero);

		
		bAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaAjustes va = new VentanaAjustes();
				va.setLocationRelativeTo(null);
				va.setVisible( true );
				va.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				va.setResizable(false);		
				dispose();
			}
		});
		
		btnBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				db.borrarDBUsuario("usuario.db", vr.usuarioRegistrado.getNombre());
				
				VentanaRegistro vr = new VentanaRegistro();
				vr.setLocationRelativeTo(null);
				vr.setVisible( true );
				vr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				vr.setResizable(false);		
				dispose();
			}
		});
		
		btnContrasena.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres cambiar tu contrasena?");

		        if (result == 0) {
		        	System.out.println("Has confirmado");
		        	String newContrasena = JOptionPane.showInputDialog(null, "Introduzca su nueva contrasena:");

		        	db.cambiarContrasena("usuario.db", vr.usuarioRegistrado.getNombre(), vr.usuarioRegistrado.getContrasena(), newContrasena);
		        	vr.usuarioRegistrado.setContrasena(newContrasena);
		        	
		        	System.out.println("Su nueva contrasena es: "+ newContrasena);
		        	textContrasena.setText(newContrasena);
		        }  
		        else
		            System.out.println("Has pulsado NO");
			}
		});
		
		btnDinero.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		        String monedero = JOptionPane.showInputDialog(null, "Introduzca una cantidad:");
		        db.cambiarDinero("usuario.db", vr.usuarioRegistrado.getNombre(), Integer.parseInt(monedero), db.buscarUsuario("usuario.db", vr.usuarioRegistrado.getNombre()).getDinero());
		        vr.usuarioRegistrado.setDinero(db.buscarUsuario("usuario.db", vr.usuarioRegistrado.getNombre()).getDinero());
		        textDinero.setText(db.buscarUsuario("usuario.db", vr.usuarioRegistrado.getNombre()).getDinero() + " €");
			}
		});
		
	}
}
