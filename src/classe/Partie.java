package classe;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Représente la partie, du début jusqu'à la fin de la partie
 *
 * <p>Elle permet de gerer le tour des joueurs, la mise en place des offres, des pioches....
 * </p>
 * 
 *
 * @author Tristan
 * @version %I%, %G%
 */

public class Partie{
    private final static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private int idPartie;
    private final Pioche pioche;
    private LinkedList<Joueur> joueurs;
    private LinkedList<Carte> trophees;
    private List<Offre> offres = new ArrayList<>();
    private boolean partieTerminee = false;
    private final Manche manche = new Manche();

    /**
     * Constructeur de la classe Partie
     */
    public Partie(){
        this.idPartie = ID_GENERATOR.getAndIncrement();
        // System.out.println("\nNouvelle partie créé :\n\t" + this.getIdPartie() + "\n\n===============" );

        this.pioche = new Pioche();
        this.pioche.setPioche();
        System.out.println("\nPioche créé : \n" + this.pioche.getPioche() +"\n\n==============="); // A supprimer, on ne veut pas voir la pioche a chaque parties.

        this.setTrophees();
        System.out.println("\nTrophé Tiré : "); 
        System.out.println("Trophé 1 : " + this.trophees.get(0) + " de condition : " + this.trophees.get(0).getTrophee());
        System.out.println("Trophé 2 : " + this.trophees.get(1) + " de condition : " + this.trophees.get(1).getTrophee());
        System.out.println("Pioche : " + this.getPioche().getPioche()); 

        this.setJoueurs();
    }

    public Partie(boolean initialiser) {
        this.idPartie = ID_GENERATOR.getAndIncrement();
        this.joueurs = new LinkedList<>();
        this.trophees = new LinkedList<>();
        this.pioche = new Pioche();

        if (initialiser) {
            this.pioche.setPioche();
            setTrophees();
        }
    }

    /**
     * Retourne l'identifiant de la partie.
     *
     * @return l'identifiant de la partie
     */
    public int getIdPartie() {
        return this.idPartie;
    }

    /**
     * Retourne la pioche associée à la partie.
     *
     * @return la pioche utilisée dans la partie (peut être `null`)
     */
    public Pioche getPioche() {
        return this.pioche;
    }

    /**
     * Retourne la liste des joueurs participant à la partie.
     *
     * @return la liste des joueurs (peut être vide)
     */
    public LinkedList<Joueur> getJoueurs() {
        return this.joueurs;
    }

    /**
     * Réinitialise la liste des joueurs à une liste vide.
     *
     * <p>Cette méthode ne prend pas de paramètre et remplace la liste interne
     * par une nouvelle liste vide.</p>
     */
    public void setJoueurs() {
        this.joueurs = new LinkedList<>();
    }

    /**
     * Retourne la liste des trophées de la partie.
     *
     * @return la liste des trophées (peut être vide)
     */
    public LinkedList<Carte> getTrophees() {
        return this.trophees;
    }

    /**
     * Réinitialise la liste des trophées à une liste vide.
     *
     * <p>Cette méthode crée et associe une nouvelle liste vide à l'attribut interne.</p>
     */
    public void setTrophees() {
        this.trophees = new LinkedList<>();
        this.trophees.add(this.pioche.piocher());
        this.trophees.add(this.pioche.piocher());

    } 
    
    /**
     * Ajoute un joueur à la partie.
     *
     * @param joueur le joueur à ajouter
     */
    public void ajouterJoueurs(Joueur joueur){
        this.joueurs.add(joueur);
        System.out.println("\n===============\n\n\t Joueur " + joueur.getIdJoueur() + " ajouté");
    }

    /**
     * Supprime un joueur de la partie.
     *
     * @param joueur le joueur à supprimer
     */
    public void supprimerJoueur(Joueur joueur){
        this.joueurs.remove(joueur);
    }

    public void jouerManche(Scanner scanner) {
        int cartesNecessaires = joueurs.size() * 2;

        if (pioche.getNombreCartes() < cartesNecessaires) {
            finDePartie();
            return;
        }

        manche.incrementNumero();

        System.out.println("===============");
        System.out.println("Manche numéro : " + manche.getNumero());
        System.out.println("===============");

        debutManche();
        creerOffres(scanner);
        resolutionOffres(scanner);
        finManche();

        System.out.println("\n===== Fin de la manche =====");
        for (Joueur j : joueurs) {
            j.afficherMain();
        }
        System.out.println("\nTapez 'manche' pour jouer une nouvelle manche ou 'exit' pour terminer la partie. Tapez help pour l'aide.");
    }

    private void debutManche() {
        for (Joueur j : joueurs) {
            j.viderMainManche();
            j.ajouterCarteManche(pioche.piocher());
            j.ajouterCarteManche(pioche.piocher());
        }
    }

    private void creerOffres(Scanner scanner) {
        offres.clear();
        for (Joueur j : joueurs) {
            offres.add(j.faireOffre(scanner));
        }
    }

    private void resolutionOffres(Scanner scanner) {
        List<Joueur> ordre = ordreDeJeuParCarteVisible();

        for (Joueur j : ordre) {
            List<Offre> offresAdverses = offres.stream()
                    .filter(o -> !o.getJoueur().equals(j))
                    .collect(Collectors.toList());

            Offre choisie = j.choisirOffre(offresAdverses, scanner);
            Carte prise = j.choisirCarteOffre(choisie, scanner);
            j.ajouterAuJest(prise);
        }
    }

    private void finManche() {
        for (Offre o : offres) {
            Carte restante = o.prendreCarteRestante();
            if (restante != null) {
                pioche.remettre(restante);
            }
        }
        offres.clear();
    }

    private List<Joueur> ordreDeJeuParCarteVisible() {
        Map<Joueur, Offre> offreParJoueur = offres.stream()
                .collect(Collectors.toMap(Offre::getJoueur, o -> o));

        List<Joueur> ordre = new ArrayList<>(joueurs);

        ordre.sort((j1, j2) -> {
            Carte c1 = offreParJoueur.get(j1).getVisible();
            Carte c2 = offreParJoueur.get(j2).getVisible();

            int cmp = Integer.compare(c2.valeurPourManche(), c1.valeurPourManche());
            if (cmp != 0) return cmp;

            return Integer.compare(c2.forceCouleur(), c1.forceCouleur());
        });

        return ordre;
    }

    public void finDePartie() {
        if (!partieTerminee) {
            partieTerminee = true;
            System.out.println("\n===== FIN DE LA PARTIE =====");

            attribuerTrophees();

            jestFinaux();

            scoreFinaux();

        } else {
            System.out.println("La partie est déjà terminée.");
        }
    }

    private void jestFinaux(){
        System.out.println("\n===== JESTS FINAUX =====");
        for (Joueur j : joueurs) {
            j.afficherMain();
        }
    }

    private void scoreFinaux() {
        System.out.println("\n===== SCORES FINAUX =====");

        Joueur gagnant = null;
        int meilleur = Integer.MIN_VALUE;

        for (Joueur j : joueurs) {
            ScoreVisitor visitor = new ScoreVisitor();
            j.accept(visitor);
            int score = visitor.getScore();

            System.out.println(j.getNom() + " : " + score);

            if (score > meilleur) {
                meilleur = score;
                gagnant = j;
            }
        }
    }

    private void attribuerTrophees() {

        TropheeVisitor visitor = new TropheeVisitor(trophees);

        for (Joueur j : joueurs) {
            j.accept(visitor);
        }

        Map<Joueur, List<Carte>> resultats = visitor.attribuerTrophees();

        System.out.println("\n===== ATTRIBUTION DES TROPHÉES =====");
        for (Carte t : trophees) {
            Joueur gagnant = null;

            for (Map.Entry<Joueur, List<Carte>> e : resultats.entrySet()) {
                if (e.getValue().contains(t)) {
                    gagnant = e.getKey();
                    break;
                }
            }

            System.out.println("- " + t + " (condition : " + t.getTrophee() + ") -> "
                    + (gagnant != null ? gagnant.getNom() : "aucun"));
        }

        for (Map.Entry<Joueur, List<Carte>> e : resultats.entrySet()) {
            for (Carte c : e.getValue()) {
                e.getKey().ajouterAuJest(c);
            }
        }
    }

    /**
     * Calcule les scores des joueurs selon les règles du jeu.
     *
     * <p>À définir : implémenter le calcul des scores. La méthode est
     * actuellement `void` ; adapter la signature si un retour est nécessaire.</p>
     */
    public void calculerScore(){
        // A definir
        // On est sur que c'est un void ?
    }

    public int getNumeroManche() {
        return manche.getNumero();
    }

    public boolean isPartieTerminee() {
        return partieTerminee;
    }

    public void setTropheesDepuisDTO(List<CarteDTO> trophees) {
        this.trophees = new LinkedList<>();
        for (CarteDTO cDTO : trophees) {
            this.trophees.add(DTOMapper.carteFromDTO(cDTO));
        }
    }

    public void setNumeroManche(int numeroManche) {
        this.manche.setNumero(numeroManche);
    }

    public void setPartieTerminee(boolean partieTerminee) {
        this.partieTerminee = partieTerminee;
    }

    public void afficherEtat() {
        System.out.println("\n===== ÉTAT DE LA PARTIE =====");
        System.out.println("Nombre de joueurs : " + joueurs.size());

        for (Joueur j : joueurs) {
            j.afficherMain();
        }

        System.out.println("Cartes restantes dans la pioche : "
                + pioche.getPioche().size());
    }
}