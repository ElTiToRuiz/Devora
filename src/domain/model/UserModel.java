//package src.domain.model;
//
//import src.db.Database;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.UUID;
//
//public class UserModel {
//    private Database database;
//
//    // Constructor que recibe una instancia de Database
//    public UserModel(Database database) {
//        this.database = database;
//    }
//
//    // Método para crear la tabla de usuarios si no existe
//    public void createTable() {
//        // Cambiamos el tipo de id a UUID (TEXT) y añadimos el campo de contraseña
//        String columns = "id TEXT PRIMARY KEY, name TEXT NOT NULL, email TEXT NOT NULL UNIQUE, password TEXT NOT NULL, role TEXT NOT NULL";
//        database.createTable("users", columns);
//    }
//
//    // Método para insertar un nuevo usuario en la base de datos
//    public boolean createUser(String name, String email, String password, String role) {
//        String id = UUID.randomUUID().toString(); // Generamos un UUID para el id
//        String columns = "id, name, email, password, role";
//        String values = "'" + id + "', '" + name + "', '" + email + "', '" + password + "', '" + role + "'";
//
//        try {
//            database.insertData("users", columns, values);
//            return true;
//        } catch (Exception e) {
//            System.out.println("Error creating user: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // Método para obtener un usuario por su id
//    public User getUser(String id) {
//        String query = "SELECT * FROM users WHERE id = '" + id + "'";
//        try (Connection connection = database.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//
//            if (rs.next()) {
//                String userId = rs.getString("id");
//                String name = rs.getString("name");
//                String email = rs.getString("email");
//                String password = rs.getString("password");
//                String role = rs.getString("role");
//                return new User(userId, name, email, password, role);
//            } else {
//                return null; // Usuario no encontrado
//            }
//        } catch (SQLException e) {
//            System.out.println("Error getting user: " + e.getMessage());
//            return null;
//        }
//    }
//
//    // Método para obtener todos los usuarios de la base de datos
//    public List<User> getAllUsers() {
//        String query = "SELECT * FROM users";
//
//        try (Connection connection = database.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//            List<User> users = null;
//            while (rs.next()) {
//                String id = rs.getString("id");
//                String name = rs.getString("name");
//                String email = rs.getString("email");
//                String role = rs.getString("role");
//                users.add(new User(id, name, email, "", role));
//            }
//            return users;
//        } catch (SQLException e) {
//            System.out.println("Error getting all users: " + e.getMessage());
//            return null;
//        }
//    }
//
//    // Método para actualizar un usuario
//    public boolean updateUser(String id, String newName, String newEmail, String newPassword, String newRole) {
//        String columns = "name = '" + newName + "', email = '" + newEmail + "', password = '" + newPassword + "', role = '" + newRole + "'";
//        String condition = "id = '" + id + "'";
//
//        try {
//            database.updateData("users", columns, "", condition);
//            return true;
//        } catch (Exception e) {
//            System.out.println("Error updating user: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // Método para eliminar un usuario
//    public boolean deleteUser(String id) {
//        String condition = "id = '" + id + "'";
//        try {
//            database.deleteData("users", condition);
//            return true;
//        } catch (Exception e) {
//            System.out.println("Error deleting user: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // Clase interna que representa un usuario
//    public static class User {
//        private String id;
//        private String name;
//        private String email;
//        private String password;
//        private String role;
//
//        public User(String id, String name, String email, String password, String role) {
//            this.id = id;
//            this.name = name;
//            this.email = email;
//            this.password = password;
//            this.role = role;
//        }
//
//        public String getId() { return id; }
//        public String getName() { return name; }
//        public String getEmail() { return email; }
//        public String getPassword() { return password; }
//        public String getRole() { return role; }
//    }
//}
