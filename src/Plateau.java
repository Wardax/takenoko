import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Guillaume on 17/10/2017.
 */
public class Plateau {
    public static int width=900;
    public static int height=700;
    private Etang etang;
    private List<Parcelle> parcelles;
    private List<LienParcelle> lienParcelles;
    private Panda panda;
    private Jardinier jardinier;

    public Plateau() {
        parcelles= new ArrayList<>();
        lienParcelles= new ArrayList<>();
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

    public List<int[]> getPositionNouvelleParcelle(){
        List<int[]> listPosNewParc= new ArrayList<>();
        for (Parcelle p : parcelles){
            for (int[] pos : p.getPosParcelleVideAdjacente()){
                boolean estDejaPresent=false;
                for (int[] pnp : listPosNewParc){
                    if (Arrays.equals(pos, pnp)) estDejaPresent=true;
                }
                if (!estDejaPresent) listPosNewParc.add(pos);
            }
        }
        return listPosNewParc;
    }

    public List<int[]> getPositionIrrigationPossible(){
        List<int[]> listPosIrrigation= new ArrayList<>();
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
        List<Parcelle> parcellesSansAmenagement= new ArrayList<>();
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

    public List<Parcelle> getParcellesIrriguees() {
        List<Parcelle> list = new ArrayList<>();
        for (Parcelle p : parcelles){
            if (p.isIrriguee()) list.add(p);
        }
        return list;
    }

}
