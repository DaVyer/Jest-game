package model;

/**
 * Représente une carte du jeu Jest.
 * 
 * <p>Une carte possède une couleur et une valeur. Les cartes sont les éléments
 * fondamentaux du jeu utilisés par les joueurs pour former des combinaisons
 * gagnantes.</p>
 * 
 * @author Gwendal Rodrigues
 * @version 03/01/2026
 */
public class Carte {
    /** La couleur de la carte. */
    private CouleurCarte couleur;
    
    /** La valeur de la carte. */
    private ValeurCarte valeur;
    private ConditionTrophee trophee;
    private String img;

    /**
     * Constructeur de la classe Carte.
     * 
     * @param valeur la valeur de la carte (voir énumération {@link ValeurCarte})
     * @param couleur la couleur de la carte (voir énumération {@link CouleurCarte})
     * @param trophee la condition de trophée associée à la carte, ou null
     * @param img le chemin du fichier image de la carte
     */
    public Carte(ValeurCarte valeur,  CouleurCarte couleur, ConditionTrophee trophee, String img) {
        this.valeur = valeur;
        this.couleur = couleur;
        this.trophee = trophee;
        this.img = img;
    }
    /**
     * Récupère la couleur actuelle de la carte.
     * 
     * @return la couleur de la carte
     */
    public CouleurCarte getCouleur() {
        return couleur;
    }

    /**
     * Définit la couleur de la carte.
     * 
     * @param couleur la couleur à assigner à la carte
     */
    public void setCouleur(CouleurCarte couleur) {
        this.couleur = couleur;
    }

    /**
     * Récupère la valeur actuelle de la carte.
     * 
     * @return la valeur de la carte
     */
    public ValeurCarte getValeur() {
        return valeur;
    }

    /**
     * Définit la valeur de la carte.
     * 
     * @param valeur la valeur à assigner à la carte
     */
    public void setValeur(ValeurCarte valeur) {
        this.valeur = valeur;
    }

    /**
     * Récupère la condition de trophée associée à la carte.
     * 
     * @return la condition de trophée de la carte
     */
    public ConditionTrophee getTrophee(){
        return this.trophee;
    }

    /**
     * Récupère le chemin de l'image de la carte.
     * 
     * @return le chemin du fichier image
     */
    public String getImg(){
        return this.img;
    }

    /**
     * Calcule la valeur de la carte pour une manche.
     * 
     * @return 0 pour un joker, 1 pour un As, sinon la valeur numérique de la carte
     */
    public int valeurPourManche() {
        if (this.getCouleur() == CouleurCarte.JOKER) return 0;
        if (this.getValeur() == ValeurCarte.AS) return 1;
        return this.getValeur().getValeur();
    }

    /**
     * Retourne la force de la couleur de la carte.
     * 
     * <p>Pique = 4, Trèfle = 3, Carreau = 2, Cœur = 1, Joker = 0</p>
     * 
     * @return la force de la couleur
     */
    public int forceCouleur() {
        return switch (this.getCouleur()) {
            case PIQUE -> 4;
            case TREFLE -> 3;
            case CARREAU -> 2;
            case COEUR -> 1;
            default -> 0;
        };
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