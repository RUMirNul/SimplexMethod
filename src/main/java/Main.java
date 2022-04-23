import controller.Controller;
import model.Model;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        JFrame mainFrame = new JFrame();


        mainFrame.setTitle("Simplex Method");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 1000);
        mainFrame.setResizable(true);
        mainFrame.add(controller.getView());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
