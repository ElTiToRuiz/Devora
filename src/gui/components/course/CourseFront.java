package src.gui.components.course;

import src.domain.Course;
import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class CourseFront extends JPanel {
    private int id;
    private Double price;
    private String courseName;
    private String categoria;
    private Double rating;
    private int duracion;
    private String imgPath;
    private JLabel courseNameLabel; // Declara courseNameLabel como una variable de instancia

    public CourseFront(int id, Double price, String name, String categoria, Double rating, int duracion, String imgPath) {
        this.id = id;
        this.courseName = name;
        this.categoria = categoria;
        this.rating = rating;
        this.duracion = duracion;
        this.imgPath = imgPath;
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setupCourse();
        addCourse();
        addCourseName();
        addClickListener();
    }

    private void setupCourse() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.setBackground(new Color(251,251,251));
        this.setPreferredSize(new Dimension(400, 400));
    }

    private void addCourseName() {
        courseNameLabel = new JLabel(this.courseName); // No es necesario declararlo de nuevo
        courseNameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        courseNameLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        courseNameLabel.setForeground(Color.black);
        this.add(courseNameLabel, BorderLayout.SOUTH);
    }

    private void addCourse() {
        ImageIcon imgCurso = new ImageIcon(new ImageIcon(this.imgPath).getImage().getScaledInstance(700, 650, Image.SCALE_SMOOTH));
        JLabel lblImgCurso = new JLabel();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                Image scaledImage = imgCurso.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                lblImgCurso.setIcon(new ImageIcon(scaledImage));
            }
        });

        this.add(lblImgCurso, BorderLayout.CENTER);
    }

    private void addClickListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                new CourseDetail(id);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                courseNameLabel.setForeground(Pallette.COLOR_PRINCIPAL.getColor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                courseNameLabel.setForeground(Color.black);
            }
        });
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
