package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dedov_d on 06.05.2015.
 */
class InventoryFrame extends JFrame {
    private final DefaultTreeModel itemsTreeModel;
    private final PlayerFrame playerFrame;
    private JTree itemsTree;
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

        itemsTreeModel = new DefaultTreeModel(new DefaultMutableTreeNode("Инвентарь"));
        itemsTree.setModel(itemsTreeModel);
        itemsTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath treePath = itemsTree.getSelectionPath();
                if (treePath != null) {
                    DefaultMutableTreeNode selected = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                    if (selected != null && selected instanceof SudTreeNode && e.getClickCount() == 2 && selected.isLeaf())
                        ((SudTreeNode) selected).invoke();
                }
            }
        });

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
        updateItemsTree();

        updateEquipmentTable();

        equipmentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                JTable table = (JTable) e.getSource();
                int r = table.rowAtPoint(e.getPoint());
                int c = table.columnAtPoint(e.getPoint());
                if (r >= 0 && r < table.getRowCount() && c >= 0 && c < table.getColumnCount()) {
                    table.setRowSelectionInterval(r, r);
                    table.setColumnSelectionInterval(c, c);
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

    private void updateItemsTree() {
        ((DefaultMutableTreeNode) itemsTreeModel.getRoot()).removeAllChildren();
        itemsTreeModel.reload();
        fillItemsTree((DefaultMutableTreeNode) itemsTreeModel.getRoot(), player.getInventory());
        expandAllNodes(itemsTree);
    }

    private static void expandAllNodes(JTree tree) {
        for (int i = 0; i < tree.getRowCount(); ++i) {
            tree.expandRow(i);
        }
    }

    private void fillItemsTree(DefaultMutableTreeNode node, Inventory inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i); //предмет для добавления
            String itemName = item.getName(); //название предмета
            int amount = inventory.amount(i); //количество предметов
            SudTreeNode itemNode = new SudTreeNode(item, null); //нода предмета
            //текст ноды
            itemNode.setText(itemName); //имя предмета
            itemNode.setCount(amount); //колчисество предмета
            itemsTreeModel.insertNodeInto(itemNode, node, node.getChildCount()); //вставляем ноду предмета в выбранную выше ноду

            //далее скрипты и действия

            //скрипты
            item.getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> itemsTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), item)), itemNode, itemNode.getChildCount()));
            for (ItemCategory category : item.getCategories()) {
                category.getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> itemsTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), item)), itemNode, itemNode.getChildCount()));
            }
            //действия
            switch (item.getType()) {
                case EQUIPPABLE:
                    itemsTreeModel.insertNodeInto(new SudTreeNode("Экипировать", l -> equipItem(item, inventory)), itemNode, itemNode.getChildCount());
                    if (amount > 1) { //если предметов больше одного
                        itemsTreeModel.insertNodeInto(new SudTreeNode("Бросить...", l -> dropSomeItems(item, inventory, amount)), itemNode, itemNode.getChildCount());
                    } else {
                        itemsTreeModel.insertNodeInto(new SudTreeNode("Бросить", l -> dropItem(item, inventory)), itemNode, itemNode.getChildCount());
                    }
                    break;
                case STORABLE:
                    if (amount > 1) { //если предметов больше одного
                        itemsTreeModel.insertNodeInto(new SudTreeNode("Бросить...", l -> dropSomeItems(item, inventory, 13)), itemNode, itemNode.getChildCount());
                    } else {
                        itemsTreeModel.insertNodeInto(new SudTreeNode("Бросить", l -> dropItem(item, inventory)), itemNode, itemNode.getChildCount());
                    }
                    break;
                case CONSUMABLE:
                    itemsTreeModel.insertNodeInto(new SudTreeNode("Использовать", l -> useItem(item, inventory)), itemNode, itemNode.getChildCount());
                    if (amount > 1) { //если предметов больше одного
                        itemsTreeModel.insertNodeInto(new SudTreeNode("Бросить...", l -> dropSomeItems(item, inventory, 13)), itemNode, itemNode.getChildCount());
                    } else {
                        itemsTreeModel.insertNodeInto(new SudTreeNode("Бросить", l -> dropItem(item, inventory)), itemNode, itemNode.getChildCount());
                    }
                    break;
            }
            if (item.isContainer()) {
                if (!item.isLocked()) {
                    fillItemsTree(itemNode, item.getInventory());
                } else {
                    itemsTreeModel.insertNodeInto(new SudTreeNode("Отпереть", l -> unlockContainer(item)), itemNode, itemNode.getChildCount());
                }
            }
            //если влокации есть контейенры, для каждого добавить опцию положить в ...

            inventory.stream().filter(itemC -> itemC.getKey().isContainer() && !itemC.getKey().isLocked() && !itemC.getKey().equals(item) && !itemC.getKey().getInventory().contains(item)).forEach(itemContained -> itemsTreeModel.insertNodeInto(new SudTreeNode("Положить в " + itemContained.getKey().getName(), l -> stashItem(itemContained.getKey(), item)), itemNode, itemNode.getChildCount()));

        }
    }

    private void dropSomeItems(Item item, Inventory inventory, int count) {
        SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, count, 1);
        JSpinner spinner = new JSpinner(sModel);
        int option = JOptionPane.showOptionDialog(null, spinner, "Enter valid number", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.OK_OPTION) {
            int amount = (int) spinner.getModel().getValue();
            for (int i = 0; i < amount; i++) {
                dropItem(item, inventory);
            }
        }
    }

    private void stashItem(Item item, Item container) {
        Script.run(item.getScript("_onStash").getText(), new Object[]{item, container});
        Script.run(playerFrame.getCommonScripts().get("_onPlayerStashesItem").getText(), new Object[]{item, container});
        container.addItem(item);
        player.getInventory().remove(item);
        updateItemsTree();
    }

    private void unlockContainer(Item container) {
        Script.run(container.getScript("_onUnlock").getText(), container);
        Script.run(playerFrame.getCommonScripts().get("_onPlayerUnlocksItem").getText(), container);
        updateItemsTree();
    }

    private JPopupMenu showEquipmentPopup() {
        JMenuItem menuItem;
        JPopupMenu popupMenu = new JPopupMenu();
        int row = equipmentTable.getSelectedRow();
        int col = equipmentTable.getSelectedColumn();
        Object selected = equipmentTableModel.getValueAt(row, col);
        if (selected instanceof Item) {
            menuItem = new JMenuItem("Снять");
            menuItem.addActionListener(e -> unequipItem(((Item) selected), row, col));
            popupMenu.add(menuItem);
        }
        return popupMenu;
    }

    private void unequipItem(Item item, int row, int col) {
        equipmentTableModel.setValueAt("Пусто", row, col);
        player.unequip(item);
        Script.run(item.getScript(ONUNEQUIP).getText(), item);
        for (ItemCategory category : item.getCategories())
            Script.run(category.getScript(ONUNEQUIP).getText(), item);
        Script.run(playerFrame.getCommonScripts().get("_onPlayerUnequipsItem").getText(), item);
        updateItemsTree();
    }

    private void updateEquipmentTable() {  //Заполнение таблицы экипировки
        equipmentTableModel.setRowCount(0);
        for (String s : Equipment.getSlotNames()) {
            Item item = player.getEquipedItem(s);
            equipmentTableModel.addRow(new Object[]{new ImageIcon(Equipment.getImage(s)), item != null ? item : "Пусто"});
        }

        Utils.updateRowHeights(equipmentTable); //обновление высоты ячеек
    }

    private void useItem(Item item, Inventory inventory) {
        Script.run(item.getScript(ONUSE).getText(), item);
        for (ItemCategory category : item.getCategories())
            Script.run(category.getScript(ONUSE).getText(), item);
        Script.run(playerFrame.getCommonScripts().get("_onPlayerUsesItem").getText(), item);
        //Удалем предмет из инвентаря
        inventory.remove(item);
        updateItemsTree();
    }

    private void dropItem(Item item, Inventory inventory) {
        player.getLocation().addItem(item);
        inventory.remove(item);
        Script.run(item.getScript(ONDROP).getText(), item);
        for (ItemCategory category : item.getCategories())
            Script.run(category.getScript(ONDROP).getText(), item);
        Script.run(playerFrame.getCommonScripts().get("_onPlayerDropsItem").getText(), item);
        updateItemsTree();
        playerFrame.updateItems();
    }

    private void equipItem(Item item, Inventory inventory) {
        if (player.equip(item)) {
            inventory.remove(item); //убираем предмет из инвенторя откуда он был экирирован
            Script.run(item.getScript(ONEQUIP).getText(), item); //выполняем скрипты связаные с экипировкой предмета
            for (ItemCategory category : item.getCategories())
                Script.run(category.getScript(ONEQUIP).getText(), item);
            Script.run(playerFrame.getCommonScripts().get("_onPlayerEquipsItem").getText(), item);
            updateItemsTree();
            updateEquipmentTable();
        }
    }
}
