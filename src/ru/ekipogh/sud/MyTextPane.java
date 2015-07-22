package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;

/**
 * Created by dedov_d on 07.05.2015.
 */
public class MyTextPane extends JTextPane {
    public MyTextPane() {
        super();
        setContentType("text/html");
        this.setText("<html><head></head><body></body></html>");
        this.setEditable(false);
        this.addHyperlinkListener(e -> { // Обработка ссылок
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                //TODO узнаем в Description ссылки имя юзер скрипта, извлекаем его текст и отправляем выполняться (Имплементировать юзерскрипты)
            }
        });
    }

    public void println(String str) {
        String temp = this.getText();
        int breakIndex = temp.lastIndexOf("</body>");
        temp = temp.substring(0, breakIndex) + str + "<br>" + temp.substring(breakIndex);
        this.setText(temp);
    }
}
