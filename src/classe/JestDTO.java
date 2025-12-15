package classe;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class JestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static List<CarteDTO> cartes;
}

