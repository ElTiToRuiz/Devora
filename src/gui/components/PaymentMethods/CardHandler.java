package src.gui.components.PaymentMethods;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CardHandler implements PaymentMethodHandler {
    @Override
    public void showPaymentDetails(JFrame parent, String method) {
        JFrame frame = new JFrame("Credit or Debit Card Payment");
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        // Fondo degradado
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(58, 134, 255);
                Color color2 = new Color(0, 102, 204);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

        // Panel para el logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setMaximumSize(new Dimension(200, 100));
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cargar el logo de la tarjeta de crédito
        ImageIcon originalIcon = new ImageIcon("src/icons/creditcard.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledIcon, SwingConstants.CENTER);
        logoPanel.add(logoLabel);

        // Título
        JLabel titleLabel = new JLabel("Enter your Card Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Formulario de tarjeta
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        formPanel.setOpaque(false);

        JLabel cardNumberLabel = new JLabel("Card Number:", SwingConstants.RIGHT);
        JTextField cardNumberField = new JTextField();
        formPanel.add(cardNumberLabel);
        formPanel.add(cardNumberField);

        JLabel expirationLabel = new JLabel("Expiration Date (MM/YY):", SwingConstants.RIGHT);
        JTextField expirationField = new JTextField();
        formPanel.add(expirationLabel);
        formPanel.add(expirationField);

        JLabel cvvLabel = new JLabel("CVV:", SwingConstants.RIGHT);
        JPasswordField cvvField = new JPasswordField();
        formPanel.add(cvvLabel);
        formPanel.add(cvvField);

        JLabel cardHolderLabel = new JLabel("Card Holder Name:", SwingConstants.RIGHT);
        JTextField cardHolderField = new JTextField();
        formPanel.add(cardHolderLabel);
        formPanel.add(cardHolderField);

        // Botón de confirmación
        JButton confirmButton = new JButton("Pay Now");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBackground(new Color(0, 153, 76));
        confirmButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        confirmButton.setFocusPainted(false);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmButton.addActionListener(e -> {
            String cardNumber = cardNumberField.getText().trim();
            String expiration = expirationField.getText().trim();
            String cvv = new String(cvvField.getPassword()).trim();
            String cardHolder = cardHolderField.getText().trim();

            if (cardNumber.isEmpty() || expiration.isEmpty() || cvv.isEmpty() || cardHolder.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cardNumber.length() < 16 || cvv.length() < 3) {
                JOptionPane.showMessageDialog(frame, "Please enter valid card details.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Payment confirmed via Credit/Debit Card!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });

        // Agregar componentes a la interfaz
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(logoPanel); // Logo con fondo blanco
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(titleLabel); // Título
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(formPanel); // Formulario
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(confirmButton); // Botón

        frame.add(backgroundPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
    }
}
