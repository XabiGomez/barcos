package ventanas;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import db.DB;

public class VentanaBarcos extends JFrame {
	private static final long serialVersionUID = 1L;
	public static List<Barco> barcos;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JToolBar botonesSup;
	private JToolBar botonesInf;
	private static VentanaRegistro vr = new VentanaRegistro();
	
	int mouseRow = -1;
	int mouseCol = -1;
	
	public VentanaBarcos() {
		DB db = new DB();
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
			 JPanel panel = new JPanel(new BorderLayout());
			 JLabel label = new JLabel(value.toString());

			    if (row == this.mouseRow) {
			        panel.setBackground(Color.YELLOW);
			    } else {
			        panel.setBackground(table.getBackground());
			    }

			    if (isSelected) {
			        panel.setBackground(table.getSelectionBackground());
			        panel.setForeground(table.getSelectionForeground());
			    }

			    panel.setOpaque(true);
			    panel.add(label, BorderLayout.CENTER);

			    return panel;
		};
		
		this.tabla.setDefaultRenderer(Object.class, renderMouseOver);
		
		
		this.tabla.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseRow = tabla.rowAtPoint(e.getPoint());

				if (mouseRow > -1) {
					tabla.repaint();
				}
			}
		});
		  
		JButton botoncomp = new JButton("Comprar");
		botonesSup.add(botoncomp);
		botoncomp.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabla.getSelectedRow() >= 0) {
					if (vr.usuarioRegistrado.getDinero() >= barcos.get(tabla.getSelectedRow()).getPrecio()) {
						String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
						db.guardarDBCompra("usuario.db", vr.usuarioRegistrado, barcos.get(tabla.getSelectedRow()).getId(), barcos.get(tabla.getSelectedRow()).getPrecio(), date);
						vr.usuarioRegistrado.setDinero(vr.usuarioRegistrado.getDinero()-barcos.get(tabla.getSelectedRow()).getPrecio());
						JOptionPane.showMessageDialog(null, "Compra añadida correctamente a tu inventario.");
						} else {
						JOptionPane.showMessageDialog(null, "No tienes suficiente dinero para realizar esta compra");
					}
				}
			}
		});		
		
		JButton bCesta = new JButton(new ImageIcon(getScaledImage(new ImageIcon("img/cesta.png").getImage(), 30, 30)));
		botonesSup.add(bCesta);
        bCesta.setPreferredSize(new Dimension(30, 30));
        bCesta.addActionListener(e -> {
        	VentanaCesta vc = new VentanaCesta();
			vc.setLocationRelativeTo(null);
			vc.setVisible( true );
			vc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			vc.setResizable(false);
			dispose();	
        });
			
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
		
		JButton btnPosiblesCompras = new JButton("Posibles compras");
		botonesInf.add(btnPosiblesCompras);
		btnPosiblesCompras.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String resp = JOptionPane.showInputDialog(null, "¿Cuanto dinero quieres gastarte?:");
					if (resp==null) return;
					int dinero = Integer.parseInt( resp );
					calcularComprasPosibles( dinero );
				} catch (NumberFormatException e2) { 
					JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
				}
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
	
	
	private void calcularComprasPosibles(int disponible) {
		ArrayList<Barco> lBarcos = new ArrayList<>();
		calcularComprasPosibles(barcos, disponible, lBarcos);
	}
	private void calcularComprasPosibles(List<Barco> listBarcos, int restante, ArrayList<Barco> lComprado) {
	    StringBuilder mensajes = new StringBuilder();

	    if (restante < 0) return;
	    else if (restante < 50) {
	    	String mensaje = "Posible compra (sobran " + String.format("%d", restante) + " euros): " + lComprado;
		    JOptionPane.showMessageDialog(null, mensaje, "Posibles Compras", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        for (Barco b : listBarcos) {
	            lComprado.add(b);
	            calcularComprasPosibles(listBarcos, restante - b.getPrecio(), lComprado);
	            lComprado.remove(lComprado.size() - 1);
	        }
	    }
	    
	    if (mensajes.length() > 0) {
	        JOptionPane.showMessageDialog(null, mensajes.toString(), "Compras Posibles", JOptionPane.INFORMATION_MESSAGE);
	    }
	}
	
	private Image getScaledImage(Image srcImg, int width, int height) {
        return srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
	
	
	
}
