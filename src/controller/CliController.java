package controller;

import model.Partie;
import model.*;
import service.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contrôleur pour l'interface en ligne de commande (CLI) du jeu Jest.
 * 
 * <p>Gère l'interaction avec l'utilisateur via la console, permettant
 * de créer des parties, de les charger/sauvegarder, de jouer des manches
 * et d'afficher l'état du jeu. Le contrôleur s'exécute dans un thread séparé
 * et communique avec le {@link GameManager} de manière synchronisée.</p>
 * 
 * @author Gwendal Rodrigues, Tristan Crémonat
 * @version 03/01/2026
 */
public class CliController implements Runnable {

    /** Le gestionnaire de jeu partagé avec l'interface graphique. */
    private final GameManager gameManager;
    
    /** Le scanner pour lire les entrées utilisateur. */
    private final Scanner scanner;
    
    /** Indicateur d'exécution du contrôleur. */
    private volatile boolean enCours = true;

    /**
     * Constructeur du CliController.
     * 
     * @param gameManager le gestionnaire de jeu
     * @param scanner le scanner pour lire les entrées console
     */
    public CliController(GameManager gameManager, Scanner scanner) {
        this.gameManager = gameManager;
        this.scanner = scanner;
    }

    /**
     * Méthode principale du thread du contrôleur CLI.
     * 
     * <p>Affiche le menu et traite les commandes de l'utilisateur en boucle
     * jusqu'à ce que la commande 'exit' soit saisie. Les commandes disponibles
     * varient selon qu'une partie est en cours ou non.</p>
     */
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

    /**
     * Demande et valide le nom d'un joueur.
     * 
     * <p>Le nom doit :
     * <ul>
     * <li>Ne pas être vide ou composé uniquement d'espaces</li>
     * <li>Ne pas être composé uniquement de chiffres</li>
     * <li>Contenir au moins 2 caractères</li>
     * </ul>
     * La méthode redemande le nom jusqu'à ce qu'il soit valide.</p>
     * 
     * @param scanner le scanner pour lire l'entrée
     * @param numeroJoueur le numéro du joueur (pour l'affichage)
     * @return le nom valide du joueur
     */
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

    /**
     * Demande à l'utilisateur de choisir le mode de jeu.
     * 
     * <p>Deux modes sont disponibles :
     * <ul>
     * <li>Règles de base : Joker + 1-3 Cœurs = Cœurs négatifs, Joker = 0 ; Joker + 4 Cœurs = Cœurs positifs, Joker = 0</li>
     * <li>Variante : Joker + 3+ Cœurs = Cœurs valent le double ; Joker + moins de 3 Cœurs = Cœurs négatifs</li>
     * </ul>
     * 
     * @param scanner le scanner pour lire l'entrée
     * @return false pour les règles de base, true pour la variante
     */
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
    
    /**
     * Demande si l'extension Miroir doit être activée.
     * 
     * <p>L'extension Miroir ajoute une carte qui copie la valeur
     * (avec le signe) de la meilleure carte du joueur.</p>
     * 
     * @param scanner le scanner pour lire l'entrée
     * @return true pour activer l'extension, false sinon
     */
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
    
    /**
     * Demande le nombre de joueurs pour la partie.
     * 
     * <p>Le nombre doit être entre 3 et 4 (inclus).
     * Redemande jusqu'à obtenir un nombre valide.</p>
     * 
     * @param scanner le scanner pour lire l'entrée
     * @return le nombre de joueurs (3 ou 4)
     */
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

    /**
     * Demande le type de stratégie pour un joueur.
     * 
     * <p>Les options sont :
     * <ul>
     * <li>'h' pour un joueur humain ({@link StrategieHumaine})</li>
     * <li>'r' pour un robot aléatoire ({@link StrategieRobotAleatoire})</li>
     * </ul>
     * 
     * @param scanner le scanner pour lire l'entrée
     * @return la stratégie choisie
     */
    private static StrategieJoueur demanderStrategie(Scanner scanner) {
        while (true) {
            System.out.print("Type (h = humain, r = robot) : ");
            String type = scanner.nextLine().trim();

            if (type.equalsIgnoreCase("h")) return new StrategieHumaine();
            if (type.equalsIgnoreCase("r")) return new StrategieRobotAleatoire();

            System.out.println("Choix invalide. Tape 'h' ou 'r'.");
        }
    }

    /**
     * Crée la liste des joueurs pour une nouvelle partie.
     * 
     * <p>Pour chaque joueur, demande son nom et sa stratégie,
     * puis crée l'objet {@link Joueur} correspondant.</p>
     * 
     * @param scanner le scanner pour lire les entrées
     * @param nbJoueurs le nombre de joueurs à créer
     * @return la liste des joueurs créés
     */
    private static List<Joueur> creerJoueurs(Scanner scanner, int nbJoueurs) {
        List<Joueur> joueurs = new ArrayList<>();
        for (int i = 1; i <= nbJoueurs; i++) {
            String nom = demanderNomJoueur(scanner, i);
            StrategieJoueur strat = demanderStrategie(scanner);
            joueurs.add(new Joueur(nom, strat));
        }
        return joueurs;
    }

    /**
     * Affiche le message de bienvenue du jeu.
     * 
     * <p>Affiche le titre du jeu et une brève description.</p>
     */
    private static void presentation() {
        System.out.println("================================");
        System.out.println("        BIENVENUE DANS JEST      ");
        System.out.println("================================");
        System.out.println("Jeu de cartes stratégique");
        System.out.println("3 à 4 joueurs | Humains ou Robots");
        System.out.println();
    }

    /**
     * Affiche le menu principal avec les commandes disponibles.
     * 
     * <p>Commandes : new, load, help, exit.</p>
     */
    private static void afficherMenuPrincipal() {
        System.out.println("Commandes principales :");
        System.out.println(" - new   : Nouvelle partie");
        System.out.println(" - load  : Charger une partie");
        System.out.println(" - help  : Aide");
        System.out.println(" - exit  : Quitter");
    }

    /**
     * Affiche les commandes disponibles pendant une partie.
     * 
     * <p>Commandes : manche, save, status, help, exit.</p>
     */
    private static void afficherCommandesJeu() {
        System.out.println("\nCommandes de jeu :");
        System.out.println(" - manche : jouer une manche");
        System.out.println(" - save   : sauvegarder la partie");
        System.out.println(" - status : afficher l'état");
        System.out.println(" - help   : aide");
        System.out.println(" - exit   : quitter");
    }

    /**
     * Sauvegarde la partie en cours dans un fichier.
     * 
     * <p>Utilise {@link Save#sauvegarder} pour sérialiser la partie
     * dans le fichier 'sauvegarde.ser'.</p>
     * 
     * @param partie la partie à sauvegarder
     */
    private static void sauvegarder(Partie partie) {
        try {
            Save.sauvegarder(partie, "sauvegarde.ser");
            System.out.println("Partie sauvegardée avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    /**
     * Charge une partie depuis un fichier de sauvegarde.
     * 
     * <p>Utilise {@link Load#charger} pour désérialiser la partie
     * depuis le fichier 'sauvegarde.ser'.</p>
     * 
     * @return la partie chargée, ou null en cas d'erreur
     */
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
