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
}
