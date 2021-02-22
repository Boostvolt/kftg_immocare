/* ------------------------------------------------------------------------
    Index.java

    Diese Datei ist für die Bereitstellung der ImmoCare-Anwendung verantwortlich und dient als Hauptklasse.

    Author: Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian
    Date: 28.10.2020

    Copyright © 2021 Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian. Alle Rechte vorbehalten.
-------------------------------------------------------------------------- */

package com.kftg.immocare;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;

import com.bulenkov.darcula.*;

public class Index {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        if (System.getProperty("os.name", "").startsWith("Mac OS")) {
            /*Wenn das Programm auf macOS ausgeführt werden soll, folgende Argumente zur JVM hinzufügen:
            -Xdock:name="ImmoCare" -Xdock:icon=Resources/Icon.png -Dapple.awt.application.appearance=system
             */

            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ImmoCare");

            BasicLookAndFeel darculaLookAndFeel = new DarculaLaf();
            UIManager.setLookAndFeel(darculaLookAndFeel);
        } else {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }

        SwingUtilities.invokeLater(() -> {
            LoginGUI login = new LoginGUI();
            login.setVisible(true);
        });
    }
}