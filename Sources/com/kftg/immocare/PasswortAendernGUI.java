/* ------------------------------------------------------------------------
    PasswortAendernGUI.java

    Diese Datei ist für die Erstellung des Passwort Ändern Fensters zuständig.

    Author: Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian
    Date: 18.12.2020

    Copyright © 2021 Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian. Alle Rechte vorbehalten.
-------------------------------------------------------------------------- */

package com.kftg.immocare;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class PasswortAendernGUI extends JFrame {
    private JLabel LPasswortN1;
    private JPasswordField FPasswortN1;
    private JLabel LPasswort;
    private JButton BAendern;
    private JLabel LAnmeldung;
    private JPasswordField FPasswort;
    private JPasswordField FPasswortN2;
    private JLabel LPasswortN2;
    private JPanel PMain;

    public Database db = new Database();

    public PasswortAendernGUI() {
        add(PMain);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setTitle("ImmoCare");

        // Passwort ändern Button Aktion
        BAendern.addActionListener(e -> ChangePW());

        // Enter Aktion
        FPasswort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ChangePW();
                }
            }
        });
        FPasswortN1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ChangePW();
                }
            }
        });
        FPasswortN2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ChangePW();
                }
            }
        });
    }

    // Passwort ändern
    private void ChangePW() {
        try {
            if (db.checkPassword(String.valueOf(FPasswort.getPassword()))) {
                if (String.valueOf(FPasswortN1.getPassword()).equals(String.valueOf(FPasswortN2.getPassword()))) {
                    db.changePassword(String.valueOf(FPasswortN1.getPassword()));

                    PasswortAendernGUI.super.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Passwörter stimmen nicht überein.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Passwort Falsch.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
