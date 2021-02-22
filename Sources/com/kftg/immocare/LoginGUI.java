/* ------------------------------------------------------------------------
    LoginGUI.java

    Diese Datei ist für die Erstellung des Loginfensters zuständig.

    Author: Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian
    Date: 03.12.2020

    Copyright © 2021 Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian. Alle Rechte vorbehalten.
-------------------------------------------------------------------------- */

package com.kftg.immocare;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class LoginGUI extends JFrame {

    private JTextField FBenutzername;
    private JButton BAnmelden;
    private JPasswordField FPasswort;
    private JPanel PAnmeldung;
    private JLabel LBenutzername;
    private JLabel LPasswort;
    private JLabel LAnmeldung;
    public Database db = new Database();

    public LoginGUI() {
        add(PAnmeldung);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setTitle("ImmoCare");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Anmelden Button Aktion
        BAnmelden.addActionListener(e -> {
            db.checkLogin(FBenutzername.getText(), String.valueOf(FPasswort.getPassword()));
            executeLogin();
        });

        // Enter Aktion
        FBenutzername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    db.checkLogin(FBenutzername.getText(), String.valueOf(FPasswort.getPassword()));
                    executeLogin();
                }
            }
        });
        FPasswort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    db.checkLogin(FBenutzername.getText(), String.valueOf(FPasswort.getPassword()));
                    executeLogin();
                }
            }
        });
    }

    // Anmeldung
    private void executeLogin() {
        try {
            if (db.sendResult()) {
                MainGUI MainPage = new MainGUI();
                MainPage.setVisible(true);
                LoginGUI.super.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Falscher Benutzer oder falsches Passwort.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
