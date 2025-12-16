package classe;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de transfert de données (DTO) pour la classe Jest.
 * 
 * <p>Permet la sérialisation d'un Jest en stockant les cartes
 * sous forme de liste de CarteDTO.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 * @see Jest
 */
public class JestDTO implements Serializable {
    /** Identifiant de version pour la sérialisation. */
    @Serial
    private static final long serialVersionUID = 1L;
    
    /** Liste des cartes du Jest sous forme de DTO. */
    public List<CarteDTO> cartes;

    /**
     * Constructeur du JestDTO.
     * 
     * <p>Initialise une liste vide de cartes.</p>
     */
    public JestDTO() {
        this.cartes = new ArrayList<>();
    }
}

