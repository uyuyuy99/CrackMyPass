package edu.uoregon.crackmypass;

import edu.uoregon.crackmypass.menu.MainMenu;

import javax.swing.*;

public class Main {

    public static String VERSION = "1.0";

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
        gui.setIconImage(new ImageIcon(ClassLoader.getSystemResource("icon/logo.png")).getImage());
    }

}