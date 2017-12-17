import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by Guillaume on 09/11/2017.
 */
public class VueJoueur extends Group{
    private Joueur joueur;

    private ImageView[] imageViews;
    /*
    0: irrigation;
    1: bambouVert;
    2: babouRose;
    3: babouJaune;
    4: objectif;
*/
    private Text[] texts;
    /*
    0: nbIrrigation;
    1: nbBambouVert;
    2: nbBambouRose;
    3: nbBambouJaune;
    4: nbObjectifs;
    5: nbPoints;
    6: nom
    //nbObjectifsRealisés;
    */

    public ImageView[] objectifs;

    public VueJoueur(Joueur joueur) {
        this.joueur = joueur;
        objectifs =new ImageView[5];
        imageViews=new ImageView[5];

        imageViews[0]=new ImageView("image/irrigation.png");
        imageViews[1]=new ImageView("image/bambouVert.PNG");
        imageViews[3]=new ImageView("image/bambouJaune.PNG");
        imageViews[2]=new ImageView("image/bambouRose.PNG");
        imageViews[4]=new ImageView("image/objectifJardinierValider.PNG");
        for (int i=0; i<imageViews.length; i++){
            imageViews[i].setPreserveRatio(true);
            imageViews[i].setFitHeight(30);
            getChildren().add(imageViews[i]);
        }

        texts =new Text[7];
        for (int i = 0; i< texts.length; i++){
            texts[i]=new Text("");

            texts[i].setFont(Font.font ("Verdana", 20));
            texts[i].setFill(Color.DARKGREEN);
            getChildren().add(texts[i]);
        }
        texts[6].setText("J"+(joueur.getNumJoueur()+1)+":");
        actualiseVueJoueur();
        actualiseObjectifs();
        affichageResume();

    }

    public void actualiseVueJoueur(){
        texts[0].setText(""+joueur.getIrrigation());
        texts[1].setText(""+joueur.getBambous()[0]);
        texts[2].setText(""+joueur.getBambous()[1]);
        texts[3].setText(""+joueur.getBambous()[2]);
        texts[4].setText(""+joueur.getNbObjectifsRealises());
        texts[5].setText(""+joueur.getPoints()+" pts");
    }

    public void actualiseObjectifs(){
        for (int i=0; i<5; i++) {
            if (i<joueur.getObjectifs().size()){
                objectifs[i]=new ImageView(joueur.getObjectifs().get(i).getImageUrl());
                objectifs[i].setPreserveRatio(true);
                objectifs[i].setFitHeight(90);
                objectifs[i].setOpacity(0.8);
                selectionObjectif(i);

            }
            else  objectifs[i]=null;
        }
    }

    public void affichageResume(){
        relocate(900,60*joueur.getNumJoueur());
        texts[6].relocate(0,20);
        texts[0].relocate(40,20);
        imageViews[0].relocate(55,20);
        texts[5].relocate(240, 20);
        for (int i=1; i<5; i++){
            texts[i].relocate(i*40+25, 20);
            imageViews[i].relocate(i*40+40,20);
        }
    }

    public void selectionObjectif(int i){
        objectifs[i].setOnMouseClicked(mouseEvent -> {
            joueur.addObjectifTest(i);
            objectifs[i].setOpacity(1);
            deselectionObjectif(i);
        });
    }

    public void deselectionObjectif(int i){
        objectifs[i].setOnMouseClicked(mouseEvent -> {
            joueur.deleteObjectifTest(i);
            objectifs[i].setOpacity(0.8);
            selectionObjectif(i);
        });
    }





}
