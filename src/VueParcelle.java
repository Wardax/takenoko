

import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Created by Guillaume on 06/11/2017.
 */
public class VueParcelle extends Group {

    private Parcelle p;
    private Text nombreBambou;
    private Circle amenagement;

    public VueParcelle(Parcelle p) {
        super();
        this.p=p;
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[]{
                50., 0.,
                0., 30.,
                0., 80.,
                50., 110.,
                100., 80.,
                100., 30.});
        if (p instanceof Etang) {
            final RadialGradient gradient = new RadialGradient(0, 0, 50, 55, 40, false, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.CYAN),
                    new Stop(1, Color.ROYALBLUE));
            polygon.setFill (gradient);
        }
        else  polygon.setFill(transformeColor(p.getColor()));
        this.getChildren().add(polygon);

        nombreBambou= new Text(45,55,""+p.getNbBambou());
        this.getChildren().add(nombreBambou);

        if (p.getAmenagement()!=0) {
            amenagement=new Circle(50,20,10, getCouleurAmenagement());
            this.getChildren().add(amenagement);
        }


        this.relocate(p.getPosX(), p.getPosY());
    }

    public void actualiseNbBambou(){
        this.nombreBambou.setText(""+p.getNbBambou());
    }


    public Color transformeColor(int pColor){
        if (pColor==0) return Color.GREEN;
        if (pColor==1) return Color.PINK;
        if (pColor==2) return Color.YELLOW;
        return null;
    }

    public Parcelle getP() {
        return p;
    }

    public Text getNombreBambou() {
        return nombreBambou;
    }

    public void addJardinier(Rectangle jardinier) {
        this.getChildren().add(jardinier);
        jardinier.relocate(20, 60);
    }

    public void addPanda(Rectangle panda) {
        this.getChildren().add(panda);
        panda.relocate(60, 60);
    }

    public Paint getCouleurAmenagement() {
        if (p.getAmenagement()==1) return Color.BROWN;
        if (p.getAmenagement()==2) return Color.RED;
        if (p.getAmenagement()==3) return Color.BLUE;
        return null;
    }

    public void addAmenagement(int amenagement) {
        p.setAmenagement(amenagement);
        if (amenagement==3) actualiseNbBambou();
        this.amenagement=new Circle(50,20,10, getCouleurAmenagement());
        this.getChildren().add(this.amenagement);

    }
}
