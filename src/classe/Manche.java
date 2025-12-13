package classe;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une manche pendant la partie
 *
 * <p>Elle permet de gerer le tour des joueurs, la mise en place des offres, des pioches....
 * </p>
 * 
 *
 * @author Tristan
 * @version %I%, %G%
 */
public class Manche {
    private int numero = 0;
    private List<Offre> offres;

    /**
     * Le constructeur de la manche
     */
    public Manche(){
        this.offres = new ArrayList<>();
    }

    /**
     * Retourne le numéro de la manche.
     *
     * @return le numéro de la manche
     */
    public int getNumero() {
        return this.numero;
    }

    public void incrementNumero() {
        this.numero++;
    }
}
