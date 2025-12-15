package classe;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Load {
    public static Partie charger(String fichier)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(fichier))) {

            PartieDTO dto = (PartieDTO) ois.readObject();

            return DTOMapper.partieFromDTO(dto);
        }
    }
}
