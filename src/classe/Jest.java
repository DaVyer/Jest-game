package classe;
import java.util.ArrayList;

/**
 * Représente le Jest d'un joueur, c'est-à-dire sa collection de cartes.
 * 
 * <p>Hérite de Carte et gère une liste de cartes accumulées par le joueur
 * au cours de la partie. Le Jest est lui-même considéré comme un joker.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 * @see Carte
 */
public class Jest extends Carte{
    /** Liste des cartes contenues dans le Jest. */
    private final ArrayList<Carte> cartes;
    
    /** Le joueur propriétaire du Jest. */
    private final Joueur joueur;

    /**
     * Constructeur du Jest.
     * 
     * <p>Initialise un Jest vide avec les caractéristiques d'un joker.
     * Le joueur propriétaire est initialisé à null.</p>
     */
    public Jest() {
        super(ValeurCarte.AS, CouleurCarte.JOKER, ConditionTrophee.MEILLEURJEST); // Jest est un joker
        this.cartes = new ArrayList<>();
        this.joueur = null;
    }

    /**
     * Récupère une carte du Jest à l'index spécifié.
     * 
     * @param idJest l'index de la carte à récupérer
     * @return la carte à l'index spécifié
     */
    public Carte getCarte(int idJest) {
        return cartes.get(idJest);
    }

    /**
     * Récupère le joueur propriétaire du Jest.
     * 
     * @return le joueur propriétaire, ou null si aucun
     */
    public Joueur getJoueur() {
        return this.joueur;
    }

    /**
     * Affiche toutes les cartes du Jest avec leurs index.
     * 
     * <p>Affiche chaque carte sur une ligne avec son numéro d'index entre crochets.</p>
     */
    public void afficher() {
        for (int i = 0; i < cartes.size(); i++) {
            System.out.println("[" + i + "] " + cartes.get(i));
        }
    }

    /**
     * Ajoute une carte au Jest.
     * 
     * @param carte la carte à ajouter
     */
    public void ajouterAuJest(Carte carte) {
        this.cartes.add(carte);
    }

    /**
     * Retire une carte du Jest à l'index spécifié.
     * 
     * @param index l'index de la carte à retirer
     * @throws IndexOutOfBoundsException si l'index est invalide
     */
    public void retirerCarte(int index) {
        if (index < 0 || index >= cartes.size()) {
            throw new IndexOutOfBoundsException(
                    "Index invalide : " + index + " (taille=" + cartes.size() + ")"
            );
        }
        cartes.remove(index);
    }

    /**
     * Retourne le nombre de cartes dans le Jest.
     * 
     * @return la taille du Jest
     */
    public int taille(){
        return cartes.size();
    }
}