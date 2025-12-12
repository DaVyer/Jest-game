package classe;
import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Joueur {
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private int idJoueur;
    private String nom;
    private Jest main;

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
        return this.main;
    }

    // public abstract void choisirOffre(Joueur joueur, Offre offre);

    // public abstract Offre FaireOffre(Joueur joueur);

    private void ajouterAuJest(Carte carte, Offre offre){

    }

    // TODO: Implement when Visitor class is created
    // public abstract void accept(Visitor visitor);
}