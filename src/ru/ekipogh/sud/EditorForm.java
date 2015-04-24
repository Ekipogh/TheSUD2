package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dedov_d on 23.04.2015.
 */
public class EditorForm extends JFrame {
    private final DefaultComboBoxModel<Location> northModel;
    private final DefaultComboBoxModel<Location> southModel;
    private final DefaultComboBoxModel<Location> eastModel;
    private final DefaultComboBoxModel<Location> westhModel;
    private DefaultListModel<Location> locationsListModel;
    private JPanel rootPanel;
    private JTabbedPane tabPane;
    private JPanel locationsPane;
    private JList<Location> locationsList;
    private JTabbedPane locTabs;
    private JPanel locExits;
    private JPanel locCommon;
    private JPanel locObjects;
    private JPanel locScript;
    private JTextField locName;
    private JTextField locID;
    private JTextArea locDescription;
    private JComboBox<Location> locNorth;
    private JComboBox<Location> locSouth;
    private JComboBox<Location> locEast;
    private JComboBox<Location> locWest;
    private JButton addLocButton;
    private JButton deleteLocButton;
    private JButton saveButton;
    private JPanel playerTab;
    private JTextField playerName;
    private JRadioButton female;
    private JRadioButton male;
    private JComboBox<Location> playerLocation;
    private JRadioButton sexless;
    private JButton savePlayer;
    private DefaultComboBoxModel<Location> playerLocationModel;

    Player player;

    public EditorForm() {
        super("Редактор");

        player = new Player("Nameless");

        locationsListModel = new DefaultListModel<Location>();
        locationsList.setModel(locationsListModel);

        northModel = new DefaultComboBoxModel<Location>();
        southModel = new DefaultComboBoxModel<Location>();
        eastModel = new DefaultComboBoxModel<Location>();
        westhModel = new DefaultComboBoxModel<Location>();
        playerLocationModel = new DefaultComboBoxModel<Location>();

        northModel.addElement(null);
        southModel.addElement(null);
        eastModel.addElement(null);
        westhModel.addElement(null);
        playerLocationModel.addElement(null);

        locNorth.setModel(northModel);
        locSouth.setModel(southModel);
        locEast.setModel(eastModel);
        locWest.setModel(westhModel);
        playerLocation.setModel(playerLocationModel);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Файл");
        JMenuItem newGame = new JMenuItem("Новая");
        JMenuItem openGame = new JMenuItem("Открыть");
        JMenuItem saveGame = new JMenuItem("Сохранить");
        menuFile.add(newGame);
        menuFile.add(openGame);
        menuFile.add(saveGame);
        menuBar.add(menuFile);

        setJMenuBar(menuBar);

        getRootPane().setDefaultButton(saveButton);

        setContentPane(rootPanel);
        pack();

        addLocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewLocation();
            }
        });

        locationsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setFormElementsEnabled();
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

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //TODO: создать метод закрытия окна
        saveButton.addActionListener(new ActionListener() {
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
    }

    private void savePlayer() {
        player.setName(playerName.getText());
        if (female.isSelected())
            player.setSex(1);
        if (male.isSelected())
            player.setSex(2);
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
        westhModel.removeElement(selected);
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
        selectedLocation.setWest(westhModel.getElementAt(westIndex));

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
        westhModel.addElement(newLocation);
        playerLocationModel.addElement(newLocation);
    }

    private void setFormElementsEnabled() { //TODO: rethink: Disable all component when no Location on the list, enable when first location added?
        if (locationsList.getSelectedIndex() >= 0) {
            deleteLocButton.setEnabled(true);
            locName.setEnabled(true);
            locDescription.setEnabled(true);
            locNorth.setEnabled(true);
            locSouth.setEnabled(true);
            locEast.setEnabled(true);
            locWest.setEnabled(true);
            saveButton.setEnabled(true);
        } else {
            deleteLocButton.setEnabled(false);
            locName.setEnabled(false);
            locDescription.setEnabled(false);
            locNorth.setEnabled(false);
            locSouth.setEnabled(false);
            locEast.setEnabled(false);
            locWest.setEnabled(false);
            saveButton.setEnabled(false);
        }
    }
}
