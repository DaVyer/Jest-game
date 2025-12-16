package classe;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Joueur {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int idJoueur;
    private String nom;
    private final Jest jest;
    private List<Carte> mainCourante = new ArrayList<>();
    private final StrategieJoueur strategieJoueur;

    public Joueur(String nom, StrategieJoueur strategieJoueur) {
        idJoueur = ID_GENERATOR.getAndIncrement();
        this.nom = nom;
        this.strategieJoueur = strategieJoueur;
        this.jest = new Jest();
        System.out.println("\n===============\n\n\tNouveau Joueur créé :\t " + this.getNom() + "\n\tJoueur numéro :\t" + this.getIdJoueur() + "\n\n===============\n");
    }

    public int getIdJoueur() {
        return this.idJoueur;
    }

    public String getNom() {
        return this.nom;
    }

    public Jest getJest() {
        return jest;
    }

    public void ajouterAuJest(Carte carte){
        jest.ajouterAuJest(carte);
    }

    public void afficherMain() {
        System.out.println("Jest de " + nom + " :");
        jest.afficher();
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public StrategieJoueur getStrategie() {
        return strategieJoueur;
    }

    public void ajouterCarteManche(Carte c) {
        mainCourante.add(c);
    }

    public void viderMainManche() {
        mainCourante.clear();
    }

    public List<Carte> getMainCourante() {
        return mainCourante;
    }

    public void afficherMainManche() {
        System.out.println("Cartes de manche de " + nom + " :");
        for (int i = 0; i < mainCourante.size(); i++) {
            System.out.println("[" + i + "] " + mainCourante.get(i));
        }
    }
}