package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by ekipogh on 12.05.2015.
 */
public class ImageRenderer extends DefaultTableCellRenderer {
    private JLabel lbl = new JLabel();

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        lbl.setIcon(((ImageIcon) value));
        return lbl;
    }
}
