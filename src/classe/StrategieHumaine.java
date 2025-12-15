package classe;

import java.util.List;
import java.util.Scanner;

public class StrategieHumaine implements StrategieJoueur {

    @Override
    public Offre faireOffre(Joueur joueur, Scanner scanner) {

        joueur.afficherMain();

        int indexCachee = choisirCarte(scanner, joueur.getMain(),
                "Choisissez la carte FACE CACHÉE : ");
        Carte cachee = joueur.getMain().getCarte(indexCachee);
        joueur.getMain().retirerCarte(indexCachee);

        joueur.afficherMain();

        int indexVisible = choisirCarte(scanner, joueur.getMain(),
                "Choisissez la carte FACE VISIBLE : ");
        Carte visible = joueur.getMain().getCarte(indexVisible);
        joueur.getMain().retirerCarte(indexVisible);

        Offre offre = new Offre(cachee, visible, joueur);

        System.out.println("\nOffre créée :");
        System.out.println(" - Carte face cachée : [cachée]");
        System.out.println(" - Carte face visible : " + visible);

        return offre;
    }

    private int choisirCarte(Scanner scanner, Jest main, String message) {
        while (true) {
            System.out.print(message);
            try {
                int choix = Integer.parseInt(scanner.nextLine());
                if (choix >= 0 && choix < main.taille()) return choix;
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
