package src.db;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
    private static Database instance; 
    private String path;
    private Connection connection;

    // Default constructor with a default path to the database
    private Database() { // Hacer privado el constructor
        this.path = "jdbc:sqlite:src/db/devora.db";
        this.connection = null;
    }

    public static Database getInstance() {
        if (instance == null) { // Crear instancia si no existe
            instance = new Database();
            instance.connect(); // Establecer la conexión al crear la instancia
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private Connection connect() {
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
    
    
    public boolean verifyCredentials(String usernameOrEmail, String password) {
        String query = "SELECT COUNT(*) AS count FROM Usuario WHERE (username = ? OR email = ?) AND password = ?";
        try (Connection conn = connect(); // Conectar a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Configurar los parámetros de la consulta
            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);
            stmt.setString(3, password);

            // Ejecutar la consulta y verificar el resultado
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt("count") > 0) {
                return true; // Credenciales válidas
            }
        } catch (Exception e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }
        return false; // Credenciales no válidas
    }
    
    
}
