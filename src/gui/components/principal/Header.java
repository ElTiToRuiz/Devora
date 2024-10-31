package src.gui.components.principal;

import src.gui.components.authentication.AuthView;
import src.utils.Pallette;
import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {
    private boolean isLogged;

    public Header() {
        this.isLogged = false;
        setupHeader();
    }

    // Configuración inicial del header
    private void setupHeader() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(3,252,207));
        this.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JPanel panelLogo = new JPanel();
        JPanel panelBusqueda = new JPanel();
        JPanel panelBotones = new JPanel();

        panelLogo.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));

        panelLogo.setOpaque(false);
        panelBusqueda.setOpaque(false);
        panelBotones.setOpaque(false);

        leftSideHeader(panelLogo);
        barraBusqueda(panelBusqueda);
        //TODO redondear las esquinas de los botones con la clase graphics
        updateBtns(panelBotones);

        this.add(panelLogo, BorderLayout.WEST);
        this.add(panelBusqueda, BorderLayout.CENTER);
        this.add(panelBotones, BorderLayout.EAST);
    }

    private void updateBtns(JPanel panel) {
        panel.removeAll();
        if (isLogged) {
            System.out.println("Logged");
            userLoged(panel);
        } else {
            System.out.println("Not logged");
            notUserLoged(panel);
        }
        panel.revalidate();
        panel.repaint();
    }

    private void leftSideHeader(JPanel panel) {
        JLabel titleLabel = new JLabel("Devora");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Pallette.TITULOS.getColor());
        panel.add(titleLabel);
    }

    private void barraBusqueda(JPanel panel) {
        JTextField tfBusqueda = new JTextField(100);
        tfBusqueda.setPreferredSize(new Dimension(900, 30));
        tfBusqueda.setBorder(BorderFactory.createEmptyBorder());
        panel.add(tfBusqueda);
    }

    private void userLoged(JPanel panel) {
        JButton profileButton = createHeaderButton("Profile");
        JButton logoutButton = createHeaderButton("Logout");

        logoutButton.addActionListener(e -> {
            System.out.println("Logged out");
            this.isLogged = false;
            updateBtns(panel);
        });

        panel.add(profileButton);
        panel.add(logoutButton);
    }

    private void notUserLoged(JPanel panel) {
        JButton loginButton = createHeaderButton("Login");
        JButton registerButton = createHeaderButton("Register");

        loginButton.addActionListener(e -> {
            this.isLogged = true;
            new AuthView(true);
            updateBtns(panel);
        });

        registerButton.addActionListener(e -> {
            this.isLogged = true;
            new AuthView(false);
            updateBtns(panel);
        });

        panel.add(loginButton);
        panel.add(registerButton);
    }

    private JButton createHeaderButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 30));
        button.setBackground(Pallette.BOTONES.getColor());
        return button;
    }
    
}
