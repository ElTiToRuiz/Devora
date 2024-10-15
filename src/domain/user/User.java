package src.domain.user;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class User {
    private String id;
    private int age;
    private String name;
    private String username;
    private String email;
    private String passwordHash;
    private Date lastLogin;
    private boolean isActive;
    private Map<String, String> preferences;
    private String profilePictureUrl;
    private String bio;

    public User() {
        this.id = UUID.randomUUID().toString();;
        this.name = "";
        this.age = 0;
        this.username = "";
        this.email = "";
        this.passwordHash = "";
        this.lastLogin = new Date();
        this.isActive = false;
        this.preferences = Map.of();
        this.profilePictureUrl = "";
        this.bio = "";
    }

    public User(String name, String username, int age, String email, String passwordHash){
        this.id = UUID.randomUUID().toString();;
        this.name = name;
        this.username = username;
        this.age = age;
        this.email = email;
        this.passwordHash = "";
        this.lastLogin = new Date();
        this.isActive = false;
        this.preferences = Map.of();
        this.profilePictureUrl = "";
        this.bio = "";
    }

    public String getId() {return id;}
    public String getName() {return name;}
    public int getAge() {return age;}
    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public String getPasswordHash() {return passwordHash;}
    public Date getLastLogin() {return lastLogin;}
    public boolean getIsActive() {return isActive;}
    public Map<String, String> getPreferences() {return preferences;}
    public String getProfilePictureUrl() {return profilePictureUrl;}
    public String getBio() {return bio;}

    public void setName(String name) {this.name = name;}
    public void setAge(int age) {this.age = age;}
    public void setUsername(String username) {this.username = username;}
    public void setEmail(String email) {this.email = email;}
    public void setPasswordHash(String passwordHash) {this.passwordHash = passwordHash;}
    public void setLastLogin(Date lastLogin) {this.lastLogin = lastLogin;}
    public void setIsActive(boolean isActive) {this.isActive = isActive;}
    public void setPreferences(Map<String, String> preferences) {this.preferences = preferences;}
    public void setProfilePictureUrl(String profilePictureUrl) {this.profilePictureUrl = profilePictureUrl;}
    public void setBio(String bio) {this.bio = bio;}
}
