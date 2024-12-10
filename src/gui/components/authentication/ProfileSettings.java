package src.gui.components.authentication;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Random;
import src.db.Database;
import src.domain.Course;
import src.gui.components.editor.Dashboard;
import src.gui.components.principal.Header;
import src.gui.components.cart.PanelCurso;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import src.utils.Pallette;

public class ProfileSettings extends JFrame {
    int id;
    private CardLayout cardLayout;
    private JPanel panelMain,panelSettings,panelSaldo,panelDashboard,panelHistorial;
    private Random rand = new Random();
    private DefaultTableModel modelo;
    
    public ProfileSettings(int id) {
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(MAXIMIZED_BOTH);
        setTitle("Configuración del perfil");
        setBackground(Color.white);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel cardLayout
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
        panelSidebar.setPreferredSize(new Dimension(400, getHeight())); // Ancho fijo de la barra lateral
        
        // Usamos BoxLayout para centrar los componentes de manera eficiente
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS)); 
        
        // Añadimos los labels en el panelSidebar
        JPanel panelCuenta = crearPanel("Mi cuenta", "src/media/person-50px.png", 1);
        panelCuenta.setAlignmentX(Component.LEFT_ALIGNMENT);  // Centrado horizontal

        JPanel labelCompras = crearPanel("Historial de Compras","src/media/history-50px.png", 3);
        labelCompras.setAlignmentX(Component.LEFT_ALIGNMENT);  // Centrado horizontal
        
        JPanel labelSaldo = crearPanel("Gestionar Saldo", "src/media/balance-50px.png", 2);
        labelSaldo.setAlignmentX(Component.LEFT_ALIGNMENT);  // Centrado horizontal

        JPanel panelCerrarSesion = crearPanelCerrarSesion();
        panelCerrarSesion.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel panelVolver = crearPanel("Volver","src/media/back-icon.png",5);
        panelVolver.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        
        panelSidebar.add(Box.createVerticalStrut(20)); 
        panelSidebar.add(panelCuenta);
        panelSidebar.add(Box.createVerticalStrut(20));  
        panelSidebar.add(labelCompras);
        panelSidebar.add(Box.createVerticalStrut(20));  
        panelSidebar.add(labelSaldo);
        
        try {
            if (Database.esVendedor(Header.id)) {
                JPanel labelDashboard = crearPanel("Acceder A Dashboard","src/media/chart-icon.png", 4);
                labelDashboard.setAlignmentX(Component.LEFT_ALIGNMENT);  
                panelSidebar.add(Box.createVerticalStrut(20));
                panelSidebar.add(labelDashboard);  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        panelSidebar.add(Box.createVerticalStrut(20));  // Espacio entre labels
        panelSidebar.add(panelVolver);
        panelSidebar.add(Box.createVerticalGlue()); // Empuja los componentes hacia arriba

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
        // Crear panel con BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        
        // Definir tamaño preferido del panel (más pequeño)
        panel.setPreferredSize(new Dimension(380, 60)); // Ajusta el tamaño de la altura a 60 píxeles

        // Etiqueta de texto
        JLabel textoLabel = new JLabel(texto);
        textoLabel.setFont(new Font("Arial", Font.BOLD, 22));
        textoLabel.setForeground(Color.gray);

        // Cargar y ajustar la imagen del icono
        ImageIcon iconoPerfil = new ImageIcon(iconoPath);
        Image img = iconoPerfil.getImage();
        Image iconoPerfilEscalado = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH); // Tamaño más pequeño
        JLabel imgLabel = new JLabel(new ImageIcon(iconoPerfilEscalado));
        imgLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Ajustar borde para centrar mejor el icono

        // Añadir la imagen y el texto al panel
        panel.add(imgLabel, BorderLayout.WEST);
        panel.add(textoLabel, BorderLayout.CENTER);

        // Configurar el fondo y el cursor
        panel.setBackground(new Color(26, 36, 52));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Configuración del evento de entrada/salida del ratón
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                textoLabel.setForeground(Color.white); // Cambiar color de texto al pasar el ratón
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                textoLabel.setForeground(Color.gray); // Restaurar el color cuando se sale
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
                        new Dashboard(4); // Asegúrate de que el `Dashboard` se abre correctamente
                        break;
                    case 5:
                        dispose(); // Cerrar la ventana actual
                        break;
                }
                // Mostrar el panel correspondiente
                if (!namePanel.isEmpty()) {
                    cardLayout.show(panelMain, namePanel); // Cambiar entre panels
                }
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
        JPanel panelWrapLogout = new JPanel();
        JPanel panelCerrarSesion = crearPanelCerrarSesion();
        panelWrapLogout.add(panelCerrarSesion);
        panelWrapLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Header.id = -1;
				Header.isLogged = false;
				Header.updateBtns();
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
        	
        });
        panelWrapLogout.setBorder(BorderFactory.createEmptyBorder(40,1100,0,0));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(180,0,0,1000));
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelWrapLogout,BorderLayout.NORTH);
        panelTop.add(panelTitulo,BorderLayout.CENTER);
        
        JPanel panelInputs = new JPanel();
        panelInputs.setLayout(new BoxLayout(panelInputs, BoxLayout.Y_AXIS));
        
        JPanel panelUser = new JPanel();
        panelUser.setMaximumSize(new Dimension(1500,100));
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setFont(new Font("Arial",Font.BOLD,26));
        JTextField tfUser = new JTextField(50);
        tfUser.setBorder(BorderFactory.createEmptyBorder(20,100,20,100));
        tfUser.setFont(new Font("Arial",Font.PLAIN,24));
        String username = Database.conseguirUser(Header.id);
        tfUser.setText(username);
        panelUser.add(lblUser); panelUser.add(tfUser);
        panelUser.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel panelMail = new JPanel();
        panelMail.setMaximumSize(new Dimension(1500,100));
        JLabel lblMail = new JLabel("Email:    ");
        lblMail.setFont(new Font("Arial",Font.BOLD,26));
        JTextField tfMail = new JTextField(50);
        tfMail.setBorder(BorderFactory.createEmptyBorder(20,100,20,100));
        tfMail.setFont(new Font("Arial",Font.PLAIN,24));
        String mail = Database.conseguirMail(Header.id);
        tfMail.setText(mail);
        panelMail.add(lblMail); panelMail.add(tfMail);
        panelMail.setAlignmentX(Component.LEFT_ALIGNMENT);
                
        panelInputs.add(Box.createVerticalStrut(50));
        panelInputs.add(panelUser);
        panelInputs.add(Box.createVerticalStrut(10));
        panelInputs.add(panelMail);
        panelInputs.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        
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
				String nuevoUsername = tfUser.getText();
				String nuevoMail = tfMail.getText();
				
		        if (nuevoUsername.isEmpty() || nuevoMail.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Por favor, ingresa los datos correctamente antes de actualizarlos.");
		            return;
		        }
		        
		        
		        try {
					if(Database.comprobarUsuario(nuevoUsername)) {
						JOptionPane.showMessageDialog(null, "Ese usuario ya está cogido");
						return;
					}
				} catch (HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		        try {
					if(Database.comprobarMail(nuevoMail)) {
						JOptionPane.showMessageDialog(null, "Ese email ya está en uso");
						return;
					}
				} catch (HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
	              if (!nuevoMail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
	                    JOptionPane.showMessageDialog(null, "El email no tiene un formato válido.");
	                    return;
	                }
				
		        boolean resultado;
				try {
					resultado = Database.actualizarDatos(nuevoUsername, nuevoMail,Header.id);
			        if (resultado) {
			            JOptionPane.showMessageDialog(null, "Los datos se actualizaron correctamente.");
			        } else {
			            JOptionPane.showMessageDialog(null, "Hubo un error al actualizar el email.");
			        }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        	
        });
        try {
			if(!Database.esVendedor(Header.id)) {
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
			                Database.asignarVendedor(Header.id);
			                
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
                
        panelSettings.add(panelTop,BorderLayout.NORTH); panelSettings.add(panelInputs,BorderLayout.CENTER); panelSettings.add(panelBtn,BorderLayout.SOUTH);
        return panelSettings;
    }
    
    private JPanel crearPanelSaldo() {
    	JPanel panel = new JPanel(new BorderLayout());
    	Double saldoActual = Database.conseguirSaldo(Header.id);
    	JLabel lblTextoSaldo = new JLabel("Tu saldo actual es de: " + saldoActual + "€");
    	lblTextoSaldo.setFont(new Font("Arial",Font.BOLD,52));
    	lblTextoSaldo.setBorder(BorderFactory.createEmptyBorder(300,150,0,0));
    	
    	JPanel panelGestionSaldo = new JPanel(new GridLayout(1,2,0,0));
    	JPanel panelAñadir = new JPanel();
    	JButton btnAñadir = new JButton("Añadir Fondos");
    	btnAñadir.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String input = JOptionPane.showInputDialog("Ingresa la cantidad que quieras añadir a tu saldo: ");

		        try {
		            // Convertir la entrada a un valor double
		            double valor = Double.parseDouble(input);
		            Double saldoActualizado = Database.conseguirSaldo(Header.id) + valor;
		            Database.actualizarBalance(Header.id, saldoActualizado);
		            // Mostrar el valor ingresado
		            lblTextoSaldo.setText("Tu saldo actual es de: " + saldoActualizado + "€");
		            JOptionPane.showMessageDialog(null, "¡Has añadido: " + valor+"€ de saldo!" );
		        } catch (NumberFormatException | SQLException e1) {
		            // Si la entrada no es un número válido, mostrar un mensaje de error
		            JOptionPane.showMessageDialog(null, "Error: Debes ingresar una cantidad válida.");
		        }
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
			}
    		
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
			@Override
			public void mouseClicked(MouseEvent e) {
				String input = JOptionPane.showInputDialog("¿Cuánto deseas retirar?");
                try {
                    double cantidadRetiro = Double.parseDouble(input);
                    if (cantidadRetiro > Database.conseguirSaldo(Header.id)) {
                        JOptionPane.showMessageDialog(null, "Error: No tienes suficiente saldo para realizar el retiro.");
                    } else if (cantidadRetiro <= 0) {
                        JOptionPane.showMessageDialog(null, "Error: La cantidad a retirar debe ser mayor que cero.");
                    } else {
                        Double saldoActualizado = Database.conseguirSaldo(Header.id) - cantidadRetiro;
                        Database.actualizarBalance(Header.id, saldoActualizado);
                        lblTextoSaldo.setText("Tu saldo actual es de: " + saldoActualizado + "€");
                        
                        JOptionPane.showMessageDialog(null, "Retiro exitoso. Has retirado: " + String.format("%.2f", cantidadRetiro) + "€.");
                    }
                } catch (NumberFormatException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Debes ingresar un número válido.");
                }
			}
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
    	JPanel panel = new JPanel(new BorderLayout());
    	panel.setBackground(Color.white);
    	JLabel lblHistorial = new JLabel("Historial de compras");
    	lblHistorial.setFont(new Font("Arial",Font.BOLD,52));
    	lblHistorial.setBorder(BorderFactory.createEmptyBorder(100,100,0,0));
    	Map<Integer,String> datosHistorial = Database.conseguirDatosHistorial(Header.id);
    	
        modelo = new DefaultTableModel();
        modelo.addColumn("Curso");
        modelo.addColumn("Fecha de compra");
    	
        for (Map.Entry<Integer, String> entry : datosHistorial.entrySet()) {
            int idCurso = entry.getKey();
            String fechaCompra = entry.getValue();
            Course curso = Database.conseguirCurso(idCurso);
            modelo.addRow(new Object[]{new PanelCurso(curso.getName(),curso.getInstructor(),curso.getRating(),rand.nextInt(4294 - 141 + 1) + 141,curso.getDuracion(),curso.getClases(),curso.getImgPath()), fechaCompra});
        }
        
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(125);
        tabla.setShowGrid(false);
        tabla.setShowHorizontalLines(false);
        tabla.getColumnModel().getColumn(0).setCellRenderer(new CursoCellRenderer());
        tabla.getTableHeader().setVisible(false); 
        tabla.setBackground(Color.white);
        tabla.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (comp instanceof JLabel) {
                    JLabel label = (JLabel) comp;
                    label.setHorizontalAlignment(SwingConstants.CENTER); 
                    label.setFont(new Font("Arial", Font.PLAIN, 20));    
                }
                return comp;
            }
        });
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBackground(Color.white);
        panel.add(lblHistorial,BorderLayout.NORTH);
        panel.add(scrollPane,BorderLayout.CENTER);
    	
    	return panel;
    }
    
    private JPanel crearPanelCerrarSesion() {
    	JPanel panel = new JPanel(new BorderLayout());
    	panel.setMaximumSize(new Dimension(300,50));
    	

        JLabel textoLabel = new JLabel("Cerrar Sesión");
        textoLabel.setFont(new Font("Arial", Font.BOLD, 22));
        textoLabel.setForeground(Color.white);
        textoLabel.setBorder(BorderFactory.createEmptyBorder(20,0,20,20));
        
        ImageIcon iconoPerfil = new ImageIcon("src/media/logout-50px.png");
        Image img = iconoPerfil.getImage();

        Image iconoPerfilEscalado = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(iconoPerfilEscalado));
        imgLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 40));

        panel.add(imgLabel, BorderLayout.WEST);
        panel.add(textoLabel, BorderLayout.CENTER);
        
        panel.setBackground(new Color(255, 99, 71));
        
        textoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	
        return panel;
    }

    private class CursoCellRenderer extends JPanel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Renderizar el JPanel con la información del curso
            if (value instanceof PanelCurso) {
                return (PanelCurso) value;
            }
            return this;
        }
    }
}

