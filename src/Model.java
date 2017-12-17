import java.util.*;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class Model {
    private Joueur[] joueurs;
    private int nbJoueurs;
    private Queue<Parcelle> pileParcelle;
    private Queue<Objectif> pileObjectifsPanda;
    private Queue<Objectif> pileObjectifsJardinier;
    private Queue<Objectif> pileObjectifsParcelle;
    private int[] amenagements;
    private Plateau plateau;
    private Joueur joueurActuel;
    private Joueur joueurFin;

    public Model(int nbJoueurs) {
        this.nbJoueurs=nbJoueurs;
        plateau=new Plateau();
        creePileParcelle();
        creePilesObjectfs();
        amenagements=new int[]{3,3,3};
        joueurs= new Joueur[nbJoueurs];
        for (int i=0; i<nbJoueurs; i++){
            joueurs[i]=new Joueur(i);
            joueurs[i].piocheObjectif(pileObjectifsPanda);
            joueurs[i].piocheObjectif(pileObjectifsJardinier);
            joueurs[i].piocheObjectif(pileObjectifsParcelle);
        }
        joueurActuel=joueurs[0];
    }

    private void creePileParcelle() {
        List<Parcelle> pileParcelle= new ArrayList<>();
        for (int i=0; i<6; i++) pileParcelle.add(new Parcelle(0));
        pileParcelle.add(new Parcelle(0,1));
        pileParcelle.add(new Parcelle(0,2));
        pileParcelle.add(new Parcelle(0,2));
        pileParcelle.add(new Parcelle(0,3));
        pileParcelle.add(new Parcelle(0,3));
        for (int i=0; i<4; i++) pileParcelle.add(new Parcelle(1));
        pileParcelle.add(new Parcelle(1,1));
        pileParcelle.add(new Parcelle(1,2));
        pileParcelle.add(new Parcelle(1,3));
        for (int i=0; i<6; i++) pileParcelle.add(new Parcelle(2));
        pileParcelle.add(new Parcelle(2,1));
        pileParcelle.add(new Parcelle(2,2));
        pileParcelle.add(new Parcelle(2,3));
        Collections.shuffle(pileParcelle);
        this.pileParcelle= new ArrayDeque<>();
        this.pileParcelle.addAll(pileParcelle);
    }

    private void creePilesObjectfs(){
        List<Objectif> pileObjectifs = new ArrayList<>();
        for (int i=0; i<5; i++) pileObjectifs.add(new Objectif(1));
        for (int i=0; i<4; i++) pileObjectifs.add(new Objectif(2));
        for (int i=0; i<3; i++) {
            pileObjectifs.add(new Objectif(3));
            pileObjectifs.add(new Objectif(4));
        }
        Collections.shuffle(pileObjectifs);
        pileObjectifsPanda= new ArrayDeque<>();
        pileObjectifsPanda.addAll(pileObjectifs);
        pileObjectifs.clear();

        for (int i=5; i<20; i++) pileObjectifs.add(new Objectif(i));
        Collections.shuffle(pileObjectifs);
        pileObjectifsJardinier= new ArrayDeque<>();
        pileObjectifsJardinier.addAll(pileObjectifs);
        pileObjectifs.clear();

        for (int i=20; i<35; i++) pileObjectifs.add(new Objectif(i));
        Collections.shuffle(pileObjectifs);
        pileObjectifsParcelle= new ArrayDeque<>();
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

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public Queue<Objectif> getPileObjectifsPanda() {
        return pileObjectifsPanda;
    }

    public Queue<Objectif> getPileObjectifsJardinier() {
        return pileObjectifsJardinier;
    }

    public Queue<Objectif> getPileObjectifsParcelle() {
        return pileObjectifsParcelle;
    }

    public void nextJoueur() {
        joueurActuel=joueurs[(joueurActuel.getNumJoueur()+1)%joueurs.length];
    }

    public void lanceTourFin(Joueur joueur) {
        joueurFin=joueur;
    }

    public Object getJoueurFin() {
        return joueurFin;
    }

    public boolean partieFini() {
        return joueurActuel==joueurFin;
    }

    public int getJoueurGagnant() {
        int n=0;
        int p=0;
        for (Joueur j : joueurs){
            if (j.getPoints()>p){
                n=j.getNumJoueur();
            }
        }
        return n;
    }
}
