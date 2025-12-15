package classe;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Joueur {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int idJoueur;
    private String nom;
    private final Jest main;
    private final StrategieJoueur strategieJoueur;

    public Joueur(String nom, StrategieJoueur strategieJoueur) {
        idJoueur = ID_GENERATOR.getAndIncrement();
        this.nom = nom;
        this.strategieJoueur = strategieJoueur;
        this.main = new Jest();
        System.out.println("\n===============\n\n\tNouveau Joueur créé :\t " + this.getNom() + "\n\tJoueur numéro :\t" + this.getIdJoueur() + "\n\n===============\n");
    }

    public int getIdJoueur() {
        return this.idJoueur;
    }

    public String getNom() {
        return this.nom;
    }

    public Jest getMain() {
        return main;
    }

    public void ajouterAuJest(Carte carte){
        main.ajouterAuJest(carte);
    }

    public void afficherMain() {
        System.out.println("Main de " + nom + " :");
        main.afficher();
    }


    public Offre faireOffre(Scanner scanner){
        return strategieJoueur.faireOffre(this, scanner);
    }


    public Offre choisirOffre(List<Offre> offres, Scanner scanner) {
        return strategieJoueur.choisirOffre(offres, this, scanner);
    }

    public Carte choisirCarteOffre(Offre offre, Scanner scanner) {
        return strategieJoueur.choisirCarteOffre(offre, this, scanner);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public StrategieJoueur getStrategie() {
        return strategieJoueur;
    }
}