package src.gui.components.course;

import src.domain.Course;
import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class CourseFront extends JPanel {
    private int id;
    private String courseName;
    private String imgPath;

    public CourseFront(int id, String name, String imgPath) {
    	this.id = id;
        this.courseName = name;
        this.imgPath = imgPath;
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setupCourse();
        addCourseName();
        addCourse();
        addClickListener();
    }

    private void setupCourse() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.setBackground(Pallette.TEXTO_SECUNDARIO.getColor());
        this.setPreferredSize(new Dimension(300, 300));
    }

    private void addCourseName(){
        JLabel courseNameLabel = new JLabel(this.courseName);
        courseNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(courseNameLabel);
        this.add(Box.createVerticalStrut(10));
    }

    private void addCourse(){
        ImageIcon imageIcon = new ImageIcon(
                new ImageIcon(this.imgPath).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)
        );
        JLabel imgLabel = new JLabel(imageIcon);
        this.add(imgLabel);
    }

    private void addClickListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new CourseDetail(id);
            }
        });
    }


}
