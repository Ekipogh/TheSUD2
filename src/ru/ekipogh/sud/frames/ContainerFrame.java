package ru.ekipogh.sud.frames;

import ru.ekipogh.sud.SudPair;
import ru.ekipogh.sud.objects.Item;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Created by ekipogh on 07.02.2016.
 */
class ContainerFrame extends JFrame {
    private final DefaultListModel<SudPair<Item, Integer>> containerItemListModel;
    private final DefaultListModel<Item> itemsListModel;
    private JPanel rootPanel;
    private JButton addItemToContainerButton;
    private JButton deleteItemFromContainerButton;
    private JList<Item> itemList;
    private JList<SudPair<Item, Integer>> containerItemList;
    private JButton addSomeItemsToContainerButton;
    private JButton deleteSomeItemsFromContainerButton;
    private JLabel containerNameLabel;
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

        itemsListModel = editorFrame.itemsListModel;
        itemList.setModel(itemsListModel);

        containerItemListModel = new DefaultListModel<>();
        containerItemList.setModel(containerItemListModel);

        container.getInventory().forEach((pair) -> containerItemListModel.addElement(((SudPair<Item, Integer>) pair)));
        containerNameLabel.setText(item.getName() + " (#" + item.getId() + ")");

        addItemToContainerButton.addActionListener(e -> addItemToContainer());
        deleteItemFromContainerButton.addActionListener(e -> deleteItemFromContainer());
        addSomeItemsToContainerButton.addActionListener(e -> addSomeItemsToContainer());
        deleteSomeItemsFromContainerButton.addActionListener(e -> deleteSomeItemsFromContainer());

        itemList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromContainerButton.setEnabled(false);
                    deleteSomeItemsFromContainerButton.setEnabled(false);
                    addItemToContainerButton.setEnabled(true);
                    addSomeItemsToContainerButton.setEnabled(true);
                }
            }
        });

        containerItemList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromContainerButton.setEnabled(true);
                    deleteSomeItemsFromContainerButton.setEnabled(true);
                    addItemToContainerButton.setEnabled(false);
                    addSomeItemsToContainerButton.setEnabled(false);
                }
            }
        });
    }

    private void deleteSomeItemsFromContainer() {
        int index = containerItemList.getSelectedIndex();
        if (index >= 0) {
            int count = containerItemListModel.get(index).getValue();
            SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, count, 1);
            JSpinner spinner = new JSpinner(sModel);
            int option = JOptionPane.showOptionDialog(null, spinner, "Введите количество", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.OK_OPTION) {
                int amount = (int) spinner.getModel().getValue();
                deleteItemFromContainer(amount);
            }
        }
    }

    private void addSomeItemsToContainer() {
        SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(sModel);
        int option = JOptionPane.showOptionDialog(null, spinner, "Введите количество", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.OK_OPTION) {
            int amount = (int) spinner.getModel().getValue();
            addItemToContainer(amount);
        }
    }

    private void deleteItemFromContainer() {
        deleteItemFromContainer(1);
    }

    private void deleteItemFromContainer(int count) {
        int indexI = containerItemList.getSelectedIndex();
        if (indexI >= 0) {
            Item item = containerItemListModel.getElementAt(indexI).getKey();
            int newAmount = containerItemListModel.get(indexI).getValue() - count;
            if (newAmount != 0) {
                containerItemListModel.get(indexI).setValue(newAmount);
                containerItemList.updateUI();
            } else {
                containerItemListModel.removeElementAt(indexI);
            }
            container.removeItem(item, count);
        }
    }

    private void addItemToContainer() {
        addItemToContainer(1);
    }

    private void addItemToContainer(int count) {
        int indexI = itemList.getSelectedIndex();
        if (indexI >= 0) {
            Item item = itemsListModel.getElementAt(indexI);
            if (container.getInventory().contains(item)) {
                int newAmount = container.getInventory().getAmount(item) + count;
                int index = containerItemListModel.indexOf(container.getInventory().getPair(item));
                containerItemListModel.get(index).setValue(newAmount);
            } else {
                containerItemListModel.addElement(new SudPair<>(item, count));
            }
            container.addItem(item, count);
        }
    }
}
