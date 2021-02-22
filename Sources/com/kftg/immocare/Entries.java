/* ------------------------------------------------------------------------
    Entries.java

    Diese Datei ist für die Bezeichnungszuordnung innerhalb der Immobilienliste zuständig.

    Author: Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian
    Date: 06.01.2021

    Copyright © 2021 Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian. Alle Rechte vorbehalten.
-------------------------------------------------------------------------- */

package com.kftg.immocare;

public class Entries {
    private final int Id;
    private final String Description;

    public Entries(int id, String description) {
        Id = id;
        Description = description;
    }

    public String toString() {
        return Description;
    }

    public int getId() {
        return Id;
    }
}
