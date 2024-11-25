package src.gui.components.principal;

import src.db.Database;
import src.gui.components.course.CoursesGrid;
import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {

    boolean showFilter;

    public Principal() {
        super("Devora");
        initializeDatabase();
        this.setUpPrincipal();
    }

    private void setUpPrincipal() {
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setBackground(Color.white);
        showFilter = false;
    }

    public JFrame createRoot() {
        this.add(new Header(), BorderLayout.NORTH);
        this.add(createContainer(), BorderLayout.CENTER);
        return this;
    }

    JPanel createContainer(){
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(Pallette.FONDO.getColor());
        if (showFilter) {
            container.add(new Filter(), BorderLayout.NORTH);
        } else {
            container.add(new Filter.FilterHidden(), BorderLayout.NORTH);
        }
        container.add(new CoursesGrid(), BorderLayout.CENTER);
        return container;
    }
    
    private void initializeDatabase() {
        try {
            Database.getInstance();
            System.out.println("Todo OK");// Establece la conexión
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al conectar con la base de datos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Salir si la conexión falla
        }
    }
}








