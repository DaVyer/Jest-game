package main;

import controller.CliController;
import service.GameManager;
import view.JestGUI;

import javax.swing.*;
import java.util.Scanner;

/**
 * Classe principale du jeu Jest.
 * 
 * <p>Lance l'application en démarrant simultanément deux interfaces :</p>
 * <ul>
 * <li>Une interface en ligne de commande (CLI) via {@link CliController}</li>
 * <li>Une interface graphique (GUI) via {@link JestGUI}</li>
 * </ul>
 * <p>Les deux interfaces partagent la même instance de {@link GameManager}
 * pour permettre une synchronisation de l'état du jeu.</p>
 * 
 * @author Gwendal Rodrigues, Tristan Crémonat
 * @version 03/01/2026
 */
public class Main {

    /**
     * Point d'entrée de l'application.
     * 
     * <p>Initialise le gestionnaire de jeu et lance les deux interfaces
     * (console et graphique) dans des threads séparés. Un hook d'arrêt
     * est également enregistré pour fermer proprement le Scanner à la fin
     * de l'exécution.</p>
     * 
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {

        GameManager gameManager = new GameManager();

        Scanner scanner = new Scanner(System.in);
        Thread cliThread = new Thread(new CliController(gameManager, scanner), "CLI-Thread");
        cliThread.start();

        SwingUtilities.invokeLater(() -> new JestGUI(gameManager));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                scanner.close();
            } catch (Exception ignored) {}
        }));
    }
}
