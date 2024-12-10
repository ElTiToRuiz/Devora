package src.gui.components.PaymentMethods;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PaysafecardHandler implements PaymentMethodHandler {
    @Override
    public void showPaymentDetails(JFrame parent, String method) {
        JFrame frame = new JFrame("Paysafecard Payment");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Fondo degradado
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(255, 102, 0);
                Color color2 = new Color(255, 153, 51);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

        // Panel blanco para el logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE); // Fondo blanco para asegurar visibilidad
        logoPanel.setMaximumSize(new Dimension(200, 100));
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Logo de Paysafecard
        ImageIcon originalIcon = new ImageIcon("src/icons/paysafecard.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledIcon, SwingConstants.CENTER);
        logoPanel.add(logoLabel);

        // Título debajo del logo
        JLabel titleLabel = new JLabel("Enter Paysafecard Code");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Formulario de Paysafecard
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        formPanel.setOpaque(false);

        JLabel codeLabel = new JLabel("Paysafecard Code:", SwingConstants.RIGHT);
        codeLabel.setForeground(Color.WHITE);

        JTextField codeField = new JTextField();
        codeField.setText(""); // Campo vacío por defecto
        codeField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        codeField.setFont(new Font("Arial", Font.PLAIN, 14));

        formPanel.add(codeLabel, BorderLayout.NORTH);
        formPanel.add(codeField, BorderLayout.CENTER);

        // Botón de confirmación
        JButton confirmButton = new JButton("Pay Now");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBackground(new Color(255, 102, 0));
        confirmButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        confirmButton.setFocusPainted(false);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmButton.addActionListener(e -> {
            String code = codeField.getText().trim();

            if (code.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid Paysafecard code.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (code.length() < 16 || code.length() > 19) {
                JOptionPane.showMessageDialog(frame, "Paysafecard code must be between 16 and 19 characters.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Payment confirmed via Paysafecard!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });

        // Panel para contener el formulario y el botón
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado
        contentPanel.add(formPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado
        contentPanel.add(confirmButton);

        // Agregar componentes al panel de fondo
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado
        backgroundPanel.add(logoPanel); // Logo con fondo blanco
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado
        backgroundPanel.add(titleLabel); // Título
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado
        backgroundPanel.add(contentPanel); // Formulario y botón

        frame.add(backgroundPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
    }
}
