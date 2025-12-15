package classe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Save {
    public static void sauvegarder(Partie partie, String fichier) throws IOException {

        PartieDTO dto = DTOMapper.partieToDTO(partie);

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(fichier))) {
            oos.writeObject(dto);
        }
    }
}
