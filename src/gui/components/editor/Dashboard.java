package src.gui.components.editor;

import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

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
import src.db.*;
import src.domain.Course;
import src.gui.components.course.CourseIncome;
import src.gui.components.principal.Header;
import src.utils.DateUtils;
import src.utils.Pallette;

public class Dashboard extends JFrame {
    private JPanel panelStats; // Panel donde estará el gráfico
    private CardLayout cardLayout; // Para cambiar de paneles
    private JPanel panelMain;
    private JPanel panelHome,panelCrearCursos,panelVisualizarCursos,panelStatistics;
    private JTable tablaCursos;
    private DefaultTableModel tableModel;
    private int id;
    private static Random rand = new Random();

    public Dashboard(int id) {
        // Establecer el tamaño del JFrame para que ocupe toda la pantalla
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setTitle("Dashboard");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(MAXIMIZED_BOTH);

        // Panel principal con BorderLayout
        cardLayout = new CardLayout();
        panelMain = new JPanel(cardLayout);
        System.out.println(id);
        
        JPanel panelHome = crearPanelMain();
        JPanel panelCrearCursos = crearPanelCrearCursos();
        JPanel panelVisualizarCursos = crearPanelVisualizarCursos(id);
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
        
        panelVolver.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
        	
        });

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
        Double dineroGenerado = Database.conseguirDineroGenerado(Header.id);
        JPanel panelDinero = crearPanelInfo(dineroGenerado + "€", "Total Generado", "4.35%");
        Integer numEstudiantes = Database.conseguirEstudiantes(Header.id);
        JPanel panelEstudiantes = crearPanelInfo(numEstudiantes.toString(), "Estudiantes Totales", "0.43%");
        Integer numCursos = Database.conseguirNumCursos(Header.id);
        JPanel panelNumCursos = crearPanelInfo(numCursos.toString(), "Cursos En venta", "2.59%");

        // Agregar paneles al panelDatos
        panelDatos.add(panelDinero);
        panelDatos.add(panelEstudiantes);
        panelDatos.add(panelNumCursos);
        panel.add(panelDatos, BorderLayout.NORTH);
        
        panelStats = new JPanel(new BorderLayout());
        panelStats.setBackground(Color.white);

        // Crear un dataset y el gráfico
        
        LocalDate startOfWeek = DateUtils.getStartOfWeek();
        LocalDate endOfWeek = DateUtils.getEndOfWeek();
        
        Map<DayOfWeek, Integer> salesData = Database.conseguirVentasSemanales(Header.id,startOfWeek, endOfWeek);
        DefaultCategoryDataset dataset = crearDataset(salesData);
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
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);

        // Título principal
        JLabel lblMain = new JLabel("Crear Cursos");
        lblMain.setFont(new Font("Arial", Font.BOLD, 36));
        lblMain.setBorder(BorderFactory.createEmptyBorder(40, 40, 0, 0));
        panel.add(lblMain, BorderLayout.NORTH);  // Añadimos el título en la parte superior

        // Panel de entrada
        JPanel panelInputs = new JPanel();
        panelInputs.setLayout(new BoxLayout(panelInputs, BoxLayout.Y_AXIS)); // BoxLayout en el eje Y

        // Panel Título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = crearLabel("Título");
        JTextField tfTitulo = new JTextField(20);
        panelTitulo.add(lblTitulo);
        panelTitulo.add(tfTitulo);
        panelInputs.add(panelTitulo);

        // Panel Descripción
        JPanel panelDesc = new JPanel();
        JLabel lblDesc = crearLabel("Descripción");
        JTextArea taDesc = new JTextArea(5, 20);
        taDesc.setLineWrap(true);
        taDesc.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(taDesc);
        panelDesc.add(lblDesc);
        panelDesc.add(scrollDescripcion); // Aseguramos que se añada el JScrollPane
        panelInputs.add(panelDesc);

        // Panel Duración
        JPanel panelDuracion = new JPanel();
        JLabel lblDuracion = crearLabel("Duración");
        JTextField tfDuracion = new JTextField(20);
        panelDuracion.add(lblDuracion);
        panelDuracion.add(tfDuracion);
        panelInputs.add(panelDuracion);

        // Panel Precio
        JPanel panelPrecio = new JPanel();
        JLabel lblPrecio = crearLabel("Precio");
        JTextField tfPrecio = new JTextField(20);
        panelPrecio.add(lblPrecio);
        panelPrecio.add(tfPrecio);
        panelInputs.add(panelPrecio);

        // Panel Idioma
        JPanel panelIdioma = new JPanel();
        JLabel lblIdioma = crearLabel("Idioma");
        String[] idiomas = {"Español", "Inglés", "Francés", "Alemán"};
        JComboBox<String> cbIdioma = new JComboBox<>(idiomas);
        panelIdioma.add(lblIdioma);
        panelIdioma.add(cbIdioma);
        panelInputs.add(panelIdioma);

        // Panel Imagen
        JPanel panelImgPath = new JPanel();
        JTextField txtImgPath = new JTextField(20);
        JButton btnImgPath = new JButton("Seleccionar Imagen");
        panelImgPath.add(txtImgPath);
        panelImgPath.add(btnImgPath);

        // Acción para el botón de selección de imagen
        btnImgPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear un JFileChooser
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar imagen");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // Mostrar el diálogo de archivo y obtener el resultado
                int result = fileChooser.showOpenDialog(panel);  // Usamos el panel principal aquí
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Actualizamos el texto del JTextField con la ruta seleccionada
                    txtImgPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        panelInputs.add(panelImgPath);
        
        JButton btnCrearCurso = new JButton("Crear Curso");

        // Acción para el botón Crear Curso
        btnCrearCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tfTitulo.getText();
                String descripcion = taDesc.getText();
                int duracion = Integer.parseInt(tfDuracion.getText());
                Double precio = Double.parseDouble(tfPrecio.getText());
                String idioma = (String) cbIdioma.getSelectedItem();
                String imgPath = txtImgPath.getText();
                
                try {
					Database.crearCurso(titulo,descripcion,duracion,precio,15,id,idioma,imgPath,1.5 + (5 - 1.5) * rand.nextDouble(),"prueba");
				} catch (SQLException e1) {
					System.out.println("Error");
					e1.printStackTrace();
				}
                
                // Aquí puedes almacenar los datos en variables o procesarlos como desees
                System.out.println("Título: " + titulo);
                System.out.println("Descripción: " + descripcion);
                System.out.println("Duración: " + duracion);
                System.out.println("Precio: " + precio);
                System.out.println("Idioma: " + idioma);
                System.out.println("Imagen: " + imgPath);

                // Aquí puedes agregar la lógica para almacenar los datos en una base de datos o en otro lugar
            }
        });

        // Añadir el panel de entradas al panel principal (panel con BorderLayout)
        panel.add(panelInputs, BorderLayout.CENTER);
        panel.add(btnCrearCurso,BorderLayout.SOUTH);

        return panel;
    }

    
    public JPanel crearPanelVisualizarCursos(int id) {
        // Panel principal con BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        
        // Tabla de cursos
        tablaCursos = crearTabla(id);
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
            	for (int i = 0; i < tableModel.getRowCount(); i++) {
                    int idCurso = (int) tableModel.getValueAt(i, 0); 
                    String titulo = (String) tableModel.getValueAt(i, 1);
                    String descripcion = (String) tableModel.getValueAt(i, 2);
                    Object precioObject = tableModel.getValueAt(i, 3);
                    
                    //Solucion bug de que no se podia parsear.
                    double precio = 0.0; 

                    if (precioObject instanceof String) {
                        try {
                            precio = Double.parseDouble((String) precioObject);
                        } catch (NumberFormatException e1) {
                            System.out.println("Error al convertir el precio: " + e1.getMessage());
                        }
                    } else if (precioObject instanceof Double) {
                        precio = (Double) precioObject;
                    }
                    
                    String idioma = (String) tableModel.getValueAt(i, 4);
                    int clases = (int) tableModel.getValueAt(i, 5);

                    try {
                        boolean comprobacion = Database.actualizarCurso(idCurso, titulo, descripcion, precio, idioma, clases);
                        if (!comprobacion) {
                            JOptionPane.showMessageDialog(null, "Error al guardar los cambios del curso con ID: " + idCurso);
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
                    }
                }
                // Mensaje de éxito
                JOptionPane.showMessageDialog(null, "Los cambios se han aplicado correctamente.");
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
                int fila = tablaCursos.getSelectedRow(); 

                if (fila != -1) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres eliminar este curso?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
                    );
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        tableModel.removeRow(fila);

                        int idCurso = (int) tablaCursos.getValueAt(fila, 0);

                        try {
                            Database.eliminarCurso(idCurso);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un curso para eliminar.");
                }
            }
        });

        panelBotones.add(Box.createHorizontalStrut(610));
        panelBotones.add(btnCambios);
        panelBotones.add(Box.createHorizontalStrut(75));
        panelBotones.add(btnEliminar);
        panelBotones.add(Box.createHorizontalStrut(10));
        panel.add(panelBotones, BorderLayout.SOUTH);
        panel.setBackground(Color.WHITE);

        return panel;
    }

    private JPanel crearPanelStats() {
    	JPanel panel = new JPanel(new BorderLayout());
    	panel.add(createPieChart(),BorderLayout.CENTER);

    	
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
    private DefaultCategoryDataset crearDataset(Map<DayOfWeek, Integer> salesData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (DayOfWeek day : DayOfWeek.values()) {
            String dayName = day.toString().substring(0, 1).toUpperCase() + day.toString().substring(1).toLowerCase();
            dataset.addValue(salesData.getOrDefault(day, 0), "Ventas", dayName);
        }

        return dataset;
    }

    private JFreeChart crearGrafico(DefaultCategoryDataset dataset) {
        JFreeChart grafico = ChartFactory.createBarChart(
                "Ventas Semanales", 
                "Días",                  
                "Ventas ($)",            
                dataset
        );

        CategoryPlot plot = grafico.getCategoryPlot();

        if (plot.getRenderer() instanceof BarRenderer) {
            BarRenderer renderer = (BarRenderer) plot.getRenderer();

            renderer.setItemMargin(0.1);
        }

        plot.setInsets(new RectangleInsets(20, 20, 20, 20));

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryMargin(0.3); // 

        grafico.setPadding(new RectangleInsets(50, 50, 50, 50)); 

        return grafico;
    }

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


    private JPanel createPieChart() {
    	try {
			ArrayList<CourseIncome> porcentajesIngresos = Database.obtenerIngresosPorcentaje(Header.id);
	    	DefaultPieDataset dataset = new DefaultPieDataset();
	        for (CourseIncome porcentajeIngreso : porcentajesIngresos) {
	            dataset.setValue("Curso " + porcentajeIngreso.getIdCurso(), porcentajeIngreso.getPorcentaje());
	        }
            JFreeChart pieChart = ChartFactory.createPieChart(
                    "Distribución de ingresos",
                    dataset,
                    true, true, false);

            return new ChartPanel(pieChart);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
	        
    private JTable crearTabla(int id) {
	            String[] columnNames = {"Id","Nombre", "Descripción", "Precio (€)", "Idioma", "Clases"};
	            ArrayList<Course> cursos = Database.obtenerCursosPorInstructor(id);

	            Object[][] data = new Object[cursos.size()][6]; 

	            for (int i = 0; i < cursos.size(); i++) {
	                Course curso = cursos.get(i);
	                data[i][0] = curso.getId();
	                data[i][1] = curso.getName();            
	                data[i][2] = curso.getDescription();       
	                data[i][3] = curso.getPrice();            
	                data[i][4] = curso.getLanguage();            
	                data[i][5] = curso.getClases();            
	            }

	            
	            tableModel = new DefaultTableModel(data, columnNames) {
	                @Override
	                public boolean isCellEditable(int row, int column) {
	                	if(column == 0) {
	                		return false;
	                	}
	                    return true; 
	                }
	            };

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
	            table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); 
	            table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); 

	            String[] languages = {"Español", "Inglés", "Francés", "Alemán", "Italiano"};
	            JComboBox<String> comboBox = new JComboBox<>(languages);
	            table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comboBox));
				return table;
	        }

	        
    private JLabel crearLabel(String name) {
	        	JLabel lbl = new JLabel(name);
	        	lbl.setFont(new Font("Arial", Font.BOLD,22));
	        	
	        	
	        	return lbl;
	        }
    
    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Dashboard(1);
				
			}
		});
	}
}
