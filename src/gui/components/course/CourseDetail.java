package src.gui.components.course;

import javax.swing.*;

import src.db.Database;
import src.domain.Course;
import src.gui.components.principal.Header;
import src.utils.Pallette;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseDetail extends JFrame {
    private final int courseId;

    public CourseDetail(int courseId) {
        this.courseId = courseId;
        setupWindow();
        loadCourseDetails();
        this.setVisible(true);
    }

    private void setupWindow() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Ventana a pantalla completa
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Detalles del Curso");
    }

    private void loadCourseDetails() {
        Connection connection = null;

        try {
            // Conexión a la base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:src/db/devora.db");

            // Consulta SQL para obtener datos del curso
            String query = "SELECT * FROM Curso WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.courseId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // ================== PANEL IZQUIERDO ==================
            	JPanel mainPanel = new JPanel(new BorderLayout());
                JPanel leftPanel = new JPanel(new BorderLayout());
                leftPanel.setPreferredSize(new Dimension(700,1080));
                leftPanel.setBackground(Color.white);
                JPanel rightPanel = new JPanel(new BorderLayout());
                rightPanel.setPreferredSize(new Dimension(500,1080));
                rightPanel.setBackground(Color.white);
                JPanel panelImg = new JPanel();
                JPanel panelDatos = new JPanel(new BorderLayout());
                panelDatos.setBackground(Color.white);
                JPanel panelInfo = new JPanel();
                panelInfo.setPreferredSize(new Dimension(9,300));
                panelInfo.setMaximumSize(new Dimension(0,300));
                panelInfo.setBackground(Color.white);
                
                ImageIcon imgCurso = new ImageIcon(new ImageIcon(resultSet.getString("imgPath")).getImage().getScaledInstance(700, 650, Image.SCALE_SMOOTH));
                JLabel lblImgCurso = new JLabel();

                panelImg.addComponentListener(new ComponentAdapter() {

					@Override
					public void componentResized(ComponentEvent e) {
						int width = panelImg.getWidth();
				        int height = panelImg.getHeight();

				        Image scaledImage = imgCurso.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
				        lblImgCurso.setIcon(new ImageIcon(scaledImage));
					}
                });
                
                panelImg.add(lblImgCurso,BorderLayout.CENTER);
                
                panelDatos.setBackground(Color.white);
                panelDatos.setPreferredSize(new Dimension(0,350));
                
                JLabel lblTitulo = new JLabel(resultSet.getString("titulo"));
                lblTitulo.setFont(new Font("Arial",Font.BOLD,42));
                lblTitulo.setForeground(Color.black);
                lblTitulo.setBorder(BorderFactory.createEmptyBorder(30,100,0,0));
                
                //Panel Instructor
                JPanel panelInstructor = new JPanel();
                panelInstructor.setBackground(Color.white);
                
                //Icono instructor
                ImageIcon iconoInstructor = new ImageIcon("src/media/person-50px.png");
                Image img = iconoInstructor.getImage();
                Image imagenEscalada = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                JLabel labelInstructor = new JLabel(new ImageIcon(imagenEscalada));
            
                //JLabel texto instructor
                JLabel lblTextInstructor = new JLabel(Database.conseguirUser(1));
                lblTextInstructor.setFont(new Font("Arial",Font.PLAIN,20));
                
                panelInstructor.add(labelInstructor); panelInstructor.add(lblTextInstructor);
                
                //Panel Valoracion
                JPanel panelValoracion = new JPanel();
                panelValoracion.setBackground(Color.white);
                
                //Icono Estrella
                ImageIcon iconoEstrella = new ImageIcon("src/media/star-50px.png");
                Image imgEstrella = iconoEstrella.getImage();
                Image imagenEscaladaEstrella = imgEstrella.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                JLabel labelEstrella = new JLabel(new ImageIcon(imagenEscaladaEstrella));
                
                //JLabel texto Rating
                JLabel lblRating = new JLabel("4,9");
                lblRating.setFont(new Font("Arial",Font.PLAIN,20));
                
                panelValoracion.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
                panelValoracion.add(labelEstrella); panelValoracion.add(lblRating);
                panelInfo.add(panelInstructor); panelInfo.add(panelValoracion);
                panelInfo.setBorder(BorderFactory.createEmptyBorder(10,0,0,930));
                
                //JLabel para la descripción del curso
                JPanel panelDesc = new JPanel(new BorderLayout());
                panelDesc.setBackground(Color.white);
                JLabel lblDescTitle = new JLabel("Descripción");
                lblDescTitle.setFont(new Font("Arial",Font.BOLD,22));
                // Obtén el texto desde la base de datos (simulación)
                String textoDesdeBD = resultSet.getString("descripcion");

                // Utilizamos HTML para converitr el texto descripcion en label con saltos de linea
                String textoHTML = "<html>" + textoDesdeBD.replace("\n", "<br>") + "</html>";
                JLabel lblDesc = new JLabel(textoHTML);
                lblDesc.setFont(new Font("Arial",Font.PLAIN,18));
                lblDesc.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
                
                panelDesc.add(lblDescTitle,BorderLayout.NORTH); panelDesc.add(lblDesc,BorderLayout.CENTER);
                panelDesc.setBorder(BorderFactory.createEmptyBorder(0,100,90,0));
                
                // Añade el panel al BorderLayout principal
                panelDatos.add(lblTitulo, BorderLayout.NORTH);
                panelDatos.add(panelInfo, BorderLayout.CENTER);
                panelDatos.add(panelDesc, BorderLayout.SOUTH);
                
                leftPanel.add(panelImg,BorderLayout.CENTER);
                leftPanel.add(panelDatos,BorderLayout.SOUTH);
                
                //Panel Derecha
                
                //Icono volver
                ImageIcon iconoMenu = new ImageIcon("src/media/home-50px.png");
                Image imgMenu = iconoMenu.getImage();
                Image imagenEscaladaMenu = imgMenu.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                JLabel lblMenu = new JLabel(new ImageIcon(imagenEscaladaMenu));
                lblMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                lblMenu.setBorder(BorderFactory.createEmptyBorder(40,350,0,0));
                
                lblMenu.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
                });
                
                //Panel precio + añadir carrito
                JPanel panelPrecio = new JPanel(new BorderLayout());
                panelPrecio.setBackground(Color.white);
                JLabel lblPrecio = new JLabel(resultSet.getString("Precio") + "€");
                lblPrecio.setFont(new Font("Arial",Font.BOLD,56));
                lblPrecio.setBorder(BorderFactory.createEmptyBorder(300,50,0,0));
                
                JPanel panelAñadir = new JPanel();
                JButton btnAñadir = new JButton("Añadir a la cesta");
                btnAñadir.setForeground(Color.white);
                btnAñadir.setBackground(Pallette.COLOR_PRINCIPAL.getColor());
                btnAñadir.setFont(new Font("Arial",Font.BOLD,24));
                btnAñadir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnAñadir.setBorder(BorderFactory.createEmptyBorder(20,100,20,100));
                panelAñadir.add(btnAñadir);
                panelAñadir.setBackground(Color.white);
                panelAñadir.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
                
                btnAñadir.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(Header.id == -1) {
					        JOptionPane.showMessageDialog(null, "Te tienes que logear para poder añadir productos al carrito", "Alerta", JOptionPane.WARNING_MESSAGE);
						} else  if(Database.comprobarCompra(Header.id,courseId)) {
					        JOptionPane.showMessageDialog(null, "Ya has comprado este curso anteriormente", "Alerta", JOptionPane.WARNING_MESSAGE);
						} else if (Database.comprobarCreador(Header.id,courseId)) {
					        JOptionPane.showMessageDialog(null, "Eres el creador del curso", "Alerta", JOptionPane.WARNING_MESSAGE);
						}else {
							Course curso = Database.conseguirCurso(courseId);
							Header.pedido.add(curso);
						}
					}

					@Override
					public void mouseExited(MouseEvent e) {
						btnAñadir.setBackground(Pallette.COLOR_PRINCIPAL.getColor());
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						btnAñadir.setBackground(Pallette.COLOR_HOVER_PRINCIPAL.getColor());
					}
                });
                
                panelPrecio.add(lblPrecio,BorderLayout.NORTH);
                panelPrecio.add(panelAñadir,BorderLayout.CENTER);

                rightPanel.add(lblMenu,BorderLayout.NORTH);
                rightPanel.add(panelPrecio,BorderLayout.CENTER);
                
                
                
                mainPanel.add(leftPanel,BorderLayout.CENTER);
                mainPanel.add(rightPanel,BorderLayout.EAST);
                add(mainPanel);

            } else {
                JOptionPane.showMessageDialog(this, "No se encontró información del curso.", "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los detalles del curso:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CourseDetail(1));
    }
}

