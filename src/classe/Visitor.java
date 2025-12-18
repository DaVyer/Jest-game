package classe;

/**
 * Interface du pattern Visitor pour les joueurs.
 * 
 * <p>Permet d'implémenter des opérations sur les joueurs
 * sans modifier leur classe (calcul de score, attribution de trophées, etc.).</p>
 * 
 * @author Gwendal Rodrigues
 * @version %I%, %G%
 */
public interface Visitor {
    /**
     * Visite un joueur pour effectuer une opération.
     * 
     * @param joueur le joueur à visiter
     */
    void visit(Joueur joueur);
}
