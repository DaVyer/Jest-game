package classe;

import java.util.List;
import java.util.Scanner;

public interface StrategieJoueur {

    Offre faireOffre(Joueur joueur, Scanner scanner);

    Offre choisirOffre(List<Offre> offresDisponibles, Joueur joueur, Scanner scanner);

    Carte choisirCarteOffre(Offre offre, Joueur joueur, Scanner scanner);
}
