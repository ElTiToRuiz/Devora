package src.gui.components.course;

import src.domain.Course;
import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoursesGrid extends JPanel {
    public CoursesGrid() {
        this.setLayout(new GridLayout(0, 3, 10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.setBackground(Pallette.SECCIONES.getColor());

        for (Course course : getAllCourses()){
            this.add(new CourseFront(course));
        }
    }

    // TEMPORAL
    private ArrayList<Course> getAllCourses(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Course> courses = objectMapper.readValue(new File("path/to/your/file.json"), new TypeReference<List<Course>>() {});
            // Use the courses list as needed
            courses.forEach(course -> System.out.println(course.name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
