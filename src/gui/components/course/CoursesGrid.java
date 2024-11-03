package src.gui.components.course;

import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;

public class CoursesGrid extends JPanel {
    
    public CoursesGrid() {
        JPanel panelCursos = new JPanel();
        panelCursos.setLayout(new GridLayout(0, 3, 10, 10)); 
        panelCursos.setBackground(Color.white);

        for (int i = 0; i < 20; i++) {
            panelCursos.add(new CourseFront("Curso " + i, "src/media/react.png"));
        }

        //Añadir scroll para los cursos
        JScrollPane scrollPane = new JScrollPane(panelCursos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

//        for (Course course : getAllCourses()){
//            this.add(new CourseFront(course));
//        }
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
