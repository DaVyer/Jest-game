package model;

import java.util.Comparator;
import java.util.List;

/**
 * Stratégie robot "gloutonne" : maximise le gain immédiat.
 *
 * <p>Cette stratégie implémente une approche gloutonne pour jouer au Jest :</p>
 * <ul>
 * <li>Offre : montre la carte la plus faible (pour limiter l'intérêt), 
 *           cache la plus forte (pour éviter de la donner)</li>
 * <li>Choix d'offre : prend l'offre dont la carte visible est la plus forte</li>
 * <li>Choix de carte : prend la meilleure des deux cartes disponibles</li>
 * </ul>
 * 
 * @author Gwendal Rodrigues
 * @version 03/01/2026
 * @see StrategieJoueur
 */
public class StrategieRobotGlouton implements StrategieJoueur {

    /**
     * Le robot crée une offre avec une stratégie gloutonne.
     * 
     * <p>Montre la carte la plus faible (pour limiter l'intérêt)
     * et cache la plus forte (pour éviter de la donner).</p>
     * 
     * @param joueur le robot qui doit faire l'offre
     * @param input le fournisseur d'entrée (non utilisé pour un robot)
     * @return l'offre créée selon la stratégie gloutonne
     */
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

    /**
     * Le robot choisit l'offre avec la meilleure carte visible.
     * 
     * @param offres la liste des offres proposées
     * @param joueur le robot qui doit choisir
     * @param input le fournisseur d'entrée (non utilisé pour un robot)
     * @return l'offre avec la plus forte carte visible
     */
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

    /**
     * Le robot choisit la meilleure carte disponible dans l'offre.
     * 
     * <p>Si la carte visible a une faible valeur, le robot prend la carte cachée
     * en espérant qu'elle soit meilleure.</p>
     * 
     * @param offre l'offre à partir de laquelle choisir
     * @param joueur le robot qui doit choisir
     * @param input le fournisseur d'entrée (non utilisé pour un robot)
     * @return la meilleure carte selon la stratégie gloutonne
     */
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

    /**
     * Retourne la carte avec la plus faible valeur entre deux cartes.
     * 
     * @param a la première carte
     * @param b la deuxième carte
     * @return la carte ayant la plus faible valeur
     */
    private Carte minCarte(Carte a, Carte b) {
        return valeurCarte(a) <= valeurCarte(b) ? a : b;
    }

    /**
     * Retourne la valeur numérique d'une carte pour la comparaison.
     * 
     * @param c la carte dont on veut connaître la valeur
     * @return la valeur numérique de la carte
     */
    private int valeurCarte(Carte c) {
        // Cas le plus courant : ValeurCarte enum avec un int
        // Exemple: c.getValeur().getValeur() ou getFaceValue()
        // --> adapte cette ligne à ton modèle exact.
        return c.getValeur().getValeur();
    }
}
