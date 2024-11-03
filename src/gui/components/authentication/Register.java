package src.gui.components.authentication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class Register extends JPanel{
	
	private AuthView auth;
	
	public Register(AuthView auth) {
		setLayout(new BorderLayout());
		
		//Panel para el Logo (Improvement: Exportarlo como componenente para facilitar reutilizacion)
		JPanel panelLogo = new JPanel();
        panelLogo.setPreferredSize(new Dimension(325, 0)); // Ajustar el tamaño del logo
        panelLogo.setBackground(new Color(3,252,207));
        
        // Panel que creamos para los componentes necesarios para el login
        JPanel panelComponentes = new JPanel();
        panelComponentes.setBackground(Color.WHITE);

        panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));
        
        // Añadimos los paneles al panel principal (BorderLayout)
        add(panelLogo, BorderLayout.WEST);
        add(panelComponentes, BorderLayout.CENTER);
        
        //Creación de componentes
        JLabel lblCuenta = new JLabel("Crear Cuenta");
        JLabel lblUsuario = new JLabel("Usuario");
        JTextField tfUsuario = new JTextField();
        JLabel lblEmail = new JLabel("Email");
        JTextField tfEmail = new JTextField();
        JLabel lblPassword = new JLabel("Contraseña");
        JPasswordField tfPassword = new JPasswordField();
        JRadioButton rbNormal = new JRadioButton("Estándar");
        JRadioButton rbFamilia = new JRadioButton("Familia");
        JRadioButton rbEstudiante = new JRadioButton("Estudiante");
        JRadioButton rbEmpresa = new JRadioButton("Empresa");
        JButton btnRegister = new JButton("Registrarme");
        JButton btnLogin = new JButton("Volver");
        
        //TODO Crear función que automatice la creación del botón + el tamaño de los mismo
        //Ajustes de dimensiones de los inputs y botones 
        Dimension fieldSize = new Dimension(600, 60); //Tamaño de los inputs
        tfUsuario.setPreferredSize(fieldSize);
        tfUsuario.setMaximumSize(fieldSize); 
        tfEmail.setPreferredSize(fieldSize);
        tfEmail.setPreferredSize(fieldSize);
        tfPassword.setPreferredSize(fieldSize);
        tfPassword.setMaximumSize(fieldSize);
        
        //Ajustes de dimensiones de los botones
        Dimension buttonSize = new Dimension(230, 45); 
        btnLogin.setPreferredSize(buttonSize);
        btnRegister.setPreferredSize(buttonSize);
        btnRegister.setBackground(new Color(3,252,207));
        btnLogin.setBackground(new Color(3,219,252));
        
        
        //Agrupamos los componentes por sección en distintos paneles
        JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));
        panelUsuario.add(lblUsuario);
        panelUsuario.add(tfUsuario);
        panelUsuario.setOpaque(false);
        JPanel panelEmail = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));
        panelEmail.add(lblEmail);
        panelEmail.add(tfEmail);
        panelEmail.setOpaque(false);
        JPanel panelPassword = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));
        panelPassword.add(lblPassword);
        panelPassword.add(tfPassword);
        panelPassword.setOpaque(false);
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));
        panelBtn.add(btnRegister);
        panelBtn.add(btnLogin);
        panelBtn.setOpaque(false);
        JPanel panelEleccion = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));
        panelEleccion.add(rbNormal);
        panelEleccion.add(rbEstudiante);
        panelEleccion.add(rbFamilia);
        panelEleccion.add(rbEmpresa);
        panelEleccion.setOpaque(false);
        
        //Creamos grupo de botones
        ButtonGroup gpRb = new ButtonGroup();
        gpRb.add(rbNormal);
        gpRb.add(rbEstudiante);
        gpRb.add(rbFamilia);
        gpRb.add(rbEmpresa);
        
        rbNormal.setOpaque(false);
        rbEstudiante.setOpaque(false);
        rbFamilia.setOpaque(false);
        rbEmpresa.setOpaque(false);
        
        //Fuentes personalizadas
        Font fuenteTitulos = new Font("Arial", Font.BOLD, 40);
        lblCuenta.setFont(fuenteTitulos);
        
        Font fuenteInputs = new Font("Arial",Font.PLAIN,20);
        tfUsuario.setFont(fuenteInputs);
        tfEmail.setFont(fuenteInputs);
        tfPassword.setFont(fuenteInputs);
        
        Font fuenteLabels = new Font("Arial", Font.BOLD,24);
        lblUsuario.setFont(fuenteLabels);
        lblEmail.setFont(fuenteLabels);
        lblPassword.setFont(fuenteLabels);
        
        Font fuenteBotones = new Font("Arial",Font.BOLD,16);
        btnLogin.setFont(fuenteBotones);
        btnRegister.setFont(fuenteBotones);
        rbNormal.setFont(fuenteBotones);
        rbFamilia.setFont(fuenteBotones);
        rbEstudiante.setFont(fuenteBotones);
        rbEmpresa.setFont(fuenteBotones);
        
        //Editar bordes de los botones
        btnLogin.setBorder(BorderFactory.createEmptyBorder());
        btnRegister.setBorder(BorderFactory.createEmptyBorder());
        
        //Alineación Central
        lblCuenta.setAlignmentX(CENTER_ALIGNMENT);
        panelUsuario.setAlignmentX(CENTER_ALIGNMENT);
        panelEmail.setAlignmentX(CENTER_ALIGNMENT);
        panelPassword.setAlignmentX(CENTER_ALIGNMENT);
        panelEleccion.setAlignmentX(CENTER_ALIGNMENT);
        panelBtn.setAlignmentX(CENTER_ALIGNMENT);
        
        //Alineacion de los labels
        lblUsuario.setAlignmentX(LEFT_ALIGNMENT);
        
        //Añadimos al BoxLayout
        panelComponentes.add(Box.createVerticalGlue());
        panelComponentes.add(lblCuenta);
        panelComponentes.add(Box.createVerticalStrut(60));
        panelComponentes.add(panelUsuario);
        panelComponentes.add(Box.createVerticalStrut(5));
        panelComponentes.add(panelEmail);
        panelComponentes.add(Box.createVerticalStrut(5));
        panelComponentes.add(panelPassword);
        panelComponentes.add(Box.createVerticalStrut(5));
        panelComponentes.add(panelEleccion);
        panelComponentes.add(Box.createVerticalStrut(30));
        panelComponentes.add(panelBtn);
        panelComponentes.add(Box.createVerticalGlue());
        
        //Efecto hover para el botón de register
        btnRegister.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
	
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnRegister.setBackground(new Color(104,255,226));
				btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
				btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        
        
        btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				auth.mostrarPanel("loginPanel");
			}
        	
        });
        
	}
}
