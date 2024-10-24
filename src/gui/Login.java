package src.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class Login extends JPanel{
	
	private AuthView auth;
	
    public Login(AuthView auth) {
        setLayout(new BorderLayout());

        // Panel para el logo 
        JPanel panelLogo = new JPanel();
        panelLogo.setPreferredSize(new Dimension(750, 0)); // Ajustar el tamaño del logo
        panelLogo.setBackground(new Color(3,252,207));

        // Panel que creamos para los componentes necesarios para el login
        JPanel panelComponentes = new JPanel();
        panelComponentes.setBackground(Color.WHITE);

        panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));

        // Añadimos los paneles al panel principal (BorderLayout)
        add(panelLogo, BorderLayout.WEST);
        add(panelComponentes, BorderLayout.CENTER);

        // Creación de los componentes
        JLabel lblBienvenido = new JLabel("¡Bienvenido de vuelta!");
        JLabel lblUsername = new JLabel("Usuario o Email:");
        JTextField tfUsername = new JTextField();
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField tfPassword = new JPasswordField();
        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegister = new JButton("Registrarse");
        JLabel lblSlogan = new JLabel("©Devora todo lo que necesitas en un sitio");

        //Ajustes de dimensiones de los inputs y botones
        Dimension fieldSize = new Dimension(600, 60); //Tamaño de los inputs
        tfUsername.setPreferredSize(fieldSize);
        tfUsername.setMaximumSize(fieldSize); 
        tfPassword.setPreferredSize(fieldSize);
        tfPassword.setMaximumSize(fieldSize);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); 
        Dimension buttonSize = new Dimension(230, 45); // Tamaño de los botones
        btnLogin.setPreferredSize(buttonSize);
        btnRegister.setPreferredSize(buttonSize);
        btnRegister.setBackground(new Color(3,252,207));
        btnLogin.setBackground(new Color(3,219,252));
        panelBotones.add(btnLogin);
        panelBotones.add(btnRegister);
        panelBotones.setOpaque(false); 

        //Fuentes personalizacion (Pasar a un archivo diferente global)
        Font fuenteTitulos = new Font("Arial", Font.BOLD, 40);
        lblBienvenido.setFont(fuenteTitulos);
        Font fuenteSubtitulos = new Font("Arial",Font.BOLD,18);
        lblSlogan.setFont(fuenteSubtitulos);
        Font fuenteLabels = new Font("Arial", Font.BOLD,20);
        lblUsername.setFont(fuenteLabels);
        lblPassword.setFont(fuenteLabels);
        Font fuenteInputs = new Font("Arial",Font.PLAIN,20);
        tfUsername.setFont(fuenteInputs);
        tfPassword.setFont(fuenteInputs);
        Font fuenteBotones = new Font("Arial",Font.BOLD,16);
        btnLogin.setFont(fuenteBotones);
        btnRegister.setFont(fuenteBotones);
        
        //Editar Bordes de los botones
        btnLogin.setBorder(BorderFactory.createEmptyBorder());
        btnRegister.setBorder(BorderFactory.createEmptyBorder());
        
        // Centramos los componentes
        lblBienvenido.setAlignmentX(CENTER_ALIGNMENT);
        lblUsername.setAlignmentX(CENTER_ALIGNMENT);
        tfUsername.setAlignmentX(CENTER_ALIGNMENT);
        lblPassword.setAlignmentX(CENTER_ALIGNMENT);
        tfPassword.setAlignmentX(CENTER_ALIGNMENT);
        panelBotones.setAlignmentX(CENTER_ALIGNMENT);
        lblSlogan.setAlignmentX(CENTER_ALIGNMENT);

        // Añadimos los componentes al BoxLayout
        panelComponentes.add(Box.createVerticalGlue()); 
        panelComponentes.add(lblBienvenido);
        panelComponentes.add(Box.createVerticalStrut(120));
        panelComponentes.add(lblUsername);
        panelComponentes.add(Box.createVerticalStrut(15)); 
        panelComponentes.add(tfUsername);
        panelComponentes.add(Box.createVerticalStrut(30));
        panelComponentes.add(lblPassword);
        panelComponentes.add(Box.createVerticalStrut(15));
        panelComponentes.add(tfPassword);
        panelComponentes.add(Box.createVerticalStrut(30));
        panelComponentes.add(panelBotones); 
        panelComponentes.add(Box.createVerticalStrut(60));
        panelComponentes.add(lblSlogan);
        panelComponentes.add(Box.createVerticalGlue()); 
        
        //Efecto hover para el botón de register
        btnRegister.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
	
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnRegister.setBackground(new Color(104,255,226));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				btnRegister.setBackground(new Color(3,252,207));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				btnRegister.setBackground(new Color(0,179,146));
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				btnRegister.setBackground(new Color(104,255,226));
				
			}
        	
        });
        
        
        //Efecto hover del botón de login
        btnLogin.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogin.setBackground(new Color(82,232,255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnLogin.setBackground(new Color(3,219,252));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        //Cambiar panel al hacer click
        btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				auth.mostrarPanel("registerPanel");
			}
        	
        });;
    }

}
