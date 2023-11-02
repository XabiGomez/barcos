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
    
    /**The window builder.
     * 
     */
    public VentanaReviews() {
        setTitle("Reviews");
        setLocation(600,250);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] reviews = { "Barco A", "Barco B", "Barco C", "Barco D", "Barco E" };
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
        case "Barco A":
            reviewTextArea.setText("Es un velero impresionante con un diseño elegante y un alto rendimiento en el agua.");
            break;
        case "Barco B":
            reviewTextArea.setText("Es un yate de lujo que ofrece comodidad y sofisticación en cada travesía."); 
            break;
        case "Barco C":
            reviewTextArea.setText("Es un barco de pesca robusto y confiable que ha demostrado su valía en mares agitados."); 
            break;
        case "Barco D":
            reviewTextArea.setText("Es un catamarán perfecto para navegaciones familiares, con amplios espacios y comodidades.");
            break;
        case "Barco E":
            reviewTextArea.setText("Es un barco de vela clásico que evoca la nostalgia de la navegación tradicional.");
            break;
            default:
                reviewTextArea.setText("");
                
        }
    }

}
