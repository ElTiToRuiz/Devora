package src.gui.components.principal;

import src.utils.Pallette;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    public Sidebar() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Pallette.SIDEBAR_FONDO.getColor());

        JButton homeButton = createSidebarButton("Home");
        JButton settingsButton = createSidebarButton("Settings");
        JButton helpButton = createSidebarButton("Help");

        this.add(createFilters());
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

    private JPanel createFilters(){
        JPanel filters = new JPanel();
        filters.setLayout(new BoxLayout(filters, BoxLayout.Y_AXIS));
        filters.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        filters.setBackground(Pallette.SIDEBAR_FONDO.getColor());

        JLabel filtersLabel = new JLabel("Filters");
        filtersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        filters.add(filtersLabel);

        filters.add(Box.createVerticalStrut(10));

        JCheckBox filter1 = new JCheckBox("Filter 1");
        JCheckBox filter2 = new JCheckBox("Filter 2");
        JCheckBox filter3 = new JCheckBox("Filter 3");

        filters.add(filter1);
        filters.add(filter2);
        filters.add(filter3);

        return filters;
    }

}
