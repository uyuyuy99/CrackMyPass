package edu.uoregon.crackmypass.menu;

import javax.swing.*;
import java.awt.*;

public class PanelSettings extends JPanel {

    private FlowLayout layout;
    private GridBagConstraints c;

    private JSpinner spinAddNumbers1;
    private JSpinner spinAddNumbers2;
    private JLabel labelAddNumbers1;
    private JLabel labelAddNumbers2;
    private JLabel labelAddNumbers3;

    public PanelSettings(final MainMenu mainMenu) {
        layout = new FlowLayout();
//        c = new GridBagConstraints();
        setLayout(layout);

//        c.insets = new Insets(12, 8, 0, 0);
//        c.gridx = 0; c.gridy = 0;
        add(new JLabel("Append numbers from "));
        spinAddNumbers1 = new JSpinner(new SpinnerNumberModel(0, 0, 99999, 1));
        add(spinAddNumbers1);

        add(new JLabel(" to "));
        spinAddNumbers2 = new JSpinner((new SpinnerNumberModel(0, 0, 99999, 1)));
        add(spinAddNumbers2);
    }

}
