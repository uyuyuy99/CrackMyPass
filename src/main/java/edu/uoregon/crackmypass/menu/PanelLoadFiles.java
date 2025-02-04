package edu.uoregon.crackmypass.menu;

import edu.uoregon.crackmypass.Cracker;
import edu.uoregon.crackmypass.Util;

import javax.swing.*;
import java.awt.*;

public class PanelLoadFiles extends JPanel {

    private GridBagLayout layout;
    private GridBagConstraints c;

    private JButton btnLoadHashes;
    private JLabel labelLoadedHashes;
    private JButton btnLoadWords;
    private JLabel labelLoadedWords;

    public PanelLoadFiles(final MainMenu mainMenu) {
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);

        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(12, 8, 0, 0);
        c.gridx = 0; c.gridy = 1;
        btnLoadHashes = new JButton("Load Hashes");
        btnLoadHashes.setToolTipText("Load password hashes from a file.");
        add(btnLoadHashes, c);
        btnLoadHashes.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(mainMenu);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (Cracker.loadHashes(fileChooser.getSelectedFile())) {
                    JOptionPane.showMessageDialog(this,
                            "Successfully loaded " + Util.formatLong(Cracker.getHashes().size()) + " hashes!",
                            "Success!",
                            JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error loading hashes.\nPlease make sure your file is formatted correctly.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                setLoadedHashesLabel();
            }
        });

        c.insets = new Insets(12, 8, 0, 8);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1; c.gridy = 1;
        labelLoadedHashes = new JLabel();
        add(labelLoadedHashes, c);

        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(12, 8, 12, 0);
        c.gridx = 0; c.gridy = 2;
        btnLoadWords = new JButton("Load Dictionary");
        btnLoadWords.setToolTipText("Load a custom dictionary file.");
        add(btnLoadWords, c);
        btnLoadWords.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(mainMenu);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (Cracker.loadWords(fileChooser.getSelectedFile())) {
                    JOptionPane.showMessageDialog(this,
                            "Successfully loaded " + Util.formatLong(Cracker.getWords().size()) + " words!",
                            "Success!",
                            JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error loading word list.\nPlease make sure your file is formatted correctly.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                setLoadedWordsLabel();
            }
        });

        c.insets = new Insets(12, 8, 12, 8);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1; c.gridy = 2;
        labelLoadedWords = new JLabel();
        add(labelLoadedWords, c);

        setLoadedHashesLabel();
        setLoadedWordsLabel();
//        this.setBackground(Color.RED);
    }

    private void setLoadedHashesLabel() {
        labelLoadedHashes.setText(Util.formatLong(Cracker.getHashes().size()) + " hashes loaded");
    }

    private void setLoadedWordsLabel() {
        labelLoadedWords.setText(Util.formatLong(Cracker.getWords().size()) + " words loaded");
    }

}
