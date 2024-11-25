package src.gui.components.editor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.*;

import src.utils.Pallette;

public class Dashboard extends JFrame {
    private JPanel panelStats; // Panel donde estará el gráfico
    private CardLayout cardLayout; // Para cambiar de paneles
    private JPanel panelMain;
    private JPanel panelHome,panelCrearCursos,panelVisualizarCursos,panelStatistics;
    private JTable tablaCursos;
    private DefaultTableModel tableModel;


    public Dashboard(int id) {
        // Establecer el tamaño del JFrame para que ocupe toda la pantalla
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setTitle("Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal con BorderLayout
        cardLayout = new CardLayout();
        panelMain = new JPanel(cardLayout);
        
        
        JPanel panelHome = crearPanelMain();
        JPanel panelCrearCursos = crearPanelCrearCursos();
        JPanel panelVisualizarCursos = crearPanelVisualizarCursos();
        JPanel panelStatistics = crearPanelStats();

        panelMain.add(panelHome, "panelHome");
        panelMain.add(panelCrearCursos, "panelCrearCursos");
        panelMain.add(panelVisualizarCursos, "panelVisualizarCursos");
        panelMain.add(panelStatistics, "panelStatistics");
        
        
        // Panel Sidebar (Menú de Navegación)
        JPanel panelSidebar = new JPanel();
        panelSidebar.setBackground(new Color(26, 36, 52));
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS)); // Diseño vertical

        // Crear botones usando el método `crearBtn`
        JPanel btnEditarCursos = crearBtn("Inicio", "src/media/home-icon.png", 1);
        JPanel btnPublicarCursos = crearBtn("Publicar Curso", "src/media/add-icon.png", 2);
        JPanel btnVerCursos = crearBtn("Editar Cursos", "src/media/tune-icon.png", 3);
        JPanel btnConfiguracion = crearBtn("Estadísticas", "src/media/chart-icon.png", 4);
        JPanel panelVolver = crearBtn("Volver", "src/media/back-icon.png", 5);

        // Agregar botones al Sidebar
        btnEditarCursos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnPublicarCursos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnVerCursos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnConfiguracion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelVolver.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelSidebar.add(Box.createRigidArea(new Dimension(0, 200))); // Espacio arriba
        panelSidebar.add(btnEditarCursos);
        panelSidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        panelSidebar.add(btnPublicarCursos);
        panelSidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        panelSidebar.add(btnVerCursos);
        panelSidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        panelSidebar.add(btnConfiguracion);
        panelSidebar.add(Box.createRigidArea(new Dimension(0, 575)));
        panelSidebar.add(panelVolver);

        panelSidebar.setPreferredSize(new Dimension(350, getHeight())); // Fijar tamaño del sidebar
        add(panelSidebar, BorderLayout.WEST);
        add(panelMain, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    private JPanel crearPanelMain() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());  // Establecer el BorderLayout correctamente

        JPanel panelDatos = new JPanel(new GridLayout(1, 3, 20, 20));
        panelDatos.setBackground(Color.white);
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelDatos.setPreferredSize(new Dimension(getWidth(), getHeight() / 3)); // Ocupa 1/3 de la altura

        // Crear paneles de información
        JPanel panelDinero = crearPanelInfo("$45.2K", "Total Generado", "4.35%");
        JPanel panelEstudiantes = crearPanelInfo("3.456K", "Estudiantes Totales", "0.43%");
        JPanel panelNumCursos = crearPanelInfo("2.450", "Cursos En venta", "2.59%");

        // Agregar paneles al panelDatos
        panelDatos.add(panelDinero);
        panelDatos.add(panelEstudiantes);
        panelDatos.add(panelNumCursos);
        panel.add(panelDatos, BorderLayout.NORTH);
        
        panelStats = new JPanel(new BorderLayout());
        panelStats.setBackground(Color.white);

        // Crear un dataset y el gráfico
        DefaultCategoryDataset dataset = crearDataset();
        JFreeChart grafico = crearGrafico(dataset);
        ChartPanel chartPanel = new ChartPanel(grafico);

        panelStats.add(chartPanel, BorderLayout.CENTER);

        // Establecer tamaño relativo (2/3)
        panelStats.setPreferredSize(new Dimension(getWidth(), (getHeight() * 2) / 3));

        // Agregar el panelStats al centro
        panel.add(panelStats, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelCrearCursos() {
    	JPanel panel = new JPanel();
    	panel.setBackground(Color.cyan);
    	return panel;
    }
    
    public JPanel crearPanelVisualizarCursos() {
        // Panel principal con BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        
        // Tabla de cursos
        tablaCursos = crearTabla();
        JScrollPane scrollPane = new JScrollPane(tablaCursos);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel para los botones, con un diseño en fila
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setBackground(new Color(240, 240, 240)); // Color de fondo suave
        panelBotones.setPreferredSize(new Dimension(0,100));
        
        // Botón de guardar cambios
        JButton btnCambios = new JButton("Guardar Cambios");
        btnCambios.setFont(new Font("Arial", Font.BOLD, 14));
        btnCambios.setForeground(Color.WHITE);
        btnCambios.setBackground(Pallette.COLOR_PRINCIPAL.getColor());
        btnCambios.setPreferredSize(new Dimension(150, 40));
        btnCambios.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        btnCambios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCambios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí agregarás el código para guardar los cambios en la base de datos
                JOptionPane.showMessageDialog(null, "Cambios guardados exitosamente.");
            }
        });

        // Botón de eliminar curso
        JButton btnEliminar = new JButton("Eliminar Curso");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(255, 69, 0)); // Rojo
        btnEliminar.setFocusPainted(false);
        btnEliminar.setPreferredSize(new Dimension(0, 100));
        btnEliminar.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablaCursos.getSelectedRow();
                if (selectedRow != -1) {
                    // Eliminar el curso de la tabla
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un curso para eliminar.");
                }
            }
        });

        // Agregar los botones al panel
        panelBotones.add(Box.createHorizontalStrut(610)); // Espacio entre los botones
        panelBotones.add(btnCambios);
        panelBotones.add(Box.createHorizontalStrut(75));
        panelBotones.add(btnEliminar);
        panelBotones.add(Box.createHorizontalStrut(10));

        // Agregar el panel de botones a la parte sur del panel principal
        panel.add(panelBotones, BorderLayout.SOUTH);

        // Establecer fondo blanco para el panel principal
        panel.setBackground(Color.WHITE);

        return panel;
    }

    private JPanel crearPanelStats() {
    	JPanel panel = new JPanel();
    	panel.add(createLineChart());
    	panel.add(createBarChart());
    	panel.add(createPieChart());
    	panel.add(createLineChartComparative());
    	panel.add(createSpiderChart());
    	
    	panel.setBackground(Color.white);
    	return panel;
    }


    // Método para crear un JPanel con información
    private JPanel crearPanelInfo(String valor, String titulo, String porcentaje) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel labelValor = new JLabel(valor);
        labelValor.setFont(new Font("Arial", Font.BOLD, 26));
        labelValor.setForeground(Pallette.COLOR_PRINCIPAL.getColor());

        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        labelTitulo.setForeground(new Color(100, 100, 100));

        JLabel labelPorcentaje = new JLabel(porcentaje);
        labelPorcentaje.setFont(new Font("Arial", Font.BOLD, 16));
        labelPorcentaje.setForeground(new Color(34, 139, 34)); // Verde

        panel.add(labelTitulo, BorderLayout.NORTH);
        panel.add(labelValor, BorderLayout.CENTER);
        panel.add(labelPorcentaje, BorderLayout.SOUTH);

        return panel;
    }

    // Método para crear el dataset
    private DefaultCategoryDataset crearDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(200, "Ventas", "Lunes");
        dataset.addValue(300, "Ventas", "Martes");
        dataset.addValue(150, "Ventas", "Miércoles");
        dataset.addValue(400, "Ventas", "Jueves");
        dataset.addValue(250, "Ventas", "Viernes");
        dataset.addValue(100, "Ventas", "Sábado");
        dataset.addValue(50, "Ventas", "Domingo");
        return dataset;
    }

    private JFreeChart crearGrafico(DefaultCategoryDataset dataset) {
        // Crear el gráfico de barras
        JFreeChart grafico = ChartFactory.createBarChart(
                "Ventas Últimos 7 Días", // Título del gráfico
                "Días",                  // Etiqueta del eje X
                "Ventas ($)",            // Etiqueta del eje Y
                dataset
        );

        // Obtener el objeto CategoryPlot para personalizar el gráfico
        CategoryPlot plot = grafico.getCategoryPlot();

        // Configurar el renderizador del gráfico
        if (plot.getRenderer() instanceof BarRenderer) {
            BarRenderer renderer = (BarRenderer) plot.getRenderer();

            // Reducir el espacio entre barras
            renderer.setItemMargin(0.1); // Margen entre barras, entre 0.0 y 1.0
        }

        // Ajustar los márgenes internos del gráfico
        plot.setInsets(new RectangleInsets(20, 20, 20, 20)); // Márgenes internos del gráfico

        // Configurar márgenes entre categorías en el eje X
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryMargin(0.3); // Controlar el espacio entre categorías

        // Ajustar márgenes externos del gráfico
        grafico.setPadding(new RectangleInsets(50, 50, 50, 50)); // Márgenes externos del gráfico

        return grafico;
    }



    // Método protegido para crear un JPanel con texto y un ícono
    protected JPanel crearBtn(String texto, String iconoPath, int id) {
        JPanel btn = new JPanel(new BorderLayout());
        btn.setSize(new Dimension(400, 50));

        JLabel textoLabel = new JLabel(texto);
        textoLabel.setFont(new Font("Arial", Font.BOLD, 22));
        textoLabel.setForeground(Color.gray);

        ImageIcon iconoPerfil = new ImageIcon(iconoPath);
        Image img = iconoPerfil.getImage();

        Image iconoPerfilEscalado = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(iconoPerfilEscalado));
        imgLabel.setBorder(BorderFactory.createEmptyBorder(0, 55, 0, 40));

        btn.add(imgLabel, BorderLayout.WEST);
        btn.add(textoLabel, BorderLayout.CENTER);

        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setBackground(new Color(26, 36, 52));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                textoLabel.setForeground(Color.white);
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                textoLabel.setForeground(Color.gray);
                super.mouseExited(e);
            }

			@Override
			public void mouseClicked(MouseEvent e) {
				String namePanel = "";
				switch (id) {
	            case 1:
	            	namePanel = "panelHome";
	                break;
	            case 2:
	            	namePanel = "panelCrearCursos";
	                break;
	            case 3:
	            	namePanel = "panelVisualizarCursos";
	                break;
	            case 4:
	            	namePanel = "panelStatistics";
	                break;
				}
				
                cardLayout.show(panelMain, namePanel);

			}
            
            
        });

        return btn;
    }

        // Método para crear un gráfico de líneas
    		private JPanel createLineChart() {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(2500, "2016", "2016");
            dataset.addValue(3000, "2017", "2017");
            dataset.addValue(8000, "2018", "2018");
            dataset.addValue(4000, "2019", "2019");
            dataset.addValue(4500, "2020", "2020");

            JFreeChart lineChart = ChartFactory.createLineChart(
                    "Tendencia Anual",
                    "Año",
                    "Valor",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false);

            return new ChartPanel(lineChart);
        }

	        // Método para crear un gráfico de barras
	        private JPanel createBarChart() {
	            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	            dataset.addValue(4000, "High", "Enero");
	            dataset.addValue(2500, "Low", "Enero");
	            dataset.addValue(5000, "High", "Febrero");
	            dataset.addValue(2000, "Low", "Febrero");
	
	            JFreeChart barChart = ChartFactory.createBarChart(
	                    "Altos y Bajos Mensuales",
	                    "Mes",
	                    "Valor",
	                    dataset,
	                    PlotOrientation.VERTICAL,
	                    true, true, false);
	
	            return new ChartPanel(barChart);
	        }
	
	        // Método para crear un gráfico circular (pie chart)
	        private JPanel createPieChart() {
	            DefaultPieDataset dataset = new DefaultPieDataset();
	            dataset.setValue("Categoria 1", 20);
	            dataset.setValue("Categoria 2", 30);
	            dataset.setValue("Categoria 3", 50);
	
	            JFreeChart pieChart = ChartFactory.createPieChart(
	                    "Distribución",
	                    dataset,
	                    true, true, false);
	
	            return new ChartPanel(pieChart);
	        }
	
	        // Método para un gráfico de líneas comparativo
	        private JPanel createLineChartComparative() {
	            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	            dataset.addValue(5000, "Paid", "Enero");
	            dataset.addValue(8000, "Organic", "Enero");
	            dataset.addValue(7000, "Paid", "Febrero");
	            dataset.addValue(3000, "Organic", "Febrero");
	            dataset.addValue(5000, "Paid", "Marzo");
	            dataset.addValue(6000, "Organic", "Marzo");
	
	            JFreeChart lineChart = ChartFactory.createLineChart(
	                    "Comparativa Paid vs Organic",
	                    "Mes",
	                    "Visitas",
	                    dataset,
	                    PlotOrientation.VERTICAL,
	                    true, true, false);
	
	            return new ChartPanel(lineChart);
	        }
	
	        // Método para crear un gráfico tipo radar/spider
	        private JPanel createSpiderChart() {
	            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	            dataset.addValue(5, "Orders", "Lunes");
	            dataset.addValue(3, "Orders", "Martes");
	            dataset.addValue(8, "Orders", "Miércoles");
	            dataset.addValue(7, "Orders", "Jueves");
	            dataset.addValue(6, "Orders", "Viernes");
	
	            SpiderWebPlot spiderPlot = new SpiderWebPlot(dataset);
	            JFreeChart spiderChart = new JFreeChart("Actividad Semanal", JFreeChart.DEFAULT_TITLE_FONT, spiderPlot, false);
	
	            return new ChartPanel(spiderChart);
	        }
	        
	        private JTable crearTabla() {
	            // Crear datos de muestra para la tabla
	            String[] columnNames = {"Nombre", "Descripción", "Precio ($)", "Idioma", "Clases"};
	            Object[][] data = {
	                    {"Curso Java Básico", "Aprende los fundamentos de Java", 49.99, "Español", 10},
	                    {"Python para Data Science", "Introducción a herramientas de análisis", 59.99, "Inglés", 12},
	                    {"Diseño Web Avanzado", "Crea sitios web modernos", 79.99, "Español", 15},
	                    {"React.js Essentials", "Domina los fundamentos de React.js", 99.99, "Inglés", 20},
	                    {"SQL para Principiantes", "Gestiona bases de datos relacionales", 29.99, "Español", 8}
	            };

	            // Crear modelo de tabla
	            tableModel = new DefaultTableModel(data, columnNames) {
	                // Permitir la edición solo en la columna de "Idioma"
	                @Override
	                public boolean isCellEditable(int row, int column) {
	                    return column == 3; // La columna de índice 3 (Idioma) es editable
	                }
	            };

	            // Crear la tabla
	            JTable table = new JTable(tableModel);
	            table.setRowHeight(30); 
	            table.setFont(new Font("Arial", Font.PLAIN, 14)); 
	            table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14)); 
	            table.getTableHeader().setBackground(Pallette.COLOR_PRINCIPAL.getColor()); 
	            table.getTableHeader().setForeground(Color.BLACK); 
	            table.setShowGrid(false); 
	            table.setIntercellSpacing(new Dimension(0, 0)); 

	            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	            table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); 
	            table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); 

	            String[] languages = {"Español", "Inglés", "Francés", "Alemán", "Italiano"};
	            JComboBox<String> comboBox = new JComboBox<>(languages);
	            table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboBox));
				return table;
	        }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard(53));
    }
}
