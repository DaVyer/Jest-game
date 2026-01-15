package service;

import model.Partie;
import model.Joueur;

import java.util.List;

/**
 * Gestionnaire centralisé de la partie en cours.
 * 
 * <p>Cette classe sert de point de synchronisation entre l'interface CLI
 * et l'interface GUI, assurant qu'une seule partie est active à la fois
 * et que les deux interfaces travaillent sur la même instance de partie.</p>
 * 
 * <p>Toutes les méthodes sont synchronisées pour garantir la cohérence
 * en environnement multi-thread.</p>
 * 
 * @author Gwendal Rodrigues, Tristan Crémonat
 * @version 03/01/2026
 */
public class GameManager {
    /** La partie actuellement en cours (peut être null). */
    private Partie partie;

    /**
     * Vérifie si une partie est en cours.
     * 
     * @return true si une partie existe, false sinon
     */
    public synchronized boolean hasPartie() {
        return partie != null;
    }

    /**
     * Récupère la partie en cours.
     * 
     * @return la partie actuelle, ou null si aucune partie n'est en cours
     */
    public synchronized Partie getPartie() {
        return partie;
    }

    /**
     * Crée une nouvelle partie avec la liste de joueurs spécifiée.
     * 
     * <p>Crée une instance de {@link Partie}, y ajoute tous les joueurs
     * de la liste et définit cette partie comme partie courante.</p>
     * 
     * @param joueurs la liste des joueurs participant à la partie
     */
    public synchronized void nouvellePartie(List<Joueur> joueurs) {
        Partie p = new Partie();
        for (Joueur j : joueurs) {
            p.ajouterJoueurs(j);
        }
        this.partie = p;
    }

    /**
     * Charge une partie existante.
     * 
     * <p>Remplace la partie courante par la partie fournie,
     * généralement restaurée depuis une sauvegarde.</p>
     * 
     * @param p la partie à charger
     */
    public synchronized void chargerPartie(Partie p) {
        this.partie = p;
    }

    /**
     * Termine la partie en cours.
     * 
     * <p>Supprime la référence à la partie courante, permettant
     * de démarrer une nouvelle partie.</p>
     */
    public synchronized void terminer() {
        this.partie = null;
    }
}
