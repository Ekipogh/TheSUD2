package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by dedov_d on 06.05.2015.
 */
public class InventoryFrame extends JFrame {
    private final DefaultListModel<Item> itemsListModel;
    private JList<Item> itemsList;
    private JPanel rootPanel;
    private JTable equipmentTable;
    private DefaultTableModel equipmentTableModel;
    private Player player = PlayerFrame.getPlayer();

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
        player.getInventory().forEach(itemsListModel::addElement);

        for (String s : Equipment.getSlotNames()) {
            Item item = player.getEquipedItem(s);
            equipmentTableModel.addRow(new Object[]{new ImageIcon(Equipment.getImage(s)), item != null ? item : "Пусто"});
        }

        updateRowHeights();
        itemsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    JList list = (JList) e.getSource();
                    int row = list.locationToIndex(e.getPoint());
                    list.setSelectedIndex(row);
                    if (!itemsList.isSelectionEmpty())
                        showPopup(e);
                }
            }
        });
    }

    private void showPopup(MouseEvent e) {
        JMenuItem menuItem;
        JPopupMenu popupMenu = new JPopupMenu();
        int index = itemsList.getSelectedIndex();
        final Item selected = itemsListModel.getElementAt(index);
        if (selected != null) {
            ItemTypes type = selected.getType();
            //можно положить в инвентарь съедобное и экипируемое
            if (type == ItemTypes.EQUIPPABLE) {
                menuItem = new JMenuItem("Экипировать");
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        equipItem(selected);
                    }
                });
                popupMenu.add(menuItem);
            }
            menuItem = new JMenuItem("Бросить");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dropItem(selected);
                }
            });
            popupMenu.add(menuItem);
            //TODO: consume, drop
            popupMenu.show(itemsList, e.getX(), e.getY());
        }
    }

    private void dropItem(Item item) {
        player.getLocation().addItem(item);
        player.getInventory().remove(item);
        itemsListModel.removeElement(item);
    }

    private void equipItem(Item item) {
        player.equip(item);
        itemsListModel.removeElement(item);
        player.getInventory().remove(item);
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
