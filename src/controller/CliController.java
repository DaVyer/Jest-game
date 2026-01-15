package controller;

import model.Partie;
import model.*;
import service.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CliController implements Runnable {

    private final GameManager gameManager;
    private final Scanner scanner;
    private volatile boolean enCours = true;

    public CliController(GameManager gameManager, Scanner scanner) {
        this.gameManager = gameManager;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        presentation();
        afficherMenuPrincipal();

        while (enCours) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            synchronized (gameManager) {
                if (!gameManager.hasPartie()) {
                    switch (input) {
                        case "new" -> {
                            int nb = demanderNombreJoueurs(scanner);
                            List<Joueur> joueurs = creerJoueurs(scanner, nb);
                            boolean modeVariante = demanderModeJeu(scanner);
                            boolean extensionActive = demanderExtension(scanner);
                            gameManager.nouvellePartie(joueurs);
                            Partie p = gameManager.getPartie();
                            p.setModeVariante(modeVariante);
                            p.setExtensionActive(extensionActive);
                            p.initialiserPiocheEtTrophees();
                            afficherCommandesJeu();
                        }
                        case "load" -> {
                            Partie p = charger();
                            if (p != null) gameManager.chargerPartie(p);
                            afficherCommandesJeu();
                        }
                        case "help" -> afficherMenuPrincipal();
                        case "exit" -> enCours = false;
                        default -> System.out.println("Commande inconnue.");
                    }
                } else {
                    Partie partie = gameManager.getPartie();
                    switch (input) {
                        case "manche" -> partie.jouerManche(new ConsoleInputProvider(scanner));
                        case "save" -> sauvegarder(partie);
                        case "status" -> partie.afficherEtat();
                        case "help" -> afficherCommandesJeu();
                        case "exit" -> enCours = false;
                        default -> System.out.println("Commande inconnue.");
                    }
                }
            }
        }
    }

    private static String demanderNomJoueur(Scanner scanner, int numeroJoueur) {
        String nom = "";
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

    private static boolean demanderModeJeu(Scanner scanner) {
        System.out.println("\n===== MODE DE JEU =====");
        System.out.println("1. Règles de base :");
        System.out.println("   - Joker + 1-3 Coeurs : les Coeurs sont NÉGATIFS, Joker = 0");
        System.out.println("   - Joker + 4 Coeurs : les Coeurs sont POSITIFS, Joker = 0");
        System.out.println("\n2. Variante :");
        System.out.println("   - Joker + 3+ Coeurs : les Coeurs valent le DOUBLE");
        System.out.println("   - Joker + moins de 3 Coeurs : les Coeurs sont NÉGATIFS");
        
        while (true) {
            System.out.print("\nChoisissez le mode (1 = Base, 2 = Variante) : ");
            try {
                int choix = Integer.parseInt(scanner.nextLine());
                if (choix == 1) {
                    System.out.println("Mode : Règles de base sélectionnées.\n");
                    return false;
                } else if (choix == 2) {
                    System.out.println("Mode : Variante sélectionnée.\n");
                    return true;
                } else {
                    System.out.println("Choix invalide. Tapez 1 ou 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Tapez 1 ou 2.");
            }
        }
    }
    
    private static boolean demanderExtension(Scanner scanner) {
        System.out.println("\n===== EXTENSION MIROIR =====");
        System.out.println("Le Miroir copie la valeur (avec le signe) de votre carte la plus forte.");
        System.out.println("Exemples : meilleur +5 => Miroir +5, meilleur -4 => Miroir -4.");
        while (true) {
            System.out.print("\nActiver l'extension Miroir ? (1 = Oui, 2 = Non) : ");
            try {
                int choix = Integer.parseInt(scanner.nextLine());
                if (choix == 1) {
                    System.out.println("Extension Miroir activée.\n");
                    return true;
                } else if (choix == 2) {
                    System.out.println("Extension Miroir désactivée.\n");
                    return false;
                } else {
                    System.out.println("Choix invalide. Tapez 1 ou 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Tapez 1 ou 2.");
            }
        }
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
}
