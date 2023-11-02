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
import javax.swing.JToolBar;

public class VentanaAjustes extends JFrame{
	private static final long serialVersionUID = 1L;
	private JToolBar barra;
	
	public VentanaAjustes() {
		setLocation(550, 150);
		setSize(500,500);
		setTitle("Ajustes");
		
		barra = new JToolBar();
		
		JButton atras = new JButton("Atras");
		barra.add(atras);
		
		JPanel panelSup = new JPanel();
		panelSup.setLayout(new BorderLayout());
		panelSup.add(barra, BorderLayout.NORTH);
		getContentPane().add( panelSup, BorderLayout.NORTH );
		
		JPanel panelCentral = new JPanel();
		getContentPane().add( panelCentral, BorderLayout.CENTER );
		panelCentral.setLayout(null);
		
		JLabel lblAjustes = new JLabel("Ajustes");
		lblAjustes.setBounds(205, 20, 133, 31);
		lblAjustes.setFont(new Font("Tahoma", Font.BOLD, 25));
		panelCentral.add(lblAjustes);
		
		JButton bInfo = new JButton("Nosotros");
		bInfo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		bInfo.setBounds(150, 100, 200, 37);
		panelCentral.add(bInfo);
		
		JButton bPerfil = new JButton("Perfil");
		bPerfil.setFont(new Font("Tahoma", Font.PLAIN, 17));
		bPerfil.setBounds(150, 180, 200, 37);
		panelCentral.add(bPerfil);
		
		JButton btnCerrarSesion = new JButton("Cerrar sesion");
		btnCerrarSesion.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnCerrarSesion.setBounds(150, 260, 200, 37);
		panelCentral.add(btnCerrarSesion);
		
		atras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal vp = new VentanaPrincipal();
				vp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				vp.setLocationRelativeTo(null);
				vp.setVisible(true);
				dispose();
			}
		});
		
		bInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Creado por:\n- Xabier Gomez\n- Alvaro Martinez");
					}
				});
		
		btnCerrarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaRegistro vr = new VentanaRegistro();
				vr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				vr.setLocationRelativeTo(null);
				vr.setVisible(true);
				dispose();
			}
		});
	}
}

	



