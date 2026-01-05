package model;

import java.util.List;
import java.util.Random;

/**
 * Stratégie de jeu pour un robot jouant aléatoirement.
 * 
 * <p>Implémente les méthodes de stratégie en faisant des choix
 * aléatoires parmi les options disponibles.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 * @see StrategieJoueur
 */
public class StrategieRobotAleatoire implements StrategieJoueur {

    /** Générateur de nombres aléatoires. */
    private final Random random = new Random();

    @Override
    public Offre faireOffre(Joueur joueur, InputProvider input) {
        List<Carte> mainManche = joueur.getMainCourante();

        if (mainManche.size() != 2) {
            throw new IllegalStateException(
                    "Le robot doit avoir exactement 2 cartes pour faire une offre (actuel = "
                            + mainManche.size() + ")"
            );
        }

        int indexCachee = random.nextInt(mainManche.size());
        Carte cachee = mainManche.remove(indexCachee);

        int indexVisible = random.nextInt(mainManche.size());
        Carte visible = mainManche.remove(indexVisible);

        System.out.println(joueur.getNom()
                + " (robot) crée une offre : carte visible=" + visible + ", carte cachée=[cachée]");

        return new Offre(cachee, visible, joueur);
    }

    @Override
    public Offre choisirOffre(List<Offre> offres, Joueur joueur, InputProvider input) {
        Offre offre = offres.get(random.nextInt(offres.size()));
        System.out.println(joueur.getNom() + " a choisi l'offre de " + offre.getJoueur().getNom());
        return offre;
    }

    @Override
    public Carte choisirCarteOffre(Offre offre, Joueur joueur, InputProvider input) {
        Carte carte = random.nextBoolean()
                ? offre.prendreCarte(true)
                : offre.prendreCarte(false);

        System.out.println(joueur.getNom() + " a choisi la carte : " + carte);
        return carte;
    }
}
