package edu.uoregon.crackmypass.menu;

import edu.uoregon.crackmypass.menu.settings.PanelSettingAppend;
import edu.uoregon.crackmypass.menu.settings.PanelSettingCapitalize;
import edu.uoregon.crackmypass.menu.settings.PanelSettingPrepend;

import javax.swing.*;
import java.awt.*;

public class PanelSettings extends JPanel {

    private GridBagLayout layout;
    private GridBagConstraints c;

    private JButton btnAppend;
    private JButton btnPrepend;
    private JButton btnCapitalize;
    private JButton btnReplace;

    public PanelSettings(final MainMenu mainMenu) {
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);

        Insets padding = new Insets(8, 8, 8, 8);
        Dimension size = new Dimension(128, 48);
        Font font;

        c.gridx = 0; c.gridy = 0;
        c.insets = new Insets(12, 8, 0, 0);
        btnAppend = new JButton("Append");
//        btnAppend.setMargin(padding);
        btnAppend.setPreferredSize(size);
        btnAppend.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/chevron.png")));
        btnAppend.setIconTextGap(10);
        font = btnAppend.getFont().deriveFont(14f);
        btnAppend.setFont(font);
        add(btnAppend, c);

        c.gridx = 1; c.gridy = 0;
        btnPrepend = new JButton("Prepend");
        btnPrepend.setPreferredSize(size);
        btnPrepend.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/chevron_left.png")));
        btnPrepend.setIconTextGap(10);
        btnPrepend.setFont(font);
        add(btnPrepend, c);

        c.gridx = 0; c.gridy = 1;
        btnCapitalize = new JButton("Capitalize");
        btnCapitalize.setPreferredSize(size);
        btnCapitalize.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/capital.png")));
        btnCapitalize.setIconTextGap(10);
        btnCapitalize.setFont(font);
        add(btnCapitalize, c);

        c.gridx = 1; c.gridy = 1;
        btnReplace = new JButton("Replace");
        btnReplace.setPreferredSize(size);
        btnReplace.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/replace.png")));
        btnReplace.setIconTextGap(10);
        btnReplace.setFont(font);
        add(btnReplace, c);

        btnAppend.addActionListener((e) -> {
            PanelSettingAppend panelAppend = new PanelSettingAppend();
            int result = JOptionPane.showConfirmDialog(mainMenu, panelAppend,
                    "Append Text/Numbers to Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                panelAppend.save();
            }
        });

        btnPrepend.addActionListener((e) -> {
            PanelSettingPrepend panelPrepend = new PanelSettingPrepend();
            int result = JOptionPane.showConfirmDialog(mainMenu, panelPrepend,
                    "Prepend Text/Numbers to Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                panelPrepend.save();
            }
        });

        btnCapitalize.addActionListener((e) -> {
            PanelSettingCapitalize panelCapitalize = new PanelSettingCapitalize();
            int result = JOptionPane.showConfirmDialog(mainMenu, panelCapitalize,
                    "Capitalize Words", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                panelCapitalize.save();
            }
        });
    }

}
