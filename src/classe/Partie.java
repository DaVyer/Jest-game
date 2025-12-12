package classe;
import java.util.ArrayList;
import java.util.List;

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
    private int idPartie;
    private Pioche pioche;
    private List<Joueur> joueurs;
    private List<Trophee> trophees;

    /**
     * Constructeur
     *
     * @param id identifiant de la partie
     * @param joueurs liste initiale des joueurs participant à la partie
     */
    public Partie(int id, List<Joueur> joueurs){
        this.idPartie = id;

        this.joueurs = new ArrayList<>();
        for (Joueur joueur : joueurs) {
            this.joueurs.add(joueur);
        }

        //Jsp comment mettre pioche et trophee a reflechir
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
     * Définit la pioche utilisée pour la partie.
     *
     * @param pioche la pioche à associer à la partie
     */
    public void setPioche(Pioche pioche) {
        this.pioche = pioche;
    }

    /**
     * Retourne la liste des joueurs participant à la partie.
     *
     * @return la liste des joueurs (peut être vide)
     */
    public List<Joueur> getJoueurs() {
        return this.joueurs;
    }

    /**
     * Réinitialise la liste des joueurs à une liste vide.
     *
     * <p>Cette méthode ne prend pas de paramètre et remplace la liste interne
     * par une nouvelle liste vide.</p>
     */
    public void setJoueurs() {
        this.joueurs = new ArrayList<>();
    }

    /**
     * Retourne la liste des trophées de la partie.
     *
     * @return la liste des trophées (peut être vide)
     */
    public List<Trophee> getTrophees() {
        return this.trophees;
    }

    /**
     * Réinitialise la liste des trophées à une liste vide.
     *
     * <p>Cette méthode crée et associe une nouvelle liste vide à l'attribut interne.</p>
     */
    public void setTrophees() {
        this.trophees = new ArrayList<>();
    } 
    
    /**
     * Ajoute un joueur à la partie.
     *
     * @param joueur le joueur à ajouter
     */
    public void ajouterJoueurs(Joueur joueur){
        this.joueurs.add(joueur);
    }

    /**
     * Ajoute un trophée à la partie.
     *
     * @param trophee le trophée à ajouter
     */
    public void ajouterTrophee(Trophee trophee){
        this.trophees.add(trophee);
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
     * Pioche et retourne une liste de trophées pour la partie.
     *
     * <p>À définir : sélectionner les trophées selon les règles de la partie.</p>
     *
     * @return liste des trophées piochés (peut être vide)
     */
    private List<Trophee> piocherTrophees(){
        // A definir
        List<Trophee> trophee = new ArrayList<>();
        return trophee;
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