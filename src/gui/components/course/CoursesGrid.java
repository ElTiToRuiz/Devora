package src.gui.components.course;

import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;

public class CoursesGrid extends JPanel {
    public CoursesGrid() {
        this.setLayout(new GridLayout(0, 3, 10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.setBackground(Pallette.SECCIONES.getColor());

        for (int i = 0; i < 9; i++) {
            this.add(new Course("Course " + (i + 1)));
        }
    }
}
