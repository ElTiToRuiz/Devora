package src.gui.components.authentication;

import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import src.db.Database;
import src.gui.components.editor.Dashboard;
import javax.swing.*;
import src.utils.Pallette;

public class ProfileSettings extends JFrame {
    int id;
    private CardLayout cardLayout;
    private JPanel panelMain,panelSettings,panelSaldo,panelDashboard,panelHistorial;

    
    public ProfileSettings(int id) {
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(MAXIMIZED_BOTH);
        setTitle("Configuración del perfil");
        setBackground(Color.white);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Panel cardLayout
        cardLayout = new CardLayout();
        panelMain = new JPanel(cardLayout);
        
        JPanel panelSettings = crearPanelSettings();
        JPanel panelSaldo = crearPanelSaldo();
        JPanel panelHistorial = crearPanelHistorial();

        panelMain.add(panelSettings, "panelSettings");
        panelMain.add(panelHistorial, "panelHistorial");
        panelMain.add(panelSaldo, "panelSaldo");
        
        // Barra lateral (sidebar) con más tamaño
        JPanel panelSidebar = new JPanel();
        panelSidebar.setBackground(new Color(26, 36, 52));
        panelSidebar.setPreferredSize(new Dimension(400, getHeight())); // Aumentamos el ancho de la barra lateral
        
        // Usamos un layout adecuado para centrar los componentes
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS)); 
        
        // Añadimos los labels en el panelSidebar
        JPanel panelCuenta = crearPanel("Mi cuenta", "src/media/home-icon.png", 1);
        panelCuenta.setAlignmentX(Component.LEFT_ALIGNMENT);  // Centrado horizontal

        JPanel labelCompras = crearPanel("Historial de Compras","src/media/home-icon.png", 2);
        labelCompras.setAlignmentX(Component.LEFT_ALIGNMENT);  // Centrado horizontal
        
        JPanel labelSaldo = crearPanel("Gestionar Saldo", "src/media/home-icon.png", 3);
        labelSaldo.setAlignmentX(Component.LEFT_ALIGNMENT);  // Centrado horizontal

        JPanel panelVolver = crearPanel("Volver","src/media/back-icon.png",5);
        panelVolver.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Añadimos los labels al panelSidebar
        panelSidebar.add(Box.createVerticalStrut(200)); // Espacio arriba
        panelSidebar.add(panelCuenta);
        panelSidebar.add(Box.createVerticalStrut(-100));  // Espacio entre labels
        panelSidebar.add(labelCompras);
        panelSidebar.add(Box.createVerticalStrut(-100));
        panelSidebar.add(labelSaldo);
        try {
			if (Database.esVendedor(id)) {
			    JPanel labelDashboard = crearPanel("Acceder A Dashboard","src/media/home-icon.png", 4);
			    labelDashboard.setAlignmentX(Component.LEFT_ALIGNMENT);  // Centrado horizontal
			    panelSidebar.add(Box.createVerticalStrut(-100));  // Espacio entre labels
			    panelSidebar.add(labelDashboard);  // Agregar el label "Acceder A Dashboard" al panel
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        panelSidebar.add(Box.createVerticalStrut(40));
        panelSidebar.add(panelVolver);
        panelSidebar.add(Box.createVerticalGlue());
        
        add(panelSidebar, BorderLayout.WEST); 
        add(panelMain, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProfileSettings(1);
            }
        });
    }
    
    private JPanel crearPanel(String texto, String iconoPath, int idPanel) {
    	JPanel panel = new JPanel(new BorderLayout());
        panel.setSize(new Dimension(400, 50));

        JLabel textoLabel = new JLabel(texto);
        textoLabel.setFont(new Font("Arial", Font.BOLD, 22));
        textoLabel.setForeground(Color.gray);

        ImageIcon iconoPerfil = new ImageIcon(iconoPath);
        Image img = iconoPerfil.getImage();

        Image iconoPerfilEscalado = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(iconoPerfilEscalado));
        imgLabel.setBorder(BorderFactory.createEmptyBorder(0, 55, 0, 40));

        panel.add(imgLabel, BorderLayout.WEST);
        panel.add(textoLabel, BorderLayout.CENTER);

        panel.setBackground(new Color(26, 36, 52));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                textoLabel.setForeground(Color.white);
                
            }
            @Override
            public void mouseExited(MouseEvent e) {
                textoLabel.setForeground(Color.gray);
                super.mouseExited(e);
            }
			@Override
			public void mouseClicked(MouseEvent e) {
				String namePanel = "";
				switch (idPanel) {
	            case 1:
	            	namePanel = "panelSettings";
	                break;
	            case 2:
	            	namePanel = "panelSaldo";
	                break;
	            case 3:
	            	namePanel = "panelHistorial";
	                break;
	            case 4:
	            	new Dashboard(4);
				case 5:
					dispose();
				}
					
				
                cardLayout.show(panelMain, namePanel);
			}
        });

        return panel;
    }
   
    private JPanel crearPanelSettings() {
    	JPanel panelSettings = new JPanel(new BorderLayout());
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Editar Perfil");
        lblTitulo.setAlignmentX(LEFT_ALIGNMENT);
        lblTitulo.setFont(new Font("Arial",Font.BOLD,42));
        panelTitulo.add(lblTitulo);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(180,0,0,1000));
        
        JPanel panelInputs = new JPanel(new BorderLayout());
        JPanel panelInputUserMail = new JPanel(new GridLayout(1,2,0,0));
        
        JPanel panelUser = new JPanel();
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setFont(new Font("Arial",Font.BOLD,22));
        JTextField tfUser = new JTextField();
        tfUser.setBorder(BorderFactory.createEmptyBorder(20,150,20,150));
        panelUser.add(lblUser); panelUser.add(tfUser);
        
        JPanel panelMail = new JPanel();
        JLabel lblMail = new JLabel("Email:");
        lblMail.setFont(new Font("Arial",Font.BOLD,22));
        JTextField tfMail = new JTextField();
        tfMail.setBorder(BorderFactory.createEmptyBorder(20,250,20,250));
        panelMail.add(lblMail); panelMail.add(tfMail);
        
        panelInputUserMail.setBorder(BorderFactory.createEmptyBorder(100,0,0,125));
        panelInputUserMail.add(panelUser); panelInputUserMail.add(panelMail);
        
        JPanel panelPassword = new JPanel();
        JLabel lblPassword = new JLabel("Contraseña: ");
        lblPassword.setFont(new Font("Arial",Font.BOLD,22));
        JPasswordField pfPassword = new JPasswordField();
        pfPassword.setBorder(BorderFactory.createEmptyBorder(20,250,20,250));
        panelPassword.add(lblPassword); panelPassword.add(pfPassword); 
        
        panelPassword.setBorder(BorderFactory.createEmptyBorder(0,0,375,570));
        panelInputs.add(panelInputUserMail,BorderLayout.CENTER); panelInputs.add(panelPassword,BorderLayout.SOUTH);
        
        JPanel panelBtn = new JPanel(new GridLayout(1,2,50,50));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0,150,100,150));
        
        JButton btnUpdate = new JButton("Actualizar Datos");
        btnUpdate.setBackground(Pallette.COLOR_PRINCIPAL.getColor());
        btnUpdate.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));
        btnUpdate.setFont(new Font("Arial",Font.BOLD,20));
        btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnUpdate.setForeground(Color.white);
        
        panelBtn.add(btnUpdate);
        
        btnUpdate.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				new JOptionPane();
			}
        	
        });
        try {
			if(!Database.esVendedor(id)) {
			    JButton btnRegVendedor = new JButton("Registrarse Como Vendedor");
			    btnRegVendedor.setBackground(Color.gray);
			    btnRegVendedor.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));
			    btnRegVendedor.setFont(new Font("Arial",Font.BOLD,20));
			    btnRegVendedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			    btnRegVendedor.setForeground(Color.white);
			    btnRegVendedor.addMouseListener(new MouseAdapter() {
			        @Override
			        public void mouseClicked(MouseEvent e) {
			            try {
			                // Asignar al usuario como vendedor
			                Database.asignarVendedor(id);
			                
			                // Desactivar y ocultar el botón "Registrarse Como Vendedor"
			                btnRegVendedor.setEnabled(false); // Desactivamos el botón para evitar más clics
			                btnRegVendedor.setVisible(false); // Lo hacemos invisible
			                
			                JButton btnDashboard = new JButton("Acceder a Dashboard");
			                panelBtn.add(btnDashboard);
			                btnDashboard.setBackground(Color.cyan);
			                btnDashboard.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));
			                btnDashboard.setFont(new Font("Arial",Font.BOLD,20));
			                btnDashboard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			                btnDashboard.setForeground(Color.white);
			                btnDashboard.addMouseListener(new MouseAdapter() {

								@Override
								public void mouseClicked(MouseEvent e) {
									new Dashboard(id);
								}
			                	
			                });
			                // Refrescar la interfaz
			                panelBtn.revalidate();
			                panelBtn.repaint();
			            } catch (SQLException e1) {
			                e1.printStackTrace();
			            }
			        }
			    });

			    panelBtn.add(btnRegVendedor);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
                
        panelSettings.add(panelTitulo,BorderLayout.NORTH); panelSettings.add(panelInputs,BorderLayout.CENTER); panelSettings.add(panelBtn,BorderLayout.SOUTH);
        return panelSettings;
    }
    
    private JPanel crearPanelSaldo() {
    	JPanel panel = new JPanel(new BorderLayout());
    	Database db = Database.getInstance();
    	JLabel lblTextoSaldo = new JLabel("Tu saldo actual es de: " + Database.conseguirSaldo(id));
    	lblTextoSaldo.setFont(new Font("Arial",Font.BOLD,52));
    	lblTextoSaldo.setBorder(BorderFactory.createEmptyBorder(300,150,0,0));
    	
    	JPanel panelGestionSaldo = new JPanel(new GridLayout(1,2,0,0));
    	JPanel panelAñadir = new JPanel();
    	JButton btnAñadir = new JButton("Añadir Fondos");
    	btnAñadir.addMouseListener(new MouseAdapter() {
    		
    	});
    	btnAñadir.setBorder(BorderFactory.createEmptyBorder(20,175,20,175));
    	btnAñadir.setFont(new Font("Arial",Font.BOLD,20));
    	btnAñadir.setBackground(Color.green);
    	btnAñadir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	btnAñadir.setForeground(Color.white);
    	panelAñadir.add(btnAñadir);
    	
    	JPanel panelRetirar = new JPanel();
    	JButton btnRetirar = new JButton("Retirar Fondos");
    	btnRetirar.setFont(new Font("Arial",Font.BOLD,20));
    	btnRetirar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	btnRetirar.addMouseListener(new MouseAdapter() {
    		
    	});
    	btnRetirar.setBorder(BorderFactory.createEmptyBorder(20,175,20,175));
    	btnRetirar.setBackground(Color.red);
    	btnRetirar.setForeground(Color.white);
    	panelRetirar.add(btnRetirar);
    	panelGestionSaldo.add(panelAñadir); panelGestionSaldo.add(panelRetirar);
    	panelGestionSaldo.setBorder(BorderFactory.createEmptyBorder(100,25,0,0));
    	
    	panel.add(lblTextoSaldo,BorderLayout.NORTH);
    	panel.add(panelGestionSaldo,BorderLayout.CENTER);
    	
    	return panel;
    }
    
    private JPanel crearPanelHistorial() {
    	JPanel panel = new JPanel();
    	panel.setBackground(Color.green);
    	return panel;
    }
    //TODO Completar los metodos para coger el user, mail, contraseña de la db y 
    //TODO ponerlo directamente en los textfields, completar metodo UpdateDatos.
    //TODO Crear un lugar para interactuar con al db
    
    private String getUser() {
    	return null;
    }
    
    private String getMail() {
    	return null;
    }
    
    private String getPassword() {
    	return null;
    }
    
    private String updateDatos() {
    	return null;
    }
    
    private boolean comprobarVendedor() {
    	return true;
    }
}

