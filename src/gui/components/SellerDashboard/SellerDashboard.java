package src.gui.components.SellerDashboard;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import src.gui.components.editor.CourseEditorPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

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
}
