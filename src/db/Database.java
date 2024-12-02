package src.db;

import java.io.FileNotFoundException;

import src.domain.Course;
import src.gui.components.course.CourseFront;
import src.utils.Encriptar;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Database {
    private static Database instance; 
    private static final String path = "jdbc:sqlite:src/db/devora.db";
    private static Connection connection = null;

    private Database() { 
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
            instance.connect(); 
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private static Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(path);
                System.out.println("Connection to SQLite has been established.");
            }
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
		return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection to SQLite has been closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    public void test(){
        try {
            String query = "SELECT * FROM USUARIO";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " Username: " + rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta: " + e.getMessage());
        }
    }
    
    
    public boolean verificarCredenciales(String username, String password) {
        String query = "SELECT COUNT(*) AS count FROM USUARIO WHERE username = ? AND password = ?";
        try (Connection conn = connect(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt("count") > 0) {
                return true; 
            }
        } catch (Exception e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }
        return false; 
    }
    
    public boolean registrarDatos(String username, String email, String password) {
        String query = "INSERT INTO USUARIO (username, email, password, saldo) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setDouble(4, 0.0);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 

        } catch (Exception e) {
            System.out.println("Error al registrar los datos: " + e.getMessage());
        }
        return false; 
    }
    
    public static void crearTablas() {
        try (Connection conn = connect();  
             Statement stmt = conn.createStatement()) {
            
            crearTablaCurso(stmt);
            crearTablaUsuario(stmt);
            crearTablaCompras(stmt);
            
            System.out.println("Todas las tablas han sido creadas con éxito.");
            
        } catch (Exception e) {
            System.out.println("Error al crear las tablas.");
            e.printStackTrace();
        }
    }

    public static void crearTablaCurso(Statement stmt) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS Curso (" 
                + "id INTEGER PRIMARY KEY AUTOINCREMENT," 
                + "titulo TEXT NOT NULL," 
                + "descripcion TEXT," 
                + "duracion INTEGER," 
                + "precio REAL," 
                + "clases INTEGER," 
                + "instructor INTEGER,"  
                + "idioma TEXT," 
                + "imgPath TEXT,"  // Columna para la ruta de la imagen
                + "FOREIGN KEY (instructor) REFERENCES Usuario(id));";

        stmt.execute(query);
        System.out.println("Tabla CURSO creada con éxito.");
    }

    public static void crearTablaUsuario(Statement stmt) throws SQLException {
    	String query = "CREATE TABLE IF NOT EXISTS Usuario ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL,"
                + "email TEXT,"
                + "password TEXT,"
                + "saldo REAL,"
                + "es_vendedor INTEGER DEFAULT 0);"; 

		stmt.execute(query);
		System.out.println("Tabla USUARIO creada con éxito.");

    }

    public static void crearTablaCompras(Statement stmt) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS Compra ("
                + "id_usuario INTEGER,"
                + "id_curso INTEGER,"
                + "fecha_compra DATE,"
                + "PRIMARY KEY (id_usuario, id_curso),"
                + "FOREIGN KEY (id_usuario) REFERENCES Usuario(id),"
                + "FOREIGN KEY (id_curso) REFERENCES Curso(id));";
        
        stmt.execute(query);
        System.out.println("Tabla COMPRAS creada con éxito.");
    }
    
    public static ArrayList<CourseFront> obtenerCursos() {
    	ArrayList<CourseFront> list = new ArrayList<CourseFront>();
        String query = "SELECT * FROM Curso";
        ResultSet rs = null;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
             rs = stmt.executeQuery(query);
             
             while(rs.next()) {
            	 
		            int id = rs.getInt("id");
		            String titulo = rs.getString("titulo");
		            String imgPath = rs.getString("imgPath");
		            list.add(new CourseFront(id,titulo,imgPath));
			}
             
        } catch (SQLException e) {
            System.out.println("Error al obtener los cursos: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    
    public static void crearCurso(String titulo, String descripcion, int duracion, 
            double precio, int clases, int instructorId, String idioma, String imgPath) throws SQLException {
		// Sentencia SQL para insertar un curso en la tabla 'Curso'
		String query = "INSERT INTO Curso (titulo, descripcion, duracion, precio, clases, instructor, idioma, imgPath) "
		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		// Crear un PreparedStatement para ejecutar la consulta con los valores proporcionados
        try (Connection conn = connect();  
                PreparedStatement pstmt = conn.prepareStatement(query)) {
		
            pstmt.setString(1, titulo);
            pstmt.setString(2, descripcion);
            pstmt.setInt(3, duracion);
            pstmt.setDouble(4, precio);
            pstmt.setInt(5, clases);
            pstmt.setInt(6, instructorId);
            pstmt.setString(7, idioma);
            pstmt.setString(8, imgPath);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Curso creado con éxito.");
            } else {
                System.out.println("No se pudo crear el curso.");
            }
		} catch (SQLException e) {
			System.err.println("Error al insertar el curso: " + e.getMessage());
			throw e; 
		}
	}
    
    public static void asignarVendedor(int id) throws SQLException {
    	 String query = "UPDATE Usuario SET es_vendedor = 1 WHERE id = ?";

    	 try (Connection conn = connect();  
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
    	        pstmt.setInt(1, id);

    	        int filasAfectadas = pstmt.executeUpdate();

    	        if (filasAfectadas > 0) {
    	            System.out.println("El usuario con ID " + id + " ha sido actualizado a vendedor.");
    	        } else {
    	            System.out.println("No se pudo actualizar el usuario con ID " + id + ".");
    	        }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el usuario: " + e.getMessage());
            throw e;
        }
    }
    
    public static boolean esVendedor(int id) throws SQLException {
        String query = "SELECT es_vendedor FROM Usuario WHERE id = ?";  // Usamos un parámetro para evitar inyección SQL
	   	try (Connection conn = connect();  
	   			PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id); 
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("es_vendedor") == 1;
                } else {
                    System.out.println("Usuario no encontrado.");
                    return false;
                }
            }
        }
    }
    
    public static int conseguirId(String username) {
        String query = "SELECT id FROM USUARIO WHERE username = ?";
        try (Connection conn = connect();  
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username); 
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                	return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; 
        }
    }

    public static ArrayList<Course> obtenerCursosPorInstructor(int instructorId) {
        String url = "jdbc:sqlite:src/db/Devora.db";
        String query = "select  * from Curso where instructor = 1";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Course> cursos = new ArrayList<>();

        try{
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descripcion = rs.getString("descripcion");
                int duracion = rs.getInt("duracion");
                double precio = rs.getDouble("precio");
                int clases = rs.getInt("clases");
                String idioma = rs.getString("idioma");
                String imgPath = rs.getString("imgPath");


                String instructor = conseguirUser(1);
                List<String> lista = new ArrayList<>();
                Course curso = new Course(id, titulo, descripcion, duracion, lista, precio, clases, instructor, idioma, 2.5, 52, 52, imgPath);
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cursos;
    }

    public static String conseguirUser(int id) {
        String query = "SELECT username FROM USUARIO WHERE id = ?";
        try (Connection conn = connect();  
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id); 
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;  
        }
    }
}
