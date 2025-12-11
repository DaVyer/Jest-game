package classe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

/**
 * Représente une pioche de cartes du jeu.
 *
 * <p>Gère la création, le mélange et la distribution des cartes pour une
 * partie. Certaines méthodes ici sont des stubs et doivent être complétées
 * </p>
 * 
 *
 * @author Tristan Crémonat, Gwendal Rodrigues
 * @version %I%, %G%
 */
public class Pioche {
    private final static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    /** Identifiant de la pioche. */
    private final int idPioche;

    /** Liste des cartes contenues dans la pioche. */
    private LinkedList<Carte> pioche;

    /**
     * Constructeur de pioche
     * 
     * @param id -- id de la Pioche.
     */
    public Pioche(){
        idPioche = ID_GENERATOR.getAndIncrement();
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
     * Utiliser cette méthode lors de la création d'une pioche vide.
     * Une fois la pioche crée, la méthode initPioche() est appelé.
     * La pioche courante est ensuite mélangé.</p>
     */
    public void setPioche(){
        this.pioche = new LinkedList<>();
        initPioche();
        melanger();
    }

    /**
     * Remplie la pioche avec toutes les combinaisons de carte et de valeur possible présente dans CouleurCarte et ValeurCarte.
     *
     * <p>Cette méthode prend la liste vide courante et insert toutes les combinaisons de cartes possibles.
     * Cette méthode est utilisé lors de l'appel de la méthode setPioche().</p>
     */
    public void initPioche(){
        this.pioche.add(new Carte(ValeurCarte.AS, CouleurCarte.JOKER, ConditionTrophee.MEILLEURJEST)); // Je ne savais pas quelle valeur mettre pour le joker, j'ai mis l'as, mais on s'en fou
        this.pioche.add(new Carte(ValeurCarte.AS, CouleurCarte.COEUR, ConditionTrophee.JOKER));
        this.pioche.add(new Carte(ValeurCarte.QUATRE, CouleurCarte.PIQUE, ConditionTrophee.PLUSBASSE_TREFLE));
        this.pioche.add(new Carte(ValeurCarte.QUATRE, CouleurCarte.TREFLE, ConditionTrophee.PLUSBASSE_PIQUE));
        this.pioche.add(new Carte(ValeurCarte.DEUX, CouleurCarte.COEUR, ConditionTrophee.JOKER));
        this.pioche.add(new Carte(ValeurCarte.QUATRE, CouleurCarte.CARREAU, ConditionTrophee.MEILLEURJESTSANSJOKER));
        this.pioche.add(new Carte(ValeurCarte.QUATRE, CouleurCarte.COEUR, ConditionTrophee.JOKER));
        this.pioche.add(new Carte(ValeurCarte.TROIS, CouleurCarte.COEUR, ConditionTrophee.JOKER));
        this.pioche.add(new Carte(ValeurCarte.AS, CouleurCarte.PIQUE, ConditionTrophee.PLUSHAUTE_TREFLE));
        this.pioche.add(new Carte(ValeurCarte.AS, CouleurCarte.TREFLE, ConditionTrophee.PLUSHAUTE_PIQUE));
        this.pioche.add(new Carte(ValeurCarte.AS, CouleurCarte.CARREAU, ConditionTrophee.MAJORITE_4));
        this.pioche.add(new Carte(ValeurCarte.DEUX, CouleurCarte.PIQUE, ConditionTrophee.MAJORITE_3));
        this.pioche.add(new Carte(ValeurCarte.DEUX, CouleurCarte.TREFLE, ConditionTrophee.PLUSBASSE_COEUR));
        this.pioche.add(new Carte(ValeurCarte.DEUX, CouleurCarte.CARREAU, ConditionTrophee.PLUSHAUTE_CARREAU));
        this.pioche.add(new Carte(ValeurCarte.TROIS, CouleurCarte.PIQUE, ConditionTrophee.MAJORITE_2));
        this.pioche.add(new Carte(ValeurCarte.TROIS, CouleurCarte.TREFLE, ConditionTrophee.PLUSHAUTE_COEUR));
        this.pioche.add(new Carte(ValeurCarte.TROIS, CouleurCarte.CARREAU, ConditionTrophee.PLUSBASSE_CARREAU));
    }

    /**
     * Retourne la liste des cartes de la pioche.
     *
     * <p>La liste retournée est la structure interne ; évitez de la modifier
     * directement si vous souhaitez préserver l'encapsulation.</p>
     *
     * @return la liste des cartes de la pioche (peut être `null` si non initialisée)
     */
    public LinkedList<Carte> getPioche(){
        return this.pioche;
    }

    /**
     * Ajoute une carte au sommet (ou à la fin) de la pioche.
     *
     * @param carte la carte à ajouter (doit être non null).
     */
    public void ajouterCartePioche(Carte carte){
        this.pioche.add(carte);
    }

    /**
     * Supprime une carte de la pioche.
     *
     * @param carte la carte à supprimer (si présente).
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
    public Carte piocher(){
        return this.pioche.pop();
    }

    /**
     * Mélange les cartes de la pioche.
     *
     * <p>À compléter : implémenter l'algorithme de mélange (par ex. Fisher–Yates)
     * pour réordonner aléatoirement les éléments de `pioche`.</p>
     */
    public void melanger(){
        Collections.shuffle(this.pioche, new Random(System.nanoTime()));
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
        return this.pioche.isEmpty();
    }
}