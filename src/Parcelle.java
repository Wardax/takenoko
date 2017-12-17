import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class Parcelle {
    // 0 vert, 1 rose, 2 jaune
    private int color;
    private LienParcelle[] lienParcelles;
    private int nbBambou;
    private int posX;
    private int posY;
    private boolean irriguee;
    // 0 aucun, 1 engrais, 2 enclos, 3 source
    private int amenagement;
    static int width=50;
    static int height=55;

    public Parcelle(int couleurParcelle, int posX, int posY) {
        color = couleurParcelle;
        lienParcelles=new LienParcelle[6];
        nbBambou=0;
        irriguee=false;
        amenagement=0;
        this.posX=posX;
        this.posY=posY;
    }

    public Parcelle(int couleurParcelle, int amenagement){
        this(couleurParcelle);
        this.amenagement=amenagement;
    }

    public Parcelle(int couleurParcelle){
        this(couleurParcelle, -1, -1);
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public LienParcelle[] getLienParcelles() {
        return lienParcelles;
    }

    public List<int[]> getPosParcelleVideAdjacente(){
        List<int[]> listPosNewParc=new ArrayList<int[]>();
        LienParcelle[] l = lienParcelles;
        int [][] posAdj = getAdjacence();
        for (int i=0; i<6; i++){
            if (l[i]==null && (l[(i+5)%6]!=null || l[(i+1)%6]!=null)) listPosNewParc.add(posAdj[i]);
        }
        return listPosNewParc;
    }
    
    public int[][] getAdjacence(){
        int [][] posAdj = new int[6][2];
        posAdj[0][0]=posX+width;
        posAdj[0][1]=posY-80;

        posAdj[1][0]=posX+width*2;
        posAdj[1][1]=posY;

        posAdj[2][0]=posX+width;
        posAdj[2][1]=posY+80;

        posAdj[3][0]=posX-width;
        posAdj[3][1]=posY+80;

        posAdj[4][0]=posX-width*2;
        posAdj[4][1]=posY;

        posAdj[5][0]=posX-width;
        posAdj[5][1]=posY-80;
        return posAdj;
    }

    public Parcelle[] parcellesAdjacentes(){
        Parcelle[] parcellesAdjacentes=new Parcelle[6];
        for (int i=0; i<6; i++){
            if (lienParcelles[i]!=null) parcellesAdjacentes[i]= lienParcelles[i].getParcelLie(this);
        }
        return parcellesAdjacentes;
    }

    public void pousseBambou(){
        if (irriguee && nbBambou<4) {
            nbBambou++;
            if (amenagement==1 && nbBambou<4) nbBambou++;
        }
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getColor() {
        return color;
    }

    public boolean isIrriguee() {
        return irriguee;
    }

    public int getNbBambou() {
        return nbBambou;
    }

    public void setNbBambou(int nbBambou) {
        this.nbBambou = nbBambou;
    }

    public void setIrriguee() {
        if (!this.irriguee) {
            this.irriguee = true;
            pousseBambou();
        }
    }

    public int getAmenagement() {
        return amenagement;
    }

    public void setAmenagement(int amenagement) {
        this.amenagement = amenagement;
        if (amenagement==3) setIrriguee();
    }


}
