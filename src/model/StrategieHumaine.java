package model;

import java.util.List;
import view.GuiInputProvider;

/**
 * Stratégie de jeu pour un joueur humain.
 * 
 * <p>Implémente les méthodes de stratégie en demandant
 * les choix au joueur via InputProvider (console ou GUI).</p>
 * 
 * @author Gwendal Rodrigues
 * @version 03/01/2026
 * @see StrategieJoueur
 */
public class StrategieHumaine implements StrategieJoueur {

    /**
     * Demande au joueur humain de faire une offre.
     * 
     * @param joueur le joueur qui doit faire l'offre
     * @param input le fournisseur d'entrée pour communiquer avec le joueur
     * @return l'offre créée par le joueur
     */
    @Override
    public Offre faireOffre(Joueur joueur, InputProvider input) {
        joueur.afficherMainManche();

        int indexCachee;
        
        // Si c'est une interface graphique, utiliser l'affichage avec images
        if (input instanceof GuiInputProvider) {
            GuiInputProvider guiInput = (GuiInputProvider) input;
            indexCachee = guiInput.demanderChoixCarte(
                joueur.getNom() + ", choisissez la carte FACE CACHÉE :", 
                joueur.getMainCourante()
            );
        } else {
            indexCachee = input.demanderChoixEntier(
                "Choisissez la carte FACE CACHÉE (numéro) : ", 
                0, 
                joueur.getMainCourante().size() - 1
            );
        }
        
        Carte cachee = joueur.getMainCourante().remove(indexCachee);
        Carte visible = joueur.getMainCourante().remove(0);

        // Afficher l'offre créée
        if (input instanceof GuiInputProvider) {
            GuiInputProvider guiInput = (GuiInputProvider) input;
            guiInput.afficherOffreCreee(visible, cachee);
        } else {
            input.afficherMessage("\nOffre créée :\n - Carte face cachée : [cachée]\n - Carte face visible : " + visible);
        }

        return new Offre(cachee, visible, joueur);
    }

    /**
     * Demande au joueur humain de choisir une offre parmi les offres disponibles.
     * 
     * @param offres la liste des offres proposées
     * @param joueur le joueur qui doit choisir
     * @param input le fournisseur d'entrée pour communiquer avec le joueur
     * @return l'offre choisie par le joueur
     */
    @Override
    public Offre choisirOffre(List<Offre> offres, Joueur joueur, InputProvider input) {
        int choix;
        
        // Si c'est une interface graphique, utiliser l'affichage avec images
        if (input instanceof GuiInputProvider) {
            GuiInputProvider guiInput = (GuiInputProvider) input;
            choix = guiInput.demanderChoixOffre(
                joueur.getNom() + ", choisissez une offre :", 
                offres
            );
        } else {
            StringBuilder message = new StringBuilder(joueur.getNom() + ", choisissez une offre :\n");
            for (int i = 0; i < offres.size(); i++) {
                Offre o = offres.get(i);
                message.append("[").append(i).append("] Offre de ").append(o.getJoueur().getNom())
                       .append(" | Carte visible : ").append(o.getVisible()).append("\n");
            }
            input.afficherMessage(message.toString());
            choix = input.demanderChoixEntier("Votre choix : ", 0, offres.size() - 1);
        }

        return offres.get(choix);
    }

    /**
     * Demande au joueur humain de choisir une carte dans une offre.
     * 
     * @param offre l'offre à partir de laquelle choisir
     * @param joueur le joueur qui doit choisir
     * @param input le fournisseur d'entrée pour communiquer avec le joueur
     * @return la carte choisie par le joueur
     */
    @Override
    public Carte choisirCarteOffre(Offre offre, Joueur joueur, InputProvider input) {
        int choix;
        
        // Si c'est une interface graphique, utiliser l'affichage avec images
        if (input instanceof GuiInputProvider) {
            GuiInputProvider guiInput = (GuiInputProvider) input;
            choix = guiInput.demanderChoixCarteOffre(
                joueur.getNom() + ", choisissez une carte :", 
                offre
            );
        } else {
            String message = joueur.getNom() + ", choisissez une carte :\n" +
                    "[0] Carte visible : " + offre.getVisible() + "\n" +
                    "[1] Carte cachée";
            input.afficherMessage(message);
            choix = input.demanderChoixEntier("Votre choix : ", 0, 1);
        }

        Carte cartePrise = offre.prendreCarte(choix == 0);
        
        // Afficher la carte choisie (révélation de la carte cachée si applicable)
        if (input instanceof GuiInputProvider) {
            GuiInputProvider guiInput = (GuiInputProvider) input;
            guiInput.afficherCarteChoisie(cartePrise);
        } else {
            input.afficherMessage(joueur.getNom() + " a choisi : " + cartePrise);
        }
        
        return cartePrise;
    }
}
