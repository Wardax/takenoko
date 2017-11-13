/**
 * Created by Guillaume on 16/10/2017.
 */
public class Joueur {
    private int action1;
    private int action2;
    private int actionBonus;
    private int bonusDe;
    private int[] bambous;
    private int[] amenagements;
    private int irrigation;

    public Joueur() {
        bambous=new int[3];
        amenagements=new int[3];
        irrigation=0;
        action1=0;
        action2=0;
        actionBonus=0;
        bonusDe=0;
        for (int i=0; i<3; i++){
            bambous[i]=0;
            amenagements[i]=0;
        }
    }

    public int getAction1() {
        return action1;
    }

    public void setAction1(int action1) {
        this.action1 = action1;
    }

    public int getAction2() {
        return action2;
    }

    public void setAction2(int action2) {
        this.action2 = action2;
    }

    public int getBonusDe() {
        return bonusDe;
    }

    public void setBonusDe(int bonusDe) {
        this.bonusDe = bonusDe;
    }
}
