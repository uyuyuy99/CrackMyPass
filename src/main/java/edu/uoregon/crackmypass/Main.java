package edu.uoregon.crackmypass;

import com.google.common.hash.Hashing;
import edu.uoregon.crackmypass.menu.MainMenu;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static String VERSION = "0.1";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        UIManager.put("DesktopPaneUI", "javax.swing.plaf.basic.BasicDesktopPaneUI");

        MainMenu gui = new MainMenu();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(MainMenu.WIDTH, MainMenu.HEIGHT);
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);
    }

}