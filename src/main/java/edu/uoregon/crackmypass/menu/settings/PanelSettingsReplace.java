package edu.uoregon.crackmypass.menu.settings;

import edu.uoregon.crackmypass.Appendage;
import edu.uoregon.crackmypass.Cracker;
import edu.uoregon.crackmypass.menu.PanelOutput;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelSettingsReplace extends JPanel {

    private GridBagLayout layout;
    private GridBagConstraints c;

    private JScrollPane scrollPane;
    private JTable table;
    private String[] columns = new String[] { "Replace this", "with this" };

    public PanelSettingsReplace() {
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);

        final String html = "<html><body style='width: %1spx'>%1s";
        String text = "<center>Replace strings of text in the word. " +
                "For example, users often replace <b>a</b> with <b>@</b> in their passwords.</center>";
        JLabel label = new JLabel(String.format(html, 200, text));
        c.gridx = 0; c.gridy = 0;
        c.weighty = 0;
        c.insets = new Insets(0, 0, 10, 0);
        add(label, c);

        List<Pair<String, String>> pairs = Cracker.getReplacements();
        Object[][] data = new Object[pairs.size()][];
        for (int i = 0; i < pairs.size(); i++) {
            Pair<String, String> pair = pairs.get(i);
            data[i] = new Object[2];
            data[i][0] = pair.getLeft();
            data[i][1] = pair.getRight();
        }

        c.gridx = 0; c.gridy = 1;
        c.weightx = 1; c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        table = new JTable(data, columns);
        table.setFont(table.getFont().deriveFont(Font.BOLD, 14f));
        scrollPane = new JScrollPane(table);
        c.insets = new Insets(0, 0, 0, 0);
        add(scrollPane, c);

        setPreferredSize(new Dimension(256, 320));
    }

    public void save() {
        //TODO
//        Cracker.setPrepends(textArea.getText().split("\n"));
    }

}
