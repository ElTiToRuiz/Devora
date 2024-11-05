package src.gui.components.editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PerfilUsuario extends JFrame {

    private JTextField campoNombre;
    private JTextField campoApellidos;
    private JTextField campoEmail;
    private JTextField campoDireccion;
    private JPasswordField campoContrasena;
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JComboBox<String> comboTipoUsuario;

    private Image fondo;

    public PerfilUsuario() {
        fondo = new ImageIcon(getClass().getResource("/src/media/jade-fondo.jpg")).getImage();

        // Configuración de la ventana principal con tamaño más grande
        setTitle("Editar Perfil de Usuario");
        setSize(700, 500); // Cambiamos el tamaño de la ventana a 700x500
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configuración del panel central
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new GridLayout(6, 2, 15, 15)); // Espacio entre filas y columnas incrementado
        panel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Reducimos el margen alrededor del panel

        // Campo Nombre
        JLabel etiquetaNombre = new JLabel("👤 Nombre:");
        etiquetaNombre.setForeground(Color.YELLOW);
        campoNombre = crearCampoTexto();
        panel.add(etiquetaNombre);
        panel.add(campoNombre);

        // Campo Apellidos
        JLabel etiquetaApellidos = new JLabel("👤 Apellidos:");
        etiquetaApellidos.setForeground(Color.YELLOW);
        campoApellidos = crearCampoTexto();
        panel.add(etiquetaApellidos);
        panel.add(campoApellidos);

        // Campo Email
        JLabel etiquetaEmail = new JLabel("✉️ Email:");
        etiquetaEmail.setForeground(Color.YELLOW);
        campoEmail = crearCampoTexto();
        panel.add(etiquetaEmail);
        panel.add(campoEmail);

        // Campo Dirección 
        JLabel etiquetaDireccion = new JLabel("🏠 Dirección:");
        etiquetaDireccion.setForeground(Color.YELLOW);
        campoDireccion = crearCampoTexto();
        panel.add(etiquetaDireccion);
        panel.add(campoDireccion);

        // Campo Contraseña 
        JLabel etiquetaContrasena = new JLabel("🔑 Contraseña:");
        etiquetaContrasena.setForeground(Color.YELLOW);
        campoContrasena = new JPasswordField();
        campoContrasena.setBorder(new LineBorder(new Color(180, 180, 255), 1, true));
        campoContrasena.setBackground(new Color(255, 255, 255));
        panel.add(etiquetaContrasena);
        panel.add(campoContrasena);

        // ComboBox para tipo de usuario
        JLabel etiquetaTipoUsuario = new JLabel("👤 Tipo de Usuario:");
        etiquetaTipoUsuario.setForeground(Color.YELLOW);
        String[] tiposUsuario = {"Estudiante", "Profesor", "Administrador", "Creador"};
        comboTipoUsuario = new JComboBox<>(tiposUsuario);
        panel.add(etiquetaTipoUsuario);
        panel.add(comboTipoUsuario);

        // Botón Guardar
        botonGuardar = crearBoton("Guardar", new Color(0, 128, 0));
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    guardarDatos();
                } else {
                    JOptionPane.showMessageDialog(PerfilUsuario.this,
                            "Por favor, completa todos los campos.",
                            "Campos Incompletos",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Botón Cancelar
        botonCancelar = crearBoton("Cancelar", new Color(200, 0, 0));
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado = JOptionPane.showConfirmDialog(PerfilUsuario.this,
                        "No se han guardado los cambios. ¿Estás seguro de que quieres cancelar?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                
                if (resultado == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setBorder(new LineBorder(new Color(180, 180, 255), 1, true));
        campo.setBackground(new Color(255, 255, 255));
        return campo;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(new LineBorder(color.darker(), 1, true));
        return boton;
    }

    private boolean validarCampos() {
        if (campoNombre.getText().trim().isEmpty() ||
            campoApellidos.getText().trim().isEmpty() ||
            campoEmail.getText().trim().isEmpty() ||
            campoDireccion.getText().trim().isEmpty() ||
            campoContrasena.getPassword().length == 0) {
            return false;
        }

        String email = campoEmail.getText();
        if (!email.equals(email.toLowerCase()) || !email.contains("@")) {
            JOptionPane.showMessageDialog(PerfilUsuario.this,
                    "El email debe estar en minúsculas y contener '@'.",
                    "Error de Email",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String contrasena = new String(campoContrasena.getPassword());
        if (!contrasena.matches(".*[A-Z].*") || !contrasena.matches(".*[0-9].*")) {
            JOptionPane.showMessageDialog(PerfilUsuario.this,
                    "La contraseña debe contener al menos una mayúscula y un número.",
                    "Error de Contraseña",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private void guardarDatos() {
        String nombre = campoNombre.getText();
        String apellidos = campoApellidos.getText();
        String email = campoEmail.getText();
        String direccion = campoDireccion.getText();
        String tipoUsuario = (String) comboTipoUsuario.getSelectedItem();
        
        JPanel panelMensaje = new JPanel();
        panelMensaje.setLayout(new BorderLayout());
        panelMensaje.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel mensaje = new JLabel("Datos guardados correctamente:");
        mensaje.setFont(new Font("Arial", Font.BOLD, 16));
        mensaje.setHorizontalAlignment(SwingConstants.CENTER);
        mensaje.setForeground(new Color(0, 128, 0));

        JTextArea info = new JTextArea("Nombre: " + nombre + "\nApellidos: " + apellidos + "\nEmail: " + email + "\nDirección: " + direccion + "\nTipo de Usuario: " + tipoUsuario);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        info.setForeground(Color.BLACK);
        info.setOpaque(false);
        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setBorder(null);
        info.setBackground(new Color(255, 255, 255, 0));

        panelMensaje.add(mensaje, BorderLayout.NORTH);
        panelMensaje.add(info, BorderLayout.CENTER);
        
        int resultado = JOptionPane.showConfirmDialog(this, panelMensaje, "Confirmación", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PerfilUsuario ventana = new PerfilUsuario();
            ventana.setVisible(true);
        });
    }
}
