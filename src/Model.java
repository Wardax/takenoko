import java.util.*;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class Model {
    private Joueur[] joueurs;
    private Queue<Parcelle> pileParcelle;
    private int[] amenagements;
    private Plateau plateau;
    private Joueur joueurActuel;

    public Model() {
        int nbJoeurs=1;
        joueurs= new Joueur[nbJoeurs];
        for (int i=0; i<nbJoeurs; i++){
            joueurs[i]=new Joueur();
        }
        joueurActuel=joueurs[0];
        plateau=new Plateau();
        creePileParcelle();
        amenagements=new int[]{3,3,3};
    }

    private void creePileParcelle() {
        List<Parcelle> pileParcelle=new ArrayList<Parcelle>();
        for (int i=0; i<11; i++) pileParcelle.add(new Parcelle(0));
        for (int i=0; i<7; i++) pileParcelle.add(new Parcelle(1));
        for (int i=0; i<9; i++) pileParcelle.add(new Parcelle(2));
        Collections.shuffle(pileParcelle);
        this.pileParcelle=new ArrayDeque<Parcelle>();
        this.pileParcelle.addAll(pileParcelle);
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Queue<Parcelle> getPileParcelle() {
        return pileParcelle;
    }

    public int[] getAmenagements() {
        return amenagements;
    }

    public Joueur getJoueurActuel() {
        return joueurActuel;
    }
}
