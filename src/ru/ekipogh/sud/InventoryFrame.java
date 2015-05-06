package ru.ekipogh.sud;

import javax.swing.*;

/**
 * Created by dedov_d on 06.05.2015.
 */
public class InventoryFrame extends JFrame {
    private final DefaultListModel<Item> itemsListModel;
    private JList<Item> itemsList;
    private JPanel rootPanel;

    public InventoryFrame() {
        super("Инвентарь");
        setContentPane(rootPanel);

        pack();
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
/*        setSize(800, 600);*/
        setVisible(true);

        itemsListModel = new DefaultListModel<Item>();
        itemsList.setModel(itemsListModel);

        PlayerFrame.getPlayer().getInventory().forEach(itemsListModel::addElement);
    }
}
