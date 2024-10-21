package src.gui;

import src.gui.components.principal.Principal;
import javax.swing.*;

public class App {
    public void createRoot(){
       JFrame main = Principal.createPrincipal();
       main.revalidate();
    }
}
