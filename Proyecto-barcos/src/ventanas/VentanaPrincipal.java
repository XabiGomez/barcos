package ventanas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class VentanaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel panelSup;
	private JToolBar barra;
	
	public VentanaPrincipal() {
		setTitle("The Boat Shop");
		setSize(500, 600);
		setLocation(550, 150);
		
		panelSup = new JPanel();
		panelSup.setLayout(new BorderLayout());
		
		barra = new JToolBar();
		
		JButton bAjustes = new JButton("Ajustes");
		barra.add(bAjustes);
		
		
		JButton bBar = new JButton("Barcos");
		barra.add(bBar);
		
		bBar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaBarcos ventBar = new VentanaBarcos();
				ventBar.setLocationRelativeTo(null);
				ventBar.setVisible( true );
				ventBar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ventBar.setResizable(false);
				dispose();	
			}
			
			
		});
		
		JButton bExtras = new JButton("Extras");
		barra.add(bExtras);
		
		panelSup.add(barra, BorderLayout.NORTH);
		add(panelSup);
		
		bExtras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaExtras ventEx = new VentanaExtras();
				ventEx.setLocationRelativeTo(null);
				ventEx.setVisible( true );
				ventEx.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ventEx.setResizable(false);		
				dispose();
				
			}
		});
		
		bAjustes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaAjustes ventAjus = new VentanaAjustes();
				ventAjus.setLocationRelativeTo(null);
				ventAjus.setVisible( true );
				ventAjus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ventAjus.setResizable(false);
				dispose();
			}
			
		});
		
		JButton bRev = new JButton("Reviews");
		barra.add(bRev);
		
		panelSup.add(barra, BorderLayout.NORTH);
		add(panelSup);
		
		bRev.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaReviews ventRe = new VentanaReviews();
				ventRe.setLocationRelativeTo(null);
				ventRe.setVisible( true );
				ventRe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ventRe.setResizable(false);		
				dispose();
				
			}
		});
	}
	


}
