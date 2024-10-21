package src.gui.components.principal;

import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    public Sidebar() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // delete all the margin
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.setBackground(Pallette.SIDEBAR_FONDO.getColor());

        JButton homeButton = createSidebarButton("Home");
        JButton settingsButton = createSidebarButton("Settings");
        JButton helpButton = createSidebarButton("Help");

        this.add(homeButton);
        this.add(Box.createVerticalStrut(10));
        this.add(settingsButton);
        this.add(Box.createVerticalStrut(10));
        this.add(helpButton);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(100, 50));
        button.setBackground(Pallette.BOTONES.getColor());
        return button;
    }

}
