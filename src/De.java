import java.util.Random;

/**
 * Created by Guillaume on 07/11/2017.
 */
public class De {
    private int valeur;

    public De() {
        valeur=0;
    }

    public void lanceDe(){
        Random random=new Random();
        valeur=random.nextInt(5)+1;
    }

    public int appliqueActionDe(){
        return valeur;
    }
}
