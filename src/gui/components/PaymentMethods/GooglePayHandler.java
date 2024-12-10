package src.gui.components.PaymentMethods;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GooglePayHandler implements PaymentMethodHandler {
    @Override
    public void showPaymentDetails(JFrame parent, String method) {
        JFrame frame = new JFrame("Google Pay Checkout");
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        // Fondo degradado
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(0, 150, 0);
                Color color2 = new Color(58, 134, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

        // Agregar logo de Google Pay
        ImageIcon originalIcon = new ImageIcon("src/icons/googlepay.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledIcon, SwingConstants.CENTER);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título
        JLabel titleLabel = new JLabel("Link your Google Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Formulario de Google Pay
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        formPanel.setOpaque(false);

        JLabel emailLabel = new JLabel("Google Email:", SwingConstants.RIGHT);
        JTextField emailField = new JTextField();
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone Number:", SwingConstants.RIGHT);
        JTextField phoneField = new JTextField();
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        // Botón de confirmación
        JButton confirmButton = new JButton("Link & Pay");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBackground(new Color(58, 134, 255));
        confirmButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        confirmButton.setFocusPainted(false);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!email.contains("@") || phone.length() < 10) {
                JOptionPane.showMessageDialog(frame, "Please enter valid email and phone number.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Google Pay linked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });

        // Agregar componentes al panel
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio
        backgroundPanel.add(logoLabel); // Logo
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio
        backgroundPanel.add(titleLabel); // Título
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio
        backgroundPanel.add(formPanel); // Formulario
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio
        backgroundPanel.add(confirmButton); // Botón

        frame.add(backgroundPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
    }
}
