import controller.CliController;
import service.GameManager;
import view.JestGUI;

import javax.swing.*;
import java.util.Scanner;

public class Main {

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
