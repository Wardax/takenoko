import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class Etang extends Parcelle {
    public Etang() {
        super(4, Plateau.width/2-Parcelle.width, Plateau.height/2-Parcelle.height );
    }

    public List<int[]> getPosParcelleVideAdjacente(){
        List<int[]> listPosNewParc= new ArrayList<>();
        LienParcelle[] l = getLienParcelles();
        int [][] posAdj = getAdjacence();
        for (int i=0; i<6; i++){
            if (l[i]==null) listPosNewParc.add(posAdj[i]);
        }
        return listPosNewParc;
    }

    @Override
    public void setIrriguee() {}
}
