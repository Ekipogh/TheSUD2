package ru.ekipogh.sud;

import javax.swing.*;

/**
 * Created by ekipo on 07.02.2016.
 */
public class ContainerFrame extends JFrame {
    private final DefaultListModel<SudPair> containerItemListModel;
    private JPanel rootPanel;
    private JButton addItemToContainerButton;
    private JButton deleteItemFromContainerButton;
    private JList<Item> itemList;
    private JList<SudPair> containerItemList;
    private JButton addSomeItemsToContainerButton;
    private JButton deleteSomeItemsFromContainerButton;
    private Item container;

    public ContainerFrame(EditorFrame editorFrame, Item item) {
        super("Контайнер");
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        this.container = item;

        itemList.setModel(editorFrame.itemsListModel);

        containerItemListModel = new DefaultListModel<>();
        containerItemList.setModel(containerItemListModel);

        container.getInventory().forEach((pair) -> containerItemListModel.addElement(((SudPair) pair)));
    }
}
