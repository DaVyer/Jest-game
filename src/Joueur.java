import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Joueur {
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private int idJoueur;
    private String nom;
    private Jest main;

    public Joueur(String nom) {
        idJoueur = ID_GENERATOR.getAndIncrement();
        this.nom = nom;
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

    public abstract void choisirOffre(Joueur joueur, Offre offre);

    public abstract Offre FaireOffre(Joueur joueur);

    private void ajouterAuJest(Carte carte, Offre offre){

    }

    public abstract void accept(Visitor visitor);
}