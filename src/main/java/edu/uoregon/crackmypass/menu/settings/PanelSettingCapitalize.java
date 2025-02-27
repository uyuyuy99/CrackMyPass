package edu.uoregon.crackmypass.menu.settings;

import edu.uoregon.crackmypass.Appendage;
import edu.uoregon.crackmypass.Cracker;

import javax.swing.*;
import java.awt.*;

public class PanelSettingCapitalize extends JPanel {

    private GridBagLayout layout;
    private GridBagConstraints c;

    private JCheckBox checkBoxFirst;
    private JCheckBox checkBoxAll;

    public PanelSettingCapitalize() {
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);

        // TODO add help text at the top

        c.gridx = 0; c.gridy = 0;
        checkBoxFirst = new JCheckBox("Capitalize first letter");
        add(checkBoxFirst, c);

        c.gridx = 0; c.gridy = 1;
        checkBoxAll = new JCheckBox("Capitalize all letters");
        add(checkBoxAll, c);

        checkBoxFirst.setSelected(Cracker.getCapFirst());
        checkBoxAll.setSelected(Cracker.getCapAll());
    }

    public void save() {
        Cracker.setCapFirst(checkBoxFirst.isSelected());
        Cracker.setCapAll(checkBoxAll.isSelected());
    }

}
