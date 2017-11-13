import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillaume on 17/10/2017.
 */
public class Plateau {
    public static int width=1000;
    public static int height=800;
    private Etang etang;
    private List<Parcelle> parcelles;
    private List<LienParcelle> lienParcelles;
    private Panda panda;
    private Jardinier jardinier;

    public Plateau() {
        parcelles=new ArrayList<Parcelle>();
        lienParcelles=new ArrayList<LienParcelle>();
        etang=new Etang();
        parcelles.add(etang);
        jardinier=new Jardinier(etang);
        panda=new Panda(etang);
    }


    public List<Parcelle> getParcelles() {
        return parcelles;
    }

    public List<LienParcelle> getLienParcelles() {
        return lienParcelles;
    }

    public List<int[]> getPositionNouvellePartelle(){
        List<int[]> listPosNewParc=new ArrayList<int[]>();
        for (Parcelle p : parcelles){
            listPosNewParc.addAll(p.getPosParcelleVideAdjacente());
        }
        return listPosNewParc;
    }

    public List<int[]> getPositionIrrigationPossible(){
        List<int[]> listPosIrrigation=new ArrayList<int[]>();
        for (LienParcelle l : lienParcelles){
            int[] pos=l.getPosSiIrrigationPossible();
            if (pos!=null) listPosIrrigation.add(pos);
        }
        return listPosIrrigation;
    }

    public void addParcelle(Parcelle p, int x, int y){
        p.setPosX(x);
        p.setPosY(y);
        creeLien(p);
        parcelles.add(p);
    }

    private void creeLien(Parcelle p) {
        int [][] posAdj = p.getAdjacence();

        for (Parcelle parcelle : parcelles){
            for (int i=0; i<6; i++){
                if (posAdj[i][0]==parcelle.getPosX() && posAdj[i][1]==parcelle.getPosY()){
                    LienParcelle lienParcelle=new LienParcelle(p, parcelle);
                    lienParcelles.add(lienParcelle);
                    p.getLienParcelles()[i]=lienParcelle;
                    parcelle.getLienParcelles()[(i+3)%6]=lienParcelle;
                    lienParcelle.initialisePos();
                }
            }
        }
    }

    public List<Parcelle> getParcellesSansAmenagement(){
        List<Parcelle> parcellesSansAmenagement=new ArrayList<Parcelle>();
        for (Parcelle p: parcelles){
            if (p.getAmenagement()==0 && !(p instanceof Etang)) parcellesSansAmenagement.add(p);
        }
        return parcellesSansAmenagement;
    }


    public Jardinier getJardinier() {
        return jardinier;
    }

    public Etang getEtang() {
        return etang;
    }

    public Panda getPanda() {
        return panda;
    }
}
