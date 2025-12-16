package classe;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class StrategieRobotAleatoire implements StrategieJoueur {

    private final Random random = new Random();

    @Override
    public Offre faireOffre(Joueur joueur, Scanner scanner) {

        List<Carte> mainManche = joueur.getMainCourante();

        if (mainManche.size() != 2) {
            throw new IllegalStateException(
                    "Le robot doit avoir exactement 2 cartes pour faire une offre (actuel = "
                            + mainManche.size() + ")"
            );
        }

        Random random = new Random();

        int indexCachee = random.nextInt(mainManche.size());
        Carte cachee = mainManche.remove(indexCachee);

        int indexVisible = random.nextInt(mainManche.size());
        Carte visible = mainManche.remove(indexVisible);

        System.out.println(joueur.getNom()
                + " \n(robot) crée une offre : carte visible=" + visible + ", carte cachée=[cachée]");

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
