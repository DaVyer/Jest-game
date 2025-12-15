import classe.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    private static int demanderNombreJoueurs(Scanner scanner) {
        int nb = 0;
        while (nb < 2 || nb > 4) {
            System.out.print("Nombre de joueurs (2 à 4) : ");
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

    public static void main(String[] arg){
        Scanner scanner = new Scanner(System.in);
        AtomicBoolean enJeu = new AtomicBoolean(true);

        System.out.println("===============");
        System.out.println("Création des joueurs...\n");

        int nbJoueurs = demanderNombreJoueurs(scanner);

        List<Joueur> joueurs = creerJoueurs(scanner, nbJoueurs);

        Partie partie = new Partie();
        for (Joueur j : joueurs) {
            partie.ajouterJoueurs(j);
        }


        while (enJeu.get()) {
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    enJeu.set(false);
                }
                if (input.equalsIgnoreCase("manche")) {
                    partie.jouerManche(scanner);
                }
                if (input.equalsIgnoreCase("save")) {
                    try {
                        Save.sauvegarder(partie, "sauvegarde.ser");
                        System.out.println("Partie sauvegardée.");
                    } catch (IOException e) {
                        System.out.println("Erreur sauvegarde : " + e.getMessage());
                    }
                }

                if (input.equalsIgnoreCase("load")) {
                    try {
                        partie = Load.charger("sauvegarde.ser");
                        System.out.println("Partie chargée.");
                    } catch (Exception e) {
                        System.out.println("Erreur chargement : " + e.getMessage());
                    }
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
