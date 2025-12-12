package classe;
public enum ValeurCarte {
    AS(1),
    DEUX(2),
    TROIS(3),
    QUATRE(4);

    private final int valeurNumerique;

    private ValeurCarte(int valeurNumerique) {
        this.valeurNumerique = valeurNumerique;
    }


    public int getValeur() {
        return this.valeurNumerique;
    }
}
