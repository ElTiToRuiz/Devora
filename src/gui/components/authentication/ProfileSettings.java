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

    public ProfileSettings(int id) {
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(MAXIMIZED_BOTH);
        setTitle("Configuración del perfil");
        setBackground(Color.white);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Barra lateral (sidebar) con más tamaño
        JPanel panelSidebar = new JPanel();
        panelSidebar.setBackground(new Color(26, 36, 52));
        panelSidebar.setPreferredSize(new Dimension(400, getHeight())); // Aumentamos el ancho de la barra lateral
        
        // Usamos un layout adecuado para centrar los componentes
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS)); 
        
        // Añadimos los labels en el panelSidebar
        JLabel labelConfiguracion = crearLabel("Configuración");
        labelConfiguracion.setAlignmentX(Component.LEFT_ALIGNMENT);  // Centrado horizontal

        JLabel labelCompras = crearLabel("Historial de Compras");
        labelCompras.setAlignmentX(Component.LEFT_ALIGNMENT);  // Centrado horizontal
        
        JLabel labelSaldo = crearLabel("Gestionar Saldo");
        labelSaldo.setAlignmentX(Component.LEFT_ALIGNMENT);  // Centrado horizontal

        
        // Añadimos los labels al panelSidebar
        panelSidebar.add(Box.createVerticalStrut(360)); // Espacio arriba
        panelSidebar.add(labelConfiguracion);
        panelSidebar.add(Box.createVerticalStrut(50));  // Espacio entre labels
        panelSidebar.add(labelCompras);
        panelSidebar.add(Box.createVerticalStrut(50));
        panelSidebar.add(labelSaldo);
        try {
			if (Database.esVendedor(id)) {
			    JLabel labelDashboard = crearLabel("Acceder A Dashboard");
			    labelDashboard.setAlignmentX(Component.LEFT_ALIGNMENT);  // Centrado horizontal

			    // Agregar un MouseListener si deseas hacer algo cuando se haga clic en el label
			    labelDashboard.addMouseListener(new MouseAdapter() {
			        public void mouseClicked(MouseEvent e) {
			        	System.out.println("Accediendo al Dashboard...");
			        	new Dashboard(id);
			        }
			    });
			    
			    panelSidebar.add(Box.createVerticalStrut(50));  // Espacio entre labels
			    panelSidebar.add(labelDashboard);  // Agregar el label "Acceder A Dashboard" al panel
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        panelSidebar.add(Box.createVerticalGlue());

        // Panel de configuración principal
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
        
        JPanel panelPedidos = new JPanel();
        
        panelSettings.add(panelTitulo,BorderLayout.NORTH); panelSettings.add(panelInputs,BorderLayout.CENTER); panelSettings.add(panelBtn,BorderLayout.SOUTH);
        
        // Añadimos los paneles principales al JFrame
        add(panelSidebar, BorderLayout.WEST);  // Cambié a BorderLayout.WEST para que esté a la izquierda
        add(panelSettings, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProfileSettings(2);
            }
        });
    }
    
    private JLabel crearLabel(String name) {
    	JLabel lbl = new JLabel(name);
    	lbl.setFont(new Font("Arial",Font.BOLD,24));
    	lbl.setForeground(Color.black);
    	lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	lbl.setBorder(BorderFactory.createEmptyBorder(0,70,0,0));
    	lbl.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lbl.setForeground(Color.white);
			}
    		
    	});
    	
		return lbl;
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

