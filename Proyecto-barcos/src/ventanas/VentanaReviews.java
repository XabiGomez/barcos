package ventanas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class VentanaReviews extends JFrame {
	private static final long serialVersionUID = 1L;
    private JComboBox<String> reviewComboBox;
    private JTextArea reviewTextArea;
    private JButton atras;
    
    
    public VentanaReviews() {
        setTitle("Reviews");
        setLocation(600,250);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] reviews = { "Lurssen kismet", "Nitro ZV21", "Nautiraid Umiak", "Marinello eden20", "Halbergrassy 50" };
        reviewComboBox = new JComboBox<>(reviews);
        reviewComboBox.addActionListener(e -> showReview());
        reviewTextArea = new JTextArea(10, 30);
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        reviewTextArea.setEditable(false);
        
        JPanel panelInf = new JPanel();
		panelInf.setLayout(new BorderLayout());
		getContentPane().add( panelInf, BorderLayout.SOUTH );
		atras = new JButton("Atras");
		panelInf.add(atras);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Selecciona una review"));
        panel.add(reviewComboBox);
        panel.add(new JScrollPane(reviewTextArea));
        add(panel);
        setVisible(true);
        
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
    }
  
    private void showReview() {

        String reviews = (String) reviewComboBox.getSelectedItem();
        
    
        switch (reviews) {
        case "Lurssen kismet":
            reviewTextArea.setText("Entre las lujosas características del yate se encuentran una pared de video de dos cubiertas, una chimenea abierta, un spa, una piscina de forma ovalada y un jacuzzi en la terraza.");
            break;
        case "Nitro ZV21":
            reviewTextArea.setText("Muchas veces en la vida tenemos que comprometernos para que las cosas sucedan. Pero cuando se trata de una gran agua, barco de pesca multi-especie hay momentos en que el compromiso simplemente no va a hacer. Y ahí es cuando es el momento de un equipo de torneos serio como el Nitro ZV21."); 
            break;
        case "Nautiraid Umiak":
            reviewTextArea.setText("Su dinámica es igual a la de las canoas rígidas sobre el agua gracias a su estructura de aluminio 7070 muy rígida. Estable pero fácil de maniobrar, sus estabilizadores dan una flotabilidad extra y líneas elegantes. El nuevo color arena hace un barco muy bonito en el agua."); 
            break;
        case "Marinello eden20":
            reviewTextArea.setText("El Eden 20 continúa la serie con dirección lateral, mucho espacio y total seguridad.\r\n"
            		+ "\r\n"
            		+ "Puede ser alimentado por un \"actualizado\" de 40 CV, que no requiere licencia de navegación, hasta una potencia máxima de 150 CV.");
            break;
        case "Halbergrassy 50":
            reviewTextArea.setText("El Hallberg-Rassy 50, la última de la reconocida marca sueca, es casi una obra maestra. German Frers ha espolvoreado una vez más su polvo de hadas sobre el diseño, conjurando una forma moderna del casco que te sorprenderá continuamente con el volumen que proporciona, pero con suficiente carácter y credenciales de navegación pura sangre para hacerlo identificable como uno de una línea familiar que abarca cinco décadas.");
            break;
            default:
                reviewTextArea.setText("");
                
        }
    }

}
