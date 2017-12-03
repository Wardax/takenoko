import javafx.scene.image.Image;

/**
 * Created by Guillaume on 26/11/2017.
 */
public class Objectif {
    private int type;
    private String imageUrl;

    public Objectif(int type) {
        this.type = type;
        this.imageUrl = "image/objectif"+type+".PNG";
    }

    public int appliqueObjectif(Joueur j, Plateau p){
        switch (type){
            case 1:
                if (j.getBambous()[0]>=2){
                    j.getBambous()[0]-=2;
                    return 3;
                }
                break;
            case 2:
                if (j.getBambous()[2]>=2){
                    j.getBambous()[2]-=2;
                    return 4;
                }
                break;
            case 3:
                if (j.getBambous()[1]>=2){
                    j.getBambous()[1]-=2;
                    return 5;
                }
                break;
            case 4:
                if (j.getBambous()[0]>=1 && j.getBambous()[1]>=1 && j.getBambous()[2]>=1){
                    j.getBambous()[0]--;
                    j.getBambous()[1]--;
                    j.getBambous()[2]--;
                    return 6;
                }
                break;


            case 5:
                if (chercheBambou(2, 3, p)) return 5;
                break;
            case 6:
                if (chercheBambou(2, 0, p)) return 6;
                break;
            case 7:
                if (cherchePlusieursBambou(1, 2, p)) return 6;
                break;
            case 8:
                if (cherchePlusieursBambou(2, 3, p)) return 7;
                break;
            case 9:
                if (cherchePlusieursBambou(0, 4, p)) return 8;
                break;
            case 10:
                if (chercheBambou(0, 2, p)) return 4;
                break;
            case 11:
                if (chercheBambou(0, 3, p)) return 4;
                break;
            case 12:
                if (chercheBambou(0, 0, p)) return 5;
                break;
            case 13:
                if (chercheBambou(2, 1, p)) return 4;
                break;
            case 14:
                if (chercheBambou(2, 2, p)) return 5;
                break;
            case 15:
                if (chercheBambou(1, 1, p)) return 5;
                break;
            case 16:
                if (chercheBambou(1, 3, p)) return 6;
                break;
            case 17:
                if (chercheBambou(1, 2, p)) return 6;
                break;
            case 18:
                if (chercheBambou(1, 0, p)) return 7;
                break;
            case 19:
                if (chercheBambou(0, 1, p)) return 3;
                break;


            case 20:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==0){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==0 && pAdj[(i+1)%6]!=null && pAdj[(i+1)%6].getColor()==0 ) return 2;
                        }
                    }
                }
                break;

            case 21:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==0){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==0 && pAdj[(i+1)%6]!=null && pAdj[(i+1)%6].getColor()==0 && pAdj[(i+2)%6]!=null && pAdj[(i+2)%6].getColor()==0) return 3;
                        }
                    }
                }
                break;

            case 22:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==2){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==1 && pAdj[(i+1)%6]!=null && pAdj[(i+1)%6].getColor()==1 && pAdj[(i+2)%6]!=null && pAdj[(i+2)%6].getColor()==2) return 5;
                        }
                    }
                }
                break;

            case 23:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==1){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==0 && pAdj[(i+1)%6]!=null && pAdj[(i+1)%6].getColor()==0 && pAdj[(i+2)%6]!=null && pAdj[(i+2)%6].getColor()==1) return 4;
                        }
                    }
                }
                break;

            case 24:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==2){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==0 && pAdj[(i+1)%6]!=null && pAdj[(i+1)%6].getColor()==0 && pAdj[(i+2)%6]!=null && pAdj[(i+2)%6].getColor()==2) return 3;
                        }
                    }
                }
                break;

            case 25:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==2){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==2 && pAdj[(i+3)%6]!=null && pAdj[(i+3)%6].getColor()==2) return 3;
                        }
                    }
                }
                break;

            case 26:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==2){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==2 && pAdj[(i+1)%6]!=null && pAdj[(i+1)%6].getColor()==2 && pAdj[(i+2)%6]!=null && pAdj[(i+2)%6].getColor()==2) return 4;
                        }
                    }
                }
                break;

            case 27:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==2){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==2 && pAdj[(i+2)%6]!=null && pAdj[(i+2)%6].getColor()==2) return 3;
                        }
                    }
                }
                break;

            case 28:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==0){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==0 && pAdj[(i+3)%6]!=null && pAdj[(i+3)%6].getColor()==0) return 2;
                        }
                    }
                }
                break;

            case 29:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==0){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==0 && pAdj[(i+2)%6]!=null && pAdj[(i+2)%6].getColor()==0) return 2;
                        }
                    }
                }
                break;

            case 30:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==1){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==1 && pAdj[(i+2)%6]!=null && pAdj[(i+2)%6].getColor()==1) return 4;
                        }
                    }
                }
                break;

            case 31:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==1){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==1 && pAdj[(i+1)%6]!=null && pAdj[(i+1)%6].getColor()==1 ) return 4;
                        }
                    }
                }
                break;

            case 32:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==1){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==1 && pAdj[(i+1)%6]!=null && pAdj[(i+1)%6].getColor()==1 && pAdj[(i+2)%6]!=null && pAdj[(i+2)%6].getColor()==1) return 5;
                        }
                    }
                }
                break;

            case 33:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==1){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==1 && pAdj[(i+3)%6]!=null && pAdj[(i+3)%6].getColor()==2) return 4;
                        }
                    }
                }
                break;

            case 34:
                for (Parcelle parcelle : p.getParcelles()) {
                    if (parcelle.getColor()==2){
                        for (int i=0; i<6; i++){
                            Parcelle[] pAdj=parcelle.parcellesAdjacentes();
                            if (pAdj[i]!=null && pAdj[i].getColor()==2 && pAdj[(i+1)%6]!=null && pAdj[(i+1)%6].getColor()==2) return 3;
                        }
                    }
                }
                break;



        }
        return 0;
    }

    public boolean chercheBambou(int col, int am, Plateau plateau){
        for (Parcelle p : plateau.getParcelles()){
            if (col==p.getColor() && am==p.getAmenagement() && 4 ==p.getNbBambou()){
                return true;
            }
        }
        return false;
    }
    public boolean cherchePlusieursBambou(int col, int nb, Plateau plateau){
        int n=0;
        for (Parcelle p : plateau.getParcelles()){
            if (col==p.getColor() &&  3 ==p.getNbBambou()){
                n+=1;
            }
        }
        if (n>=nb) return true;
        return false;
    }

    public String  getImageUrl() {
        return imageUrl;
    }
}
