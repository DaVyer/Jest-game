package classe;

import java.io.Serial;
import java.io.Serializable;

public class CarteDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ValeurCarte valeur;
    public CouleurCarte couleur;
    public ConditionTrophee condition;

    public static CarteDTO toDTO(Carte c) {
        CarteDTO dto = new CarteDTO();
        dto.valeur = c.getValeur();
        dto.couleur = c.getCouleur();
        dto.condition = c.getTrophee();
        return dto;
    }

    public static Carte carteFromDTO(CarteDTO dto) {
        return new Carte(dto.valeur, dto.couleur, dto.condition);
    }
}
