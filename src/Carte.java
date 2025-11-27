public class Carte {
    private CouleurCarte couleur;
    private ValeurCarte valeur;

    public Carte(ValeurCarte valeur,  CouleurCarte couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
    }

    public CouleurCarte getCouleur() {
        return couleur;
    }

    public void setCouleur(CouleurCarte couleur) {
        this.couleur = couleur;
    }

    public ValeurCarte getValeur() {
        return valeur;
    }

    public void setValeur(ValeurCarte valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return valeur + " de " +  couleur;
    }
}