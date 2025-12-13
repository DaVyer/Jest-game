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
    private Pioche pioche;
    private LinkedList<Joueur> joueurs;
    private LinkedList<Carte> trophees;
    private List<Offre> offres = new ArrayList<>();
    private ArrayList<Joueur> ordreDeJeu;
    private java.util.Set<Joueur> joueursAyantJoue;
    private boolean partieTerminee = false;

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

    /**
     * Retourne l'identifiant de la partie.
     *
     * @return l'identifiant de la partie
     */
    public int getIdPartie() {
        return this.idPartie;
    }

    /**
     * Définit l'identifiant de la partie.
     *
     * @param idPartie nouvel identifiant de la partie
     */
    public void setIdPartie(int idPartie) {
        this.idPartie = idPartie;
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


        Manche manche = new Manche();
        manche.incrementNumero();

        distribuerCartes();
        System.out.println("Manche numéro : " + manche.getNumero());

        faireOffre(scanner);
        carteLaPlusHaute();
        choisirOffre(scanner);

        System.out.println("\n===== Fin de la manche =====");
        for (Joueur j : joueurs) {
            j.afficherMain();
        }

    }


    public void distribuerCartes(){
        if (pioche.estVide()) {
            throw new IllegalStateException(
                    "Pas assez de cartes dans la pioche pour distribuer une nouvelle manche."
            );
        }
        for (Joueur j : joueurs){
            j.ajouterAuJest(this.pioche.piocher());
            j.ajouterAuJest(this.pioche.piocher());
        }
    }

    public void faireOffre(Scanner scanner){
        offres = new ArrayList<>();
        for (Joueur j : joueurs){
            System.out.println("Joueur "+j.getNom()+" : Faites une offre.");
            Offre offre = j.faireOffre(scanner);
            offres.add(offre);
        }
    }

    public void carteLaPlusHaute(){
        ordreDeJeu = new ArrayList<>(joueurs);

        ordreDeJeu.sort((j1, j2) -> {
            Offre o1 = getOffreDe(j1);
            Offre o2 = getOffreDe(j2);

            Carte c1 = o1.getVisible();
            Carte c2 = o2.getVisible();

            int cmp = Integer.compare(
                    c2.valeurPourManche(),
                    c1.valeurPourManche()
            );

            if (cmp != 0) return cmp;

            return Integer.compare(
                    c2.forceCouleur(),
                    c1.forceCouleur()
            );
        });
    }

    private Offre getOffreDe(Joueur joueur) {
        for (Offre o : offres) {
            if (o.getJoueur().equals(joueur)) {
                return o;
            }
        }
        return null;
    }

    public void choisirOffre(Scanner scanner) {

        joueursAyantJoue = new HashSet<>();

        for (Joueur joueurActuel : ordreDeJeu) {

            if (joueursAyantJoue.contains(joueurActuel)) {
                continue;
            }

            System.out.println("\nTour de " + joueurActuel.getNom());

            List<Offre> offresCompletes = offres.stream()
                    .filter(Offre::isDisponible)
                    .collect(Collectors.toList());

            List<Offre> offresDisponibles;

            if (offresCompletes.size() == 1 &&
                    offresCompletes.get(0).getJoueur().equals(joueurActuel)) {

                offresDisponibles = offresCompletes;

            } else {
                offresDisponibles = offresCompletes.stream()
                        .filter(o -> !o.getJoueur().equals(joueurActuel))
                        .collect(Collectors.toList());
            }

            Offre choisie = joueurActuel.choisirOffre(offresDisponibles, scanner);

            Carte prise = joueurActuel.choisirCarteOffre(choisie, scanner);
            joueurActuel.ajouterAuJest(prise);

            joueursAyantJoue.add(joueurActuel);
        }
    }

    public void finDePartie() {
        if (!partieTerminee) {
            partieTerminee = true;
            System.out.println("\n===== FIN DE LA PARTIE =====");

            for (Offre o : offres) {
                Carte restante = o.prendreCarteRestante();
                o.getJoueur().ajouterAuJest(restante);
            }

            System.out.println("\n===== JESTS FINAUX =====");
            for (Joueur j : joueurs) {
                j.afficherMain();
            }
        } else {
            System.out.println("La partie est déjà terminée.");
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

    // Je mets pas le accept de visitor on verra ca direct au moment du patron de conception


}