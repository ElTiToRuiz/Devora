package src.gui.components.principal;

import src.utils.Pallette;
import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {
    public Header() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Pallette.ENCABEZADOS.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));


        JLabel titleLabel = new JLabel("Devora");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(titleLabel);


        this.add(Box.createHorizontalGlue());

        JButton homeButton = new JButton("Home");
        JButton settingsButton = new JButton("Settings");
        JButton helpButton = new JButton("Help");

        this.add(homeButton);
        this.add(Box.createHorizontalStrut(10)); // Spacing
        this.add(settingsButton);
        this.add(Box.createHorizontalStrut(10)); // Spacing
        this.add(helpButton);
    }
}
