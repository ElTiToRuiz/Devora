package src.gui.components.course;

import javax.swing.*;

import src.db.Database;

import java.awt.*;
import java.util.ArrayList;

public class CoursesGrid extends JPanel {

    // Hacer panelCursos una variable de instancia (no estático)
    private static JPanel panelCursos;  // Debería ser estático si vas a acceder a él sin una instancia

    public CoursesGrid() {
        // Inicializar correctamente el panelCursos
        panelCursos = new JPanel();
        panelCursos.setLayout(new GridLayout(0, 3, 10, 10)); 
        panelCursos.setBackground(Color.white);

        // Crear un hilo para cargar los cursos desde la base de datos
        Thread t = new Thread(new Runnable() {
            public void run() {
                Database db = Database.getInstance();
                ArrayList<CourseFront> lista = Database.obtenerCursos();

                // Usamos invokeLater para actualizar la UI en el hilo principal
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        mostrarCursos(lista);  // Actualizar la UI con los cursos obtenidos
                    }
                });
            }
        });

        // Iniciar el hilo de carga
        t.start();

        // Añadir scroll para los cursos
        JScrollPane scrollPane = new JScrollPane(panelCursos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 

        // Configurar el layout y agregar el JScrollPane
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    // Hacer el método mostrarCursos estático para que pueda ser llamado sin una instancia
    public static void mostrarCursos(ArrayList<CourseFront> cursos) {
        // Limpiar el panel antes de añadir los nuevos cursos
        panelCursos.removeAll();

        for (CourseFront curso : cursos) {
            // Suponiendo que CourseFront extiende JPanel o se puede agregar al panel
            panelCursos.add(curso);
        }
        
        // Forzar la validación del layout y repaint para redibujar el panel correctamente
        panelCursos.revalidate();
        panelCursos.repaint();
    }
}

