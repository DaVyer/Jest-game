package classe;

import java.util.ArrayList;
import java.util.List;

public class DTOMapper {

    /* =========================
       CARTE
       ========================= */

    public static Carte carteFromDTO(CarteDTO dto) {
        return new Carte(dto.valeur, dto.couleur, dto.condition);
    }

    public static CarteDTO carteToDTO(Carte c) {
        CarteDTO dto = new CarteDTO();
        dto.valeur = c.getValeur();
        dto.couleur = c.getCouleur();
        dto.condition = c.getTrophee();
        return dto;
    }

    public static Joueur joueurFromDTO(JoueurDTO dto) {

        StrategieJoueur strategie = dto.typeStrategie.equals("StrategieAleatoire")
                                    ? new StrategieRobotAleatoire()
                                    : new StrategieHumaine();

        Joueur j = new Joueur(dto.nom, strategie);

        if (dto.jest != null && dto.jest.cartes != null) {
            for (CarteDTO cDTO : dto.jest.cartes) {
                j.ajouterAuJest(carteFromDTO(cDTO));
            }
        }

        return j;
    }

    public static JoueurDTO joueurToDTO(Joueur j) {

        JoueurDTO dto = new JoueurDTO();
        dto.nom = j.getNom();
        dto.typeStrategie = j.getStrategie().getClass().getSimpleName();

        JestDTO jestDTO = new JestDTO();

        for (int i = 0; i < j.getMain().taille(); i++) {
            jestDTO.cartes.add(carteToDTO(j.getMain().getCarte(i)));
        }

        dto.jest = jestDTO;

        System.out.println("DTO sauvegardé pour " + j.getNom()
                + " : " + jestDTO.cartes.size() + " cartes");

        return dto;
    }

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
