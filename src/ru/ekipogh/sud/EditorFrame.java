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
    private final DefaultListModel<Item> characterItemsListModel;
    private final DefaultListModel<Item> playerItemsListModel;
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
    private JList<String> locationScriptsList;
    private RSyntaxTextArea locationScriptText;
    private DefaultComboBoxModel<Location> playerLocationModel;
    private DefaultComboBoxModel<String> slotNamesModel;
    private DefaultComboBoxModel<Location> charLocationModel;
    private DefaultListModel<String> scriptListModel;
    private DefaultListModel<String> charScriptListModel;
    private DefaultListModel<String> itemScriptListModel;
    private JButton addScriptLocButton;
    private JButton deleteScriptLocButton;
    private JButton saveScriptButton;
    private JTextField picturePathField;
    private JButton pictureDialogButton;
    private JTextField itemIdField;
    private JList<GameCharacter> charactersList;
    private JTextField charNameFiled;
    private JRadioButton femCharButton;
    private JRadioButton maleCharButton;
    private JRadioButton sexlessCharButton;
    private JComboBox<Location> charLocCombo;
    private JButton charSaveButton;
    private JButton addCharButton;
    private JButton deleteCharButton;
    private JList<String> characterScriptList;
    private RSyntaxTextArea characterScriptText;
    private JButton saveCharScriptButton;
    private JButton addCharScriptButton;
    private JButton deleteCharScriptButton;
    private JRadioButton availableButton;
    private JRadioButton notAvailableButton;
    private JList<String> itemScriptsList;
    private RSyntaxTextArea itemScriptText;
    private JButton saveItemScriptButton;
    private JButton addItemScriptButton;
    private JButton deleteItemScriptButton;
    private JList<Item> charTabItemsList;
    private JList<Item> charItemsList;
    private JButton addItemToChar;
    private JButton deleteItemFromChar;
    private JList<Item> playerTabItemsList;
    private JList<Item> playerItemsList;
    private JButton addItemToPlayerButton;
    private JButton deleteItemFromPlayerButton;

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

        locationScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        locationScriptText.setCodeFoldingEnabled(true);

        characterScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        characterScriptText.setCodeFoldingEnabled(true);

        scriptListModel = new DefaultListModel<>();
        locationScriptsList.setModel(scriptListModel);

        charScriptListModel = new DefaultListModel<>();
        characterScriptList.setModel(charScriptListModel);

        itemScriptListModel = new DefaultListModel<>();
        itemScriptsList.setModel(itemScriptListModel);

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
        characterItemsListModel = new DefaultListModel<>();
        charItemsList.setModel(characterItemsListModel);
        charTabItemsList.setModel(itemsListModel);
        playerTabItemsList.setModel(itemsListModel);
        playerItemsListModel = new DefaultListModel<>();
        playerItemsList.setModel(playerItemsListModel);

        itemType.setModel(new DefaultComboBoxModel<>(ItemTypes.values()));

        northModel = new DefaultComboBoxModel<>();
        southModel = new DefaultComboBoxModel<>();
        eastModel = new DefaultComboBoxModel<>();
        westModel = new DefaultComboBoxModel<>();
        playerLocationModel = new DefaultComboBoxModel<>();
        charLocationModel = new DefaultComboBoxModel<>();

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
        charLocationModel.addElement(null);

        locNorth.setModel(northModel);
        locSouth.setModel(southModel);
        locEast.setModel(eastModel);
        locWest.setModel(westModel);
        playerLocation.setModel(playerLocationModel);
        charLocCombo.setModel(charLocationModel);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Файл");
        JMenuItem newGameMenu = new JMenuItem("Новая");
        openGameMenu = new JMenuItem("Открыть");
        saveGameMenu = new JMenuItem("Сохранить");
        JMenuItem startGameMenu = new JMenuItem("Запустить игру");
        menuFile.add(newGameMenu);
        menuFile.add(openGameMenu);
        menuFile.add(saveGameMenu);
        menuFile.add(startGameMenu);
        menuBar.add(menuFile);

        setJMenuBar(menuBar);

        saveGameMenu.addActionListener(e -> saveGame());

        openGameMenu.addActionListener(e -> openGame());

        addLocButton.addActionListener(e -> addNewLocation());

        startGameMenu.addActionListener(e -> startGame());

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
        locationScriptsList.addListSelectionListener(e -> selectLocationScript());
        saveScriptButton.addActionListener(e -> saveSelectedScript());
        deleteScriptLocButton.addActionListener(e -> deleteLocationScript());
        addScriptLocButton.addActionListener(e -> addScriptToLocation());
        pictureDialogButton.addActionListener(e -> showChooseDialog());
        charactersList.addListSelectionListener(e -> {
            setCharsFromItemsEnabled();
            selectChar();
        });
        charSaveButton.addActionListener(e -> saveSelectedCharacter());
        addCharButton.addActionListener(e -> addNewCharacter());
        deleteCharButton.addActionListener(e -> deleteSelectedCharacter());
        addCharScriptButton.addActionListener(e -> addCharScript());
        deleteCharScriptButton.addActionListener(e -> deleteCharScript());
        characterScriptList.addListSelectionListener(e -> selectCharScript());
        saveCharScriptButton.addActionListener(e -> saveCharScript());
        itemScriptsList.addListSelectionListener(e -> selectItemScript());
        saveItemScriptButton.addActionListener(e -> saveItemScript());
        addItemScriptButton.addActionListener(e -> addItemScript());
        deleteItemScriptButton.addActionListener(e -> deleteItemScript());
        charTabItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromChar.setEnabled(false);
                    addItemToChar.setEnabled(true);
                }
            }
        });
        charItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromChar.setEnabled(true);
                    addItemToLoc.setEnabled(false);
                }
            }
        });
        addItemToChar.addActionListener(e -> addItemToCharacter());
        deleteItemFromChar.addActionListener(e -> deleteItemFromCharacter());
        playerTabItemsList.addFocusListener(new FocusAdapter() {
        });
        playerTabItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromPlayerButton.setEnabled(false);
                    addItemToPlayerButton.setEnabled(true);
                }
            }
        });
        playerItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromPlayerButton.setEnabled(true);
                    addItemToPlayerButton.setEnabled(false);
                }
            }
        });
        addItemToPlayerButton.addActionListener(e -> addItemToPlayer());
        deleteItemFromPlayerButton.addActionListener(e -> deleteItemFromPlayer());
    }

    private void deleteItemFromPlayer() {
        int indexI = playerItemsList.getSelectedIndex();
        if (indexI >= 0) {
            Item item = playerItemsListModel.getElementAt(indexI);
            player.getInventory().remove(item);
            playerItemsListModel.removeElementAt(indexI);
            if (indexI == 0)
                deleteItemFromPlayerButton.setEnabled(false);
            playerItemsList.setSelectedIndex((indexI > 0) ? indexI - 1 : indexI);
        }
    }

    private void addItemToPlayer() {
        int indexI = playerTabItemsList.getSelectedIndex();
        if (indexI >= 0) {
            Item item = itemsListModel.getElementAt(indexI);
            player.addToInventory(item);
            playerItemsListModel.addElement(item);
        }
    }

    private void deleteItemFromCharacter() {
        int indexC = charactersList.getSelectedIndex();
        int indexI = charItemsList.getSelectedIndex();
        if (indexI >= 0 && indexC >= 0) {
            GameCharacter selectedC = charactersListModel.getElementAt(indexC);
            Item selectedItem = characterItemsListModel.getElementAt(indexI);
            selectedC.getInventory().remove(selectedItem);
            characterItemsListModel.removeElement(selectedItem);
            if (indexI == 0)
                deleteItemFromChar.setEnabled(false);
            charItemsList.setSelectedIndex((indexI > 0) ? indexI - 1 : indexI);
        }
    }

    private void addItemToCharacter() {
        int indexC = charactersList.getSelectedIndex();
        int indexI = charTabItemsList.getSelectedIndex();
        if (indexC >= 0 && indexI >= 0) {
            GameCharacter selectedC = charactersListModel.getElementAt(indexC);
            Item selectedItem = itemsListModel.getElementAt(indexI);
            selectedC.addToInventory(selectedItem);
            characterItemsListModel.addElement(selectedItem);
        }
    }

    private void deleteItemScript() {
        int indexS = itemScriptsList.getSelectedIndex();
        int indexI = itemsList.getSelectedIndex();
        if (indexS >= 0) {
            String scriptName = itemScriptListModel.getElementAt(indexS);
            Item item = itemsListModel.getElementAt(indexI);
            itemScriptListModel.removeElementAt(indexS);
            item.removeScript(scriptName);
        }
    }

    private void addItemScript() {
        int index = itemsList.getSelectedIndex();
        if (index >= 0) {
            Item item = itemsListModel.getElementAt(index);
            String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
            item.addScript(scriptName, "");
            itemScriptListModel.addElement(scriptName);
        }
    }

    private void saveItemScript() {
        int indexS = itemScriptsList.getSelectedIndex();
        int indexI = itemsList.getSelectedIndex();
        if (indexI >= 0 && indexS >= 0) {
            String scriptName = itemScriptListModel.getElementAt(indexS); //TODo: BUG:java.lang.ArrayIndexOutOfBoundsException: -1 (посисбле во всех других сэйв функциях?)
            Item item = itemsListModel.getElementAt(indexI);
            item.setScript(scriptName, itemScriptText.getText());
        }
    }

    private void selectItemScript() {
        int indexI = itemsList.getSelectedIndex();
        int indexS = itemScriptsList.getSelectedIndex();
        if (indexS >= 0) {
            Item selectedItem = itemsListModel.getElementAt(indexI);
            String selectedScript = itemScriptListModel.getElementAt(indexS);
            itemScriptText.setText(selectedItem.getScript(selectedScript));
        }
    }

    private void startGame() {
        //TODO: запуск игры
    }

    private void saveCharScript() {
        int indexS = characterScriptList.getSelectedIndex();
        int indexC = charactersList.getSelectedIndex();
        if (indexC >= 0 && indexS >= 0) {
            String scriptName = charScriptListModel.getElementAt(indexS);
            GameCharacter character = charactersListModel.getElementAt(indexC);
            character.setScript(scriptName, characterScriptText.getText());
        }
    }

    private void selectCharScript() {
        int index = characterScriptList.getSelectedIndex();
        if (index >= 0) {
            String script = charScriptListModel.getElementAt(index);
            int indexC = charactersList.getSelectedIndex();
            GameCharacter character = charactersListModel.elementAt(indexC);
            characterScriptText.setEnabled(true);
            characterScriptText.setText(character.getScript(script));
        } else
            characterScriptText.setEnabled(false);
    }

    private void deleteCharScript() {
        int indexS = characterScriptList.getSelectedIndex();
        int indexC = charactersList.getSelectedIndex();
        if (indexS >= 0) {
            String scriptName = charScriptListModel.getElementAt(indexS);
            GameCharacter character = charactersListModel.getElementAt(indexC);

            if (!scriptName.equals("onPlayerArrive") && !scriptName.equals("onPlayerLeave")) {
                charactersListModel.removeElementAt(indexS);
                character.removeScript(scriptName);
            }
        }
    }

    private void addCharScript() {
        int index = charactersList.getSelectedIndex();
        if (index >= 0) {
            GameCharacter character = charactersListModel.getElementAt(index);
            String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
            character.addScript(scriptName, "");
            charScriptListModel.addElement(scriptName);
        }
    }

    private void deleteSelectedCharacter() {
        int index = charactersList.getSelectedIndex();
        charactersListModel.removeElementAt(index);
        charactersList.setSelectedIndex((index > 0) ? index - 1 : index);
    }

    private void addNewCharacter() {
        GameCharacter character = new GameCharacter("Введите имя");
        charactersListModel.addElement(character);
    }

    private void saveSelectedCharacter() {
        int index = charactersList.getSelectedIndex();
        GameCharacter selected = charactersListModel.getElementAt(index);
        selected.setName(charNameFiled.getText());
        if (femCharButton.isSelected())
            selected.setSex(1);
        if (maleCharButton.isSelected())
            selected.setSex(2);
        if (sexlessCharButton.isSelected())
            selected.setSex(0);
        selected.setLocation((Location) charLocationModel.getSelectedItem());
    }

    private void selectChar() {
        int index = charactersList.getSelectedIndex();
        if (index != -1) {
            GameCharacter selected = charactersListModel.getElementAt(index);
            charNameFiled.setText(selected.getName());
            switch (selected.getSex()) {
                case 0:
                    sexlessCharButton.setSelected(true);
                    break;
                case 1:
                    femCharButton.setSelected(true);
                    break;
                case 2:
                    maleCharButton.setSelected(true);
                    break;
            }
            charLocationModel.setSelectedItem(selected.getLocation());
            charScriptListModel.removeAllElements();
            selected.getScripts().keySet().forEach(charScriptListModel::addElement);
        }
    }

    private void setCharsFromItemsEnabled() {
        boolean enabled = charactersList.getSelectedIndex() >= 0;
        charNameFiled.setEnabled(enabled);
        femCharButton.setEnabled(enabled);
        maleCharButton.setEnabled(enabled);
        sexlessCharButton.setEnabled(enabled);
        charTabItemsList.setEnabled(enabled);
        charItemsList.setEnabled(enabled);
        charLocCombo.setEnabled(enabled);
        charSaveButton.setEnabled(enabled);
        addCharButton.setEnabled(enabled);
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
        if (index >= 0) {
            Location location = locationsListModel.elementAt(index);
            String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
            location.addScript(scriptName, "");
            scriptListModel.addElement(scriptName);
        }
    }

    private void deleteLocationScript() {
        int indexS = locationScriptsList.getSelectedIndex();
        int indexL = locationsList.getSelectedIndex();
        if (indexS >= 0) {
            String scriptName = scriptListModel.getElementAt(indexS);
            if (!scriptName.equals("onEnter") && !scriptName.equals("onExit")) {
                Location location = locationsListModel.elementAt(indexL);
                location.removeScript(scriptName);
                scriptListModel.remove(indexS);
            }
        }
    }

    private void saveSelectedScript() {
        int indexS = locationScriptsList.getSelectedIndex();
        int indexL = locationsList.getSelectedIndex();
        if (indexS >= 0 && indexL >= 0) {
            String scriptName = scriptListModel.getElementAt(indexS);
            Location location = locationsListModel.elementAt(indexL);
            location.setScript(scriptName, locationScriptText.getText());
        }
    }

    private void selectLocationScript() {
        int index = locationScriptsList.getSelectedIndex();
        System.out.println(index);
        if (index >= 0) {
            String script = scriptListModel.getElementAt(index);
            int indexL = locationsList.getSelectedIndex();
            Location location = locationsListModel.elementAt(indexL);
            locationScriptText.setEnabled(true);
            locationScriptText.setText(location.getScript(script));
        } else
            locationScriptText.setEnabled(false);
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
            slotCombo.setEnabled(true);
        } else {
            slotCombo.setEnabled(false);
        }
    }

    private void deleteItemFromLocation() {
        int indexLoc = locationsList.getSelectedIndex();
        int indexItem = locationItemsList.getSelectedIndex();
        if (indexItem >= 0 && indexLoc >= 0) {
            Location selectedLoc = locationsListModel.getElementAt(indexLoc);
            Item selectedItem = locationItemsListModel.getElementAt(indexItem);
            selectedLoc.getInventory().remove(selectedItem);
            locationItemsListModel.removeElement(selectedItem);
            if (indexItem == 0)
                deleteItemFromLoc.setEnabled(false);
            locationItemsList.setSelectedIndex((indexItem > 0) ? indexItem - 1 : indexItem);
        }
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
        int indexItem = locationTabItemsList.getSelectedIndex();
        if (indexLoc >= 0 && indexItem >= 0) {
            Location selectedLoc = locationsListModel.getElementAt(indexLoc);
            Item selectedItem = itemsListModel.getElementAt(indexItem);
            selectedLoc.addItem(selectedItem);
            locationItemsListModel.addElement(selectedItem);
        }
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
            itemScriptListModel.clear();
            selected.getScripts().keySet().forEach(itemScriptListModel::addElement);
        }
    }

    private void deleteSelectedItem() {
        int index = itemsList.getSelectedIndex();
        itemsListModel.removeElementAt(index);
        itemsList.setSelectedIndex((index > 0) ? index - 1 : index);
    }

    private void setItemFormElementsEnabled() {
        boolean enabled = itemsList.getSelectedIndex() >= 0;
        itemName.setEnabled(enabled);
        itemDescription.setEnabled(enabled);
        saveItemButton.setEnabled(enabled);
        deleteItemButton.setEnabled(enabled);
        itemType.setEnabled(enabled);
        slotCombo.setEnabled(enabled);
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
                charLocationModel.addElement(l);
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
        player.getInventory().forEach(playerItemsListModel::addElement);
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
        charLocationModel.removeElement(selected);
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
        selectedLocation.setAvailable(availableButton.isSelected());
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
            availableButton.setSelected(selected.isAvailable());
            notAvailableButton.setSelected(!selected.isAvailable());
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
        charLocationModel.addElement(newLocation);
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
        locationScriptsList.setEnabled(enabled);
        saveScriptButton.setEnabled(enabled);
        pictureDialogButton.setEnabled(enabled);
        picturePathField.setEnabled(enabled);
        availableButton.setEnabled(enabled);
        notAvailableButton.setEnabled(enabled);
    }
}
