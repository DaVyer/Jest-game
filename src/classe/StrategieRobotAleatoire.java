package classe;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

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

    /**
     * Crée une offre aléatoirement pour le robot.
     * 
     * <p>Sélectionne aléatoirement quelle carte sera visible
     * et quelle carte sera cachée.</p>
     * 
     * @param joueur le joueur robot
     * @param scanner le scanner (non utilisé par le robot)
     * @return l'offre créée aléatoirement
     */
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

    /**
     * Choisit aléatoirement une offre parmi celles disponibles.
     * 
     * @param offres la liste des offres disponibles
     * @param joueur le joueur robot
     * @param scanner le scanner (non utilisé par le robot)
     * @return l'offre choisie aléatoirement
     */
    @Override
    public Offre choisirOffre(List<Offre> offres, Joueur joueur, Scanner scanner) {
        Offre offre = offres.get(random.nextInt(offres.size()));
        System.out.println(joueur.getNom() + " a choisi l'offre de " + offre.getJoueur().getNom());
        return offre;
    }

    /**
     * Choisit aléatoirement entre la carte visible et la carte cachée.
     * 
     * @param offre l'offre dans laquelle choisir
     * @param joueur le joueur robot
     * @param scanner le scanner (non utilisé par le robot)
     * @return la carte choisie aléatoirement
     */
    @Override
    public Carte choisirCarteOffre(Offre offre, Joueur joueur, Scanner scanner) {
        Carte carte = random.nextBoolean()
                ? offre.prendreCarte(true)
                : offre.prendreCarte(false);

        System.out.println(joueur.getNom() + " a choisi la carte : " + carte);
        return carte;
    }
}
