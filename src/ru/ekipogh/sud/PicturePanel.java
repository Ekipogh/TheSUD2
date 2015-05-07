package ru.ekipogh.sud;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dedov_d on 07.05.2015.
 */
public class PicturePanel extends JPanel {

    private Image img;

    public PicturePanel(String imagepath) {
        this(new ImageIcon(imagepath).getImage());
    }

    public PicturePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

}