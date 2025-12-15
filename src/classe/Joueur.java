package classe;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Joueur {
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private int idJoueur;
    private String nom;
    private Jest main = new Jest();

    public Joueur(String nom) {
        idJoueur = ID_GENERATOR.getAndIncrement();
        this.nom = nom;
        System.out.println("\n===============\n\n\tNouveau Joueur créé :\t " + this.getNom() + "\n\tJoueur numéro :\t" + this.getIdJoueur() + "\n\n===============\n");
    }

    public int getIdJoueur() {
        return this.idJoueur;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Jest getMain() {
        return main;
    }

    // public abstract void choisirOffre(Joueur joueur, Offre offre);

    // public abstract Offre FaireOffre(Joueur joueur);

    public void ajouterAuJest(Carte carte){
        main.ajouterAuJest(carte);
    }

    public void afficherMain() {
        System.out.println("Main de " + nom + " :");
        main.afficher();
    }


    public Offre faireOffre(Scanner scanner){
        afficherMain();

        int indexCachee = choisirCarte(scanner, "Choisissez la carte FACE CACHÉE : ");
        Carte cachee = main.getCarte(indexCachee);
        main.retirerCarte(indexCachee);

        afficherMain();

        int indexVisible = choisirCarte(scanner, "Choisissez la carte FACE VISIBLE : ");
        Carte visible = main.getCarte(indexVisible);
        main.retirerCarte(indexVisible);

        Offre offre = new Offre(cachee, visible, this);

        System.out.println("\nOffre créée :");
        System.out.println(" - Carte face cachée : [cachée]");
        System.out.println(" - Carte face visible : " + visible);

        return offre;
    }

    private int choisirCarte(Scanner scanner, String message) {
        int choix = -1;

        while (true) {
            System.out.print(message);
            try {
                choix = Integer.parseInt(scanner.nextLine());
                if (choix >= 0 && choix < main.taille()) {
                    return choix;
                }
            } catch (NumberFormatException e) {
                // ignore
            }
            System.out.println("Choix invalide. Réessayez.");
        }
    }

    public Offre choisirOffre(List<Offre> offres, Scanner scanner) {
        System.out.println("Choisissez une offre :");

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

    public Carte choisirCarteOffre(Offre offre, Scanner scanner) {
        System.out.println("Choisissez une carte :");
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}