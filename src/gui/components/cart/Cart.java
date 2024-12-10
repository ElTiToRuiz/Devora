package src.gui.components.cart;

import src.db.Database;
import src.domain.Course;
import src.gui.components.principal.Header;
import src.utils.Pallette;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.util.EventObject;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class Cart extends JFrame {
	
	// Declarar textoCursos como atributo de la clase Cart
	private JLabel textoCursos;
	private JLabel lblPrecioTotal;

    public Cart(List<Course> listaCursos) {
        setTitle("Tu Cart - Devora");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(1920, 1080));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panelMain = new JPanel(new BorderLayout());

        JPanel panelProductos = new JPanel(new BorderLayout());
        JPanel panelTabla = new JPanel(new BorderLayout());
        JPanel panelTextoProducto = new JPanel();

        JPanel panelTotal = new JPanel(new BorderLayout());
        JPanel panelPromocion = new JPanel();
        JPanel panelPagar = new JPanel();

        // Añadimos los paneles principales al panel main
        panelMain.add(panelProductos, BorderLayout.WEST);
        panelMain.add(panelTotal, BorderLayout.EAST);

        panelProductos.setPreferredSize(new Dimension(1310, 0));
        panelTotal.setPreferredSize(new Dimension(610, 500));

        panelProductos.setBackground(Color.white);
        panelTotal.setBackground(Color.black); //Sirve como separador

        // Ajustes panelProductos (Panel Izquierda)
        // Panel texto
        panelProductos.add(panelTextoProducto, BorderLayout.NORTH);
        panelProductos.add(panelTabla, BorderLayout.SOUTH);

        panelTextoProducto.setLayout(new BoxLayout(panelTextoProducto, BoxLayout.Y_AXIS));
        panelTextoProducto.setPreferredSize(new Dimension(1310, 200));
        panelTextoProducto.setBackground(Color.white);

        JLabel textoCarrito = new JLabel("Cesta");
        textoCarrito.setBorder(BorderFactory.createEmptyBorder(50, 125, 0, 0));
        textoCarrito.setFont(new Font("Arial", Font.BOLD, 58));
        textoCarrito.setAlignmentX(LEFT_ALIGNMENT);
        panelTextoProducto.add(textoCarrito);

        JLabel textoCursos = new JLabel(listaCursos.size() + " cursos en la cesta");
        textoCursos.setFont(new Font("Arial", Font.BOLD, 28));
        textoCursos.setBorder(BorderFactory.createEmptyBorder(25, 130, 0, 0));
        textoCursos.setAlignmentX(LEFT_ALIGNMENT);
        panelTextoProducto.add(textoCursos);
        
        //Panel Tabla Cursos
        panelTabla.setPreferredSize(new Dimension(900,900));
        panelTabla.setOpaque(false);
        
        JTable table = crearTabla(listaCursos);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        JPanel contenedorConMargen = new JPanel(new BorderLayout());
        contenedorConMargen.setBorder(BorderFactory.createEmptyBorder(0, 125, 0, 80));
        
        contenedorConMargen.add(scrollPane, BorderLayout.CENTER);
        contenedorConMargen.setOpaque(false);

        panelTabla.add(contenedorConMargen, BorderLayout.CENTER);
        
        panelPagar.setLayout(new BoxLayout(panelPagar, BoxLayout.Y_AXIS)); 
        panelPagar.setBackground(Color.white); 
        panelPagar.setBorder(BorderFactory.createEmptyBorder(170, 100, 20, 20));

        panelPagar.setPreferredSize(new Dimension(300, 360));
        panelPagar.setMaximumSize(new Dimension(300, 360));
        panelPagar.setMinimumSize(new Dimension(300, 360));

        JLabel lblTotal = new JLabel("Total: ");
        lblTotal.setFont(new Font("Arial",Font.BOLD,22));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
        
        JLabel lblPrecioTotal = new JLabel(String.valueOf(calcularPrecioTotal(listaCursos)) + "€");
        lblPrecioTotal.setFont(new Font("Arial",Font.BOLD,52));
        lblPrecioTotal.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        
        JButton btnPagar = new JButton("Pagar");
        btnPagar.setBackground(Pallette.COLOR_PRINCIPAL.getColor());
        btnPagar.setForeground(Color.white);
        btnPagar.setFont(new Font("Arial",Font.BOLD,20));
        btnPagar.setBorder(BorderFactory.createEmptyBorder(10,175,10,175));
        
        //Hover
        btnPagar.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(listaCursos.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Tienes que añadir al menos un producto antes de realizar la compra.");
				}
				
				Double saldo = Database.conseguirSaldo(Header.id);
		        String costeTotal = lblPrecioTotal.getText().replaceAll("[^0-9.,]", ""); //Quitamos el simbolo de €
				if(saldo < Double.parseDouble(costeTotal)) {
			        JOptionPane.showMessageDialog(null, "No tienes saldo suficiente", "Alerta", JOptionPane.WARNING_MESSAGE);
				}else {
			        JOptionPane.showMessageDialog(null, "Compra realizada", "Alerta", JOptionPane.WARNING_MESSAGE);
			        Double balanceActual = saldo-Double.parseDouble(costeTotal);
			        try {
						Database.actualizarBalance(Header.id, balanceActual);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        for(Course curso : listaCursos) {
			        	int idCurso = curso.getId();
			        	try {
							Database.registrarCompra(Header.id, idCurso);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
				}
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnPagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnPagar.setBackground(Pallette.COLOR_HOVER_PRINCIPAL.getColor());
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				btnPagar.setBackground(Pallette.COLOR_PRINCIPAL.getColor());
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        // Configurar alineación y bordes
        lblTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPrecioTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnPagar.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelPagar.add(lblTotal);
        panelPagar.add(lblPrecioTotal);
        panelPagar.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre etiquetas y botón
        panelPagar.add(btnPagar);
        
        //Panel Promoción
        panelPromocion.setLayout(new BoxLayout(panelPromocion, BoxLayout.Y_AXIS)); 
        panelPromocion.setBackground(Color.white); 
        panelPromocion.setBorder(BorderFactory.createEmptyBorder(30, 100, 20, 20));
        
        panelPromocion.setPreferredSize(new Dimension(300, 750));
        panelPromocion.setMaximumSize(new Dimension(300, 750));
        panelPromocion.setMinimumSize(new Dimension(300, 750));
        
        JPanel panelLabel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        panelLabel.setPreferredSize(new Dimension(300,0));
        panelLabel.setOpaque(false);
        
        JLabel lblPromo = new JLabel("Promociones");
        lblPromo.setFont(new Font("Arial",Font.BOLD,22));
        lblPromo.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        lblPromo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelLabel.add(lblPromo);
        panelLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        panelPromocion.add(panelLabel);
        
        JPanel panelInputPromo = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        panelInputPromo.setPreferredSize(new Dimension(300,460));
        panelInputPromo.setOpaque(false);
        
        JTextField tfPromo = new JTextField();
        tfPromo.setPreferredSize(new Dimension(300,38));

        JButton btnPromo = new JButton("Aplicar");
        btnPromo.setPreferredSize(new Dimension(104,38));
        btnPromo.setFont(new Font("Arial",Font.BOLD,16));
        btnPromo.setBorder(BorderFactory.createEmptyBorder());
        btnPromo.setBackground(Pallette.COLOR_PRINCIPAL.getColor());
        btnPromo.setForeground(Color.white);

        //Hover
        btnPromo.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				btnPromo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnPromo.setBackground(Pallette.COLOR_HOVER_PRINCIPAL.getColor());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnPromo.setBackground(Pallette.COLOR_PRINCIPAL.getColor());

			}

        });
        
        btnPromo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    if (tfPromo.getText().isEmpty()) {
			        JOptionPane.showMessageDialog(null, "Añade un código promocional para obtener un descuento en tu compra");
			        return;
			    }

			    Double descuento = 1.0; 

			    if (tfPromo.getText().equals("Imanol")) {
			        descuento = 0.90; 
			    } else if (tfPromo.getText().equals("Igor")) {
			        descuento = 0.85; 
			    } else if (tfPromo.getText().equals("Iñigo")) {
			        descuento = 0.80; 
			    } else if (tfPromo.getText().equals("JoseMaria")) {
			        descuento = 0.75; 
			    } else {
			        JOptionPane.showMessageDialog(null, "Introduce un código promocional válido para obtener un descuento en tu compra");
			        return;
			    }

			    String costeTotalAnterior = lblPrecioTotal.getText().replaceAll("[^0-9.,]", ""); // Quitamos el símbolo de €
			    try {
			        Double costeTotalActual = descuento * Double.parseDouble(costeTotalAnterior); 
			        lblPrecioTotal.setText(String.format("%.2f€", costeTotalActual)); 
			        tfPromo.setEditable(false);  
			        btnPromo.setEnabled(false); 
			    } catch (NumberFormatException ex) {
			        JOptionPane.showMessageDialog(null, "Error al calcular el precio total. Asegúrate de que el precio sea válido.");
			    }
			}
        	
        });
        panelInputPromo.add(tfPromo); panelInputPromo.add(btnPromo);
        panelPromocion.add(panelInputPromo);
        
        JPanel panelEliminar = new JPanel();
        JButton btnEliminar = new JButton("Vaciar cesta");
        btnEliminar.setPreferredSize(new Dimension(404,38));
        btnEliminar.setBorder(BorderFactory.createEmptyBorder());
        btnEliminar.setForeground(Color.white);
        btnEliminar.setBackground(new Color(255, 102, 102));
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminar.setFont(new Font("Arial",Font.BOLD,18));
        btnEliminar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				lblPrecioTotal.setText("0.00€");
				textoCursos.setText("0 Cursos en la cesta");
				listaCursos.clear();
				Header.pedido.clear();
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
			}
        	
        });
        JButton btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(404,38));
        btnVolver.setBackground(Pallette.COLOR_PRINCIPAL.getColor());
        btnVolver.setBorder(BorderFactory.createEmptyBorder());
        btnVolver.setFont(new Font("Arial",Font.BOLD,18));
        btnVolver.setForeground(Color.white);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnVolver.setBackground(Pallette.COLOR_HOVER_PRINCIPAL.getColor());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnVolver.setBackground(Pallette.COLOR_PRINCIPAL.getColor());
			}
        	
        });
        
        panelEliminar.add(btnEliminar);
        panelEliminar.add(btnVolver);
        panelEliminar.setBackground(Color.white);
        panelEliminar.setBorder(BorderFactory.createEmptyBorder(0,0,100,0)); 
        
        // Añadir panelPagar al panelTotal
        panelTotal.add(panelPagar, BorderLayout.NORTH);
        panelTotal.add(panelPromocion, BorderLayout.CENTER);
        panelTotal.add(panelEliminar,BorderLayout.SOUTH);

        add(panelMain);
        setVisible(true);
    }

    class lblPrecio extends JLabel{
    	
    	public lblPrecio(Double precio) {
    		setFont(new Font("Arial",Font.BOLD,20));
    		setAlignmentX(RIGHT_ALIGNMENT);
    		setText(precio + "€");
    		setOpaque(false);
    		setBorder(BorderFactory.createEmptyBorder(0,70,0,0));
    	}
    }
    
    class PanelRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof PanelCurso) {
                return (PanelCurso) value;
            }
			return table;
        }
    }
    
    class lblPrecioRenderer implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean arg2, boolean arg3, int arg4,
				int arg5) {
			if (value instanceof lblPrecio) {
				return (lblPrecio) value;
			}
			return new JLabel("");
		}

        public boolean isCellEditable(EventObject e) {
            return true;
        }
    };
    
    public JTable crearTabla(List<Course> listaCursos) {
        String[] nombresColumnas = {"Producto", "Precio"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, nombresColumnas) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return PanelCurso.class;
                return String.class;
            }
        };
        
    	if(!listaCursos.isEmpty()) {
        	for(Course curso : listaCursos) {
        		model.addRow(new Object[]{
        			    new PanelCurso(curso.getName(), 
        			                   "Por " + curso.getInstructor(), 
        			                   curso.getRating(), 
        			                   curso.getNumReseñas(), 
        			                   curso.getDuration(), 
        			                   curso.getClases(), 
        			                   curso.getImgPath()), 
        			    new lblPrecio(curso.getPrice())
        			});

        	}
    	}

        JTable table = new JTable(model);
        table.setRowHeight(120); 

        // Configurar renderizador para la primera columna
        table.getColumnModel().getColumn(0).setCellRenderer(new PanelRenderer());
        table.getColumnModel().getColumn(0).setMinWidth(700);
        table.getColumnModel().getColumn(1).setMinWidth(100);
        table.getColumnModel().getColumn(1).setCellRenderer(new lblPrecioRenderer());
   
        table.setOpaque(false);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));

        table.setTableHeader(null);
		return table;
    }
    
    public Double calcularPrecioTotal(List<Course> listaCursos) {
    	
    	Double precioTotal = 0.00;
    	for(Course curso : listaCursos) {
    		precioTotal = precioTotal + curso.getPrice();
    	}
    	
		return precioTotal;
	}
    
}

