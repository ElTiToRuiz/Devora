package src.gui.components.course;

import src.domain.Course;
import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

public class CourseFront extends JPanel {
    private int id;
    private float price;
    private float rating;
    private int duracion;
    private List<String> categorias;
    private String courseName;
    private String imgPath;

    public CourseFront(int id, float price, String name,  List<String> categorias, float rating,
                       int duracion, String imgPath) {
    	this.id = id;
        this.price = price;
        this.categorias = categorias;
        this.rating = rating;
        this.duracion = duracion;
        this.courseName = name;
        this.imgPath = imgPath;
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setupCourse();
        addCourseName();
        addCourse();
        addClickListener();
    }

    public float getPrice() {return price;}
    public float getRating() {return rating;}
    public float getDuracion() {return duracion;}
    public List<String> getCategorias() {return categorias;}

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