package src.gui.components.PaymentMethods;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PaysafecardHandler implements PaymentMethodHandler {
    @Override
    public void showPaymentDetails(JFrame parent, String method) {
        JFrame frame = new JFrame("Paysafecard Checkout");
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

        // Título
        JLabel titleLabel = new JLabel("Enter your Paysafecard Code");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Formulario de Paysafecard
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        formPanel.setOpaque(false);

        formPanel.add(new JLabel("Paysafecard Code:", SwingConstants.RIGHT));
        JTextField codeField = new JTextField();
        formPanel.add(codeField);

        // Botón de confirmación
        JButton confirmButton = new JButton("Pay Now");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBackground(new Color(255, 102, 0));
        confirmButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        confirmButton.setFocusPainted(false);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmButton.addActionListener(e -> {
            if (codeField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid Paysafecard code!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Payment confirmed with Paysafecard!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });

        // Agregar componentes al panel
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(titleLabel);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(formPanel);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(confirmButton);

        frame.add(backgroundPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
    }
}
