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

    public static int demanderNombreJoueurs(Scanner scanner){
        int nombreJoueurs = 0;
        System.out.println("Combien de joueurs voulez vous dans votre partie (3 à 4) ? ");
        
        while (nombreJoueurs != 3 && nombreJoueurs != 4) {
            if (scanner.hasNextInt()) {
                nombreJoueurs = scanner.nextInt();
                scanner.nextLine(); // consommer le retour à la ligne
                
                if (nombreJoueurs != 3 && nombreJoueurs != 4) { 
                    System.out.println("Veuillez rentrer un nombre de joueurs valant 3 ou 4.");
                }
            }
        }
        
        System.out.println("\n\n===============\n");
        return nombreJoueurs;
    }

    public static void main(String[] arg){
        Scanner scanner = new Scanner(System.in);
        AtomicBoolean enJeu = new AtomicBoolean(true);

        // Thread pour lire l'input sans bloquer la boucle
       Thread inputThread = new Thread(() -> {
            while (enJeu.get()) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("exit")) {
                        enJeu.set(false);
                    }
                    if (input.equalsIgnoreCase("start")) {
                        System.out.println("\n===============\n");
                        
                        Partie partie = new Partie();

                        int nombreJoueurs = demanderNombreJoueurs(scanner);
                        for(int i = 1; i<=nombreJoueurs; i++){
                            
                            String nom = demanderNomJoueur(scanner, i);
                            Joueur joueur = new Joueur(nom);
                            partie.ajouterJoueurs(joueur);

                        }
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
