package src.gui.components.course;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseDetail extends JFrame {
    private final int courseId;

    public CourseDetail(int courseId) {
        this.courseId = courseId;
        setupWindow();
        loadCourseDetails();
        this.setVisible(true);
    }

    private void setupWindow() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Ventana a pantalla completa
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Detalles del Curso");
        this.setLayout(new GridLayout(1, 2)); // Dividir la ventana en dos paneles
    }

    private void loadCourseDetails() {
        Connection connection = null;

        try {
            // Conexión a la base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:src/db/devora.db");

            // Consulta SQL para obtener datos del curso
            String query = "SELECT * FROM Curso WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.courseId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // ================== PANEL IZQUIERDO ==================
                JPanel leftPanel = new JPanel(new BorderLayout(10, 20));
                leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

                // Imagen del curso
                String imgPath = resultSet.getString("imgPath");
                ImageIcon courseImage = new ImageIcon(
                        new ImageIcon(imgPath).getImage().getScaledInstance(550, 350, Image.SCALE_SMOOTH)
                );
                JLabel imgLabel = new JLabel(courseImage);
                imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
                leftPanel.add(imgLabel, BorderLayout.NORTH);

                // Detalles del curso en el panel izquierdo
                JPanel detailsPanel = new JPanel(new GridLayout(5, 1, 15, 15));
                detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

                JLabel titleLabel = new JLabel(resultSet.getString("titulo"));
                titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                detailsPanel.add(titleLabel);

                JLabel instructorLabel = new JLabel("Instructor: " + resultSet.getString("instructor"));
                instructorLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                detailsPanel.add(instructorLabel);

                JLabel ratingLabel = new JLabel("Valoración: 3*");
                ratingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                detailsPanel.add(ratingLabel);

                // Descripción del curso
                JTextArea descriptionArea = new JTextArea(resultSet.getString("descripcion"));
                descriptionArea.setRows(4); // Número de líneas visibles (ajustado)
                descriptionArea.setLineWrap(true);
                descriptionArea.setWrapStyleWord(true);
                descriptionArea.setEditable(false);
                descriptionArea.setFont(new Font("Arial", Font.PLAIN, 18));
                JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
                detailsPanel.add(descriptionScroll);

                leftPanel.add(detailsPanel, BorderLayout.CENTER);
                this.add(leftPanel);

                // ================== PANEL DERECHO ==================
                JPanel rightPanel = new JPanel(new BorderLayout(20, 20));
                rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

                // Botón "Volver" en la parte superior
                JButton backButton = new JButton("Volver");
                backButton.setFont(new Font("Arial", Font.BOLD, 20));
                backButton.setPreferredSize(new Dimension(200, 50));
                backButton.addActionListener(e -> {
                    this.dispose();
                    // Aquí puedes añadir el código para volver al grid de cursos si es necesario
                });
                rightPanel.add(backButton, BorderLayout.NORTH);

                // Parte media del panel derecho (Precio y Botón)
                JPanel middleRightPanel = new JPanel(new GridLayout(2, 1, 20, 20));
                middleRightPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0)); // Espaciado superior e inferior
                JLabel priceLabel = new JLabel("Precio: $" + resultSet.getDouble("precio"));
                priceLabel.setFont(new Font("Arial", Font.BOLD, 30));
                priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
                middleRightPanel.add(priceLabel);

                JButton addToCartButton = new JButton("Añadir al carrito");
                addToCartButton.setFont(new Font("Arial", Font.BOLD, 16)); // Fuente ajustada
                addToCartButton.setPreferredSize(new Dimension(200, 35)); // Botón más bajo
                middleRightPanel.add(addToCartButton);

                rightPanel.add(middleRightPanel, BorderLayout.CENTER);

                this.add(rightPanel);

            } else {
                JOptionPane.showMessageDialog(this, "No se encontró información del curso.", "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los detalles del curso:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CourseDetail(1));
    }
}
