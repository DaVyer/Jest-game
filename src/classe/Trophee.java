package classe;
import java.util.ArrayList;

public class Trophee {
    private Carte carte;
    private ConditionTrophee condition;

    public Trophee(Carte carte, ConditionTrophee condition) {
        this.carte = carte;
        this.condition = condition;
    }

    public Carte getCarte() {
        return carte;
    }

    public static void joueurGagnant(Joueur joueur) {

    }
}
