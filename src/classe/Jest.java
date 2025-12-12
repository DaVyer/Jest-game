package classe;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Jest extends Carte{
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private final ArrayList<Carte> cartes;
    private Joueur joueur;

    public Jest(ArrayList<Carte> cartes, Joueur joueur) {
        super(ValeurCarte.AS, CouleurCarte.JOKER, ConditionTrophee.MEILLEURJEST); // Jest est un joker
        int idJest = ID_GENERATOR.getAndIncrement();
        this.cartes = cartes;
        this.joueur = joueur;

    }

    public Carte getCarte(int idJest) {
        return cartes.get(idJest);
    }

    public Joueur getJoueur() {
        return this.joueur;
    }
    public int ajouterAuJest(Joueur joueur, Carte carte) {
        return 1;
    }

    public int recupererCarteOffre(Carte carte) {
        return 0;
    }
}