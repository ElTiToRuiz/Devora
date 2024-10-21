package src.gui.components.principal;

import src.gui.components.course.CoursesGrid;

import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {
    private Principal() {
        super("Devora");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);
    }


    public static JFrame createPrincipal() {
        JFrame principal = new Principal();
        principal.add(new Header(), BorderLayout.NORTH);
        principal.add(new Sidebar(), BorderLayout.EAST);
        principal.add(new CoursesGrid(), BorderLayout.CENTER);
        return principal;
    }
}




