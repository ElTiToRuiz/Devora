package src.gui.components.principal;

import src.gui.components.authentication.AuthView;
import src.gui.components.authentication.Login;
import src.utils.Pallette;
import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {
    private boolean isLogged;

    public Header(){
        this.isLogged = false;
        setupHeader();
    }
    private void setupHeader() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Pallette.ENCABEZADOS.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        leftSideHeader();
        updateBtns();
    }

    private void updateBtns(){
        this.removeAll();
        leftSideHeader();
        if (isLogged) {
            System.out.println("Logged");
            userLoged();
        } else {
            System.out.println("Not logged");
            notUserLoged();
        }
        this.revalidate();
        this.repaint();
    }


    private void leftSideHeader(){
        JLabel titleLabel = new JLabel("Devora");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(titleLabel);
        this.add(Box.createHorizontalGlue());
    }


    private void userLoged(){
        JButton profileButton = createHeaderButton("Profile");
        JButton logoutButton = createHeaderButton("Logout");

        logoutButton.addActionListener(e -> {
            System.out.println("Loged out");
            this.isLogged = false;
            updateBtns();
        });

        this.add(profileButton);
        this.add(Box.createHorizontalStrut(10));
        this.add(logoutButton);
    }

    private void notUserLoged(){
        JButton loginButton = createHeaderButton("Login");
        JButton registerButton = createHeaderButton("Register");

        loginButton.addActionListener(e -> {
            this.isLogged = true;
            new AuthView(true);
            updateBtns();
        });

        registerButton.addActionListener(e-> {
            this.isLogged = true;
            new AuthView(false);
            updateBtns();
        });

        this.add(loginButton);
        this.add(Box.createHorizontalStrut(10));
        this.add(registerButton);
    }

    private JButton createHeaderButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(100, 50));
        button.setBackground(Pallette.BOTONES.getColor());
        return button;
    }
}
