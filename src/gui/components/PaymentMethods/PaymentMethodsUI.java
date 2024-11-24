package src.gui.components.PaymentMethods;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PaymentMethodsUI {

    private static JButton payButton; // Botón de pago declarado como variable global
    private static JPanel paymentPanel; // Panel de métodos de pago como variable global
    private static String selectedMethod = null; // Método de pago seleccionado como variable global

    public static void main(String[] args) {
        // Crear el marco principal
        JFrame frame = new JFrame("Payment Methods");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Tamaño inicial
        frame.setLayout(new BorderLayout());

        // Panel principal con margen y diseño adaptable
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Crear un panel para los métodos de pago
        paymentPanel = new JPanel();
        paymentPanel.setLayout(new GridLayout(0, 1, 10, 10)); // GridLayout adaptable
        paymentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        paymentPanel.setBackground(new Color(245, 245, 245)); // Fondo suave

        // Crear un grupo de botones para los métodos de pago
        ButtonGroup paymentGroup = new ButtonGroup();

        // Lista de métodos de pago
        String[] paymentMethods = {
                "PayPal",
                "Credit or Debit Card",
                "Google Pay",
                "Paysafecard",
                "Bitcoin"
        };

        // Lista de rutas de los íconos
        String[] paymentLogos = {
                "icons/paypal.png",
                "icons/creditcard.png",
                "icons/googlepay.png",
                "icons/paysafecard.png",
                "icons/bitcoin.png"
        };

        // Añadir los métodos de pago como botones de selección con imágenes
        for (int i = 0; i < paymentMethods.length; i++) {
            String method = paymentMethods[i];
            String logoPath = paymentLogos[i];

            // Crear un ícono escalado para cada botón
            ImageIcon icon = new ImageIcon(new ImageIcon(logoPath).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

            // Crear un JRadioButton para cada método de pago
            JRadioButton button = new JRadioButton(method);
            button.setIcon(icon); // Asignar el ícono al botón
            button.setBackground(Color.WHITE);
            button.setFocusPainted(false); // Quitar el borde de enfoque
            button.setHorizontalAlignment(SwingConstants.LEFT); // Ícono alineado a la izquierda
            button.setVerticalTextPosition(SwingConstants.CENTER);
            button.setHorizontalTextPosition(SwingConstants.RIGHT); // Indicador circular visible
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Añadir el JRadioButton al grupo y al panel
            paymentGroup.add(button);
            paymentPanel.add(button);

            // Actualizar el botón de pago dinámicamente al seleccionar un método
            button.addActionListener(e -> {
                selectedMethod = method; // Actualizar el método seleccionado
                payButton.setText("Pay with " + method);

                // Cambiar el fondo del panel a un color más oscuro
                paymentPanel.setBackground(new Color(220, 220, 220));

                // Cambiar el fondo del botón seleccionado
                for (Component comp : paymentPanel.getComponents()) {
                    if (comp instanceof JRadioButton) {
                        comp.setBackground(Color.WHITE); // Restaurar el fondo de otros botones
                    }
                }
                button.setBackground(new Color(200, 200, 200)); // Fondo oscuro para el botón seleccionado
            });
        }

        // Botón de pago inicializado
        payButton = new JButton("Pay with ...");
        payButton.setFont(new Font("Arial", Font.BOLD, 16));
        payButton.setForeground(Color.WHITE);
        payButton.setBackground(new Color(58, 134, 255));
        payButton.setFocusPainted(false);
        payButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // ActionListener para abrir la ventana con los datos del método de pago
        payButton.addActionListener(e -> {
            if (selectedMethod == null) {
                JOptionPane.showMessageDialog(frame, "Please select a payment method first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JFrame paymentDetailsFrame = new JFrame("Payment Details - " + selectedMethod);
            paymentDetailsFrame.setSize(400, 150); // Reducir altura de la ventana (mitad)
            paymentDetailsFrame.setLayout(new BorderLayout());
            paymentDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel formPanel = new JPanel();
            formPanel.setLayout(new GridLayout(0, 2, 10, 5)); // Espaciado ajustado
            formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            switch (selectedMethod) {
                case "PayPal":
                    formPanel.add(new JLabel("PayPal Email:"));
                    JTextField paypalEmailField = new JTextField();
                    paypalEmailField.setPreferredSize(new Dimension(200, 20)); // Reducir altura
                    formPanel.add(paypalEmailField);

                    formPanel.add(new JLabel("PayPal Password:"));
                    JPasswordField paypalPasswordField = new JPasswordField();
                    paypalPasswordField.setPreferredSize(new Dimension(200, 20)); // Reducir altura
                    formPanel.add(paypalPasswordField);
                    break;
                case "Credit or Debit Card":
                    formPanel.add(new JLabel("Card Number:"));
                    JTextField cardNumberField = new JTextField();
                    cardNumberField.setPreferredSize(new Dimension(200, 20)); // Reducir altura
                    formPanel.add(cardNumberField);

                    formPanel.add(new JLabel("Expiration Date (MM/YY):"));
                    JTextField expiryDateField = new JTextField();
                    expiryDateField.setPreferredSize(new Dimension(200, 20)); // Reducir altura
                    formPanel.add(expiryDateField);

                    formPanel.add(new JLabel("CVV:"));
                    JPasswordField cvvField = new JPasswordField();
                    cvvField.setPreferredSize(new Dimension(200, 20)); // Reducir altura
                    formPanel.add(cvvField);
                    break;
                case "Google Pay":
                    formPanel.add(new JLabel("Google Account Email:"));
                    JTextField googleEmailField = new JTextField();
                    googleEmailField.setPreferredSize(new Dimension(200, 20)); // Reducir altura
                    formPanel.add(googleEmailField);

                    formPanel.add(new JLabel("Phone Number:"));
                    JTextField phoneNumberField = new JTextField();
                    phoneNumberField.setPreferredSize(new Dimension(200, 20)); // Reducir altura
                    formPanel.add(phoneNumberField);
                    break;
                case "Paysafecard":
                    formPanel.add(new JLabel("Paysafecard Code:"));
                    JTextField paysafecardCodeField = new JTextField();
                    paysafecardCodeField.setPreferredSize(new Dimension(200, 20)); // Reducir altura
                    formPanel.add(paysafecardCodeField);
                    break;
                case "Bitcoin":
                    formPanel.add(new JLabel("Bitcoin Wallet Address:"));
                    JTextField bitcoinWalletField = new JTextField();
                    bitcoinWalletField.setPreferredSize(new Dimension(200, 20)); // Reducir altura
                    formPanel.add(bitcoinWalletField);
                    break;
                default:
                    JOptionPane.showMessageDialog(paymentDetailsFrame, "Unknown payment method.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            JButton confirmButton = new JButton("Confirm Payment");
            confirmButton.addActionListener(ev -> {
                JOptionPane.showMessageDialog(paymentDetailsFrame, "Payment confirmed with " + selectedMethod + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                paymentDetailsFrame.dispose();
            });

            paymentDetailsFrame.add(formPanel, BorderLayout.CENTER);
            paymentDetailsFrame.add(confirmButton, BorderLayout.PAGE_END);

            paymentDetailsFrame.setLocationRelativeTo(frame);
            paymentDetailsFrame.setVisible(true);
        });

        // Panel inferior con botón de pago
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        bottomPanel.add(payButton, BorderLayout.CENTER);

        // Panel de precios para la sección derecha
        JPanel pricePanel = new JPanel(new GridBagLayout());
        pricePanel.setBorder(BorderFactory.createTitledBorder("Payment Summary"));
        pricePanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        double subtotal = 12.88; // Subtotal inicial fijo
        double fee = subtotal * 0.01; // Fee fijo
        JLabel subtotalLabel = new JLabel("€" + String.format("%.2f", subtotal), SwingConstants.RIGHT);
        JLabel feeLabel = new JLabel("€" + String.format("%.2f", fee), SwingConstants.RIGHT);
        JLabel totalPriceLabel = new JLabel("€" + String.format("%.2f", subtotal + fee), SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        pricePanel.add(new JLabel("Subtotal:"), gbc);
        gbc.gridx = 1;
        pricePanel.add(subtotalLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pricePanel.add(new JLabel("Payment Fee:"), gbc);
        gbc.gridx = 1;
        pricePanel.add(feeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pricePanel.add(new JLabel("Total Price:"), gbc);
        gbc.gridx = 1;
        pricePanel.add(totalPriceLabel, gbc);

        // Añadir componentes al marco principal
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new JScrollPane(paymentPanel), BorderLayout.CENTER);
        contentPanel.add(pricePanel, BorderLayout.EAST); // Panel de precios a la derecha
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.PAGE_END);

        // Configuración de la ventana
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.setVisible(true);
    }
}

