package src.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class Carrito extends JFrame {
    
    private DefaultTableModel modelo;

    @SuppressWarnings("serial")
    public Carrito() {
    	//TODO EMPEZAR DE CERO, esto se podría usar como base
        setTitle("Tu Carrito - Devora");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        
        // Personalizar Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240)); // Fondo gris claro
        
        JLabel lblCarrito = new JLabel("Tu pedido actual", JLabel.CENTER);
        lblCarrito.setFont(new Font("Arial", Font.BOLD, 24));
        lblCarrito.setForeground(new Color(0, 102, 51)); // Color verde
        mainPanel.add(lblCarrito, BorderLayout.NORTH);
        
        // Creación Tabla
        modelo = new DefaultTableModel(new Object[] {"Producto", "Cantidad", "Precio", "Acciones"}, 0);
        JTable tablePedido = new JTable(modelo);
        
        // Agregar filas de ejemplo
        addRow("Prueba", 70, 99);
        addRow("Prueba2", 500, 323);
        addRow("Prueba3", 250, 33);
        
        tablePedido.setDefaultRenderer(Object.class, new RendererTabla());
        tablePedido.setDefaultEditor(Object.class, new ButtonEditor()); // Usar editor para botones
        tablePedido.setRowHeight(40); // Aumentar altura de fila
        tablePedido.setFont(new Font("Arial", Font.PLAIN, 16));
        tablePedido.setGridColor(Color.LIGHT_GRAY);
        tablePedido.setShowGrid(true);
        tablePedido.setIntercellSpacing(new Dimension(5, 5)); // Espaciado entre celdas
        
        JScrollPane scrollPane = new JScrollPane(tablePedido);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Crear contenedor de botones
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
    
    private void addRow(String producto, int cantidad, double precio) {
        modelo.addRow(new Object[] {producto, cantidad, precio, new JButton("Acciones")}); // Usar botón como marcador
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Carrito());
    }
    
    private class RendererTabla implements TableCellRenderer {
        
        private JLabel label;
        
        public RendererTabla() {
            label = new JLabel();
            label.setFont(new Font("Arial", Font.PLAIN, 16));
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (value instanceof JButton) {
                return (JButton) value; // Devuelve el botón directamente
            }
            label.setText(value != null ? value.toString() : "");
            label.setOpaque(true);
            
            // Personalizar colores
            if (isSelected) {
                label.setBackground(new Color(0, 153, 153)); // Color cuando está seleccionado
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
            
            return label;
        }
    }
    
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private JPanel panel;

        public ButtonEditor() {
            super(new JCheckBox());
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                // Aquí puedes manejar la lógica para ajustar cantidades y eliminar
                JOptionPane.showMessageDialog(button, "Botón de acción presionado");
            });

            panel = new JPanel();
            panel.add(button);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText("Acciones");
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return button;
        }
    }
}

