package classe;
import java.util.ArrayList;

public class Jest extends Carte{
    private final ArrayList<Carte> cartes;
    private Joueur joueur;

    public Jest() {
        super(ValeurCarte.AS, CouleurCarte.JOKER, ConditionTrophee.MEILLEURJEST); // Jest est un joker
        this.cartes = new ArrayList<>();
        this.joueur = null;
    }

    public Carte getCarte(int idJest) {
        return cartes.get(idJest);
    }

    public Joueur getJoueur() {
        return this.joueur;
    }

    public void afficher() {
        for (int i = 0; i < cartes.size(); i++) {
            System.out.println("[" + i + "] " + cartes.get(i));
        }
    }


    public void ajouterAuJest(Carte carte) {
        this.cartes.add(carte);
    }

    public void retirerCarte(int index) {
        if (index < 0 || index >= cartes.size()) {
            throw new IndexOutOfBoundsException(
                    "Index invalide : " + index + " (taille=" + cartes.size() + ")"
            );
        }
        cartes.remove(index);
    }

    public int taille(){
        return cartes.size();
    }
}