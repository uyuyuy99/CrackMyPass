package edu.uoregon.crackmypass.menu;

import edu.uoregon.crackmypass.Cracker;

import javax.swing.*;
import java.awt.*;

public class PanelStart extends JPanel {

    private static GridBagLayout layout;
    private static GridBagConstraints c;

    private static JButton btnStart;
    private static JButton btnStop;

    public PanelStart() {
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);

        c.gridx = 0; c.gridy = 0;
        c.insets = new Insets(4, 4, 4, 4);
        btnStart = new JButton("Start");
        btnStart.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/start.png")));
        btnStart.setIconTextGap(10);
        btnStart.setMargin(new Insets(8, 20, 8, 20));
        Font startFont = btnStart.getFont().deriveFont(20f);
        btnStart.setFont(startFont);
        add(btnStart, c);
        btnStart.addActionListener(e -> {
            PanelOutput.reset();
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            Cracker.startCracking();
        });

        c.gridx = 1; c.gridy = 0;
        btnStop = new JButton("Stop");
        btnStop.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/stop.png")));
        btnStop.setIconTextGap(10);
        btnStop.setMargin(new Insets(8, 20, 8, 20));
        btnStop.setFont(startFont);
        btnStop.setEnabled(false);
        add(btnStop, c);
        btnStop.addActionListener(e -> {
            btnStop.setEnabled(false);
            btnStart.setEnabled(true);
            Cracker.stopCracking();
        });
    }

    public static void onComplete() {
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
    }

}
