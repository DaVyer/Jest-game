package classe;
/**
 * A class that permits to create cards with their color and value.
 *
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 */

public class Carte {
    private CouleurCarte couleur;
    private ValeurCarte valeur;
    private ConditionTrophee trophee;

    /**
     * Constructor of the Carte class
     * @param valeur the value of the card (see @CouleurCarte enum).
     * @param couleur the color of the card (see @ValeurCarte enum).
     */
    public Carte(ValeurCarte valeur,  CouleurCarte couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
        this.trophee = calculTrophee();

    }

    private ConditionTrophee calculTrophee(){
        if (this.valeur == ValeurCarte.DEUX && this.couleur == CouleurCarte.TREFLE) {
            return ConditionTrophee.PLUSBASSE;
        }

        // IMPORTANT : Il faut retourner null si ce n'est pas une carte à trophée
        return null; 
    }

    /**
     * Method that get the current color of a card.
     * @return CouleurCarte
     */
    public CouleurCarte getCouleur() {
        return couleur;
    }

    /**
     * Method that set a "couleur" to the current color of a card.
     * @param couleur -- color of a card.
     */
    public void setCouleur(CouleurCarte couleur) {
        this.couleur = couleur;
    }

    /**
     * Method that get the current value of a card.
     * @return ValeurCarte
     */
    public ValeurCarte getValeur() {
        return valeur;
    }

    /**
     * Method that set a "valeur" to the current value of a card.
     * @param valeur -- value of a card.
     */
    public void setValeur(ValeurCarte valeur) {
        this.valeur = valeur;
    }

    /**
     * Method that return the trophy value of a card?
     * 
     */
    public ConditionTrophee getTrophee(){
        return this.trophee;
    }

    /**
     * Method that returns a formated string.
     * @return String -- a String with the value and the color of the card.
     */
    @Override
    public String toString() {
        return valeur + " de " +  couleur;
    }
}