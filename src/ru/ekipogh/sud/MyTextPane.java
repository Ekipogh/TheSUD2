package ru.ekipogh.sud;

import ru.ekipogh.sud.frames.PlayerFrame;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;

/**
 * Created by ekipogh on 07.05.2015.
 * licensed under WTFPL
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
        if (str == null)
            str = "";
        if (str.contains("img")) {
            str = (String) Script.run("magic('" + str + "')", null);
        }
        String temp = this.getText();
        int breakIndex = temp.lastIndexOf("</body>");
        temp = temp.substring(0, breakIndex) + "<br>" + str + temp.substring(breakIndex);
        this.setText(temp);
        this.setCaretPosition(getDocument().getLength());
    }

    public void print(String str) {
        if (str == null)
            str = "";
        if (str.contains("img")) {
            str = (String) Script.run("magic('" + str + "')", null);
        }
        String temp = this.getText();
        int breakIndex = temp.lastIndexOf("</body>");
        temp = temp.substring(0, breakIndex) + str + temp.substring(breakIndex);
        this.setText(temp);
        this.setCaretPosition(getDocument().getLength());
    }

    public void clear() {
        this.setText("<html><head></head><body></body></html>");
    }
}
