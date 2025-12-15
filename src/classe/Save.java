package classe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Save {
    public static void sauvegarder(Partie partie, String fichier) throws IOException {

        PartieDTO dto = DTOMapper.partieToDTO(partie);
        System.out.println("DEBUG DTO AVANT WRITE");
        for (JoueurDTO j : dto.joueurs) {
            int n = (j.jest == null || j.jest.cartes == null)
                    ? -1
                    : j.jest.cartes.size();
            System.out.println(" - " + j.nom + " jestDTO=" + n);
        }
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(fichier))) {
            oos.writeObject(dto);
        }
    }
}
