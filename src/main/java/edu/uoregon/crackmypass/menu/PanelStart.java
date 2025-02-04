package edu.uoregon.crackmypass.menu;

import edu.uoregon.crackmypass.Cracker;

import javax.swing.*;
import java.awt.*;

public class PanelStart extends JPanel {

    private static GridBagLayout layout;
    private static GridBagConstraints c;

    private static JButton btnStart;

    public PanelStart() {
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);

        c.gridx = 0; c.gridy = 0;
        btnStart = new JButton("Start");
        add(btnStart, c);
        btnStart.addActionListener(e -> {
            Cracker.startCracking();
        });

//        setBackground(Color.YELLOW);
    }

}
