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
    private JList locationsList;
    private JTabbedPane locTabs;
    private JPanel locExits;
    private JPanel locCommon;
    private JPanel locObjects;
    private JPanel locScript;
    private JTextField locName;
    private JTextField locID;
    private JTextArea locDescription;
    private JComboBox locNorth;
    private JComboBox locSouth;
    private JComboBox locEast;
    private JComboBox locWest;
    private JButton addLocButton;
    private JButton deleteLocButton;
    private JButton сохранитьButton;

    public EditorForm() {
        super("Редактор");

        locationsListModel = new DefaultListModel<Location>();
        locationsList.setModel(locationsListModel);

        northModel = new DefaultComboBoxModel<Location>();
        southModel = new DefaultComboBoxModel<Location>();
        eastModel = new DefaultComboBoxModel<Location>();
        westhModel = new DefaultComboBoxModel<Location>();

        northModel.addElement(null);
        southModel.addElement(null);
        eastModel.addElement(null);
        westhModel.addElement(null);

        locNorth.setModel(northModel);
        locSouth.setModel(southModel);
        locEast.setModel(eastModel);
        locWest.setModel(westhModel);

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
        сохранитьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSelectedLocation();
            }
        });
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

    }

    private void setFormElementsEnabled() { //TODO: rethink: Disable all component when no Location on the list, enable when first location added?
        if(locationsList.getSelectedIndex()>=0) {
            deleteLocButton.setEnabled(true);
            locName.setEnabled(true);
            locDescription.setEnabled(true);
            locNorth.setEnabled(true);
            locSouth.setEnabled(true);
            locEast.setEnabled(true);
            locWest.setEnabled(true);
        }
        else{
            deleteLocButton.setEnabled(false);
            locName.setEnabled(false);
            locDescription.setEnabled(false);
            locNorth.setEnabled(false);
            locSouth.setEnabled(false);
            locEast.setEnabled(false);
            locWest.setEnabled(false);
        }
    }
}
