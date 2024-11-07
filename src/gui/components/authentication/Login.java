package src.gui.components.authentication;

import src.utils.Pallette;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import src.utils.Pallette;

@SuppressWarnings("serial")
public class Login extends JPanel{
	
	private AuthView auth;
	//TODO Abstraer código y dejarlo claro
    public Login(AuthView auth) {
        setLayout(new BorderLayout());

        // Panel para el logo 
        JPanel panelLogo = new JPanel();
        panelLogo.setPreferredSize(new Dimension(1125,0)); 
        
        //Poner imagen en el panel
        
        ImageIcon jadeFondo = new ImageIcon("src/media/jade-fondo.jpg");
        Image img = jadeFondo.getImage();
        Image imgEscalada = img.getScaledInstance(1500, 1500, Image.SCALE_SMOOTH);
        
        JLabel lblJadeFondo = new JLabel(new ImageIcon(imgEscalada));
        
        panelLogo.add(lblJadeFondo);
        
        panelLogo.setBackground(new Color(3,252,207));

        // Panel que creamos para los componentes necesarios para el login
        JPanel panelComponentes = new JPanel();
        panelComponentes.setBackground(Color.WHITE);

        panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));

        // Añadimos los paneles al panel principal (BorderLayout)
        add(panelLogo, BorderLayout.WEST);
        add(panelComponentes, BorderLayout.CENTER);

        // Creación de los componentes
        JLabel lblBienvenido = new JLabel("Inicio de sesión");
        JLabel lblInstrucciones = new JLabel("Incia sesión en una de tus cuenta para poder comprar en Devora.");
        JLabel lblUsername = new JLabel("Usuario o Email:");
        JTextField tfUsername = new JTextField();
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField tfPassword = new JPasswordField();
        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegister = new JButton("Volver");
        JLabel lblAvisoCuenta = new JLabel("¿Aún no tienes cuenta? ");
        JLabel lblCrearCuenta = new JLabel("Crear cuenta");
        
        //Creación Wraps (Paneles) para los inputs usuario y password
        JPanel panelUsuarioGrande = new JPanel(new FlowLayout());
        JPanel panelPasswordGrande = new JPanel(new FlowLayout());
        JPanel panelUsuario = new JPanel(new FlowLayout());
        JPanel panelPassword = new JPanel(new FlowLayout());
        
        //Ajustes de los paneles
        panelUsuario.setSize(new Dimension(550, 50));
        panelPassword.setSize(new Dimension(500, 50));
        
        panelUsuario.setBackground(new Color(221,221,221));
        panelPassword.setBackground(new Color(221,221,221));
        
        
        //Creación logos input
        ImageIcon iconoUsuario = new ImageIcon("src/media/user-icon.png");
        
        Image imgUsuario = iconoUsuario.getImage();
        Image escaladaUsuario = imgUsuario.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        JLabel labelUsuario = new JLabel(new ImageIcon(escaladaUsuario));
        
        ImageIcon iconoLock = new ImageIcon("src/media/lock-icon.png");
        
        Image imgLock = iconoLock.getImage();
        Image imagenEscalada = imgLock.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        JLabel labelLock = new JLabel(new ImageIcon(imagenEscalada));
        
        
        //Ajustes de dimensiones de los inputs y botones
        Dimension fieldSize = new Dimension(400, 50); //Tamaño de los inputs
        tfUsername.setPreferredSize(fieldSize);
        tfUsername.setMaximumSize(fieldSize); 
        tfPassword.setPreferredSize(fieldSize);
        tfPassword.setMaximumSize(fieldSize);
        
        //Ajuste estilo inputs
        tfUsername.setBackground(new Color(221,221,221));
        tfUsername.setBorder(BorderFactory.createEmptyBorder());
        tfPassword.setBackground(new Color(221,221,221));
        tfPassword.setBorder(BorderFactory.createEmptyBorder());
        
        //Añadir los tf a los paneles
        panelUsuario.add(tfUsername);
        panelUsuario.add(labelUsuario);
        panelPassword.add(tfPassword);
        panelPassword.add(labelLock);
        
        panelUsuarioGrande.add(panelUsuario);
        panelPasswordGrande.add(panelPassword);
        
        panelUsuarioGrande.setOpaque(false);
        panelPasswordGrande.setOpaque(false);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); 
        Dimension buttonSize = new Dimension(230, 45); // Tamaño de los botones
        btnLogin.setPreferredSize(buttonSize);
        btnRegister.setPreferredSize(buttonSize);
        btnRegister.setBackground(Pallette.COLOR_PRINCIPAL.getColor());
        btnLogin.setBackground(new Color(3,219,252));
        panelBotones.add(btnLogin);
        panelBotones.add(btnRegister);
        panelBotones.setOpaque(false); 
        
        //Panel para el texto de crear cuenta
        JPanel panelTexto = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTexto.setOpaque(false);
        panelTexto.setBorder(BorderFactory.createEmptyBorder(90,0,0,0));
        panelTexto.add(lblAvisoCuenta);
        panelTexto.add(lblCrearCuenta);

        //Ajsutes label Instrucciones
        lblInstrucciones.setForeground(new Color(93,93,93));
        
        //Fuentes personalizacion (Pasar a un archivo diferente global)
        Font fuenteTitulos = new Font("Arial", Font.BOLD, 40);
        lblBienvenido.setFont(fuenteTitulos);
        Font fuenteSubtitulos = new Font("Arial",Font.BOLD,18);
        Font fuenteLabels = new Font("Arial", Font.BOLD,20);
        lblUsername.setFont(fuenteLabels);
        lblPassword.setFont(fuenteLabels);
        Font fuenteInputs = new Font("Arial",Font.PLAIN,20);
        lblInstrucciones.setFont(fuenteInputs);
        tfUsername.setFont(fuenteInputs);
        tfPassword.setFont(fuenteInputs);
        Font fuenteBotones = new Font("Arial",Font.BOLD,16);
        btnLogin.setFont(fuenteBotones);
        btnRegister.setFont(fuenteBotones);
        lblAvisoCuenta.setFont(fuenteBotones);
        lblCrearCuenta.setFont(fuenteBotones);
        
        //Editar Bordes de los botones
        btnLogin.setBorder(BorderFactory.createEmptyBorder());
        btnRegister.setBorder(BorderFactory.createEmptyBorder());
        
        // Centramos los componentes
        lblBienvenido.setAlignmentX(CENTER_ALIGNMENT);
        lblInstrucciones.setAlignmentX(CENTER_ALIGNMENT);
        lblUsername.setAlignmentX(CENTER_ALIGNMENT);
        panelUsuarioGrande.setAlignmentX(CENTER_ALIGNMENT);
        lblPassword.setAlignmentX(CENTER_ALIGNMENT);
        panelPasswordGrande.setAlignmentX(CENTER_ALIGNMENT);
        panelBotones.setAlignmentX(CENTER_ALIGNMENT);
        panelTexto.setAlignmentX(CENTER_ALIGNMENT);

        // Añadimos los componentes al BoxLayout
        panelComponentes.add(Box.createVerticalGlue()); 
        panelComponentes.add(lblBienvenido);
        panelComponentes.add(Box.createVerticalStrut(120));
        panelComponentes.add(lblInstrucciones);
        panelComponentes.add(Box.createVerticalStrut(15));
        panelComponentes.add(panelUsuarioGrande);
        panelComponentes.add(Box.createVerticalStrut(0));
        panelComponentes.add(panelPasswordGrande);
        panelComponentes.add(Box.createVerticalStrut(30));
        panelComponentes.add(panelBotones); 
        panelComponentes.add(Box.createVerticalStrut(90));
        panelComponentes.add(panelTexto);
        panelComponentes.add(Box.createVerticalGlue()); 
        
        //Listener para los placeholder
        tfUsername.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tfUsername.getText().equals("Usuario o email")){
					tfUsername.setText("");
					tfUsername.setForeground(Color.black);
				};
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (tfUsername.getText().isEmpty()) {
					tfUsername.setText("Usuario o email");
					tfUsername.setForeground(new Color(174,176,177));
				};
			}
        });
        
        tfPassword.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tfPassword.getText().equals("Contraseña")){
					tfPassword.setText("");
					tfPassword.setForeground(Color.black);
				};
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (tfPassword.getText().isEmpty()) {
					tfPassword.setText("Contraseña");
					tfPassword.setForeground(new Color(174,176,177));
				};
			}
        });
        //Efecto hover para el botón de register
        btnRegister.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnRegister.setBackground(Pallette.COLOR_HOVER_PRINCIPAL.getColor());
				btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				btnRegister.setBackground(Pallette.COLOR_HOVER_PRINCIPAL.getColor());
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
        
        //Cambiar panel al hacer click
        btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				auth.dispose();
			}
        	
        });;
        
        lblCrearCuenta.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblCrearCuenta.setForeground(Color.cyan);;
				lblCrearCuenta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				lblCrearCuenta.setForeground(lblAvisoCuenta.getForeground());
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        lblCrearCuenta.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				auth.dispose();
				new AuthView(false);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
    }

}
