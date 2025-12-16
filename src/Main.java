import classe.*;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    private static int demanderNombreJoueurs(Scanner scanner) {
        int nb = 0;
        while (nb < 3 || nb > 4) {
            System.out.print("Nombre de joueurs (3 à 4) : ");
            try {
                nb = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                nb = 0;
            }
        }
        return nb;
    }

    private static StrategieJoueur demanderStrategie(Scanner scanner) {
        while (true) {
            System.out.print("Type (h = humain, r = robot) : ");
            String type = scanner.nextLine().trim();

            if (type.equalsIgnoreCase("h")) return new StrategieHumaine();
            if (type.equalsIgnoreCase("r")) return new StrategieRobotAleatoire();

            System.out.println("Choix invalide. Tape 'h' ou 'r'.");
        }
    }

    private static List<Joueur> creerJoueurs(Scanner scanner, int nbJoueurs) {
        List<Joueur> joueurs = new ArrayList<>();

        for (int i = 1; i <= nbJoueurs; i++) {
            String nom = demanderNomJoueur(scanner, i);
            StrategieJoueur strat = demanderStrategie(scanner);
            joueurs.add(new Joueur(nom, strat));
        }
        return joueurs;
    }

    private static void presentation() {
        System.out.println("================================");
        System.out.println("        BIENVENUE DANS JEST      ");
        System.out.println("================================");
        System.out.println("Jeu de cartes stratégique");
        System.out.println("3 à 4 joueurs | Humains ou Robots");
        System.out.println();
    }

    private static void afficherMenuPrincipal() {
        System.out.println("Commandes principales :");
        System.out.println(" - new   : Nouvelle partie");
        System.out.println(" - load  : Charger une partie");
        System.out.println(" - help  : Aide");
        System.out.println(" - exit  : Quitter");
    }

    private static Partie creerNouvellePartie(Scanner scanner) {

        int nbJoueurs = demanderNombreJoueurs(scanner);
        List<Joueur> joueurs = creerJoueurs(scanner, nbJoueurs);

        Partie partie = new Partie();
        for (Joueur j : joueurs) {
            partie.ajouterJoueurs(j);
        }

        System.out.println("Nouvelle partie créée !");
        return partie;
    }

    private static void afficherCommandesJeu() {
        System.out.println("\nCommandes de jeu :");
        System.out.println(" - manche : jouer une manche");
        System.out.println(" - save   : sauvegarder la partie");
        System.out.println(" - status : afficher l'état");
        System.out.println(" - help   : aide");
        System.out.println(" - exit   : quitter");
    }

    private static void sauvegarder(Partie partie) {
        try {
            Save.sauvegarder(partie, "sauvegarde.ser");
            System.out.println("Partie sauvegardée avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    private static Partie charger() {
        try {
            Partie partie = Load.charger("sauvegarde.ser");

            System.out.println("Partie chargée avec succès.");
            return partie;
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement : " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean enCours = true;
        Partie partie = null;

        presentation();
        afficherMenuPrincipal();

        while (enCours) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (partie == null) {

                switch (input) {
                    case "new" -> {
                        partie = creerNouvellePartie(scanner);
                        afficherCommandesJeu();
                    }
                    case "load" -> partie = charger();
                    case "help" -> afficherMenuPrincipal();
                    case "exit" -> enCours = false;
                    default -> System.out.println("Commande inconnue (new / help / exit)");
                }

            } else {

                switch (input) {
                    case "manche" -> partie.jouerManche(scanner);
                    case "save" -> sauvegarder(partie);
                    case "status" -> partie.afficherEtat();
                    case "exit" -> enCours = false;
                    case "help" -> afficherCommandesJeu();
                    default -> System.out.println("Commande inconnue (help)");
                }
            }
        }

        System.out.println("Merci d'avoir joué !");
        scanner.close();
    }
}
