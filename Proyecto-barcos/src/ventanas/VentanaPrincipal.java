package ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public class VentanaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JPanel panelBotones;
	private JToolBar barra;
	
	public VentanaPrincipal() {
		setTitle("The Boat Shop");
		setSize(500, 600);
		setLocation(550, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JButton bCesta = new JButton(new ImageIcon(getScaledImage(new ImageIcon("img/cesta.png").getImage(), 30, 30)));
        bCesta.setPreferredSize(new Dimension(30, 30));
        bCesta.addActionListener(e -> {
        	VentanaCesta vc = new VentanaCesta();
			vc.setLocationRelativeTo(null);
			vc.setVisible( true );
			vc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			vc.setResizable(false);
			dispose();	
        });

        JButton bAjustes = new JButton("Ajustes");
        JButton bBar = new JButton("Barcos");
        JButton bExtras = new JButton("Extras");
        JButton bRev = new JButton("Reviews");

        panelBotones = new JPanel(new GridLayout(2, 2, 20, 20));
        panelBotones.setBorder(new EmptyBorder(20, 20, 20, 20)); 
        
        panelBotones.add(bBar);
        panelBotones.add(bExtras);
        panelBotones.add(bRev);
        panelBotones.add(bAjustes);
        
        barra = new JToolBar();
        barra.add(bCesta);
        
        //JPanel panelEste = new JPanel(new BorderLayout());
        //panelEste.add(barra, BorderLayout.NORTH);
        
        panel.add(barra, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);
        panel.add(Box.createVerticalStrut(30), BorderLayout.SOUTH);

        add(panel);
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
	
	private Image getScaledImage(Image srcImg, int width, int height) {
        return srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
	


}
