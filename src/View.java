import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class View {
    private Model model;
    private Scene scene;
    private Rectangle jardinier;
    private Rectangle panda;
    Group selectionAction;
    Group plateau;
    Group positionPossible;
    Group selectionParcelle;
    Group selectionAmenagement;
    Group irrigationPossible;
    Button bParcelle;
    Button irrigation;
    Button bJardinier;
    Button bPanda;
    Button bNuage;
    Button bPluie;
    Button bOrage;
    ImageView de;

    MenuBar barreMenu;
    Menu options;
    MenuItem newPartie;
    MenuItem rules;
    MenuItem helper;
    MenuItem quitter;
    Group sousMenu;


    public View(Model model) {
        this.model=model;
        creerScene();
        creerSousMenu();
    }

    private void creerScene() {
        plateau = new Group();
        Etang e=model.getPlateau().getEtang();
        VueParcelle etang=new VueParcelle(e);
        etang.getChildren().remove(etang.getNombreBambou());
        plateau.getChildren().add(etang);
        jardinier=new Rectangle(20,20, Color.RED);
        etang.addJardinier(jardinier);
        panda=new Rectangle(20,20);
        etang.addPanda(panda);


    bParcelle =new Button("Action Parcelle");
        bParcelle.relocate(0, 450);
        irrigation = new Button("Action Irrigation");
        irrigation.relocate(0, 600);
        bJardinier= new Button("Action jardinier");
        bJardinier.relocate(0, 650);
        bPanda=new Button("Action Panda");
        bPanda.relocate(0,550);
        bNuage =new Button("Action nuage");
        bNuage.relocate(0, 150);
        bPluie=new Button("Action Pluie");
        bPluie.relocate(0,50);
        bOrage=new Button("Action Orage");
        bOrage.relocate(0,100);

        /*plateau.getChildren().add(bParcelle);
        plateau.getChildren().add(irrigation);
        plateau.getChildren().add(bJardinier);
        plateau.getChildren().add(bPanda);
        plateau.getChildren().add(bNuage);*/

        creeSelectionAction();


        scene=new Scene(plateau,1200,700);


    }

    public void enleveBouttonActions(){
        plateau.getChildren().remove(bParcelle);
        plateau.getChildren().remove(irrigation);
        plateau.getChildren().remove(bJardinier);
        plateau.getChildren().remove(bPanda);
        plateau.getChildren().remove(bNuage);
        plateau.getChildren().remove(bPluie);
        plateau.getChildren().remove(bOrage);
    }

    public Scene getScene() {
        return scene;
    }

    public void affichePosPossible(){
        List<int[]> list=model.getPlateau().getPositionNouvellePartelle();
        positionPossible=new Group();
        for (int[] l : list){
            Polygon polygon = new Polygon();
            polygon.getPoints().addAll(50., 0.,
                    0., 30.,
                    0., 80.,
                    50., 110.,
                    100., 80.,
                    100., 30.);
            positionPossible.getChildren().add(polygon);
            polygon.relocate(l[0], l[1]);
        }
        plateau.getChildren().add(positionPossible);
    }


    public void affichageSelectionParcelle(Parcelle[] parcelles) {
        selectionParcelle=new Group();
        for (int i=0; i<parcelles.length; i++) {
            VueParcelle vp=new VueParcelle(parcelles[i]);
            vp.getNombreBambou().setText("");
            selectionParcelle.getChildren().add(vp);
            if (i==0) vp.relocate(100,0);
            if (i==1) vp.relocate(0, 100);
            if (i==2) vp.relocate(200, 100);
        }
        selectionParcelle.relocate(0,30);
        plateau.getChildren().add(selectionParcelle);
    }

    public void afficheParcelle(Parcelle parcelle) {
        VueParcelle vp=new VueParcelle(parcelle);
        plateau.getChildren().add(vp);
    }


    public void afficheIrrigationPossible(){
        List<int[]> list=model.getPlateau().getPositionIrrigationPossible();
        irrigationPossible=new Group();
        for (int[] l : list){
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(50);
            rectangle.setWidth(10);

            irrigationPossible.getChildren().add(rectangle);
            rectangle.relocate(l[0], l[1]);
        }
        plateau.getChildren().add(irrigationPossible);
    }

    public void afficheIrrigation(Node n) {
        ((Rectangle)n).setFill(Color.AQUA);
        plateau.getChildren().add(n);

    }

    public void actualiseParcelle() {
        for (Node n : plateau.getChildren()){
            if (n instanceof VueParcelle){
                ((VueParcelle)n).actualiseNbBambou();
            }
        }
    }

    public void deplaceJardinier(VueParcelle vp) {
        for (Node n : plateau.getChildren()) {
            if (n instanceof VueParcelle && ((VueParcelle) n).getP() == model.getPlateau().getJardinier().getPosition())
                ((VueParcelle) n).getChildren().remove(jardinier);
        }
        vp.addJardinier(jardinier);
        actualiseParcelle();
    }

    public void deplacePanda(VueParcelle vp) {
        for (Node n : plateau.getChildren()) {
            if (n instanceof VueParcelle && ((VueParcelle) n).getP() == model.getPlateau().getPanda().getPosition())
                ((VueParcelle) n).getChildren().remove(panda);
        }
        vp.addPanda(panda);
        actualiseParcelle();
    }

    public void affichageSelectionAmenagement() {
        int[] amenagements= model.getAmenagements();
        selectionAmenagement=new Group();
        if (amenagements[0]>0) {
            Circle amenagement= new Circle(50, 50, 30, Color.BROWN); // amenagement engrais
            selectionAmenagement.getChildren().add(amenagement);
        }
        if (amenagements[1]>0) {
            Circle amenagement= new Circle(150, 50, 30, Color.RED); // amenagement enclos
            selectionAmenagement.getChildren().add(amenagement);
        }
        if (amenagements[2]>0) {
            Circle amenagement= new Circle(50, 150, 30, Color.BLUE); // amenagement irriguation
            selectionAmenagement.getChildren().add(amenagement);
        }
        selectionAmenagement.relocate(0,30);
        plateau.getChildren().add(selectionAmenagement);

    }


    private void creeSelectionAction(){
        selectionAction = new Group();

        /* place dans la liste du groupe :
        * 0 -> le bouton pour valider les actions
        * 1 -> bouton pour reset les actions
        * 2 à 5 -> les cercles de selections
        * 6 à 9 -> les rectangles */

        Button validButton=new Button("Valider");
        validButton.relocate(0, 100);
        selectionAction.getChildren().add(validButton);

        Button resetButton=new Button("Reset");
        resetButton.relocate(0, 130);
        selectionAction.getChildren().add(resetButton);
        resetButton.setVisible(false);


        Circle circlePacelle = new Circle(20, 65, 15);
        circlePacelle.setStroke(Color.GREEN);
        circlePacelle.setFill(Color.TRANSPARENT);
        circlePacelle.setStrokeWidth(2);
        selectionAction.getChildren().add(circlePacelle);


        Circle circleIrrigation = new Circle(80, 65, 15);
        circleIrrigation.setStroke(Color.BLUE);
        circleIrrigation.setFill(Color.TRANSPARENT);
        circleIrrigation.setStrokeWidth(2);
        selectionAction.getChildren().add(circleIrrigation);


        Circle circleJardinier = new Circle(140, 65, 15);
        circleJardinier.setStroke(Color.RED);
        circleJardinier.setFill(Color.TRANSPARENT);
        circleJardinier.setStrokeWidth(2);
        selectionAction.getChildren().add(circleJardinier);


        Circle circlePanda = new Circle(200, 65, 15);
        circlePanda.setStroke(Color.BLACK);
        circlePanda.setFill(Color.TRANSPARENT);
        circlePanda.setStrokeWidth(2);
        selectionAction.getChildren().add(circlePanda);


        Rectangle imageActionParcelle =new Rectangle(40,40, Color.GREEN);
        Rectangle imageActionIrrigation =new Rectangle(40,40, Color.BLUE);
        Rectangle imageActionJardinier =new Rectangle(40,40, Color.RED);
        Rectangle imageActionPanda =new Rectangle(40,40);


        selectionAction.getChildren().add(imageActionParcelle);
        selectionAction.getChildren().add(imageActionIrrigation);
        selectionAction.getChildren().add(imageActionJardinier);
        selectionAction.getChildren().add(imageActionPanda);


        imageActionIrrigation.relocate(60, 0);
        imageActionJardinier.relocate(120,0);
        imageActionPanda.relocate(180,0);


        de=new ImageView();
        selectionAction.getChildren().add(de);
        de.relocate(150,100);

        plateau.getChildren().add(selectionAction);
        selectionAction.relocate(900, 200);


    }

    public void creerSousMenu(){
        sousMenu = new Group();
        barreMenu = new MenuBar();
        barreMenu.prefWidthProperty().bind(scene.widthProperty());

        options = new Menu("Options");
        newPartie = new MenuItem("Nouvelle Partie");
        rules = new MenuItem("Règles");
        helper = new MenuItem("Aides");
        quitter = new MenuItem("Quitter");


        options.getItems().addAll(newPartie, new SeparatorMenuItem(), rules, new SeparatorMenuItem(), helper, new SeparatorMenuItem(), quitter);
        barreMenu.getMenus().addAll(options);

        sousMenu.getChildren().add(barreMenu);
        plateau.getChildren().add(sousMenu);
    }


    public void ajouteBouttonParcelle() {
        plateau.getChildren().add(bParcelle);
    }

    public void ajouteBouttonIrrigation() {
        plateau.getChildren().add(irrigation);
    }

    public void ajouteBouttonJardinier() {
        plateau.getChildren().add(bJardinier);
    }

    public void ajouteBouttonPanda() {
        plateau.getChildren().add(bPanda);
    }


    public void ajouteBouttonPluie() {
        plateau.getChildren().add(bPluie);
    }
    public void ajouteBouttonOrage() {
        plateau.getChildren().add(bOrage);
    }
    public void ajouteBouttonNuage() {
        plateau.getChildren().add(bNuage);
    }

    public void afficheDe(int bonusDe) {
        de.setImage(new Image("image/faceDe"+bonusDe+".png"));
    }
}
