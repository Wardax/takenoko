import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class Jardinier {
    private Parcelle position;

    public Jardinier(Parcelle position) {
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

    public void cultiveBambou(){
        position.pousseBambou();
        for (Parcelle p : position.parcellesAdjacentes()){
            if (p!= null && p.getColor()==position.getColor()) p.pousseBambou();
        }
    }

    public void deplacement(Parcelle p) {
        position=p;
        cultiveBambou();
    }

    public Parcelle getPosition() {
        return position;
    }
}
