import java.util.ArrayList;
import java.util.List;

/**
 * Représente une manche pendant la partie
 *
 * <p>Elle permet de gerer le tour des joueurs, la mise en place des offres, des pioches....
 * </p>
 * 
 *
 * @author Tristan
 * @version 1.0
 */
public class Manche {
    private int numero;
    private List<Offre> offres;

    /**
     * Le constructeur
     *
     * @param numero numéro de la manche (commence généralement à 1)
     */
    public Manche(int numero){
        this.numero = numero;
        this.offres = new ArrayList<>();
    }

    /**
     * Retourne le numéro de la manche.
     *
     * @return le numéro de la manche
     */
    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Retourne la liste des offres de la manche.
     *
     * @return la liste des offres (peut être vide)
     */
    public List<Offre> getOffres() {
        return offres;
    }

    /**
     * Initialise ou remplace la liste des offres pour cette manche.
     *
     * <p>Remarque : la méthode actuelle initialise la liste vide</p>
     *
     * @param offres liste d'offres à associer à la manche
     */
    public void setOffres() {
        this.offres = new ArrayList<>();
    }

    /**
     * Ajoute une offre à la manche.
     *
     * @param offre l'offre à ajouter
     */
    public void ajouterOffre(Offre offre){
        this.offres.add(offre);
    }

    /**
     * Supprime une offre de la manche.
     *
     * @param offre l'offre à retirer
     */
    public void enleverOffre(Offre offre){
        this.offres.remove(offre);
    }

    /**
     * Distribue des cartes depuis une pioche à un joueur.
     *
     * <p>À définir</p>
     *
     * @param pioche la pioche source des cartes
     * @param joueur le joueur qui reçoit les cartes
     * @return la liste des cartes distribuées au joueur (peut être vide)
     */
    public List<Carte> distribuer(Pioche pioche, Joueur joueur){ //Ici on met pioche ou carte, on peut aussi mettre pioche et carte
        // À définir
        return new ArrayList<>();
    }

    /**
     * Collecte les offres à la fin d'une phase ou d'une manche.
     *
     * <p>À définir</p>
     *
     * @param joueurs la liste des joueurs participant à la collecte
     */
    public void collecterOffre(List<Joueur> joueurs){
        // A definir
    }

    /**
     * Exécute un tour de jeu pour la liste de joueurs fournie.
     *
     * <p>À définir</p>
     *
     * @param joueurs la liste de joueurs qui jouent ce tour
     */
    public void jouerTour(List<Joueur> joueurs){
        // A definir
    }

    /**
     * Termine la manche en réalisant les opérations de fin de manche.
     *
     * <p>À définir : actions attendues en fin de manche</p>
     */
    public void terminerManche(){
        // A definir
    }
    
}
