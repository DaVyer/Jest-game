package classe;

import java.util.List;
import java.util.Scanner;

/**
 * Interface définissant une stratégie de jeu.
 * 
 * <p>Implémente le pattern Strategy pour permettre différentes
 * façons de jouer (humain, robot aléatoire, etc.).</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 */
public interface StrategieJoueur {

    /**
     * Crée une offre pour le joueur.
     * 
     * @param joueur le joueur qui crée l'offre
     * @param scanner le scanner pour les interactions
     * @return l'offre créée
     */
    Offre faireOffre(Joueur joueur, Scanner scanner);

    /**
     * Choisit une offre parmi celles disponibles.
     * 
     * @param offresDisponibles la liste des offres disponibles
     * @param joueur le joueur qui fait le choix
     * @param scanner le scanner pour les interactions
     * @return l'offre choisie
     */
    Offre choisirOffre(List<Offre> offresDisponibles, Joueur joueur, Scanner scanner);

    /**
     * Choisit une carte dans une offre.
     * 
     * @param offre l'offre dans laquelle choisir
     * @param joueur le joueur qui fait le choix
     * @param scanner le scanner pour les interactions
     * @return la carte choisie
     */
    Carte choisirCarteOffre(Offre offre, Joueur joueur, Scanner scanner);
}
