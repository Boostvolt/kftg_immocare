/* ------------------------------------------------------------------------
    MainGUI.java

    Diese Datei ist für die Erstellung des Hauptfensters und des Menüs zuständig.

    Author: Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian
    Date: 28.10.2020

    Copyright © 2021 Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian. Alle Rechte vorbehalten.
-------------------------------------------------------------------------- */

package com.kftg.immocare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainGUI extends JFrame {
    private JPanel PMain;
    private JPanel PContent;
    private JPanel PLiegeschaft;
    private JPanel PKalkulation;
    private JPanel PDiagramm;
    private JScrollPane SPImmobilien;
    private JPanel PImmobilien;
    private JButton BDelete;
    private JButton BAddImmo;
    private JTextField FSuche;
    private JComboBox<String> CBSortieren;
    private JComboBox<String> CBArt;
    private JTextField FGrundFlaeche;
    private JTextField FNutzflaeche;
    private JComboBox<String> CBAusbau;
    private JTextField FStrasse;
    private JComboBox<String> CBZustand;
    private JTextField FZimmerzahl;
    private JTextField FHausnummer;
    private JTextField FOrtsname;
    private JTextField FPLZ;
    private JButton BBearbeiten;
    private JTextField FBaujahr;
    private JLabel LStrasse;
    private JLabel LOrtsname;
    private JLabel LHausNummer;
    private JLabel LPLZ;
    private JLabel LArt;
    private JLabel LZimmerAnz;
    private JLabel LAusbau;
    private JLabel LZustand;
    private JLabel LBaujahr;
    private JLabel LBezeichnung;
    private JLabel LGrundFlaeche;
    private JLabel LNutzflaeche;
    private JLabel LWohnungsAnz;
    private JLabel LWert;
    private JLabel FWert;
    private JLabel LGRaum;
    private JTextField FGRaum;
    private JTextField FWohnungsAnz;
    private JLabel LSuchen;
    private JLabel LSortieren;
    private JPanel PBenutzer;
    private JList LImmobilien;
    private JLabel LAdresse;
    private JLabel LInformationen;
    private JTextField FBezeichnung;
    private JMenuBar MenuBar;
    private JMenu MDarstellung;
    private JMenu MAblage;
    private JMenu MHilfe;
    private JMenu MBearbeiten;
    private JMenu MAccount;
    private JLabel LBeschreibung;
    private JTextField FBeschreibung;
    private JComboBox<String> CBSortOrder;
    private JComboBox CBSearchType;

    private final Database db;
    private int selectedImmo;
    private int selectedImmoIndex;
    private final DefaultListModel listModel;

    public MainGUI() {
        listModel = new DefaultListModel();
        LImmobilien.setModel(listModel);
        db = new Database();

        createListModelSorted("ASC", "ImmBezeichnung");

        add(PMain);
        setSize(1200, 800);
        setMinimumSize(new Dimension(1100, 500));
        setLocationRelativeTo(null);
        setTitle("ImmoCare");
        setJMenuBar(MenuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ablage Menü
        JMenuItem MIImmobilieHinzufuegen, MIImmobilieLoeschen;
        MIImmobilieHinzufuegen = new JMenuItem("Immobilie hinzufügen");
        MIImmobilieHinzufuegen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        MIImmobilieLoeschen = new JMenuItem("Immobilie löschen");
        MIImmobilieLoeschen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        MAblage.add(MIImmobilieHinzufuegen);
        MAblage.add(MIImmobilieLoeschen);

        // Bearbeiten Menü
        JMenuItem MIBearbeiten, MISpeichern, MIAusschneiden, MIKopieren, MIEinsetzen, MIAlleAuswaehlen;
        MIBearbeiten = new JMenuItem("Bearbeiten");
        MIBearbeiten.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        MISpeichern = new JMenuItem("Speichern");
        MISpeichern.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        MIAusschneiden = new JMenuItem("Ausschneiden");
        MIAusschneiden.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        MIKopieren = new JMenuItem("Kopieren");
        MIKopieren.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        MIEinsetzen = new JMenuItem("Einsetzen");
        MIEinsetzen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        MIAlleAuswaehlen = new JMenuItem("Alle Auswählen");
        MIAlleAuswaehlen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        MBearbeiten.add(MIBearbeiten);
        MBearbeiten.add(MISpeichern);
        MBearbeiten.addSeparator();
        MBearbeiten.add(MIAusschneiden);
        MBearbeiten.add(MIKopieren);
        MBearbeiten.add(MIEinsetzen);
        MBearbeiten.add(MIAlleAuswaehlen);

        // Darstellung Menü
        JMenuItem MIBezeichnungAufwaerts, MIBezeichnungAbwaerts, MIBaujahrAufwaerts, MIBaujahrAbwaerts, MISuchen;
        JMenu MSortieren = new JMenu("Sortieren nach");
        JMenu MBezeichnung = new JMenu("Bezeichnung");
        MIBezeichnungAufwaerts = new JMenuItem("Aufwärts");
        MIBezeichnungAufwaerts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        MIBezeichnungAbwaerts = new JMenuItem("Abwärts");
        MIBezeichnungAbwaerts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        JMenu MBaujahr = new JMenu("Baujahr");
        MIBaujahrAufwaerts = new JMenuItem("Aufwärts");
        MIBaujahrAufwaerts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        MIBaujahrAbwaerts = new JMenuItem("Abwärts");
        MIBaujahrAbwaerts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        MISuchen = new JMenuItem("Suchen");
        MISuchen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        MSortieren.add(MBezeichnung);
        MBezeichnung.add(MIBezeichnungAufwaerts);
        MBezeichnung.add(MIBezeichnungAbwaerts);
        MSortieren.add(MBaujahr);
        MBaujahr.add(MIBaujahrAufwaerts);
        MBaujahr.add(MIBaujahrAbwaerts);
        MDarstellung.add(MSortieren);
        MDarstellung.add(MISuchen);

        // Account Menü
        JMenuItem MIPasswortAendern, MIAbmelden;
        MIPasswortAendern = new JMenuItem("Passwort ändern");
        MIPasswortAendern.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        MIAbmelden = new JMenuItem("Abmelden");
        MIAbmelden.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        MAccount.add(MIPasswortAendern);
        MAccount.add(MIAbmelden);

        // Hilfe Menü
        JMenuItem MIHilfe;
        MIHilfe = new JMenuItem("ImmoCare-Hilfe");
        MIHilfe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_HELP,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        MHilfe.add(MIHilfe);

        // Sortieren Dropdown Elemente
        for (String s : Arrays.asList("Bezeichnung", "Baujahr")) {
            CBSortieren.addItem(s);
        }
        for (String s : Arrays.asList("Aufwärts", "Abwärts")) {
            CBSortOrder.addItem(s);
        }

        // Ausbau Dropdown Elemente
        for (String s : Arrays.asList("Einfach", "Normal", "Luxeriös")) {
            CBAusbau.addItem(s);
            CBAusbau.setSelectedItem(null);
        }

        for (String s : Arrays.asList("Bezeichnung", "Ortschaft")) {
            CBSearchType.addItem(s);
        }

        // Zustand Dropdown Elemente
        for (String s : Arrays.asList("Sanierung", "Normal", "Neuwert")) {
            CBZustand.addItem(s);
            CBZustand.setSelectedItem(null);
        }

        // Art Dropdown Elemente
        for (String s : Arrays.asList("Einfamilienhaus", "Mehrfamilienhaus", "Gewerbehaus")) {
            CBArt.addItem(s);
            CBArt.setSelectedItem(null);
        }

        // Bearbeiten und Speichern Button Aktion
        BBearbeiten.addActionListener(e -> {
            selectedImmoIndex = LImmobilien.getSelectedIndex();
            if (BBearbeiten.getText().equals("Bearbeiten")) {
                enableFields();
                BBearbeiten.setText("Speichern");
            } else if (BBearbeiten.getText().equals("Speichern")) {

                if ((LImmobilien.getSelectedValue()).equals("Neue Immobilie")) {
                    newImmo();
                } else if (!(LImmobilien.getSelectedValue()).equals("Neue Immobilie")) {
                    updateImmo();
                }
                if (!FBeschreibung.getText().equals("") && !FBezeichnung.getText().equals("") && !FBaujahr.getText().equals("") && !FPLZ.getText().equals("") && !FOrtsname.getText().equals("") && !FHausnummer.getText().equals("") && !FStrasse.getText().equals("") && !FNutzflaeche.getText().equals("") && !FGrundFlaeche.getText().equals("") && CBArt.getSelectedIndex() != -1 && CBAusbau.getSelectedIndex() != -1 && CBZustand.getSelectedIndex() != -1) {
                    disableFields();
                    BBearbeiten.setText("Bearbeiten");
                    if (FSuche.getText().equals("")) {
                        sortieren();
                    } else {
                        suche(FSuche.getText());
                    }
                    LImmobilien.setSelectedIndex(selectedImmoIndex);
                } else {
                    JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Felder aus.");
                }
            }
        });

        // Eingabefelder editierbar entsprechend der Immobilienart
        CBArt.addActionListener(e -> {
            FZimmerzahl.setText(null);
            FWohnungsAnz.setText(null);
            FGRaum.setText(null);
            if (Objects.equals(CBArt.getSelectedItem(), "Einfamilienhaus") && BBearbeiten.getText().equals("Speichern")) {
                FZimmerzahl.setEnabled(true);
                FWohnungsAnz.setEnabled(false);
                FGRaum.setEnabled(false);
            } else if (Objects.equals(CBArt.getSelectedItem(), "Mehrfamilienhaus") && BBearbeiten.getText().equals("Speichern")) {
                FWohnungsAnz.setEnabled(true);
                FZimmerzahl.setEnabled(false);
                FGRaum.setEnabled(false);
            } else if (Objects.equals(CBArt.getSelectedItem(), "Gewerbehaus") && BBearbeiten.getText().equals("Speichern")) {
                FGRaum.setEnabled(true);
                FZimmerzahl.setEnabled(false);
                FWohnungsAnz.setEnabled(false);
            }
        });

        // Eingabefelder Validierung
        FStrasse.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                Pattern pattern = Pattern.compile("^[a-zA-Z\\u0080-\\uFFFF ]*$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(textField.getText());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    JOptionPane.showMessageDialog(null, "Bitte gebe Sie eine gültige Strasse ein.");
                }
                return matchFound;
            }
        });
        FHausnummer.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                Pattern pattern = Pattern.compile("^\\d+[a-zA-Z]*$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(textField.getText());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige Hausnummer ein.");
                }
                return matchFound;
            }
        });
        FOrtsname.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                Pattern pattern = Pattern.compile("^[a-zA-Z\\u0080-\\uFFFF ]*$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(textField.getText());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    JOptionPane.showMessageDialog(null, "Bitte geben Sie einen gültige Ortsnamen ein.");
                }
                return matchFound;
            }
        });
        FPLZ.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                Pattern pattern = Pattern.compile("^[0-9]{4}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(textField.getText());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige Postleitzahl ein.");
                }
                return matchFound;

            }
        });
        FGrundFlaeche.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                Pattern pattern = Pattern.compile("^[0-9]*[.]?[0-9]*$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(textField.getText());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige Fläche ein.");
                }
                return matchFound;
            }
        });
        FNutzflaeche.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                Pattern pattern = Pattern.compile("^[0-9]*[.]?[0-9]*$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(textField.getText());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige Nutzfläche ein.");
                }
                return matchFound;
            }
        });
        FBaujahr.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                Pattern pattern = Pattern.compile("^[0-9]{4}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(textField.getText());
                boolean matchFound = matcher.find();
                if (!matchFound || Integer.parseInt(textField.getText()) > Integer.parseInt(String.valueOf(Year.now()))) {
                    JOptionPane.showMessageDialog(null, "Bitte geben Sie ein gültiges Baujahr ein.");
                }
                return matchFound;
            }
        });
        FZimmerzahl.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                Pattern pattern = Pattern.compile("^[0-9]*[.]?[0-9]*$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(textField.getText());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige Zimmeranzahl ein.");
                }
                return matchFound;
            }
        });
        FWohnungsAnz.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                Pattern pattern = Pattern.compile("^[1-9]*$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(textField.getText());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige Wohnungsanzahl ein.");
                }
                return matchFound;
            }
        });
        FGRaum.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                Pattern pattern = Pattern.compile("^[0-9]*[.]?[0-9]*$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(textField.getText());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    JOptionPane.showMessageDialog(null, "Bitte geben Sie einen gültigen Grösster Raum ein.");
                }
                return matchFound;
            }
        });

        // Abmelden Menü Item Aktion
        MIAbmelden.addActionListener(e -> {
            MainGUI.super.dispose();
            LoginGUI logingui = new LoginGUI();
            logingui.setVisible(true);
        });

        // Passwort ändern Menü Item Aktion
        MIPasswortAendern.addActionListener(e -> {
            PasswortAendernGUI CPW = new PasswortAendernGUI();
            CPW.setVisible(true);
        });

        // Hilfe Menü Item Aktion
        MIHilfe.addActionListener(e -> {
            HilfeGUI CPW = new HilfeGUI();
            CPW.setVisible(true);
        });

        // Immobilie Hinzufügen Menü Item Aktion
        MIImmobilieHinzufuegen.addActionListener(e -> {
            if (!listModel.contains("Neue Immobilie")) {
                listModel.addElement("Neue Immobilie");
                LImmobilien.setSelectedValue("Neue Immobilie", true);
            } else {
                JOptionPane.showMessageDialog(null, "Bitte speichern Sie zuerst die neue Immobilie.");
            }
        });

        // Immobilie Löschen Menü Item Aktion
        MIImmobilieLoeschen.addActionListener(e -> {
            selectedImmoIndex = LImmobilien.getSelectedIndex();
            if (LImmobilien.getSelectedValue().equals("Neue Immobilie")) {
                listModel.remove(selectedImmoIndex);
            } else {
                db.deleteImmoInformation(String.valueOf(((Entries) LImmobilien.getSelectedValue()).getId()));
                if (FSuche.getText().equals("")) {
                    sortieren();
                } else {
                    suche(FSuche.getText());
                }
                BBearbeiten.setText("Bearbeiten");
                disableFields();
                clearFields();
            }
        });

        // Sortieren Bezeichnung Aufwärts Menü Item Aktion
        MIBezeichnungAufwaerts.addActionListener(e -> {
            CBSortieren.setSelectedItem("Bezeichnung");
            CBSortOrder.setSelectedItem("Aufwärts");
            createListModelSorted("ASC", "ImmBezeichnung");
        });

        // Sortieren Bezeichnung Abwärts Menü Item Aktion
        MIBezeichnungAbwaerts.addActionListener(e -> {
            CBSortieren.setSelectedItem("Bezeichnung");
            CBSortOrder.setSelectedItem("Abwärts");
            createListModelSorted("DESC", "ImmBezeichnung");
        });

        // Sortieren Baujahr Aufwärts Menü Item Aktion
        MIBaujahrAufwaerts.addActionListener(e -> {
            CBSortieren.setSelectedItem("Baujahr");
            CBSortOrder.setSelectedItem("Aufwärts");
            createListModelSorted("ASC", "ImmBaujahr");
        });

        // Sortieren Baujahr Abwärts Menü Item Aktion
        MIBaujahrAbwaerts.addActionListener(e -> {
            CBSortieren.setSelectedItem("Baujahr");
            CBSortOrder.setSelectedItem("Abwärts");
            createListModelSorted("DESC", "ImmBaujahr");
        });

        // Suchen Menü Item Aktion
        MISuchen.addActionListener(e -> {
            FSuche.requestFocus();
        });

        // Bearbeiten Menü Item Aktion
        MIBearbeiten.addActionListener(e -> {
            if (LImmobilien.getSelectedValue() != null) {
                selectedImmoIndex = LImmobilien.getSelectedIndex();
                if (BBearbeiten.getText().equals("Bearbeiten")) {
                    enableFields();
                    BBearbeiten.setText("Speichern");
                } else if (BBearbeiten.getText().equals("Speichern")) {

                    if ((LImmobilien.getSelectedValue()).equals("Neue Immobilie")) {
                        newImmo();
                    } else if (!(LImmobilien.getSelectedValue()).equals("Neue Immobilie")) {
                        updateImmo();
                    }
                    if (!FBeschreibung.getText().equals("") && !FBezeichnung.getText().equals("") && !FBaujahr.getText().equals("") && !FPLZ.getText().equals("") && !FOrtsname.getText().equals("") && !FHausnummer.getText().equals("") && !FStrasse.getText().equals("") && !FNutzflaeche.getText().equals("") && !FGrundFlaeche.getText().equals("") && CBArt.getSelectedIndex() != -1 && CBAusbau.getSelectedIndex() != -1 && CBZustand.getSelectedIndex() != -1) {
                        disableFields();
                        BBearbeiten.setText("Bearbeiten");
                        if (FSuche.getText().equals("")) {
                            sortieren();
                        } else {
                            suche(FSuche.getText());
                        }
                        LImmobilien.setSelectedIndex(selectedImmoIndex);
                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Felder aus.");
                    }
                }
            }
        });

        // Speichern Menü Item Aktion
        MISpeichern.addActionListener(e -> {
            if (LImmobilien.getSelectedValue() != null) {
                selectedImmoIndex = LImmobilien.getSelectedIndex();
                if (BBearbeiten.getText().equals("Bearbeiten")) {
                    enableFields();
                    BBearbeiten.setText("Speichern");
                } else if (BBearbeiten.getText().equals("Speichern")) {

                    if ((LImmobilien.getSelectedValue()).equals("Neue Immobilie")) {
                        newImmo();
                    } else if (!(LImmobilien.getSelectedValue()).equals("Neue Immobilie")) {
                        updateImmo();
                    }
                    if (!FBeschreibung.getText().equals("") && !FBezeichnung.getText().equals("") && !FBaujahr.getText().equals("") && !FPLZ.getText().equals("") && !FOrtsname.getText().equals("") && !FHausnummer.getText().equals("") && !FStrasse.getText().equals("") && !FNutzflaeche.getText().equals("") && !FGrundFlaeche.getText().equals("") && CBArt.getSelectedIndex() != -1 && CBAusbau.getSelectedIndex() != -1 && CBZustand.getSelectedIndex() != -1) {
                        disableFields();
                        BBearbeiten.setText("Bearbeiten");
                        if (FSuche.getText().equals("")) {
                            sortieren();
                        } else {
                            suche(FSuche.getText());
                        }
                        LImmobilien.setSelectedIndex(selectedImmoIndex);
                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Felder aus.");
                    }
                }
            }
        });

        // Item Auswahl Listener
        LImmobilien.addListSelectionListener(e -> {
            BBearbeiten.setEnabled(true);
            BDelete.setEnabled(true);
            BBearbeiten.setText("Bearbeiten");
            disableFields();
            if (String.valueOf(LImmobilien.getSelectedValue()).equals("Neue Immobilie")) {
                BBearbeiten.setText("Speichern");
                enableFields();
                clearFields();
                FBeschreibung.setText(null);
            } else {
                if (LImmobilien.getSelectedValue() != null) {
                    selectedImmo = ((Entries) LImmobilien.getSelectedValue()).getId();
                    insertDataintoPanel(String.valueOf(selectedImmo));
                }
            }
        });

        // Suche Listener
        FSuche.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    suche(FSuche.getText());
                }
            }
        });

        // Hinzufügen Listener
        BAddImmo.addActionListener(e -> {
            if (!listModel.contains("Neue Immobilie")) {
                listModel.addElement("Neue Immobilie");
                LImmobilien.setSelectedValue("Neue Immobilie", true);
            } else {
                JOptionPane.showMessageDialog(null, "Bitte speichern Sie zuerst die neue Immobilie.");
            }
        });

        // Löschen Listener
        BDelete.addActionListener(e -> {
            selectedImmoIndex = LImmobilien.getSelectedIndex();
            if (LImmobilien.getSelectedValue().equals("Neue Immobilie")) {
                listModel.remove(selectedImmoIndex);
            } else {
                db.deleteImmoInformation(String.valueOf(((Entries) LImmobilien.getSelectedValue()).getId()));
                if (FSuche.getText().equals("")) {
                    sortieren();
                } else {
                    suche(FSuche.getText());
                }
                BBearbeiten.setText("Bearbeiten");
                disableFields();
                clearFields();
            }
        });

        //Sortieren Listener
        CBSortieren.addActionListener(e -> sortieren());

        //Sortierreihenfolge Listener
        CBSortOrder.addActionListener(e -> sortieren());

        //Suche Typ Listener
        CBSearchType.addActionListener(e -> suche(FSuche.getText()));
    }

    // Daten in Panel einfügen
    private void insertDataintoPanel(String immoid) {
        db.getselecetedData(immoid);
        clearFields();
        FBezeichnung.setText(db.ImmBezeichnung);
        FStrasse.setText(db.ImmStrasse);
        FHausnummer.setText(db.ImmHausnummer);
        FPLZ.setText(db.ImmPLZ);
        FOrtsname.setText(db.ImmOrt);
        FGrundFlaeche.setText(String.valueOf(db.ImmGrundstuecksflaeche));
        FNutzflaeche.setText(String.valueOf(db.ImmNutzflaeche));
        FBaujahr.setText(String.valueOf(db.ImmBaujahr));
        CBArt.setSelectedItem(db.ImmArt);
        CBAusbau.setSelectedItem(db.ImmAusbau);
        CBZustand.setSelectedItem(db.ImmZustand);
        FBeschreibung.setText(db.ImmBeschreibung);

        double wert = 0;
        switch (db.ImmArt) {
            case "Einfamilienhaus" -> {
                wert = 500 * db.ImmGrundstuecksflaeche + 1000 * db.ImmNutzflaeche + 30000 * db.ImmZimmeranzahl;
                FZimmerzahl.setText(String.valueOf(db.ImmZimmeranzahl));
            }
            case "Mehrfamilienhaus" -> {
                wert = 500 * db.ImmGrundstuecksflaeche + 1000 * db.ImmNutzflaeche + 100000 * db.ImmAnzahlWohnungen;
                FWohnungsAnz.setText(String.valueOf(db.ImmAnzahlWohnungen));
            }
            case "Gewerbehaus" -> {
                wert = 200 * db.ImmGrundstuecksflaeche + 800 * db.ImmNutzflaeche + 1000 * db.ImmGroessterRaum;
                FGRaum.setText(String.valueOf(db.ImmGroessterRaum));

            }
        }
        wert = switch (db.ImmAusbau) {
            case "Einfach" -> wert * 0.8;
            case "Normal" -> wert;
            case "Luxeriös" -> wert * 1.2;
            default -> wert;
        };
        wert = switch (db.ImmZustand) {
            case "Sanierung" -> wert * 0.75;
            case "Normal" -> wert;
            case "Neuwertig" -> wert * 1.25;
            default -> wert;
        };
        FWert.setText(String.valueOf(wert));
    }

    // Sortierte Immobilienliste erstellen
    private void createListModelSorted(String type, String Name) {
        listModel.clear();
        db.generateListOrderBy(type, Name);
        for (Entries listitem : db.getEntry()) {
            listModel.addElement(listitem);
        }
    }

    // Liegenschaft Eingabefelder  aktivieren
    private void enableFields() {
        CBArt.setEnabled(true);
        FBezeichnung.setEnabled(true);
        FBeschreibung.setEnabled(true);
        FGrundFlaeche.setEnabled(true);
        FNutzflaeche.setEnabled(true);
        CBAusbau.setEnabled(true);
        FStrasse.setEnabled(true);
        CBZustand.setEnabled(true);
        FHausnummer.setEnabled(true);
        FOrtsname.setEnabled(true);
        FPLZ.setEnabled(true);
        FBaujahr.setEnabled(true);

        switch (String.valueOf(CBArt.getSelectedItem())) {
            case "Einfamilienhaus" -> FZimmerzahl.setEnabled(true);
            case "Mehrfamilienhaus" -> FWohnungsAnz.setEnabled(true);
            case "Gewerbehaus" -> FGRaum.setEnabled(true);
        }
    }

    // Liegenschaft Eingabefelder deaktivieren
    private void disableFields() {
        CBArt.setEnabled(false);
        FBezeichnung.setEnabled(false);
        FBeschreibung.setEnabled(false);
        FGrundFlaeche.setEnabled(false);
        FNutzflaeche.setEnabled(false);
        CBAusbau.setEnabled(false);
        FStrasse.setEnabled(false);
        CBZustand.setEnabled(false);
        FHausnummer.setEnabled(false);
        FOrtsname.setEnabled(false);
        FPLZ.setEnabled(false);
        FBaujahr.setEnabled(false);
        FZimmerzahl.setEnabled(false);
        FWohnungsAnz.setEnabled(false);
        FGRaum.setEnabled(false);
    }

    // Liegenschaft Eingabefelder löschen
    private void clearFields() {
        CBArt.setSelectedItem(null);
        FBezeichnung.setText(null);
        FBeschreibung.setText(null);
        FGrundFlaeche.setText(null);
        FNutzflaeche.setText(null);
        CBAusbau.setSelectedItem(null);
        FStrasse.setText(null);
        CBZustand.setSelectedItem(null);
        FHausnummer.setText(null);
        FOrtsname.setText(null);
        FPLZ.setText(null);
        FBaujahr.setText(null);
        FZimmerzahl.setText(null);
        FWohnungsAnz.setText(null);
        FGRaum.setText(null);
        FWert.setText(null);
    }

    // Neue Liegenschaft hinzufügen
    private void newImmo() {
        if (!FWohnungsAnz.getText().equals("")) {
            db.addImmo(FStrasse.getText(), FHausnummer.getText(), FOrtsname.getText(), FPLZ.getText(),
                    Objects.requireNonNull(CBArt.getSelectedItem()).toString(), FBezeichnung.getText(), Objects.requireNonNull(CBAusbau.getSelectedItem()).toString(),
                    Double.parseDouble(FGrundFlaeche.getText()), Objects.requireNonNull(CBZustand.getSelectedItem()).toString(), Double.parseDouble(FNutzflaeche.getText()),
                    FBaujahr.getText(), Integer.parseInt(FWohnungsAnz.getText()), 0, 0, FBeschreibung.getText());

        } else if (!FGRaum.getText().equals("")) {

            db.addImmo(FStrasse.getText(), FHausnummer.getText(), FOrtsname.getText(), FPLZ.getText(),
                    Objects.requireNonNull(CBArt.getSelectedItem()).toString(), FBezeichnung.getText(), Objects.requireNonNull(CBAusbau.getSelectedItem()).toString(),
                    Double.parseDouble(FGrundFlaeche.getText()), Objects.requireNonNull(CBZustand.getSelectedItem()).toString(), Double.parseDouble(FNutzflaeche.getText()),
                    FBaujahr.getText(), 0, Double.parseDouble(FGRaum.getText()), 0, FBeschreibung.getText());
        } else if (!FZimmerzahl.getText().equals("")) {

            db.addImmo(FStrasse.getText(), FHausnummer.getText(), FOrtsname.getText(), FPLZ.getText(),
                    Objects.requireNonNull(CBArt.getSelectedItem()).toString(), FBezeichnung.getText(), Objects.requireNonNull(CBAusbau.getSelectedItem()).toString(),
                    Double.parseDouble(FGrundFlaeche.getText()), Objects.requireNonNull(CBZustand.getSelectedItem()).toString(), Double.parseDouble(FNutzflaeche.getText()),
                    FBaujahr.getText(), 0, 0, Double.parseDouble(FZimmerzahl.getText()), FBeschreibung.getText());
        }
    }

    // Immobilie aktualisieren
    private void updateImmo() {
        if (!FWohnungsAnz.getText().equals("")) {
            db.changeImmoInformation(FStrasse.getText(), FHausnummer.getText(), FOrtsname.getText(), FPLZ.getText(),
                    Objects.requireNonNull(CBArt.getSelectedItem()).toString(), FBezeichnung.getText(), Objects.requireNonNull(CBAusbau.getSelectedItem()).toString(),
                    Double.parseDouble(FGrundFlaeche.getText()), Objects.requireNonNull(CBZustand.getSelectedItem()).toString(), Double.parseDouble(FNutzflaeche.getText()),
                    FBaujahr.getText(), Integer.parseInt(FWohnungsAnz.getText()), 0, 0, FBeschreibung.getText(), selectedImmo);

        } else if (!FGRaum.getText().equals("")) {

            db.changeImmoInformation(FStrasse.getText(), FHausnummer.getText(), FOrtsname.getText(), FPLZ.getText(),
                    Objects.requireNonNull(CBArt.getSelectedItem()).toString(), FBezeichnung.getText(), Objects.requireNonNull(CBAusbau.getSelectedItem()).toString(),
                    Double.parseDouble(FGrundFlaeche.getText()), Objects.requireNonNull(CBZustand.getSelectedItem()).toString(), Double.parseDouble(FNutzflaeche.getText()),
                    FBaujahr.getText(), 0, Double.parseDouble(FGRaum.getText()), 0, FBeschreibung.getText(), selectedImmo);

        } else if (!FZimmerzahl.getText().equals("")) {
            db.changeImmoInformation(FStrasse.getText(), FHausnummer.getText(), FOrtsname.getText(), FPLZ.getText(),
                    Objects.requireNonNull(CBArt.getSelectedItem()).toString(), FBezeichnung.getText(), Objects.requireNonNull(CBAusbau.getSelectedItem()).toString(),
                    Double.parseDouble(FGrundFlaeche.getText()), Objects.requireNonNull(CBZustand.getSelectedItem()).toString(), Double.parseDouble(FNutzflaeche.getText()),
                    FBaujahr.getText(), 0, 0, Double.parseDouble(FZimmerzahl.getText()), FBeschreibung.getText(), selectedImmo);
        }
    }

    // Immobilien sortieren
    private void sortieren() {
        String name = "";
        switch (String.valueOf(CBSortieren.getSelectedItem())) {
            case "Bezeichnung" -> name = "ImmBezeichnung";
            case "Baujahr" -> name = "ImmBaujahr";
        }
        switch (String.valueOf(CBSortOrder.getSelectedItem())) {
            case "Aufwärts" -> createListModelSorted("ASC", name);
            case "Abwärts" -> createListModelSorted("DESC", name);
        }
    }

    // Nach Bezeichnung oder Ortschaft suchen
    private void suche(String suche) {
        listModel.clear();

        String type = "";
        if (Objects.requireNonNull(CBSearchType.getSelectedItem()).toString().equals("Bezeichnung")) {
            type = "ImmBezeichnung";
        } else if (CBSearchType.getSelectedItem().toString().equals("Ortschaft")) {
            type = "ImmOrt";
        }
        db.suche(suche, type);
        ArrayList<Entries> result = db.getEntry();
        if (!result.isEmpty()) {
            for (Entries listitem : db.getEntry()) {
                listModel.addElement(listitem);
            }
        } else {
            listModel.addElement("Keine Ergebnisse");
        }
    }
}