package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import clases.Extras;
import clases.TipoExtras;


public class VentanaExtras extends JFrame {
	private static final long serialVersionUID = 1L;
	private JToolBar barra;
	private JToolBar barraAbajo;
	public static List<Extras> extras;
	private JTable tablaExtras;
	private DefaultTableModel modeloDatosExtras;

	int mouseRow = -1;
	int mouseCol = -1;
	
	
	public VentanaExtras() {
		
		extras = new ArrayList<Extras>();		
		
		leerCSV();
		iniciarTabla();
		cargarExtras();
		
		setTitle("Extras");
		setSize(700,500);
		JScrollPane scrollPaneRepuestos = new JScrollPane(this.tablaExtras);
		scrollPaneRepuestos.setBorder(new TitledBorder("Extras"));
		this.tablaExtras.setFillsViewportHeight(true);
		this.getContentPane().setLayout(new GridLayout(2, 1));
		this.getContentPane().add(scrollPaneRepuestos);
		
		barra = new JToolBar();
		barraAbajo = new JToolBar();
		
		JPanel panelSup = new JPanel();
		panelSup.setLayout(new BorderLayout());
		getContentPane().add( panelSup, BorderLayout.NORTH );
		
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(new BorderLayout());
		getContentPane().add( panelSouth, BorderLayout.SOUTH );
		
		
		JButton atras = new JButton("Atras");
		JButton comprar = new JButton("Comprar");
		JButton vender = new JButton("Vender");
		
		barra.add(comprar);
		barra.add(vender);
		barraAbajo.add(atras);
	
		panelSup.add(barra, BorderLayout.NORTH);
		panelSouth.add(barraAbajo, BorderLayout.SOUTH);
		panelSup.add(scrollPaneRepuestos, BorderLayout.CENTER);
		
		TableCellRenderer renderMouseOver = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel label = new JLabel(value.toString());
			
			if (row == this.mouseRow && column == this.mouseCol) {
				label.setBackground(Color.YELLOW);
			} else {
				label.setBackground(table.getBackground());
			}
			
			if (isSelected) {
				label.setBackground(table.getSelectionBackground());
				label.setForeground(table.getSelectionForeground());
			}
			
			label.setOpaque(true);
				
			return label;
		};
		
		this.tablaExtras.setDefaultRenderer(Object.class, renderMouseOver);
		
		
		this.tablaExtras.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseRow = tablaExtras.rowAtPoint(e.getPoint());
				mouseCol = tablaExtras.columnAtPoint(e.getPoint());

				if (mouseRow > -1 && mouseCol >-1) {
					tablaExtras.repaint();
				}
			}
		});

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
		

	private void iniciarTabla() {

		Vector<String> cabeceraExtras = new Vector<String>(Arrays.asList("ID", "Tipo", "Compra", "Venta"));
		this.modeloDatosExtras = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraExtras) {
		   @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; 
	        }
	    };
		this.tablaExtras = new JTable(this.modeloDatosExtras);
		this.tablaExtras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}
	
	private void cargarExtras() {
		Collections.sort(extras);
		this.modeloDatosExtras.setRowCount(0);
		
		for (Extras e : extras) {
			this.modeloDatosExtras.addRow( new Object[] {e.getId(), e.getTipo(), e.getCompra(), e.getVenta()} );
		}		
	}
	
	
	public void leerCSV() {
		String linea = "";
		
		try(BufferedReader br = new BufferedReader(new FileReader("data/extras.csv"))) {
			
			while((linea=br.readLine())!=null) {
				
				String[] values = linea.split(",");
			
				TipoExtras tipo = TipoExtras.valueOf(values[0]);
				int id = Integer.parseInt(values[1]);
				int compra = Integer.parseInt(values[2]);
				int venta = Integer.parseInt(values[3]);
				//String url = values[4];
				
				Extras e = new Extras(id,tipo,compra,venta);
				extras.add(e);
				
			}
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	

}
