package classe;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Représente un joueur dans le jeu.
 * 
 * <p>Gère les informations du joueur : son identifiant unique, son nom,
 * son Jest (collection de cartes), sa main courante pour la manche en cours,
 * et sa stratégie de jeu (humaine ou robot).</p>
 * 
 * @author Gwendal Rodrigues, Tristan Crémonat
 * @version %I%, %G%
 */
public class Joueur {
    /** Générateur d'identifiants uniques pour les joueurs. */
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    
    /** Identifiant unique du joueur. */
    private final int idJoueur;
    
    /** Nom du joueur. */
    private String nom;
    
    /** Le Jest du joueur contenant ses cartes accumulées. */
    private final Jest jest;
    
    /** Liste des cartes en main pour la manche courante. */
    private List<Carte> mainCourante = new ArrayList<>();
    
    /** La stratégie de jeu du joueur (humaine ou robot). */
    private final StrategieJoueur strategieJoueur;

    /**
     * Constructeur du Joueur.
     * 
     * <p>Crée un nouveau joueur avec un identifiant unique, un nom
     * et une stratégie de jeu. Initialise également son Jest vide.</p>
     * 
     * @param nom le nom du joueur
     * @param strategieJoueur la stratégie de jeu du joueur
     */
    public Joueur(String nom, StrategieJoueur strategieJoueur) {
        idJoueur = ID_GENERATOR.getAndIncrement();
        this.nom = nom;
        this.strategieJoueur = strategieJoueur;
        this.jest = new Jest();
        System.out.println("\n===============\n\n\tNouveau Joueur créé :\t " + this.getNom() + "\n\tJoueur numéro :\t" + this.getIdJoueur() + "\n\n===============\n");
    }

    /**
     * Récupère l'identifiant du joueur.
     * 
     * @return l'identifiant unique du joueur
     */
    public int getIdJoueur() {
        return this.idJoueur;
    }

    /**
     * Récupère le nom du joueur.
     * 
     * @return le nom du joueur
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Récupère le Jest du joueur.
     * 
     * @return le Jest contenant toutes les cartes du joueur
     */
    public Jest getJest() {
        return jest;
    }

    /**
     * Ajoute une carte au Jest du joueur.
     * 
     * @param carte la carte à ajouter
     */
    public void ajouterAuJest(Carte carte){
        jest.ajouterAuJest(carte);
    }

    /**
     * Affiche le Jest complet du joueur.
     * 
     * <p>Affiche le nom du joueur suivi de toutes ses cartes.</p>
     */
    public void afficherMain() {
        System.out.println("Jest de " + nom + " :");
        jest.afficher();
    }

    /**
     * Accepte un visiteur pour implémenter le pattern Visitor.
     * 
     * @param visitor le visiteur à accepter
     */
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * Récupère la stratégie de jeu du joueur.
     * 
     * @return la stratégie du joueur
     */
    public StrategieJoueur getStrategie() {
        return strategieJoueur;
    }

    /**
     * Ajoute une carte à la main courante du joueur pour la manche en cours.
     * 
     * @param c la carte à ajouter
     */
    public void ajouterCarteManche(Carte c) {
        mainCourante.add(c);
    }

    /**
     * Vide la main courante du joueur.
     * 
     * <p>Utilisé au début de chaque nouvelle manche.</p>
     */
    public void viderMainManche() {
        mainCourante.clear();
    }

    /**
     * Récupère la main courante du joueur.
     * 
     * @return la liste des cartes de la main courante
     */
    public List<Carte> getMainCourante() {
        return mainCourante;
    }

    /**
     * Affiche les cartes de la main courante pour la manche en cours.
     * 
     * <p>Affiche chaque carte avec son index.</p>
     */
    public void afficherMainManche() {
        System.out.println("Cartes de manche de " + nom + " :");
        for (int i = 0; i < mainCourante.size(); i++) {
            System.out.println("[" + i + "] " + mainCourante.get(i));
        }
    }
}