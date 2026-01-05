package model;

import java.util.Scanner;

/**
 * Implémentation pour la console.
 * Utilise Scanner pour lire au clavier.
 */
public class ConsoleInputProvider implements InputProvider {
    
    private final Scanner scanner;
    
    public ConsoleInputProvider(Scanner scanner) {
        this.scanner = scanner;
    }
    
    @Override
    public int demanderChoixEntier(String message, int min, int max) {
        int choix = -1;
        while (choix < min || choix > max) {
            System.out.print(message);
            try {
                choix = Integer.parseInt(scanner.nextLine());
                if (choix < min || choix > max) {
                    System.out.println("Choix invalide. Doit être entre " + min + " et " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
        return choix;
    }
    
    @Override
    public void afficherMessage(String message) {
        System.out.println(message);
    }
}
