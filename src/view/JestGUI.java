package view;

import model.*;
import service.GameManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JestGUI {

    private final GameManager gameManager;

    private JFrame frame;
    private JButton btnNew;
    private JButton btnLoad;
    private JButton btnSave;
    private JButton btnStatus;
    private JButton btnManche;
    private JButton btnExit;

    private JTextArea output;

    public JestGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        initGUI();
        refreshUI();
    }

    private void initGUI() {
        frame = new JFrame("Jeu de JEST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 420);
        frame.setLocationRelativeTo(null);

        URL url = getClass().getResource("/image/logo.png");
        if (url != null) {
            frame.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        }

        Image icon = Toolkit.getDefaultToolkit().getImage(url);
        frame.setIconImage(icon);

        // Boutons
        btnNew = new JButton("Nouvelle partie");
        btnLoad = new JButton("Charger");
        btnSave = new JButton("Sauvegarder");
        btnStatus = new JButton("État");
        btnManche = new JButton("Jouer une manche");
        btnExit = new JButton("Quitter");

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnNew);
        top.add(btnLoad);
        top.add(btnSave);
        top.add(btnStatus);
        top.add(btnManche);
        top.add(btnExit);

        // Zone sortie
        output = new JTextArea(18, 60);
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(output);

        frame.setLayout(new BorderLayout(8, 8));
        frame.add(top, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);

        addListeners();

        frame.setVisible(true);
    }

    private void addListeners() {

        btnNew.addActionListener(e -> {
            synchronized (gameManager) {
                if (gameManager.hasPartie()) {
                    showInfo("Une partie est déjà en cours. (Utilise Save/Status/Manche ou relance l'app)");
                    return;
                }

                Integer nb = demanderNombreJoueurs();
                if (nb == null) return;

                List<Joueur> joueurs = new ArrayList<>();
                for (int i = 1; i <= nb; i++) {
                    String nom = demanderNomJoueur(i);
                    if (nom == null) return;

                    StrategieJoueur strat = demanderStrategie(i);
                    if (strat == null) return;

                    joueurs.add(new Joueur(nom, strat));
                }

                gameManager.nouvellePartie(joueurs);
                showInfo("Nouvelle partie créée (" + nb + " joueurs).");
                refreshUI();
            }
        });

        btnLoad.addActionListener(e -> {
            synchronized (gameManager) {
                Partie p = charger();
                if (p != null) {
                    gameManager.chargerPartie(p);
                    showInfo("Partie chargée.");
                }
                refreshUI();
            }
        });

        btnSave.addActionListener(e -> {
            synchronized (gameManager) {
                if (!gameManager.hasPartie()) {
                    showInfo("Aucune partie à sauvegarder.");
                    return;
                }
                sauvegarder(gameManager.getPartie());
                showInfo("Sauvegarde effectuée.");
                refreshUI();
            }
        });

        btnStatus.addActionListener(e -> {
            synchronized (gameManager) {
                if (!gameManager.hasPartie()) {
                    showInfo("Aucune partie en cours.");
                    return;
                }

                showInfo("Affichage de l'état dans la console (ou implémente un toString/DTO).");
                gameManager.getPartie().afficherEtat();
            }
        });

        btnManche.addActionListener(e -> {
            synchronized (gameManager) {
                if (!gameManager.hasPartie()) {
                    showInfo("Aucune partie en cours. Clique sur 'Nouvelle partie'.");
                    return;
                }

                // ⚠️ Problème : jouerManche(scanner) demande des entrées console.
                // En GUI, on ne veut pas lire au clavier console.
                // => Solution minimale : empêcher ce bouton si jouerManche dépend du Scanner.
                // => Solution propre : refactorer Partie pour accepter des choix fournis par contrôleur GUI.
                showInfo("Impossible en l'état : jouerManche(Scanner) dépend de la console.\n"
                        + "Solution : refactorer pour séparer logique et entrée utilisateur.");
            }
        });

        btnExit.addActionListener(e -> {
            frame.dispose();
            System.exit(0);
        });
    }

    private void refreshUI() {
        boolean has = gameManager.hasPartie();
        btnSave.setEnabled(has);
        btnStatus.setEnabled(has);
        // btnManche : désactivé tant que Partie.jouerManche dépend du Scanner
        btnManche.setEnabled(false);
    }

    /* ========= Dialogs ========= */

    private Integer demanderNombreJoueurs() {
        Object[] options = {"3", "4"};
        int choix = JOptionPane.showOptionDialog(
                frame,
                "Choisissez le nombre de joueurs :",
                "Nombre de joueurs",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choix == JOptionPane.CLOSED_OPTION) return null;
        return (choix == 0) ? 3 : 4;
    }

    private String demanderNomJoueur(int numeroJoueur) {
        while (true) {
            String nom = JOptionPane.showInputDialog(
                    frame,
                    "Entrez le nom du joueur " + numeroJoueur + " :",
                    "Nom du joueur",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (nom == null) return null; // Cancel

            nom = nom.trim();
            if (nom.isEmpty() || nom.matches("\\d+") || nom.length() < 2) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Le nom doit contenir au moins 2 caractères et ne pas être uniquement des chiffres.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }
            return nom;
        }
    }

    private StrategieJoueur demanderStrategie(int numeroJoueur) {
        Object[] options = {"Humain", "Robot (aléatoire)"};
        int choix = JOptionPane.showOptionDialog(
                frame,
                "Choisissez le type du joueur " + numeroJoueur + " :",
                "Type de joueur",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choix == JOptionPane.CLOSED_OPTION) return null;

        if (choix == 0) return new StrategieHumaine();
        return new StrategieRobotAleatoire();
    }

    /* ========= Save/Load ========= */

    private void sauvegarder(Partie partie) {
        try {
            Save.sauvegarder(partie, "sauvegarde.ser");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Erreur sauvegarde : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Partie charger() {
        try {
            return Load.charger("sauvegarde.ser");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Erreur chargement : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /* ========= Util ========= */

    private void showInfo(String msg) {
        output.append(msg + "\n");
        output.setCaretPosition(output.getDocument().getLength());
    }
}
