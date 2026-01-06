package controller;

import model.GameIO;

import javax.swing.*;

public class SwingIO implements GameIO {
    private final java.awt.Component parent;
    public SwingIO(java.awt.Component parent) { this.parent = parent; }

    @Override public void println(String s) { JOptionPane.showMessageDialog(parent, s); }
    @Override public String readLine(String prompt) { return JOptionPane.showInputDialog(parent, prompt); }
}

