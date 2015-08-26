package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dedov_d on 06.05.2015.
 */
class InventoryFrame extends JFrame {
    private final DefaultListModel<Item> itemsListModel;
    private final PlayerFrame playerFrame;
    private JList<Item> itemsList;
    private JPanel rootPanel;
    private JTable equipmentTable;
    private DefaultTableModel equipmentTableModel;
    private GameCharacter player = PlayerFrame.getPlayer();
    private final String ONUSE = "_onUse";
    private final String ONDROP = "_onDrop";
    private final String ONEQUIP = "_onEquip";
    private final String ONUNEQUIP = "_onUnequip";

    public InventoryFrame(PlayerFrame playerFrame) {
        super("Инвентарь");
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                playerFrame.setEnabled(true);
            }
        });

        this.playerFrame = playerFrame;
        playerFrame.setEnabled(false);

        itemsListModel = new DefaultListModel<>();
        itemsList.setModel(itemsListModel);

        //Картинки слотов не редактируемы
        equipmentTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        equipmentTable.setModel(equipmentTableModel);
        equipmentTableModel.addColumn("Слот");
        equipmentTableModel.addColumn("Предмет");
        equipmentTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer()); //для отображения картинок слотов
        player.getInventory().forEach(itemsListModel::addElement); //заполняем список инвенторя

        updateEquipmentTable();

        itemsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    JList list = (JList) e.getSource();
                    int row = list.locationToIndex(e.getPoint());
                    list.setSelectedIndex(row);
                    if (!itemsList.isSelectionEmpty())
                        showInventoryPopup(e);
                }
            }
        });
        equipmentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                JTable table = (JTable) e.getSource();
                int r = table.rowAtPoint(e.getPoint());
                if (r >= 0 && r < table.getRowCount()) {
                    table.setRowSelectionInterval(r, r);
                } else {
                    table.clearSelection();
                }

                int rowindex = table.getSelectedRow();
                if (rowindex < 0)
                    return;
                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu popup = showEquipmentPopup();
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private JPopupMenu showEquipmentPopup() {
        JMenuItem menuItem;
        JPopupMenu popupMenu = new JPopupMenu();
        int row = equipmentTable.getSelectedRow();
        int col = equipmentTable.getSelectedColumn();
        Object selected = equipmentTableModel.getValueAt(row, col);
        if (selected instanceof Item) {
            menuItem = new JMenuItem("Снять");
            menuItem.addActionListener(e -> {
                unequipItem(((Item) selected), row, col);
            });
            popupMenu.add(menuItem);
        }
        return popupMenu;
    }

    private void unequipItem(Item item, int row, int col) {
        equipmentTableModel.setValueAt("Пусто", row, col);
        unequipItem(item);
    }

    private void unequipItem(Item item) {
        player.unequip(item);
        itemsListModel.addElement(item);
        Script.run(item.getScript(ONUNEQUIP), item);
        if (item.getCategory() != null)
            Script.run(item.getCategory().getScript(ONUNEQUIP), item);
    }

    private void updateEquipmentTable() {  //Заполнение таблицы экипировки
        equipmentTableModel.setRowCount(0);
        for (String s : Equipment.getSlotNames()) {
            Item item = player.getEquipedItem(s);
            equipmentTableModel.addRow(new Object[]{new ImageIcon(Equipment.getImage(s)), item != null ? item : "Пусто"});
        }

        Utils.updateRowHeights(equipmentTable); //обновление высоты ячеек
    }


    private void showInventoryPopup(MouseEvent e) {  //меню инвенторя
        JMenuItem menuItem;
        JPopupMenu popupMenu = new JPopupMenu();
        int index = itemsList.getSelectedIndex();
        final Item selected = itemsListModel.getElementAt(index);
        if (selected != null) {
            ItemTypes type = selected.getType();
            //можно положить в инвентарь съедобное и экипируемое
            if (type == ItemTypes.EQUIPPABLE) {
                menuItem = new JMenuItem("Экипировать");
                menuItem.addActionListener(e1 -> equipItem(selected));
                popupMenu.add(menuItem);
            }
            if (type == ItemTypes.CONSUMABLE) {
                menuItem = new JMenuItem("Использовать");
                menuItem.addActionListener(e1 -> useItem(selected));
                popupMenu.add(menuItem);
            }
            menuItem = new JMenuItem("Бросить");
            menuItem.addActionListener(e1 -> dropItem(selected));
            popupMenu.add(menuItem);
            popupMenu.show(itemsList, e.getX(), e.getY());
        }
    }

    private void useItem(Item item) {
        Script.run(item.getScript(ONUSE), item);
        if (item.getCategory() != null)
            Script.run(item.getCategory().getScript(ONUSE), item);
        //Удалем предмет из инвенторя
        player.getInventory().remove(item);
        itemsListModel.removeElement(item);
    }

    private void dropItem(Item item) {
        player.getLocation().addItem(item);
        player.getInventory().remove(item);
        itemsListModel.removeElement(item);
        playerFrame.updateItems();
        Script.run(item.getScript(ONDROP), item);
        if (item.getCategory() != null)
            Script.run(item.getCategory().getScript(ONDROP), item);
    }

    private void equipItem(Item item) {
        player.equip(item);
        itemsListModel.removeElement(item);
        player.getInventory().remove(item);
        Script.run(item.getScript(ONEQUIP), item);
        if (item.getCategory() != null)
            Script.run(item.getCategory().getScript(ONEQUIP), item);
        updateEquipmentTable();
    }
}
