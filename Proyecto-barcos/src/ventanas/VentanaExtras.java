package ventanas;

import java.awt.*;

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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


import clases.*;
import db.DB;


public class VentanaExtras extends JFrame {
	private DB db;
	private static final long serialVersionUID = 1L;
	private JToolBar botonesSup;
	private JToolBar botonesInf;
	public static List<Extras> extras;
	private JTable table;
	private DefaultTableModel model2;
	private static VentanaRegistro vr = new VentanaRegistro();

	int mouseRow = -1;
	int mouseCol = -1;
	
	
	public VentanaExtras() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Extras");
		setSize(700, 500);

		model2 = new DefaultTableModel(new Object[]{"IMAGEN", "ID", "TIPO", "COMPRA", "VENTA", "COMPRAR", "VENDER"}, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0) { // Índice de la columna de imágenes
					return ImageIcon.class;
				}
				return super.getColumnClass(columnIndex);
			}
		};

		table = new JTable(model2);
		db = new DB();
		extras = new ArrayList<Extras>();
		botonesSup = new JToolBar();
		botonesInf = new JToolBar();

		JPanel panelSup = new JPanel();
		panelSup.add(botonesSup);

		JPanel panelInf = new JPanel();
		panelInf.add(botonesInf);


		JScrollPane panelExtrasScrl = new JScrollPane(table);
		panelExtrasScrl.setBorder(new TitledBorder("Extras"));
		getContentPane().add(panelSup, BorderLayout.PAGE_START);
		getContentPane().add(panelInf, BorderLayout.SOUTH);
		getContentPane().add(panelExtrasScrl, BorderLayout.CENTER);

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

		this.table.setDefaultRenderer(Object.class, renderMouseOver);


		this.table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseRow = table.rowAtPoint(e.getPoint());

				if (mouseRow > -1) {
					table.repaint();
				}
			}
		});



		JButton eCesta = new JButton(new ImageIcon(getScaledImage(new ImageIcon("img/cesta.png").getImage(), 30, 30)));
		botonesSup.add(eCesta);
		eCesta.setPreferredSize(new Dimension(30, 30));
		eCesta.addActionListener(e -> {
			VentanaCesta vc = new VentanaCesta();
			vc.setLocationRelativeTo(null);
			vc.setVisible(true);
			vc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			vc.setResizable(false);
			dispose();
		});


		JButton atras = new JButton("Atras");
		botonesInf.add(atras);
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
		botonesInf.add(btnPosiblesCompras);
		btnPosiblesCompras.addActionListener(new ActionListener() {
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

		leerCSV();
		cargarExtras();
		table.getColumn("IMAGEN").setCellRenderer(new ImageRenderer());
		table.getColumn("COMPRAR").setCellRenderer(new ButtonRenderer(true));
		table.getColumn("COMPRAR").setCellEditor(new ButtonEditor(new JCheckBox(),true));
		table.getColumn("VENDER").setCellRenderer(new ButtonRenderer(false));
		table.getColumn("VENDER").setCellEditor(new ButtonEditor(new JCheckBox(),false));
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.setRowHeight(100);


	}


	private void cargarExtras() {
		Collections.sort(extras);
		model2.setRowCount(0);

		for (Extras e : extras) {
			// Cargar la imagen
			ImageIcon imagen = new ImageIcon("img/extras/" + e.getId() + ".png");
			Image originalImage = imagen.getImage();
			Image resizedImage = originalImage.getScaledInstance(125, 125, Image.SCALE_SMOOTH);
			ImageIcon resizedIcon = new ImageIcon(resizedImage);
			model2.addRow(new Object[]{resizedIcon, e.getId(), e.getTipo(),e.getCompra(), e.getVenta(), new JButton("Comprar"), new JButton("Vender")});
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


	class ButtonRenderer extends JButton implements TableCellRenderer {
		private boolean isComprar = true;

		public ButtonRenderer() {
			setOpaque(true);
		}

		public ButtonRenderer(boolean isComprar) {
			this.isComprar = isComprar;
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
													   boolean isSelected, boolean hasFocus, int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			if (isComprar) {
				setText("Comprar"); // Cambiado para establecer el texto como "Comprar"
			}else{
				setText("Vender"); // Cambiado para establecer el texto como "Vender"
			}
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {

		protected JButton button;
		private String label;
		private boolean isPushed;
		private boolean isComprar;

		public ButtonEditor(JCheckBox checkBox,boolean isComprar) {
			super(checkBox);
			this.isComprar=isComprar;
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value,
													 boolean isSelected, int row, int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}

			// Almacenar el ID del extra en la variable label
			int extraId = (int) table.getValueAt(row, 1);
			label = String.valueOf(extraId);

			if (isComprar) {
				button.setText("Comprar"); // Cambiado para establecer el texto como "Comprar"
				button.addActionListener(e -> {
					Extras extrasSelected = null;
					for (Extras extras1 : extras) {
						if (extras1.getId() == extraId) {
							extrasSelected = extras1;
						}
					}

					if (vr.usuarioRegistrado.getDinero() >= extrasSelected.getCompra()) {
						String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
						db.guardarDBCompra("usuario.db", vr.usuarioRegistrado, extrasSelected.getId(), extrasSelected.getCompra(), date);
						vr.usuarioRegistrado.setDinero(vr.usuarioRegistrado.getDinero() - extrasSelected.getCompra());
						JOptionPane.showMessageDialog(null, "Compra añadida correctamente a tu inventario.");
					} else {
						JOptionPane.showMessageDialog(null, "No tienes suficiente dinero para realizar esta compra");
					}

				});
			}else{
				button.setText("Vender"); // Cambiado para establecer el texto como "Vender"
				Extras extrasAEliminar = null;

				// Buscar el extra con el ID especificado

				for (Extras extras1 : extras) {
					if (extras1.getId() == extraId) {
						extrasAEliminar = extras1;
					}
				}

				if (table.getSelectedRow() >= 0) {
					String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
					db.guardarDBCompra("usuario.db", vr.usuarioRegistrado, extrasAEliminar.getId(), extrasAEliminar.getVenta(), date);
					vr.usuarioRegistrado.setDinero(vr.usuarioRegistrado.getDinero() + extrasAEliminar.getVenta());
					JOptionPane.showMessageDialog(null, "Venta realizada");
				}

				// Eliminar el barco de la lista
				if (extrasAEliminar != null) {
					extras.remove(extrasAEliminar);
				}

				cargarExtras();

			}
			isPushed = true;




			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (isPushed) {
			}
			isPushed = false;
			return label;
		}

		@Override
		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}
	}

	class ImageRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			if (value instanceof ImageIcon) {
				setIcon((ImageIcon) value);
				setText("");  // Limpiar el texto para que no se solape con la imagen
				setHorizontalAlignment(JLabel.CENTER); // Ajustar la alineación horizontal
				setVerticalAlignment(JLabel.CENTER);   // Ajustar la alineación vertical
			}

			return c;
		}
	}
}



		

