package src.gui.components.PaymentMethods;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PaymentMethodsUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Payment Methods");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 700);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = createMainPanel(); // Creamos el panel principal
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Payment Methods");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Añadimos detalles de la compra
        JPanel purchasePanel = createPurchasePanel();

        // Añadimos botones de pago
        JButton paypalButton = createPaymentButton("PayPal", new Color(255, 204, 0), e -> openHandler("PayPal"));
        JButton cardButton = createPaymentButton("Credit or Debit Card", new Color(58, 134, 255), e -> openHandler("Credit or Debit Card"));
        JButton googlePayButton = createPaymentButton("Google Pay", new Color(0, 150, 0), e -> openHandler("Google Pay"));
        JButton paysafecardButton = createPaymentButton("Paysafecard", new Color(255, 102, 0), e -> openHandler("Paysafecard"));
        JButton bitcoinButton = createPaymentButton("Bitcoin", new Color(255, 153, 51), e -> openHandler("Bitcoin"));

        // Organizar componentes
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio
        mainPanel.add(purchasePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(paypalButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(cardButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(googlePayButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(paysafecardButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(bitcoinButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio

        return mainPanel;
    }

    private static JPanel createPurchasePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));

        // Detalles de la compra
        double subtotal = 12.88;
        double tax = subtotal * 0.1; // 10% de impuestos
        double total = subtotal + tax;

        panel.add(new JLabel("Subtotal:"));
        panel.add(new JLabel("€" + String.format("%.2f", subtotal)));

        panel.add(new JLabel("Tax:"));
        panel.add(new JLabel("€" + String.format("%.2f", tax)));

        panel.add(new JLabel("Total:"));
        panel.add(new JLabel("€" + String.format("%.2f", total)));

        return panel;
    }

    private static JButton createPaymentButton(String text, Color backgroundColor, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        return button;
    }

    private static void openHandler(String method) {
        PaymentMethodHandler handler = PaymentMethodFactory.getHandler(method);
        if (handler != null) {
            handler.showPaymentDetails(null, method);
        } else {
            JOptionPane.showMessageDialog(null, "Handler not implemented for: " + method, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}