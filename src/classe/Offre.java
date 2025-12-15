package classe;

/**
 * Représente une offre d'un joueur
 *
 * <p>Gere la mise en place et la récuperation d'offres par les joueurs
 * partie. Certaines méthodes ici sont des stubs et doivent être complétées
 * </p>
 * 
 *
 * @author Tristan
 * @version %I%, %G%
 */

public class Offre {

    /**Attribut du Joueur */
    private final Joueur joueur;

    /**Attribut de la carte visible */
    private Carte visible;

    /**Attribut de la carte cachée */
    private Carte cachee;

    /**Attribut de disponibilité de l'offre */
    private boolean disponible;

    /**Constructeur d'Offre
     * 
     * @param visible Carte visible de l'offre
     * @param cachee Carte cachée de l'offre
     * @param joueur Joueur qui fait l'offre
    */
    public Offre(Carte visible, Carte cachee, Joueur joueur) {
        if (joueur == null) {
            throw new IllegalArgumentException("Une offre doit avoir un joueur");
        }
        this.visible = visible;
        this.cachee = cachee;
        this.joueur = joueur;
        this.disponible = true;
    }

    public Carte prendreCarteRestante() {
        if (visible != null) {
            Carte c = visible;
            visible = null;
            disponible = false;
            return c;
        }
        if (cachee != null) {
            Carte c = cachee;
            cachee = null;
            disponible = false;
            return c;
        }
        return null;
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
     * Retourne la carte cachée associée à l'offre.
     *
     * @return la carte cachée (peut être `null`)
     */
    public Carte getCachee(){
        return this.cachee;
    }

    /**
     * Retourne le joueur associé à l'offre.
     *
     * @return le joueur qui a fait l'offre
     */
    public Joueur getJoueur(){
        return this.joueur;
    }

    /**
     * Permet de prendre une carte de l'offre.
     *
     * @param prendreVisible true pour prendre la carte visible, false pour la carte cachée
     * @return la carte prise (visible ou cachée)
     */
    public Carte prendreCarte(boolean prendreVisible) {
        Carte carte;

        if (prendreVisible) {
            carte = visible;
            visible = null;
        } else {
            carte = cachee;
            cachee = null;
        }

        disponible = false;
        return carte;
    }

    public boolean isDisponible() {
        return disponible;
    }
}
