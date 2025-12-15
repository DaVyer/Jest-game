package classe;

import java.util.List;
import java.util.Scanner;

public class StrategieHumaine implements StrategieJoueur {

    @Override
    public Offre faireOffre(Joueur joueur, Scanner scanner) {

        joueur.afficherMainManche();

        int indexCachee = choisirCarte(scanner, joueur.getMainManche(),
                "Choisissez la carte FACE CACHÉE : ");
        Carte cachee = joueur.getMainManche().remove(indexCachee);

        joueur.afficherMainManche();

        int indexVisible = choisirCarte(scanner, joueur.getMainManche(),
                "Choisissez la carte FACE VISIBLE : ");
        Carte visible = joueur.getMainManche().remove(indexVisible);

        System.out.println("\nOffre créée :");
        System.out.println(" - Carte face cachée : [cachée]");
        System.out.println(" - Carte face visible : " + visible);

        return new Offre(cachee, visible, joueur);
    }

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
