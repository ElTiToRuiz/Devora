package src.gui.components.authentication;

import java.awt.Dimension;
import java.awt.CardLayout;

import javax.swing.*;

@SuppressWarnings("serial")
public class AuthView extends JFrame {
	private boolean isLogged;
	private CardLayout cardlayout;
	private JPanel panelPrincipal;
	
    public AuthView(Boolean isLogged) {
        this.isLogged = isLogged;

        // Configuración básica del JFrame
        setSize(new Dimension(1000, 680));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setTitle("Devora App");
        

        // Inicializamos el CardLayout y el panel contenedor
        cardlayout = new CardLayout();
        panelPrincipal = new JPanel(cardlayout);

        // Creamos los paneles de Login y Register
        if(isLogged) {
            Login loginPanel = new Login(this);
            panelPrincipal.add(loginPanel, "loginPanel");
        } else {
            Register registerPanel = new Register(this);
            panelPrincipal.add(registerPanel, "registerPanel");
        }

        cardlayout.show(panelPrincipal, "loginPanel");
        add(panelPrincipal);
        
        setVisible(true);
    }
    
    public void mostrarPanel(String panel) {
        cardlayout.show(panelPrincipal, panel);  
    }

}

