package ru.ekipogh.sud;

import javax.swing.*;

class Main {
    public static LauncherFrame launcher;
    public static EditorFrame editor;

    public static void main(String[] args) {
        Icon empty = new ImageIcon();
        UIManager.put("Tree.closedIcon", empty);
        UIManager.put("Tree.openIcon", empty);
        UIManager.put("Tree.leafIcon", empty);
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        launcher = new LauncherFrame();

    }
}
