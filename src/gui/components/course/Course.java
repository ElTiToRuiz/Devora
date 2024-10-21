package src.gui.components.course;

import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;

// this is a component for each course
public class Course extends JPanel {
    public Course(String courseName) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.setBackground(Pallette.TARJETAS_CURSOS.getColor());

        JLabel courseLabel = new JLabel(courseName);
        courseLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(courseLabel);

        this.add(Box.createVerticalGlue());
    }



}
