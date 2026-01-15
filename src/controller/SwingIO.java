package controller;

import javax.swing.*;
import model.GameIO;

/**
 * Implémentation de {@link GameIO} pour l'interface graphique Swing.
 * 
 * <p>Gère l'affichage de messages et la lecture d'entrées via des boîtes
 * de dialogue Swing ({@link JOptionPane}).</p>
 * 
 * @author Gwendal Rodrigues, Tristan Crémonat
 * @version 03/01/2026
 * @see GameIO
 */
public class SwingIO implements GameIO {
    private final java.awt.Component parent;
    public SwingIO(java.awt.Component parent) { this.parent = parent; }

    @Override public void println(String s) { JOptionPane.showMessageDialog(parent, s); }
    @Override public String readLine(String prompt) { return JOptionPane.showInputDialog(parent, prompt); }
}

