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

        final String html = "<html><body style='width: %1spx'>%1s";
        String text = "<center>Each new line of text will be appended to the end of each word. " +
                "You can specify ranges of numbers to append, like <b>0 - 100</b></center>";
        JLabel label = new JLabel(String.format(html, 200, text));
        c.gridx = 0; c.gridy = 0;
        c.weighty = 0;
        c.insets = new Insets(0, 0, 10, 0);
        add(label, c);

        c.gridx = 0; c.gridy = 1;
        c.weightx = 1; c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        textArea = new JTextArea();
        textArea.setFont(textArea.getFont().deriveFont(Font.BOLD, 14f));
        scrollPane = new JScrollPane(textArea);
        c.insets = new Insets(0, 0, 0, 0);
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
