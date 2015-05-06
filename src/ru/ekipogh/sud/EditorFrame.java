package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by dedov_d on 23.04.2015.
 */
public class EditorFrame extends JFrame {
    private final DefaultComboBoxModel<Location> northModel;
    private final DefaultComboBoxModel<Location> southModel;
    private final DefaultComboBoxModel<Location> eastModel;
    private final DefaultComboBoxModel<Location> westModel;
    private final JMenuItem saveGameMenu;
    private final JMenuItem newGameMenu;
    private final JMenuItem openGameMenu;
    private final DefaultListModel<Item> itemsListModel;
    private final DefaultListModel<Item> locationItemsListModel;
    private DefaultListModel<Location> locationsListModel;
    private JPanel rootPanel;
    private JTabbedPane tabPanel;
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
    private JPanel playerTab;
    private JTextField playerName;
    private JRadioButton female;
    private JRadioButton male;
    private JComboBox<Location> playerLocation;
    private JRadioButton sexless;
    private JButton savePlayer;
    private JTextField gameName;
    private JTextArea gameStartMessage;
    private JTabbedPane itemTabs;
    private JList itemsList;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JTextField itemName;
    private JTextArea itemDescription;
    private JComboBox itemType;
    private JButton saveItemButton;
    private JList locationTabItemsList;
    private JList locationItemsList;
    private JButton addItemToLoc;
    private JButton deleteiIemFromLoc;
    private JPanel locationItems;
    private DefaultComboBoxModel<Location> playerLocationModel;

    Player player;

    public EditorFrame() {
        super("Редактор");
        player = new Player("Nameless");

        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //TODO: создать метод закрытия окна

        locationsListModel = new DefaultListModel<Location>();
        locationsList.setModel(locationsListModel);

        itemsListModel = new DefaultListModel<Item>();
        itemsList.setModel(itemsListModel);
        locationTabItemsList.setModel(itemsListModel);
        locationItemsListModel = new DefaultListModel<Item>();
        locationItemsList.setModel(locationItemsListModel);

        itemType.setModel(new DefaultComboBoxModel(ItemTypes.values()));

        northModel = new DefaultComboBoxModel<Location>();
        southModel = new DefaultComboBoxModel<Location>();
        eastModel = new DefaultComboBoxModel<Location>();
        westModel = new DefaultComboBoxModel<Location>();
        playerLocationModel = new DefaultComboBoxModel<Location>();

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
        newGameMenu = new JMenuItem("Новая");
        openGameMenu = new JMenuItem("Открыть");
        saveGameMenu = new JMenuItem("Сохранить");
        menuFile.add(newGameMenu);
        menuFile.add(openGameMenu);
        menuFile.add(saveGameMenu);
        menuBar.add(menuFile);

        setJMenuBar(menuBar);

        saveGameMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });

        openGameMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGame();
            }
        });

        addLocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewLocation();
            }
        });

        locationsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setLocationFormElementsEnabled();
                selectLocation();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.launcher.setVisible(true);
                super.windowClosing(e);
            }
        });

        saveLocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSelectedLocation();
            }
        });
        deleteLocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedLocation();
            }
        });
        savePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePlayer();
            }
        });
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewItem();
            }
        });
        itemsList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                setItemFormElementsEnabled();
                selectItem();
            }
        });
        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedItem();
            }
        });
        addItemToLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToLocation();
            }
        });
        saveItemButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveSelectedItem();
            }
        });

        deleteiIemFromLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItemFromLocation();
            }
        });
        locationTabItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteiIemFromLoc.setEnabled(false);
                    addItemToLoc.setEnabled(true);
                }
            }
        });
        locationItemsList.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (((JList) e.getSource()).getSelectedIndex() >= 0) {
                    deleteiIemFromLoc.setEnabled(true);
                    addItemToLoc.setEnabled(false);
                }
            }
        });
    }

    private void deleteItemFromLocation() {
        int indexLoc = locationsList.getSelectedIndex();
        Location selectedLoc = locationsListModel.getElementAt(indexLoc);
        int indexItem = locationItemsList.getSelectedIndex();
        Item selectedItem = locationItemsListModel.getElementAt(indexItem);
        selectedLoc.getInventory().remove(selectedItem);
        locationItemsListModel.removeElement(selectedItem);
        if (indexItem == 0)
            deleteiIemFromLoc.setEnabled(false);
        locationItemsList.setSelectedIndex((indexItem > 0) ? indexItem - 1 : indexItem);
    }

    private void saveSelectedItem() {
        int index = itemsList.getSelectedIndex();
        Item selected = itemsListModel.getElementAt(index);
        selected.setName(itemName.getText());
        selected.setDescription(itemDescription.getText());
        ItemTypes type = (ItemTypes) itemType.getSelectedItem();
        selected.setType(type);

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
        }
    }

    private void deleteSelectedItem() {
        int index = itemsList.getSelectedIndex();
        Item selected = itemsListModel.getElementAt(index);
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
        } else {
            itemName.setEnabled(false);
            itemDescription.setEnabled(false);
            saveItemButton.setEnabled(false);
            deleteItemButton.setEnabled(false);
            itemType.setEnabled(false);
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
            locationsListModel.clear();
            for (Location l : saveFile.getLocations()) {
                playerLocationModel.addElement(l);
                locationsListModel.addElement(l);
            }
            itemsListModel.clear();
            for (Item i : saveFile.getItems())
                itemsListModel.addElement(i);
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
            System.out.print("Saving to " + fc.getSelectedFile().getPath());
            SaveFile saveFile = new SaveFile();
            saveFile.setPlayer(player);
            ArrayList<Location> locations = new ArrayList<Location>();
            for (int i = 0; i < locationsListModel.size(); i++) {
                locations.add(locationsListModel.getElementAt(i));
            }
            saveFile.setLocations(locations);
            saveFile.setGameName(gameName.getText());
            saveFile.setGameStartMessage(gameStartMessage.getText());
            ArrayList<Item> items = new ArrayList<Item>();
            for (int i = 0; i < itemsListModel.getSize(); i++)
                items.add(itemsListModel.getElementAt(i));
            saveFile.setItems(items);
            saveFile.save(fc.getSelectedFile().getPath());
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
        int sounthIndex = locSouth.getSelectedIndex();
        int eastIndex = locEast.getSelectedIndex();
        int westIndex = locWest.getSelectedIndex();
        Location selectedLocation = locationsListModel.getElementAt(index);

        selectedLocation.setName(locName.getText());
        selectedLocation.setDescription(locDescription.getText());
        selectedLocation.setNorth(northModel.getElementAt(northIndex));
        selectedLocation.setSouth(southModel.getElementAt(sounthIndex));
        selectedLocation.setEast(eastModel.getElementAt(eastIndex));
        selectedLocation.setWest(westModel.getElementAt(westIndex));

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
            /*locationItemsList.setEnabled(true);
            locationTabItemsList.setEnabled(true);*/
            locationItemsListModel.clear();
            for (Item i : selected.getInventory()) {
                locationItemsListModel.addElement(i);
            }
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

    private void setLocationFormElementsEnabled() { //TODO: rethink: Disable all component when no Location on the list, enable when first location added?
        if (locationsList.getSelectedIndex() >= 0) {
            deleteLocButton.setEnabled(true);
            locName.setEnabled(true);
            locDescription.setEnabled(true);
            locNorth.setEnabled(true);
            locSouth.setEnabled(true);
            locEast.setEnabled(true);
            locWest.setEnabled(true);
            saveLocButton.setEnabled(true);
            locationItemsList.setEnabled(true);
            locationTabItemsList.setEnabled(true);
        } else {
            deleteLocButton.setEnabled(false);
            locName.setEnabled(false);
            locDescription.setEnabled(false);
            locNorth.setEnabled(false);
            locSouth.setEnabled(false);
            locEast.setEnabled(false);
            locWest.setEnabled(false);
            saveLocButton.setEnabled(false);
            locationItemsList.setEnabled(false);
            locationTabItemsList.setEnabled(false);
            deleteiIemFromLoc.setEnabled(false);
            addItemToLoc.setEnabled(false);
        }
    }
}
