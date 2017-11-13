import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class Panda {
    private Parcelle position;

    public Panda(Parcelle position) {
        this.position = position;
    }

    public List<Parcelle> deplacementsPossible(){
        List<Parcelle> deplacementsPossible=new ArrayList<Parcelle>();
        for (int i=0; i<6; i++){
            Parcelle p= position.parcellesAdjacentes()[i];
            while (p!= null){
                deplacementsPossible.add(p);
                p=p.parcellesAdjacentes()[i];
            }
        }
        return deplacementsPossible;
    }

    public void deplacement(Parcelle p) {
        position=p;
        if (position.getAmenagement()!=2){
            mangeBambou();
        }
    }

    private void mangeBambou() {
        if (position.getNbBambou()>0) position.setNbBambou(position.getNbBambou()-1);
    }

    public Parcelle getPosition() {
        return position;
    }
}
