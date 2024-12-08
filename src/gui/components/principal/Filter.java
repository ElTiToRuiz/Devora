package src.gui.components.principal;

import src.db.Database;
import src.domain.Course;
import src.gui.components.course.CourseFront;
import src.utils.Pallette;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Filter extends JPanel {
    private List<String> categories;
    private int rating;
    private int duracion = 0;
    private JSlider minPriceSlider;
    private JSlider maxPriceSlider;
    private JLabel priceRangeLabel;

    public Filter() {
        setupPanel();
    }

    public HashMap<String, Object> getAvailableFilters() {
        HashMap<String, Object> filters = new HashMap<>();
        filters.put("minPrice", minPriceSlider.getValue());
        filters.put("maxPrice", maxPriceSlider.getValue());
        filters.put("categories", this.categories);
        filters.put("rating", this.rating);
        filters.put("duracion", this.duracion);
        return filters;
    }

    public static ArrayList<CourseFront> filterCourses() {
        ArrayList<CourseFront> courses = Database.obtenerCursos();
        HashMap<String, Object> filters = new Filter().getAvailableFilters();
        List<String> categories = (List<String>) filters.get("categories");
        int duracion = (int) filters.get("duracion");
        int rating = (int) filters.get("rating");
        int minPrice = (int) filters.get("minPrice");
        int maxPrice = (int) filters.get("maxPrice");

        return (ArrayList<CourseFront>) courses.stream()
                .filter(course -> course.getPrice() >= minPrice && course.getPrice() <= maxPrice)
                .filter(course -> categories.isEmpty() || categories.contains(course.getCategorias()))
                .filter(course -> course.getRating() >= rating)
                .filter(course -> course.getDuracion() >= duracion)
                .collect(Collectors.toList());
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
        String[] categories = {"Programación", "Emprendimiento", "Trading", "Psicología", "Cocina",
                "Diseño", "Música", "Idiomas"};
        addCheckBoxesToPanel(panel, categories);
        return panel;
    }

    private JPanel createRatingPanel() {
        JPanel panel = createVerticalPanelWithLabel("Estrellas");
        String[] ratings = {"5⭐","4⭐", "3⭐", "2⭐", "1⭐"};
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
            JCheckBox checkBox = new JCheckBox(item);

            checkBox.addActionListener(e -> {
                boolean isSelected = checkBox.isSelected();
                if (item.contains("⭐")) {
                    this.rating = Integer.parseInt(item.split("⭐")[0]);
                } else {
                    boolean b = isSelected ? categories.add(item) : categories.remove(item);
                }
            });
            panel.add(checkBox);
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
        JButton hideFiltersButton = new JButton("Ocultar Filtros");
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
            JButton btnFiltros = new JButton("Mostrar Filtros");
            
            btnFiltros.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
            
            btnFiltros.addActionListener(e -> {
                Principal principal = (Principal) SwingUtilities.getWindowAncestor(this);
                principal.showFilter = true;
                principal.getContentPane().remove(1);
                principal.getContentPane().add(principal.createContainer(), BorderLayout.CENTER);
                principal.revalidate();
            });
            add(btnFiltros);
            add(Box.createVerticalStrut(20));
        }
    }
}
