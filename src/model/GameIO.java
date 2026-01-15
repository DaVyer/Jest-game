package model;

/**
 * Interface pour l'affichage et la lecture d'entrées lors du jeu.
 * 
 * <p>Abstrait les opérations d'entrée/sortie pour permettre différentes
 * implémentations (console, GUI) sans modifier la logique du jeu.</p>
 * 
 * @author Gwendal Rodrigues
 * @version 03/01/2026
 */
public interface GameIO {
    void println(String s);
    String readLine(String prompt);
}

