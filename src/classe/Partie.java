package classe;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
    /** Générateur d'identifiants uniques pour les parties. */
    private final static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    
    /** Identifiant unique de la partie. */
    private int idPartie;
    
    /** La pioche de cartes de la partie. */
    private final Pioche pioche;
    
    /** Liste des joueurs participant à la partie. */
    private LinkedList<Joueur> joueurs;
    
    /** Liste des cartes trophées de la partie. */
    private LinkedList<Carte> trophees;
    
    /** Liste des offres en cours pour la manche actuelle. */
    private List<Offre> offres = new ArrayList<>();
    
    /** Indique si la partie est terminée. */
    private boolean partieTerminee = false;
    
    /** La manche courante. */
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
        System.out.println("\n===============\n"); 

        this.setJoueurs();
    }

    /**
     * Constructeur de la classe Partie avec option d'initialisation.
     * 
     * <p>Permet de créer une partie vide sans initialiser la pioche
     * et les trophées, utile pour le chargement de parties.</p>
     * 
     * @param initialiser true pour initialiser la pioche et les trophées, false sinon
     */
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
    }

    /**
     * Supprime un joueur de la partie.
     *
     * @param joueur le joueur à supprimer
     */
    public void supprimerJoueur(Joueur joueur){
        this.joueurs.remove(joueur);
    }

    /**
     * Joue une manche complète de la partie.
     * 
     * <p>Gère le déroulement d'une manche : distribution des cartes,
     * création des offres, résolution des offres et fin de manche.</p>
     * 
     * @param scanner le scanner pour les interactions avec les joueurs
     */
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

    /**
     * Initialise le début d'une manche.
     * 
     * <p>Vide les mains des joueurs et distribue 2 cartes à chaque joueur.</p>
     */
    private void debutManche() {
        for (Joueur j : joueurs) {
            j.viderMainManche();
            j.ajouterCarteManche(pioche.piocher());
            j.ajouterCarteManche(pioche.piocher());
        }
    }

    /**
     * Crée les offres de tous les joueurs.
     * 
     * <p>Chaque joueur utilise sa stratégie pour créer une offre
     * avec une carte visible et une carte cachée.</p>
     * 
     * @param scanner le scanner pour les interactions avec les joueurs
     */
    private void creerOffres(Scanner scanner) {
        offres.clear();
        for (Joueur j : joueurs) {
            offres.add(j.getStrategie().faireOffre(j, scanner));
        }
    }

    /**
     * Résout les offres pour tous les joueurs.
     * 
     * <p>Chaque joueur choisit une offre disponible (pas la sienne si possible)
     * et prend une carte de cette offre pour l'ajouter à son Jest.</p>
     * 
     * @param scanner le scanner pour les interactions avec les joueurs
     */
    private void resolutionOffres(Scanner scanner) {

        Set<Joueur> joueursAyantJoue = new HashSet<>();

        for (Joueur joueurActuel : joueurs) {

            List<Offre> offresCompletes = offres.stream()
                    .filter(Offre::isDisponible)
                    .toList();

            List<Offre> offresDisponibles;

            if (offresCompletes.size() == 1 &&
                    offresCompletes.getFirst().getJoueur().equals(joueurActuel)) {

                offresDisponibles = offresCompletes;

            } else {
                offresDisponibles = offresCompletes.stream()
                        .filter(o -> !o.getJoueur().equals(joueurActuel))
                        .toList();
            }

            StrategieJoueur strategie = joueurActuel.getStrategie();

            Offre choisie = strategie.choisirOffre(offresDisponibles, joueurActuel, scanner);
            Carte prise = strategie.choisirCarteOffre(choisie, joueurActuel, scanner);
            joueurActuel.ajouterAuJest(prise);

            joueursAyantJoue.add(joueurActuel);
        }
    }

    /**
     * Finalise la manche en cours.
     * 
     * <p>Remet les cartes restantes des offres dans la pioche
     * et vide la liste des offres.</p>
     */
    private void finManche() {
        for (Offre o : offres) {
            Carte restante = o.prendreCarteRestante();
            if (restante != null) {
                pioche.remettre(restante);
            }
        }
        offres.clear();
    }

    /**
     * Termine la partie et affiche les résultats.
     * 
     * <p>Attribue les trophées, affiche les Jests finaux
     * et calcule les scores de tous les joueurs.</p>
     */
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

    /**
     * Affiche les Jests finaux de tous les joueurs.
     */
    private void jestFinaux(){
        System.out.println("\n===== JESTS FINAUX =====");
        for (Joueur j : joueurs) {
            j.afficherMain();
        }
    }

    /**
     * Calcule et affiche les scores finaux de tous les joueurs.
     * 
     * <p>Utilise le ScoreVisitor pour calculer le score de chaque joueur
     * et détermine le gagnant.</p>
     */
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

    /**
     * Attribue les trophées aux joueurs.
     * 
     * <p>Utilise le TropheeVisitor pour déterminer quel joueur remporte
     * chaque trophée selon les conditions. Ajoute les trophées aux Jests des gagnants.</p>
     */
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

    /**
     * Récupère le numéro de la manche courante.
     * 
     * @return le numéro de la manche
     */
    public int getNumeroManche() {
        return manche.getNumero();
    }

    /**
     * Vérifie si la partie est terminée.
     * 
     * @return true si la partie est terminée, false sinon
     */
    public boolean isPartieTerminee() {
        return partieTerminee;
    }

    /**
     * Définit les trophées depuis une liste de DTO.
     * 
     * <p>Utilisé lors du chargement d'une partie sauvegardée.</p>
     * 
     * @param trophees la liste de CarteDTO à convertir
     */
    public void setTropheesDepuisDTO(List<CarteDTO> trophees) {
        this.trophees = new LinkedList<>();
        for (CarteDTO cDTO : trophees) {
            this.trophees.add(DTOMapper.carteFromDTO(cDTO));
        }
    }

    /**
     * Définit le numéro de la manche courante.
     * 
     * @param numeroManche le numéro de manche à définir
     */
    public void setNumeroManche(int numeroManche) {
        this.manche.setNumero(numeroManche);
    }

    /**
     * Définit si la partie est terminée.
     * 
     * @param partieTerminee true si la partie est terminée, false sinon
     */
    public void setPartieTerminee(boolean partieTerminee) {
        this.partieTerminee = partieTerminee;
    }

    /**
     * Affiche l'état actuel de la partie.
     * 
     * <p>Affiche le nombre de joueurs, les Jests de chaque joueur
     * et le nombre de cartes restantes dans la pioche.</p>
     */
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