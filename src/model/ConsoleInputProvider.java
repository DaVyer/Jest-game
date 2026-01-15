package model;

import java.util.Scanner;

/**
 * Implémentation de {@link InputProvider} pour l'interface console.
 * 
 * <p>Utilise un {@link Scanner} pour lire les entrées au clavier
 * et afficher les messages sur la sortie standard.</p>
 * 
 * @author Gwendal Rodrigues
 * @version 03/01/2026
 * @see InputProvider
 */
public class ConsoleInputProvider implements InputProvider {
    
    /** Le scanner pour lire les entrées clavier. */
    private final Scanner scanner;
    
    /**
     * Constructeur du ConsoleInputProvider.
     * 
     * @param scanner le scanner à utiliser pour lire les entrées
     */
    public ConsoleInputProvider(Scanner scanner) {
        this.scanner = scanner;
    }
    
    /**
     * Demande un nombre entier à l'utilisateur via la console.
     * 
     * <p>Redemande jusqu'à ce que l'utilisateur entre un nombre valide
     * dans la plage spécifiée. Affiche un message d'erreur si l'entrée
     * n'est pas un nombre ou est hors limites.</p>
     * 
     * @param message le message à afficher à l'utilisateur
     * @param min la valeur minimale (inclusive)
     * @param max la valeur maximale (inclusive)
     * @return le choix valide de l'utilisateur
     */
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
    
    /**
     * Affiche un message sur la sortie standard.
     * 
     * @param message le message à afficher
     */
    @Override
    public void afficherMessage(String message) {
        System.out.println(message);
    }
}
