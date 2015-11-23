package ru.ekipogh.sud;

import javax.swing.*;

class Main {
    public static LauncherFrame launcher;
    public static EditorFrame editor;

    public static void main(String[] args) {
        /*Icon closed = new ImageIcon("data/closed.png");
        Icon open = new ImageIcon("data/open.png");
        Icon leaf = new ImageIcon("data/leaf.png");
        UIManager.put("Tree.closedIcon", closed);
        UIManager.put("Tree.openIcon", open);
        UIManager.put("Tree.leafIcon", leaf);*/
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
