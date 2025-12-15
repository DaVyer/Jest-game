package classe;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PartieDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public List<JoueurDTO> joueurs;
    public List<CarteDTO> pioche;
    public List<CarteDTO> trophees;
    public int numeroManche;
    public boolean partieTerminee;
}

