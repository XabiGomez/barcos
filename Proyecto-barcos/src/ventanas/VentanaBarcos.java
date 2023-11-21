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

import clases.Barco;
import clases.MarcaBarco;
import clases.TipoBarco;

public class VentanaBarcos extends JFrame {
	private static final long serialVersionUID = 1L;
	public static List<Barco> barcos;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JToolBar botonesSup;
	private JToolBar botonesInf;
	
	int mouseRow = -1;
	int mouseCol = -1;
	
	public VentanaBarcos() {
		barcos= new ArrayList<Barco>();
		leerCSV();
		initTabla();
		cargarBarcos();
		
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setTitle("Barcos");
		setSize(700, 500);
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBorder(new TitledBorder("Barcos"));
		tabla.setFillsViewportHeight(true);
		getContentPane().setLayout(new GridLayout(2, 1));
		getContentPane().add(scrollPane);
		botonesSup = new JToolBar();
		botonesInf = new JToolBar();
		
		JPanel panelSup = new JPanel();
		panelSup.setLayout(new BorderLayout());
		getContentPane().add( panelSup, BorderLayout.NORTH );
		
		JPanel panelInf = new JPanel();
		panelInf.setLayout(new BorderLayout());
		getContentPane().add( panelInf, BorderLayout.SOUTH );
		
		panelSup.add(scrollPane,BorderLayout.CENTER);
		panelInf.add(botonesInf,BorderLayout.SOUTH);
		panelSup.add(botonesSup,BorderLayout.NORTH);
		
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
		
		this.tabla.setDefaultRenderer(Object.class, renderMouseOver);
		
		
		this.tabla.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseRow = tabla.rowAtPoint(e.getPoint());
				mouseCol = tabla.columnAtPoint(e.getPoint());

				if (mouseRow > -1 && mouseCol >-1) {
					tabla.repaint();
				}
			}
		});
		
		JButton botoncomp = new JButton("Comprar");
		botonesSup.add(botoncomp);
		
		JButton atras = new JButton("Atras");
		botonesInf.add(atras);
		atras.addActionListener( new ActionListener() {
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
	
	
	private void initTabla() {

		Vector<String> cabeceras = new Vector<String>(Arrays.asList( "ID", "TIPO", "MARCA", "MODELO","PRECIO"));
		modelo = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras) {
			   @Override
		        public boolean isCellEditable(int row, int column) {
		            return false;
		        }
		    };
		
		tabla = new JTable(modelo);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void cargarBarcos() {
		Collections.sort(barcos);
		modelo.setRowCount(0);
		
		for (Barco b : barcos) {
			this.modelo.addRow( new Object[] {b.getId(), b.getTipo(), b.getMarca(), b.getModelo(), b.getPrecio()} );
		}		
	}
	
	
	public void leerCSV() {
		String linea = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader("data/barcos.csv"))) {
			while((linea=br.readLine())!=null) {
				
				
				String[] values = linea.split(",");
				//System.out.println(linea);
			
				TipoBarco tipo = TipoBarco.valueOf(values[0]);
				MarcaBarco marca = MarcaBarco.valueOf(values[1]);
				String modelo = values[2];
				int id = Integer.parseInt(values[3]);
				int precio = Integer.parseInt(values[4]);
				//String url = values[5];
				
				
				Barco b = new Barco(tipo,marca, modelo, id, precio);
				barcos.add(b);
				//System.out.println(b);
			}
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		} 
	}
	
	
}
