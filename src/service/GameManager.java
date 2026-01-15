package service;

import model.Partie;
import model.Joueur;

import java.util.List;

public class GameManager {
    private Partie partie;

    public synchronized boolean hasPartie() {
        return partie != null;
    }

    public synchronized Partie getPartie() {
        return partie;
    }

    public synchronized void nouvellePartie(List<Joueur> joueurs) {
        Partie p = new Partie();
        for (Joueur j : joueurs) {
            p.ajouterJoueurs(j);
        }
        this.partie = p;
    }

    public synchronized void chargerPartie(Partie p) {
        this.partie = p;
    }

    public synchronized void terminer() {
        this.partie = null;
    }
}
