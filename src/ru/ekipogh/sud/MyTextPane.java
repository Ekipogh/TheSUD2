package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;

/**
 * Created by dedov_d on 07.05.2015.
 */
public class MyTextPane extends JTextPane {
    private boolean enabled = true;

    public MyTextPane() {
        super();
        setContentType("text/html");
        this.setText("<html><head></head><body></body></html>");
        this.setEditable(false);
        this.addHyperlinkListener(e -> { // Обработка ссылок
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType()) && this.enabled) {
                Script.run(e.getDescription(), PlayerFrame.getPlayer());
            }
        });
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void println(String str) {
        //<img src = "file:/D:/Tomato/IdeaProjects/TheSUD2/photo.jpg">
        String s = (String) Script.run("magic()", str);
        String temp = this.getText();
        int breakIndex = temp.lastIndexOf("</body>");
        temp = temp.substring(0, breakIndex) + s + "<br>" + temp.substring(breakIndex);
        this.setText(temp);
    }

    public void print(String str) {
        String s = (String) Script.run("magic()", str);
        String temp = this.getText();
        int breakIndex = temp.lastIndexOf("</body>");
        temp = temp.substring(0, breakIndex) + s + temp.substring(breakIndex);
        this.setText(temp);
    }

    public void clear() {
        this.setText("<html><head></head><body></body></html>");
    }
}
