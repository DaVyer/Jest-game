package classe;

import java.util.List;
import java.util.Scanner;

/**
 * Stratégie de jeu pour un joueur humain.
 * 
 * <p>Implémente les méthodes de stratégie en demandant
 * les choix au joueur via l'entrée standard.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 * @see StrategieJoueur
 */
public class StrategieHumaine implements StrategieJoueur {

    /**
     * Demande au joueur humain de créer une offre.
     * 
     * <p>Le joueur choisit quelle carte mettre face cachée
     * et quelle carte mettre face visible.</p>
     * 
     * @param joueur le joueur qui crée l'offre
     * @param scanner le scanner pour lire les entrées
     * @return l'offre créée par le joueur
     */
    @Override
    public Offre faireOffre(Joueur joueur, Scanner scanner) {

        joueur.afficherMainManche();

        int indexCachee = choisirCarte(scanner, joueur.getMainCourante(),
                "Choisissez la carte FACE CACHÉE (Sélectionner le numéro) : ");
        Carte cachee = joueur.getMainCourante().remove(indexCachee);

        joueur.afficherMainManche();

        int indexVisible = choisirCarte(scanner, joueur.getMainCourante(),
                "Choisissez la carte FACE VISIBLE (Sélectionner le numéro) : ");
        Carte visible = joueur.getMainCourante().remove(indexVisible);

        System.out.println("\nOffre créée :");
        System.out.println(" - Carte face cachée : [cachée]");
        System.out.println(" - Carte face visible : " + visible);

        return new Offre(cachee, visible, joueur);
    }

    /**
     * Demande au joueur de choisir une carte parmi sa main.
     * 
     * @param scanner le scanner pour lire les entrées
     * @param mainManche la main de cartes disponibles
     * @param message le message à afficher
     * @return l'index de la carte choisie
     */
    private int choisirCarte(Scanner scanner, List<Carte> mainManche, String message) {
        while (true) {
            System.out.print(message);
            try {
                int choix = Integer.parseInt(scanner.nextLine());
                if (choix >= 0 && choix < mainManche.size()) {
                    return choix;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Choix invalide. Réessayez.");
        }
    }

    /**
     * Demande au joueur humain de choisir une offre parmi les offres disponibles.
     * 
     * @param offres la liste des offres disponibles
     * @param joueur le joueur qui fait le choix
     * @param scanner le scanner pour lire les entrées
     * @return l'offre choisie
     */
    @Override
    public Offre choisirOffre(List<Offre> offres, Joueur joueur, Scanner scanner) {

        System.out.println("\n" + joueur.getNom() + ", choisissez une offre :");

        for (int i = 0; i < offres.size(); i++) {
            Offre o = offres.get(i);
            System.out.println(
                    "[" + i + "] Offre de " + o.getJoueur().getNom()
                            + " | Carte visible : " + o.getVisible()
            );
        }

        int choix = -1;
        while (choix < 0 || choix >= offres.size()) {
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Choix invalide.");
            }
        }

        return offres.get(choix);
    }

    /**
     * Demande au joueur humain de choisir une carte dans l'offre sélectionnée.
     * 
     * @param offre l'offre dans laquelle choisir
     * @param joueur le joueur qui fait le choix
     * @param scanner le scanner pour lire les entrées
     * @return la carte choisie
     */
    @Override
    public Carte choisirCarteOffre(Offre offre, Joueur joueur, Scanner scanner) {

        System.out.println("\n" + joueur.getNom() + ", choisissez une carte :");
        System.out.println("[0] Carte visible : " + offre.getVisible());
        System.out.println("[1] Carte cachée");

        int choix = -1;
        while (choix != 0 && choix != 1) {
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Choix invalide.");
            }
        }

        return offre.prendreCarte(choix == 0);
    }
}
