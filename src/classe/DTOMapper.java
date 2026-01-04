package classe;

import java.util.ArrayList;

/**
 * Classe utilitaire pour la conversion entre objets métier et objets DTO.
 * 
 * <p>Fournit des méthodes statiques pour convertir les entités du jeu
 * (Carte, Joueur, Partie) en leurs équivalents DTO et vice-versa,
 * facilitant la sérialisation et la désérialisation.</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 */
public class DTOMapper {

    /**
     * Convertit un CarteDTO en objet Carte.
     * 
     * @param dto le DTO de carte à convertir
     * @return une nouvelle instance de Carte
     */
    public static Carte carteFromDTO(CarteDTO dto) {
        return new Carte(dto.valeur, dto.couleur, dto.condition);
    }

    /**
     * Convertit une Carte en CarteDTO.
     * 
     * @param c la carte à convertir
     * @return un nouveau DTO de carte
     */
    public static CarteDTO carteToDTO(Carte c) {
        CarteDTO dto = new CarteDTO();
        dto.valeur = c.getValeur();
        dto.couleur = c.getCouleur();
        dto.condition = c.getTrophee();
        return dto;
    }

    /**
     * Convertit un JoueurDTO en objet Joueur.
     * 
     * <p>Crée un joueur avec sa stratégie appropriée (humaine ou robot)
     * et reconstitue son Jest avec toutes les cartes sauvegardées.</p>
     * 
     * @param dto le DTO de joueur à convertir
     * @return une nouvelle instance de Joueur
     */
    public static Joueur joueurFromDTO(JoueurDTO dto) {

        StrategieJoueur strategie = dto.typeStrategie.equals("StrategieRobotAleatoire")
                                    ? new StrategieRobotAleatoire()
                                    : new StrategieHumaine();

        Joueur j = new Joueur(dto.nom, strategie);

        if (dto.jest == null) {
            dto.jest = new JestDTO();
        }
        if (dto.jest.cartes == null) {
            dto.jest.cartes = new ArrayList<>();
        }

        for (CarteDTO cDTO : dto.jest.cartes) {
            j.ajouterAuJest(carteFromDTO(cDTO));
        }

        return j;
    }

    /**
     * Convertit un Joueur en JoueurDTO.
     * 
     * <p>Sauvegarde toutes les informations du joueur y compris
     * son nom, son type de stratégie et toutes les cartes de son Jest.</p>
     * 
     * @param j le joueur à convertir
     * @return un nouveau DTO de joueur
     */
    public static JoueurDTO joueurToDTO(Joueur j) {

        JoueurDTO dto = new JoueurDTO();
        dto.nom = j.getNom();
        dto.typeStrategie = j.getStrategie().getClass().getSimpleName();

        JestDTO jestDTO = new JestDTO();

        for (int i = 0; i < j.getJest().taille(); i++) {
            jestDTO.cartes.add(carteToDTO(j.getJest().getCarte(i)));
        }

        dto.jest = jestDTO;


        return dto;
    }

    /**
     * Convertit une Partie en PartieDTO.
     * 
     * <p>Sauvegarde l'état complet de la partie : joueurs, pioche,
     * trophées, numéro de manche et statut de fin de partie.</p>
     * 
     * @param partie la partie à convertir
     * @return un nouveau DTO de partie
     */
    public static PartieDTO partieToDTO(Partie partie) {

        PartieDTO dto = new PartieDTO();

        dto.joueurs = new ArrayList<>();
        for (Joueur j : partie.getJoueurs()) {
            dto.joueurs.add(joueurToDTO(j));
        }

        dto.pioche = new ArrayList<>();
        for (Carte c : partie.getPioche().getPioche()) {
            dto.pioche.add(carteToDTO(c));
        }

        dto.trophees = new ArrayList<>();
        for (Carte c : partie.getTrophees()) {
            dto.trophees.add(carteToDTO(c));
        }

        dto.numeroManche = partie.getNumeroManche();
        dto.partieTerminee = partie.isPartieTerminee();

        return dto;
    }

    /**
     * Convertit un PartieDTO en objet Partie.
     * 
     * <p>Reconstitue une partie complète depuis ses données sérialisées,
     * en restaurant tous les joueurs, la pioche, les trophées et l'état de la partie.</p>
     * 
     * @param dto le DTO de partie à convertir
     * @return une nouvelle instance de Partie
     */
    public static Partie partieFromDTO(PartieDTO dto) {

        Partie partie = new Partie(false);

        for (JoueurDTO jDTO : dto.joueurs) {
            partie.ajouterJoueurs(joueurFromDTO(jDTO));
        }

        partie.getPioche().chargerDepuisDTO(dto.pioche);

        for (CarteDTO cDTO : dto.trophees) {
            partie.getTrophees().add(carteFromDTO(cDTO));
        }

        partie.setNumeroManche(dto.numeroManche);
        partie.setPartieTerminee(dto.partieTerminee);

        return partie;
    }
}
