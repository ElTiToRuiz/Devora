package src.gui.components.cart;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Cart extends JFrame {
    
    private DefaultTableModel modelo;

    public Cart() {
        setTitle("Tu Cart - Devora");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(1920, 1080));
        setExtendedState(this.MAXIMIZED_BOTH);
        
        // Personalizar Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.white); 
        
        JLabel lblCart = new JLabel("Tu pedido actual", JLabel.LEFT);
        lblCart.setFont(new Font("Arial", Font.BOLD, 30)); 
        lblCart.setForeground(new Color(0, 102, 51)); // Color verde
        lblCart.setBorder(BorderFactory.createEmptyBorder(30,10,20,0));
        mainPanel.add(lblCart, BorderLayout.NORTH);
        
        // Panel central que tendrá la tabla y el checkout
        JPanel panelCentro = new JPanel(new BorderLayout());
        
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(400,2));
        separator.setBackground(Color.black);
        
        panelCentro.setBackground(Color.white); 
        mainPanel.add(panelCentro, BorderLayout.CENTER);
        
        // Crear la tabla del carrito
        String[] columnas = {"Producto", "Precio", "Subtotal"};
        Object[][] datos = {
            {new ProductPanel("Producto A", "Desc A", "⭐⭐⭐", new Color(240,240,255)), "$10", "$20"},
            {new ProductPanel("Producto B", "Desc B", "⭐⭐⭐⭐", new Color(255,240,240)), "$15", "$15"},
            {new ProductPanel("Producto C", "Desc C", "⭐⭐", new Color(240,255,240)), "$7", "$21"}
        };
        
        modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; 
            }
        };

        JTable tablaResumen = new JTable(modelo) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 0) {
                    return new ProductCellRenderer();
                }
                return super.getCellRenderer(row, column);
            }
        };
        
        tablaResumen.setFont(new Font("SansSerif", Font.PLAIN, 18)); 
        tablaResumen.setRowHeight(80); // Mayor altura para mostrar el panel completo
        
        DefaultTableCellRenderer alineacionDerecha = new DefaultTableCellRenderer();
        alineacionDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        tablaResumen.getColumnModel().getColumn(1).setCellRenderer(alineacionDerecha);
        tablaResumen.getColumnModel().getColumn(2).setCellRenderer(alineacionDerecha);

        JScrollPane scrollPane = new JScrollPane(tablaResumen);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        panelTabla.setBackground(Color.white);

        JPanel panelCheckout = new JPanel();
        panelCheckout.setLayout(new BoxLayout(panelCheckout, BoxLayout.Y_AXIS));
        panelCheckout.setBackground(new Color(255, 255, 255));
        panelCheckout.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        panelCheckout.setPreferredSize(new Dimension(600, 0)); 

        // Texto Coste Total
        JLabel lblTotal = new JLabel("Coste Total:");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 48));
        panelCheckout.add(lblTotal);

        panelCheckout.add(Box.createVerticalStrut(10)); 

        // Texto Precio
        JLabel lblPrecio = new JLabel("9,99€");
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 48));
        panelCheckout.add(lblPrecio);

        panelCheckout.add(Box.createVerticalStrut(20)); 

        // Panel código promocional
        JPanel panelPromo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelPromo.setOpaque(false);
        JTextField tfPromo = new JTextField();
        JButton btnPromo = new JButton("Aplicar");

        btnPromo.setBorder(BorderFactory.createEmptyBorder());
        tfPromo.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        tfPromo.setFont(new Font("Arial", Font.PLAIN, 20));
        tfPromo.setBackground(new Color(221,221,221));

        btnPromo.setBackground(new Color(3, 252, 207));
        tfPromo.setPreferredSize(new Dimension(300, 40));
        btnPromo.setPreferredSize(new Dimension(80, 40)); 

        panelPromo.setBorder(BorderFactory.createEmptyBorder(0,40,0,0));
        panelPromo.add(tfPromo);
        panelPromo.add(btnPromo);
        panelCheckout.add(panelPromo);

        panelCheckout.add(Box.createVerticalStrut(0)); 

        // Botón pagar
        JButton btnPagar = new JButton("Pagar");
        btnPagar.setBorder(BorderFactory.createEmptyBorder());
        btnPagar.setPreferredSize(new Dimension(300,50));
        btnPagar.setFont(new Font("Arial", Font.BOLD, 20));
        btnPagar.setBackground(new Color(3, 252, 207));
        
        panelCheckout.add(btnPagar);

        // Placeholder en tfPromo
        tfPromo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfPromo.getText().equals("Código Promocional")){
                    tfPromo.setText("");
                    tfPromo.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfPromo.getText().isEmpty()) {
                    tfPromo.setText("Código Promocional");
                    tfPromo.setForeground(new Color(93,93,93));
                }
            }
        });

        // Añadir los paneles al panel central
        panelCentro.add(separator, BorderLayout.CENTER);
        panelCentro.add(panelTabla, BorderLayout.CENTER);
        panelCentro.add(panelCheckout, BorderLayout.EAST); 
        
        add(mainPanel);
        setVisible(true);
    }

    // Clase interna para crear un JPanel de Producto personalizado
    private class ProductPanel extends JPanel {
        public ProductPanel(String title, String description, String rating, Color bgColor) {
            setLayout(new BorderLayout());
            setBackground(bgColor);
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            JLabel lblTitle = new JLabel(title);
            lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
            
            JLabel lblDescription = new JLabel(description);
            lblDescription.setFont(new Font("SansSerif", Font.PLAIN, 12));
            
            JLabel lblRating = new JLabel(rating);
            lblRating.setFont(new Font("SansSerif", Font.PLAIN, 12));

            add(lblTitle, BorderLayout.NORTH);
            add(lblDescription, BorderLayout.CENTER);
            add(lblRating, BorderLayout.SOUTH);
        }
    }
    
    // Renderer para mostrar el JPanel de producto en la tabla
    private class ProductCellRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JPanel) {
                return (JPanel) value;
            }
            return new JLabel("No data");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Cart();
            }
        });
    }
}

