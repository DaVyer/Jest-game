import java.util.ArrayList;
import java.util.List;

/**
 * Représente une pioche de cartes du jeu.
 *
 * <p>Gère la création, le mélange et la distribution des cartes pour une
 * partie. Certaines méthodes ici sont des stubs et doivent être complétées
 * </p>
 * 
 *
 * @author Tristan
 * @version 1.0
 */
public class Pioche {

    /** Identifiant de la pioche. */
    private int idPioche;

    /** Liste des cartes contenues dans la pioche. */
    private List<Carte> pioche;

    /**
     * Définit l'identifiant de la pioche.
     * 
     * A completer, je ne sais pas comment on gere des id.
     *
     * @param idPioche identifiant à affecter à la pioche
     */
    public void setIdPioche(int idPioche){
        this.idPioche = idPioche;
    }

    /**
     * Retourne l'identifiant de cette pioche.
     *
     * @return l'identifiant de la pioche (valeur entière, généralement >= 0)
     */
    public int getIdPioche(){
        return this.idPioche;
    }

    /**
     * Initialise la pioche comme une liste vide.
     *
     * <p>Cette méthode crée une nouvelle liste vide pour contenir les cartes.
     * Utiliser cette méthode lors de la création d'une pioche vide.</p>
     */
    public void setPioche(){
        this.pioche = new ArrayList<>();
    }

    /**
     * Retourne la liste des cartes de la pioche.
     *
     * <p>La liste retournée est la structure interne ; évitez de la modifier
     * directement si vous souhaitez préserver l'encapsulation.</p>
     *
     * @return la liste des cartes de la pioche (peut être `null` si non initialisée)
     */
    public List<Carte> getPioche(){
        return this.pioche;
    }

    /**
     * Ajoute une carte au sommet (ou à la fin) de la pioche.
     *
     * @param carte la carte à ajouter (doit être non null)
     */
    public void ajouterCartePioche(Carte carte){
        this.pioche.add(carte);
    }

    /**
     * Supprime une carte de la pioche.
     *
     * @param carte la carte à supprimer (si présente)
     */
    public void enleverCartePioche(Carte carte){
        this.pioche.remove(carte);
    }

    /**
     * Pioche une ou plusieurs cartes depuis la pioche.
     *
     * <p>À compléter : implémentation nécessaire. Cette méthode doit définir
     * la logique de tirage (par ex. piocher une carte simple ou plusieurs cartes
     * en fonction des règles du jeu) et retourner la liste des cartes piochées.</p>
     *
     * @return la liste des cartes piochées (ou une liste vide si aucune carte piochée)
     */
    public List<Carte> piocher(){
        List<Carte> carte;
        return carte;
    }

    /**
     * Mélange les cartes de la pioche.
     *
     * <p>À compléter : implémenter l'algorithme de mélange (par ex. Fisher–Yates)
     * pour réordonner aléatoirement les éléments de `pioche`.</p>
     */
    public void melanger(){

    }

    /**
     * Indique si la pioche est vide.
     *
     * <p>À compléter : retourner `true` si la pioche ne contient aucune carte,
     * sinon `false`.</p>
     *
     * @return `true` si la pioche est vide, `false` sinon
     */
    public boolean estVide(){
        return true;
    }
}