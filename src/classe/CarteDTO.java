package classe;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe de transfert de données (DTO) pour la classe Carte.
 * 
 * <p>Permet la sérialisation et la désérialisation des objets Carte
 * pour la sauvegarde et le chargement de parties.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 * @see Carte
 */
public class CarteDTO implements Serializable {
    /** Identifiant de version pour la sérialisation. */
    @Serial
    private static final long serialVersionUID = 1L;

    /** La valeur de la carte. */
    public ValeurCarte valeur;
    
    /** La couleur de la carte. */
    public CouleurCarte couleur;
    
    /** La condition de trophée associée à la carte. */
    public ConditionTrophee condition;

    /**
     * Convertit un objet Carte en CarteDTO.
     * 
     * @param c la carte à convertir
     * @return le DTO correspondant à la carte
     */
    public static CarteDTO toDTO(Carte c) {
        CarteDTO dto = new CarteDTO();
        dto.valeur = c.getValeur();
        dto.couleur = c.getCouleur();
        dto.condition = c.getTrophee();
        return dto;
    }

    /**
     * Convertit un CarteDTO en objet Carte.
     * 
     * @param dto le DTO à convertir
     * @return la carte correspondant au DTO
     */
    public static Carte carteFromDTO(CarteDTO dto) {
        return new Carte(dto.valeur, dto.couleur, dto.condition);
    }
}
