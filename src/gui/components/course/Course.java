package src.gui.components.course;

import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Course extends JPanel {
    public Course(String courseName, String imgPath) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.setBackground(Pallette.TEXTO_SECUNDARIO.getColor());
        this.setPreferredSize(new Dimension(300, 300));

        JLabel courseLabel = new JLabel(courseName);
        courseLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(courseLabel);

        // create and space for an img
        this.add(Box.createVerticalStrut(10));

        ImageIcon imageIcon = new ImageIcon(
                new ImageIcon(imgPath).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)
        );

        JLabel imgLabel = new JLabel(imageIcon);
        this.add(imgLabel);
    }
}
