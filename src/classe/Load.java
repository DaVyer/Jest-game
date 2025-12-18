package classe;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Classe utilitaire pour charger une partie sauvegardée.
 * 
 * <p>Permet de désérialiser une partie depuis un fichier
 * en utilisant les objets DTO.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 */
public class Load {
    /**
     * Charge une partie depuis un fichier.
     * 
     * <p>Désérialise le PartieDTO depuis le fichier spécifié
     * et le convertit en objet Partie.</p>
     * 
     * @param fichier le chemin du fichier à charger
     * @return la partie chargée
     * @throws IOException si une erreur d'entrée/sortie survient
     * @throws ClassNotFoundException si la classe PartieDTO n'est pas trouvée
     */
    public static Partie charger(String fichier)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(fichier))) {

            PartieDTO dto = (PartieDTO) ois.readObject();

            return DTOMapper.partieFromDTO(dto);
        }
    }
}
