package classe;

import java.util.*;

public class TropheeVisitor implements Visitor {
    private final List<Carte> trophees;
    private final List<Joueur> joueurs = new ArrayList<>();

    public TropheeVisitor(List<Carte> trophees) {
        this.trophees = trophees;
    }

    @Override
    public void visit(Joueur joueur) {
        joueurs.add(joueur);
    }


    public Map<Joueur, List<Carte>> attribuerTrophees() {

        Map<Joueur, List<Carte>> gains = new HashMap<>();

        for (Carte carte : trophees) {
            Joueur gagnant = determinerGagnant(carte);
            gains.computeIfAbsent(gagnant, k -> new ArrayList<>()).add(carte);
        }

        return gains;
    }

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

    private boolean possedeJoker(Jest jest) {
        for (int i = 0; i < jest.taille(); i++) {
            if (jest.getCarte(i).getCouleur() == CouleurCarte.JOKER) {
                return true;
            }
        }
        return false;
    }

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

    private int compterValeur(Jest jest, ValeurCarte valeur) {

        int count = 0;
        for (int i = 0; i < jest.taille(); i++) {
            if (jest.getCarte(i).getValeur() == valeur) count++;
        }
        return count;
    }

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

    private int comparerCartes(Carte a, Carte b) {

        int cmp = Integer.compare(valeurFaciale(a), valeurFaciale(b));
        if (cmp != 0) return cmp;

        return Integer.compare(
                forceCouleur(a.getCouleur()),
                forceCouleur(b.getCouleur())
        );
    }

    private int valeurFaciale(Carte c) {

        if (c.getCouleur() == CouleurCarte.JOKER) return 0;
        if (c.getValeur() == ValeurCarte.AS) return 1;
        return c.getValeur().getValeur();
    }

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
