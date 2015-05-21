package ru.ekipogh.sud;

import javax.swing.*;

/**
 * Created by dedov_d on 07.05.2015.
 */
public class MyTextPane extends JTextPane {
    public MyTextPane() {
        super();
        setContentType("text/html");
        this.setText("<html><head></head><body></body></html>");
    }

    public void println(String str) {
        String temp = this.getText();
        int breakIndex = temp.lastIndexOf("</body>");
        temp = temp.substring(0, breakIndex) + str + "<br>" + temp.substring(breakIndex);
        this.setText(temp);
    }
}
