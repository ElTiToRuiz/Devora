package src.domain.user;

import src.db.Database;
import src.utils.ClientType;

public class ClientModel {
    private String userName;
    private String email;
    private String password;
    private ClientType clientType;
    private Database db;

    public ClientModel(String userName, String email, String password, ClientType clientType) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.clientType = clientType;
    }

    public String getUserName() {return userName;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public ClientType getClientType() {return clientType;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setClientType(ClientType clientType) {this.clientType = clientType;}




//    private static void connectDB(){
//        db = new Database().setUp();
//    }

    public static void main(String[] args) {
//        Database.setUp();
    }
}
