package src.gui.components.authentication;

import java.awt.Dimension;
import java.awt.Toolkit;
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
        setExtendedState(this.MAXIMIZED_BOTH);
        Dimension tamañoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(tamañoPantalla.width, tamañoPantalla.height));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setTitle("Devora App");
        

        // Inicializamos el CardLayout y el panel contenedor (Ya no hace falta realmente el cardlayout)
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

