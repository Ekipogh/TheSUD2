package ru.ekipogh.sud;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
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
    private final DefaultListModel<Item> itemsListModel;
    private final DefaultListModel<Item> locationItemsListModel;
    private final DefaultTableModel equipTableModel;
    private final DefaultListModel<GameCharacter> charactersListModel;
    private final DefaultListModel<Item> characterItemsListModel;
    private final DefaultListModel<Item> playerItemsListModel;
    private final DefaultListModel<LocationCategory> locationCategoriesListModel;
    private final DefaultListModel<String> locationCategoryScriptsListModel;
    private final DefaultListModel<ItemCategory> itemCotegoriesListModel;
    private final DefaultListModel<String> itemCategoryScriptsListModel;
    private final DefaultListModel<CharacterCategory> charCategoriesListModel;
    private final DefaultListModel<String> charCategoryScriptsListModel;
    private final DefaultComboBoxModel<LocationCategory> locationCategoryComboModel;
    private final DefaultComboBoxModel<ItemCategory> itemCategoryComboModel;
    private final DefaultComboBoxModel<CharacterCategory> characterCategoryComboModel;
    private final DefaultListModel<String> playerScriptListModel;
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
    private JButton addLocationButton;
    private JButton deleteLocButton;
    private JButton saveLocButton;
    private JTextField playerName;
    private JComboBox<Location> playerLocation;
    private JButton savePlayer;
    private JTextField gameName;
    private JTextArea gameStartMessage;
    private JList<Item> itemsList;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JTextField itemName;
    private JTextArea itemDescription;
    private JComboBox<ItemTypes> itemTypeCombo;
    private JButton saveItemButton;
    private JList<Item> locationTabItemsList;
    private JList<Item> locationItemsList;
    private JButton addItemToLocationButton;
    private JButton deleteItemFromLocButton;
    private JComboBox<String> slotCombo;
    private JTable equipTable;
    private JButton addSlotButton;
    private JButton deleteSlotButton;
    private JButton saveSlotsButton;
    private JList<String> locationScriptsList;
    private RSyntaxTextArea locationScriptText;
    private DefaultComboBoxModel<Location> playerLocationModel;
    private DefaultComboBoxModel<String> slotNamesModel;
    private DefaultComboBoxModel<Location> characterLocationModel;
    private DefaultListModel<String> locationScriptListModel;
    private DefaultListModel<String> characterScriptListModel;
    private DefaultListModel<String> itemScriptListModel;
    private JButton addScriptLocButton;
    private JButton deleteScriptLocButton;
    private JButton saveLocScriptButton;
    private JTextField itemIdField;
    private JList<GameCharacter> charactersList;
    private JTextField charNameFiled;
    private JComboBox<Location> characterLocationCombo;
    private JButton charSaveButton;
    private JButton addCharacterButton;
    private JButton deleteCharButton;
    private JList<String> characterScriptList;
    private RSyntaxTextArea characterScriptText;
    private JButton saveCharScriptButton;
    private JButton addCharacterScriptButton;
    private JButton deleteCharScriptButton;
    private JRadioButton availableButton;
    private JRadioButton notAvailableButton;
    private JList<String> itemScriptsList;
    private RSyntaxTextArea itemScriptText;
    private JButton saveItemScriptButton;
    private JButton addItemScriptButton;
    private JButton deleteItemScriptButton;
    private JList<Item> characterTabItemsList;
    private JList<Item> characterItemsList;
    private JButton addItemToCharacterButton;
    private JButton deleteItemFromCharButton;
    private JList<Item> playerTabItemsList;
    private JList<Item> playerItemsList;
    private JButton addItemToPlayerButton;
    private JButton deleteItemFromPlayerButton;
    private JComboBox<ItemCategory> itemCategoryCombo;
    private JComboBox<LocationCategory> locationCategoryCombo;
    private JComboBox<CharacterCategory> characterCategoryCombo;
    private JList<LocationCategory> locationCategoriesList;
    private JButton addLocationCategoryButton;
    private JButton deleteLocationCategoryButton;
    private JList<String> locationCategoryScriptsList;
    private JTextField locationCategoryNameFiled;
    private RSyntaxTextArea locationCategoryScriptText;
    private JButton saveLocationCategoryScriptButton;
    private JList<ItemCategory> itemCotegoriesList;
    private JList<String> itemCategoryScriptsList;
    private RSyntaxTextArea itemCategoryScriptText;
    private JButton addItemCategoryButton;
    private JButton deleteItemCategoryButton;
    private JButton saveItemCategoryScriptButton;
    private JList<CharacterCategory> charCategoriesList;
    private JList<String> charCategoryScriptsList;
    private RSyntaxTextArea charCategoryScriptText;
    private JButton addCharacterCategoryButton;
    private JButton deleteCharCategoryButton;
    private JButton saveCharacterCategoryScriptButton;
    private JTextField itemCategoryNameField;
    private JButton addLocationCategoryScriptButton;
    private JButton deleteLocationCategoryScriptButton;
    private JButton addItemCategoryScriptButton;
    private JButton deleteItemCategoryScriptButton;
    private JButton addCharacterCategoryScriptButton;
    private JButton deleteCharCategoryScriptButton;
    private JTextField charCategoryNameField;
    private RSyntaxTextArea initScriptText;
    private JTextField charIdField;
    private JTextArea playerDescriptionArea;
    private JTextArea charDescriptionArea;
    private JTable table1;
    private JCheckBox locationScriptEnabledBox;
    private JCheckBox itemScriptEnabledBox;
    private JCheckBox characterScriptEnabledBox;
    private JCheckBox locationCategoryScriptEnabledBox;
    private JCheckBox itemCategoryScriptEnabledBox;
    private JCheckBox characterCategoryScriptEnabledBox;
    private JList<String> playerScriptList;
    private JButton addPlayerScriptButton;
    private JButton deletePlayerScriptButton;
    private RSyntaxTextArea playerScriptText;
    private JCheckBox playerScriptEnableBox;
    private JButton savePlayerScriptButton;
    private GameCharacter player;
    private String gamePath;


    public EditorFrame(String gamePath) {
        super("Редактор");
        player = new GameCharacter("Безымянный");

        this.gamePath = gamePath;

        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //модели листов
        charactersListModel = new DefaultListModel<>();
        charactersList.setModel(charactersListModel);

        characterScriptListModel = new DefaultListModel<>();
        characterScriptList.setModel(characterScriptListModel);

        characterItemsListModel = new DefaultListModel<>();
        characterItemsList.setModel(characterItemsListModel);

        charCategoriesListModel = new DefaultListModel<>();
        charCategoriesList.setModel(charCategoriesListModel);

        charCategoryScriptsListModel = new DefaultListModel<>();
        charCategoryScriptsList.setModel(charCategoryScriptsListModel);

        playerItemsListModel = new DefaultListModel<>();
        playerItemsList.setModel(playerItemsListModel);

        playerScriptListModel = new DefaultListModel<>();
        playerScriptList.setModel(playerScriptListModel);

        locationsListModel = new DefaultListModel<>();
        locationsList.setModel(locationsListModel);

        locationScriptListModel = new DefaultListModel<>();
        locationScriptsList.setModel(locationScriptListModel);

        locationItemsListModel = new DefaultListModel<>();
        locationItemsList.setModel(locationItemsListModel);

        locationCategoriesListModel = new DefaultListModel<>();
        locationCategoriesList.setModel(locationCategoriesListModel);

        locationCategoryScriptsListModel = new DefaultListModel<>();
        locationCategoryScriptsList.setModel(locationCategoryScriptsListModel);

        itemScriptListModel = new DefaultListModel<>();
        itemScriptsList.setModel(itemScriptListModel);

        itemsListModel = new DefaultListModel<>();
        itemsList.setModel(itemsListModel);

        itemCotegoriesListModel = new DefaultListModel<>();
        itemCotegoriesList.setModel(itemCotegoriesListModel);

        itemCategoryScriptsListModel = new DefaultListModel<>();
        itemCategoryScriptsList.setModel(itemCategoryScriptsListModel);

        locationTabItemsList.setModel(itemsListModel);
        characterTabItemsList.setModel(itemsListModel);
        playerTabItemsList.setModel(itemsListModel);

        //поля скриптов
        characterScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        characterScriptText.setCodeFoldingEnabled(true);

        charCategoryScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        charCategoryScriptText.setCodeFoldingEnabled(true);

        playerScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        playerScriptText.setCodeFoldingEnabled(true);

        locationScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        locationScriptText.setCodeFoldingEnabled(true);

        locationCategoryScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        locationCategoryScriptText.setCodeFoldingEnabled(true);

        itemScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        itemScriptText.setCodeFoldingEnabled(true);

        itemCategoryScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        itemCategoryScriptText.setCodeFoldingEnabled(true);

        initScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        initScriptText.setCodeFoldingEnabled(true);

        //модели комбобоксов
        itemTypeCombo.setModel(new DefaultComboBoxModel<>(ItemTypes.values()));

        northModel = new DefaultComboBoxModel<>();
        southModel = new DefaultComboBoxModel<>();
        eastModel = new DefaultComboBoxModel<>();
        westModel = new DefaultComboBoxModel<>();

        playerLocationModel = new DefaultComboBoxModel<>();
        characterLocationModel = new DefaultComboBoxModel<>();

        slotNamesModel = new DefaultComboBoxModel<>();

        locationCategoryComboModel = new DefaultComboBoxModel<>();
        itemCategoryComboModel = new DefaultComboBoxModel<>();
        characterCategoryComboModel = new DefaultComboBoxModel<>();

        northModel.addElement(null);
        southModel.addElement(null);
        eastModel.addElement(null);
        westModel.addElement(null);

        playerLocationModel.addElement(null);
        characterLocationModel.addElement(null);

        locationCategoryComboModel.addElement(null);
        itemCategoryComboModel.addElement(null);
        characterCategoryComboModel.addElement(null);

        locNorth.setModel(northModel);
        locSouth.setModel(southModel);
        locEast.setModel(eastModel);
        locWest.setModel(westModel);

        characterLocationCombo.setModel(characterLocationModel);
        playerLocation.setModel(playerLocationModel);

        slotCombo.setModel(slotNamesModel);

        characterCategoryCombo.setModel(characterCategoryComboModel);
        locationCategoryCombo.setModel(locationCategoryComboModel);
        itemCategoryCombo.setModel(itemCategoryComboModel);

        //модели таблиц
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

        //заполнение таблицы экипировки
        fillEquipmentTable();

        //меню окна
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Файл");
        JMenuItem newGameMenu = new JMenuItem("Новая");
        JMenuItem openGameMenu = new JMenuItem("Открыть");
        JMenuItem saveGameMenu = new JMenuItem("Сохранить");
        JMenuItem saveAsMenu = new JMenuItem("Сохранить как");
        JMenuItem startGameMenu = new JMenuItem("Запустить игру");
        menuFile.add(newGameMenu);
        menuFile.add(openGameMenu);
        menuFile.add(saveGameMenu);
        menuFile.add(saveAsMenu);
        menuFile.add(startGameMenu);
        menuBar.add(menuFile);

        setJMenuBar(menuBar);

        //листенеры
        //листенеры конопок
        addCharacterButton.addActionListener(e -> addNewCharacter());

        addCharacterCategoryButton.addActionListener(e -> addCharCategory());

        addCharacterCategoryScriptButton.addActionListener(e -> addCharCategoryScript());

        addCharacterScriptButton.addActionListener(e -> addCharScript());

        addItemButton.addActionListener(e -> addNewItem());

        addItemCategoryButton.addActionListener(e -> addItemCategory());

        addItemCategoryScriptButton.addActionListener(e -> addItemCategoryScript());

        addItemScriptButton.addActionListener(e -> addItemScript());

        addItemToCharacterButton.addActionListener(e -> addItemToCharacter());

        addItemToLocationButton.addActionListener(e -> addItemToLocation());

        addItemToPlayerButton.addActionListener(e -> addItemToPlayer());

        addLocationButton.addActionListener(e -> addNewLocation());

        addLocationCategoryButton.addActionListener(e -> addLocationCategory());

        savePlayerScriptButton.addActionListener(e -> savePlayerScript());

        deletePlayerScriptButton.addActionListener(e -> deletePlayerScript());

        addPlayerScriptButton.addActionListener(e -> addPlayerScript());

        saveCharacterCategoryScriptButton.addActionListener(e -> saveCharCategoryScript());

        deleteCharCategoryScriptButton.addActionListener(e -> deleteCharCategoryScript());

        deleteCharCategoryButton.addActionListener(e -> deleteCharCategory());

        deleteItemCategoryScriptButton.addActionListener(e -> deleteItemCategoryScript());

        deleteLocationCategoryScriptButton.addActionListener(e -> deleteLocationCategoryScript());

        addLocationCategoryScriptButton.addActionListener(e -> addLocationCategoryScript());

        saveItemCategoryScriptButton.addActionListener(e -> saveItemCategoryScript());

        saveLocButton.addActionListener(e -> saveSelectedLocation());

        deleteLocButton.addActionListener(e -> deleteSelectedLocation());

        savePlayer.addActionListener(e -> savePlayer());

        deleteItemButton.addActionListener(e -> deleteSelectedItem());

        saveItemButton.addActionListener(e -> saveSelectedItem());

        deleteItemFromLocButton.addActionListener(e -> deleteItemFromLocation());

        addSlotButton.addActionListener(e -> addSlot());

        deleteSlotButton.addActionListener(e -> deleteSlot());

        saveSlotsButton.addActionListener(e -> saveSlotNames());

        saveLocScriptButton.addActionListener(e -> saveLocationScript());

        deleteScriptLocButton.addActionListener(e -> deleteLocationScript());

        addScriptLocButton.addActionListener(e -> addScriptToLocation());

        charSaveButton.addActionListener(e -> saveSelectedCharacter());

        deleteCharButton.addActionListener(e -> deleteSelectedCharacter());

        deleteCharScriptButton.addActionListener(e -> deleteCharScript());

        saveCharScriptButton.addActionListener(e -> saveCharScript());

        saveItemScriptButton.addActionListener(e -> saveItemScript());

        deleteItemScriptButton.addActionListener(e -> deleteItemScript());

        deleteItemFromCharButton.addActionListener(e -> deleteItemFromCharacter());

        deleteItemFromPlayerButton.addActionListener(e -> deleteItemFromPlayer());

        deleteLocationCategoryButton.addActionListener(e -> deleteLocationCategory());

        saveLocationCategoryScriptButton.addActionListener(e -> saveLocationCategoryScript());

        deleteItemCategoryButton.addActionListener(e -> deleteItemCategory());

        //листенеры меню
        newGameMenu.addActionListener(e -> newGame());
        newGameMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));

        saveGameMenu.addActionListener(e -> menuSaveGame());
        saveGameMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));

        openGameMenu.addActionListener(e -> openGame());
        openGameMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));

        startGameMenu.addActionListener(e -> startGame());
        startGameMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));

        saveAsMenu.addActionListener(e -> saveAs());
        saveAsMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK + InputEvent.SHIFT_MASK));

        //листенеры листов
        charCategoryScriptsList.addListSelectionListener(e -> selectCharCategoryScript());

        charCategoriesList.addListSelectionListener(e -> selectCharCategory());

        itemCategoryScriptsList.addListSelectionListener(e -> selectItemCategoryScript());

        itemCotegoriesList.addListSelectionListener(e -> selectItemCategory());

        locationsList.addListSelectionListener(e -> {
            setLocationFormElementsEnabled();
            selectLocation();
        });

        itemsList.addListSelectionListener(e -> {
            setItemFormElementsEnabled();
            selectItem();
        });

        locationScriptsList.addListSelectionListener(e -> selectLocationScript());

        locationTabItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromLocButton.setEnabled(false);
                    addItemToLocationButton.setEnabled(true);
                }
            }
        });

        locationItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromLocButton.setEnabled(true);
                    addItemToLocationButton.setEnabled(false);
                }
            }
        });

        charactersList.addListSelectionListener(e -> {
            setCharsFromItemsEnabled();
            selectChar();
        });

        characterScriptList.addListSelectionListener(e -> selectCharScript());

        itemScriptsList.addListSelectionListener(e -> selectItemScript());

        characterTabItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromCharButton.setEnabled(false);
                    addItemToCharacterButton.setEnabled(true);
                }
            }
        });
        characterItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromCharButton.setEnabled(true);
                    addItemToLocationButton.setEnabled(false);
                }
            }
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

        playerScriptList.addListSelectionListener(e -> selectPlayerScript());

        locationCategoriesList.addListSelectionListener(e -> selectLocationCategory());
        locationCategoryScriptsList.addListSelectionListener(e -> selectLocationCategoryScript());

        //листенеры комбобоксов
        itemTypeCombo.addActionListener(e -> showSlotField());

        //листенеры текстфилдов
        locationCategoryNameFiled.addActionListener(e -> saveLocationCategoryName());
        itemCategoryNameField.addActionListener(e -> saveItemCategoryName());
        charCategoryNameField.addActionListener(e -> saveCharCategoryName());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.launcher.setVisible(true);
                super.windowClosing(e);
            }
        });

        if (!gamePath.isEmpty())
            loadGame();

        //test area
        CheckTableModel tableModel = new CheckTableModel();
        table1.setModel(tableModel);
        tableModel.addRow(true, "sadjklsjdlk");
    }

    private void savePlayerScript() {
        int indexS = playerScriptList.getSelectedIndex();
        if (indexS >= 0) {
            String scriptName = playerScriptListModel.elementAt(indexS);
            Script script = new Script(playerScriptText.getText(), playerScriptEnableBox.isSelected());
            player.setScript(scriptName, script);
        }
    }

    private void deletePlayerScript() {
        int indexS = playerScriptList.getSelectedIndex();
        if (indexS >= 0) {
            String scriptName = playerScriptListModel.elementAt(indexS);
            player.removeScript(scriptName);
            playerScriptListModel.remove(indexS);
        }
    }

    private void addPlayerScript() {
        String scriptName = JOptionPane.showInputDialog("Введите название скритпа");
        if (scriptName != null) {
            Script s = new Script("", true);
            player.setScript(scriptName, s);
            playerScriptListModel.addElement(scriptName);
        }
    }

    private void selectPlayerScript() {
        int indexS = playerScriptList.getSelectedIndex();
        if (indexS >= 0) {
            String scriptName = playerScriptListModel.elementAt(indexS);
            Script s = player.getScript(scriptName);
            playerScriptText.setText(s.getText());
            playerScriptEnableBox.setSelected(s.isEnabled());
        }
    }

    private void saveAs() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        FileFilter ff = new FileNameExtensionFilter("TheSUD game", "sud");
        fc.setFileFilter(ff);
        int response = fc.showSaveDialog(this);
        if (response == JFileChooser.APPROVE_OPTION)
            gamePath = fc.getSelectedFile().getPath();
        else
            return;
        saveGame();
    }

    private void fillEquipmentTable() {
        Equipment.getSlotMap().entrySet().forEach((entry) -> {
            String slotName = entry.getKey();
            String icon = entry.getValue();
            equipTableModel.addRow(new Object[]{icon, new ImageIcon(icon), slotName});
        });
        Utils.updateRowHeights(equipTable);

        Equipment.getSlotNames().forEach(slotNamesModel::addElement);
    }

    private void newGame() {
        player = new GameCharacter("Безымянный");
        gameName.setText("");
        gameStartMessage.setText("");
        locationsListModel.clear();
        itemsListModel.clear();
        charactersListModel.clear();
        northModel.removeAllElements();
        southModel.removeAllElements();
        westModel.removeAllElements();
        eastModel.removeAllElements();
        northModel.addElement(null);
        southModel.addElement(null);
        westModel.addElement(null);
        eastModel.addElement(null);
        characterLocationModel.removeAllElements();
        characterLocationModel.addElement(null);
        playerLocationModel.removeAllElements();
        Item.clearCategories();
        Location.clearCategories();
        GameCharacter.clearCategories();
        Equipment.clearSlots();
        equipTableModel.setRowCount(0);
        fillEquipmentTable();
        locationCategoriesListModel.clear();
        itemCotegoriesListModel.clear();
        charCategoriesListModel.clear();
        locationCategoryComboModel.removeAllElements();
        itemCategoryComboModel.removeAllElements();
        characterCategoryComboModel.removeAllElements();
        initScriptText.setText("");
        playerDescriptionArea.setText("");
    }

    private void saveCharCategoryScript() {
        int indexCat = charCategoriesList.getSelectedIndex();
        int indexS = charCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            CharacterCategory characterCategory = charCategoriesListModel.elementAt(indexCat);
            String scriptName = charCategoryScriptsListModel.elementAt(indexS);
            //characterCategory.setScript(scriptName, charCategoryScriptText.getText());
            characterCategory.setScript(scriptName, new Script(charCategoryScriptText.getText(), characterCategoryScriptEnabledBox.isSelected()));
        }
    }

    private void saveCharCategoryName() {
        int indexCat = charCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            CharacterCategory characterCategory = charCategoriesListModel.elementAt(indexCat);
            String name = charCategoryNameField.getText();
            for (CharacterCategory category : GameCharacter.getCategories()) {
                if (category.getName().equals(name)) {
                    JOptionPane.showMessageDialog(this, "Категория с таким названием уже существует");
                    return;
                }
            }
            characterCategory.setName(name);
            charCategoriesList.updateUI();
        }
    }

    private void selectCharCategoryScript() {
        int indexCat = charCategoriesList.getSelectedIndex();
        int indexS = charCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            CharacterCategory characterCategory = charCategoriesListModel.elementAt(indexCat);
            String scriptName = charCategoryScriptsListModel.elementAt(indexS);
            //charCategoryScriptText.setText(characterCategory.getScript(scriptName));
            Script script = characterCategory.getScript(scriptName);
            charCategoryScriptText.setText(script.getText());
            characterCategoryScriptEnabledBox.setSelected(script.isEnabled());
        }
    }

    private void deleteCharCategoryScript() {
        int indexCat = charCategoriesList.getSelectedIndex();
        int indexS = charCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            CharacterCategory characterCategory = charCategoriesListModel.elementAt(indexCat);
            String scriptName = charCategoryScriptsListModel.elementAt(indexS);
            if (!scriptName.startsWith("_on")) {
                characterCategory.removeScript(scriptName);
                charCategoryScriptsListModel.removeElement(scriptName);
            }
        }
    }

    private void addCharCategoryScript() {
        int index = charCategoriesList.getSelectedIndex();
        if (index >= 0) {
            CharacterCategory characterCategory = charCategoriesListModel.elementAt(index);
            String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
            if (scriptName != null) {
                Script script = new Script("", true);
                characterCategory.setScript(scriptName, script);
                charCategoryScriptsListModel.addElement(scriptName);
            }
        }
    }

    private void deleteCharCategory() {
        int indexCat = charCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            CharacterCategory characterCategory = charCategoriesListModel.elementAt(indexCat);
            GameCharacter.deleteCategory(characterCategory);
            charCategoriesListModel.removeElement(characterCategory);
            for (int i = 0; i < charactersListModel.size(); i++) {
                GameCharacter character = charactersListModel.elementAt(i);
                if (character.getCategory().equals(characterCategory))
                    character.removeCategory();
            }
            characterCategoryComboModel.removeElement(characterCategory);
            charCategoriesList.setSelectedIndex((indexCat > 0) ? indexCat - 1 : indexCat);
        }
    }

    private void addCharCategory() {
        CharacterCategory characterCategory = new CharacterCategory("Название категории");
        GameCharacter.addNewCategory(characterCategory);
        charCategoriesListModel.addElement(characterCategory);
        characterCategoryComboModel.addElement(characterCategory);
    }

    private void selectCharCategory() {
        int indexCat = charCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            CharacterCategory characterCategory = charCategoriesListModel.elementAt(indexCat);
            charCategoryNameField.setText(characterCategory.getName());
            charCategoryScriptsListModel.clear();
            //characterCategory.getScripts().keySet().forEach(charCategoryScriptsListModel::addElement);
            characterCategory.getScripts().keySet().forEach(charCategoryScriptsListModel::addElement);
        }
    }

    private void deleteItemCategoryScript() {
        int indexCat = itemCotegoriesList.getSelectedIndex();
        int indexS = itemCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            ItemCategory itemCategory = itemCotegoriesListModel.elementAt(indexCat);
            String scriptName = itemCategoryScriptsListModel.elementAt(indexS);
            if (!scriptName.startsWith("_on")) {
                itemCategory.removeScript(scriptName);
                itemCategoryScriptsListModel.removeElement(scriptName);
            }
        }
    }

    private void addItemCategoryScript() {
        int index = itemCotegoriesList.getSelectedIndex();
        if (index >= 0) {
            ItemCategory itemCategory = itemCotegoriesListModel.elementAt(index);
            String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
            if (scriptName != null) {
                Script script = new Script("", true);
                itemCategory.setScript(scriptName, script);
                itemCategoryScriptsListModel.addElement(scriptName);
            }
        }
    }

    private void deleteLocationCategoryScript() {
        int indexCat = locationCategoriesList.getSelectedIndex();
        int indexS = locationCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            LocationCategory locationCategory = locationCategoriesListModel.elementAt(indexCat);
            String scriptName = locationCategoryScriptsListModel.elementAt(indexS);
            if (!scriptName.startsWith("_on")) {
                locationCategory.removeScript(scriptName);
                locationCategoryScriptsListModel.removeElement(scriptName);
            }
        }
    }

    private void addLocationCategoryScript() {
        int index = locationCategoriesList.getSelectedIndex();
        if (index >= 0) {
            LocationCategory locationCategory = locationCategoriesListModel.elementAt(index);
            String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
            if (scriptName != null) {
                Script script = new Script("", true);
                locationCategory.setScript(scriptName, script);
                locationCategoryScriptsListModel.addElement(scriptName);
            }
        }
    }

    private void saveItemCategoryScript() {
        int indexCat = itemCotegoriesList.getSelectedIndex();
        int indexS = itemCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            ItemCategory itemCategory = itemCotegoriesListModel.elementAt(indexCat);
            String scriptName = itemCategoryScriptsListModel.elementAt(indexS);
            //itemCategory.setScript(scriptName, locationCategoryScriptText.getText());
            itemCategory.setScript(scriptName, new Script(itemCategoryScriptText.getText(), itemCategoryScriptEnabledBox.isSelected()));
        }
    }

    private void saveItemCategoryName() {
        int indexCat = itemCotegoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            ItemCategory itemCategory = itemCotegoriesListModel.elementAt(indexCat);
            String name = itemCategoryNameField.getText();
            for (ItemCategory category : Item.getCategories()) {
                if (category.getName().equals(name)) {
                    JOptionPane.showMessageDialog(this, "Категория с таким названием уже существует");
                    return;
                }
            }
            itemCategory.setName(name);
            itemCotegoriesList.updateUI();
        }
    }

    private void selectItemCategoryScript() {
        int indexCat = itemCotegoriesList.getSelectedIndex();
        int indexS = itemCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            ItemCategory itemCategory = itemCotegoriesListModel.elementAt(indexCat);
            String scriptName = itemCategoryScriptsListModel.elementAt(indexS);
            Script script = itemCategory.getScript(scriptName);
            itemCategoryScriptText.setText(script.getText());
            itemCategoryScriptEnabledBox.setSelected(script.isEnabled());
            //itemCategoryScriptText.setText(itemCategory.getScript(scriptName));
        }
    }

    private void selectItemCategory() {
        int indexCat = itemCotegoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            ItemCategory itemCategory = itemCotegoriesListModel.elementAt(indexCat);
            itemCategoryNameField.setText(itemCategory.getName());
            itemCategoryScriptsListModel.clear();
            //itemCategory.getScripts().keySet().forEach(itemCategoryScriptsListModel::addElement);
            itemCategory.getScripts().keySet().forEach(itemCategoryScriptsListModel::addElement);
        }
    }

    private void deleteItemCategory() {
        int indexCat = itemCotegoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            ItemCategory itemCategory = itemCotegoriesListModel.elementAt(indexCat);
            Item.deleteCategory(itemCategory);
            itemCotegoriesListModel.removeElement(itemCategory);
            for (int i = 0; i < itemsListModel.size(); i++) {
                Item item = itemsListModel.elementAt(i);
                if (item.getCategory().equals(itemCategory))
                    item.removeCategory();
            }
            itemCategoryComboModel.removeElement(itemCategory);
            itemCotegoriesList.setSelectedIndex((indexCat > 0) ? indexCat - 1 : indexCat);
        }
    }

    private void addItemCategory() {
        ItemCategory itemCategory = new ItemCategory("Название категории");
        Item.addNewCategory(itemCategory);
        itemCotegoriesListModel.addElement(itemCategory);
        itemCategoryComboModel.addElement(itemCategory);
    }

    private void selectLocationCategoryScript() {
        int indexCat = locationCategoriesList.getSelectedIndex();
        int indexS = locationCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            LocationCategory locationCategory = locationCategoriesListModel.elementAt(indexCat);
            String scriptName = locationCategoryScriptsListModel.elementAt(indexS);
            Script script = locationCategory.getScript(scriptName);
            locationCategoryScriptText.setText(script.getText());
            locationCategoryScriptEnabledBox.setSelected(script.isEnabled());
            //locationCategoryScriptText.setText(locationCategory.getScript(scriptName));
        }
    }

    private void selectLocationCategory() {
        int indexCat = locationCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            LocationCategory locationCategory = locationCategoriesListModel.elementAt(indexCat);
            locationCategoryNameFiled.setText(locationCategory.getName());
            locationCategoryScriptsListModel.clear();
            //locationCategory.getScripts().keySet().forEach(locationCategoryScriptsListModel::addElement);
            locationCategory.getScripts().keySet().forEach(locationCategoryScriptsListModel::addElement);
        }
    }

    private void saveLocationCategoryScript() {
        int indexCat = locationCategoriesList.getSelectedIndex();
        int indexS = locationCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            LocationCategory locationCategory = locationCategoriesListModel.elementAt(indexCat);
            String scriptName = locationCategoryScriptsListModel.elementAt(indexS);
            //locationCategory.setScript(scriptName, locationCategoryScriptText.getText());
            locationCategory.setScript(scriptName, new Script(locationCategoryScriptText.getText(), locationCategoryScriptEnabledBox.isSelected()));
        }
    }

    private void saveLocationCategoryName() {
        int indexCat = locationCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            LocationCategory locationCategory = locationCategoriesListModel.elementAt(indexCat);
            String name = locationCategoryNameFiled.getText();
            for (LocationCategory category : Location.getCategories()) {
                if (category.getName().equals(name)) {
                    JOptionPane.showMessageDialog(this, "Категория с таким названием уже существует");
                    return;
                }
            }
            locationCategory.setName(name);
            locationCategoriesList.updateUI();
        }
    }

    private void deleteLocationCategory() {
        int indexCat = locationCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            LocationCategory locationCategory = locationCategoriesListModel.elementAt(indexCat);
            Location.deleteCategory(locationCategory);
            locationCategoriesListModel.removeElement(locationCategory);
            for (int i = 0; i < locationsListModel.size(); i++) {
                Location l = locationsListModel.elementAt(i);
                if (l.getCategory().equals(locationCategory))
                    l.removeCategory();
            }
            locationCategoryComboModel.removeElement(locationCategory);
            locationCategoriesList.setSelectedIndex((indexCat > 0) ? indexCat - 1 : indexCat);
        }
    }

    private void addLocationCategory() {
        LocationCategory locationCategory = new LocationCategory("Название категории");
        Location.addNewCategory(locationCategory);
        locationCategoriesListModel.addElement(locationCategory);
        locationCategoryComboModel.addElement(locationCategory);
    }

    private void deleteSlot() {
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
        int indexI = characterItemsList.getSelectedIndex();
        if (indexI >= 0 && indexC >= 0) {
            GameCharacter selectedC = charactersListModel.getElementAt(indexC);
            Item selectedItem = characterItemsListModel.getElementAt(indexI);
            selectedC.getInventory().remove(selectedItem);
            characterItemsListModel.removeElement(selectedItem);
            if (indexI == 0)
                deleteItemFromCharButton.setEnabled(false);
            characterItemsList.setSelectedIndex((indexI > 0) ? indexI - 1 : indexI);
        }
    }

    private void addItemToCharacter() {
        int indexC = charactersList.getSelectedIndex();
        int indexI = characterTabItemsList.getSelectedIndex();
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
            if (!scriptName.startsWith("_on")) {
                Item item = itemsListModel.getElementAt(indexI);
                itemScriptListModel.removeElementAt(indexS);
                item.removeScript(scriptName);
            }
        }
    }

    private void addItemScript() {
        int index = itemsList.getSelectedIndex();
        if (index >= 0) {
            Item item = itemsListModel.getElementAt(index);
            String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
            if (scriptName != null) {
                Script script = new Script("", true);
                item.setScript(scriptName, script);
                itemScriptListModel.addElement(scriptName);
            }
        }
    }

    private void saveItemScript() {
        int indexS = itemScriptsList.getSelectedIndex();
        int indexI = itemsList.getSelectedIndex();
        if (indexI >= 0 && indexS >= 0) {
            String scriptName = itemScriptListModel.getElementAt(indexS); //TODo: BUG:java.lang.ArrayIndexOutOfBoundsException: -1 (посисбле во всех других сэйв функциях?)
            Item item = itemsListModel.getElementAt(indexI);
            //item.setScript(scriptName, itemScriptText.getText());
            item.setScript(scriptName, new Script(itemScriptText.getText(), itemScriptEnabledBox.isSelected()));
        }
    }

    private void selectItemScript() {
        int indexI = itemsList.getSelectedIndex();
        int indexS = itemScriptsList.getSelectedIndex();
        if (indexS >= 0) {
            Item selectedItem = itemsListModel.getElementAt(indexI);
            String selectedScript = itemScriptListModel.getElementAt(indexS);
            //itemScriptText.setText(selectedItem.getScript(selectedScript));
            Script script = selectedItem.getScript(selectedScript);
            itemScriptText.setText(script.getText());
            itemScriptEnabledBox.setSelected(script.isEnabled());
        }
    }

    private void startGame() {
        if (!gamePath.isEmpty())
            new PlayerFrame(gamePath);
        else
            saveAs();
    }

    private void saveCharScript() {
        int indexS = characterScriptList.getSelectedIndex();
        int indexC = charactersList.getSelectedIndex();
        if (indexC >= 0 && indexS >= 0) {
            String scriptName = characterScriptListModel.getElementAt(indexS);
            GameCharacter character = charactersListModel.getElementAt(indexC);
            //character.setScript(scriptName, characterScriptText.getText());
            character.setScript(scriptName, new Script(characterScriptText.getText(), characterScriptEnabledBox.isSelected()));
        }
    }

    private void selectCharScript() {
        int index = characterScriptList.getSelectedIndex();
        if (index >= 0) {
            String scriptName = characterScriptListModel.getElementAt(index);
            int indexC = charactersList.getSelectedIndex();
            GameCharacter character = charactersListModel.elementAt(indexC);
            characterScriptText.setEnabled(true);
            Script script = character.getScript(scriptName);
            characterScriptText.setText(script.getText());
            characterScriptEnabledBox.setSelected(script.isEnabled());
        } else
            characterScriptText.setEnabled(false);
    }

    private void deleteCharScript() {
        int indexS = characterScriptList.getSelectedIndex();
        int indexC = charactersList.getSelectedIndex();
        if (indexS >= 0) {
            String scriptName = characterScriptListModel.getElementAt(indexS);
            GameCharacter character = charactersListModel.getElementAt(indexC);
            if (!scriptName.startsWith("_on")) {
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
            if (scriptName != null) {
                Script script = new Script("", true);
                character.setScript(scriptName, script);
                characterScriptListModel.addElement(scriptName);
            }
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
        selected.setLocation((Location) characterLocationModel.getSelectedItem());
        selected.setDescription(charDescriptionArea.getText());
        selected.setCategory((CharacterCategory) characterCategoryComboModel.getSelectedItem());
    }

    private void selectChar() {
        int index = charactersList.getSelectedIndex();
        if (index != -1) {
            GameCharacter selected = charactersListModel.getElementAt(index);
            charNameFiled.setText(selected.getName());
            charIdField.setText(String.valueOf(selected.getId()));
            characterLocationModel.setSelectedItem(selected.getLocation());
            characterScriptListModel.removeAllElements();
            selected.getScripts().keySet().forEach(characterScriptListModel::addElement);
            charDescriptionArea.setText(selected.getDescription());
            characterCategoryCombo.setSelectedItem(selected.getCategory());
        }
    }

    private void setCharsFromItemsEnabled() {
        boolean enabled = charactersList.getSelectedIndex() >= 0;
        charNameFiled.setEnabled(enabled);
        characterTabItemsList.setEnabled(enabled);
        characterItemsList.setEnabled(enabled);
        characterLocationCombo.setEnabled(enabled);
        charSaveButton.setEnabled(enabled);
        addCharacterButton.setEnabled(enabled);
        charDescriptionArea.setEnabled(enabled);
    }

    private void addScriptToLocation() {
        int index = locationsList.getSelectedIndex();
        if (index >= 0) {
            Location location = locationsListModel.elementAt(index);
            String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
            if (scriptName != null) {
                Script script = new Script("", true);
                location.setScript(scriptName, script);
                locationScriptListModel.addElement(scriptName);
            }
        }
    }

    private void deleteLocationScript() {
        int indexS = locationScriptsList.getSelectedIndex();
        int indexL = locationsList.getSelectedIndex();
        if (indexS >= 0) {
            String scriptName = locationScriptListModel.getElementAt(indexS);
            if (!scriptName.startsWith("_on")) {
                Location location = locationsListModel.elementAt(indexL);
                location.removeScript(scriptName);
                locationScriptListModel.remove(indexS);
            }
        }
    }

    private void saveLocationScript() {
        int indexS = locationScriptsList.getSelectedIndex();
        int indexL = locationsList.getSelectedIndex();
        if (indexS >= 0 && indexL >= 0) {
            String scriptName = locationScriptListModel.getElementAt(indexS);
            Location location = locationsListModel.elementAt(indexL);
            //location.setScript(scriptName, locationScriptText.getText());
            location.setScript(scriptName, new Script(locationScriptText.getText(), locationScriptEnabledBox.isSelected()));
        }
    }

    private void selectLocationScript() {
        int index = locationScriptsList.getSelectedIndex();
        if (index >= 0) {
            String scriptName = locationScriptListModel.getElementAt(index);
            int indexL = locationsList.getSelectedIndex();
            Location location = locationsListModel.elementAt(indexL);
            locationScriptText.setEnabled(true);
            //locationScriptText.setText(location.getScript(script));
            Script script = location.getScript(scriptName);
            locationScriptText.setText(script.getText());
            locationScriptEnabledBox.setSelected(script.isEnabled());
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
        if (itemTypeCombo.getModel().getSelectedItem() == ItemTypes.EQUIPPABLE) {
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
                deleteItemFromLocButton.setEnabled(false);
            locationItemsList.setSelectedIndex((indexItem > 0) ? indexItem - 1 : indexItem);
        }
    }

    private void saveSelectedItem() {
        int index = itemsList.getSelectedIndex();
        Item selected = itemsListModel.getElementAt(index);
        selected.setName(itemName.getText());
        selected.setDescription(itemDescription.getText());
        ItemTypes type = (ItemTypes) itemTypeCombo.getSelectedItem();
        selected.setType(type);
        selected.setCategory((ItemCategory) itemCategoryCombo.getSelectedItem());
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
            itemTypeCombo.setSelectedItem(selected.getType());
            itemIdField.setText(String.valueOf(selected.getId()));
            slotCombo.setSelectedItem(selected.getEquipmentSlot());
            itemScriptListModel.clear();
            itemCategoryCombo.setSelectedItem(selected.getCategory());
            //selected.getScripts().keySet().forEach(itemScriptListModel::addElement);
            selected.getScripts().keySet().forEach(itemScriptListModel::addElement);
            itemDescription.setText(selected.getDescription());
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
        itemTypeCombo.setEnabled(enabled);
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
        int response = fc.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            gamePath = fc.getSelectedFile().getPath();

            loadGame();
        }
    }

    private void loadGame() {
        System.out.println("Opening file " + gamePath);
        SaveFile saveFile = SaveFile.open(gamePath);
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
            characterLocationModel.addElement(l);
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

        saveFile.getCharacterCategories().forEach(charCategoriesListModel::addElement);
        saveFile.getCharacterCategories().forEach(GameCharacter::addNewCategory);
        saveFile.getCharacterCategories().forEach(characterCategoryComboModel::addElement);
        saveFile.getItemCategories().forEach(itemCotegoriesListModel::addElement);
        saveFile.getItemCategories().forEach(Item::addNewCategory);
        saveFile.getItemCategories().forEach(itemCategoryComboModel::addElement);
        saveFile.getLocationCategories().forEach(locationCategoriesListModel::addElement);
        saveFile.getLocationCategories().forEach(Location::addNewCategory);
        saveFile.getLocationCategories().forEach(locationCategoryComboModel::addElement);

        gameName.setText(saveFile.getGameName());
        gameStartMessage.setText(saveFile.getGameStartMessage());

        initScriptText.setText(saveFile.getInitScript());

        Location.setCategories(saveFile.getLocationCategories());
        Item.setCategories(saveFile.getItemCategories());
        GameCharacter.setCategories(saveFile.getCharacterCategories());
        updatePlayerTab();
    }

    private void updatePlayerTab() {
        playerName.setText(player.getName());
        playerLocation.setSelectedItem(player.getLocation());
        player.getInventory().forEach(playerItemsListModel::addElement);
        playerDescriptionArea.setText(player.getDescription());
        player.getScripts().keySet().stream().filter(scriptName -> !scriptName.startsWith("_on")).forEach(playerScriptListModel::addElement);
    }


    private void menuSaveGame() {
        if (gamePath.isEmpty()) {
            JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
            FileFilter ff = new FileNameExtensionFilter("TheSUD game", "sud");
            fc.setFileFilter(ff);
            int response = fc.showSaveDialog(this);
            if (response == JFileChooser.APPROVE_OPTION)
                gamePath = fc.getSelectedFile().getPath();
            else
                return;
        }
        saveGame();
    }

    private void saveGame() {
        if (player.getLocation() != null) {
            System.out.println("Saving to " + gamePath);
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
            saveFile.setCharacterCategories(GameCharacter.getCategories());
            saveFile.setItemCategories(Item.getCategories());
            saveFile.setLocationCategories(Location.getCategories());
            saveFile.setSlotNames(slotsNames);
            saveFile.setInitScript(initScriptText.getText());
            saveFile.save(gamePath);
        } else
            JOptionPane.showMessageDialog(this, "Выберите стартовую локацию игрока!");
    }

    private void savePlayer() {
        player.setName(playerName.getText());
        Location pLocation = (Location) playerLocation.getSelectedItem();
        player.setLocation(pLocation);
        player.setDescription(playerDescriptionArea.getText());
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
        characterLocationModel.removeElement(selected);
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
        selectedLocation.setCategory((LocationCategory) locationCategoryComboModel.getSelectedItem());
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
            locationScriptListModel.removeAllElements();
            //selected.getScripts().keySet().forEach(locationScriptListModel::addElement);
            selected.getScripts().keySet().forEach(locationScriptListModel::addElement);
            selected.getInventory().forEach(locationItemsListModel::addElement);
            availableButton.setSelected(selected.isAvailable());
            notAvailableButton.setSelected(!selected.isAvailable());
            locationCategoryCombo.setSelectedItem(selected.getCategory());
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
        characterLocationModel.addElement(newLocation);
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
        saveLocScriptButton.setEnabled(enabled);
        availableButton.setEnabled(enabled);
        notAvailableButton.setEnabled(enabled);
    }
}
