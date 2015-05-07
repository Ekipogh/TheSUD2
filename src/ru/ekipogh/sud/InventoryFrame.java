package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by dedov_d on 06.05.2015.
 */
public class InventoryFrame extends JFrame {
    private final DefaultListModel<Item> itemsListModel;
    private JList<Item> itemsList;
    private JPanel rootPanel;
    private JTable equipmentTable;
    private DefaultTableModel equipmentTableModel;

    public InventoryFrame() {
        super("Инвентарь");
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        itemsListModel = new DefaultListModel<Item>();
        itemsList.setModel(itemsListModel);

        equipmentTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        equipmentTable.setModel(equipmentTableModel);
        equipmentTableModel.addColumn("Слот");
        equipmentTableModel.addColumn("Предмет");
        equipmentTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        PlayerFrame.getPlayer().getInventory().forEach(itemsListModel::addElement);

        for (String s : Equipment.getSlotNames()) {
            Item item = PlayerFrame.getPlayer().getEquipedItem(s);
            equipmentTableModel.addRow(new Object[]{new ImageIcon(Equipment.getImage(s)), item != null ? item : "Пусто"});
        }

        updateRowHeights();
    }

    private void updateRowHeights() {
        try {
            for (int row = 0; row < equipmentTable.getRowCount(); row++) {
                int rowHeight = equipmentTable.getRowHeight();

                for (int column = 0; column < equipmentTable.getColumnCount(); column++) {
                    Component comp = equipmentTable.prepareRenderer(equipmentTable.getCellRenderer(row, column), row, column);
                    rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                }

                equipmentTable.setRowHeight(row, rowHeight);
            }
        } catch (ClassCastException e) {
        }
    }

    class ImageRenderer extends DefaultTableCellRenderer {
        JLabel lbl = new JLabel();

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            lbl.setIcon(((ImageIcon) value));
            return lbl;
        }
    }
}
