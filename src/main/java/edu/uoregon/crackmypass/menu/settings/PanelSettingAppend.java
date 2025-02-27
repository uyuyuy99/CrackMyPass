package edu.uoregon.crackmypass.menu.settings;

import edu.uoregon.crackmypass.Appendage;
import edu.uoregon.crackmypass.Cracker;
import edu.uoregon.crackmypass.menu.MainMenu;

import javax.swing.*;
import java.awt.*;

public class PanelSettingAppend extends JPanel {

    private GridBagLayout layout;
    private GridBagConstraints c;

    private JScrollPane scrollPane;
    private JTextArea textArea;

    public PanelSettingAppend() {
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);

        // TODO add help text at the top

        c.gridx = 0; c.gridy = 1;
        c.weightx = 1; c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        textArea = new JTextArea();
        textArea.setFont(textArea.getFont().deriveFont(Font.BOLD, 14f));
        scrollPane = new JScrollPane(textArea);
        add(scrollPane, c);

        for (Appendage appendage : Cracker.getAppends()) {
            textArea.append(appendage.getDisplayText() + "\n");
        }

        setPreferredSize(new Dimension(256, 320));
    }

    public void save() {
        Cracker.setAppends(textArea.getText().split("\n"));
    }

}
