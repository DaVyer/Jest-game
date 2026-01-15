package controller;

import java.util.Scanner;
import model.GameIO;

/**
 * Implémentation de {@link GameIO} pour l'interface console.
 * 
 * <p>Gère l'affichage sur la sortie standard et la lecture des entrées
 * depuis l'entrée standard via un {@link Scanner}.</p>
 * 
 * @author Gwendal Rodrigues, Tristan Crémonat
 * @version 03/01/2026
 * @see GameIO
 * @see model.ConsoleInputProvider
 */
public class ConsoleIO implements GameIO {
    private final Scanner scanner;

    public ConsoleIO(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {   // ✅ AJOUT
        return scanner;
    }

    @Override
    public void println(String s) {
        System.out.println(s);
    }

    @Override
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
