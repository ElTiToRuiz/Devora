package src.gui.components.editarCursos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseEditorPanel extends JFrame {

    private JTable courseTable;
    private DefaultTableModel tableModel;

    public CourseEditorPanel() {
        setTitle("Editar Cursos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cambiar fondo ventana
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(60, 180, 140)); // Color fondo general
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // Modelo de tabla
        String[] columnNames = {"IDCurso", "Nombre del Curso", "Descripción Breve", "Idioma", "Precio (€)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        courseTable = new JTable(tableModel);
        courseTable.setRowHeight(50);
        courseTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        courseTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        courseTable.getColumnModel().getColumn(2).setPreferredWidth(350);
        courseTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        courseTable.getColumnModel().getColumn(4).setPreferredWidth(80);

        // Colores de la tabla
        courseTable.setBackground(new Color(230, 255, 240)); // Fondo tabla
        courseTable.setForeground(Color.DARK_GRAY); // Color texto tabla
        courseTable.setSelectionBackground(new Color(100, 200, 160)); // Fondo selección
        courseTable.setSelectionForeground(Color.WHITE); // Texto selección

        // Panel tabla con scroll
        JScrollPane tableScrollPane = new JScrollPane(courseTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableScrollPane.setBackground(new Color(60, 180, 140));
        tableScrollPane.setOpaque(true);
        
        // Agregar cursos de ejemplo
        tableModel.addRow(new Object[]{1, "Matemáticas", "Curso de matemáticas básicas", "Inglés", 9.99});
        tableModel.addRow(new Object[]{2, "Historia", "Historia universal", "Castellano", 18.99});
        tableModel.addRow(new Object[]{3, "Programación", "Curso de introducción a Java", "Euskera", 7.99});

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(50, 150, 120)); 
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        JButton addButton = createStyledButton("Agregar Curso");
        JButton editButton = createStyledButton("Editar Curso");
        JButton deleteButton = createStyledButton("Eliminar Curso");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Componentes frame
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Botón de agregar
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCurso();
            }
        });

        // Botón de editar
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCurso();
            }
        });

        // Botón de eliminar
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCurso();
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(100, 200, 160)); // Fondo botón
        button.setForeground(Color.WHITE); // Texto botón
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void agregarCurso() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField languageField = new JTextField();
        JTextField precioField = new JTextField();

        Object[] message = {
            "ID:", idField,
            "Nombre del Curso:", nameField,
            "Descripción:", descriptionField,
            "Idioma:", languageField,
            "Precio:", precioField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Agregar Curso", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Verificar si los campos están vacíos
            if (idField.getText().isEmpty() || nameField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                languageField.getText().isEmpty() || precioField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar si el nombre, la descripción y el idioma contienen números
            if (!isTextOnly(nameField.getText()) || !isTextOnly(descriptionField.getText()) || !isTextOnly(languageField.getText())) {
                JOptionPane.showMessageDialog(null, "El Nombre del Curso, la Descripción y el Idioma no deben contener números.", "Entrada no válida", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String description = descriptionField.getText();
                String language = languageField.getText();
                double precio = Double.parseDouble(precioField.getText());
                tableModel.addRow(new Object[]{id, name, description, language, precio});
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: ID debe ser un número entero y Precio debe ser un número decimal.", "Entrada no válida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarCurso() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String description = (String) tableModel.getValueAt(selectedRow, 2);
            String language = (String) tableModel.getValueAt(selectedRow, 3);
            Double precio = (Double) tableModel.getValueAt(selectedRow, 4);

            JTextField idField = new JTextField(String.valueOf(id));
            JTextField nameField = new JTextField(name);
            JTextField descriptionField = new JTextField(description);
            JTextField languageField = new JTextField(language);
            JTextField precioField = new JTextField(String.valueOf(precio));

            Object[] message = {
                "ID:", idField,
                "Nombre del Curso:", nameField,
                "Descripción:", descriptionField,
                "Idioma:", languageField,
                "Precio (€):", precioField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Editar Curso", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // Verificar si los campos están vacíos
                if (idField.getText().isEmpty() || nameField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                    languageField.getText().isEmpty() || precioField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Verificar si el nombre, la descripción y el idioma contienen números
                if (!isTextOnly(nameField.getText()) || !isTextOnly(descriptionField.getText()) || !isTextOnly(languageField.getText())) {
                    JOptionPane.showMessageDialog(null, "El Nombre del Curso, la Descripción y el Idioma no deben contener números.", "Entrada no válida", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    tableModel.setValueAt(Integer.parseInt(idField.getText()), selectedRow, 0);
                    tableModel.setValueAt(nameField.getText(), selectedRow, 1);
                    tableModel.setValueAt(descriptionField.getText(), selectedRow, 2);
                    tableModel.setValueAt(languageField.getText(), selectedRow, 3);
                    tableModel.setValueAt(Double.parseDouble(precioField.getText()), selectedRow, 4);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error: ID debe ser un número entero y Precio debe ser un número decimal.", "Entrada no válida", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un curso para editar.");
        }
    }

    private void eliminarCurso() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un curso para eliminar.");
        }
    }

    private boolean isTextOnly(String str) {
        return str.chars().allMatch(Character::isLetter);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CourseEditorPanel frame = new CourseEditorPanel();
            frame.setVisible(true);
        });
    }
}
