package edu.uoregon.crackmypass.menu.settings;

import edu.uoregon.crackmypass.Cracker;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PanelSettingsReplace extends JPanel {

    private GridBagLayout layout;
    private GridBagConstraints c;

    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private int rowCount;
    private String[] columns = new String[] { "Target", "Replacement" };
    private JButton btnAdd;
    private JButton btnRemove;

    public PanelSettingsReplace() {
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);

        final String html = "<html><body style='width: %1spx'>%1s";
        String text = "<center>Replace strings of text in the word. " +
                "For example, users often replace <b>a</b> with <b>@</b> in their passwords.</center>";
        JLabel label = new JLabel(String.format(html, 200, text));
        c.gridx = 0; c.gridy = 0;
        c.gridwidth = 2;
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
        tableModel = new DefaultTableModel(data, columns);
        rowCount = tableModel.getRowCount();

        c.gridx = 0; c.gridy = 1;
        c.weightx = 1; c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(231, 241, 255) : Color.WHITE);
                } else {
                    c.setBackground(new Color(150, 175, 220));
                }

                return c;
            }
        };
        DefaultTableCellRenderer cellRenderer = getTableCellRenderer();
        DefaultTableCellRenderer headerRenderer = getHeaderRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        table.setRowHeight(32);
        table.setFont(table.getFont().deriveFont(Font.PLAIN, 14f));
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Remove rows when user hits DEL key
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int[] selected = table.getSelectedRows();
                    for (int i = selected.length - 1; i >= 0; i--) {
                        tableModel.removeRow(selected[i]);
                    }
                }
            }
        });
        scrollPane = new JScrollPane(table);
        c.insets = new Insets(0, 0, 0, 0);
        add(scrollPane, c);

        c.gridx = 0; c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 1; c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        btnAdd = new JButton("Add Replacement");
        btnAdd.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/add.png")));
        btnAdd.setIconTextGap(8);
        btnAdd.setMargin(new Insets(6, 6, 6, 6));
        add(btnAdd, c);
        btnAdd.addActionListener((e) -> {
            tableModel.addRow(new Object[] { "", "" });
            table.editCellAt(table.getRowCount() - 1, 0);
            Component editor = table.getEditorComponent();
            if (editor != null) editor.requestFocus();
        });

        table.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (tableModel.getRowCount() > rowCount) { // if row was added
                    table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0, true));
                }
                rowCount = tableModel.getRowCount();
            }
        });

        setPreferredSize(new Dimension(256, 420));
    }

    private static DefaultTableCellRenderer getTableCellRenderer() {
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (comp instanceof JLabel) {
                    ((JLabel) comp).setBorder(new EmptyBorder(0, 4, 0, 4));
                }

                return comp;
            }
        };
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        return cellRenderer;
    }

    private static DefaultTableCellRenderer getHeaderRenderer() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        renderer.setBackground(Color.LIGHT_GRAY);
        return renderer;
    }

    public void save() {
        List<Pair<String, String>> replacements = new ArrayList<>();
        for (int row = 0; row < table.getRowCount(); row++) {
            replacements.add(Pair.of((String) table.getValueAt(row, 0), (String) table.getValueAt(row, 1)));
        }
        Cracker.setReplacements(replacements);
//        Cracker.setReplacements(table.getModel().get);
    }

}
