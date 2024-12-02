package src.gui.components.SellerDashboard;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import src.gui.components.course.CourseEditorPanel;

public class SellerDashboard {
	public static void main(String[] args) {
        // Crear el marco principal
        JFrame frame = new JFrame("Seller Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        // Crear un panel para el menú lateral
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new GridLayout(5, 1, 5, 5)); // Cambiar a 5 opciones para incluir "Editar Cursos"
        sideMenu.setBackground(new Color(34, 40, 49));
        sideMenu.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Crear botones para el menú
        String[] menuOptions = {"Perfil", "Rendimiento de ventas", "Ventas", "Editar Cursos", "Cerrar Sesión"};
        final CardLayout cardLayout = new CardLayout();
        final JPanel mainContent = new JPanel(cardLayout);
        
        for (final String option : menuOptions) {
            JButton button = new JButton(option);
            button.setFocusPainted(false);
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(57, 62, 70));
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            // Agregar acción para cada botón
            button.addActionListener(e -> {
                if (option.equals("Cerrar Sesión")) {
                    int confirm = JOptionPane.showConfirmDialog(frame, "¿Seguro que deseas cerrar sesión?", "Confirmación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        frame.dispose(); // Cierra la aplicación
                    }
                } else if (option.equals("Editar Cursos")) {
                    new CourseEditorPanel().setVisible(true); // Abrir el panel de edición de cursos
                } else {
                    cardLayout.show(mainContent, option);
                }
            });

            sideMenu.add(button);
        }

        // Crear paneles para cada sección
        mainContent.add(createProfilePanel(), "Perfil");
        mainContent.add(createDashboardPanel(), "Rendimiento de ventas");
        mainContent.add(createSalesPanel(), "Ventas");
        
     // Mostrar el panel "Perfil" al iniciar
        cardLayout.show(mainContent, "Perfil");

        // Añadir los componentes principales al marco
        frame.add(sideMenu, BorderLayout.WEST);
        frame.add(mainContent, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.setVisible(true);
    }

    // Panel de "Perfil"
    private static JPanel createProfilePanel() {
        // (código del panel "Perfil" tal como lo tenías)
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 15, 15));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setOpaque(false);
        
     // Crear campos y etiquetas
        formPanel.add(new JLabel("👤 Nombre:"));
        JTextField campoNombre = new JTextField();
        formPanel.add(campoNombre);

        formPanel.add(new JLabel("👤 Apellidos:"));
        JTextField campoApellidos = new JTextField();
        formPanel.add(campoApellidos);

        formPanel.add(new JLabel("✉️ Email:"));
        JTextField campoEmail = new JTextField();
        formPanel.add(campoEmail);

        formPanel.add(new JLabel("🏠 Dirección:"));
        JTextField campoDireccion = new JTextField();
        formPanel.add(campoDireccion);

        formPanel.add(new JLabel("🔑 Contraseña:"));
        JPasswordField campoContrasena = new JPasswordField();
        formPanel.add(campoContrasena);

        formPanel.add(new JLabel("👤 Tipo de Usuario:"));
        String[] tiposUsuario = {"Estudiante", "Profesor", "Administrador", "Creador"};
        JComboBox<String> comboTipoUsuario = new JComboBox<>(tiposUsuario);
        formPanel.add(comboTipoUsuario);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.setBackground(new Color(0, 128, 0));
        botonGuardar.setForeground(Color.WHITE);
        botonGuardar.setFocusPainted(false);
        botonGuardar.setBorder(new LineBorder(new Color(0, 100, 0), 1, true));
        buttonPanel.add(botonGuardar);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setBackground(new Color(200, 0, 0));
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setBorder(new LineBorder(new Color(150, 0, 0), 1, true));
        buttonPanel.add(botonCancelar);

        botonGuardar.addActionListener(e -> JOptionPane.showMessageDialog(panel, "Datos guardados correctamente."));
        botonCancelar.addActionListener(e -> JOptionPane.showMessageDialog(panel, "Cambios descartados."));

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
 // Panel de "Dashboard"
    private static JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Bienvenido a tu Dashboard de Vendedor", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(57, 62, 70));

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(1, 3, 20, 0));
        statsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        statsPanel.add(createSalesChartPanel());
        statsPanel.add(createChartPanel("Productos Vendidos", new double[]{50, 70, 45, 90}, "Mes", "Unidades"));
        statsPanel.add(createChartPanel("Clientes", new double[]{120, 150, 130, 170}, "Mes", "Clientes"));

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);

        return panel;
    }

    // Panel de "Ventas"
    private static JPanel createSalesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Ventas Recientes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        String[] columns = {"ID Venta", "Cliente", "Producto", "Total", "Fecha"};
        Object[][] data = {
                {"001", "Juan Pérez", "Camiseta", "€25.00", "2024-11-20"},
                {"002", "María López", "Pantalón", "€40.00", "2024-11-21"},
                {"003", "Carlos Ruiz", "Zapatos", "€60.00", "2024-11-22"},
        };

        JTable salesTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(salesTable);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Gráfico de ventas
    private static JPanel createSalesChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] categories = {"Enero", "Febrero", "Marzo", "Abril"};
        double[] values = {3000, 5000, 4000, 6000};

        for (int i = 0; i < values.length; i++) {
            dataset.addValue(values[i], "Cursos", categories[i]);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Ventas Totales", "Mes", "Cursos", dataset
        );

        chart.setBackgroundPaint(Color.WHITE);
        return new ChartPanel(chart);
    }

    private static JPanel createChartPanel(String title, double[] values, String categoryAxisLabel, String valueAxisLabel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] categories = {"Enero", "Febrero", "Marzo", "Abril"};

        for (int i = 0; i < values.length; i++) {
            dataset.addValue(values[i], title, categories[i]);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                title, categoryAxisLabel, valueAxisLabel, dataset
        );

        chart.setBackgroundPaint(Color.WHITE);
        return new ChartPanel(chart);
    }
}
