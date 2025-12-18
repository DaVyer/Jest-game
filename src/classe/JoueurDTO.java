package classe;

import java.io.Serializable;

/**
 * Classe de transfert de données (DTO) pour la classe Joueur.
 * 
 * <p>Permet la sérialisation des informations d'un joueur :
 * son nom, son type de stratégie et son Jest.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 * @see Joueur
 */
public class JoueurDTO implements Serializable {
    /** Le nom du joueur. */
    public String nom;
    
    /** Le type de stratégie du joueur (nom de la classe). */
    public String typeStrategie;
    
    /** Le Jest du joueur sous forme de DTO. */
    public JestDTO jest;
}

