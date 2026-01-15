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
    private JButton btnResultats;
    private JButton btnExit;

    private JTextArea output;
    private JPanel trophiesPanel;

    public JestGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        initGUI();
        refreshUI();
    }

    private void initGUI() {
        frame = new JFrame("Jeu de JEST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(null);

        URL url = getClass().getResource("/image/logo.png");
        Image icon = null;
        if (url != null) {
            icon = Toolkit.getDefaultToolkit().getImage(url);
        } else {
            // Fallback si la ressource n'est pas sur le classpath (ex: lancement via `-cp src`)
            icon = new ImageIcon("image/logo.png").getImage();
        }
        if (icon != null) {
            frame.setIconImage(icon);
        }

        // Boutons
        btnNew = new JButton("Nouvelle partie");
        btnLoad = new JButton("Charger");
        btnSave = new JButton("Sauvegarder");
        btnStatus = new JButton("État");
        btnManche = new JButton("Jouer une manche");
        btnResultats = new JButton("Résultats");
        btnExit = new JButton("Quitter");

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnNew);
        top.add(btnLoad);
        top.add(btnSave);
        top.add(btnStatus);
        top.add(btnManche);
        top.add(btnResultats);
        top.add(btnExit);

        // Zone sortie
        output = new JTextArea(18, 60);
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(output);

        // Panel des trophées
        trophiesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        trophiesPanel.setBorder(BorderFactory.createTitledBorder("Trophées de la partie"));
        trophiesPanel.setBackground(new Color(240, 240, 240));

        frame.setLayout(new BorderLayout(8, 8));
        frame.add(top, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(trophiesPanel, BorderLayout.SOUTH);

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
                
                boolean modeVariante = demanderModeJeu();
                boolean extensionActive = demanderExtension();

                gameManager.nouvellePartie(joueurs);
                Partie p = gameManager.getPartie();
                p.setModeVariante(modeVariante);
                p.setExtensionActive(extensionActive);
                p.initialiserPiocheEtTrophees();
                showInfo("Nouvelle partie créée (" + nb + " joueurs).");
                
                // Afficher les trophées dans une fenêtre de dialogue
                GuiInputProvider guiInput = new GuiInputProvider(frame);
                guiInput.afficherTrophees(gameManager.getPartie().getTrophees());
                
                afficherTrophees();
                refreshUI();
            }
        });

        btnLoad.addActionListener(e -> {
            synchronized (gameManager) {
                Partie p = charger();
                if (p != null) {
                    gameManager.chargerPartie(p);
                    showInfo("Partie chargée.");
                    afficherTrophees();
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

                // Lancer la manche dans un thread séparé pour ne pas bloquer l'UI
                new Thread(() -> {
                    try {
                        GuiInputProvider guiInput = new GuiInputProvider(frame);
                        synchronized (gameManager) {
                            gameManager.getPartie().jouerManche(guiInput);
                        }
                        SwingUtilities.invokeLater(() -> {
                            showInfo("Manche terminée !");
                            // Si la partie est finie, afficher automatiquement les résultats
                            if (gameManager.hasPartie() && gameManager.getPartie().isPartieTerminee()) {
                                afficherResultatsFinaux();
                            }
                        });
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> {
                            showInfo("Erreur : " + ex.getMessage());
                            ex.printStackTrace();
                        });
                    }
                }).start();
            }
        });

        btnResultats.addActionListener(e -> {
            synchronized (gameManager) {
                if (!gameManager.hasPartie()) {
                    showInfo("Aucune partie en cours.");
                    return;
                }
                afficherResultatsFinaux();
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
        btnManche.setEnabled(has);
        btnResultats.setEnabled(has);
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

    /**
     * Affiche les trophées de la partie en cours dans le panel.
     */
    private void afficherTrophees() {
        trophiesPanel.removeAll();
        
        if (!gameManager.hasPartie()) {
            trophiesPanel.add(new JLabel("Aucune partie en cours"));
            trophiesPanel.revalidate();
            trophiesPanel.repaint();
            return;
        }
        
        Partie partie = gameManager.getPartie();
        java.util.LinkedList<Carte> trophees = partie.getTrophees();
        
        if (trophees == null || trophees.isEmpty()) {
            trophiesPanel.add(new JLabel("Aucun trophée"));
            trophiesPanel.revalidate();
            trophiesPanel.repaint();
            return;
        }
        
        // Afficher les trophées avec leurs images
        for (int i = 0; i < trophees.size(); i++) {
            Carte trophee = trophees.get(i);
            JPanel cartePanel = new JPanel(new BorderLayout());
            cartePanel.setBorder(BorderFactory.createTitledBorder("Trophée " + (i + 1)));
            
            // Charger l'image
            ImageIcon icon = chargerImageCarte(trophee);
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            
            // Afficher la condition du trophée
            JLabel conditionLabel = new JLabel(trophee.getTrophee().toString());
            conditionLabel.setHorizontalAlignment(JLabel.CENTER);
            conditionLabel.setFont(new Font("Arial", Font.PLAIN, 9));
            
            cartePanel.add(imageLabel, BorderLayout.CENTER);
            cartePanel.add(conditionLabel, BorderLayout.SOUTH);
            trophiesPanel.add(cartePanel);
        }
        
        trophiesPanel.revalidate();
        trophiesPanel.repaint();
    }
    
    /**
     * Charge l'image d'une carte.
     */
    private ImageIcon chargerImageCarte(Carte carte) {
        try {
            String chemin = carte.getImg();
            if (chemin.startsWith("./Jest-game/")) {
                chemin = chemin.substring("./Jest-game/".length());
            }
            
            ImageIcon icon = new ImageIcon(chemin);
            if (icon.getIconWidth() <= 0) {
                return creerImageParDefaut(carte.toString());
            }
            
            Image img = icon.getImage().getScaledInstance(80, 110, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return creerImageParDefaut(carte.toString());
        }
    }
    
    /**
     * Affiche une fenêtre avec les résultats finaux de la partie.
     */
    public void afficherResultatsFinaux() {
        if (!gameManager.hasPartie()) {
            JOptionPane.showMessageDialog(frame, 
                "Aucune partie en cours.", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Partie partie = gameManager.getPartie();
        
        // Calculer les scores
        java.util.Map<Joueur, Integer> scores = new java.util.LinkedHashMap<>();
        for (Joueur j : partie.getJoueurs()) {
            ScoreVisitor visitor = new ScoreVisitor(partie.isModeVariante());
            j.accept(visitor);
            scores.put(j, visitor.getScore());
        }
        
        // Trier par score décroissant
        java.util.List<java.util.Map.Entry<Joueur, Integer>> classement = 
            new java.util.ArrayList<>(scores.entrySet());
        classement.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        
        // Créer la fenêtre des résultats
        JFrame resultFrame = new JFrame("Résultats finaux - Jest");
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setSize(500, 400);
        resultFrame.setLocationRelativeTo(frame);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Titre
        JLabel titleLabel = new JLabel("RÉSULTATS DE LA PARTIE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Classement
        JPanel classementPanel = new JPanel();
        classementPanel.setLayout(new BoxLayout(classementPanel, BoxLayout.Y_AXIS));
        classementPanel.setBorder(BorderFactory.createTitledBorder("Classement"));
        
        int position = 1;
        for (java.util.Map.Entry<Joueur, Integer> entry : classement) {
            Joueur joueur = entry.getKey();
            int score = entry.getValue();
            
            JPanel joueurPanel = new JPanel(new BorderLayout());
            joueurPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            joueurPanel.setBackground(position == 1 ? new Color(255, 215, 0) : Color.WHITE);
            
            JLabel posLabel = new JLabel((position) + ".");
            posLabel.setFont(new Font("Arial", Font.BOLD, 14));
            posLabel.setPreferredSize(new Dimension(30, 30));
            
            JLabel nomLabel = new JLabel(joueur.getNom());
            nomLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            
            JLabel scoreLabel = new JLabel(score + " pts");
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
            scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
            
            joueurPanel.add(posLabel, BorderLayout.WEST);
            joueurPanel.add(nomLabel, BorderLayout.CENTER);
            joueurPanel.add(scoreLabel, BorderLayout.EAST);
            
            classementPanel.add(joueurPanel);
            position++;
        }
        
        JScrollPane scrollClassement = new JScrollPane(classementPanel);
        mainPanel.add(scrollClassement, BorderLayout.CENTER);
        
        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnFermer = new JButton("Fermer");
        btnFermer.addActionListener(e -> resultFrame.dispose());
        buttonPanel.add(btnFermer);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        resultFrame.add(mainPanel);
        resultFrame.setVisible(true);
    }
    
    /**
     * Crée une image par défaut avec le texte.
     */
    private ImageIcon creerImageParDefaut(String texte) {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(80, 110, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 80, 110);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, 79, 109);
        g2d.setFont(new Font("Arial", Font.PLAIN, 8));
        
        String[] mots = texte.split(" ");
        int y = 50;
        for (String mot : mots) {
            FontMetrics fm = g2d.getFontMetrics();
            int x = (80 - fm.stringWidth(mot)) / 2;
            g2d.drawString(mot, x, y);
            y += 12;
        }
        
        g2d.dispose();
        return new ImageIcon(img);
    }
    
    /**
     * Demande à l'utilisateur de choisir le mode de jeu.
     * 
     * @return true pour la variante, false pour les règles de base
     */
    private boolean demanderModeJeu() {
        String[] options = {"Règles de base", "Variante"};
        
        String message = "<html><body style='width: 400px;'>" +
                "<h3>Choisissez le mode de jeu :</h3>" +
                "<br><b>1. Règles de base :</b>" +
                "<ul>" +
                "<li>Joker + 1-3 Cœurs : les Cœurs sont <b>NÉGATIFS</b>, Joker = 0</li>" +
                "<li>Joker + 4 Cœurs : les Cœurs sont <b>POSITIFS</b>, Joker = 0</li>" +
                "</ul>" +
                "<br><b>2. Variante :</b>" +
                "<ul>" +
                "<li>Joker + 3+ Cœurs : les Cœurs valent le <b>DOUBLE</b></li>" +
                "<li>Joker + moins de 3 Cœurs : les Cœurs sont <b>NÉGATIFS</b></li>" +
                "</ul>" +
                "</body></html>";
        
        int choix = JOptionPane.showOptionDialog(
                frame,
                message,
                "Mode de jeu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        
        return choix == 1; // true si variante, false si règles de base
    }

    /**
     * Demande si l'extension Miroir est activée.
     * 
     * @return true si l'extension est activée, false sinon
     */
    private boolean demanderExtension() {
        String[] options = {"Oui", "Non"};

        String message = "<html><body style='width: 420px;'>" +
                "<h3>Extension Miroir</h3>" +
                "<p>Le <b>Miroir</b> copie la valeur (avec le signe) de ta carte la plus forte.</p>" +
                "<ul>" +
                "<li>Meilleure carte = +5 &rarr; Miroir = +5</li>" +
                "<li>Meilleure carte = -4 &rarr; Miroir = -4</li>" +
                "</ul>" +
                "<p>Activer l'extension ?</p>" +
                "</body></html>";

        int choix = JOptionPane.showOptionDialog(
                frame,
                message,
                "Extension Miroir",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
        );

        return choix == 0; // Oui
    }
}
