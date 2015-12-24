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
        fillItemsTree(player.getInventory(), false);

        expandAllNodes(itemsTree);
    }

    private static void expandAllNodes(JTree tree) {
        for (int i = 0; i < tree.getRowCount(); ++i) {
            tree.expandRow(i);
        }
    }

    private void fillItemsTree(Inventory inventory, boolean container) {
        DefaultMutableTreeNode node;
        if (!container) {
            node = (DefaultMutableTreeNode) itemsTreeModel.getRoot();
        } else {
            DefaultMutableTreeNode root = ((DefaultMutableTreeNode) itemsTreeModel.getRoot());
            node = (DefaultMutableTreeNode) itemsTreeModel.getChild(root, itemsTreeModel.getChildCount(root) - 1);
        }
        //for (Item item : inventory) {
        inventory.stream().forEach(item -> {
            DefaultMutableTreeNode itemNode = new DefaultMutableTreeNode(item);
            itemsTreeModel.insertNodeInto(itemNode, node, node.getChildCount());
            item.getKey().getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> itemsTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), item.getKey())), itemNode, itemNode.getChildCount()));
            for (ItemCategory category : item.getKey().getCategories())
                category.getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> itemsTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), item.getKey())), itemNode, itemNode.getChildCount()));
            if (item.getKey().getType() == ItemTypes.EQUIPPABLE)
                itemsTreeModel.insertNodeInto(new SudTreeNode("Экипировать", l -> equipItem(item.getKey(), inventory)), itemNode, itemNode.getChildCount());
            if (item.getKey().getType() == ItemTypes.EQUIPPABLE || item.getKey().getType() == ItemTypes.STORABLE)
                itemsTreeModel.insertNodeInto(new SudTreeNode("Бросить", l -> dropItem(item.getKey(), inventory)), itemNode, itemNode.getChildCount());
            if (item.getKey().getType() == ItemTypes.CONSUMABLE)
                itemsTreeModel.insertNodeInto(new SudTreeNode("Использовать", l -> useItem(item.getKey(), inventory)), itemNode, itemNode.getChildCount());
            if (item.getKey().isContainer()) {
                if (!item.getKey().isLocked()) {
                    fillItemsTree(item.getKey().getInventory(), true);
                } else {
                    itemsTreeModel.insertNodeInto(new SudTreeNode("Отпереть", l -> unlockContainer(item.getKey())), itemNode, itemNode.getChildCount());
                }
            }
            //если влокации есть контейенры, для каждого добавить опцию положить в ...
            if (!container) {
                inventory.stream().filter(i -> i.getKey().isContainer() && !i.getKey().isLocked() && !i.getKey().equals(item.getKey()) && !i.getKey().getInventory().contains(item.getKey())).forEach(i -> itemsTreeModel.insertNodeInto(new SudTreeNode("Положить в " + i.getKey().getName(), l -> stashItem(i.getKey(), item.getKey())), itemNode, itemNode.getChildCount()));
            }
        });
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
        player.equip(item);
        inventory.remove(item);
        Script.run(item.getScript(ONEQUIP).getText(), item);
        for (ItemCategory category : item.getCategories())
            Script.run(category.getScript(ONEQUIP).getText(), item);
        Script.run(playerFrame.getCommonScripts().get("_onPlayerEquipsItem").getText(), item);
        updateItemsTree();
        updateEquipmentTable();
    }
}
