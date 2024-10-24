package src.gui.components.course;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class CourseDetail extends JFrame {
    private HashMap<String, Object> courseInfo;
    private final String courseName;

    public CourseDetail(String courseName){
        this.courseName = courseName;
        openCourseDetails();
    }

    private void openCourseDetails(){
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);


        JLabel detailsLabel = new JLabel("Details about " + this.courseName);
        detailsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(detailsLabel);

        this.setVisible(true);
    }
}
