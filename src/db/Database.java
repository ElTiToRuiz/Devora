package src.db;

import java.io.FileNotFoundException;

import org.mindrot.jbcrypt.BCrypt;

import src.domain.Course;
import src.gui.components.course.CourseFront;
import src.gui.components.course.CourseIncome;
import src.utils.Encriptar;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        String query = "SELECT COUNT(*) AS count, PASSWORD as password FROM USUARIO WHERE username = ?";
        try (Connection conn = connect(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.getInt("count") > 0) {
            	String hashedPassword = rs.getString("password");
            	if(BCrypt.checkpw(password, hashedPassword)) {
            		return true;
            	}
                return false; 
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
                + "rating REAL,"
                + "categoria TEXT,"
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
        String url = "jdbc:sqlite:src/db/Devora.db"; //Para solucionar el problema stmt close, tenemos que poner manualmente los datos
        ArrayList<CourseFront> list = new ArrayList<>();
        String query = "SELECT * FROM Curso";
        ResultSet rs = null;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
             
            rs = stmt.executeQuery(query);  

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                Double price = rs.getDouble("precio");
                String categoria = null; 
                Double rating = rs.getDouble("rating");
                
                int duracion = rs.getInt("duracion");
                String imgPath = rs.getString("imgPath");

                list.add(new CourseFront(id, price, titulo, categoria, rating, duracion, imgPath));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los cursos: " + e.getMessage());
            e.printStackTrace();
        }
        return list;  
    }

    
    public static void crearCurso(String titulo, String descripcion, int duracion, 
            double precio, int clases, int instructorId, String idioma, String imgPath, Double rating, String categoria) throws SQLException {
		// Sentencia SQL para insertar un curso en la tabla 'Curso'
        String url = "jdbc:sqlite:src/db/Devora.db";
		String query = "INSERT INTO Curso (titulo, descripcion, duracion, precio, clases, instructor, idioma, imgPath, rating, categoria) "
		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;

		// Crear un PreparedStatement para ejecutar la consulta con los valores proporcionados
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, titulo);
            pstmt.setString(2, descripcion);
            pstmt.setInt(3, duracion);
            pstmt.setDouble(4, precio);
            pstmt.setInt(5, clases);
            pstmt.setInt(6, instructorId);
            pstmt.setString(7, idioma);
            pstmt.setString(8, imgPath);
            pstmt.setDouble(9, rating);
            pstmt.setString(10, categoria);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println(titulo + " creado con éxito.");
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
        String query = "select  * from Curso where instructor = " + instructorId;

        Connection conn;
        Statement stmt;
        ResultSet rs;
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
                Course curso = new Course(id, titulo, descripcion, duracion, "hola", precio, clases, instructor, idioma, 2.5, 52, 52, imgPath);
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
    
    public static String conseguirMail(int id) {
        String query = "SELECT email FROM USUARIO WHERE id = ?";
        try (Connection conn = connect();  
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id); 
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;  
        }
    }
    
    public static String conseguirPassword(int id) {    	
        String query = "SELECT password FROM USUARIO WHERE id = ?";
        try (Connection conn = connect();  
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id); 
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;  
        }    	
    }
    
    public static Double conseguirSaldo(int id) {
    	String query = "SELECT saldo FROM USUARIO WHERE id = ?";
        try (Connection conn = connect();  
                PreparedStatement pstmt = conn.prepareStatement(query)) {
               pstmt.setInt(1, id); 
               
               try (ResultSet rs = pstmt.executeQuery()) {
                   if (rs.next()) {
                       return Double.parseDouble(rs.getString("saldo"));
                   } else {
                       return null;
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
               return null;  
           }    	
       }
    
    public static Course conseguirCurso(int id) {
        String url = "jdbc:sqlite:src/db/Devora.db";
        String query = "SELECT id, titulo, descripcion, duracion, precio, clases, instructor, idioma, imgPath, rating, Categoria FROM Curso WHERE id = ?";
        
        // Usamos try-with-resources para cerrar la conexión y el Statement automáticamente
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Establecer el parámetro del query para prevenir inyecciones SQL
            stmt.setInt(1, id);

            // Ejecutar la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Leer los valores de la fila
                    String titulo = rs.getString("titulo");
                    String desc = rs.getString("descripcion");
                    int duracion = rs.getInt("duracion");
                    Double precio = rs.getDouble("precio");
                    int clases = rs.getInt("clases");
                    String instructor = conseguirUser(rs.getInt("instructor")); // Asumiendo que este método es correcto
                    String idioma = rs.getString("idioma");
                    String imgPath = rs.getString("imgPath");
                    Double rating = rs.getDouble("rating");
                    String categoria = rs.getString("categoria");

                    // Crear un objeto Course con los datos obtenidos
                    return new Course(id, titulo, desc, duracion, categoria, precio, clases, instructor, idioma, rating, 244, 524, imgPath);
                } else {
                    return null; // No se encontró el curso
                }
            }
        } catch (SQLException e) {
            // Mejor manejo de errores con detalles
            e.printStackTrace();
            return null;
        }
    }

    
    public static boolean comprobarCompra(int id, int idCurso) {
        // Consulta SQL para obtener el id_curso de la tabla Compra para el id_usuario dado
        String query = "SELECT id_curso FROM Compra WHERE id_usuario = ?";
        
        try (Connection conn = connect();  
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            // Establecer el parámetro del id_usuario
            pstmt.setInt(1, id); 
            
            try (ResultSet rs = pstmt.executeQuery()) {
                // Recorrer el ResultSet y verificar si id_curso coincide
                while (rs.next()) {
                    // Si encontramos un id_curso que coincida con el idCurso proporcionado
                    if (idCurso == rs.getInt("id_curso")) {
                        return true;  // Compra encontrada
                    }
                }
                // Si no encontramos ninguna coincidencia
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Si ocurre algún error en la consulta
        }
    }

    
    public static boolean comprobarCreador(int id,int idCurso) {
    	String query = "SELECT instructor FROM Curso WHERE id = ?";
    	try (Connection conn = connect();  
                PreparedStatement pstmt = conn.prepareStatement(query)) {
               pstmt.setInt(1, idCurso); 
               try (ResultSet rs = pstmt.executeQuery()) {
                   if (rs.next()) {
                	   int instructor = rs.getInt("instructor");
                	   if(instructor == id) {
                		   return true;
                	   }
                   } else {
                       return false;
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
               return false;  
           }
		return false;
    }
    
    public static void actualizarBalance(int id, Double balance) throws SQLException {
    	String query = "UPDATE Usuario SET saldo = ? WHERE id = ?";
   	 try (Connection conn = connect();  
             PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setDouble(1, balance);
	        pstmt.setInt(2, id);
	        
	        int filasAfectadas = pstmt.executeUpdate();

	        if (filasAfectadas > 0) {
	            System.out.println("El usuario con ID " + id + " ha recibido su saldo actualizado.");
	        } else {
	            System.out.println("No se pudo actualizar el usuario con ID " + id + ".");
	        }
    } catch (SQLException e) {
        System.err.println("Error al actualizar el usuario: " + e.getMessage());
        throw e;
    }
}
    
    public static void registrarCompra(int id, int idCurso) throws SQLException {
    	String query = "INSERT INTO Compra (id_usuario, id_curso, fecha_compra) VALUES (?, ?, CURRENT_DATE)";
   	 try (Connection conn = connect();  
            PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setInt(1, id);
	        pstmt.setInt(2, idCurso);
	        
	        int filasAfectadas = pstmt.executeUpdate();

	        if (filasAfectadas > 0) {
	            System.out.println("Compra registrada");
	        } else {
	            System.out.println("No se ha registrado la compra");
	        }
    } catch (SQLException e) {
        System.err.println("Error al registrar la compra: " + e.getMessage());
        throw e;
    }
    }
    
    public static int conseguirEstudiantes(int id) {
        String query = "SELECT COUNT(DISTINCT comp.id_usuario) AS estudiantes " +
                       "FROM COMPRA AS comp " +
                       "JOIN CURSO AS c ON comp.id_curso = c.id " +  // Cambié 'id_curso' por 'id'
                       "WHERE c.instructor = ?";  // Filtrar por instructor

        try (Connection conn = connect();  
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            // Establecer el valor del parámetro 'id' (del instructor)
            pstmt.setInt(1, id); 
            
            try (ResultSet rs = pstmt.executeQuery()) {
                // Si la consulta tiene resultados, obtener el número de estudiantes
                if (rs.next()) {
                    return rs.getInt("estudiantes");
                } else {
                    return 0;  // Si no hay resultados, retornar 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;  // Si ocurre un error, retornar 0
        }
    }


    
    public static int conseguirNumCursos(int id) {
    	String query = "SELECT COUNT(id) as num FROM CURSO WHERE INSTRUCTOR = ?";
        try (Connection conn = connect();  
                PreparedStatement pstmt = conn.prepareStatement(query)) {
               
               pstmt.setInt(1, id); 
               
               try (ResultSet rs = pstmt.executeQuery()) {
                   if (rs.next()) {
                       return rs.getInt("num");
                   } else {
                       return 0;  
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
               return 0;
           }
       }
    
    public static Double conseguirDineroGenerado(int id) {
        String query = "SELECT SUM(D.Total * D.Precio) AS Generado " +
                       "FROM ( " +
                       "    SELECT B.id_curso, COUNT(B.id_usuario) AS Total, B.Precio AS Precio " +
                       "    FROM ( " +
                       "        SELECT A.id_usuario, A.id_curso, C.Precio " +
                       "        FROM COMPRA AS A " +
                       "        JOIN CURSO AS C ON A.id_curso = C.id " +
                       "        WHERE C.instructor = ? " +  
                       "    ) AS B " +
                       "    GROUP BY B.id_curso " +
                       ") AS D";

        try (Connection conn = connect();  
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id); 
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("Generado");
                } else {
                    return 0.0; 
                }
            }
        } catch (SQLException e) {
            // Manejar excepciones
            e.printStackTrace();
            return 0.0;
        }
    }


    public static Map<Integer, String> conseguirDatosHistorial(int id) {
        String query = "SELECT id_curso, fecha_compra FROM Compra WHERE id_usuario = ?";
        Map<Integer, String> compras = new HashMap<>();

        try (Connection conn = connect();  
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int idCurso = rs.getInt("id_curso");
                    String fechaCompra = rs.getString("fecha_compra");

                    System.out.println(fechaCompra);
                    compras.put(idCurso, fechaCompra);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compras;
    }

    	
    public static Map<DayOfWeek, Integer> conseguirVentasSemanales(int id, LocalDate comienzoSemana, LocalDate finalSemana) {
        Map<DayOfWeek, Integer> salesData = new HashMap<>();

        String query = "SELECT strftime('%w', B.fecha_compra) AS dia, " +
                "COUNT(B.id_curso) AS total_ventas " +
                "FROM " +
                "    (SELECT C.id_usuario, C.id_curso, C.fecha_compra " +
                "     FROM Compra AS C " +
                "     JOIN " +
                "        (SELECT id " +
                "         FROM Curso " +
                "         WHERE instructor = ?) AS A " +
                "     ON A.id = C.id_curso) AS B " +
                "WHERE B.fecha_compra BETWEEN ? AND ? " +
                "GROUP BY strftime('%w', B.fecha_compra);";
        
        try (Connection conn = connect();  
             PreparedStatement pstmt = conn.prepareStatement(query)) {

        	pstmt.setInt(1, id);
            pstmt.setString(2, comienzoSemana.toString());
            pstmt.setString(3, finalSemana.toString());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int dayOfWeek = rs.getInt("dia"); 
                int totalSales = rs.getInt("total_ventas");

                for (DayOfWeek day : DayOfWeek.values()) {
                    salesData.putIfAbsent(day, 0);
                }

                DayOfWeek day = DayOfWeek.of((dayOfWeek == 0) ? 7 : dayOfWeek); 
                salesData.put(day, totalSales);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesData;
    }
    
    public static void eliminarCurso(int id) throws SQLException {
    	String query = "DELETE FROM CURSO WHERE id = ?";
   	 try (Connection conn = connect();  
             PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setDouble(1, id);

	        int filasAfectadas = pstmt.executeUpdate();

	        if (filasAfectadas > 0) {
	            System.out.println("El curso se ha eliminado con exito");
	        } else {
	            System.out.println("No se pudo eliminar el curso");
	        }
    } catch (SQLException e) {
        System.err.println("Error al eliminar el curso" + e.getMessage());
        throw e;
    }
}
    
    public static boolean actualizarCurso(int idCurso, String titulo, String descripcion, double precio, String idioma, int clases) throws SQLException {
    	String query = "UPDATE SET Curso titulo = ?, descripcion = ?, precio = ?, clases = ?, idioma = ? WHERE id = ?";
      	 try (Connection conn = connect();  
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
	             pstmt.setString(1, titulo);
	             pstmt.setString(2, descripcion);
	             pstmt.setDouble(3, precio);
	             pstmt.setInt(4, clases);
	             pstmt.setString(5, idioma);
	             pstmt.setInt(6, idCurso);


    	        int filasAfectadas = pstmt.executeUpdate();

    	        if (filasAfectadas > 0) {
    	            return true;
    	        } else {
    	            return false;
    	        }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el curso" + e.getMessage());
            throw e;
        }
    }
    
    public static boolean actualizarDatos(String username, String mail,int id) throws SQLException {
    	String query = "UPDATE Usuario SET username = ?, email = ? WHERE id = ?";
      	 try (Connection conn = connect();  
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
	             pstmt.setString(1, username);
	             pstmt.setString(2, mail);
	             pstmt.setInt(3, id);

    	        int filasAfectadas = pstmt.executeUpdate();

    	        if (filasAfectadas > 0) {
    	            return true;
    	        } else {
    	            return false;
    	        }
        } catch (SQLException e) {
            System.err.println("Error al actualizar los datos" + e.getMessage());
            throw e;
        }
    }
    
    public static boolean comprobarUsuario(String username) throws SQLException{
    	String query = "SELECT * FROM Usuario Where username = ?";
    	try (Connection conn = connect();  
                PreparedStatement pstmt = conn.prepareStatement(query)) {
               pstmt.setString(1, username); 
               try (ResultSet rs = pstmt.executeQuery()) {
                   if (rs.next()) {
                	   String user = rs.getString("username");
                	   if(user == username) {
                		   return true;
                	   }
                   } else {
                       return false;
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
               return false;  
           }
		return false;
    }
    
    public static boolean comprobarMail(String mail) throws SQLException{
    	String query = "SELECT * FROM Usuario Where username = ?";
    	try (Connection conn = connect();  
                PreparedStatement pstmt = conn.prepareStatement(query)) {
               pstmt.setString(1, mail); 
               try (ResultSet rs = pstmt.executeQuery()) {
                   if (rs.next()) {
                	   String email = rs.getString("email");
                	   if(email == mail) {
                		   return true;
                	   }
                   } else {
                       return false;
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
               return false;  
           }
		return false;
    }
    
    public static ArrayList<CourseIncome> obtenerIngresosPorcentaje(int id) throws SQLException{
    	
        ArrayList<CourseIncome> ingresosCursoPorcentaje = new ArrayList<CourseIncome>();
        
        String query = "SELECT D.id_curso, ROUND((D.Recaudado / SUM(D.Recaudado) OVER () * 100), 2) AS Porcentaje "
                + "FROM (SELECT B.id_curso, SUM(B.precio) AS Recaudado "
                + "FROM (SELECT A.id_usuario, A.id_curso, C.precio "
                + "FROM Compra AS A "
                + "JOIN (SELECT id, precio FROM Curso WHERE instructor = ?) AS C "
                + "ON A.id_curso = C.id) AS B "
                + "GROUP BY B.id_curso) AS D;";
        
    	try (Connection conn = connect();  
                PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setInt(1, id); 
	            try (ResultSet rs = pstmt.executeQuery()) {
		            while (rs.next()) {
		                int idCurso = rs.getInt("id_curso");
		                double porcentaje = rs.getDouble("Porcentaje");
		                ingresosCursoPorcentaje.add(new CourseIncome(idCurso, porcentaje));
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
        return ingresosCursoPorcentaje;
    }
}
    
}