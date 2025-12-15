package classe;

import java.util.*;

public class ScoreVisitor implements Visitor {

    private int score;

    @Override
    public void visit(Joueur joueur) {
        score = calculerScore(joueur.getMain());
    }

    public int getScore() {
        return score;
    }

    private int calculerScore(Jest jest) {

        Map<CouleurCarte, List<Carte>> parCouleur = new EnumMap<>(CouleurCarte.class);
        Map<ValeurCarte, List<Carte>> parValeur = new EnumMap<>(ValeurCarte.class);

        boolean hasJoker = indexerCartes(jest, parCouleur, parValeur);

        int total = 0;

        total += calculerValeurCartes(jest, parCouleur, hasJoker);
        total += calculerPairesNoires(parValeur);

        return total;
    }

    private boolean indexerCartes(Jest jest,
                                  Map<CouleurCarte, List<Carte>> parCouleur,
                                  Map<ValeurCarte, List<Carte>> parValeur) {

        boolean hasJoker = false;

        for (int i = 0; i < jest.taille(); i++) {
            Carte c = jest.getCarte(i);

            parCouleur.computeIfAbsent(c.getCouleur(), k -> new ArrayList<>()).add(c);
            parValeur.computeIfAbsent(c.getValeur(), k -> new ArrayList<>()).add(c);

            if (c.getCouleur() == CouleurCarte.JOKER) {
                hasJoker = true;
            }
        }
        return hasJoker;
    }

    private int calculerValeurCartes(Jest jest,
                                     Map<CouleurCarte, List<Carte>> parCouleur,
                                     boolean hasJoker) {

        int total = 0;

        for (int i = 0; i < jest.taille(); i++) {
            Carte c = jest.getCarte(i);
            total += valeurCarte(c, parCouleur, hasJoker);
        }
        return total;
    }

    private int calculerPairesNoires(Map<ValeurCarte, List<Carte>> parValeur) {

        int bonus = 0;

        for (ValeurCarte v : parValeur.keySet()) {

            boolean hasPique = false;
            boolean hasTrefle = false;

            for (Carte c : parValeur.get(v)) {
                if (c.getCouleur() == CouleurCarte.PIQUE) hasPique = true;
                if (c.getCouleur() == CouleurCarte.TREFLE) hasTrefle = true;
            }

            if (hasPique && hasTrefle) {
                bonus += 2;
            }
        }
        return bonus;
    }

    private int valeurCarte(Carte c,
                            Map<CouleurCarte, List<Carte>> parCouleur,
                            boolean hasJoker) {

        if (c.getCouleur() == CouleurCarte.JOKER) {
            return valeurJoker(parCouleur);
        }

        if (c.getCouleur() == CouleurCarte.COEUR) {
            return valeurCoeur(c, parCouleur, hasJoker);
        }

        if (c.getValeur() == ValeurCarte.AS) {
            return valeurAs(c, parCouleur);
        }

        return valeurCarteNormale(c);
    }

    private int valeurJoker(Map<CouleurCarte, List<Carte>> parCouleur) {
        int nbCoeurs = parCouleur.getOrDefault(CouleurCarte.COEUR, List.of()).size();
        return (nbCoeurs == 0) ? 4 : 0;
    }

    private int valeurCoeur(Carte c,
                            Map<CouleurCarte, List<Carte>> parCouleur,
                            boolean hasJoker) {

        if (!hasJoker) return 0;

        int nbCoeurs = parCouleur.getOrDefault(CouleurCarte.COEUR, List.of()).size();
        int v = valeurNumerique(c.getValeur());

        return (nbCoeurs == 4) ? v : -v;
    }

    private int valeurAs(Carte c,
                         Map<CouleurCarte, List<Carte>> parCouleur) {

        int nbDansCouleur = parCouleur.getOrDefault(c.getCouleur(), List.of()).size();
        return (nbDansCouleur == 1) ? 5 : 1;
    }

    private int valeurCarteNormale(Carte c) {

        int v = valeurNumerique(c.getValeur());

        if (c.getCouleur() == CouleurCarte.PIQUE ||
                c.getCouleur() == CouleurCarte.TREFLE) {
            return v;
        }

        if (c.getCouleur() == CouleurCarte.CARREAU) {
            return -v;
        }

        return 0;
    }

    private int valeurNumerique(ValeurCarte v) {
        return v.getValeur();
    }
}
