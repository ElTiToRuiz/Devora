package src.gui;

import javax.swing.*;

// This is the principal page of the application.
public class App {
    public void createRoot(){
        JFrame principal = new JFrame("DEVORA");
        principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        principal.setSize(400, 400);
        principal.setVisible(true);
    }
}
