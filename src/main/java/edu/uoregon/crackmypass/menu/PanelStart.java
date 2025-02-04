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
        btnStart.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/start.png")));
        btnStart.setIconTextGap(10);
        btnStart.setMargin(new Insets(8, 20, 8, 20));
        Font startFont = btnStart.getFont().deriveFont(20f);
        btnStart.setFont(startFont);
        add(btnStart, c);
        btnStart.addActionListener(e -> {
            btnStart.setEnabled(false);
            Cracker.startCracking();
        });

//        setBackground(Color.YELLOW);
    }

    public static void onComplete() {
        btnStart.setEnabled(true);
    }

}
