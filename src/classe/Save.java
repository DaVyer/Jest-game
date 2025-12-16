package classe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Classe utilitaire pour sauvegarder une partie en cours.
 * 
 * <p>Permet de sérialiser une partie dans un fichier
 * en utilisant les objets DTO.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 */
public class Save {
    /**
     * Sauvegarde une partie dans un fichier.
     * 
     * <p>Convertit la partie en PartieDTO puis la sérialise
     * dans le fichier spécifié.</p>
     * 
     * @param partie la partie à sauvegarder
     * @param fichier le chemin du fichier de sauvegarde
     * @throws IOException si une erreur d'entrée/sortie survient
     */
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
