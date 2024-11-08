package src.gui.components.cart;

import src.domain.Course;
import src.utils.Pallette;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Cart extends JFrame {

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

        JLabel textoCursos = new JLabel("X cursos en la cesta");
        textoCursos.setFont(new Font("Arial", Font.BOLD, 28));
        textoCursos.setBorder(BorderFactory.createEmptyBorder(25, 130, 0, 0));
        textoCursos.setAlignmentX(LEFT_ALIGNMENT);
        panelTextoProducto.add(textoCursos);
        
        //Panel Tabla Cursos
        panelTabla.setPreferredSize(new Dimension(900,900));
        panelTabla.setOpaque(false);
        // Datos temporales para la previsualización
        Object[][] data = {
            {new PanelCurso("Machine Learning y Data Science: Curso Completo con Python", "Por Paco Jimenez", 4.7, 2572, 31, 121), new lblPrecio(55.99),new lblEliminar()},
            {new PanelCurso("Curso Completo de IA Generativa: ChatGPT, Midjourney y más!", "Por Santiago Hernández", 4.7, 2368, 18, 137), new lblPrecio(34.99),new lblEliminar()},
            {new PanelCurso("Inteligencia Artificial & ChatGPT: De Cero a Avanzado 2024", "Por Julián Mac Loughlin", 4.5, 3396, 29.5, 356), new lblPrecio(14.99),new lblEliminar()}
        };

        // Definir nombres de columnas
        String[] nombresColumnas = {"Producto", "Acción", "Precio"};

        // Crear el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(data, nombresColumnas) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return PanelCurso.class;
                if (columnIndex == 2) return lblEliminar.class;
                return String.class;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(120); // Ajusta la altura de las filas para que los paneles se muestren completamente

        // Configurar renderizador para la primera columna
        table.getColumnModel().getColumn(0).setCellRenderer(new PanelRenderer());
        table.getColumnModel().getColumn(0).setMinWidth(700);
        table.getColumnModel().getColumn(1).setMinWidth(100);
        table.getColumnModel().getColumn(1).setCellRenderer(new lblPrecioRenderer());
        table.getColumnModel().getColumn(2).setMinWidth(100);
        table.getColumnModel().getColumn(2).setCellRenderer(new lblEliminarRenderer());
        
        table.setOpaque(false);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));
        
        JPanel contenedorConMargen = new JPanel(new BorderLayout());
        contenedorConMargen.setBorder(BorderFactory.createEmptyBorder(0, 125, 0, 80)); // Margen izquierdo de 125 px

        table.setTableHeader(null);
        
        // Crear el JScrollPane sin borde y añadirlo al contenedor
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Sin borde en el JScrollPane
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        // Añadir el JScrollPane al contenedor con margen
        contenedorConMargen.add(scrollPane, BorderLayout.CENTER);
        contenedorConMargen.setOpaque(false);

        // Añadir el contenedor con margen al panelTabla
        panelTabla.add(contenedorConMargen, BorderLayout.CENTER);
        
        
        // Configuración de panelTotal (Panel Derecha)
        //Panel Pagar
        panelPagar.setLayout(new BoxLayout(panelPagar, BoxLayout.Y_AXIS)); 
        panelPagar.setBackground(Color.white); 
        panelPagar.setBorder(BorderFactory.createEmptyBorder(170, 100, 20, 20));

        
        panelPagar.setPreferredSize(new Dimension(300, 360));
        panelPagar.setMaximumSize(new Dimension(300, 360));
        panelPagar.setMinimumSize(new Dimension(300, 360));

        // Añadiendo etiquetas y botón
        JLabel lblTotal = new JLabel("Total: ");
        lblTotal.setFont(new Font("Arial",Font.BOLD,22));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
        
        JLabel lblPrecioTotal = new JLabel("184,97€");
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
				// TODO Auto-generated method stub
				
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
        panelPromocion.add(panelLabel);
        
        JPanel panelInputPromo = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        panelInputPromo.setPreferredSize(new Dimension(300,610));
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
        btnPromo.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnPromo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnPromo.setBackground(Pallette.COLOR_HOVER_PRINCIPAL.getColor());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnPromo.setBackground(Pallette.COLOR_PRINCIPAL.getColor());

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

        });
        panelInputPromo.add(tfPromo); panelInputPromo.add(btnPromo);
        
        panelPromocion.add(panelInputPromo);
        
        // Añadir panelPagar al panelTotal
        panelTotal.add(panelPagar, BorderLayout.NORTH);
        panelTotal.add(panelPromocion, BorderLayout.SOUTH);

        add(panelMain);
        setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new Cart(null));
//    }

    class lblEliminar extends JLabel {
    	public lblEliminar() {
    		ImageIcon deleteIcon = new ImageIcon("src/media/delete-icon.png");
            Image img = deleteIcon.getImage();
            Image deleteIconEscalada = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            
            deleteIcon.setImage(deleteIconEscalada);
            setIcon(deleteIcon);
            setBorder(BorderFactory.createEmptyBorder(0,80,0,0));
            
    	}
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
    
    class lblEliminarRenderer implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (value instanceof lblEliminar) {
				return (lblEliminar) value;
			}
			return null;
		}
    }
    
    class lblPrecioRenderer implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean arg2, boolean arg3, int arg4,
				int arg5) {
			if (value instanceof lblPrecio) {
				return (lblPrecio) value;
			}
			return null;
		}
    	
    }
}

