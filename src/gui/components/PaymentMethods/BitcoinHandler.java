package src.gui.components.PaymentMethods;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BitcoinHandler implements PaymentMethodHandler {
    @Override
    public void showPaymentDetails(JFrame parent, String method) {
        JFrame frame = new JFrame("Bitcoin Payment");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Fondo degradado más oscuro
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(50, 50, 50); // Negro/gris oscuro
                Color color2 = new Color(100, 100, 100); // Gris más claro
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

        // Panel blanco detrás del logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setMaximumSize(new Dimension(200, 100));
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Logo de Bitcoin
        ImageIcon originalIcon = new ImageIcon("src/icons/bitcoin.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledIcon, SwingConstants.CENTER);
        logoPanel.add(logoLabel);

        // Título
        JLabel titleLabel = new JLabel("Enter Bitcoin Wallet Address");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Formulario para dirección de la billetera
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        formPanel.setOpaque(false);

        JLabel walletLabel = new JLabel("Wallet Address:", SwingConstants.RIGHT);
        walletLabel.setForeground(Color.WHITE);

        JTextField walletField = new JTextField(); // Campo editable
        walletField.setText(""); // Sin valor predeterminado
        walletField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        walletField.setFont(new Font("Arial", Font.PLAIN, 14));

        formPanel.add(walletLabel, BorderLayout.NORTH);
        formPanel.add(walletField, BorderLayout.CENTER);

        // Botón de confirmación
        JButton confirmButton = new JButton("Pay with Bitcoin");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBackground(new Color(255, 102, 0));
        confirmButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        confirmButton.setFocusPainted(false);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmButton.addActionListener(e -> {
            String walletAddress = walletField.getText().trim();

            if (walletAddress.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid wallet address.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (walletAddress.length() < 26 || walletAddress.length() > 35) {
                JOptionPane.showMessageDialog(frame, "Bitcoin wallet address must be between 26 and 35 characters.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Bitcoin payment confirmed to address:\n" + walletAddress, "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });

        // Agregar componentes al panel
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio
        backgroundPanel.add(logoPanel); // Logo con fondo blanco
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
