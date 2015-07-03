package ru.ekipogh.sud;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dedov_d on 23.04.2015.
 */
public class EditorFrame extends JFrame {
    private final DefaultComboBoxModel<Location> northModel;
    private final DefaultComboBoxModel<Location> southModel;
    private final DefaultComboBoxModel<Location> eastModel;
    private final DefaultComboBoxModel<Location> westModel;
    private final JMenuItem saveGameMenu;
    private final JMenuItem openGameMenu;
    private final DefaultListModel<Item> itemsListModel;
    private final DefaultListModel<Item> locationItemsListModel;
    private final DefaultTableModel equipTableModel;
    private final DefaultListModel<GameCharacter> charactersListModel;
    private DefaultListModel<Location> locationsListModel;
    private JPanel rootPanel;
    private JList<Location> locationsList;
    private JTextField locName;
    private JTextField locID;
    private JTextArea locDescription;
    private JComboBox<Location> locNorth;
    private JComboBox<Location> locSouth;
    private JComboBox<Location> locEast;
    private JComboBox<Location> locWest;
    private JButton addLocButton;
    private JButton deleteLocButton;
    private JButton saveLocButton;
    private JTextField playerName;
    private JRadioButton female;
    private JRadioButton male;
    private JComboBox<Location> playerLocation;
    private JRadioButton sexless;
    private JButton savePlayer;
    private JTextField gameName;
    private JTextArea gameStartMessage;
    private JList<Item> itemsList;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JTextField itemName;
    private JTextArea itemDescription;
    private JComboBox<ItemTypes> itemType;
    private JButton saveItemButton;
    private JList<Item> locationTabItemsList;
    private JList<Item> locationItemsList;
    private JButton addItemToLoc;
    private JButton deleteItemFromLoc;
    private JComboBox<String> slotCombo;
    private JTable equipTable;
    private JButton addSlotButton;
    private JButton deleteSlotButton;
    private JButton saveSlotsButton;
    private JList<String> scriptsList;
    private RSyntaxTextArea scriptText;
    private DefaultComboBoxModel<Location> playerLocationModel;
    private DefaultComboBoxModel<String> slotNamesModel;
    private DefaultListModel<String> scriptListModel;
    private JButton addScriptLocButton;
    private JButton deleteScriptLocButton;
    private JButton saveScriptButton;
    private JTextField picturePathField;
    private JButton pictureDialogButton;
    private JTextField itemIdField;
    private JList<GameCharacter> charactersList;

    private GameCharacter player;

    public EditorFrame() {
        super("Редактор");
        player = new GameCharacter("Безымянный");

        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //TODO: создать метод закрытия окна

        locationsListModel = new DefaultListModel<>();
        locationsList.setModel(locationsListModel);

        charactersListModel = new DefaultListModel<>();
        charactersList.setModel(charactersListModel);

        scriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        scriptText.setCodeFoldingEnabled(true);

        scriptListModel = new DefaultListModel<>();
        scriptsList.setModel(scriptListModel);

        equipTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return !(column == 1); //вторая колонка с иконками не редактируемая
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);
                if (column == 0) {
                    equipTableModel.setValueAt(new ImageIcon(String.valueOf(aValue)), row, 1);
                    Utils.updateRowHeights(equipTable);
                }
            }
        };

        equipTable.setModel(equipTableModel);
        equipTableModel.addColumn("Путь к иконке");
        equipTableModel.addColumn("Иконка");
        equipTableModel.addColumn("Название");
        equipTable.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());

        Equipment.getSlotMap().entrySet().forEach((entry) -> {
            String slotName = entry.getKey();
            String icon = entry.getValue();
            equipTableModel.addRow(new Object[]{icon, new ImageIcon(icon), slotName});
        });
        Utils.updateRowHeights(equipTable);

        itemsListModel = new DefaultListModel<>();
        itemsList.setModel(itemsListModel);
        locationTabItemsList.setModel(itemsListModel);
        locationItemsListModel = new DefaultListModel<>();
        locationItemsList.setModel(locationItemsListModel);

        itemType.setModel(new DefaultComboBoxModel<>(ItemTypes.values()));

        northModel = new DefaultComboBoxModel<>();
        southModel = new DefaultComboBoxModel<>();
        eastModel = new DefaultComboBoxModel<>();
        westModel = new DefaultComboBoxModel<>();
        playerLocationModel = new DefaultComboBoxModel<>();

        slotNamesModel = new DefaultComboBoxModel<>();
        //default init
        Equipment.getSlotNames().forEach(slotNamesModel::addElement);
        //default init
        slotCombo.setModel(slotNamesModel);

        northModel.addElement(null);
        southModel.addElement(null);
        eastModel.addElement(null);
        westModel.addElement(null);
        playerLocationModel.addElement(null);

        locNorth.setModel(northModel);
        locSouth.setModel(southModel);
        locEast.setModel(eastModel);
        locWest.setModel(westModel);
        playerLocation.setModel(playerLocationModel);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Файл");
        JMenuItem newGameMenu = new JMenuItem("Новая");
        openGameMenu = new JMenuItem("Открыть");
        saveGameMenu = new JMenuItem("Сохранить");
        menuFile.add(newGameMenu);
        menuFile.add(openGameMenu);
        menuFile.add(saveGameMenu);
        menuBar.add(menuFile);

        setJMenuBar(menuBar);

        saveGameMenu.addActionListener(e -> saveGame());

        openGameMenu.addActionListener(e -> openGame());

        addLocButton.addActionListener(e -> addNewLocation());

        locationsList.addListSelectionListener(e -> {
            setLocationFormElementsEnabled();
            selectLocation();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.launcher.setVisible(true);
                super.windowClosing(e);
            }
        });

        saveLocButton.addActionListener(e -> saveSelectedLocation());
        deleteLocButton.addActionListener(e -> deleteSelectedLocation());
        savePlayer.addActionListener(e -> savePlayer());
        addItemButton.addActionListener(e -> addNewItem());
        itemsList.addListSelectionListener(e -> {
            setItemFormElementsEnabled();
            selectItem();
        });
        deleteItemButton.addActionListener(e -> deleteSelectedItem());
        addItemToLoc.addActionListener(e -> addItemToLocation());
        saveItemButton.addActionListener(e -> saveSelectedItem());

        deleteItemFromLoc.addActionListener(e -> deleteItemFromLocation());
        locationTabItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromLoc.setEnabled(false);
                    addItemToLoc.setEnabled(true);
                }
            }
        });
        locationItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromLoc.setEnabled(true);
                    addItemToLoc.setEnabled(false);
                }
            }
        });
        itemType.addActionListener(e -> showSlotField());
        addSlotButton.addActionListener(e -> addSlot());
        deleteSlotButton.addActionListener(e -> {
            Item found = null;
            int row = equipTable.getSelectedRow();
            for (int i = 0; i < itemsListModel.getSize(); i++) {
                Item item = itemsListModel.getElementAt(i);
                if (item.getType() == ItemTypes.EQUIPPABLE && item.getEquipmentSlot() == equipTableModel.getValueAt(row, 2))
                    found = item;
            }
            if (found == null) {
                if (row > -1) {
                    equipTableModel.removeRow(row);
                }
            } else
                JOptionPane.showMessageDialog(null, "Удаление невозможно существует предмет (" + found.getName() + ") с таким слотом экипировки");
        });
        saveSlotsButton.addActionListener(e -> saveSlotNames());
        scriptsList.addListSelectionListener(e -> selectLocationScript());
        saveScriptButton.addActionListener(e -> saveSelectedScript());
        deleteScriptLocButton.addActionListener(e -> deleteSelectedLocationScript());
        addScriptLocButton.addActionListener(e -> addScriptToLocation());
        pictureDialogButton.addActionListener(e -> showChooseDialog());
    }

    private void showChooseDialog() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        FileFilter ff = new FileNameExtensionFilter("Image file", "png", "jpg", "jpeg");
        fc.setFileFilter(ff);
        int response = fc.showOpenDialog(pictureDialogButton);
        if (response == JFileChooser.APPROVE_OPTION) {
            picturePathField.setText(fc.getSelectedFile().getPath());
        }
    }

    private void addScriptToLocation() {
        int index = locationsList.getSelectedIndex();
        Location location = locationsListModel.elementAt(index);
        String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
        location.addScript(scriptName, "");
        scriptListModel.addElement(scriptName);
    }

    private void deleteSelectedLocationScript() {
        int indexS = scriptsList.getSelectedIndex();
        int indexL = locationsList.getSelectedIndex();
        String scriptName = scriptListModel.getElementAt(indexS);
        if (!scriptName.equals("onEnter") && !scriptName.equals("onExit")) {
            Location location = locationsListModel.elementAt(indexL);
            location.removeScript(scriptName);
            scriptListModel.remove(indexS);
        }
    }

    private void saveSelectedScript() {
        int indexS = scriptsList.getSelectedIndex();
        String scriptName = scriptListModel.getElementAt(indexS);
        int indexL = locationsList.getSelectedIndex();
        Location location = locationsListModel.elementAt(indexL);
        location.setScript(scriptName, scriptText.getText());
    }


    private void selectLocationScript() {
        int index = scriptsList.getSelectedIndex();
        System.out.println(index);
        if (index >= 0) {
            String script = scriptListModel.getElementAt(index);
            int indexL = locationsList.getSelectedIndex();
            Location location = locationsListModel.elementAt(indexL);
            scriptText.setEnabled(true);
            scriptText.setText(location.getScript(script));
        } else
            scriptText.setEnabled(false);
    }

    private void saveSlotNames() {
        Map<String, String> slots = new HashMap<>();
        slotNamesModel.removeAllElements();
        for (int i = 0; i < equipTableModel.getRowCount(); i++) {
            String slotName = String.valueOf(equipTableModel.getValueAt(i, 2));
            String slotImagePath = String.valueOf(equipTableModel.getValueAt(i, 0));
            slots.put(slotName, slotImagePath);
            slotNamesModel.addElement(slotName);
        }
        Equipment.setSlotNames(slots);
    }


    private void addSlot() {
        equipTableModel.addRow(new Object[]{"/path/to/file", new ImageIcon(), "название"});
        Utils.updateRowHeights(equipTable);
    }

    private void showSlotField() {
        if (itemType.getModel().getSelectedItem() == ItemTypes.EQUIPPABLE) {
//            slotLabel.setEnabled(true);
            slotCombo.setEnabled(true);
        } else {
//            slotLabel.setEnabled(false);
            slotCombo.setEnabled(false);
        }
    }

    private void deleteItemFromLocation() {
        int indexLoc = locationsList.getSelectedIndex();
        Location selectedLoc = locationsListModel.getElementAt(indexLoc);
        int indexItem = locationItemsList.getSelectedIndex();
        Item selectedItem = locationItemsListModel.getElementAt(indexItem);
        selectedLoc.getInventory().remove(selectedItem);
        locationItemsListModel.removeElement(selectedItem);
        if (indexItem == 0)
            deleteItemFromLoc.setEnabled(false);
        locationItemsList.setSelectedIndex((indexItem > 0) ? indexItem - 1 : indexItem);
    }

    private void saveSelectedItem() {
        int index = itemsList.getSelectedIndex();
        Item selected = itemsListModel.getElementAt(index);
        selected.setName(itemName.getText());
        selected.setDescription(itemDescription.getText());
        ItemTypes type = (ItemTypes) itemType.getSelectedItem();
        selected.setType(type);
        if (slotCombo.isEnabled())
            selected.setEquipmentSlot(String.valueOf(slotCombo.getSelectedItem()));
        itemsList.updateUI();
    }

    private void addItemToLocation() {
        int indexLoc = locationsList.getSelectedIndex();
        Location selectedLoc = locationsListModel.getElementAt(indexLoc);
        int indexItem = locationTabItemsList.getSelectedIndex();
        Item selectedItem = itemsListModel.getElementAt(indexItem);
        selectedLoc.addItem(selectedItem);
        locationItemsListModel.addElement(selectedItem);
    }


    private void selectItem() {
        int index = itemsList.getSelectedIndex();
        if (index != -1) {
            Item selected = itemsListModel.getElementAt(index);
            itemName.setText(selected.getName());
            itemDescription.setText(selected.getDescription());
            itemType.setSelectedItem(selected.getType());
            itemIdField.setText(String.valueOf(selected.getId()));
            slotCombo.setSelectedItem(selected.getEquipmentSlot());
        }
    }

    private void deleteSelectedItem() {
        int index = itemsList.getSelectedIndex();
        itemsListModel.removeElementAt(index);
        itemsList.setSelectedIndex((index > 0) ? index - 1 : index);
    }


    private void setItemFormElementsEnabled() {
        if (itemsList.getSelectedIndex() >= 0) {
            itemName.setEnabled(true);
            itemDescription.setEnabled(true);
            saveItemButton.setEnabled(true);
            deleteItemButton.setEnabled(true);
            itemType.setEnabled(true);
            slotCombo.setEnabled(true);
        } else {
            itemName.setEnabled(false);
            itemDescription.setEnabled(false);
            saveItemButton.setEnabled(false);
            deleteItemButton.setEnabled(false);
            itemType.setEnabled(false);
            slotCombo.setEnabled(false);
        }
    }


    private void addNewItem() {
        Item newItem = new Item("Введите название");
        itemsListModel.addElement(newItem);
        int index = itemsListModel.getSize() - 1;
        itemsList.setSelectedIndex(index);
    }

    private void openGame() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        FileFilter ff = new FileNameExtensionFilter("TheSUD game", "sud");
        fc.setFileFilter(ff);
        int response = fc.showOpenDialog(openGameMenu);
        if (response == JFileChooser.APPROVE_OPTION) {
            System.out.println("Opening file " + fc.getSelectedFile().getPath());
            SaveFile saveFile = SaveFile.open(fc.getSelectedFile().getPath());
            player = saveFile.getPlayer();

            Sequencer.setID(saveFile.getSequencerID());

            locationsListModel.clear();
            for (Location l : saveFile.getLocations()) {
                playerLocationModel.addElement(l);
                locationsListModel.addElement(l);
                northModel.addElement(l);
                southModel.addElement(l);
                eastModel.addElement(l);
                westModel.addElement(l);
            }

            itemsListModel.clear();
            saveFile.getItems().forEach(itemsListModel::addElement);

            charactersListModel.clear();
            saveFile.getCharacters().forEach(charactersListModel::addElement);

            Map<String, String> slotNames = saveFile.getSlotNames();
            Equipment.setSlotNames(slotNames);
            equipTableModel.setRowCount(0);
            slotNamesModel.removeAllElements();
            for (Map.Entry<String, String> slotsEntry : slotNames.entrySet()) {
                equipTableModel.addRow(new Object[]{slotsEntry.getValue(), new ImageIcon(slotsEntry.getValue()), slotsEntry.getKey()});
                slotNamesModel.addElement(slotsEntry.getKey());
            }
            Utils.updateRowHeights(equipTable);
            gameName.setText(saveFile.getGameName());
            gameStartMessage.setText(saveFile.getGameStartMessage());
            updatePlayerTab();
        }
    }

    private void updatePlayerTab() {
        playerName.setText(player.getName());
        switch (player.getSex()) {
            case 0:
                sexless.setSelected(true);
                break;
            case 1:
                female.setSelected(true);
                break;
            case 2:
                male.setSelected(true);
                break;
        }
        playerLocation.setSelectedItem(player.getLocation());
    }

    private void saveGame() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        FileFilter ff = new FileNameExtensionFilter("TheSUD game", "sud");
        fc.setFileFilter(ff);
        int response = fc.showSaveDialog(saveGameMenu);
        if (response == JFileChooser.APPROVE_OPTION) {
            if (player.getLocation() != null) {
                System.out.println("Saving to " + fc.getSelectedFile().getPath());
                SaveFile saveFile = new SaveFile();
                saveFile.setPlayer(player);
                saveFile.setSequencerID(Sequencer.getCurrentId());
                ArrayList<Location> locations = new ArrayList<>();
                for (int i = 0; i < locationsListModel.size(); i++) {
                    locations.add(locationsListModel.getElementAt(i));
                }
                ArrayList<GameCharacter> characters = new ArrayList<>();
                for (int i = 0; i < charactersListModel.size(); i++) {
                    characters.add(charactersListModel.getElementAt(i));
                }
                saveFile.setCharacters(characters);
                saveFile.setLocations(locations);
                saveFile.setGameName(gameName.getText());
                saveFile.setGameStartMessage(gameStartMessage.getText());
                ArrayList<Item> items = new ArrayList<>();
                for (int i = 0; i < itemsListModel.getSize(); i++)
                    items.add(itemsListModel.getElementAt(i));
                saveFile.setItems(items);
                Map<String, String> slotsNames = new HashMap<>();
                for (int i = 0; i < equipTableModel.getRowCount(); i++) { //TODO: сохраняется в одну сторону, открывается в другую (пока не критично)
                    String slotName = String.valueOf(equipTableModel.getValueAt(i, 2));
                    String slotImagePath = String.valueOf(equipTableModel.getValueAt(i, 0));
                    slotsNames.put(slotName, slotImagePath);
                }
                saveFile.setSlotNames(slotsNames);
                saveFile.save(fc.getSelectedFile().getPath());
            } else
                JOptionPane.showMessageDialog(this, "Выберите стартовую локацию игрока!");
        }
    }

    private void savePlayer() {
        player.setName(playerName.getText());
        if (female.isSelected())
            player.setSex(1);
        if (male.isSelected())
            player.setSex(2);
        if (sexless.isSelected())
            player.setSex(0);
        Location pLocation = (Location) playerLocation.getSelectedItem();
        player.setLocation(pLocation);
    }

    private void deleteSelectedLocation() {
        int index = locationsList.getSelectedIndex();
        Location selected = locationsListModel.getElementAt(index);

        //remove location from exits in every location
        for (int i = 0; i < locationsListModel.getSize(); i++) {
            if (i != index) {
                Location removeFrom = locationsListModel.getElementAt(i);
                removeFrom.removeFromExits(selected);
            }
        }

        locationsListModel.removeElementAt(index);
        //select next other location TODO: hmmmm.....
        locationsList.setSelectedIndex((index > 0) ? index - 1 : index);
        northModel.removeElement(selected);
        southModel.removeElement(selected);
        eastModel.removeElement(selected);
        westModel.removeElement(selected);
    }

    private void saveSelectedLocation() {
        int index = locationsList.getSelectedIndex();
        int northIndex = locNorth.getSelectedIndex();
        int southIndex = locSouth.getSelectedIndex();
        int eastIndex = locEast.getSelectedIndex();
        int westIndex = locWest.getSelectedIndex();
        Location selectedLocation = locationsListModel.getElementAt(index);

        selectedLocation.setName(locName.getText());
        selectedLocation.setDescription(locDescription.getText());
        selectedLocation.setNorth(northModel.getElementAt(northIndex));
        selectedLocation.setSouth(southModel.getElementAt(southIndex));
        selectedLocation.setEast(eastModel.getElementAt(eastIndex));
        selectedLocation.setWest(westModel.getElementAt(westIndex));
        /*if (picturePathField.getText().isEmpty())
            selectedLocation.setPicturePath("/data/empty.png"); //TODO: I can't think properly right now and this is not working. You! Yes, you! Fix it!*/
        selectedLocation.setPicturePath(picturePathField.getText());

        locationsList.updateUI();
    }

    private void selectLocation() {
        int index = locationsList.getSelectedIndex();
        if (index != -1) {
            Location selected = locationsListModel.getElementAt(index);
            locName.setText(selected.getName());
            locID.setText(String.valueOf(selected.getId()));
            locDescription.setText(selected.getDescription());
            locNorth.getModel().setSelectedItem(selected.getNorth());
            locSouth.getModel().setSelectedItem(selected.getSouth());
            locEast.getModel().setSelectedItem(selected.getEast());
            locWest.getModel().setSelectedItem(selected.getWest());
            locationItemsListModel.clear();
            scriptListModel.removeAllElements();
            selected.getScripts().keySet().forEach(scriptListModel::addElement);
            selected.getInventory().forEach(locationItemsListModel::addElement);
            picturePathField.setText(selected.getPicturePath());
        }
    }

    private void addNewLocation() {
        Location newLocation = new Location("Enter location name!");
        locationsListModel.addElement(newLocation);
        int index = locationsListModel.getSize() - 1;
        locationsList.setSelectedIndex(index);
        locationsList.ensureIndexIsVisible(index);
        northModel.addElement(newLocation);
        southModel.addElement(newLocation);
        eastModel.addElement(newLocation);
        westModel.addElement(newLocation);
        playerLocationModel.addElement(newLocation);
    }

    private void setLocationFormElementsEnabled() {
        boolean enabled = locationsList.getSelectedIndex() >= 0;
        deleteLocButton.setEnabled(enabled);
        locName.setEnabled(enabled);
        locDescription.setEnabled(enabled);
        locNorth.setEnabled(enabled);
        locSouth.setEnabled(enabled);
        locEast.setEnabled(enabled);
        locWest.setEnabled(enabled);
        saveLocButton.setEnabled(enabled);
        locationItemsList.setEnabled(enabled);
        locationTabItemsList.setEnabled(enabled);
        scriptsList.setEnabled(enabled);
        saveScriptButton.setEnabled(enabled);
        pictureDialogButton.setEnabled(enabled);
        picturePathField.setEnabled(enabled);
    }
}
