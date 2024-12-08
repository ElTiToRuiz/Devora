package src.gui.components.course;

import src.domain.Course;
import src.gui.components.principal.Filter;
import src.utils.Pallette;
import src.db.*;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoursesGrid extends JPanel {
    
	JPanel panelCursos;
	
    public CoursesGrid() {
        JPanel panelCursos = new JPanel();
        panelCursos.setLayout(new GridLayout(0, 3, 10, 10)); 
        panelCursos.setBackground(Color.white);

        Thread t = new Thread(new Runnable() {
            public void run() {
                // Usamos invokeLater para asegurar que las actualizaciones de la UI se hagan en el hilo principal
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ArrayList<CourseFront> cursosFiltrados = Filter.filterCourses();
                        for(CourseFront curso : cursosFiltrados) {
                            panelCursos.add(curso);
                        }
                        panelCursos.revalidate();  // Forzar la validación del layout
                        panelCursos.repaint();     // Forzar el repaint para que se dibuje el panel correctamente
                    }
                });
            }
        });

        t.start();

        //Añadir scroll para los cursos
        JScrollPane scrollPane = new JScrollPane(panelCursos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    // TEMPORAL
//    private ArrayList<Course> getAllCourses(){
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            List<Course> courses = objectMapper.readValue(new File("path/to/your/file.json"), new TypeReference<List<Course>>() {});
//            // Use the courses list as needed
//            courses.forEach(course -> System.out.println(course.name));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
