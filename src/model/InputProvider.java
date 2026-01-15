package model;

/**
 * Interface pour demander des choix à l'utilisateur.
 * 
 * <p>Permet de séparer la logique métier de la source d'entrée (console ou GUI).
 * Cette interface applique le pattern Strategy pour permettre différentes
 * implémentations d'interaction avec l'utilisateur.</p>
 * 
 * @author Gwendal Rodrigues
 * @version 03/01/2026
 * @see ConsoleInputProvider
 * @see view.GuiInputProvider
 */
public interface InputProvider {
    
    /**
     * Demande un nombre entre min et max à l'utilisateur.
     * @param message Le message à afficher
     * @param min Valeur minimale (inclusive)
     * @param max Valeur maximale (inclusive)
     * @return Le choix de l'utilisateur
     */
    int demanderChoixEntier(String message, int min, int max);
    
    /**
     * Affiche un message à l'utilisateur.
     * @param message Le message à afficher
     */
    void afficherMessage(String message);
}
