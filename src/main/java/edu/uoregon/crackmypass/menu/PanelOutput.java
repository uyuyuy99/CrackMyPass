package edu.uoregon.crackmypass.menu;

import edu.uoregon.crackmypass.Util;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class PanelOutput extends JPanel {

    private static GridBagLayout layout;
    private static GridBagConstraints c;

    private static JLabel labelFoundText;
    private static JLabel labelFoundAmount;
    private static JLabel labelTriedText;
    private static JLabel labelTriedAmount;
    private static JScrollPane scrollPane;
    private static JTextPane textOutput;

    private static Style stylePassword;
    private static Style styleHash;

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
        labelFont = new Font("Monospaced", Font.BOLD, 16);
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
        textOutput = new JTextPane();
        textOutput.setEditable(false);
        textOutput.setForeground(Color.GREEN);
        textOutput.setBackground(Color.BLACK);
        textOutput.setEditorKit(new NoWrapEditorKit());
        scrollPane = new JScrollPane(textOutput);
        scrollPane.setPreferredSize(new Dimension(384, 320));
        new SmartScroller(scrollPane);
        add(scrollPane, c);

        StyledDocument doc = textOutput.getStyledDocument();

        stylePassword = doc.addStyle("password", null);
        StyleConstants.setForeground(stylePassword, Color.GREEN);
        StyleConstants.setFontSize(stylePassword, 16);
        StyleConstants.setFontFamily(stylePassword, "Monospaced");

        styleHash = doc.addStyle("hash", null);
        StyleConstants.setForeground(styleHash, Color.GRAY);
        StyleConstants.setFontSize(styleHash, 12);
        StyleConstants.setFontFamily(styleHash, "Monospaced");

        setFoundAmount(0);
        setTriedAmount(0);
        this.setPreferredSize(new Dimension(384, 320));
        this.setBackground(new Color(160, 255, 160));
    }

    public static synchronized void addLine(String password, String hash) {
        SwingUtilities.invokeLater(() -> {
            StyledDocument doc = textOutput.getStyledDocument();
            try {
                doc.insertString(doc.getLength(), password, stylePassword);
                doc.insertString(doc.getLength(), " (" + hash + ")\n", styleHash);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }

    public static synchronized void setFoundAmount(long amount) {
        labelFoundAmount.setText(Util.formatLong(amount));
    }

    public static synchronized void setTriedAmount(long amount) {
        labelTriedAmount.setText(Util.formatLong(amount));
    }

    public static void reset() {
        labelFoundAmount.setText("");
        labelTriedAmount.setText("");
        textOutput.setText("");
    }

    static class NoWrapEditorKit extends StyledEditorKit {
        @Override
        public ViewFactory getViewFactory() {
            return new NoWrapViewFactory();
        }
    }

    static class NoWrapViewFactory implements ViewFactory {
        @Override
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new LabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphViewNoWrap(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new BoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }
            return new LabelView(elem);
        }
    }

    static class ParagraphViewNoWrap extends ParagraphView {
        public ParagraphViewNoWrap(Element elem) {
            super(elem);
        }

        @Override
        public void layout(int width, int height) {
            super.layout(Integer.MAX_VALUE, height);
        }

        @Override
        public float getMinimumSpan(int axis) {
            return super.getPreferredSpan(axis);
        }
    }

}
