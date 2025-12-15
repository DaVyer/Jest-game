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
            System.out.println("DEBUG DTO APRÈS READ");
            for (JoueurDTO j : dto.joueurs) {
                int n = (j.jest == null || j.jest.cartes == null)
                        ? -1
                        : j.jest.cartes.size();
                System.out.println(" - " + j.nom + " jestDTO=" + n);
            }
            return DTOMapper.partieFromDTO(dto);
        }
    }
}
