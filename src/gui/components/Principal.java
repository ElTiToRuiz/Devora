package src.gui.components;
import javax.swing.*;

public class Principal {

    public static JFrame createPrincipal() {
        JFrame principal = new JFrame("DEVORA");
        principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        principal.setSize(400, 400);
        principal.setVisible(true);
        return principal;
    }
}
