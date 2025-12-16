package classe;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Classe de transfert de données (DTO) pour la classe Partie.
 * 
 * <p>Permet la sérialisation de l'état complet d'une partie
 * pour la sauvegarde et le chargement.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 * @see Partie
 */
public class PartieDTO implements Serializable {
    /** Identifiant de version pour la sérialisation. */
    @Serial
    private static final long serialVersionUID = 1L;

    /** Liste des joueurs de la partie. */
    public List<JoueurDTO> joueurs;
    
    /** Liste des cartes de la pioche. */
    public List<CarteDTO> pioche;
    
    /** Liste des cartes trophées. */
    public List<CarteDTO> trophees;
    
    /** Le numéro de la manche courante. */
    public int numeroManche;
    
    /** Indique si la partie est terminée. */
    public boolean partieTerminee;
}

