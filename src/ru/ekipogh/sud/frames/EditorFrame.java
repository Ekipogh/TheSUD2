package ru.ekipogh.sud.frames;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.js.JavaScriptLanguageSupport;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import ru.ekipogh.sud.*;
import ru.ekipogh.sud.behavior.*;
import ru.ekipogh.sud.objects.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ekipogh on 23.04.2015.
 * licensed under WTFPL
 */
public class EditorFrame extends JFrame {
    private enum OBJECTYPES {
        LOCATION, CHARACTER, PLAYER, ITEM, LOCATIONCATEGORY, CHARACTERCATEGORY, ITEMCATEGORY, PLAYERCATEGORY
    }

    private final FindDialog findDialog;
    private final DefaultComboBoxModel<Location> northModel;
    private final DefaultComboBoxModel<Location> southModel;
    private final DefaultComboBoxModel<Location> eastModel;
    private final DefaultComboBoxModel<Location> westModel;
    private final DefaultComboBoxModel<Location> upModel;
    private final DefaultComboBoxModel<Location> downModel;
    private final DefaultComboBoxModel<Location> playerLocationModel;
    private final DefaultComboBoxModel<String> slotNamesModel;
    private final DefaultComboBoxModel<Location> characterLocationModel;
    private final DefaultListModel<GameCharacter> charactersListModel;
    final DefaultListModel<Item> itemsListModel;
    private final DefaultListModel<SudPair<Item, Integer>> locationItemsListModel;
    private final DefaultListModel<SudPair<Item, Integer>> characterItemsListModel;
    private final DefaultListModel<SudPair<Item, Integer>> playerItemsListModel;
    private final DefaultListModel<LocationCategory> locationsCategoriesListModel;
    private final DefaultListModel<String> locationCategoryScriptsListModel;
    private final DefaultListModel<ItemCategory> itemsCategoriesListModel;
    private final DefaultListModel<String> itemCategoryScriptsListModel;
    private final DefaultListModel<CharacterCategory> characterCategoriesListModel;
    private final DefaultListModel<String> charCategoryScriptsListModel;
    private final DefaultListModel<String> playerScriptListModel;
    private final DefaultListModel<GameObjectCategory> itemCategoriesListModel;
    private final DefaultListModel<GameObjectCategory> playerCategoriesListModel;
    private final DefaultListModel<Location> locationsListModel;
    private final DefaultListModel<String> locationScriptListModel;
    private final DefaultListModel<String> characterScriptListModel;
    private final DefaultListModel<String> itemScriptListModel;
    private final DefaultTableModel equipTableModel;
    private final DefaultListModel<String> commonScriptsListModel;
    private final DefaultTableModel playerEquipmentTableModel;
    private final DefaultTableModel characterEquipmentTableModel;
    private final DefaultListModel<SudTimer> timersListModel;
    private final DefaultTableModel itemValuesTableModel;
    private final DefaultTableModel locationValuesTableModel;
    private final DefaultTableModel characterValuesTableModel;
    private final DefaultTableModel playerValuesTableModel;
    private String gameFolder;
    private JPanel rootPanel;
    private JList<Location> locationsList;
    private JTextField locName;
    private JTextField locID;
    private JTextArea locDescription;
    private JComboBox<Location> northComboBox;
    private JComboBox<Location> southComboBox;
    private JComboBox<Location> eastComboBox;
    private JComboBox<Location> westComboBox;
    private JButton addLocationButton;
    private JButton deleteLocButton;
    private JButton saveLocButton;
    private JTextField playerName;
    private JComboBox<Location> playerLocation;
    private JButton savePlayerButton;
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
    private JList<SudPair<Item, Integer>> locationItemsList;
    private JButton addItemToLocationButton;
    private JButton deleteItemFromLocButton;
    private JComboBox<String> slotCombo;
    private JTable equipTable;
    private JButton addSlotButton;
    private JButton deleteSlotButton;
    private JButton saveSlotsButton;
    private JList<String> locationScriptsList;
    private RSyntaxTextArea locationScriptText;
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
    private JList<String> itemScriptsList;
    private RSyntaxTextArea itemScriptText;
    private JButton saveItemScriptButton;
    private JButton addItemScriptButton;
    private JButton deleteItemScriptButton;
    private JList<Item> characterTabItemsList;
    private JList<SudPair<Item, Integer>> characterItemsList;
    private JButton addItemToCharacterButton;
    private JButton deleteItemFromCharButton;
    private JList<Item> playerTabItemsList;
    private JList<SudPair<Item, Integer>> playerItemsList;
    private JButton addItemToPlayerButton;
    private JButton deleteItemFromPlayerButton;
    private JList<LocationCategory> locationsCategoriesList;
    private JButton addLocationCategoryButton;
    private JButton deleteLocationCategoryButton;
    private JList<String> locationCategoryScriptsList;
    private JTextField locationCategoryNameFiled;
    private RSyntaxTextArea locationCategoryScriptText;
    private JButton saveLocationCategoryScriptButton;
    private JList<ItemCategory> itemsCategoriesList;
    private JList<String> itemCategoryScriptsList;
    private RSyntaxTextArea itemCategoryScriptText;
    private JButton addItemCategoryButton;
    private JButton deleteItemCategoryButton;
    private JButton saveItemCategoryScriptButton;
    private JList<CharacterCategory> characterCategoriesList;
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
    private JCheckBox northEnabledBox;
    private JCheckBox southEnabledBox;
    private JCheckBox eastEnabledBox;
    private JCheckBox westEnabledBox;
    private JComboBox<Location> upComboBox;
    private JCheckBox upEnabledBox;
    private JComboBox<Location> downComboBox;
    private JCheckBox downEnabledBox;
    private JList<CharacterCategory> characterAllCategoriesList;
    private JList<GameObjectCategory> characterCategoryList;
    private JButton addCategoryToCharacterButton;
    private JButton deleteCategoryFromCharacterButton;
    private JList<LocationCategory> locationAllCategoriesList;
    private JList<GameObjectCategory> locationCategoriesList;
    private JButton addCategoryToLocationButton;
    private JButton deleteCategoryFromLocationButton;
    private JList<ItemCategory> itemAllCategoriesList;
    private JList<GameObjectCategory> itemCategoriesList;
    private JButton addCategoryToItemButton;
    private JButton deleteCategoryFromItemButton;
    private JCheckBox isContainerBox;
    private JCheckBox isLockedBox;
    private JList<String> commonScriptsList;
    private RSyntaxTextArea commonScriptText;
    private JButton saveCommonScriptButton;
    private JButton addSomeItemsToLocationButton;
    private JButton deleteSomeItemsFromLocationButton;
    private JButton addSomeItemsToCharacterButton;
    private JButton deleteSomeItemsFromCharacterButton;
    private JButton addSomeItemsToPlayerButton;
    private JButton deleteSomeItemsFromPlayerButton;
    private JButton locationContainerButton;
    private JButton characterContainerButton;
    private JButton playerContainerButton;
    private JPanel initScriptPanel;
    private JPanel characterScriptPanel;
    private JPanel locationScriptPanel;
    private JPanel itemScriptPanel;
    private JPanel playerScriptPanel;
    private JPanel locationCategoryScriptPanel;
    private JPanel itemCategoryScriptPanel;
    private JPanel characterCategoryScriptPanel;
    private JPanel commonScriptPanel;
    private JTable playerEquipmentTable;
    private JList<Item> playerInventoryList;
    private JList<Item> characterInventoryList;
    private JTable characterEquipmentTable;
    private JButton addLocationCategoriesFolderButton;
    private JButton renameLocationCategoryScriptButton;
    private JButton addItemCategoriesFolderButton;
    private JButton renameItemCategoryScriptButton;
    private JButton addCharacterCategoriesFolderButton;
    private JButton renameCharacterCategoryScriptButton;
    private JList<CharacterCategory> playerAllCategoriesList;
    private JList<GameObjectCategory> playerCategoriesList;
    private JButton addCategoryToPlayerButton;
    private JButton deleteCategoryFromPlayerButton;
    private JTextField idFiled;
    private JButton getButton;
    private JButton setButton;
    private JButton clearInventoryButton;
    private JList<SudTimer> timersList;
    private JTextField timerNameField;
    private JTextField timerStepField;
    private RSyntaxTextArea timerScriptText;
    private JPanel timerScriptPanel;
    private JButton saveTimerButton;
    private JButton addTimerButton;
    private JButton removeTimerButton;
    private JTable itemValuesTable;
    private JButton addItemValueButton;
    private JButton deleteItemValueButton;
    private JButton saveItemValuesButton;
    private JTable locationValuesTable;
    private JButton saveLocationValuesButton;
    private JButton addLocationValueButton;
    private JButton deleteLocationValueButton;
    private JTable characterValuesTable;
    private JButton saveCharacterValuesButton;
    private JButton addCharacterValueButton;
    private JButton deleteCharacterValueButton;
    private JTable playerValuesTable;
    private JButton savePlayerValuesButton;
    private JButton addPlayerValueButton;
    private JButton deletePlayerValueButton;
    private JTree characterBehaviorTree;
    private JTabbedPane mainTabbedPain;
    private JTree playerBehaviorTree;
    private JTree characterCategoryBehaviorTree;
    private RSyntaxTextArea characterBehaviorScriptArea;
    private JPanel characterBehaviorPanel;
    private final ReplaceDialog replaceDialog;
    private RSyntaxTextArea characterCategoryBehaviorScriptArea;
    private RSyntaxTextArea playerBehaviorScriptArea;
    private JPanel playerBehaviorScriptPanel;
    private JPanel characterCategoryBehaviorScriptPanel;
    private JButton saveCharacterCategoryBehaviorScriptButton;
    private JTabbedPane playerTab;
    private JTabbedPane characterTab;
    private DefaultListModel<GameObjectCategory> characterCategoryListModel;
    private DefaultListModel<GameObjectCategory> locationCategoryListModel;
    private HashMap<String, Script> commonScripts;
    private GameCharacter player;
    private String gamePath;
    private RSyntaxTextArea selectedRSyntaxArea; //для поиска

    public EditorFrame(String gamePath) {
        super("Редактор");

        Sequencer.reset();
        player = new GameCharacter("Безымянный");

        this.gamePath = gamePath;

        setContentPane(rootPanel);

        commonScripts = new HashMap<>();
        commonScripts.put("_onPlayerMoves", new Script("", true));
        commonScripts.put("_onPlayerMovesNorth", new Script("", true));
        commonScripts.put("_onPlayerMovesSouth", new Script("", true));
        commonScripts.put("_onPlayerMovesEast", new Script("", true));
        commonScripts.put("_onPlayerMovesWest", new Script("", true));
        commonScripts.put("_onPlayerMovesUp", new Script("", true));
        commonScripts.put("_onPlayerMovesDown", new Script("", true));
        commonScripts.put("_onPlayerTakesItem", new Script("", true));
        commonScripts.put("_onPlayerUsesItem", new Script("", true));
        commonScripts.put("_onPlayerDropsItem", new Script("", true));
        commonScripts.put("_onPlayerEquipsItem", new Script("", true));
        commonScripts.put("_onPlayerUnequipsItem", new Script("", true));
        commonScripts.put("_onPlayerUnlocksItem", new Script("", true));
        commonScripts.put("_onPlayerStashesItem", new Script("", true));
        commonScripts.put("_onMakePlayer", new Script("", true));

        //модели листов
        charactersListModel = new DefaultListModel<>();
        charactersList.setModel(charactersListModel);

        characterScriptListModel = new DefaultListModel<>();
        characterScriptList.setModel(characterScriptListModel);

        characterItemsListModel = new DefaultListModel<>();
        characterItemsList.setModel(characterItemsListModel);

        characterCategoriesListModel = new DefaultListModel<>();
        characterCategoriesList.setModel(characterCategoriesListModel);
        characterAllCategoriesList.setModel(characterCategoriesListModel);

        playerAllCategoriesList.setModel(characterCategoriesListModel);
        playerCategoriesListModel = new DefaultListModel<>();
        playerCategoriesList.setModel(playerCategoriesListModel);

        characterCategoryListModel = new DefaultListModel<>();
        characterCategoryList.setModel(characterCategoryListModel);

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

        locationsCategoriesListModel = new DefaultListModel<>();
        locationsCategoriesList.setModel(locationsCategoriesListModel);
        locationAllCategoriesList.setModel(locationsCategoriesListModel);

        locationCategoryScriptsListModel = new DefaultListModel<>();
        locationCategoryScriptsList.setModel(locationCategoryScriptsListModel);

        itemScriptListModel = new DefaultListModel<>();
        itemScriptsList.setModel(itemScriptListModel);

        itemsListModel = new DefaultListModel<>();
        itemsList.setModel(itemsListModel);

        itemsCategoriesListModel = new DefaultListModel<>();
        itemsCategoriesList.setModel(itemsCategoriesListModel);
        itemAllCategoriesList.setModel(itemsCategoriesListModel);

        itemCategoryScriptsListModel = new DefaultListModel<>();
        itemCategoryScriptsList.setModel(itemCategoryScriptsListModel);

        locationCategoryListModel = new DefaultListModel<>();
        locationCategoriesList.setModel(locationCategoryListModel);

        itemCategoriesListModel = new DefaultListModel<>();
        itemCategoriesList.setModel(itemCategoriesListModel);

        locationTabItemsList.setModel(itemsListModel);
        characterTabItemsList.setModel(itemsListModel);
        playerTabItemsList.setModel(itemsListModel);

        commonScriptsListModel = new DefaultListModel<>();
        commonScriptsList.setModel(commonScriptsListModel);

        timersListModel = new DefaultListModel<>();
        timersList.setModel(timersListModel);
        //Заполняем commonScriptsList
        commonScripts.keySet().forEach(commonScriptsListModel::addElement);

        //поля скриптов
        AutoCompletion acChar = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsChar = new JavaScriptLanguageSupport();
        characterScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        characterScriptText.setCodeFoldingEnabled(true);
        characterScriptText.setMarkOccurrences(true);
        acChar.install(characterScriptText);
        lsChar.install(characterScriptText);
        characterScriptPanel.add(new ErrorStrip(characterScriptText), BorderLayout.LINE_END);

        AutoCompletion acCharCat = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsCharCat = new JavaScriptLanguageSupport();
        charCategoryScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        charCategoryScriptText.setCodeFoldingEnabled(true);
        charCategoryScriptText.setMarkOccurrences(true);
        acCharCat.install(charCategoryScriptText);
        lsCharCat.install(charCategoryScriptText);
        characterCategoryScriptPanel.add(new ErrorStrip(charCategoryScriptText), BorderLayout.LINE_END);

        AutoCompletion acPl = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsPl = new JavaScriptLanguageSupport();
        playerScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        playerScriptText.setCodeFoldingEnabled(true);
        playerScriptText.setMarkOccurrences(true);
        acPl.install(playerScriptText);
        lsPl.install(playerScriptText);
        playerScriptPanel.add(new ErrorStrip(playerScriptText), BorderLayout.LINE_END);

        AutoCompletion acLoc = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsLoc = new JavaScriptLanguageSupport();
        locationScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        locationScriptText.setCodeFoldingEnabled(true);
        locationScriptText.setMarkOccurrences(true);
        acLoc.install(locationScriptText);
        lsLoc.install(locationScriptText);
        locationScriptPanel.add(new ErrorStrip(locationScriptText), BorderLayout.LINE_END);

        AutoCompletion acLocCat = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsLocCat = new JavaScriptLanguageSupport();
        locationCategoryScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        locationCategoryScriptText.setCodeFoldingEnabled(true);
        locationCategoryScriptText.setMarkOccurrences(true);
        acLocCat.install(locationCategoryScriptText);
        lsLocCat.install(locationCategoryScriptText);
        locationCategoryScriptPanel.add(new ErrorStrip(locationCategoryScriptText), BorderLayout.LINE_END);

        AutoCompletion acItem = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsItem = new JavaScriptLanguageSupport();
        itemScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        itemScriptText.setCodeFoldingEnabled(true);
        itemScriptText.setMarkOccurrences(true);
        acItem.install(itemScriptText);
        lsItem.install(itemScriptText);
        itemScriptPanel.add(new ErrorStrip(itemScriptText), BorderLayout.LINE_END);

        AutoCompletion acItemCat = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsItemCat = new JavaScriptLanguageSupport();
        itemCategoryScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        itemCategoryScriptText.setCodeFoldingEnabled(true);
        itemCategoryScriptText.setMarkOccurrences(true);
        acItemCat.install(itemCategoryScriptText);
        lsItemCat.install(itemCategoryScriptText);
        itemCategoryScriptPanel.add(new ErrorStrip(itemCategoryScriptText), BorderLayout.LINE_END);

        AutoCompletion acInit = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsInit = new JavaScriptLanguageSupport();
        initScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        initScriptText.setCodeFoldingEnabled(true);
        initScriptText.setMarkOccurrences(true);
        acInit.install(initScriptText);
        lsInit.install(initScriptText);
        initScriptPanel.add(new ErrorStrip(initScriptText), BorderLayout.LINE_END);

        AutoCompletion acCommon = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsCommon = new JavaScriptLanguageSupport();
        commonScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        commonScriptText.setCodeFoldingEnabled(true);
        commonScriptText.setMarkOccurrences(true);
        commonScriptText.setMarkOccurrences(true);
        acCommon.install(commonScriptText);
        lsCommon.install(commonScriptText);
        commonScriptPanel.add(new ErrorStrip(commonScriptText), BorderLayout.LINE_END);

        AutoCompletion acTimer = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsTimer = new JavaScriptLanguageSupport();
        timerScriptText.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        timerScriptText.setCodeFoldingEnabled(true);
        timerScriptText.setMarkOccurrences(true);
        timerScriptText.setMarkOccurrences(true);
        acTimer.install(timerScriptText);
        lsTimer.install(timerScriptText);
        timerScriptPanel.add(new ErrorStrip(timerScriptText), BorderLayout.LINE_END);

        AutoCompletion acCharBeh = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsCharBeh = new JavaScriptLanguageSupport();
        characterBehaviorScriptArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        characterBehaviorScriptArea.setCodeFoldingEnabled(true);
        characterBehaviorScriptArea.setMarkOccurrences(true);
        characterBehaviorScriptArea.setMarkOccurrences(true);
        acCharBeh.install(characterBehaviorScriptArea);
        lsCharBeh.install(characterBehaviorScriptArea);
        characterBehaviorPanel.add(new ErrorStrip(characterBehaviorScriptArea), BorderLayout.LINE_END);

        AutoCompletion acPlayerBeh = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsPlayerBeh = new JavaScriptLanguageSupport();
        playerBehaviorScriptArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        playerBehaviorScriptArea.setCodeFoldingEnabled(true);
        playerBehaviorScriptArea.setMarkOccurrences(true);
        playerBehaviorScriptArea.setMarkOccurrences(true);
        acPlayerBeh.install(playerBehaviorScriptArea);
        lsPlayerBeh.install(playerBehaviorScriptArea);
        playerBehaviorScriptPanel.add(new ErrorStrip(playerBehaviorScriptArea), BorderLayout.LINE_END);

        AutoCompletion acCharCatBeh = new AutoCompletion(new DefaultCompletionProvider());
        LanguageSupport lsCharCatBeh = new JavaScriptLanguageSupport();
        characterCategoryBehaviorScriptArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        characterCategoryBehaviorScriptArea.setCodeFoldingEnabled(true);
        characterCategoryBehaviorScriptArea.setMarkOccurrences(true);
        characterCategoryBehaviorScriptArea.setMarkOccurrences(true);
        acCharCatBeh.install(characterCategoryBehaviorScriptArea);
        lsCharCatBeh.install(characterCategoryBehaviorScriptArea);
        characterCategoryBehaviorScriptPanel.add(new ErrorStrip(characterCategoryBehaviorScriptArea), BorderLayout.LINE_END);

        //модели комбобоксов
        itemTypeCombo.setModel(new DefaultComboBoxModel<>(ItemTypes.values()));

        northModel = new DefaultComboBoxModel<>();
        southModel = new DefaultComboBoxModel<>();
        eastModel = new DefaultComboBoxModel<>();
        westModel = new DefaultComboBoxModel<>();
        upModel = new DefaultComboBoxModel<>();
        downModel = new DefaultComboBoxModel<>();

        playerLocationModel = new DefaultComboBoxModel<>();
        characterLocationModel = new DefaultComboBoxModel<>();

        slotNamesModel = new DefaultComboBoxModel<>();

        northModel.addElement(null);
        southModel.addElement(null);
        eastModel.addElement(null);
        westModel.addElement(null);
        upModel.addElement(null);
        downModel.addElement(null);

        playerLocationModel.addElement(null);
        characterLocationModel.addElement(null);

        northComboBox.setModel(northModel);
        southComboBox.setModel(southModel);
        eastComboBox.setModel(eastModel);
        westComboBox.setModel(westModel);
        upComboBox.setModel(upModel);
        downComboBox.setModel(downModel);

        characterLocationCombo.setModel(characterLocationModel);
        playerLocation.setModel(playerLocationModel);

        slotCombo.setModel(slotNamesModel);

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
                    String iconPath = gameFolder + "\\" + aValue;
                    ImageIcon icon = new ImageIcon(iconPath);
                    equipTableModel.setValueAt(icon, row, 1);
                    Utils.updateRowHeights(equipTable);
                }
            }
        };
        equipTable.setModel(equipTableModel);

        equipTableModel.addColumn("Путь к иконке");
        equipTableModel.addColumn("Иконка");
        equipTableModel.addColumn("Название");

        equipTable.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());

        itemValuesTableModel = new DefaultTableModel();
        itemValuesTable.setModel(itemValuesTableModel);
        itemValuesTableModel.addColumn("Название");
        itemValuesTableModel.addColumn("Значение");
        itemValuesTable.setShowGrid(true);

        locationValuesTableModel = new DefaultTableModel();
        locationValuesTable.setModel(locationValuesTableModel);
        locationValuesTableModel.addColumn("Название");
        locationValuesTableModel.addColumn("Значение");
        locationValuesTable.setShowGrid(true);

        characterValuesTableModel = new DefaultTableModel();
        characterValuesTable.setModel(characterValuesTableModel);
        characterValuesTableModel.addColumn("Название");
        characterValuesTableModel.addColumn("Значение");
        characterValuesTable.setShowGrid(true);

        playerValuesTableModel = new DefaultTableModel();
        playerValuesTable.setModel(playerValuesTableModel);
        playerValuesTableModel.addColumn("Название");
        playerValuesTableModel.addColumn("Значение");
        playerValuesTable.setShowGrid(true);

        //заполнение таблицы экипировки
        fillEquipmentTable();

        //меню окна
        //Файл
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

        //Поиск
        findDialog = new FindDialog();
        replaceDialog = new ReplaceDialog();

        JMenu menuSearch = new JMenu("Поиск");
        JMenuItem findMenu = new JMenuItem("Найти");
        JMenuItem replaceMenu = new JMenuItem("Заменить");
        JMenuItem nextMenu = new JMenuItem("Найти далее", KeyEvent.VK_F3);
        menuSearch.add(findMenu);
        menuSearch.add(replaceMenu);
        menuSearch.add(nextMenu);
        menuBar.add(menuSearch);

        setJMenuBar(menuBar);

        //модели деревьев
        characterBehaviorTree.setModel(null);
        characterCategoryBehaviorTree.setModel(null);
        playerBehaviorTree.setModel(null);
        //листенеры
        //листенеры конопок
        saveCharacterCategoryBehaviorScriptButton.addActionListener(e -> saveCharacterCategoryBehaviorScript());

        addItemValueButton.addActionListener(e -> addItemValue());

        deleteItemValueButton.addActionListener(e -> deleteItemValue());

        saveItemValuesButton.addActionListener(e -> saveItemValues());

        addLocationValueButton.addActionListener(e -> addLocationValue());

        deleteLocationValueButton.addActionListener(e -> deleteLocationValue());

        saveLocationValuesButton.addActionListener(e -> saveLocationValues());

        addCharacterValueButton.addActionListener(e -> addCharacterValue());

        deleteCharacterValueButton.addActionListener(e -> deleteCharacterValue());

        saveCharacterValuesButton.addActionListener(e -> saveCharacterValues());

        addPlayerValueButton.addActionListener(e -> addPlayerValue());

        deletePlayerValueButton.addActionListener(e -> deletePlayerValue());

        savePlayerValuesButton.addActionListener(e -> savePlayerValues());

        addTimerButton.addActionListener(e -> addTimer());

        removeTimerButton.addActionListener(e -> removeTimer());

        getButton.addActionListener(e -> idFiled.setText(String.valueOf(Sequencer.getCurrentId())));

        setButton.addActionListener(e -> Sequencer.setID(Integer.parseInt(idFiled.getText())));

        deleteCategoryFromPlayerButton.addActionListener(e -> deleteCategoryFromPlayer());

        addCategoryToPlayerButton.addActionListener(e -> addCategoryToPlayer());

        renameLocationCategoryScriptButton.addActionListener(e -> renameLocationCategoryScript());

        renameItemCategoryScriptButton.addActionListener(e -> renameItemCategoryScript());

        renameCharacterCategoryScriptButton.addActionListener(e -> renameCharacterCategoryScript());

        addLocationCategoriesFolderButton.addActionListener(e -> addCategoriesFolder(1));

        addItemCategoriesFolderButton.addActionListener(e -> addCategoriesFolder(2));

        addCharacterCategoriesFolderButton.addActionListener(e -> addCategoriesFolder(3));

        locationContainerButton.addActionListener(e -> showContainerFrame(OBJECTYPES.LOCATION));

        characterContainerButton.addActionListener(e -> showContainerFrame(OBJECTYPES.CHARACTER));

        playerContainerButton.addActionListener(e -> showContainerFrame(OBJECTYPES.PLAYER));

        addSomeItemsToPlayerButton.addActionListener(e -> addSomeItemsToPlayer());

        deleteSomeItemsFromPlayerButton.addActionListener(e -> deleteSomeItemsFromPlayer());

        saveTimerButton.addActionListener(e -> saveTimer());

        addSomeItemsToCharacterButton.addActionListener(e -> addSomeItemsToCharacter());

        deleteSomeItemsFromCharacterButton.addActionListener(e -> deleteSomeItemsFromCharacter());

        addSomeItemsToLocationButton.addActionListener(e -> addSomeItemsToLocation());

        deleteSomeItemsFromLocationButton.addActionListener(e -> deleteSomeItemsFromLocation());

        saveCommonScriptButton.addActionListener(e -> saveCommonScript());

        saveCommonScriptButton.setMnemonic(KeyEvent.VK_ENTER);

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
        savePlayerScriptButton.setMnemonic(KeyEvent.VK_S);

        deletePlayerScriptButton.addActionListener(e -> deletePlayerScript());

        addPlayerScriptButton.addActionListener(e -> addPlayerScript());

        saveCharacterCategoryScriptButton.addActionListener(e -> saveCharCategoryScript());

        saveCharacterCategoryScriptButton.setMnemonic(KeyEvent.VK_ENTER);

        deleteCharCategoryScriptButton.addActionListener(e -> deleteCharCategoryScript());

        deleteCharCategoryButton.addActionListener(e -> deleteCharCategory());

        deleteItemCategoryScriptButton.addActionListener(e -> deleteItemCategoryScript());

        deleteLocationCategoryScriptButton.addActionListener(e -> deleteLocationCategoryScript());

        addLocationCategoryScriptButton.addActionListener(e -> addLocationCategoryScript());

        saveItemCategoryScriptButton.addActionListener(e -> saveItemCategoryScript());
        saveItemCategoryScriptButton.setMnemonic(KeyEvent.VK_ENTER);

        saveLocButton.addActionListener(e -> saveSelectedLocation());
        saveLocButton.setMnemonic(KeyEvent.VK_ENTER);

        deleteLocButton.addActionListener(e -> deleteSelectedLocation());

        savePlayerButton.addActionListener(e -> savePlayer());
        savePlayerButton.setMnemonic(KeyEvent.VK_ENTER);

        deleteItemButton.addActionListener(e -> deleteSelectedItem());

        saveItemButton.addActionListener(e -> saveSelectedItem());
        saveItemButton.setMnemonic(KeyEvent.VK_ENTER);

        deleteItemFromLocButton.addActionListener(e -> deleteItemFromLocation());

        addSlotButton.addActionListener(e -> addSlot());

        deleteSlotButton.addActionListener(e -> deleteSlot());

        saveSlotsButton.addActionListener(e -> saveSlotNames());
        saveSlotsButton.setMnemonic(KeyEvent.VK_ENTER);

        saveLocScriptButton.addActionListener(e -> saveLocationScript());
        saveLocScriptButton.setMnemonic(KeyEvent.VK_S);

        deleteScriptLocButton.addActionListener(e -> deleteLocationScript());

        addScriptLocButton.addActionListener(e -> addScriptToLocation());

        charSaveButton.addActionListener(e -> saveSelectedCharacter());
        charSaveButton.setMnemonic(KeyEvent.VK_ENTER);

        deleteCharButton.addActionListener(e -> deleteSelectedCharacter());

        deleteCharScriptButton.addActionListener(e -> deleteCharScript());

        saveCharScriptButton.addActionListener(e -> saveCharScript());
        saveCharScriptButton.setMnemonic(KeyEvent.VK_S);

        saveItemScriptButton.addActionListener(e -> saveItemScript());
        saveItemScriptButton.setMnemonic(KeyEvent.VK_S);

        deleteItemScriptButton.addActionListener(e -> deleteItemScript());

        deleteItemFromCharButton.addActionListener(e -> deleteItemFromCharacter());

        deleteItemFromPlayerButton.addActionListener(e -> deleteItemFromPlayer());

        deleteLocationCategoryButton.addActionListener(e -> deleteLocationCategory());

        saveLocationCategoryScriptButton.addActionListener(e -> saveLocationCategoryScript());
        saveLocationCategoryScriptButton.setMnemonic(KeyEvent.VK_ENTER);

        deleteItemCategoryButton.addActionListener(e -> deleteItemCategory());

        addCategoryToCharacterButton.addActionListener(e -> addCategoryToCharacter());

        deleteCategoryFromCharacterButton.addActionListener(e -> deleteCategoryFromCharacter());

        addCategoryToLocationButton.addActionListener(e -> addCategoryToLocation());

        deleteCategoryFromLocationButton.addActionListener(e -> deleteCategoryFromLocation());

        addCategoryToItemButton.addActionListener(e -> addCategoryToItem());

        deleteCategoryFromItemButton.addActionListener(e -> deleteCategoryFromItem());

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

        findMenu.addActionListener(e -> find());
        findMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));

        replaceMenu.addActionListener(e -> replace());
        replaceMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_MASK));

        nextMenu.addActionListener(e -> findNext());
        nextMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));

        //листенеры листов
        commonScriptsList.addListSelectionListener(e -> selectCommonScript());

        charCategoryScriptsList.addListSelectionListener(e -> selectCharCategoryScript());

        characterCategoriesList.addListSelectionListener(e -> selectCharacterCategory());

        itemCategoryScriptsList.addListSelectionListener(e -> selectItemCategoryScript());

        itemsCategoriesList.addListSelectionListener(e -> selectItemCategory());

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
                int indexI = ((JList) e.getSource()).getSelectedIndex();
                if (indexI >= 0) {
                    Item selected = itemsListModel.elementAt(indexI);
                    deleteItemFromLocButton.setEnabled(false);
                    addItemToLocationButton.setEnabled(true);
                    if (!selected.isContainer()) {
                        deleteSomeItemsFromLocationButton.setEnabled(false);
                        addSomeItemsToLocationButton.setEnabled(true);
                    }
                }
            }
        });

        locationItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                int indexI = ((JList) e.getSource()).getSelectedIndex();
                if (indexI >= 0) {
                    Item selected = locationItemsListModel.elementAt(indexI).getKey();
                    deleteItemFromLocButton.setEnabled(true);
                    addItemToLocationButton.setEnabled(false);
                    if (!selected.isContainer()) {
                        deleteSomeItemsFromLocationButton.setEnabled(true);
                        addSomeItemsToLocationButton.setEnabled(false);
                    }
                    locationContainerButton.setEnabled(selected.isContainer());
                }
            }
        });

        charactersList.addListSelectionListener(e -> {
            setCharsFromItemsEnabled();
            selectCharacter();
        });

        characterScriptList.addListSelectionListener(e -> selectCharScript());

        itemScriptsList.addListSelectionListener(e -> selectItemScript());

        characterTabItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteItemFromCharButton.setEnabled(false);
                    deleteSomeItemsFromCharacterButton.setEnabled(false);
                    addItemToCharacterButton.setEnabled(true);
                    addSomeItemsToCharacterButton.setEnabled(true);
                }
            }
        });
        characterItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                int indexI = ((JList) e.getSource()).getSelectedIndex();
                if (indexI >= 0) {
                    Item selected = characterItemsListModel.elementAt(indexI).getKey();
                    if (!selected.isContainer()) {
                        deleteSomeItemsFromCharacterButton.setEnabled(true);
                        addSomeItemsToLocationButton.setEnabled(false);
                    }
                    characterContainerButton.setEnabled(selected.isContainer());
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
                    deleteSomeItemsFromPlayerButton.setEnabled(false);
                    addItemToPlayerButton.setEnabled(true);
                    addSomeItemsToPlayerButton.setEnabled(true);
                }
            }
        });
        playerItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                int indexI = ((JList) e.getSource()).getSelectedIndex();
                if (indexI >= 0) {
                    Item selected = playerItemsListModel.elementAt(indexI).getKey();
                    if (!selected.isContainer()) {
                        deleteSomeItemsFromPlayerButton.setEnabled(true);
                        addSomeItemsToPlayerButton.setEnabled(false);
                    }
                    playerContainerButton.setEnabled(selected.isContainer());
                    deleteItemFromPlayerButton.setEnabled(true);
                    addItemToPlayerButton.setEnabled(false);
                }
            }
        });

        playerScriptList.addListSelectionListener(e -> selectPlayerScript());

        timersList.addListSelectionListener(e -> selectTimer());

        locationsCategoriesList.addListSelectionListener(e -> selectLocationCategory());
        locationCategoryScriptsList.addListSelectionListener(e -> selectLocationCategoryScript());

        // tree listeners
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    Object node = ((JTree) e.getSource()).getLastSelectedPathComponent();
                    if (node != null) {
                        showBehaviorTreeMenu(e);
                    }
                }
            }
        };
        characterBehaviorTree.addTreeSelectionListener(e -> {
            BTreeNode node = (BTreeNode) characterBehaviorTree.getLastSelectedPathComponent();
            if (node != null && node.getClass() == TaskNode.class) {
                characterBehaviorScriptArea.setEnabled(true);
                characterBehaviorScriptArea.setText(((TaskNode) node).getScript().getText());
            } else {
                characterBehaviorScriptArea.setEnabled(false);
            }
        });
        characterBehaviorTree.addMouseListener(adapter);
        playerBehaviorTree.addTreeSelectionListener(e -> {
            BTreeNode node = (BTreeNode) playerBehaviorTree.getLastSelectedPathComponent();
            if (node != null && node.getClass() == TaskNode.class) {
                playerBehaviorScriptArea.setEnabled(true);
                playerBehaviorScriptArea.setText(((TaskNode) node).getScript().getText());
            } else {
                playerBehaviorScriptArea.setEnabled(false);
            }
        });
        playerBehaviorTree.addMouseListener(adapter);
        characterCategoryBehaviorTree.addTreeSelectionListener(e -> {
            BTreeNode node = (BTreeNode) characterCategoryBehaviorTree.getLastSelectedPathComponent();
            if (node != null && node.getClass() == TaskNode.class) {
                characterCategoryBehaviorScriptArea.setEnabled(true);
                characterCategoryBehaviorScriptArea.setText(((TaskNode) node).getScript().getText());
            } else {
                characterCategoryBehaviorScriptArea.setEnabled(false);
            }
        });
        characterCategoryBehaviorTree.addMouseListener(adapter);

        //листенеры комбобоксов
        itemTypeCombo.addActionListener(e -> showSlotField());
        northComboBox.addActionListener(e -> {
            if (northComboBox.getSelectedItem() != null) {
                northEnabledBox.setSelected(true);
            }
        });
        southComboBox.addActionListener(e -> {
            if (southComboBox.getSelectedItem() != null) {
                southEnabledBox.setSelected(true);
            }
        });
        eastComboBox.addActionListener(e -> {
            if (eastComboBox.getSelectedItem() != null) {
                eastEnabledBox.setSelected(true);
            }
        });
        westComboBox.addActionListener(e -> {
            if (westComboBox.getSelectedItem() != null) {
                westEnabledBox.setSelected(true);
            }
        });
        upComboBox.addActionListener(e -> {
            if (upComboBox.getSelectedItem() != null) {
                upEnabledBox.setSelected(true);
            }
        });
        downComboBox.addActionListener(e -> {
            if (downComboBox.getSelectedItem() != null) {
                downEnabledBox.setSelected(true);
            }
        });

        //листенеры текстфилдов
        locationCategoryNameFiled.addActionListener(e -> saveLocationCategoryName());
        itemCategoryNameField.addActionListener(e -> saveItemCategoryName());
        charCategoryNameField.addActionListener(e -> saveCharCategoryName());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });

        if (!this.gamePath.isEmpty()) {
            this.gameFolder = new File(this.gamePath).getParentFile().getAbsolutePath();
            loadGame();
        } else {
            new NewProjectDialog(this);
        }

        //Экипировка игрока и персонажей
        playerEquipmentTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        playerEquipmentTableModel.addColumn("Слот");
        playerEquipmentTableModel.addColumn("Предмет");
        fillPlayerEquipmentTable();
        playerEquipmentTable.setModel(playerEquipmentTableModel);
        playerInventoryList.setModel(itemsListModel);
        playerInventoryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    equipItemToPlayer();
                    fillPlayerEquipmentTable();
                }
            }
        });
        playerEquipmentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable table = (JTable) e.getSource();
                if (e.getClickCount() == 2) {
                    int column = table.columnAtPoint(e.getPoint());
                    int row = table.rowAtPoint(e.getPoint());
                    if (column == 1) {
                        unequipItemFromPlayer(row);
                        fillPlayerEquipmentTable();
                    }
                }
            }
        });
        characterEquipmentTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        characterEquipmentTableModel.addColumn("Слот");
        characterEquipmentTableModel.addColumn("Предмет");
        fillCharacterEquipmentTable();
        characterEquipmentTable.setModel(characterEquipmentTableModel);
        characterInventoryList.setModel(itemsListModel);
        characterInventoryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    equipItemToCharacter();
                    fillCharacterEquipmentTable();
                }
            }
        });
        characterEquipmentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable table = (JTable) e.getSource();
                if (e.getClickCount() == 2) {
                    int column = table.columnAtPoint(e.getPoint());
                    int row = table.rowAtPoint(e.getPoint());
                    if (column == 1) {
                        unequipItemFromCharacter(row);
                        fillCharacterEquipmentTable();
                    }
                }
            }
        });

        //листенеры чекбоксов
        isContainerBox.addActionListener(e -> setContainer());
        isLockedBox.addActionListener(e -> setLocked());
        characterCategoryScriptEnabledBox.addActionListener(e -> setCharacterCategoryScriptEnabled());
        itemCategoryScriptEnabledBox.addActionListener(e -> setItemCategoryScriptEnabled());
        locationCategoryScriptEnabledBox.addActionListener(e -> setLocationCategoryScriptEnabled());
        //test area

        initScriptText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                selectedRSyntaxArea = initScriptText;
            }
        });
        commonScriptText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                selectedRSyntaxArea = commonScriptText;
            }
        });
        locationScriptText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                selectedRSyntaxArea = locationScriptText;
            }
        });
        characterScriptText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                selectedRSyntaxArea = characterScriptText;
            }
        });
        itemScriptText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                selectedRSyntaxArea = itemScriptText;
            }
        });
        playerScriptText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                selectedRSyntaxArea = playerScriptText;
            }
        });
        locationCategoryScriptText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                selectedRSyntaxArea = locationCategoryScriptText;
            }
        });
        charCategoryScriptText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                selectedRSyntaxArea = charCategoryScriptText;
            }
        });
        itemCategoryScriptText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                selectedRSyntaxArea = itemCategoryScriptText;
            }
        });

        timerScriptText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                selectedRSyntaxArea = timerScriptText;
            }
        });

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        clearInventoryButton.addActionListener(e -> player.getInventory().clear());
    }

    private void saveCharacterCategoryBehaviorScript() {
        BTreeNode node = ((BTreeNode) characterCategoryBehaviorTree.getLastSelectedPathComponent());
        if (node != null && node.getClass() == TaskNode.class) {
            ((TaskNode) node).setScript(new Script(characterCategoryBehaviorScriptArea.getText(), true));
        }
    }

    private void showBehaviorTreeMenu(MouseEvent event) {
        JPopupMenu addBTreeMenu = new JPopupMenu();
        for (BehaviorTree.TYPES type : BehaviorTree.TYPES.values()) {
            JMenuItem item = new JMenuItem(type.toString());
            item.addActionListener(e -> addBehavior(type));
            addBTreeMenu.add(item);
        }
        JMenuItem removeItem = new JMenuItem(("Удалить"));
        removeItem.addActionListener(e -> removeBehaviorTreeNode());
        addBTreeMenu.add(removeItem);
        addBTreeMenu.show((Component) event.getSource(), event.getX(), event.getY());
    }

    private void removeBehaviorTreeNode() {
        int tabIndex = mainTabbedPain.getSelectedIndex();
        switch (tabIndex) {
            case 3:
                removeBehaviorTreeNode(OBJECTYPES.CHARACTER);
                break;
            case 4:
                removeBehaviorTreeNode(OBJECTYPES.PLAYER);
                break;
            case 6:
                removeBehaviorTreeNode(OBJECTYPES.CHARACTERCATEGORY);
                break;
        }
    }

    private void removeBehaviorTreeNode(OBJECTYPES type) {
        int index;
        Object object = null;
        JTree tree = null;
        switch (type) {
            case CHARACTER:
                index = charactersList.getSelectedIndex();
                if (index >= 0) {
                    object = charactersListModel.getElementAt(index);
                    tree = characterBehaviorTree;
                }
                break;
            case PLAYER:
                object = player;
                tree = playerBehaviorTree;
                break;
            case CHARACTERCATEGORY:
                index = characterCategoriesList.getSelectedIndex();
                if (index >= 0) {
                    object = characterCategoriesListModel.getElementAt(index);
                    tree = characterCategoryBehaviorTree;
                }
        }
        if (object != null) {
            BTreeNode node = (BTreeNode) tree.getLastSelectedPathComponent();
            if (node.getClass() != BehaviorTree.class) { //can't remove root
                if (type == OBJECTYPES.CHARACTER || type == OBJECTYPES.PLAYER) {
                    ((GameObject) object).getBtree().removeRecurcivly(node);
                } else {
                    ((CharacterCategory) object).getBtree().removeRecurcivly(node);
                }
            }
        }
        tree.updateUI();
    }

    private void addBehavior(BehaviorTree.TYPES bType) {
        //characters, player, character categories
        JTree tree = null;
        GameObject character = null;
        int tabIndex = mainTabbedPain.getSelectedIndex();
        switch (tabIndex) {
            case 3:
                int indexC = charactersList.getSelectedIndex();
                if (indexC >= 0) {
                    tree = characterBehaviorTree;
                    character = charactersListModel.elementAt(indexC);
                }
                break;
            case 4:
                tree = playerBehaviorTree;
                character = player;
                break;
            case 6:
                tree = characterCategoryBehaviorTree;
                break;
        }
        try {
            if (tree != null) {
                BTreeNode node = (BTreeNode) tree.getLastSelectedPathComponent();
                if (node.getClass() != TaskNode.class && node != null) {
                    switch (bType) {
                        case SELECTOR:
                            node.addChild(new Selector());
                            break;
                        case SEQUENCE:
                            node.addChild(new Sequence());
                            break;
                        case TASK:
                            node.addChild(new TaskNode(character));
                            break;
                    }

                }
                tree.updateUI();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillCharacterEquipmentTable() {
        int indexC = charactersList.getSelectedIndex();
        if (indexC >= 0) {
            GameCharacter character = charactersListModel.get(indexC);
            characterEquipmentTableModel.setRowCount(0);
            Equipment.getSlotMap().forEach((key, value) -> characterEquipmentTableModel.addRow(new Object[]{key, character.getEquipedItem(key)}));
        }
    }

    private void saveItemValues() {
        int indexI = itemsList.getSelectedIndex();
        if (indexI >= 0) {
            Item item = itemsListModel.get(indexI);
            for (int r = 0; r < itemValuesTable.getRowCount(); r++) {
                String valueName = (String) itemValuesTableModel.getValueAt(r, 0);
                String value = (String) itemValuesTableModel.getValueAt(r, 1);
                item.setValue(valueName, value);
            }
        }
    }

    private void deleteItemValue() {
        int indexI = itemsList.getSelectedIndex();
        if (indexI >= 0) {
            Item item = itemsListModel.get(indexI);
            int row = itemValuesTable.getSelectedRow();
            String valueName = (String) itemValuesTableModel.getValueAt(row, 0);
            item.removeValue(valueName);
            itemValuesTableModel.removeRow(row);
        }
    }

    private void addItemValue() {
        String valueName = JOptionPane.showInputDialog(this, "Название параметра");
        if (!valueName.isEmpty()) {
            int indexI = itemsList.getSelectedIndex();
            if (indexI >= 0) {
                Item item = itemsListModel.get(indexI);
                item.setValue(valueName, "");
                itemValuesTableModel.addRow(new Object[]{valueName, ""});
            }
        }
    }

    private void saveLocationValues() {
        int indexI = locationsList.getSelectedIndex();
        if (indexI >= 0) {
            Location location = locationsListModel.get(indexI);
            for (int r = 0; r < locationValuesTable.getRowCount(); r++) {
                String valueName = (String) locationValuesTableModel.getValueAt(r, 0);
                String value = (String) locationValuesTableModel.getValueAt(r, 1);
                location.setValue(valueName, value);
            }
        }
    }

    private void deleteLocationValue() {
        int indexI = locationsList.getSelectedIndex();
        if (indexI >= 0) {
            Location location = locationsListModel.get(indexI);
            int row = locationValuesTable.getSelectedRow();
            String valueName = (String) locationValuesTableModel.getValueAt(row, 0);
            location.removeValue(valueName);
            locationValuesTableModel.removeRow(row);
        }
    }

    private void addLocationValue() {
        String valueName = JOptionPane.showInputDialog(this, "Название параметра");
        if (!valueName.isEmpty()) {
            int indexI = locationsList.getSelectedIndex();
            if (indexI >= 0) {
                Location location = locationsListModel.get(indexI);
                location.setValue(valueName, "");
                locationValuesTableModel.addRow(new Object[]{valueName, ""});
            }
        }
    }

    private void saveCharacterValues() {
        int indexI = charactersList.getSelectedIndex();
        if (indexI >= 0) {
            GameCharacter character = charactersListModel.get(indexI);
            for (int r = 0; r < characterValuesTable.getRowCount(); r++) {
                String valueName = (String) characterValuesTableModel.getValueAt(r, 0);
                String value = (String) characterValuesTableModel.getValueAt(r, 1);
                character.setValue(valueName, value);
            }
        }
    }

    private void deleteCharacterValue() {
        int indexI = charactersList.getSelectedIndex();
        if (indexI >= 0) {
            GameCharacter character = charactersListModel.get(indexI);
            int row = characterValuesTable.getSelectedRow();
            String valueName = (String) characterValuesTableModel.getValueAt(row, 0);
            character.removeValue(valueName);
            characterValuesTableModel.removeRow(row);
        }
    }

    private void addCharacterValue() {
        String valueName = JOptionPane.showInputDialog(this, "Название параметра");
        if (!valueName.isEmpty()) {
            int indexI = charactersList.getSelectedIndex();
            if (indexI >= 0) {
                GameCharacter character = charactersListModel.get(indexI);
                character.setValue(valueName, "");
                characterValuesTableModel.addRow(new Object[]{valueName, ""});
            }
        }
    }

    private void savePlayerValues() {
        for (int r = 0; r < playerValuesTable.getRowCount(); r++) {
            String valueName = (String) playerValuesTableModel.getValueAt(r, 0);
            String value = (String) playerValuesTableModel.getValueAt(r, 1);
            player.setValue(valueName, value);
        }
    }

    private void deletePlayerValue() {
        int row = playerValuesTable.getSelectedRow();
        String valueName = (String) playerValuesTableModel.getValueAt(row, 0);
        player.removeValue(valueName);
        playerValuesTableModel.removeRow(row);
    }

    private void addPlayerValue() {
        String valueName = JOptionPane.showInputDialog(this, "Название параметра");
        if (!valueName.isEmpty()) {
            player.setValue(valueName, "");
            playerValuesTableModel.addRow(new Object[]{valueName, ""});
        }
    }


    private void saveTimer() {
        int indexT = timersList.getSelectedIndex();
        if (indexT != -1) {
            SudTimer sudTimer = timersListModel.elementAt(indexT);
            sudTimer.setTimerName(timerNameField.getText());
            sudTimer.setStep(Integer.parseInt(timerStepField.getText()));
            sudTimer.setScript(timerScriptText.getText());
            timersList.updateUI();
        }
    }

    private void selectTimer() {
        int indexT = timersList.getSelectedIndex();
        if (indexT != -1) {
            SudTimer sudTimer = timersListModel.get(indexT);
            timerNameField.setText(sudTimer.getTimerName());
            timerStepField.setText(String.valueOf(sudTimer.getStep()));
            timerScriptText.setText(sudTimer.getScript());
        }
    }

    private void removeTimer() {
        int indexT = timersList.getSelectedIndex();
        if (indexT >= 0) {
            timersListModel.removeElementAt(indexT);
        }
    }

    private void addTimer() {
        SudTimer sudTimer = new SudTimer();
        sudTimer.setTimerName("Новый таймер");
        sudTimer.setStep(1);
        timersListModel.addElement(sudTimer);
    }

    private void setLocationCategoryScriptEnabled() {
        int indexCat = locationCategoriesList.getSelectedIndex();
        int indexS = locationCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            GameObjectCategory locationCategory = locationsCategoriesListModel.elementAt(indexCat);
            String scriptName = locationCategoryScriptsListModel.elementAt(indexS);
            if (!scriptName.startsWith("_on")) {
                locationCategory.getScript(scriptName).setEnabled(locationCategoryScriptEnabledBox.isSelected());
            }
        }
    }

    private void setItemCategoryScriptEnabled() {
        int indexCat = itemsCategoriesList.getSelectedIndex();
        int indexS = itemCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            GameObjectCategory itemCategory = itemsCategoriesListModel.elementAt(indexCat);
            String scriptName = itemCategoryScriptsListModel.elementAt(indexS);
            if (!scriptName.startsWith("_on")) {
                itemCategory.getScript(scriptName).setEnabled(itemCategoryScriptEnabledBox.isSelected());
            }
        }
    }

    private void setCharacterCategoryScriptEnabled() {
        int indexCat = characterCategoriesList.getSelectedIndex();
        int indexS = charCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            CharacterCategory characterCategory = characterCategoriesListModel.elementAt(indexCat);
            String scriptName = charCategoryScriptsListModel.elementAt(indexS);
            if (!scriptName.startsWith("_on")) {
                characterCategory.getScript(scriptName).setEnabled(characterCategoryScriptEnabledBox.isSelected());
            }
        }
    }

    private void deleteCategoryFromPlayer() {
        int indexCC = playerCategoriesList.getSelectedIndex();
        if (indexCC >= 0) {
            GameObjectCategory category = playerCategoriesListModel.getElementAt(indexCC);
            player.removeCategory(category);
            playerCategoriesListModel.removeElement(category);
        }
    }

    private void addCategoryToPlayer() {
        int indexCC = playerAllCategoriesList.getSelectedIndex();
        if (indexCC >= 0) {
            CharacterCategory category = characterCategoriesListModel.getElementAt(indexCC);
            if (!player.getCategories().contains(category)) {
                player.addCategory(category);
                playerCategoriesListModel.addElement(category);
            }
        }
    }

    private void renameCharacterCategoryScript() {
        String newName = JOptionPane.showInputDialog(null);
        if (!newName.isEmpty()) {
            int indexIC = characterCategoriesList.getSelectedIndex();
            int indexS = charCategoryScriptsList.getSelectedIndex();
            if (indexIC >= 0 && indexS >= 0) {
                GameObjectCategory category = characterCategoriesListModel.get(indexIC);
                String oldScriptName = charCategoryScriptsListModel.get(indexS);
                category.setScriptName(newName, oldScriptName);
                charCategoryScriptsListModel.removeElement(oldScriptName);
                charCategoryScriptsListModel.addElement(newName);
            }
        }
    }

    private void renameItemCategoryScript() {
        String newName = JOptionPane.showInputDialog(null);
        if (!newName.isEmpty()) {
            int indexIC = itemsCategoriesList.getSelectedIndex();
            int indexS = itemCategoryScriptsList.getSelectedIndex();
            if (indexIC >= 0 && indexS >= 0) {
                GameObjectCategory category = itemsCategoriesListModel.get(indexIC);
                String oldScriptName = itemCategoryScriptsListModel.get(indexS);
                category.setScriptName(newName, oldScriptName);
                itemCategoryScriptsListModel.removeElement(oldScriptName);
                itemCategoryScriptsListModel.addElement(newName);
            }
        }
    }

    private void renameLocationCategoryScript() {
        String newName = JOptionPane.showInputDialog(null);
        if (!newName.isEmpty()) {
            int indexLC = locationsCategoriesList.getSelectedIndex();
            int indexS = locationCategoryScriptsList.getSelectedIndex();
            if (indexLC >= 0 && indexS >= 0) {
                GameObjectCategory category = locationsCategoriesListModel.get(indexLC);
                String oldScriptName = locationCategoryScriptsListModel.get(indexS);
                category.setScriptName(newName, oldScriptName);
                locationCategoryScriptsListModel.removeElement(oldScriptName);
                locationCategoryScriptsListModel.addElement(newName);
            }
        }
    }

    private void addCategoriesFolder(int objectType) { //TODO: одна функция для фсех категорий
        File folder = chooseFolder(); //выбираем папку со скриптами
        if (folder != null) {
            int indexC = -1;
            switch (objectType) {
                case 1:
                    indexC = locationsCategoriesList.getSelectedIndex();
                    break;
                case 2:
                    indexC = itemsCategoriesList.getSelectedIndex();
                    break;
                case 3:
                    indexC = characterCategoriesList.getSelectedIndex();
                    break;
            }
            if (indexC >= 0) {
                GameObjectCategory category = null;
                switch (objectType) {
                    case 1:
                        category = locationsCategoriesListModel.get(indexC);
                        break;
                    case 2:
                        category = itemsCategoriesListModel.get(indexC);
                        break;
                    case 3:
                        category = characterCategoriesListModel.get(indexC);
                        break;
                }

                if (folder.list().length > 0) {
                    //читаем все файлы в папку и добавляем в выбранную категория скрипты (название файла без разрешения, file: название файла, обрезаное после scripts/)
                    for (File scriptFile : folder.listFiles()) {
                        String scriptFileName = scriptFile.getName();
                        if (scriptFileName.substring(scriptFileName.lastIndexOf(".") + 1).equals("js")) { //проверка то, что это javascript файл
                            String scriptFileAbsPath = scriptFile.getAbsolutePath();
                            String scriptName = scriptFileName.substring(0, scriptFileName.lastIndexOf("."));
                            String scriptRelPath = scriptFileAbsPath.substring(scriptFileAbsPath.lastIndexOf("scripts\\") + 8);
                            Script script = new Script("file:" + scriptRelPath, true);
                            category.setScript(scriptName, script);
                            switch (objectType) {
                                case 1:
                                    locationCategoryScriptsListModel.addElement(scriptName);
                                    break;
                                case 2:
                                    itemCategoryScriptsListModel.addElement(scriptName);
                                    break;
                                case 3:
                                    charCategoryScriptsListModel.addElement(scriptName);
                                    break;
                            }

                        }
                    }
                }
            }
        }
    }

    private File chooseFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(gameFolder + "/scripts"));
        fileChooser.setDialogTitle("Выберите папку со скриптами");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    private void unequipItemFromCharacter(int row) {
        String slotName = (String) characterEquipmentTableModel.getValueAt(row, 0);
        int indexC = charactersList.getSelectedIndex();
        if (indexC >= 0) {
            GameCharacter character = charactersListModel.get(indexC);
            Item item = character.getEquipedItem(slotName);
            if (item != null) {
                character.unequip(item);
            }
        }
    }

    public void setGamePath(String gamePath) {
        this.gamePath = gamePath;
    }

    public void setGameFolder(String gameFolder) {
        this.gameFolder = gameFolder;
    }

    private void equipItemToCharacter() {
        int indexI = characterInventoryList.getSelectedIndex();
        int indexC = charactersList.getSelectedIndex();
        if (indexI >= 0 && indexC >= 0) {
            GameCharacter character = charactersListModel.get(indexC);
            Item item = itemsListModel.get(indexI);
            if (item.getType() == ItemTypes.EQUIPPABLE) {
                character.equip(item);
            }
        }
    }

    private void fillPlayerEquipmentTable() {
        playerEquipmentTableModel.setRowCount(0);
        Equipment.getSlotMap().forEach((key, value) -> playerEquipmentTableModel.addRow(new Object[]{key, player.getEquipedItem(key)}));
    }

    private void fillEquipmentTable() {
        equipTableModel.setRowCount(0);
        Equipment.getSlotMap().forEach((slotName, icon) -> {
            String iconPath = gameFolder + "\\" + icon;
            equipTableModel.addRow(new Object[]{icon, new ImageIcon(iconPath), slotName});
        });
        Utils.updateRowHeights(equipTable);
        fillSlotCombo();
    }

    private void unequipItemFromPlayer(int row) {
        String slotName = (String) playerEquipmentTableModel.getValueAt(row, 0);
        Item item = player.getEquipedItem(slotName);
        if (item != null) {
            player.unequip(item);
        }
    }

    private void equipItemToPlayer() {
        int indexI = playerInventoryList.getSelectedIndex();
        if (indexI >= 0) {
            Item item = itemsListModel.get(indexI);
            if (item.getType() == ItemTypes.EQUIPPABLE) {
                player.equip(item);
            }
        }
    }

    private void findNext() {
        findDialog.findNext();
    }

    private void replace() {
        if (!replaceDialog.isVisible() && selectedRSyntaxArea != null) {
            replaceDialog.setVisible(true);
            replaceDialog.setArea(selectedRSyntaxArea);
        }
    }

    private void find() {
        if (!findDialog.isVisible() && selectedRSyntaxArea != null) {
            findDialog.setVisible(true);
            findDialog.setArea(selectedRSyntaxArea);
        }
    }

    private void showContainerFrame(OBJECTYPES type) {
        Item item = null;
        int indexI;
        switch (type) {
            case LOCATION:
                indexI = locationItemsList.getSelectedIndex();
                if (indexI >= 0) {
                    item = locationItemsListModel.get(indexI).getKey();
                }
                break;
            case CHARACTER:
                indexI = characterItemsList.getSelectedIndex();
                if (indexI >= 0) {
                    item = characterItemsListModel.get(indexI).getKey();
                }
                break;
            case PLAYER:
                indexI = playerItemsList.getSelectedIndex();
                if (indexI >= 0) {
                    item = playerItemsListModel.get(indexI).getKey();
                }
                break;
        }
        new ContainerFrame(this, item);
    }

    private void deleteSomeItemsFromLocation() {
        int index = locationItemsList.getSelectedIndex();
        if (index >= 0) {
            int count = locationItemsListModel.get(index).getValue();
            SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, count, 1);
            JSpinner spinner = new JSpinner(sModel);
            int option = JOptionPane.showOptionDialog(null, spinner, "Введите количество", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.OK_OPTION) {
                int amount = (int) spinner.getModel().getValue();
                deleteItemFromLocation(amount);
            }
        }
    }

    private void addSomeItemsToLocation() {
        SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(sModel);
        int option = JOptionPane.showOptionDialog(null, spinner, "Введите количество", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.OK_OPTION) {
            int amount = (int) spinner.getModel().getValue();
            addItemToLocation(amount);
        }
    }

    private void deleteSomeItemsFromCharacter() {
        int index = characterItemsList.getSelectedIndex();
        if (index >= 0) {
            int count = characterItemsListModel.get(index).getValue();
            SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, count, 1);
            JSpinner spinner = new JSpinner(sModel);
            int option = JOptionPane.showOptionDialog(null, spinner, "Введите количество", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.OK_OPTION) {
                int amount = (int) spinner.getModel().getValue();
                deleteItemFromCharacter(amount);
            }
        }
    }

    private void addSomeItemsToCharacter() {
        SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(sModel);
        int option = JOptionPane.showOptionDialog(null, spinner, "Введите количество", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.OK_OPTION) {
            int amount = (int) spinner.getModel().getValue();
            addItemToCharacter(amount);
        }
    }

    private void deleteSomeItemsFromPlayer() {
        int index = playerItemsList.getSelectedIndex();
        if (index >= 0) {
            int count = playerItemsListModel.get(index).getValue();
            SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, count, 1);
            JSpinner spinner = new JSpinner(sModel);
            int option = JOptionPane.showOptionDialog(null, spinner, "Введите количество", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.OK_OPTION) {
                int amount = (int) spinner.getModel().getValue();
                deleteItemFromPlayer(amount);
            }
        }
    }

    private void addSomeItemsToPlayer() {
        SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(sModel);
        int option = JOptionPane.showOptionDialog(null, spinner, "Введите количество", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.OK_OPTION) {
            int amount = (int) spinner.getModel().getValue();
            addItemToPlayer(amount);
        }
    }

    private void saveCommonScript() {
        int indexS = commonScriptsList.getSelectedIndex();
        if (indexS >= 0) {
            String scriptName = commonScriptsListModel.elementAt(indexS);
            commonScripts.put(scriptName, new Script(commonScriptText.getText(), true));
        }
    }

    private void selectCommonScript() {
        int indexS = commonScriptsList.getSelectedIndex();
        if (indexS >= 0) {
            String scriptName = commonScriptsListModel.elementAt(indexS);
            commonScriptText.setText(commonScripts.get(scriptName).getText());
        }
    }

    private void setLocked() {
        int indexI = itemsList.getSelectedIndex();
        if (indexI >= 0) {
            Item item = itemsListModel.elementAt(indexI);
            if (item.isContainer()) {
                item.setLocked(isLockedBox.isSelected());
            }
        }
    }

    private void setContainer() {
        int indexI = itemsList.getSelectedIndex();
        if (indexI >= 0) {
            Item item = itemsListModel.get(indexI);
            boolean container = isContainerBox.isSelected();
            item.setContainer(container);

            isLockedBox.setEnabled(container);

        }
    }

    private void deleteCategoryFromItem() {
        int indexI = itemsList.getSelectedIndex();
        int indexC = itemCategoriesList.getSelectedIndex();
        if (indexI >= 0 && indexC >= 0) {
            Item item = itemsListModel.getElementAt(indexI);
            GameObjectCategory category = itemCategoriesListModel.getElementAt(indexC);
            item.removeCategory(category);
            itemCategoriesListModel.removeElement(category);
        }
    }

    private void addCategoryToItem() {
        int indexI = itemsList.getSelectedIndex();
        int indexC = itemAllCategoriesList.getSelectedIndex();
        if (indexI >= 0 && indexC >= 0) {
            Item item = itemsListModel.getElementAt(indexI);
            ItemCategory category = itemsCategoriesListModel.getElementAt(indexC);
            if (!item.getCategories().contains(category)) {
                item.addCategory(category);
                itemCategoriesListModel.addElement(category);
            }
        }
    }

    private void deleteCategoryFromLocation() {
        int indexL = locationsList.getSelectedIndex();
        int indexC = locationCategoriesList.getSelectedIndex();
        if (indexL >= 0 && indexC >= 0) {
            Location location = locationsListModel.getElementAt(indexL);
            GameObjectCategory category = locationCategoryListModel.getElementAt(indexC);
            location.removeCategory(category);
            locationCategoryListModel.removeElement(category);
        }
    }

    private void addCategoryToLocation() {
        int indexL = locationsList.getSelectedIndex();
        int indexC = locationAllCategoriesList.getSelectedIndex();
        if (indexL >= 0 && indexC >= 0) {
            Location location = locationsListModel.getElementAt(indexL);
            LocationCategory category = locationsCategoriesListModel.getElementAt(indexC);
            if (!location.getCategories().contains(category)) {
                location.addCategory(category);
                locationCategoryListModel.addElement(category);
            }
        }
    }

    private void deleteCategoryFromCharacter() {
        int indexCh = charactersList.getSelectedIndex();
        int indexCC = characterCategoryList.getSelectedIndex();
        if (indexCh >= 0 && indexCC >= 0) {
            GameCharacter character = charactersListModel.getElementAt(indexCh);
            GameObjectCategory category = characterCategoryListModel.getElementAt(indexCC);
            character.removeCategory(category);
            characterCategoryListModel.removeElement(category);
        }
    }

    private void addCategoryToCharacter() {
        int indexCh = charactersList.getSelectedIndex();
        int indexCC = characterAllCategoriesList.getSelectedIndex();
        if (indexCh >= 0 && indexCC >= 0) {
            GameCharacter character = charactersListModel.getElementAt(indexCh);
            CharacterCategory category = characterCategoriesListModel.getElementAt(indexCC);
            if (!character.getCategories().contains(category)) {
                character.addCategory(category);
                characterCategoryListModel.addElement(category);
            }
        }
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
        JFileChooser fc = new JFileChooser(gameFolder);
        FileFilter ff = new FileNameExtensionFilter("TheSUD game", "sud");
        fc.setFileFilter(ff);
        int response = fc.showSaveDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            this.gamePath = fc.getSelectedFile().getPath();
            if (!this.gamePath.endsWith(".sud")) {
                this.gamePath += ".sud";
            }
            this.gameFolder = new File(this.gamePath).getParentFile().getAbsolutePath();
        } else {
            return;
        }
        saveGame(this.gamePath);
    }

    private void fillSlotCombo() {
        slotNamesModel.removeAllElements();
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
        Item.clearItemsCategories();
        Location.clearLocationsCategories();
        GameCharacter.clearCharactersCategories();
        Equipment.clearSlots();
        equipTableModel.setRowCount(0);
        fillEquipmentTable();
        locationsCategoriesListModel.clear();
        itemsCategoriesListModel.clear();
        characterCategoriesListModel.clear();
        initScriptText.setText("");
        playerDescriptionArea.setText("");
        commonScripts = new HashMap<>();
        commonScripts.put("_onPlayerMoves", new Script("", true));
        commonScripts.put("_onPlayerMovesNorth", new Script("", true));
        commonScripts.put("_onPlayerMovesSouth", new Script("", true));
        commonScripts.put("_onPlayerMovesEast", new Script("", true));
        commonScripts.put("_onPlayerMovesWest", new Script("", true));
        commonScripts.put("_onPlayerMovesUp", new Script("", true));
        commonScripts.put("_onPlayerMovesDown", new Script("", true));
        commonScripts.put("_onPlayerTakesItem", new Script("", true));
        commonScripts.put("_onPlayerUsesItem", new Script("", true));
        commonScripts.put("_onPlayerDropsItem", new Script("", true));
        commonScripts.put("_onPlayerEquipsItem", new Script("", true));
        commonScripts.put("_onPlayerUnequipsItem", new Script("", true));
        commonScripts.put("_onPlayerUnlocksItem", new Script("", true));
        commonScripts.put("_onPlayerStashesItem", new Script("", true));
    }

    private void saveCharCategoryScript() {
        int indexCat = characterCategoriesList.getSelectedIndex();
        int indexS = charCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            CharacterCategory characterCategory = characterCategoriesListModel.elementAt(indexCat);
            String scriptName = charCategoryScriptsListModel.elementAt(indexS);
            //characterCategory.setScript(scriptName, charCategoryScriptText.getText());
            characterCategory.setScript(scriptName, new Script(charCategoryScriptText.getText(), characterCategoryScriptEnabledBox.isSelected()));
        }
    }

    private void saveCharCategoryName() {
        int indexCat = characterCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            CharacterCategory characterCategory = characterCategoriesListModel.elementAt(indexCat);
            String name = charCategoryNameField.getText();
            for (CharacterCategory category : GameCharacter.getCharacterCategories()) {
                if (category.getName().equals(name)) {
                    JOptionPane.showMessageDialog(this, "Категория с таким названием уже существует");
                    return;
                }
            }
            characterCategory.setName(name);
            characterCategoriesList.updateUI();
        }
    }

    private void selectCharCategoryScript() {
        int indexCat = characterCategoriesList.getSelectedIndex();
        int indexS = charCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            CharacterCategory characterCategory = characterCategoriesListModel.elementAt(indexCat);
            String scriptName = charCategoryScriptsListModel.elementAt(indexS);
            //charCategoryScriptText.setText(characterCategory.getScript(scriptName));
            Script script = characterCategory.getScript(scriptName);
            charCategoryScriptText.setText(script.getText());
            characterCategoryScriptEnabledBox.setSelected(script.isEnabled());
        }
    }

    private void deleteCharCategoryScript() {
        int indexCat = characterCategoriesList.getSelectedIndex();
        int indexS = charCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            CharacterCategory characterCategory = characterCategoriesListModel.elementAt(indexCat);
            String scriptName = charCategoryScriptsListModel.elementAt(indexS);
            if (!scriptName.startsWith("_on")) {
                characterCategory.removeScript(scriptName);
                charCategoryScriptsListModel.removeElement(scriptName);
            }
        }
    }

    private void addCharCategoryScript() {
        int index = characterCategoriesList.getSelectedIndex();
        if (index >= 0) {
            CharacterCategory characterCategory = characterCategoriesListModel.elementAt(index);
            String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
            if (scriptName != null) {
                Script script = new Script("", true);
                characterCategory.setScript(scriptName, script);
                charCategoryScriptsListModel.addElement(scriptName);
            }
        }
    }

    private void deleteCharCategory() {
        int indexCat = characterCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            CharacterCategory characterCategory = characterCategoriesListModel.elementAt(indexCat);
            GameCharacter.deleteCategory(characterCategory);
            characterCategoriesListModel.removeElement(characterCategory);
            for (int i = 0; i < charactersListModel.size(); i++) {
                charactersListModel.get(i).removeCategory(characterCategory);
            }
            characterCategoriesList.setSelectedIndex((indexCat > 0) ? indexCat - 1 : indexCat);
        }
    }

    private void addCharCategory() {
        CharacterCategory characterCategory = new CharacterCategory("Название категории");
        GameCharacter.addCharacterCategory(characterCategory);
        characterCategoriesListModel.addElement(characterCategory);
    }

    private void selectCharacterCategory() {
        int indexCat = characterCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            CharacterCategory characterCategory = characterCategoriesListModel.elementAt(indexCat);
            charCategoryNameField.setText(characterCategory.getName());
            charCategoryScriptsListModel.clear();
            characterCategory.getScripts().keySet().forEach(charCategoryScriptsListModel::addElement);
            DefaultTreeModel treeModel = new DefaultTreeModel(characterCategory.getBtree());
            characterCategoryBehaviorTree.setModel(treeModel);
        }
    }

    private void deleteItemCategoryScript() {
        int indexCat = itemsCategoriesList.getSelectedIndex();
        int indexS = itemCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            ItemCategory itemCategory = itemsCategoriesListModel.elementAt(indexCat);
            String scriptName = itemCategoryScriptsListModel.elementAt(indexS);
            if (!scriptName.startsWith("_on")) {
                itemCategory.removeScript(scriptName);
                itemCategoryScriptsListModel.removeElement(scriptName);
            }
        }
    }

    private void addItemCategoryScript() {
        int index = itemsCategoriesList.getSelectedIndex();
        if (index >= 0) {
            ItemCategory itemCategory = itemsCategoriesListModel.elementAt(index);
            String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
            if (scriptName != null) {
                Script script = new Script("", true);
                itemCategory.setScript(scriptName, script);
                itemCategoryScriptsListModel.addElement(scriptName);
            }
        }
    }

    private void deleteLocationCategoryScript() {
        int indexCat = locationsCategoriesList.getSelectedIndex();
        int indexS = locationCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            LocationCategory locationCategory = locationsCategoriesListModel.elementAt(indexCat);
            String scriptName = locationCategoryScriptsListModel.elementAt(indexS);
            if (!scriptName.startsWith("_on")) {
                locationCategory.removeScript(scriptName);
                locationCategoryScriptsListModel.removeElement(scriptName);
            }
        }
    }

    private void addLocationCategoryScript() {
        int index = locationsCategoriesList.getSelectedIndex();
        if (index >= 0) {
            LocationCategory locationCategory = locationsCategoriesListModel.elementAt(index);
            String scriptName = JOptionPane.showInputDialog(this, "Название скрипта");
            if (scriptName != null) {
                Script script = new Script("", true);
                locationCategory.setScript(scriptName, script);
                locationCategoryScriptsListModel.addElement(scriptName);
            }
        }
    }

    private void saveItemCategoryScript() {
        int indexCat = itemsCategoriesList.getSelectedIndex();
        int indexS = itemCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            ItemCategory itemCategory = itemsCategoriesListModel.elementAt(indexCat);
            String scriptName = itemCategoryScriptsListModel.elementAt(indexS);
            //itemCategory.setScript(scriptName, locationCategoryScriptText.getText());
            itemCategory.setScript(scriptName, new Script(itemCategoryScriptText.getText(), itemCategoryScriptEnabledBox.isSelected()));
        }
    }

    private void saveItemCategoryName() {
        int indexCat = itemsCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            ItemCategory itemCategory = itemsCategoriesListModel.elementAt(indexCat);
            String name = itemCategoryNameField.getText();
            for (ItemCategory category : Item.getItemCategories()) {
                if (category.getName().equals(name)) {
                    JOptionPane.showMessageDialog(this, "Категория с таким названием уже существует");
                    return;
                }
            }
            itemCategory.setName(name);
            itemsCategoriesList.updateUI();
        }
    }

    private void selectItemCategoryScript() {
        int indexCat = itemsCategoriesList.getSelectedIndex();
        int indexS = itemCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            ItemCategory itemCategory = itemsCategoriesListModel.elementAt(indexCat);
            String scriptName = itemCategoryScriptsListModel.elementAt(indexS);
            Script script = itemCategory.getScript(scriptName);
            itemCategoryScriptText.setText(script.getText());
            itemCategoryScriptEnabledBox.setSelected(script.isEnabled());
        }
    }

    private void selectItemCategory() {
        int indexCat = itemsCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            ItemCategory itemCategory = itemsCategoriesListModel.elementAt(indexCat);
            itemCategoryNameField.setText(itemCategory.getName());
            itemCategoryScriptsListModel.clear();
            itemCategory.getScripts().keySet().forEach(itemCategoryScriptsListModel::addElement);
        }
    }

    private void deleteItemCategory() {
        int indexCat = itemsCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            ItemCategory itemCategory = itemsCategoriesListModel.elementAt(indexCat);
            Item.deleteItemCategory(itemCategory);
            itemsCategoriesListModel.removeElement(itemCategory);
            for (int i = 0; i < itemsListModel.size(); i++) {
                Item item = itemsListModel.elementAt(i);
                item.removeCategory(itemCategory);
            }
            itemsCategoriesList.setSelectedIndex((indexCat > 0) ? indexCat - 1 : indexCat);
        }
    }

    private void addItemCategory() {
        ItemCategory itemCategory = new ItemCategory("Название категории");
        Item.addItemCategory(itemCategory);
        itemsCategoriesListModel.addElement(itemCategory);
    }

    private void selectLocationCategoryScript() {
        int indexCat = locationsCategoriesList.getSelectedIndex();
        int indexS = locationCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            LocationCategory locationCategory = locationsCategoriesListModel.elementAt(indexCat);
            String scriptName = locationCategoryScriptsListModel.elementAt(indexS);
            Script script = locationCategory.getScript(scriptName);
            locationCategoryScriptText.setText(script.getText());
            locationCategoryScriptEnabledBox.setSelected(script.isEnabled());
        }
    }

    private void selectLocationCategory() {
        int indexCat = locationsCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            LocationCategory locationCategory = locationsCategoriesListModel.elementAt(indexCat);
            locationCategoryNameFiled.setText(locationCategory.getName());
            locationCategoryScriptsListModel.clear();
            locationCategory.getScripts().keySet().forEach(locationCategoryScriptsListModel::addElement);
        }
    }

    private void saveLocationCategoryScript() {
        int indexCat = locationsCategoriesList.getSelectedIndex();
        int indexS = locationCategoryScriptsList.getSelectedIndex();
        if (indexCat >= 0 && indexS >= 0) {
            LocationCategory locationCategory = locationsCategoriesListModel.elementAt(indexCat);
            String scriptName = locationCategoryScriptsListModel.elementAt(indexS);
            locationCategory.setScript(scriptName, new Script(locationCategoryScriptText.getText(), locationCategoryScriptEnabledBox.isSelected()));
        }
    }

    private void saveLocationCategoryName() {
        int indexCat = locationsCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            LocationCategory locationCategory = locationsCategoriesListModel.elementAt(indexCat);
            String name = locationCategoryNameFiled.getText();
            for (LocationCategory category : Location.getLocationCategories()) {
                if (category.getName().equals(name)) {
                    JOptionPane.showMessageDialog(this, "Категория с таким названием уже существует");
                    return;
                }
            }
            locationCategory.setName(name);
            locationsCategoriesList.updateUI();
        }
    }

    private void deleteLocationCategory() {
        int indexCat = locationsCategoriesList.getSelectedIndex();
        if (indexCat >= 0) {
            LocationCategory locationCategory = locationsCategoriesListModel.elementAt(indexCat);
            Location.deleteLocationCategory(locationCategory);
            locationsCategoriesListModel.removeElement(locationCategory);
            for (int i = 0; i < locationsListModel.size(); i++) {
                locationsListModel.get(i).removeCategory(locationCategory);
            }
            locationsCategoriesList.setSelectedIndex((indexCat > 0) ? indexCat - 1 : indexCat);
        }
    }

    private void addLocationCategory() {
        LocationCategory locationCategory = new LocationCategory("Название категории");
        Location.addLocationCategory(locationCategory);
        locationsCategoriesListModel.addElement(locationCategory);
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
        deleteItemFromPlayer(1);
    }

    private void deleteItemFromPlayer(int count) {
        int indexI = playerItemsList.getSelectedIndex();
        if (indexI >= 0) {
            Item item = playerItemsListModel.getElementAt(indexI).getKey();
            int newAmount = playerItemsListModel.get(indexI).getValue() - count;
            if (newAmount != 0) {
                playerItemsListModel.get(indexI).setValue(newAmount);
                playerItemsList.updateUI();
            } else {
                playerItemsListModel.removeElementAt(indexI);
            }
            player.removeItem(item, count);
        }
    }

    private void addItemToPlayer() {
        addItemToPlayer(1);
    }

    private void addItemToPlayer(int count) {
        int indexI = playerTabItemsList.getSelectedIndex();
        if (indexI >= 0) {
            Item item = itemsListModel.getElementAt(indexI);
            if (item.isContainer()) {
                try {
                    Item container = (Item) item.clone();
                    player.addItem(container, count);
                    playerItemsListModel.addElement(new SudPair<>(container, count));
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            } else {
                if (player.getInventory().contains(item)) {
                    int newAmount = player.getInventory().getAmount(item) + count;
                    int index = playerItemsListModel.indexOf(player.getInventory().getPair(item));
                    playerItemsListModel.get(index).setValue(newAmount);
                } else {
                    playerItemsListModel.addElement(new SudPair<>(item, count));
                }
                player.addItem(item, count);
            }
        }
    }

    private void deleteItemFromCharacter() {
        deleteItemFromCharacter(1);
    }

    private void deleteItemFromCharacter(int count) {
        int indexC = charactersList.getSelectedIndex();
        int indexI = characterItemsList.getSelectedIndex();
        if (indexI >= 0 && indexC >= 0) {
            GameCharacter character = charactersListModel.getElementAt(indexC);
            Item item = characterItemsListModel.getElementAt(indexI).getKey();
            int newAmount = characterItemsListModel.get(indexI).getValue() - count;
            if (newAmount != 0) {
                characterItemsListModel.get(indexI).setValue(newAmount);
                characterItemsList.updateUI();
            } else {
                characterItemsListModel.removeElementAt(indexI);
            }
            character.removeItem(item, count);
        }

    }

    private void addItemToCharacter() {
        addItemToCharacter(1);
    }

    private void addItemToCharacter(int count) {
        int indexC = charactersList.getSelectedIndex();
        int indexI = characterTabItemsList.getSelectedIndex();
        if (indexC >= 0 && indexI >= 0) {
            GameCharacter character = charactersListModel.getElementAt(indexC);
            Item item = itemsListModel.getElementAt(indexI);
            if (item.isContainer()) {
                try {
                    Item container = (Item) item.clone();
                    character.addItem(container, count);
                    characterItemsListModel.addElement(new SudPair<>(container, count));
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            } else {
                if (character.getInventory().contains(item)) {
                    int newAmount = character.getInventory().getAmount(item) + count;
                    int index = characterItemsListModel.indexOf(character.getInventory().getPair(item));
                    characterItemsListModel.get(index).setValue(newAmount);
                } else {
                    characterItemsListModel.addElement(new SudPair<>(item, count));
                }
                character.addItem(item, count);
            }
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
        if (!this.gamePath.isEmpty()) {
            saveGame(this.gamePath + ".temp");
            new PlayerFrame(this.gamePath + ".temp");
        } else {
            saveAs();
            new PlayerFrame(this.gamePath);
        }
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
        String name = charNameFiled.getText();
        selected.setName(name);
        selected.setLocation((Location) characterLocationModel.getSelectedItem());
        selected.setDescription(charDescriptionArea.getText());
        List<GameObjectCategory> categories = new ArrayList<>();
        for (int i = 0; i < characterCategoryListModel.size(); i++)
            categories.add(characterCategoryListModel.getElementAt(i));
        selected.setCategories(categories);
        int tabIndex = characterTab.getSelectedIndex();
        if (tabIndex == 6) {
            BTreeNode node = ((BTreeNode) characterBehaviorTree.getLastSelectedPathComponent());
            if (node != null && node.getClass() == TaskNode.class) {
                ((TaskNode) node).setScript(new Script(characterBehaviorScriptArea.getText(), true));
            }
        }
    }

    private void selectCharacter() {
        int index = charactersList.getSelectedIndex();
        if (index != -1) {
            GameCharacter selected = charactersListModel.getElementAt(index);
            charNameFiled.setText(selected.getName());
            charIdField.setText(String.valueOf(selected.getId()));
            characterLocationModel.setSelectedItem(selected.getLocation());
            characterScriptListModel.removeAllElements();
            selected.getScripts().keySet().forEach(characterScriptListModel::addElement);
            charDescriptionArea.setText(selected.getDescription());
            characterCategoryListModel.removeAllElements();
            selected.getCategories().forEach(characterCategoryListModel::addElement);
            fillCharacterEquipmentTable();
            characterValuesTableModel.setRowCount(0);
            /*for (Map.Entry<String, Object> entry : selected.getValues().entrySet()) {
                characterValuesTableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }*/
            DefaultTreeModel treeModel = new DefaultTreeModel(selected.getBtree());
            characterBehaviorTree.setModel(treeModel);
        }
    }

    private void setCharsFromItemsEnabled() {
        boolean enabled = charactersList.getSelectedIndex() >= 0;
        charNameFiled.setEnabled(enabled);
        characterTabItemsList.setEnabled(enabled);
        characterItemsList.setEnabled(enabled);
        characterLocationCombo.setEnabled(enabled);
        charSaveButton.setEnabled(enabled);
        deleteCharButton.setEnabled(enabled);
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
        fillSlotCombo();
        fillPlayerEquipmentTable();
    }

    private void addSlot() {
        System.out.println("addslot");
        equipTableModel.addRow(new Object[]{"/path/to/file", new ImageIcon(), "название"});
        updateEquipmentSlots();
        fillSlotCombo();
        fillPlayerEquipmentTable();
        //fillEquipmentTable();
        Utils.updateRowHeights(equipTable);
    }

    private void updateEquipmentSlots() {
        Equipment.clearSlots();
        for (int row = 0; row < equipTableModel.getRowCount(); row++) {
            Equipment.addSlotName(equipTableModel.getValueAt(row, 0).toString(), equipTableModel.getValueAt(row, 2).toString());
        }
    }

    private void showSlotField() {
        if (itemTypeCombo.getModel().getSelectedItem() == ItemTypes.EQUIPPABLE) {
            slotCombo.setEnabled(true);
        } else {
            slotCombo.setEnabled(false);
        }
    }

    private void deleteItemFromLocation() {
        deleteItemFromLocation(1);
    }

    private void deleteItemFromLocation(int count) {
        int indexLoc = locationsList.getSelectedIndex();
        int indexItem = locationItemsList.getSelectedIndex();
        if (indexItem >= 0 && indexLoc >= 0) {
            Location location = locationsListModel.getElementAt(indexLoc);
            Item item = locationItemsListModel.getElementAt(indexItem).getKey();
            int newAmount = locationItemsListModel.get(indexItem).getValue() - count;
            if (newAmount != 0) {
                locationItemsListModel.get(indexItem).setValue(newAmount);
                locationItemsList.updateUI();
            } else {
                locationItemsListModel.removeElementAt(indexItem);
            }
            location.removeItem(item, count);
        }
    }

    private void saveSelectedItem() {
        int index = itemsList.getSelectedIndex();
        Item selected = itemsListModel.getElementAt(index);
        selected.setName(itemName.getText());
        selected.setDescription(itemDescription.getText());
        ItemTypes type = (ItemTypes) itemTypeCombo.getSelectedItem();
        selected.setType(type);
        if (slotCombo.isEnabled())
            selected.setEquipmentSlot(String.valueOf(slotCombo.getSelectedItem()));
        itemsList.updateUI();
    }

    private void addItemToLocation() {
        addItemToLocation(1);
    }

    private void addItemToLocation(int count) {
        int indexLoc = locationsList.getSelectedIndex();
        int indexItem = locationTabItemsList.getSelectedIndex();
        if (indexLoc >= 0 && indexItem >= 0) {
            Location location = locationsListModel.getElementAt(indexLoc);
            Item item = itemsListModel.getElementAt(indexItem);
            if (item.isContainer()) {
                try {
                    Item container = (Item) item.clone();
                    location.addItem(container, count);
                    locationItemsListModel.addElement(new SudPair<>(container, count));
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            } else {
                if (location.getInventory().contains(item)) {
                    int newAmount = location.getInventory().getAmount(item) + count;
                    int index = locationItemsListModel.indexOf(location.getInventory().getPair(item));
                    locationItemsListModel.get(index).setValue(newAmount);
                } else {
                    locationItemsListModel.addElement(new SudPair<>(item, count));
                }
                location.addItem(item, count);
            }
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
            itemCategoriesListModel.clear();
            selected.getCategories().forEach(itemCategoriesListModel::addElement);
            selected.getScripts().keySet().forEach(itemScriptListModel::addElement);
            itemDescription.setText(selected.getDescription());
            isLockedBox.setEnabled(selected.isContainer());
            isLockedBox.setSelected(selected.isLocked());
            isContainerBox.setSelected(selected.isContainer());
            itemValuesTableModel.setRowCount(0);
            /*for (Map.Entry<String, Object> entry : selected.getValues().entrySet()) {
                itemValuesTableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }*/
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
        JFileChooser fc = new JFileChooser(gameFolder);
        FileFilter ff = new FileNameExtensionFilter("TheSUD game", "sud");
        fc.setFileFilter(ff);
        int response = fc.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            this.gamePath = fc.getSelectedFile().getPath();
            this.gameFolder = new File(this.gamePath).getParentFile().getAbsolutePath();
            loadGame();
        }
    }

    private void loadGame() {
        System.out.println("Opening file " + this.gamePath);
        GameFile gameFile = GameFile.open(this.gamePath);
        assert gameFile != null;
        player = gameFile.getPlayer();

        Sequencer.setID(gameFile.getSequencerID());

        commonScripts = gameFile.getCommonScripts();

        locationsListModel.clear();
        for (Location l : gameFile.getLocations()) {
            playerLocationModel.addElement(l);
            locationsListModel.addElement(l);
            northModel.addElement(l);
            southModel.addElement(l);
            eastModel.addElement(l);
            westModel.addElement(l);
            characterLocationModel.addElement(l);
        }

        itemsListModel.clear();
        gameFile.getItems().forEach(itemsListModel::addElement);

        charactersListModel.clear();
        gameFile.getCharacters().forEach(charactersListModel::addElement);

        Map<String, String> slotNames = gameFile.getSlotNames();
        Equipment.setSlotNames(slotNames);
        /*equipTableModel.setRowCount(0);
        slotNamesModel.removeAllElements();
        for (Map.Entry<String, String> slotsEntry : slotNames.entrySet()) {
            equipTableModel.addRow(new Object[]{slotsEntry.getValue(), new ImageIcon(slotsEntry.getValue()), slotsEntry.getKey()});
            slotNamesModel.addElement(slotsEntry.getKey());
        }
        Utils.updateRowHeights(equipTable);*/
        fillEquipmentTable();

        characterCategoryListModel.clear();
        itemCategoriesListModel.clear();
        locationCategoryListModel.clear();
        playerCategoriesListModel.clear();

        gameFile.getCharacterCategories().forEach(characterCategoriesListModel::addElement);
        gameFile.getCharacterCategories().forEach(GameCharacter::addCharacterCategory);
        gameFile.getItemCategories().forEach(itemsCategoriesListModel::addElement);
        gameFile.getItemCategories().forEach(Item::addItemCategory);
        gameFile.getLocationCategories().forEach(locationsCategoriesListModel::addElement);
        gameFile.getLocationCategories().forEach(Location::addLocationCategory);
        gameFile.getPlayer().getCategories().forEach(playerCategoriesListModel::addElement);

        timersListModel.clear();
        gameFile.getTimers().forEach(timersListModel::addElement);

        gameName.setText(gameFile.getGameName());
        gameStartMessage.setText(gameFile.getGameStartMessage());

        initScriptText.setText(gameFile.getInitScript());

        Location.setLocationCategories(gameFile.getLocationCategories());
        Item.setItemCategories(gameFile.getItemCategories());
        GameCharacter.setCharacterCategories(gameFile.getCharacterCategories());
        // btree workaround
        for (int i = 0; i < charactersListModel.size(); i++) {
            GameCharacter character = charactersListModel.elementAt(i);
            if (character.getBtree() == null) {
                character.restoreBTree();
            }
        }
        for (int i = 0; i < characterCategoriesListModel.size(); i++) {
            GameObjectCategory character = characterCategoriesListModel.elementAt(i);
            if (character.getBtree() == null) {
                character.restoreBTree();
            }
        }
        if (player.getBtree() == null) {
            player.restoreBTree();
        }

        updatePlayerTab();
    }

    private void updatePlayerTab() {
        playerName.setText(player.getName());
        playerLocation.setSelectedItem(player.getLocation());
        player.getInventory().stream().forEach(playerItemsListModel::addElement);

        playerDescriptionArea.setText(player.getDescription());
        player.getScripts().keySet().stream().filter(scriptName -> !scriptName.startsWith("_on")).forEach(playerScriptListModel::addElement);

        DefaultTreeModel treeModel = new DefaultTreeModel(player.getBtree());
        playerBehaviorTree.setModel(treeModel);
    }


    private void menuSaveGame() {
        if (this.gamePath.isEmpty()) {
            JFileChooser fc = new JFileChooser(gameFolder);
            FileFilter ff = new FileNameExtensionFilter("TheSUD game", "sud");
            fc.setFileFilter(ff);
            int response = fc.showSaveDialog(this);
            if (response == JFileChooser.APPROVE_OPTION) {
                this.gamePath = fc.getSelectedFile().getPath();
                if (!this.gamePath.endsWith(".sud")) {
                    this.gamePath += ".sud";
                }
                this.gameFolder = new File(this.gamePath).getParentFile().getAbsolutePath();
            } else {
                return;
            }
        }
        saveGame(this.gamePath);
    }

    private void saveGame(String filePath) {
        if (player.getLocation() != null) {
            System.out.println("Saving to " + filePath);
            GameFile gameFile = new GameFile();
            gameFile.setPlayer(player);
            gameFile.setSequencerID(Sequencer.getCurrentId());
            gameFile.setCommonScripts(commonScripts);
            ArrayList<Location> locations = new ArrayList<>();
            for (int i = 0; i < locationsListModel.size(); i++) {
                locations.add(locationsListModel.getElementAt(i));
            }
            ArrayList<GameCharacter> characters = new ArrayList<>();
            for (int i = 0; i < charactersListModel.size(); i++) {
                characters.add(charactersListModel.getElementAt(i));
            }
            gameFile.setCharacters(characters);
            gameFile.setLocations(locations);
            gameFile.setGameName(gameName.getText());
            gameFile.setGameStartMessage(gameStartMessage.getText());
            ArrayList<Item> items = new ArrayList<>();
            for (int i = 0; i < itemsListModel.getSize(); i++)
                items.add(itemsListModel.getElementAt(i));
            gameFile.setItems(items);
            Map<String, String> slotsNames = new HashMap<>();
            for (int i = 0; i < equipTableModel.getRowCount(); i++) { //TODO: сохраняется в одну сторону, открывается в другую (пока не критично)
                String slotName = String.valueOf(equipTableModel.getValueAt(i, 2));
                String slotImagePath = String.valueOf(equipTableModel.getValueAt(i, 0));
                slotsNames.put(slotName, slotImagePath);
            }
            gameFile.setCharacterCategories(GameCharacter.getCharacterCategories());
            gameFile.setItemCategories(Item.getItemCategories());
            gameFile.setLocationCategories(Location.getLocationCategories());
            gameFile.setSlotNames(slotsNames);
            gameFile.setInitScript(initScriptText.getText());
            ArrayList<SudTimer> timers = new ArrayList<>();
            for (int i = 0; i < timersListModel.size(); i++) {
                timers.add(timersListModel.get(i));
            }
            gameFile.setTimers(timers);
            gameFile.save(filePath);
        } else
            JOptionPane.showMessageDialog(this, "Выберите стартовую локацию игрока!");
    }

    private void savePlayer() {
        player.setName(playerName.getText());
        Location pLocation = (Location) playerLocation.getSelectedItem();
        player.setLocation(pLocation);
        player.setDescription(playerDescriptionArea.getText());
        int index = playerTab.getSelectedIndex();
        if (index == 6) {
            BTreeNode node = (BTreeNode) playerBehaviorTree.getLastSelectedPathComponent();
            if (node != null && node.getClass() == TaskNode.class) {
                ((TaskNode) node).setScript(new Script(playerBehaviorScriptArea.getText(), true));
            }
        }
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
        locationsList.setSelectedIndex((index > 0) ? index - 1 : index);
        northModel.removeElement(selected);
        southModel.removeElement(selected);
        eastModel.removeElement(selected);
        westModel.removeElement(selected);
        characterLocationModel.removeElement(selected);
    }


    private void saveSelectedLocation() {
        int index = locationsList.getSelectedIndex();
        int northIndex = northComboBox.getSelectedIndex();
        int southIndex = southComboBox.getSelectedIndex();
        int eastIndex = eastComboBox.getSelectedIndex();
        int westIndex = westComboBox.getSelectedIndex();
        int upIndex = upComboBox.getSelectedIndex();
        int downIndex = downComboBox.getSelectedIndex();
        Location selectedLocation = locationsListModel.getElementAt(index);

        selectedLocation.setName(locName.getText());
        selectedLocation.setDescription(locDescription.getText());
        selectedLocation.setNorth(northModel.getElementAt(northIndex));
        selectedLocation.setSouth(southModel.getElementAt(southIndex));
        selectedLocation.setEast(eastModel.getElementAt(eastIndex));
        selectedLocation.setWest(westModel.getElementAt(westIndex));
        selectedLocation.setUp(upModel.getElementAt(upIndex));
        selectedLocation.setDown(downModel.getElementAt(downIndex));
        selectedLocation.setNorthOpened(northEnabledBox.isSelected());
        selectedLocation.setSouthOpened(southEnabledBox.isSelected());
        selectedLocation.setEastOpened(eastEnabledBox.isSelected());
        selectedLocation.setWestOpened(westEnabledBox.isSelected());
        selectedLocation.setUpOpened(upEnabledBox.isSelected());
        selectedLocation.setDownOpened(downEnabledBox.isSelected());
        locationsList.updateUI();
    }

    private void selectLocation() {
        int index = locationsList.getSelectedIndex();
        if (index != -1) {
            Location selected = locationsListModel.getElementAt(index);
            locName.setText(selected.getName());
            locID.setText(String.valueOf(selected.getId()));
            locDescription.setText(selected.getDescription());
            northComboBox.getModel().setSelectedItem(selected.getNorth());
            southComboBox.getModel().setSelectedItem(selected.getSouth());
            eastComboBox.getModel().setSelectedItem(selected.getEast());
            westComboBox.getModel().setSelectedItem(selected.getWest());
            upComboBox.getModel().setSelectedItem(selected.getUp());
            downComboBox.getModel().setSelectedItem(selected.getDown());
            locationItemsListModel.clear();
            locationScriptListModel.removeAllElements();
            //selected.getScripts().keySet().forEach(locationScriptListModel::addElement);
            selected.getScripts().keySet().forEach(locationScriptListModel::addElement);
            selected.getInventory().stream().forEach(locationItemsListModel::addElement);
            northEnabledBox.setSelected(selected.isNorthOpened());
            southEnabledBox.setSelected(selected.isSouthOpened());
            eastEnabledBox.setSelected(selected.isEastOpened());
            westEnabledBox.setSelected(selected.isWestOpened());
            upEnabledBox.setSelected(selected.isUpOpened());
            downEnabledBox.setSelected(selected.isDownOpened());
            locationCategoryListModel.removeAllElements();
            selected.getCategories().forEach(locationCategoryListModel::addElement);
            locationValuesTableModel.setRowCount(0);
            /*for (Map.Entry<String, Object> entry : selected.getValues().entrySet()) {
                locationValuesTableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }*/
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
        upModel.addElement(newLocation);
        downModel.addElement(newLocation);
        playerLocationModel.addElement(newLocation);
        characterLocationModel.addElement(newLocation);
    }

    private void setLocationFormElementsEnabled() {
        boolean enabled = locationsList.getSelectedIndex() >= 0;
        deleteLocButton.setEnabled(enabled);
        locName.setEnabled(enabled);
        locDescription.setEnabled(enabled);
        northComboBox.setEnabled(enabled);
        southComboBox.setEnabled(enabled);
        eastComboBox.setEnabled(enabled);
        westComboBox.setEnabled(enabled);
        upComboBox.setEnabled(enabled);
        downComboBox.setEnabled(enabled);
        saveLocButton.setEnabled(enabled);
        locationItemsList.setEnabled(enabled);
        locationTabItemsList.setEnabled(enabled);
        locationScriptsList.setEnabled(enabled);
        saveLocScriptButton.setEnabled(enabled);
        northEnabledBox.setEnabled(enabled);
        southEnabledBox.setEnabled(enabled);
        eastEnabledBox.setEnabled(enabled);
        westEnabledBox.setEnabled(enabled);
        upEnabledBox.setEnabled(enabled);
        downEnabledBox.setEnabled(enabled);
        locationAllCategoriesList.setEnabled(enabled);
        locationCategoriesList.setEnabled(enabled);
        addCategoryToLocationButton.setEnabled(enabled);
        deleteCategoryFromLocationButton.setEnabled(enabled);
    }
}
