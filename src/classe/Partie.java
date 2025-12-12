package classe;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Représente la partie, du début jusqu'à la fin de la partie
 *
 * <p>Elle permet de gerer le tour des joueurs, la mise en place des offres, des pioches....
 * </p>
 * 
 *
 * @author Tristan
 * @version %I%, %G%
 */

public class Partie{
    private final static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private int idPartie;
    private Pioche pioche;
    private LinkedList<Joueur> joueurs;
    private LinkedList<Carte> trophees;

    /**
     * Constructeur
     *
     * @param id identifiant de la partie
     * @param joueurs liste initiale des joueurs participant à la partie
     */
    public Partie(){
        this.idPartie = ID_GENERATOR.getAndIncrement();
        System.out.println("\nNouvelle partie créé :\n\t" + this.getIdPartie() + "\n\n===============" ); 

        this.pioche = new Pioche();
        this.pioche.setPioche();
        System.out.println("\nPioche créé : \n" + this.pioche.getPioche() +"\n\n==============="); // A supprimer, on ne veut pas voir la pioche a chaque parties.

        this.setTrophees();
        System.out.println("\nTrophé Tiré : "); 
        System.out.println("Trophé 1 : " + this.trophees.get(0) + " de condition : " + this.trophees.get(0).getTrophee());
        System.out.println("Trophé 2 : " + this.trophees.get(1) + " de condition : " + this.trophees.get(1).getTrophee());
        System.out.println("Pioche : " + this.getPioche().getPioche());
        System.out.println("\n===============\n"); 

        this.setJoueurs();
    }

    /**
     * Retourne l'identifiant de la partie.
     *
     * @return l'identifiant de la partie
     */
    public int getIdPartie() {
        return this.idPartie;
    }

    /**
     * Définit l'identifiant de la partie.
     *
     * @param idPartie nouvel identifiant de la partie
     */
    public void setIdPartie(int idPartie) {
        this.idPartie = idPartie;
    }

    /**
     * Retourne la pioche associée à la partie.
     *
     * @return la pioche utilisée dans la partie (peut être `null`)
     */
    public Pioche getPioche() {
        return this.pioche;
    }

    /**
     * Retourne la liste des joueurs participant à la partie.
     *
     * @return la liste des joueurs (peut être vide)
     */
    public LinkedList<Joueur> getJoueurs() {
        return this.joueurs;
    }

    /**
     * Réinitialise la liste des joueurs à une liste vide.
     *
     * <p>Cette méthode ne prend pas de paramètre et remplace la liste interne
     * par une nouvelle liste vide.</p>
     */
    public void setJoueurs() {
        this.joueurs = new LinkedList<>();
    }

    /**
     * Retourne la liste des trophées de la partie.
     *
     * @return la liste des trophées (peut être vide)
     */
    public LinkedList<Carte> getTrophees() {
        return this.trophees;
    }

    /**
     * Réinitialise la liste des trophées à une liste vide.
     *
     * <p>Cette méthode crée et associe une nouvelle liste vide à l'attribut interne.</p>
     */
    public void setTrophees() {
        this.trophees = new LinkedList<>();
        this.trophees.add(this.pioche.piocher());
        this.trophees.add(this.pioche.piocher());

    } 
    
    /**
     * Ajoute un joueur à la partie.
     *
     * @param joueur le joueur à ajouter
     */
    public void ajouterJoueurs(Joueur joueur){
        this.joueurs.add(joueur);
        System.out.println("\t Joueur " + joueur.getIdJoueur() + " ajouté" + "\n\n===============\n");
    }

    /**
     * Supprime un joueur de la partie.
     *
     * @param joueur le joueur à supprimer
     */
    public void supprimerJoueur(Joueur joueur){
        this.joueurs.remove(joueur);
    }
    /**
     * Supprime un trophée de la partie.
     *
     * @param trophee le trophée à supprimer
     */
    public void supprimerTrophee(Trophee trophee){
        this.trophees.remove(trophee);
    }

    /**
     * Initialise la partie (prépare les pioches, offres, et autres structures).
     *
     * <p>À définir : comportement complet d'initialisation selon les règles du jeu.</p>
     */
    public void initialiserPartie(){
        // A definir
    }

    /**
     * Termine la partie et effectue les opérations de fin (affichage, sauvegarde...).
     *
     * <p>À définir : logique de fin de partie (calculs finaux, affichage, sauvegarde).</p>
     */
    public void finPartie(){
        // A definir
    }
    /**
     * Affiche ou prépare le résultat/score de la partie.
     *
     * <p>À définir : format et calcul du score final; actuellement cette méthode
     * affiche une chaîne vide.</p>
     */
    private void resultat(){
        // A definir
        // J'ai modifié et mis ca en void et ca affichera le score

        String score = "";
        System.out.println(score);
    }

    /**
     * Calcule les scores des joueurs selon les règles du jeu.
     *
     * <p>À définir : implémenter le calcul des scores. La méthode est
     * actuellement `void` ; adapter la signature si un retour est nécessaire.</p>
     */
    public void calculerScore(){
        // A definir
        // On est sur que c'est un void ?
    }

    // Je mets pas le accept de visitor on verra ca direct au moment du patron de conception


}