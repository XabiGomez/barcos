package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import clases.Barco;
import clases.Extras;
import db.DB;


public class VentanaCesta extends JFrame {
	private static final long serialVersionUID = 1L;
	public static List<Barco> barcosUsu;
	private static List<Extras> extrasUsu;
	private JToolBar botonesSup;
	private JToolBar botonesInf;
    private DefaultTableModel modeloCesta;
    private JTable tablaCesta;
	
	
	
	public VentanaCesta() {	
		barcosUsu = new ArrayList<>();
		extrasUsu = new ArrayList<>();
		VentanaRegistro vr = new VentanaRegistro();
		VentanaBarcos vb = new VentanaBarcos();
		VentanaExtras ve = new VentanaExtras();
		DB db = new DB();

		
		for (Barco b: vb.barcos) {
			 for (int i = 0; i< db.sacarIdsUsuario("usuario.db", vr.usuarioRegistrado).size(); i++) {
				if (db.sacarIdsUsuario("usuario.db", vr.usuarioRegistrado).get(i) == b.getId()) {
					barcosUsu.add(b);
				}
			}
		}
		
		for (Extras e: ve.extras) {
			 for (int i = 0; i< db.sacarIdsUsuario("usuario.db", vr.usuarioRegistrado).size(); i++) {
				if (db.sacarIdsUsuario("Usuario.db", vr.usuarioRegistrado).get(i) == e.getId()) {
					extrasUsu.add(e);
				}
			}
		}
		
		initTablaCesta();

	    cargarCesta(barcosUsu, extrasUsu); 
		
		
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setTitle("Cesta");
		setSize(700, 700);
		JScrollPane scrollPane = new JScrollPane(tablaCesta);
		scrollPane.setBorder(new TitledBorder("Cesta"));
		this.tablaCesta.setFillsViewportHeight(true);
		getContentPane().setLayout(new GridLayout(2, 1));
		getContentPane().add(scrollPane);
		botonesInf = new JToolBar();
	
		JPanel panelInf = new JPanel();
		panelInf.setLayout(new BorderLayout());
		getContentPane().add( panelInf, BorderLayout.SOUTH );
		
		panelInf.add(botonesInf,BorderLayout.SOUTH);
			
		JButton botonv = new JButton("Atras");
		botonesInf.add(botonv);
		botonv.addActionListener( new ActionListener() {
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
	
	
	 private void initTablaCesta() {
		 Vector<String> cabeceras = new Vector<>(Arrays.asList("ID", "Tipo", "Marca/Compra", "Precio/Venta"));
		    this.modeloCesta = new DefaultTableModel(new Vector<>(), cabeceras) {
		        @Override
		        public boolean isCellEditable(int row, int column) {
		            return false;
		        }
		    };
		    this.tablaCesta = new JTable(this.modeloCesta);
		    this.tablaCesta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    }

	  private void cargarCesta(List<Barco> barcos, List<Extras> extras) {
		  this.modeloCesta.setRowCount(0);

	        if (barcos != null && !barcos.isEmpty()) {
	            for (Barco b : barcos) {
	                this.modeloCesta.addRow(new Object[]{b.getId(), b.getTipo(), b.getMarca(), b.getPrecio()});
	            }
	        }

	        if (extras != null && !extras.isEmpty()) {
	            for (Extras e : extras) {
	                this.modeloCesta.addRow(new Object[]{e.getId(), e.getTipo(), e.getCompra(), e.getVenta()});
	            }
	        }
	    }
	

}
