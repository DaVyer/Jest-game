import classe.Partie;
import classe.Pioche;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

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
                    if (input.equalsIgnoreCase("start qsfkdj")) {
                        System.out.println("===============");
                        Partie partie = new Partie();
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
