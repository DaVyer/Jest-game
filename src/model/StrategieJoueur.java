package model;

import java.util.List;

/**
 * Interface définissant une stratégie de jeu.
 * 
 * <p>Implémente le pattern Strategy pour permettre différentes
 * façons de jouer (humain, robot aléatoire, etc.).</p>
 * 
 * @author Gwendal Rodrigues
 * @version 03/01/2026
 */
public interface StrategieJoueur {

    /**
     * Crée une offre pour le joueur.
     * 
     * @param joueur le joueur qui crée l'offre
     * @param input le fournisseur d'entrées (console ou GUI)
     * @return l'offre créée
     */
    Offre faireOffre(Joueur joueur, InputProvider input);

    /**
     * Choisit une offre parmi celles disponibles.
     * 
     * @param offresDisponibles la liste des offres disponibles
     * @param joueur le joueur qui fait le choix
     * @param input le fournisseur d'entrées (console ou GUI)
     * @return l'offre choisie
     */
    Offre choisirOffre(List<Offre> offresDisponibles, Joueur joueur, InputProvider input);

    /**
     * Choisit une carte dans une offre.
     * 
     * @param offre l'offre dans laquelle choisir
     * @param joueur le joueur qui fait le choix
     * @param input le fournisseur d'entrées (console ou GUI)
     * @return la carte choisie
     */
    Carte choisirCarteOffre(Offre offre, Joueur joueur, InputProvider input);
}
