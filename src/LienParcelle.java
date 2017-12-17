/**
 * Created by Guillaume on 16/10/2017.
 */
public class LienParcelle {
    private boolean irriguee;
    private Parcelle parcelleLie1;
    private Parcelle parcelleLie2;
    private int[] pos;

    public LienParcelle(Parcelle parcelleLie1, Parcelle parcelleLie2) {
        this.parcelleLie1 = parcelleLie1;
        this.parcelleLie2 = parcelleLie2;
        if (parcelleLie1 instanceof Etang || parcelleLie2 instanceof Etang) {
            setIrriguee();
        }
        else irriguee=false;
    }

    public boolean isIrriguee() {
        return irriguee;
    }

    public void initialisePos(){

        pos= new int[3];
        if (this==parcelleLie1.getLienParcelles()[0]){
            pos[0]=parcelleLie1.getPosX()+50+20;
            pos[1]=parcelleLie1.getPosY()-10;
            pos[2]=0;
        }
        else if (this==parcelleLie1.getLienParcelles()[1]){
            pos[0]=parcelleLie1.getPosX()+100-5;
            pos[1]=parcelleLie1.getPosY()+30;
            pos[2]=1;
        }
        else if (this==parcelleLie1.getLienParcelles()[2]){
            pos[0]=parcelleLie1.getPosX()+50+20;
            pos[1]=parcelleLie1.getPosY()+80-10;
            pos[2]=2;
        }
        else if (this==parcelleLie1.getLienParcelles()[3]){
            pos[0]=parcelleLie1.getPosX()+20;
            pos[1]=parcelleLie1.getPosY()+80-10;
            pos[2]=0;
        }
        else if (this==parcelleLie1.getLienParcelles()[4]){
            pos[0]=parcelleLie1.getPosX()-5;
            pos[1]=parcelleLie1.getPosY()+30;
            pos[2]=1;
        }
        else {
            pos[0]=parcelleLie1.getPosX()+20;
            pos[1]=parcelleLie1.getPosY()-10;
            pos[2]=2;
        }
    }

    public int[] getPosSiIrrigationPossible(){
        if (irriguee) return null;
        boolean irrigable=false;
        for (int i=0; i<6; i++){
            if ((this==parcelleLie1.getLienParcelles()[i]
                    && ((parcelleLie1.getLienParcelles()[(i+5)%6]!= null
                        && parcelleLie1.getLienParcelles()[(i+5)%6].isIrriguee())
                    || (parcelleLie1.getLienParcelles()[(i+1)%6]!=null
                        && parcelleLie1.getLienParcelles()[(i+1)%6].isIrriguee())))
               || (this==parcelleLie2.getLienParcelles()[i]
                    && ((parcelleLie2.getLienParcelles()[(i+5)%6]!= null
                    && parcelleLie2.getLienParcelles()[(i+5)%6].isIrriguee())
                    || (parcelleLie2.getLienParcelles()[(i+1)%6]!=null
                    && parcelleLie2.getLienParcelles()[(i+1)%6].isIrriguee()))))  irrigable=true;
        }
        if (!irrigable) return null;
        return pos;

    }

    public int[] getPos() {
        return pos;
    }

    public void setIrriguee() {
        this.irriguee = true;
        parcelleLie1.setIrriguee();
        parcelleLie2.setIrriguee();
    }

    public Parcelle getParcelLie(Parcelle p) {
        if (p == parcelleLie1) return parcelleLie2;
        if (p == parcelleLie2) return parcelleLie1;
        return null;
    }
}
