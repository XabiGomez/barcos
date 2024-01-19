package ventanas;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
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


import clases.Extras;
import clases.TipoExtras;
import db.DB;


public class VentanaExtras extends JFrame {
	private static final long serialVersionUID = 1L;
	private JToolBar barra;
	private JToolBar barraAbajo;
	public static List<Extras> extras;
	private JTable tablaExtras;
	private DefaultTableModel modeloDatosExtras;
	private static VentanaRegistro vr = new VentanaRegistro();

	int mouseRow = -1;
	int mouseCol = -1;
	
	
	public VentanaExtras() {
		DB db = new DB();
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
		
		barra.add(comprar);
		barra.add(vender);
		barra.add(bCesta);
		barraAbajo.add(atras);
	
		panelSup.add(barra, BorderLayout.NORTH);
		panelSouth.add(barraAbajo, BorderLayout.SOUTH);
		panelSup.add(scrollPaneRepuestos, BorderLayout.CENTER);
		
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
		
		this.tablaExtras.setDefaultRenderer(Object.class, renderMouseOver);
		
		
		this.tablaExtras.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseRow = tablaExtras.rowAtPoint(e.getPoint());

				if (mouseRow > -1) {
					tablaExtras.repaint();
				}
			}
		});
		
		comprar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tablaExtras.getSelectedRow() >= 0) {
					if (vr.usuarioRegistrado.getDinero() >= extras.get(tablaExtras.getSelectedRow()).getCompra()) {
						String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
						db.guardarDBCompra("usuario.db", vr.usuarioRegistrado, extras.get(tablaExtras.getSelectedRow()).getId(), extras.get(tablaExtras.getSelectedRow()).getCompra(), date);
						vr.usuarioRegistrado.setDinero(vr.usuarioRegistrado.getDinero()-extras.get(tablaExtras.getSelectedRow()).getCompra());
						JOptionPane.showMessageDialog(null, "Compra añadida correctamente a tu inventario.");
						} else {
						JOptionPane.showMessageDialog(null, "No tienes suficiente dinero para realizar esta compra");
					}
				}

				
			}
		});
		
		vender.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tablaExtras.getSelectedRow() >= 0) {
					String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
					db.guardarDBCompra("usuario.db", vr.usuarioRegistrado, extras.get(tablaExtras.getSelectedRow()).getId(), extras.get(tablaExtras.getSelectedRow()).getVenta(), date);
					vr.usuarioRegistrado.setDinero(vr.usuarioRegistrado.getDinero()+extras.get(tablaExtras.getSelectedRow()).getVenta());
					JOptionPane.showMessageDialog(null, "Venta realizada");
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
		
		JButton btnPosiblesCompras = new JButton("Posibles compras");
		barraAbajo.add(btnPosiblesCompras);
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
	
	private void calcularComprasPosibles(int disponible) {
		ArrayList<Extras> lExtras = new ArrayList<>();
		calcularComprasPosibles(extras, disponible, lExtras);
	}
	private void calcularComprasPosibles(List<Extras> listExtras, int restante, ArrayList<Extras> lComprado) {
		if (restante < 0) return;
		else if (restante < 50) {
			String mensaje = "Posible compra (sobran " + String.format("%d", restante) + " euros): " + lComprado;
		    JOptionPane.showMessageDialog(null, mensaje, "Posibles Compras", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			for (Extras e : listExtras) {
				lComprado.add(e);
				calcularComprasPosibles(listExtras, restante - e.getCompra(), lComprado);
				lComprado.remove(lComprado.size()-1);
			}
		}
	}
	
	private Image getScaledImage(Image srcImg, int width, int height) {
        return srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
	

}
