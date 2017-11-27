import java.util.Random;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class Joueur {
    private int[] actions;
    private int bonusDe;
    private int[] bambous;
    private int[] amenagements;
    private int irrigation;
    private int nbPoints;
    private Objectif[] objectifs;

    public Joueur() {
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
        nbPoints=0;
        objectifs=new Objectif[5];
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
}
