package ru.ekipogh.sud;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * Created by ekipogh on 12.05.2015.
 */
public class Utils {
    public static void updateRowHeights(JTable table) {
        for (int row = 0; row < table.getRowCount(); row++) {
            int rowHeight = table.getRowHeight();

            for (int column = 0; column < table.getColumnCount(); column++) {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            table.setRowHeight(row, rowHeight);
        }
    }

    public static int showSpinnerDialog() {
        javafx.scene.control.Dialog<Integer> spinnerDialog = new javafx.scene.control.Dialog<>();
        spinnerDialog.setTitle("Enter amount");
        ButtonType okButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        spinnerDialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        javafx.scene.control.Label amountLabel = new Label("Amount: ");
        Spinner<Integer> spinner = new Spinner<>(0, Integer.MAX_VALUE, 1);
        spinner.setEditable(true);
        grid.add(amountLabel, 0, 0);
        grid.add(spinner, 1, 0);
        spinnerDialog.getDialogPane().setContent(grid);

        spinnerDialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return spinner.getValue();
            }
            return 0;
        });
        Optional<Integer> result = spinnerDialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return 0;
    }
}
