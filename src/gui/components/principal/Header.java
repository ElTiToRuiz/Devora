package src.gui.components.principal;

import src.gui.components.authentication.AuthView;
import src.gui.components.cart.Cart;
import src.gui.components.editarCursos.CourseEditorPanel;
import src.gui.components.editor.PerfilUsuario;
import src.utils.Pallette;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    
    //Generación de la parte donde va la "Marca" de la App
    private void leftSideHeader(JPanel panel) {
        JLabel titleLabel = new JLabel("Devora");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 38));
        titleLabel.setForeground(Pallette.TITULOS.getColor());
        panel.add(titleLabel);
    }

    //Generación de la barra de Busqueda
    private void barraBusqueda(JPanel panel) {
    	
    	//Se usa un panel, y dentro un JLabel y JTextField para poder poner la imagen de la lupa en la misma barra de busqueda
    	
    	JPanel panelBusqueda = new JPanel(new BorderLayout());
    	panelBusqueda.setPreferredSize(new Dimension(900,50));
    	panelBusqueda.setBackground(Color.white);
    	
        JTextField tfBusqueda = new JTextField(20);
        tfBusqueda.setPreferredSize(new Dimension(900, 50));
        tfBusqueda.setBorder(BorderFactory.createEmptyBorder());
        
        //Fuente tamaño
        tfBusqueda.setFont(new Font("Arial",Font.PLAIN,18));
        
        //Se agrega el PlaceHolder puede ser que haya que cambiar esto, ya que lo coge como default
        tfBusqueda.setText("Busca cualquier cosa");
        
        tfBusqueda.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tfBusqueda.getText().equals("Busca cualquier cosa")){
					tfBusqueda.setText("");
				};
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (tfBusqueda.getText().isEmpty()) {
					tfBusqueda.setText("Busca cualquier cosa");
				};
			}
        });
        
        //Agregar logo
        
        ImageIcon iconoLupa = new ImageIcon("src/media/search-icon.png");
        
        Image img = iconoLupa.getImage();
        Image imagenEscalada = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        JLabel labelLupa = new JLabel(new ImageIcon(imagenEscalada));
        
        labelLupa.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        panelBusqueda.add(tfBusqueda,BorderLayout.CENTER);
        panelBusqueda.add(labelLupa,BorderLayout.WEST);
        
        panel.add(panelBusqueda);
    }

    //Vista de un usuario que está registrado
    private void userLoged(JPanel panel) {

        ImageIcon iconoPerfil = new ImageIcon("src/media/user-icon.png");
        ImageIcon iconoCarrito = new ImageIcon("src/media/cart-icon.png");
        ImageIcon iconoCursos = new ImageIcon("src/media/lapiz.png");
        
        //Reescalar la imagen para que quede bien en el header
        Image img = iconoPerfil.getImage();
        Image iconoPerfilEscalado = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        
        Image img1 = iconoCarrito.getImage();
        Image iconoCarritoEscalado = img1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        
        Image img2 = iconoCursos.getImage();
        Image iconoCursosEscalado = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        
        //Creación de los labels
        JLabel labelPerfil = new JLabel(new ImageIcon(iconoPerfilEscalado));
        JLabel labelCarrito = new JLabel(new ImageIcon(iconoCarritoEscalado));
        JLabel labelCursos = new JLabel(new ImageIcon(iconoCursosEscalado));
        
        labelPerfil.setBorder(BorderFactory.createEmptyBorder(10,10,10,20));
        labelCarrito.setBorder(BorderFactory.createEmptyBorder(10,10,10,20));
        labelCursos.setBorder(BorderFactory.createEmptyBorder(10,10,10,20));

        
        //Crear hovers para los labels
        labelPerfil.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PerfilUsuario perfil = new PerfilUsuario();
                perfil.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
			public void mouseEntered(MouseEvent arg0) {
				labelPerfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        labelCarrito.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
		        new Cart();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				labelCarrito.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
        	
        });
        
        labelCursos.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	CourseEditorPanel editor = new CourseEditorPanel();
            	editor.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelCursos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}
        });

            
        
        panel.add(labelCursos);
        panel.add(labelCarrito);
        panel.add(labelPerfil);
    }

    
    //Vista de un usuario que no está registrado
    private void notUserLoged(JPanel panel) {
        JButton loginButton = createHeaderButton("Iniciar sesión");
        JButton registerButton = createHeaderButton("Regístrate");
        
        //Personalización botón registrarse
        registerButton.setFont(new Font("Arial",Font.BOLD,18));
        registerButton.setBorder(BorderFactory.createEmptyBorder());
        registerButton.setBackground(Color.cyan);
        
        //Personalización botón inicio de sesión
        loginButton.setPreferredSize(new Dimension(150,50));
        loginButton.setFont(new Font("Arial",Font.BOLD,18));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        loginButton.setOpaque(false);
        
        //Lógica botones
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
        
        //Lógica botones hover
        loginButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				loginButton.setOpaque(true);
				loginButton.setBackground(Color.gray);
				loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				loginButton.setOpaque(false);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
        	
        });
        
        registerButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
        });

        panel.add(loginButton);
        panel.add(registerButton);
    }

    private JButton createHeaderButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 50));
        button.setBackground(Pallette.BOTONES.getColor());
        return button;
    }
}
