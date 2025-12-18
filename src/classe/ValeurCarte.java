package classe;

/**
 * Énumération des valeurs de carte possibles dans le jeu.
 * 
 * <p>Chaque valeur possède une valeur numérique associée.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 */
public enum ValeurCarte {
    AS(1),
    DEUX(2),
    TROIS(3),
    QUATRE(4);

    /** La valeur numérique de la carte. */
    private final int valeurNumerique;

    /**
     * Constructeur privé de l'énumération.
     * 
     * @param valeurNumerique la valeur numérique associée
     */
    private ValeurCarte(int valeurNumerique) {
        this.valeurNumerique = valeurNumerique;
    }

    /**
     * Récupère la valeur numérique de la carte.
     * 
     * @return la valeur numérique
     */
    public int getValeur() {
        return this.valeurNumerique;
    }
}
