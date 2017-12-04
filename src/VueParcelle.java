

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * Created by Guillaume on 06/11/2017.
 */
public class VueParcelle extends Group {

    private Parcelle p;
    private Text nombreBambou;
    private ImageView amenagement;

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
        getChildren().add(polygon);

        nombreBambou= new Text(45,55,""+p.getNbBambou());

        nombreBambou.setFont(Font.font ("Verdana", 20));
        nombreBambou.setFill(Color.DARKBLUE);
        getChildren().add(nombreBambou);

        if (p.getAmenagement()!=0) {
            amenagement=new ImageView("image/"+getCouleurAmenagement());
            amenagement.setPreserveRatio(true);
            amenagement.setFitHeight(20);
            amenagement.relocate(40,10);
            getChildren().add(amenagement);
        }


        relocate(p.getPosX(), p.getPosY());
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

    public void addJardinier(ImageView jardinier) {
        getChildren().add(jardinier);
        jardinier.relocate(10, 40);
    }

    public void addPanda(ImageView panda) {
        getChildren().add(panda);
        panda.relocate(55, 50);
    }

    public String getCouleurAmenagement() {
        if (p.getAmenagement()==1) return "Amenagement_jardinier.PNG";
        if (p.getAmenagement()==2) return "Amenagement_cloture.PNG";
        if (p.getAmenagement()==3) return "Amenagement_irrigation.PNG";
        return null;
    }

    public void addAmenagement(int amenagement) {
        p.setAmenagement(amenagement);
        if (amenagement==3) actualiseNbBambou();
        this.amenagement=new ImageView("image/"+getCouleurAmenagement());
        this.amenagement.setPreserveRatio(true);
        this.amenagement.setFitHeight(20);
        this.amenagement.relocate(40,10);
        getChildren().add(this.amenagement);

    }
}
