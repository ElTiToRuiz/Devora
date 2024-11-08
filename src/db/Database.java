package src.db;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// this is the database class that will be used to connect sava and get data from the database
public class Database {
    private String path;
    private Connection connection;

    public Database() {
        this.path = "jdbc:sqlite:src/db/devora.db";
        this.connection = null;
    }
    public Database(String path) {
        this.path = path;
        this.connection = null;
    }

    public static Database setUp(){
        return (Database) new Database().connect();
    }

    public String getUrl() {return path;}
    public Connection getConnection() {return connection;}
    public void setUrl(String path) {
        try {
            if (existPath(path)) {
                this.path = path;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public Boolean existPath(String path) throws FileNotFoundException {
        if(Files.exists(Paths.get(path))) {
            return true;
        }else {
            throw new FileNotFoundException("The file does not exist");
        }
    }

    private Connection connect() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            connection = DriverManager.getConnection(getUrl());
            System.out.println("Connection to SQLite has been established.");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    private void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void executeQuery(String query) {
        try {
            connect().createStatement().execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public void createTable(String tableName, String columns) {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columns + ")";
        executeQuery(query);
    }

    public void dropTable(String tableName) {
        String query = "DROP TABLE IF EXISTS " + tableName;
        executeQuery(query);
    }

    public void insertData(String tableName, String columns, String values) {
        String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
        executeQuery(query);
    }

    public void updateData(String tableName, String columns, String values, String condition) {
        String query = "UPDATE " + tableName + " SET " + columns + " = " + values + " WHERE " + condition;
        executeQuery(query);
    }

    public void deleteData(String tableName, String condition) {
        String query = "DELETE FROM " + tableName + " WHERE " + condition;
        executeQuery(query);
    }
}