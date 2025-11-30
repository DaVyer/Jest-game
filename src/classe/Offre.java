import java.util.ArrayList;
import java.util.List;

/**
 * Représente une offre d'un joueur
 *
 * <p>Gere la mise en place et la récuperation d'offres par les joueurs
 * partie. Certaines méthodes ici sont des stubs et doivent être complétées
 * </p>
 * 
 *
 * @author Tristan
 * @version 1.0
 */

public class Offre {

    /**Identifiant de la pioche */
    private int idOffre;

    /**Attribut de la carte visible */
    private Carte visible;

    /**Attribut de la carte cachée */
    private Carte cachee;

    /**Attribut permettant de savoir si la pioche est disponible ou non */
    private boolean disponible;

    /**Constructeur de Offre 
     * 
     * @param idOffre
    */
    public Offre(int idOffre){
        // En vrai ici je verrais bien une varible static pour les id, qui permettent de savoir et initialiser les id
        this.idOffre = idOffre;
        this.disponible = true;
    }

    /**Getter de idOffre */
    public int getIdOffre(){
        return this.idOffre;
    }
    /**
     * Définit l'identifiant de l'offre.
     *
     * @param id identifiant à affecter à cette offre
     */
    public void setIdOffre(int id){
        this.idOffre = id;
    }

    /**
     * Retourne la carte visible associée à l'offre.
     *
     * @return la carte visible (peut être `null`)
     */
    public Carte getVisible(){
        return this.visible;
    }

    /**
     * Définit la carte visible de l'offre.
     *
     * @param visible la carte à rendre visible
     */
    public void setVisible(Carte visible){
        this.visible = visible;
    }

    /**
     * Retourne la carte cachée associée à l'offre.
     *
     * @return la carte cachée (peut être `null`)
     */
    public Carte getCachee(){
        return this.cachee;
    }

    /**
     * Définit la carte cachée de l'offre.
     *
     * @param cachee la carte à placer en cache
     */
    public void setCachee(Carte cachee){
        this.cachee = cachee;
    }

    /**
     * Indique si l'offre est disponible.
     *
     * @return `true` si l'offre est disponible, `false` sinon
     */
    public boolean getDisponible(){
        return this.disponible;
    }

    /**
     * Définit la disponibilité de l'offre.
     *
     * @param disponible `true` pour rendre l'offre disponible
     */
    public void setDisponible(boolean disponible){
        this.disponible = disponible;
    }
    /**
     * Organise une offre en assignant la carte visible et la carte cachée.
     *
     * @param visible la carte à rendre visible pour l'offre
     * @param cachee  la carte à garder cachée pour l'offre
     */
    public void organiserOffre(Carte visible, Carte cachee){
        this.setVisible(visible);
        this.setCachee(cachee);
    }

    /**
     * Vérifie si l'offre est disponible pour un joueur donné.
     *
     * <p>À définir</p>
     *
     * @param joueur le joueur qui souhaite accéder à l'offre
     * @param offre  l'offre concernée
     * @return `true` si l'offre est disponible pour le joueur, `false` sinon
     */
    public boolean isAvailable(Joueur joueur, Offre offre){
        // A definir

        return disponible;
    }

    /**
     * Récupère des cartes depuis la pioche vers cette offre.
     *
     * <p>À définir : préciser le comportement (combien de cartes, ordre,
     * vérification de disponibilité de la pioche, etc.).</p>
     *
     * @param carte carte source ou indicateur du tirage (selon l'implémentation)
     */
    public void recupererCartesPioche(Carte carte){
        // A definir
    }

    /**
     * Permet au joueur de choisir une offre (ou une carte dans une offre).
     *
     * <p>À définir</p>
     *
     * @param carte la carte choisie par le joueur
     */
    public void choisirOffre(Carte carte){
        // A definir
    }

    /**
     * Récupère les cartes restantes d'une offre après une action.
     *
     * <p>À définir</p>
     *
     * @param carte carte de référence pour la récupération (selon l'implémentation)
     */
    public void recupererCarteRestantes(Carte carte){
        // À définir
    }
}
