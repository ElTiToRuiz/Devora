package src.gui.components.authentication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.mindrot.jbcrypt.BCrypt;

import src.db.Database;

public class Register extends JPanel {

    private AuthView auth;

    public Register(AuthView auth) {
        setLayout(new BorderLayout());

        // Panel de logo
        JPanel panelLogo = new JPanel();
        panelLogo.setPreferredSize(new Dimension(1125, 0)); // Ajuste tamaño logo
        panelLogo.setBackground(new Color(3, 252, 207));

        // Panel de componentes
        JPanel panelComponentes = new JPanel();
        panelComponentes.setBackground(Color.WHITE);
        panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));

        // Imagen del logo
        ImageIcon jadeFondo = new ImageIcon("src/media/jade-fondo.jpg");
        Image img = jadeFondo.getImage();
        Image imgEscalada = img.getScaledInstance(1500, 1500, Image.SCALE_SMOOTH);
        JLabel lblJadeFondo = new JLabel(new ImageIcon(imgEscalada));
        panelLogo.add(lblJadeFondo);

        add(panelLogo, BorderLayout.WEST);
        add(panelComponentes, BorderLayout.CENTER);

        // Crear componentes
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

        // Configuración de componentes
        Dimension fieldSize = new Dimension(600, 60);
        tfUsuario.setPreferredSize(fieldSize);
        tfEmail.setPreferredSize(fieldSize);
        tfPassword.setPreferredSize(fieldSize);

        Dimension buttonSize = new Dimension(230, 45);
        btnLogin.setPreferredSize(buttonSize);
        btnRegister.setPreferredSize(buttonSize);
        btnRegister.setBackground(new Color(3, 252, 207));
        btnLogin.setBackground(new Color(3, 219, 252));

        // Agrupar los componentes
        JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panelUsuario.add(lblUsuario);
        panelUsuario.add(tfUsuario);
        JPanel panelEmail = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panelEmail.add(lblEmail);
        panelEmail.add(tfEmail);
        JPanel panelPassword = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panelPassword.add(lblPassword);
        panelPassword.add(tfPassword);
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panelBtn.add(btnRegister);
        panelBtn.add(btnLogin);
        JPanel panelEleccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panelEleccion.add(rbNormal);
        panelEleccion.add(rbEstudiante);
        panelEleccion.add(rbFamilia);
        panelEleccion.add(rbEmpresa);

        ButtonGroup gpRb = new ButtonGroup();
        gpRb.add(rbNormal);
        gpRb.add(rbEstudiante);
        gpRb.add(rbFamilia);
        gpRb.add(rbEmpresa);

        // Configuración de fuentes
        Font fuenteTitulos = new Font("Arial", Font.BOLD, 40);
        lblCuenta.setFont(fuenteTitulos);
        Font fuenteInputs = new Font("Arial", Font.PLAIN, 20);
        tfUsuario.setFont(fuenteInputs);
        tfEmail.setFont(fuenteInputs);
        tfPassword.setFont(fuenteInputs);
        Font fuenteLabels = new Font("Arial", Font.BOLD, 24);
        lblUsuario.setFont(fuenteLabels);
        lblEmail.setFont(fuenteLabels);
        lblPassword.setFont(fuenteLabels);
        Font fuenteBotones = new Font("Arial", Font.BOLD, 16);
        btnLogin.setFont(fuenteBotones);
        btnRegister.setFont(fuenteBotones);
        rbNormal.setFont(fuenteBotones);
        rbFamilia.setFont(fuenteBotones);
        rbEstudiante.setFont(fuenteBotones);
        rbEmpresa.setFont(fuenteBotones);

        // Añadir al BoxLayout
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

        // Acción para el botón de registro
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validación de campos
                String usuario = tfUsuario.getText();
                String email = tfEmail.getText();
                String password = new String(tfPassword.getPassword());

                if (usuario.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser completados.");
                    return;
                }

                // Validación del formato del email
                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(null, "El email no tiene un formato válido.");
                    return;
                }

                //Guardar los datos en la db
		        Database db = Database.getInstance(); // Obtener instancia de la base de datos
		        if (db.registrarDatos(usuario, email, BCrypt.hashpw(password, BCrypt.gensalt()))) {
		            JOptionPane.showMessageDialog(null, "Registro compleatado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		            // Redirigir al usuario a la siguiente ventana
					auth.dispose();
					new AuthView(true);
		        } else {
		            JOptionPane.showMessageDialog(null, "Ha ocurrido un error, revisar consola", "Error", JOptionPane.ERROR_MESSAGE);
		        }
                
                JOptionPane.showMessageDialog(null, "¡Registro exitoso!");

                // Redirigir a la pantalla de login
                auth.mostrarPanel("loginPanel");
            }
        });

        // Acción para el botón de login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auth.mostrarPanel("loginPanel");
            }
        });
    }
}
