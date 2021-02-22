/* ------------------------------------------------------------------------
    HilfeGUI.java

    Diese Datei ist für die Erstellung des Hilfefensters zuständig.

    Author: Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian
    Date: 13.01.2021

    Copyright © 2021 Jan Kott, Laurel Mayer, Robin Gerstlauer, Yeshu Gudian. Alle Rechte vorbehalten.
-------------------------------------------------------------------------- */

package com.kftg.immocare;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class HilfeGUI extends JFrame {
    private JPanel PMain;
    private JTextPane LInfo;
    private JLabel LHilfe;

    public HilfeGUI() {
        add(PMain);
        setSize(400, 170);
        setLocationRelativeTo(null);
        setTitle("ImmoCare");

        StyledDocument doc = LInfo.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }
}
