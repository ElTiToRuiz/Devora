package src.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Carrito extends JFrame {
    
    private DefaultTableModel modelo;

    public Carrito() {
        setTitle("Tu Carrito - Devora");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1920, 1080));
        
        // Personalizar Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240)); // Fondo gris claro
        
        JLabel lblCarrito = new JLabel("Tu pedido actual", JLabel.LEFT);
        lblCarrito.setFont(new Font("Arial", Font.PLAIN, 20));
        lblCarrito.setForeground(new Color(0, 102, 51)); // Color verde
        lblCarrito.setBorder(BorderFactory.createEmptyBorder(30,10,20,0));
        mainPanel.add(lblCarrito, BorderLayout.NORTH);
        
        // Panel central que tendrá la tabla y el checkout
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(new Color(255, 255, 255)); 
        mainPanel.add(panelCentro, BorderLayout.CENTER);
        
        // Crear la tabla del carrito
        String[] columnas = {"Producto", "Cantidad", "Precio", "Subtotal"};
        //Datos temporales para ver que funciona
        Object[][] datos = {
            {"Producto A", 2, "$10", "$20"},
            {"Producto B", 1, "$15", "$15"},
            {"Producto C", 3, "$7", "$21"}
        };
        
        modelo = new DefaultTableModel(datos, columnas);
        JTable tablaResumen = new JTable(modelo);
        
        tablaResumen.setFont(new Font("SansSerif", Font.PLAIN, 18)); 
        tablaResumen.setRowHeight(40); 
        
        tablaResumen.setShowGrid(false);
        tablaResumen.setIntercellSpacing(new Dimension(0, 0)); 
        tablaResumen.setFocusable(false); 
        
        DefaultTableCellRenderer alineacionDerecha = new DefaultTableCellRenderer();
        alineacionDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        tablaResumen.getColumnModel().getColumn(1).setCellRenderer(alineacionDerecha);
        tablaResumen.getColumnModel().getColumn(2).setCellRenderer(alineacionDerecha);
        tablaResumen.getColumnModel().getColumn(3).setCellRenderer(alineacionDerecha);

        JScrollPane scrollPane = new JScrollPane(tablaResumen);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        JPanel panelCheckout = new JPanel();
        panelCheckout.setBackground(Color.red); // Color temporal para configurar el tamaño
        panelCheckout.setPreferredSize(new Dimension(600, 0)); // Ancho del panel checkout

        //Añadir los paneles al panel central
        panelCentro.add(panelTabla, BorderLayout.CENTER);
        panelCentro.add(panelCheckout, BorderLayout.EAST); 
        
        JPanel btnPanel = new JPanel();
        JButton btnVaciar = new JButton("Vaciar Carrito");
        JButton btnComprar = new JButton("Realizar Compra");
        JButton btnVolver = new JButton("Volver");
        
        btnPanel.add(btnVaciar);
        btnPanel.add(btnComprar);
        btnPanel.add(btnVolver);
        
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Carrito(); //Run temporal para facilitar la visualización
			}
        	
        });
    }
}
