import javax.xml.stream.FactoryConfigurationError;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Jest extends Carte{
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private int idJest;
    private final ArrayList<Carte> cartes;
    private Joueur joueur;

    public Jest(ArrayList<Carte> cartes, Joueur joueur) {
        idJest = ID_GENERATOR.getAndIncrement();
        this.cartes = cartes;
        this.joueur = joueur;

    }

    public Carte getCarte(int idJest) {
        return cartes.get(idJest);
    }

    public int choisirOffre(Joueur joueur, Offre offre){
        return 1;
    }

    public int faireOffre(){
        return 1;
    }

    private int ajouterAuJest(Carte carte, Offre offre){
        return 1;
    }

    public int accept(Visitor visitor){
        return 1;
    }
}