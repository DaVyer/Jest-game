import classe.Partie;
import classe.Joueur;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    /**
     * Demande et valide le nom d'un joueur.
     *
     * @param scanner le Scanner pour lire l'entrée utilisateur
     * @param numeroJoueur le numéro du joueur (1 ou 2)
     * @return le nom validé du joueur
     */
    private static String demanderNomJoueur(Scanner scanner, int numeroJoueur) {
        String nom = ""; // initialiser avec une chaîne vide
        boolean valide = false;
        while (!valide) {
            System.out.println("Veuillez taper le nom du joueur " + numeroJoueur + " : ");
            nom = scanner.nextLine().trim();
            if (nom.isEmpty()) {
                System.out.println("Le nom ne peut pas être vide. Réessayez.");
                continue;
            }

            if (nom.replaceAll(" ", "").isEmpty()) {
                System.out.println("Le nom ne peut pas être que des espaces. Réessayez.");
                continue;
            }

            if (nom.matches("\\d+")) {
                System.out.println("Le nom ne peut pas être que des chiffres. Réessayez.");
                continue;
            }

            if (nom.length() < 2) {
                System.out.println("Le nom doit contenir au moins 2 caractères. Réessayez.");
                continue;
            }

            valide = true;
            System.out.println("Joueur " + numeroJoueur + " : " + nom);
        }
        return nom;
    }

    public static void main(String[] arg){
        Scanner scanner = new Scanner(System.in);
        AtomicBoolean enJeu = new AtomicBoolean(true);

        System.out.println("===============");
        System.out.println("Création des joueurs...\n");

        String nom1 = demanderNomJoueur(scanner, 1);
        String nom2 = demanderNomJoueur(scanner, 2);

        Joueur joueur1 = new Joueur(nom1);
        Joueur joueur2 = new Joueur(nom2);

        Partie partie = new Partie();
        partie.ajouterJoueurs(joueur1);
        partie.ajouterJoueurs(joueur2);
        // Thread pour lire l'input sans bloquer la boucle
       Thread inputThread = new Thread(() -> {
            while (enJeu.get()) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("exit")) {
                        enJeu.set(false);
                    }
                    if (input.equalsIgnoreCase("manche")) {
                        partie.jouerManche(scanner);
                    }
                    if (input.equalsIgnoreCase("help")) {
                        System.out.println("Commandes disponibles :");
                        System.out.println("-----------");
                        System.out.println("manche - Jouer une nouvelle manche");
                        System.out.println("status - Afficher le statut actuel de la partie (non implémenté)");
                        System.out.println("exit   - Quitter le jeu");
                        System.out.println("help   - Afficher cette aide");
                        System.out.println("save   - Sauvegarder la partie (non implémenté)");
                        System.out.println("load   - Charger une partie (non implémenté)");
                        System.out.println("-----------");
                    }
                }
            }
        });

        inputThread.setDaemon(true);
        inputThread.start();

        System.out.println("En jeu : " + enJeu.get());

        while (enJeu.get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("En jeu : " + enJeu.get());
        scanner.close();
    }
}
