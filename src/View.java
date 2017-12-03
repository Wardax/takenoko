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
    Group selectionObjectifs;
    Button bParcelle;
    Button irrigation;
    Button bJardinier;
    Button bPanda;
    Button bObjectifs;
    Button bNuage;
    Button bPluie;
    Button bOrage;
    Button bVerifObjectifs;
    Button bFinTour;
    ImageView de;

    VueJoueur[] vueJoueurs;
    Group vueObjectif;

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
        bParcelle.relocate(0, 50);
        irrigation = new Button("Action Irrigation");
        irrigation.relocate(0, 100);
        bJardinier= new Button("Action jardinier");
        bJardinier.relocate(0, 150);
        bPanda=new Button("Action Panda");
        bPanda.relocate(0,200);
        bObjectifs=new Button("Action Objectif");
        bObjectifs.relocate(0,250);
        bNuage =new Button("Action nuage");
        bNuage.relocate(0, 300);
        bPluie=new Button("Action Pluie");
        bPluie.relocate(0,350);
        bOrage=new Button("Action Orage");
        bOrage.relocate(0,400);


        bVerifObjectifs=new Button("Verification des objectifs");
        bVerifObjectifs.relocate(920, 650);
        plateau.getChildren().add(bVerifObjectifs);

        bFinTour=new Button("Fin du tour");
        bFinTour.relocate(1100, 650);
        plateau.getChildren().add(bFinTour);

        creeSelectionAction();

        vueJoueurs=new VueJoueur[model.getNbJoueurs()];
        for (int i=0; i<vueJoueurs.length; i++){
            vueJoueurs[i]=new VueJoueur(model.getJoueurs()[i]);
            plateau.getChildren().add(vueJoueurs[i]);
        }

        afficheObjectifs();


        ImageView imageFond=new ImageView("image/fond.jpg");
        imageFond.setFitHeight(700);
        imageFond.setFitWidth(1200);
        plateau.getChildren().add(imageFond);
        imageFond.toBack();
        imageFond.setOpacity(0.2);


        scene=new Scene(plateau,1200,700);
    }

    public void enleveBouttonActions(){
        plateau.getChildren().remove(bParcelle);
        plateau.getChildren().remove(irrigation);
        plateau.getChildren().remove(bJardinier);
        plateau.getChildren().remove(bPanda);
        plateau.getChildren().remove(bObjectifs);
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
        vueJoueurs[model.getJoueurActuel().getNumJoueur()].actualiseVueJoueur();

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
        vueJoueurs[model.getJoueurActuel().getNumJoueur()].actualiseVueJoueur();
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


    /* place dans la liste du groupe :
       * 0 -> le bouton pour valider les actions
       * 1 -> bouton pour reset les actions
       * 2 à 6 -> les cercles de selections
       * 7 à 11 -> les images */
    private void creeSelectionAction(){
        selectionAction = new Group();

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


        Circle circleObjectif = new Circle(260, 65, 15);
        circleObjectif.setStroke(Color.BLUEVIOLET);
        circleObjectif.setFill(Color.TRANSPARENT);
        circleObjectif.setStrokeWidth(2);
        selectionAction.getChildren().add(circleObjectif);


        ImageView imageActionParcelle =new ImageView("image/parcelle.jpg");
        imageActionParcelle.setPreserveRatio(true);
        imageActionParcelle.setFitHeight(40);
        ImageView imageActionIrrigation =new ImageView("image/irrigation120.png");
        imageActionIrrigation.setPreserveRatio(true);
        imageActionIrrigation.setFitWidth(40);
        ImageView imageActionJardinier =new ImageView("image/Jardinier.PNG");
        imageActionJardinier.setPreserveRatio(true);
        imageActionJardinier.setFitHeight(40);
        ImageView imageActionPanda =new ImageView("image/bebepanda.png");
        imageActionPanda.setPreserveRatio(true);
        imageActionPanda.setFitHeight(40);
        ImageView imageActionObjectif =new ImageView("image/objectifPanda.PNG");
        imageActionObjectif.setPreserveRatio(true);
        imageActionObjectif.setFitHeight(40);


        selectionAction.getChildren().add(imageActionParcelle);
        selectionAction.getChildren().add(imageActionIrrigation);
        selectionAction.getChildren().add(imageActionJardinier);
        selectionAction.getChildren().add(imageActionPanda);
        selectionAction.getChildren().add(imageActionObjectif);


        imageActionIrrigation.relocate(60, 0);
        imageActionJardinier.relocate(120,0);
        imageActionPanda.relocate(180,0);
        imageActionObjectif.relocate(240,0);


        de=new ImageView();
        selectionAction.getChildren().add(de);
        de.relocate(150,100);

        plateau.getChildren().add(selectionAction);
        selectionAction.relocate(900, 270);


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
        for (Node n : plateau.getChildren()){
            if (n==bParcelle) return;
        }
        plateau.getChildren().add(bParcelle);
    }

    public void ajouteBouttonIrrigation() {
        for (Node n : plateau.getChildren()){
            if (n==irrigation) return;
        }
        plateau.getChildren().add(irrigation);
        vueJoueurs[model.getJoueurActuel().getNumJoueur()].actualiseVueJoueur();
    }

    public void ajouteBouttonJardinier() {
        for (Node n : plateau.getChildren()){
            if (n==bJardinier) return;
        }
        plateau.getChildren().add(bJardinier);
    }

    public void ajouteBouttonPanda() {
        for (Node n : plateau.getChildren()){
            if (n==bPanda) return;
        }
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

    public void ajouteBouttonObjectifs() {

        for (Node n : plateau.getChildren()){
            if (n==bObjectifs) return;
        }
        plateau.getChildren().add(bObjectifs);
    }

    public void affichageSelectionObjectifs() {
        selectionObjectifs=new Group();
        ImageView im;
        if (model.getPileObjectifsPanda().size()>0) {
            im=new ImageView("image/objectifPanda.PNG");
            im.setPreserveRatio(true);
            im.setFitHeight(100);
            selectionObjectifs.getChildren().add(im);
        }
        if (model.getPileObjectifsJardinier().size()>0) {
            im=new ImageView("image/objectifJardinier.PNG");
            im.setPreserveRatio(true);
            im.setFitHeight(100);
            im.relocate(100, 0);
            selectionObjectifs.getChildren().add(im);
        }
        if (model.getPileObjectifsParcelle().size()>0) {
            im=new ImageView("image/objectifParcelle.PNG");
            im.setPreserveRatio(true);
            im.setFitHeight(100);
            im.relocate(200, 0);
            selectionObjectifs.getChildren().add(im);
        }

        selectionObjectifs.relocate(10,30);
        plateau.getChildren().add(selectionObjectifs);
    }

    public void actualiseAjoutObjectif() {
        VueJoueur vj =vueJoueurs[model.getJoueurActuel().getNumJoueur()];
        vj.actualiseVueJoueur();
        vj.actualiseObjectifs();
        afficheObjectifs();
        plateau.getChildren().remove(selectionObjectifs);
    }

    public void afficheObjectifs(){
        plateau.getChildren().remove(vueObjectif);
        vueObjectif=new Group();
        vueObjectif.relocate(900,0);
        plateau.getChildren().add(vueObjectif);
        int n=model.getJoueurActuel().getNumJoueur();
        for (int i=0; i<model.getJoueurActuel().getObjectifs().size(); i++){
            vueObjectif.getChildren().add(vueJoueurs[n].objectifs[i]);
            if (i<3)  vueJoueurs[n].objectifs[i].relocate(i * 100, 420);
            else vueJoueurs[n].objectifs[i].relocate((i-3)*100+50, 530);
        }
    }

}
