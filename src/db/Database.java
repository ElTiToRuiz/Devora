package src.db;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        if(Files.exists(Path.of(path))) {
            return true;
        }else {
            throw new FileNotFoundException("The file does not exist");
        }
    }

    private Connection connect() {
        try {
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

    public void createTable(String query) {
        try {
            // TODO - CHECK QUERY FOR POSIBLE INYECCTION SQL
            connect().createStatement().execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection();
        }
    }
}
