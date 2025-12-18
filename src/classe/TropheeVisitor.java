package classe;

import java.util.*;

/**
 * Visiteur pour l'attribution des trophées.
 * 
 * <p>Implémente le pattern Visitor pour déterminer quels joueurs
 * remportent les trophées selon les conditions associées à chaque carte trophée.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 * @see Visitor
 */
public class TropheeVisitor implements Visitor {
    /** Liste des cartes trophées à attribuer. */
    private final List<Carte> trophees;
    
    /** Liste des joueurs visités. */
    private final List<Joueur> joueurs = new ArrayList<>();

    /**
     * Constructeur du TropheeVisitor.
     * 
     * @param trophees la liste des cartes trophées à attribuer
     */
    public TropheeVisitor(List<Carte> trophees) {
        this.trophees = trophees;
    }

    /**
     * Visite un joueur et l'ajoute à la liste des joueurs.
     * 
     * @param joueur le joueur à visiter
     */
    @Override
    public void visit(Joueur joueur) {
        joueurs.add(joueur);
    }

    /**
     * Attribue les trophées aux joueurs gagnants.
     * 
     * @return une map associant chaque joueur gagnant à ses trophées
     */
    public Map<Joueur, List<Carte>> attribuerTrophees() {

        Map<Joueur, List<Carte>> gains = new HashMap<>();

        for (Carte carte : trophees) {
            Joueur gagnant = determinerGagnant(carte);
            gains.computeIfAbsent(gagnant, k -> new ArrayList<>()).add(carte);
        }

        return gains;
    }

    /**
     * Détermine le gagnant d'un trophée selon sa condition.
     * 
     * @param trophee le trophée à attribuer
     * @return le joueur gagnant du trophée
     */
    private Joueur determinerGagnant(Carte trophee) {
        ConditionTrophee condition = trophee.getTrophee();
        return switch (condition) {

            // PLUS HAUTE CARTE D’UNE COULEUR
            case PLUSHAUTE_TREFLE -> gagnantPlusHauteCouleur(CouleurCarte.TREFLE);
            case PLUSHAUTE_PIQUE -> gagnantPlusHauteCouleur(CouleurCarte.PIQUE);
            case PLUSHAUTE_COEUR -> gagnantPlusHauteCouleur(CouleurCarte.COEUR);
            case PLUSHAUTE_CARREAU -> gagnantPlusHauteCouleur(CouleurCarte.CARREAU);

            // PLUS BASSE CARTE D’UNE COULEUR
            case PLUSBASSE_TREFLE -> gagnantPlusBasseCouleur(CouleurCarte.TREFLE);
            case PLUSBASSE_PIQUE -> gagnantPlusBasseCouleur(CouleurCarte.PIQUE);
            case PLUSBASSE_COEUR -> gagnantPlusBasseCouleur(CouleurCarte.COEUR);
            case PLUSBASSE_CARREAU -> gagnantPlusBasseCouleur(CouleurCarte.CARREAU);

            // MAJORITÉS
            case MAJORITE_4 -> gagnantMajorite(ValeurCarte.QUATRE);
            case MAJORITE_3 -> gagnantMajorite(ValeurCarte.TROIS);
            case MAJORITE_2 -> gagnantMajorite(ValeurCarte.DEUX);

            // AUTRES
            case JOKER -> gagnantAvecJoker();
            case MEILLEURJEST -> gagnantBestJest();
            case MEILLEURJESTSANSJOKER -> gagnantBestJestSansJoker();
        };
    }

    /**
     * Détermine le gagnant ayant la carte la plus haute d'une couleur.
     * 
     * @param couleur la couleur à évaluer
     * @return le joueur gagnant
     */
    private Joueur gagnantPlusHauteCouleur(CouleurCarte couleur) {

        Joueur best = null;
        Carte meilleure = null;

        for (Joueur j : joueurs) {
            Carte c = carteExtremeCouleur(j.getJest(), couleur, true);
            if (c == null) continue;

            if (meilleure == null || comparerCartes(c, meilleure) > 0) {
                meilleure = c;
                best = j;
            }
        }
        return best;
    }

    /**
     * Détermine le gagnant ayant la carte la plus basse d'une couleur.
     * 
     * @param couleur la couleur à évaluer
     * @return le joueur gagnant
     */
    private Joueur gagnantPlusBasseCouleur(CouleurCarte couleur) {

        Joueur best = null;
        Carte pire = null;

        for (Joueur j : joueurs) {
            Carte c = carteExtremeCouleur(j.getJest(), couleur, false);
            if (c == null) continue;

            if (pire == null || comparerCartes(c, pire) < 0) {
                pire = c;
                best = j;
            }
        }
        return best;
    }

    /**
     * Trouve la carte extrême (plus haute ou plus basse) d'une couleur dans un Jest.
     * 
     * @param jest le Jest à analyser
     * @param couleur la couleur recherchée
     * @param max true pour la plus haute, false pour la plus basse
     * @return la carte extrême, ou null si aucune carte de cette couleur
     */
    private Carte carteExtremeCouleur(Jest jest,
                                      CouleurCarte couleur,
                                      boolean max) {

        Carte best = null;

        for (int i = 0; i < jest.taille(); i++) {
            Carte c = jest.getCarte(i);
            if (c.getCouleur() == couleur) {
                if (best == null ||
                        (max && comparerCartes(c, best) > 0) ||
                        (!max && comparerCartes(c, best) < 0)) {
                    best = c;
                }
            }
        }
        return best;
    }

    /**
     * Détermine le gagnant ayant le meilleur Jest (carte la plus forte).
     * 
     * @return le joueur gagnant
     */
    private Joueur gagnantBestJest() {

        Joueur best = null;
        Carte meilleure = null;

        for (Joueur j : joueurs) {
            Carte c = carteLaPlusForte(j.getJest());
            if (meilleure == null || comparerCartes(c, meilleure) > 0) {
                meilleure = c;
                best = j;
            }
        }
        return best;
    }

    /**
     * Détermine le gagnant ayant le meilleur Jest parmi une liste de joueurs.
     * 
     * @param liste la liste de joueurs à évaluer
     * @return le joueur gagnant
     */
    private Joueur gagnantBestJest(List<Joueur> liste) {

        Joueur best = null;
        Carte meilleure = null;

        for (Joueur j : liste) {
            Carte c = carteLaPlusForte(j.getJest());
            if (meilleure == null || comparerCartes(c, meilleure) > 0) {
                meilleure = c;
                best = j;
            }
        }
        return best;
    }

    /**
     * Détermine le gagnant ayant le meilleur Jest sans joker.
     * 
     * @return le joueur gagnant
     */
    private Joueur gagnantBestJestSansJoker() {

        List<Joueur> sansJoker = new ArrayList<>();

        for (Joueur j : joueurs) {
            if (!possedeJoker(j.getJest())) {
                sansJoker.add(j);
            }
        }

        if (sansJoker.isEmpty()) return joueurs.getFirst(); // sécurité
        return gagnantBestJest(sansJoker);
    }

    /**
     * Trouve la carte la plus forte dans un Jest.
     * 
     * @param jest le Jest à analyser
     * @return la carte la plus forte
     */
    private Carte carteLaPlusForte(Jest jest) {

        Carte best = null;
        for (int i = 0; i < jest.taille(); i++) {
            Carte c = jest.getCarte(i);
            if (best == null || comparerCartes(c, best) > 0) {
                best = c;
            }
        }
        return best;
    }

    /**
     * Détermine le gagnant possédant un joker.
     * 
     * <p>En cas de plusieurs jokers, départage par le meilleur Jest.</p>
     * 
     * @return le joueur gagnant
     */
    private Joueur gagnantAvecJoker() {

        List<Joueur> candidats = new ArrayList<>();

        for (Joueur j : joueurs) {
            if (possedeJoker(j.getJest())) {
                candidats.add(j);
            }
        }

        if (candidats.isEmpty()) return joueurs.getFirst(); // sécurité
        if (candidats.size() == 1) return candidats.getFirst();

        return gagnantBestJest(candidats);
    }

    /**
     * Vérifie si un Jest contient un joker.
     * 
     * @param jest le Jest à vérifier
     * @return true si le Jest contient un joker, false sinon
     */
    private boolean possedeJoker(Jest jest) {
        for (int i = 0; i < jest.taille(); i++) {
            if (jest.getCarte(i).getCouleur() == CouleurCarte.JOKER) {
                return true;
            }
        }
        return false;
    }

    /**
     * Détermine le gagnant ayant la majorité d'une valeur.
     * 
     * @param valeur la valeur à évaluer
     * @return le joueur gagnant
     */
    private Joueur gagnantMajorite(ValeurCarte valeur) {

        int max = -1;
        List<Joueur> egalite = new ArrayList<>();

        for (Joueur j : joueurs) {
            int nb = compterValeur(j.getJest(), valeur);

            if (nb > max) {
                max = nb;
                egalite.clear();
                egalite.add(j);
            } else if (nb == max) {
                egalite.add(j);
            }
        }

        if (egalite.size() == 1) return egalite.getFirst();
        return departagerMajorite(egalite, valeur);
    }

    /**
     * Compte le nombre de cartes d'une valeur donnée dans un Jest.
     * 
     * @param jest le Jest à analyser
     * @param valeur la valeur à compter
     * @return le nombre de cartes de cette valeur
     */
    private int compterValeur(Jest jest, ValeurCarte valeur) {

        int count = 0;
        for (int i = 0; i < jest.taille(); i++) {
            if (jest.getCarte(i).getValeur() == valeur) count++;
        }
        return count;
    }

    /**
     * Départage les joueurs à égalité pour une majorité.
     * 
     * <p>Le départage se fait par la force de la couleur la plus forte.</p>
     * 
     * @param candidats la liste des joueurs à départager
     * @param valeur la valeur concernée
     * @return le joueur gagnant
     */
    private Joueur departagerMajorite(List<Joueur> candidats, ValeurCarte valeur) {

        Joueur best = null;
        int bestForce = -1;

        for (Joueur j : candidats) {
            int force = meilleureForceCouleur(j.getJest(), valeur);
            if (force > bestForce) {
                bestForce = force;
                best = j;
            }
        }
        return best;
    }

    /**
     * Trouve la meilleure force de couleur pour une valeur dans un Jest.
     * 
     * @param jest le Jest à analyser
     * @param valeur la valeur à évaluer
     * @return la force de couleur maximale
     */
    private int meilleureForceCouleur(Jest jest, ValeurCarte valeur) {

        int best = -1;
        for (int i = 0; i < jest.taille(); i++) {
            Carte c = jest.getCarte(i);
            if (c.getValeur() == valeur) {
                best = Math.max(best, forceCouleur(c.getCouleur()));
            }
        }
        return best;
    }

    /**
     * Compare deux cartes selon leur valeur faciale et leur couleur.
     * 
     * @param a la première carte
     * @param b la deuxième carte
     * @return un nombre négatif, zéro ou positif selon que a est inférieure, égale ou supérieure à b
     */
    private int comparerCartes(Carte a, Carte b) {

        int cmp = Integer.compare(valeurFaciale(a), valeurFaciale(b));
        if (cmp != 0) return cmp;

        return Integer.compare(
                forceCouleur(a.getCouleur()),
                forceCouleur(b.getCouleur())
        );
    }

    /**
     * Récupère la valeur faciale d'une carte.
     * 
     * @param c la carte à évaluer
     * @return la valeur faciale (0 pour joker, 1 pour As, sinon valeur numérique)
     */
    private int valeurFaciale(Carte c) {

        if (c.getCouleur() == CouleurCarte.JOKER) return 0;
        if (c.getValeur() == ValeurCarte.AS) return 1;
        return c.getValeur().getValeur();
    }

    /**
     * Récupère la force d'une couleur.
     * 
     * <p>Pique > Trèfle > Carreau > Cœur > Joker</p>
     * 
     * @param c la couleur à évaluer
     * @return la force de la couleur (4 pour Pique, 3 pour Trèfle, 2 pour Carreau, 1 pour Cœur, 0 pour Joker)
     */
    private int forceCouleur(CouleurCarte c) {

        return switch (c) {
            case PIQUE -> 4;
            case TREFLE -> 3;
            case CARREAU -> 2;
            case COEUR -> 1;
            default -> 0;
        };
    }
}
