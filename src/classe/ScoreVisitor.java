package classe;

import java.util.*;

/**
 * Visiteur pour calculer le score d'un joueur.
 * 
 * <p>Implémente le pattern Visitor pour calculer le score final
 * d'un joueur selon les règles du jeu Jest.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 * @see Visitor
 */
public class ScoreVisitor implements Visitor {

    /** Le score calculé du joueur. */
    private int score;

    /**
     * Visite un joueur et calcule son score.
     * 
     * @param joueur le joueur dont le score doit être calculé
     */
    @Override
    public void visit(Joueur joueur) {
        score = calculerScore(joueur.getJest());
    }

    /**
     * Récupère le score calculé.
     * 
     * @return le score du joueur
     */
    public int getScore() {
        return score;
    }

    /**
     * Calcule le score total d'un Jest.
     * 
     * @param jest le Jest à évaluer
     * @return le score total
     */
    private int calculerScore(Jest jest) {

        Map<CouleurCarte, List<Carte>> parCouleur = new EnumMap<>(CouleurCarte.class);
        Map<ValeurCarte, List<Carte>> parValeur = new EnumMap<>(ValeurCarte.class);

        boolean hasJoker = indexerCartes(jest, parCouleur, parValeur);

        int total = 0;

        total += calculerValeurCartes(jest, parCouleur, hasJoker);
        total += calculerPairesNoires(parValeur);

        return total;
    }

    /**
     * Indexe les cartes par couleur et par valeur.
     * 
     * @param jest le Jest contenant les cartes
     * @param parCouleur map à remplir avec les cartes groupées par couleur
     * @param parValeur map à remplir avec les cartes groupées par valeur
     * @return true si le Jest contient un joker, false sinon
     */
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

    /**
     * Calcule la valeur totale de toutes les cartes.
     * 
     * @param jest le Jest contenant les cartes
     * @param parCouleur map des cartes groupées par couleur
     * @param hasJoker true si le Jest contient un joker
     * @return la valeur totale des cartes
     */
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

    /**
     * Calcule le bonus pour les paires noires (pique + trèfle).
     * 
     * @param parValeur map des cartes groupées par valeur
     * @return le bonus total pour les paires noires
     */
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

    /**
     * Calcule la valeur d'une carte individuelle.
     * 
     * @param c la carte à évaluer
     * @param parCouleur map des cartes groupées par couleur
     * @param hasJoker true si le Jest contient un joker
     * @return la valeur de la carte
     */
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

    /**
     * Calcule la valeur du joker.
     * 
     * @param parCouleur map des cartes groupées par couleur
     * @return 4 si aucun cœur, 0 sinon
     */
    private int valeurJoker(Map<CouleurCarte, List<Carte>> parCouleur) {
        int nbCoeurs = parCouleur.getOrDefault(CouleurCarte.COEUR, List.of()).size();
        return (nbCoeurs == 0) ? 4 : 0;
    }

    /**
     * Calcule la valeur d'une carte cœur.
     * 
     * @param c la carte cœur
     * @param parCouleur map des cartes groupées par couleur
     * @param hasJoker true si le Jest contient un joker
     * @return la valeur de la carte cœur
     */
    private int valeurCoeur(Carte c,
                            Map<CouleurCarte, List<Carte>> parCouleur,
                            boolean hasJoker) {

        if (!hasJoker) return 0;

        int nbCoeurs = parCouleur.getOrDefault(CouleurCarte.COEUR, List.of()).size();
        int v = valeurNumerique(c.getValeur());

        return (nbCoeurs >= 3) ? v*2 : -v;
    }

    /**
     * Calcule la valeur d'un As.
     * 
     * @param c la carte As
     * @param parCouleur map des cartes groupées par couleur
     * @return 5 si seul de sa couleur, 1 sinon
     */
    private int valeurAs(Carte c,
                         Map<CouleurCarte, List<Carte>> parCouleur) {

        int nbDansCouleur = parCouleur.getOrDefault(c.getCouleur(), List.of()).size();
        return (nbDansCouleur == 1) ? 5 : 1;
    }

    /**
     * Calcule la valeur d'une carte normale (ni joker, ni cœur, ni As).
     * 
     * @param c la carte à évaluer
     * @return la valeur de la carte
     */
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

    /**
     * Récupère la valeur numérique d'une carte.
     * 
     * @param v la valeur de la carte
     * @return la valeur numérique
     */
    private int valeurNumerique(ValeurCarte v) {
        return v.getValeur();
    }
}
