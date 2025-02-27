package edu.uoregon.crackmypass.menu;

import edu.uoregon.crackmypass.Util;

import javax.swing.*;
import java.awt.*;

public class PanelOutput extends JPanel {

    private static GridBagLayout layout;
    private static GridBagConstraints c;

    private static JLabel labelFoundText;
    private static JLabel labelFoundAmount;
    private static JLabel labelTriedText;
    private static JLabel labelTriedAmount;
    private static JScrollPane paneOutput;
    private static JTextArea textOutput;

    public PanelOutput() {
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);

        c.gridx = 0; c.gridy = 0;
        c.weighty = 0;
        c.insets = new Insets(8, 8, 4, 0);
        c.anchor = GridBagConstraints.EAST;
        labelFoundText = new JLabel("Passwords Cracked:");
        Font labelFont = labelFoundText.getFont().deriveFont(Font.BOLD, 14f);
        labelFoundText.setFont(labelFont);
        add(labelFoundText, c);

        c.gridx = 1; c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        labelFoundAmount = new JLabel();
        labelFoundAmount.setFont(labelFont);
        add(labelFoundAmount, c);

        c.gridx = 0; c.gridy = 1;
        c.insets = new Insets(4, 8, 8, 0);
        c.anchor = GridBagConstraints.EAST;
        labelTriedText = new JLabel("Passwords Tried:");
        labelTriedText.setFont(labelFont);
        add(labelTriedText, c);

        c.gridx = 1; c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        labelTriedAmount = new JLabel();
        labelTriedAmount.setFont(labelFont);
        add(labelTriedAmount, c);

        c.gridx = 0; c.gridy = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 8, 8, 8);
        c.anchor = GridBagConstraints.SOUTH;
        c.fill = GridBagConstraints.BOTH;
        textOutput = new JTextArea();
        textOutput.setForeground(Color.GREEN);
        textOutput.setBackground(Color.BLACK);
        paneOutput = new JScrollPane(textOutput);
        paneOutput.setPreferredSize(new Dimension(384, 320));
        add(paneOutput, c);

        setFoundAmount(0);
        setTriedAmount(0);
        this.setPreferredSize(new Dimension(384, 320));
        this.setBackground(new Color(160, 255, 160));
    }

    public static void addLine(String text) {
        textOutput.append((textOutput.getText().isEmpty() ? "" : "\n") + text);
        textOutput.setCaretPosition(Math.max(0, textOutput.getText().lastIndexOf("\n") + 1));
    }

    public static void setFoundAmount(long amount) {
        labelFoundAmount.setText(Util.formatLong(amount));
    }

    public static void setTriedAmount(long amount) {
        labelTriedAmount.setText(Util.formatLong(amount));
    }

    public static void reset() {
        labelFoundAmount.setText("");
        labelTriedAmount.setText("");
        textOutput.setText("");
    }

}
