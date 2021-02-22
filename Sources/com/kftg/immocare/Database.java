/* ------------------------------------------------------------------------
    Database.java

    Diese Datei ist für alle Datenbankbefehle inherlab der ImmoCare-Anwendung zuständig.

    Author: Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian
    Date: 15.11.2020

    Copyright © 2021 Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian. Alle Rechte vorbehalten.
-------------------------------------------------------------------------- */

package com.kftg.immocare;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private final String Database = "PATH TO DATABASE";
    private final String DUser = "USERNAME";
    private final String DPassword = "PASSWORD";

    public static String LUser;
    private String LPassword;
    public ArrayList<Entries> Entries = new ArrayList<>();
    public String ImmStrasse;
    public String ImmHausnummer;
    public String ImmOrt;
    public String ImmPLZ;
    public String ImmArt;
    public String ImmBezeichnung;
    public String ImmAusbau;
    public double ImmGrundstuecksflaeche;
    public String ImmZustand;
    public double ImmNutzflaeche;
    public String ImmBaujahr;
    public int ImmAnzahlWohnungen;
    public double ImmGroessterRaum;
    public double ImmZimmeranzahl;
    public String ImmBeschreibung;

    private Connection Con;
    private Statement Stmt;
    private ResultSet Rset;
    private final String encryptionKey = "ENCRYPTION KEY";

    public Database() {
        generateList();
    }

    public boolean sendResult() throws SQLException {
        return Rset.next();
    }

    // Datenbankverbindung
    private void connectDatabase(String query) {
        try {
            Con = DriverManager.getConnection(Database, DUser, DPassword);
            Stmt = Con.createStatement();
            Rset = Stmt.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Immobilien in Liste anzeigen
    public void generateList() {
        String query = "SELECT * FROM TImmobilien;";
        try {
            Con = DriverManager.getConnection(Database, DUser, DPassword);
            Stmt = Con.createStatement();
            Rset = Stmt.executeQuery(query);
            Entries.clear();
            while (Rset.next()) {
                Entries.add(new Entries((Integer) Rset.getObject("ImmId"), (String) Rset.getObject("ImmBezeichnung")));
            }
            Con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Imobilien Sortieren
    public void generateListOrderBy(String type, String Name) {
        String query = "SELECT * FROM TImmobilien ORDER BY " + Name + " " + type + ";";
        try {
            Con = DriverManager.getConnection(Database, DUser, DPassword);
            Stmt = Con.createStatement();
            Rset = Stmt.executeQuery(query);
            Entries.clear();
            while (Rset.next()) {
                Entries.add(new Entries((Integer) Rset.getObject("ImmId"), (String) Rset.getObject("ImmBezeichnung")));
            }
            Con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Suche nach Bezeichnung oder Ortschaft
    public void suche(String name, String type) {
        String query = "SELECT * FROM TImmobilien WHERE " + type + " SOUNDS LIKE '" + name + "' OR LOCATE('" + name + "', ImmBezeichnung);";
        try {
            Con = DriverManager.getConnection(Database, DUser, DPassword);
            Stmt = Con.createStatement();
            Rset = Stmt.executeQuery(query);
            Entries.clear();
            while (Rset.next()) {
                Entries.add(new Entries((Integer) Rset.getObject("ImmId"), (String) Rset.getObject("ImmBezeichnung")));
            }
            Con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Erhalte Einträge der Array Liste
    public ArrayList<Entries> getEntry() {
        return Entries;
    }

    // Updates ausführen
    private void executeUpdate(String query) {
        try {
            Con = DriverManager.getConnection(Database, DUser, DPassword);
            Stmt = Con.createStatement();
            Stmt.executeUpdate(query);
            Con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Überprüfung Anmeldedaten
    public void checkLogin(String User, String Password) {
        LUser = User;
        LPassword = Password;
        String query = "SELECT UserId FROM TUsers WHERE UserName ='" + LUser + "'&& UserPassword = AES_ENCRYPT('" + LPassword + "','" + encryptionKey + "');";
        connectDatabase(query);
    }

    // Erhalte ausgewählte Immobilie
    public void getselecetedData(String immId) {
        String query = "SELECT * FROM TImmobilien WHERE ImmId =" + immId + ";";
        connectDatabase(query);
        try {
            Con = DriverManager.getConnection(Database, DUser, DPassword);
            Stmt = Con.createStatement();
            Rset = Stmt.executeQuery(query);
            while (Rset.next()) {
                ImmStrasse = (String) Rset.getObject("ImmStrasse");
                ImmHausnummer = (String) Rset.getObject("ImmHausNr");
                ImmOrt = (String) Rset.getObject("ImmOrt");
                ImmPLZ = (String) Rset.getObject("ImmPLZ");
                ImmArt = (String) Rset.getObject("ImmArt");
                ImmBezeichnung = (String) Rset.getObject("ImmBezeichnung");
                ImmAusbau = (String) Rset.getObject("ImmAusbau");
                ImmGrundstuecksflaeche = Rset.getBigDecimal("ImmGrundstuecksflaeche").doubleValue();
                ImmZustand = (String) Rset.getObject("ImmZustand");
                ImmNutzflaeche = Rset.getBigDecimal("ImmNutzflaeche").doubleValue();
                ImmBaujahr = (String) Rset.getObject("ImmBaujahr");
                ImmBeschreibung = (String) Rset.getObject("ImmBeschreibung");

                if (Rset.getBigDecimal("ImmZimmeranzahl") != null) {
                    ImmZimmeranzahl = Rset.getBigDecimal("ImmZimmeranzahl").doubleValue();
                }
                if (Rset.getBigDecimal("ImmAnzahlWohnungen") != null) {
                    ImmAnzahlWohnungen = Rset.getInt("ImmAnzahlWohnungen");
                }
                if (Rset.getObject("ImmGroessterRaum") != null) {
                    ImmGroessterRaum = Rset.getInt("ImmGroessterRaum");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Passwort ändern
    public void changePassword(String nPW) {
        String query = "UPDATE TUsers SET UserPassword = AES_ENCRYPT('" + nPW + "','" + encryptionKey + "') WHERE UserName = '" + LUser + "'";
        executeUpdate(query);
    }

    // Immobilie aktualisieren
    public void changeImmoInformation(String Strasse, String Hausnummer, String Ort, String PLZ, String Art, String Bezeichnung,
                                      String Ausbau, double Grundstuecksflaeche, String Zustand, double Nutzflaeche, String Baujahr,
                                      int AnzahlWohnungen, double GroessterRaum, double Zimmeranzahl, String Beschreibung, int Id) {

        assignImmoInformation(Strasse, Hausnummer, Ort, PLZ, Art, Bezeichnung, Ausbau, Grundstuecksflaeche, Zustand, Nutzflaeche, Baujahr, AnzahlWohnungen, GroessterRaum, Zimmeranzahl, Beschreibung);

        String query = "UPDATE TImmobilien SET ImmStrasse = \"" + ImmStrasse + "\" , ImmHausNr = \"" + ImmHausnummer + "\", ImmOrt =\"" + ImmOrt + "\", ImmPLZ = \"" + ImmPLZ + "\", ImmArt = \"" + ImmArt + "\", ImmBezeichnung = \"" + ImmBezeichnung + "\", ImmBeschreibung = \"" + ImmBeschreibung + "\", ImmAusbau = \"" + ImmAusbau + "\", ImmGrundstuecksflaeche=" + ImmGrundstuecksflaeche + "," +
                "ImmZustand = \"" + ImmZustand + "\", ImmNutzflaeche= " + ImmNutzflaeche + ", ImmBaujahr = " + ImmBaujahr + ", ImmAnzahlWohnungen=" + ImmAnzahlWohnungen + ", ImmGroessterRaum =" + ImmGroessterRaum + ", ImmZimmeranzahl= " + ImmZimmeranzahl + "" +
                "WHERE ImmId = \"" + Id + "\";";
        executeUpdate(query);
    }

    // Immobilien Informationen zuordnen
    private void assignImmoInformation(String Strasse, String Hausnummer, String Ort, String PLZ, String Art, String Bezeichnung, String Ausbau, double Grundstuecksflaeche, String Zustand, double Nutzflaeche, String Baujahr, int AnzahlWohnungen, double GroessterRaum, double Zimmeranzahl, String Beschreibung) {
        ImmStrasse = Strasse;
        ImmHausnummer = Hausnummer;
        ImmOrt = Ort;
        ImmPLZ = PLZ;
        ImmArt = Art;
        ImmBezeichnung = Bezeichnung;
        ImmAusbau = Ausbau;
        ImmGrundstuecksflaeche = Grundstuecksflaeche;
        ImmZustand = Zustand;
        ImmNutzflaeche = Nutzflaeche;
        ImmBaujahr = Baujahr;
        ImmAnzahlWohnungen = AnzahlWohnungen;
        ImmGroessterRaum = GroessterRaum;
        ImmZimmeranzahl = Zimmeranzahl;
        ImmBeschreibung = Beschreibung;
    }

    // Inhalt löschen
    public void deleteImmoInformation(String immId) {
        String query = "DELETE FROM TImmobilien WHERE ImmId = " + immId + ";";
        executeUpdate(query);
    }

    // Passwort überprüfen
    public boolean checkPassword(String Password) throws SQLException {
        LPassword = Password;
        String query = "SELECT UserId FROM TUsers WHERE UserName ='" + LUser + "' && UserPassword = AES_ENCRYPT('" + LPassword + "','" + encryptionKey + "');";
        connectDatabase(query);
        return Rset.next();
    }

    // Immobilie hinzufügen
    public void addImmo(String Strasse, String Hausnummer, String Ort, String PLZ, String Art, String Bezeichnung,
                        String Ausbau, double Grundstuecksflaeche, String Zustand, double Nutzflaeche, String Baujahr,
                        int AnzahlWohnungen, double GroessterRaum, double Zimmeranzahl, String Beschreibung) {
        String query;
        assignImmoInformation(Strasse, Hausnummer, Ort, PLZ, Art, Bezeichnung, Ausbau, Grundstuecksflaeche, Zustand, Nutzflaeche, Baujahr, AnzahlWohnungen, GroessterRaum, Zimmeranzahl, Beschreibung);
        query = "INSERT INTO TImmobilien VALUES (NULL,'" + ImmStrasse + "','" + ImmHausnummer + "','" + ImmOrt + "','" + ImmPLZ + "','" + ImmArt + "','" + ImmBezeichnung + "','" + ImmAusbau + "','" + ImmGrundstuecksflaeche + "','" + ImmZustand + "','" + ImmNutzflaeche + "','" + ImmBaujahr + "',";

        if (ImmAnzahlWohnungen != 0) {
            query = query + "'" + ImmAnzahlWohnungen + "',NULL,NULL,'" + ImmBeschreibung + "');";
        } else if (ImmGroessterRaum != 0) {
            query = query + "NULL,'" + ImmGroessterRaum + "',Null,'" + ImmBeschreibung + "');";
        } else if (ImmZimmeranzahl != 0) {
            query = query + "NULL,NULL,'" + ImmZimmeranzahl + "','" + ImmBeschreibung + "');";
        }
        executeUpdate(query);
    }
}
                                                                                                                      