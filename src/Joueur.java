import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class Joueur {
    private int numJoueur;
    private int[] actions;
    private int bonusDe;
    private int[] bambous;
    private int[] amenagements;
    private int irrigation;
    private int points;
    private List<Objectif> objectifs;
    private List<Objectif> objectifsATest;
    private int nbObjectifsRealises;

    public Joueur(int n) {
        numJoueur=n;
        bambous=new int[3];
        amenagements=new int[3];
        actions=new int[3];
        irrigation=0;
        bonusDe=0;
        for (int i=0; i<3; i++){
            bambous[i]=0;
            amenagements[i]=0;
            actions[i]=0;
        }
        points=0;
        objectifs=new ArrayList<Objectif>();
        objectifsATest=new ArrayList<Objectif>();
        nbObjectifsRealises=0;
    }

    public int[] getActions() {
        return actions;
    }

    public int[] getBambous() {
        return bambous;
    }

    public int[] getAmenagements() {
        return amenagements;
    }

    public int getBonusDe() {
        return bonusDe;
    }

    public void setBonusDe(int bonusDe) {
        this.bonusDe = bonusDe;
    }

    public void addBambou(int color) {
        bambous[color]+=1;
    }

    public void lanceDe(){
        Random random=new Random();
        bonusDe=random.nextInt(5)+1;
    }

    public int getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(int irrigation) {
        this.irrigation = irrigation;
    }

    public List<Objectif> getObjectifs() {
        return objectifs;
    }

    public int getPoints() {
        return points;
    }

    public void verificationObjectifs(Model m){
        int n;
        for (int i=0; i<objectifsATest.size(); i++){
            n=objectifsATest.get(i).appliqueObjectif(this,m.getPlateau());
            if (n!=0) {
                objectifs.remove(objectifsATest.get(i));
                objectifsATest.remove(i);
                i--;
                points+=n;
                nbObjectifsRealises++;
            }
            else points--;
        }
        objectifsATest.clear();
        if (nbObjectifsRealises>=11-m.getNbJoueurs() && m.getJoueurFin()==null){
            m.lanceTourFin(this);
            points+=2;
        }
    }

    public void piocheObjectif(Queue<Objectif> pileObjectifs) {
        if (objectifs.size()<5){
            objectifs.add(pileObjectifs.poll());
        }
    }

    public int getNumJoueur() {
        return numJoueur;
    }

    public void addObjectifTest(int i) {
        objectifsATest.add(objectifs.get(i));
    }

    public void deleteObjectifTest(int i) {
        objectifsATest.remove(objectifs.get(i));
    }
}
