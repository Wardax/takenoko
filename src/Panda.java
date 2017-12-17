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
        List<Parcelle> deplacementsPossible= new ArrayList<>();
        for (int i=0; i<6; i++){
            Parcelle p= position.parcellesAdjacentes()[i];
            while (p!= null){
                deplacementsPossible.add(p);
                p=p.parcellesAdjacentes()[i];
            }
        }
        return deplacementsPossible;
    }

    public void deplacement(Parcelle p, Joueur j) {
        position=p;
        if (position.getAmenagement()!=2){
            mangeBambou(j);
        }
    }

    private void mangeBambou(Joueur j) {
        if (position.getNbBambou()>0) {
            position.setNbBambou(position.getNbBambou()-1);
            j.addBambou(position.getColor());
        }
    }

    public Parcelle getPosition() {
        return position;
    }
}
