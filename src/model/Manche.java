package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une manche (round) du jeu Jest.
 *
 * <p>Une manche est une unité de jeu au cours de laquelle les joueurs
 * placent des offres et concourent pour les jests disponibles.
 * Cette classe gère le numéro de la manche en cours.</p>
 * 
 * @author Tristan Crémonat, Gwendal Rodrigues
 * @version 03/01/2026
 */
public class Manche {
    /** Le numéro de la manche courante. */
    private int numero = 0;

    /**
     * Constructeur de la manche.
     * 
     * <p>Initialise une nouvelle manche avec le numéro 0.</p>
     */
    public Manche(){
        List<Offre> offres = new ArrayList<>();
    }

    /**
     * Retourne le numéro de la manche.
     *
     * @return le numéro de la manche
     */
    public int getNumero() {
        return this.numero;
    }

    /**
     * Incrémente le numéro de la manche de 1.
     */
    public void incrementNumero() {
        this.numero++;
    }

    /**
     * Définit le numéro de la manche.
     * 
     * @param numeroManche le numéro de manche à définir
     */
    public void setNumero(int numeroManche) {
        this.numero = numeroManche;
    }
}
