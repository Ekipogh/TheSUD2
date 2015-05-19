package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by dedov_d on 12.05.2015.
 */
public class ImageRenderer extends DefaultTableCellRenderer {
    JLabel lbl = new JLabel();

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        lbl.setIcon(((ImageIcon) value));
        return lbl;
    }
}
