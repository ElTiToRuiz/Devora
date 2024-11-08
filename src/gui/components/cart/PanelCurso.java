package src.gui.components.cart;

import javax.swing.*;
import java.awt.*;

public class PanelCurso extends JPanel {

    public PanelCurso(String titulo, String instructor, double rating, int valoraciones, double horas, int clases) {
        String imgPath = "src/media/default.png";

        setLayout(new BorderLayout(5, 5));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder());

        // Título del curso
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        // Nombre del instructor
        JLabel lblInstructor = new JLabel(instructor);
        lblInstructor.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInstructor.setForeground(Color.DARK_GRAY);

        // Calificación y número de valoraciones
        JLabel lblRating = new JLabel("⭐ " + rating + " (" + valoraciones + " valoraciones)");
        lblRating.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRating.setForeground(Color.GRAY);

        // Horas y número de clases
        JLabel lblDetalles = new JLabel(horas + " horas en total - " + clases + " clases");
        lblDetalles.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDetalles.setForeground(Color.GRAY);

        ImageIcon jadeFondo = new ImageIcon(imgPath);
        Image img = jadeFondo.getImage();
        Image imgEscalada = img.getScaledInstance(170, 100, Image.SCALE_SMOOTH);

        JLabel lblLogo = new JLabel(new ImageIcon(imgEscalada));

        // Agregar los componentes al panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(lblTitulo);
        infoPanel.add(lblInstructor);
        infoPanel.add(lblRating);
        infoPanel.add(lblDetalles);

        add(lblLogo, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
    }
}