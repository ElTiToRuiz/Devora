package src.gui.components.principal;

import src.utils.Pallette;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Filter extends JPanel {

    private JSlider minPriceSlider;
    private JSlider maxPriceSlider;
    private JLabel priceRangeLabel;

    public Filter() {
        setupPanel();
    }

    private void setupPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Pallette.SIDEBAR_FONDO.getColor());
        add(createFiltersPanel());
    }

    private JPanel createFiltersPanel() {
        JPanel filtersPanel = new JPanel();
        filtersPanel.setLayout(new BoxLayout(filtersPanel, BoxLayout.X_AXIS));
        filtersPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        filtersPanel.setBackground(Pallette.SIDEBAR_FONDO.getColor());

        filtersPanel.add(createRatingPanel());
        filtersPanel.add(Box.createHorizontalStrut(10));
        filtersPanel.add(createCategoryPanel());
        filtersPanel.add(Box.createHorizontalStrut(10));
        filtersPanel.add(createPriceRangePanel());
        filtersPanel.add(Box.createHorizontalStrut(10));
        filtersPanel.add(createHideFiltersPanel());

        return filtersPanel;
    }

    private JPanel createPriceRangePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        priceRangeLabel = new JLabel("Price Range: $0 - $100");
        panel.add(priceRangeLabel);
        panel.add(Box.createVerticalStrut(10));

        JPanel priceSliders = new JPanel();
        priceSliders.setLayout(new BoxLayout(priceSliders, BoxLayout.X_AXIS));

        minPriceSlider = createSlider("Min Price", 0, 100, 0, 10, 10);
        maxPriceSlider = createSlider("Max Price", 0, 100, 100, 10, 10);

        priceSliders.add(createSliderPanel("Min Price", minPriceSlider));
        priceSliders.add(Box.createHorizontalStrut(10));
        priceSliders.add(createSliderPanel("Max Price", maxPriceSlider));

        panel.add(priceSliders);
        return panel;
    }

    private JPanel createCategoryPanel() {
        JPanel panel = createVerticalPanelWithLabel("Categories");
        String[] categories = {"Electronics", "Clothing", "Home Goods", "Books"};
        addCheckBoxesToPanel(panel, categories);
        return panel;
    }

    private JPanel createRatingPanel() {
        JPanel panel = createVerticalPanelWithLabel("Rating");
        String[] ratings = {"+4⭐", "+3⭐", "+2⭐", "+1⭐"};
        addCheckBoxesToPanel(panel, ratings);
        return panel;
    }

    private JPanel createVerticalPanelWithLabel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
        return panel;
    }

    private void addCheckBoxesToPanel(JPanel panel, String[] items) {
        for (String item : items) {
            panel.add(new JCheckBox(item));
        }
    }

    private JPanel createSliderPanel(String title, JSlider slider) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(50, 100));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(title);
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
        panel.add(slider);
        return panel;
    }

    private JSlider createSlider(String title, int min, int max, int value, int majorTickSpacing, int minorTickSpacing) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
        slider.setMajorTickSpacing(majorTickSpacing);
        slider.setMinorTickSpacing(minorTickSpacing);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new PriceSliderChangeListener());
        return slider;
    }

    private class PriceSliderChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            int minPrice = minPriceSlider.getValue();
            int maxPrice = maxPriceSlider.getValue();
            if (minPrice > maxPrice) {
                maxPriceSlider.setValue(minPrice);
            }
            priceRangeLabel.setText("Price Range: $" + minPrice + " - $" + maxPrice);
        }
    }

    private JPanel createHideFiltersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Pallette.SIDEBAR_FONDO.getColor());
        JButton hideFiltersButton = new JButton("Hide Filters");
        hideFiltersButton.addActionListener(e -> {
            Principal principal = (Principal) SwingUtilities.getWindowAncestor(this);
            principal.showFilter = false;
            principal.getContentPane().remove(1);
            principal.getContentPane().add(principal.createContainer(), BorderLayout.CENTER);
            principal.revalidate();
        });
        panel.add(hideFiltersButton);
        panel.add(Box.createVerticalStrut(20));
        return panel;
    }

    public static class FilterHidden extends JPanel {
        public FilterHidden() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Pallette.SIDEBAR_FONDO.getColor());
            JButton showFiltersButton = new JButton("Show Filters");
            showFiltersButton.addActionListener(e -> {
                Principal principal = (Principal) SwingUtilities.getWindowAncestor(this);
                principal.showFilter = true;
                principal.getContentPane().remove(1);
                principal.getContentPane().add(principal.createContainer(), BorderLayout.CENTER);
                principal.revalidate();
            });
            add(showFiltersButton);
            add(Box.createVerticalStrut(20));
        }
    }
}
