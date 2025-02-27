package edu.uoregon.crackmypass.menu;

import edu.uoregon.crackmypass.Main;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public static int WIDTH = 768;
    public static int HEIGHT = 512;

    private JPanel panel;
    private GridBagLayout layout;
    private GridBagConstraints c;

    private PanelLoadFiles panelLoadFiles;
    private PanelSettings panelSettings;
    private PanelStart panelStart;
    private PanelOutput panelOutput;

    public MainMenu() {
        super("Crack Attack v" + Main.VERSION);

        layout = new GridBagLayout();
        panel = new JPanel(layout);
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(layout);

        c = new GridBagConstraints();
//        c.anchor = GridBagConstraints.CENTER;

        c.gridx = 0; c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        panelLoadFiles = new PanelLoadFiles(this);
        panel.add(panelLoadFiles, c);

//        c.gridx = 0; c.gridy = 1;
//        c.anchor = GridBagConstraints.PAGE_START;
//        c.fill = GridBagConstraints.HORIZONTAL;
//        panelSettings = new PanelSettings(this);
//        panel.add(panelSettings, c);

        c.gridx = 0; c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.ipady = 12;
        c.ipadx = 12;
        panelStart = new PanelStart();
        panel.add(panelStart, c);

        c.gridx = 1; c.gridy = 0;
        c.gridheight = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.fill = GridBagConstraints.VERTICAL;
        panelOutput = new PanelOutput();
//        panel.add(panelOutput, c);
        getContentPane().add(panelOutput, BorderLayout.EAST);

//        panel.setBackground(Color.BLUE);
    }

}
