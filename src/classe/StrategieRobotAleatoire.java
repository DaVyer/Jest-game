package classe;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class StrategieRobotAleatoire implements StrategieJoueur {

    private final Random random = new Random();

    @Override
    public Offre faireOffre(Joueur joueur, Scanner scanner) {

        int taille = joueur.getMain().taille();
        int indexCachee = random.nextInt(taille);

        Carte cachee = joueur.getMain().getCarte(indexCachee);
        joueur.getMain().retirerCarte(indexCachee);

        int indexVisible = random.nextInt(joueur.getMain().taille());
        Carte visible = joueur.getMain().getCarte(indexVisible);
        joueur.getMain().retirerCarte(indexVisible);

        return new Offre(cachee, visible, joueur);
    }


    @Override
    public Offre choisirOffre(List<Offre> offres, Joueur joueur, Scanner scanner) {
        Offre offre = offres.get(random.nextInt(offres.size()));
        System.out.println(joueur.getNom() + " a choisi l'offre de " + offre.getJoueur().getNom());
        return offre;
    }

    @Override
    public Carte choisirCarteOffre(Offre offre, Joueur joueur, Scanner scanner) {
        Carte carte = random.nextBoolean()
                ? offre.prendreCarte(true)
                : offre.prendreCarte(false);

        System.out.println(joueur.getNom() + " a choisi la carte : " + carte);
        return carte;
    }
}
