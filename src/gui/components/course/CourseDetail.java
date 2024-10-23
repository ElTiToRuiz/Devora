package src.gui.components.course;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class CourseDetail {
    private HashMap<String, Object> courseInfo;
    private final String courseName;

    public CourseDetail(String courseName){
        this.courseName = courseName;
        openCourseDetails();
    }

    private void openCourseDetails(){
        JFrame courseDetails = new JFrame();
        courseDetails.setSize(800, 600);
        courseDetails.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        courseDetails.setLocationRelativeTo(null);


        JLabel detailsLabel = new JLabel("Details about " + this.courseName);
        detailsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        courseDetails.add(detailsLabel);

        courseDetails.setVisible(true);
    }
}
