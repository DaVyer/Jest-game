package model;

import java.util.Comparator;
import java.util.List;

/**
 * Stratégie robot "gloutonne" : maximise le gain immédiat.
 *
 * <p>- Offre : montre la carte la plus faible (pour limiter l'intérêt),
 *           cache la plus forte (pour éviter de la donner).</p>
 * <p>- Choix d'offre : prend l'offre dont la carte visible est la plus forte.</p>
 * <p>- Choix de carte : prend la meilleure des deux cartes disponibles.</p>
 */
public class StrategieRobotGlouton implements StrategieJoueur {

    @Override
    public Offre faireOffre(Joueur joueur, InputProvider input) {
        List<Carte> mainManche = joueur.getMainCourante();

        if (mainManche.size() != 2) {
            throw new IllegalStateException(
                    "Le robot doit avoir exactement 2 cartes pour faire une offre (actuel = "
                            + mainManche.size() + ")"
            );
        }

        Carte c1 = mainManche.get(0);
        Carte c2 = mainManche.get(1);

        Carte faible = minCarte(c1, c2);
        Carte forte = (faible == c1) ? c2 : c1;

        mainManche.remove(forte);
        mainManche.remove(faible);

        System.out.println(joueur.getNom()
                + " (robot glouton) crée une offre : carte visible=" + faible + ", carte cachée=[cachée]");

        return new Offre(forte, faible, joueur);
    }

    @Override
    public Offre choisirOffre(List<Offre> offres, Joueur joueur, InputProvider input) {
        Offre meilleure = offres.stream()
                .max(Comparator.comparingInt(o -> valeurCarte(o.getVisible())))
                .orElseThrow();

        System.out.println(joueur.getNom()
                + " (robot glouton) choisit l'offre de " + meilleure.getJoueur().getNom()
                + " (visible=" + meilleure.getVisible() + ")");

        return meilleure;
    }

    @Override
    public Carte choisirCarteOffre(Offre offre, Joueur joueur, InputProvider input) {
        Carte visible = offre.getVisible();

        boolean prendreVisible = true;

        if (valeurCarte(visible) <= 2) {
            prendreVisible = false;
        }

        Carte prise = offre.prendreCarte(prendreVisible);

        System.out.println(joueur.getNom()
                + " (robot glouton) prend : " + prise);

        return prise;
    }

    private Carte minCarte(Carte a, Carte b) {
        return valeurCarte(a) <= valeurCarte(b) ? a : b;
    }

    /**
     * Donne une valeur "immédiate" pour comparer des cartes.
     * Adapte si ton modèle a un getValeur(), getFaceValue(), etc.
     */
    private int valeurCarte(Carte c) {
        // Cas le plus courant : ValeurCarte enum avec un int
        // Exemple: c.getValeur().getValeur() ou getFaceValue()
        // --> adapte cette ligne à ton modèle exact.
        return c.getValeur().getValeur();
    }
}
