import java.util.*;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class Model {
    private Joueur[] joueurs;
    private Queue<Parcelle> pileParcelle;
    private Queue<Objectif> pileObjectifsPanda;
    private Queue<Objectif> pileObjectifsJardinier;
    private Queue<Objectif> pileObjectifsParcelle;
    private int[] amenagements;
    private Plateau plateau;
    private Joueur joueurActuel;

    public Model() {
        int nbJoeurs=1;
        plateau=new Plateau();
        creePileParcelle();
        creePilesObjectfs();
        amenagements=new int[]{3,3,3};
        joueurs= new Joueur[nbJoeurs];
        for (int i=0; i<nbJoeurs; i++){
            joueurs[i]=new Joueur();
        }
        joueurActuel=joueurs[0];
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

    private void creePilesObjectfs(){
        List<Objectif> pileObjectifs = new ArrayList<Objectif>();
        for (int i=0; i<5; i++) pileObjectifs.add(new Objectif(1));
        for (int i=0; i<4; i++) pileObjectifs.add(new Objectif(2));
        for (int i=0; i<3; i++) {
            pileObjectifs.add(new Objectif(3));
            pileObjectifs.add(new Objectif(4));
        }
        Collections.shuffle(pileObjectifs);
        pileObjectifsPanda=new ArrayDeque<Objectif>();
        pileObjectifsPanda.addAll(pileObjectifs);
        pileObjectifs.clear();

        for (int i=5; i<20; i++) pileObjectifs.add(new Objectif(i));
        Collections.shuffle(pileObjectifs);
        pileObjectifsJardinier=new ArrayDeque<Objectif>();
        pileObjectifsJardinier.addAll(pileObjectifs);
        pileObjectifs.clear();

        for (int i=20; i<35; i++) pileObjectifs.add(new Objectif(i));
        Collections.shuffle(pileObjectifs);
        pileObjectifsParcelle=new ArrayDeque<Objectif>();
        pileObjectifsParcelle.addAll(pileObjectifs);

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
