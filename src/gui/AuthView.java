package src.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AuthView extends JFrame {
	
	private CardLayout cardlayout;
	private JPanel panelPrincipal;
	
    public AuthView() {
        // Configuración básica del JFrame
        setSize(new Dimension(1920, 1080));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Devora App");
        

        // Inicializamos el CardLayout y el panel contenedor
        cardlayout = new CardLayout();
        panelPrincipal = new JPanel(cardlayout);

        // Creamos los paneles de Login y Register
        Login loginPanel = new Login(this);  
        Register registerPanel = new Register(this);

        panelPrincipal.add(loginPanel, "loginPanel");
        panelPrincipal.add(registerPanel, "registerPanel");


        cardlayout.show(panelPrincipal, "loginPanel");

        add(panelPrincipal);
        
        setVisible(true);
    }
    
    public void mostrarPanel(String panel) {
        cardlayout.show(panelPrincipal, panel);  
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AuthView(); //Eliminar al terminar pruebas
            }
        });
    }
}

