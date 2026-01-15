package view;

import model.InputProvider;
import model.Carte;
import model.Offre;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Implémentation de {@link InputProvider} pour l'interface graphique.
 * 
 * <p>Utilise des {@link JOptionPane} et des dialogues personnalisés
 * pour afficher des informations et demander des choix à l'utilisateur,
 * avec affichage des images des cartes.</p>
 * 
 * @author Gwendal Rodrigues
 * @version 03/01/2026
 * @see InputProvider
 */
public class GuiInputProvider implements InputProvider {
    
    /** La fenêtre parente pour les dialogues. */
    private final JFrame parent;
    
    /**
     * Constructeur du GuiInputProvider.
     * 
     * @param parent la fenêtre parente pour centrer les dialogues
     */
    public GuiInputProvider(JFrame parent) {
        this.parent = parent;
    }
    
    @Override
    public int demanderChoixEntier(String message, int min, int max) {
        while (true) {
            String input = JOptionPane.showInputDialog(
                parent,
                message + "\n(Entre " + min + " et " + max + ")",
                "Choix requis",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (input == null) {
                continue;
            }
            
            try {
                int choix = Integer.parseInt(input.trim());
                if (choix >= min && choix <= max) {
                    return choix;
                }
                JOptionPane.showMessageDialog(parent,
                    "Choix invalide. Doit être entre " + min + " et " + max + ".",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent,
                    "Veuillez entrer un nombre valide.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    @Override
    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(parent, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Affiche les trophées de la partie dans un dialogue.
     * 
     * <p>Affiche chaque carte trophée avec son image et sa condition
     * dans une fenêtre modale.</p>
     * 
     * @param trophees la liste des cartes trophées à afficher
     */
    public void afficherTrophees(java.util.LinkedList<Carte> trophees) {
        if (trophees == null || trophees.isEmpty()) {
            afficherMessage("Aucun trophée tiré.");
            return;
        }
        
        JPanel panel = new JPanel(new GridLayout(1, trophees.size(), 15, 0));
        
        for (int i = 0; i < trophees.size(); i++) {
            Carte trophee = trophees.get(i);
            JPanel cartePanel = new JPanel(new BorderLayout());
            cartePanel.setBorder(BorderFactory.createTitledBorder("Trophée " + (i + 1)));
            
            ImageIcon icon = chargerImageCarte(trophee);
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            
            JLabel conditionLabel = new JLabel(trophee.getTrophee().toString());
            conditionLabel.setHorizontalAlignment(JLabel.CENTER);
            conditionLabel.setFont(new Font("Arial", Font.PLAIN, 9));
            
            cartePanel.add(imageLabel, BorderLayout.CENTER);
            cartePanel.add(conditionLabel, BorderLayout.SOUTH);
            panel.add(cartePanel);
        }
        
        JOptionPane.showMessageDialog(
            parent,
            panel,
            "Trophées de la partie",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Affiche une offre créée avec les images des cartes.
     * 
     * <p>Montre la carte visible et le dos de la carte cachée.</p>
     * 
     * @param carteVisible la carte visible de l'offre
     * @param carteCachee la carte cachée de l'offre (affichée comme dos)
     */
    public void afficherOffreCreee(Carte carteVisible, Carte carteCachee) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        
        // Carte visible
        JPanel visiblePanel = new JPanel(new BorderLayout());
        ImageIcon iconVisible = chargerImageCarte(carteVisible);
        JLabel visibleLabel = new JLabel(iconVisible);
        visibleLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel visibleText = new JLabel("Carte Visible : " + carteVisible);
        visibleText.setHorizontalAlignment(JLabel.CENTER);
        visiblePanel.add(visibleLabel, BorderLayout.CENTER);
        visiblePanel.add(visibleText, BorderLayout.SOUTH);
        
        // Carte cachée
        JPanel cacheePanel = new JPanel(new BorderLayout());
        ImageIcon iconCachee = chargerImageDos();
        JLabel cacheeLabel = new JLabel(iconCachee);
        cacheeLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel cacheeText = new JLabel("Carte Cachée");
        cacheeText.setHorizontalAlignment(JLabel.CENTER);
        cacheePanel.add(cacheeLabel, BorderLayout.CENTER);
        cacheePanel.add(cacheeText, BorderLayout.SOUTH);
        
        panel.add(visiblePanel);
        panel.add(cacheePanel);
        
        JOptionPane.showMessageDialog(
            parent,
            panel,
            "Offre créée",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    
    /**
     * Affiche une carte révélée après que le joueur l'ait choisie.
     * 
     * <p>Montre l'image de la carte sélectionnée avec son nom.</p>
     * 
     * @param carte la carte choisie à afficher
     */
    public void afficherCarteChoisie(Carte carte) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        ImageIcon icon = chargerImageCarte(carte);
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel texteLabel = new JLabel("Vous avez choisi : " + carte);
        texteLabel.setHorizontalAlignment(JLabel.CENTER);
        texteLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(texteLabel, BorderLayout.SOUTH);
        
        JOptionPane.showMessageDialog(
            parent,
            panel,
            "Carte choisie",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Demande à l'utilisateur de choisir une carte avec affichage des images.
     * 
     * <p>Affiche toutes les cartes avec leurs images et des boutons radio
     * pour permettre la sélection.</p>
     * 
     * @param message le message à afficher
     * @param cartes la liste des cartes parmi lesquelles choisir
     * @return l'index de la carte choisie
     */
    public int demanderChoixCarte(String message, List<Carte> cartes) {
        JPanel panel = new JPanel(new GridLayout(1, cartes.size(), 10, 0));
        ButtonGroup group = new ButtonGroup();
        JRadioButton[] buttons = new JRadioButton[cartes.size()];
        
        for (int i = 0; i < cartes.size(); i++) {
            Carte carte = cartes.get(i);
            JPanel cartePanel = new JPanel(new BorderLayout());
            
            // Charger l'image de la carte
            ImageIcon icon = chargerImageCarte(carte);
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            
            buttons[i] = new JRadioButton("[" + i + "]", i == 0);
            buttons[i].setHorizontalAlignment(JRadioButton.CENTER);
            group.add(buttons[i]);
            
            cartePanel.add(imageLabel, BorderLayout.CENTER);
            cartePanel.add(buttons[i], BorderLayout.SOUTH);
            panel.add(cartePanel);
        }
        
        int result = JOptionPane.showConfirmDialog(
            parent,
            panel,
            message,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].isSelected()) {
                    return i;
                }
            }
        }
        
        return 0; // Par défaut
    }
    
    /**
     * Demande à l'utilisateur de choisir une offre avec affichage des images.
     * 
     * <p>Affiche chaque offre avec la carte visible et le dos de la carte cachée,
     * ainsi que le nom du joueur qui a créé l'offre.</p>
     * 
     * @param message le message à afficher
     * @param offres la liste des offres disponibles
     * @return l'index de l'offre choisie
     */
    public int demanderChoixOffre(String message, List<Offre> offres) {
        JPanel panel = new JPanel(new GridLayout(1, offres.size(), 10, 0));
        ButtonGroup group = new ButtonGroup();
        JRadioButton[] buttons = new JRadioButton[offres.size()];
        
        for (int i = 0; i < offres.size(); i++) {
            Offre offre = offres.get(i);
            JPanel offrePanel = new JPanel(new BorderLayout());
            
            JPanel cartesPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            
            // Carte visible
            ImageIcon iconVisible = chargerImageCarte(offre.getVisible());
            JLabel visibleLabel = new JLabel(iconVisible);
            visibleLabel.setHorizontalAlignment(JLabel.CENTER);
            visibleLabel.setBorder(BorderFactory.createTitledBorder("Visible"));
            
            // Carte cachée (dos de carte)
            ImageIcon iconCachee = chargerImageDos();
            JLabel cacheeLabel = new JLabel(iconCachee);
            cacheeLabel.setHorizontalAlignment(JLabel.CENTER);
            cacheeLabel.setBorder(BorderFactory.createTitledBorder("Cachée"));
            
            cartesPanel.add(visibleLabel);
            cartesPanel.add(cacheeLabel);
            
            buttons[i] = new JRadioButton("[" + i + "] " + offre.getJoueur().getNom(), i == 0);
            buttons[i].setHorizontalAlignment(JRadioButton.CENTER);
            group.add(buttons[i]);
            
            offrePanel.add(cartesPanel, BorderLayout.CENTER);
            offrePanel.add(buttons[i], BorderLayout.SOUTH);
            panel.add(offrePanel);
        }
        
        int result = JOptionPane.showConfirmDialog(
            parent,
            panel,
            message,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].isSelected()) {
                    return i;
                }
            }
        }
        
        return 0;
    }
    
    /**
     * Demande de choisir entre carte visible ou cachée dans une offre.
     * 
     * <p>Affiche les deux cartes de l'offre : l'une avec son image,
     * l'autre avec le dos de carte.</p>
     * 
     * @param message le message à afficher
     * @param offre l'offre dans laquelle choisir
     * @return 0 pour la carte visible, 1 pour la carte cachée
     */
    public int demanderChoixCarteOffre(String message, Offre offre) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        ButtonGroup group = new ButtonGroup();
        
        // Carte visible
        JPanel visiblePanel = new JPanel(new BorderLayout());
        ImageIcon iconVisible = chargerImageCarte(offre.getVisible());
        JLabel visibleLabel = new JLabel(iconVisible);
        visibleLabel.setHorizontalAlignment(JLabel.CENTER);
        JRadioButton btnVisible = new JRadioButton("[0] Visible", true);
        btnVisible.setHorizontalAlignment(JRadioButton.CENTER);
        group.add(btnVisible);
        visiblePanel.add(visibleLabel, BorderLayout.CENTER);
        visiblePanel.add(btnVisible, BorderLayout.SOUTH);
        
        // Carte cachée
        JPanel cacheePanel = new JPanel(new BorderLayout());
        ImageIcon iconCachee = chargerImageDos();
        JLabel cacheeLabel = new JLabel(iconCachee);
        cacheeLabel.setHorizontalAlignment(JLabel.CENTER);
        JRadioButton btnCachee = new JRadioButton("[1] Cachée");
        btnCachee.setHorizontalAlignment(JRadioButton.CENTER);
        group.add(btnCachee);
        cacheePanel.add(cacheeLabel, BorderLayout.CENTER);
        cacheePanel.add(btnCachee, BorderLayout.SOUTH);
        
        panel.add(visiblePanel);
        panel.add(cacheePanel);
        
        int result = JOptionPane.showConfirmDialog(
            parent,
            panel,
            message,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            return btnVisible.isSelected() ? 0 : 1;
        }
        
        return 0;
    }
    
    /**
     * Charge l'image d'une carte depuis son chemin.
     * 
     * <p>Redimensionne l'image à 100x140 pixels. Si le chargement échoue,
     * crée une image par défaut avec le texte de la carte.</p>
     * 
     * @param carte la carte dont charger l'image
     * @return l'icône de l'image redimensionnée
     */
    private ImageIcon chargerImageCarte(Carte carte) {
        try {
            String chemin = carte.getImg();
            // Corriger le chemin si nécessaire
            if (chemin.startsWith("./Jest-game/")) {
                chemin = chemin.substring("./Jest-game/".length());
            }
            
            ImageIcon icon = new ImageIcon(chemin);
            
            // Vérifier si l'image a été chargée
            if (icon.getIconWidth() <= 0) {
                System.err.println("Erreur chargement image: " + chemin);
                return creerImageParDefaut(carte.toString());
            }
            
            Image img = icon.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Exception lors du chargement: " + e.getMessage());
            return creerImageParDefaut(carte.toString());
        }
    }
    
    /**
     * Charge l'image du dos de carte.
     * 
     * <p>Essaie plusieurs chemins possibles. Si aucune image n'est trouvée,
     * crée un dos de carte par défaut de style classique.</p>
     * 
     * @return l'icône du dos de carte
     */
    private ImageIcon chargerImageDos() {
        // Essayer plusieurs chemins possibles
        String[] cheminsPossibles = {
            "image/cartes/Dos.png",
            "image/cartes/dos.png",
            "./image/cartes/Dos.png",
            "./image/cartes/dos.png"
        };
        
        for (String chemin : cheminsPossibles) {
            try {
                ImageIcon icon = new ImageIcon(chemin);
                
                if (icon.getIconWidth() > 0) {
                    Image img = icon.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
                    return new ImageIcon(img);
                }
            } catch (Exception e) {
                // Continuer avec le chemin suivant
            }
        }
        
        // Si aucune image n'est trouvée, créer une image par défaut stylée
        return creerDosParDefaut();
    }
    
    /**
     * Crée une image de dos de carte par défaut de style classique.
     * 
     * <p>Génère une image avec un fond bleu, une bordure dorée,
     * des motifs décoratifs et le texte "Dos" au centre.</p>
     * 
     * @return l'icône du dos de carte généré
     */
    private ImageIcon creerDosParDefaut() {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(100, 140, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        
        // Fond bleu
        g2d.setColor(new Color(0, 51, 102));
        g2d.fillRect(0, 0, 100, 140);
        
        // Bordure dorée
        g2d.setColor(new Color(204, 153, 0));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(1, 1, 98, 138);
        
        // Motifs décoratifs
        g2d.setColor(new Color(255, 215, 0));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                int x = 15 + i * 18;
                int y = 15 + j * 18;
                g2d.fillOval(x, y, 8, 8);
            }
        }
        
        // Texte "Dos" au centre
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.WHITE);
        FontMetrics fm = g2d.getFontMetrics();
        String texte = "Dos";
        int x = (100 - fm.stringWidth(texte)) / 2;
        int y = ((140 - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(texte, x, y);
        
        g2d.dispose();
        return new ImageIcon(img);
    }
    
    /**
     * Crée une image par défaut avec le texte de la carte.
     * 
     * <p>Génère une image blanche avec bordure noire contenant
     * le texte de la carte découpé sur plusieurs lignes.</p>
     * 
     * @param texte le texte à afficher sur l'image
     * @return l'icône de l'image générée
     */
    private ImageIcon creerImageParDefaut(String texte) {
        // Créer une image simple avec le texte
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(100, 140, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 100, 140);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, 99, 139);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        
        // Découper le texte en plusieurs lignes si nécessaire
        String[] mots = texte.split(" ");
        int y = 60;
        for (String mot : mots) {
            FontMetrics fm = g2d.getFontMetrics();
            int x = (100 - fm.stringWidth(mot)) / 2;
            g2d.drawString(mot, x, y);
            y += 15;
        }
        
        g2d.dispose();
        return new ImageIcon(img);
    }
}
