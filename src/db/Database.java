package src.db;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private String path;
    private Connection connection;

    // Default constructor with a default path to the database
    public Database() {
        this.path = "jdbc:sqlite:src/db/devora.db";
        this.connection = null;
    }

    // Constructor allowing a custom path to the database
    public Database(String path) {
        this.path = path;
        this.connection = null;
    }

    // Method to set up a connection and return a Database instance
    public static Database setUp() {
        Database db = new Database();
        db.connect(); // Connect without returning anything
        return db;
    }

    public String getUrl() {
        return path;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setUrl(String path) {
        try {
            if (existPath(path)) {
                this.path = path;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Check if the file at the given path exists
    public Boolean existPath(String path) throws FileNotFoundException {
        if (Files.exists(Paths.get(path))) {
            return true;
        } else {
            throw new FileNotFoundException("The file does not exist at the given path.");
        }
    }

    // Connect to the database
    private Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(getUrl());
                System.out.println("Connection to SQLite has been established.");
            }
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
        return connection;
    }

    // Close the database connection
    private void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    // Execute a query (used for creating, updating, deleting tables, etc.)
    private void executeQuery(String query) {
        try (Statement stmt = connect().createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    // Create a table in the database
    public void createTable(String tableName, String columns) {
        String query = String.format("CREATE TABLE IF NOT EXISTS %s (%s)", tableName, columns);
        executeQuery(query);
    }

    // Drop a table from the database
    public void dropTable(String tableName) {
        String query = String.format("DROP TABLE IF EXISTS %s", tableName);
        executeQuery(query);
    }

    // Insert data into a table
    public void insertData(String tableName, String columns, String values) {
        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
        executeQuery(query);
    }

    // Update data in a table based on a condition
    public void updateData(String tableName, String columns, String values, String condition) {
        String query = String.format("UPDATE %s SET %s WHERE %s", tableName, columns + " = " + values, condition);
        executeQuery(query);
    }

    // Delete data from a table based on a condition
    public void deleteData(String tableName, String condition) {
        String query = String.format("DELETE FROM %s WHERE %s", tableName, condition);
        executeQuery(query);
    }
}
