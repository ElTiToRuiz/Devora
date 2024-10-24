package src.gui.components.course;

import src.domain.Course;
import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;


public class CoursesGrid extends JPanel {
    public CoursesGrid() {
        this.setLayout(new GridLayout(0, 3, 10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.setBackground(Pallette.SECCIONES.getColor());

        for (int i = 0; i < 6; i++) {
            this.add(new CourseFront("Curso " + i, "src/media/react.png"));
        }

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
