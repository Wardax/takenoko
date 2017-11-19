
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import jdk.nashorn.internal.scripts.JO;

import java.util.Queue;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class Controller {
    private Model model;
    private View view;
    private Joueur j;

    public Controller(View view, Model model) {
        this.view=view;
        this.model=model;
        start();
    }

    private void start() {
        actionAmenagement();
        actionIrrigation();
        actionJardinier();
        actionPanda();
        actionParcelle();
        actionPluie();
        actionOrage();
        activeSelectionAction();


        actionQuitter();
        actionHelper();
    }
    
    private void appliqueSelectionAction(){
        j=model.getJoueurActuel();

        if (j.getActions()[0]==0 && j.getActions()[1]==0 && j.getActions()[2]==0) {
            activeSelectionAction();
            //activeBouttonFinDeTour();
            return;
        }

        for (int i=0; i<3; i++){
            if (j.getActions()[i]==1) view.ajouteBouttonParcelle();
            if (j.getActions()[i]==2) {
                j.setIrrigation(j.getIrrigation()+1);
                j.getActions()[i]=0;
            }
            if (j.getActions()[i]==3) view.ajouteBouttonJardinier();
            if (j.getActions()[i]==4) view.ajouteBouttonPanda();
        }

        if (j.getActions()[2] == 5) view.ajouteBouttonPluie();
        if (j.getActions()[2] == 6) view.ajouteBouttonOrage();
        if (j.getActions()[2] == 7) view.ajouteBouttonNuage();

        if (j.getIrrigation()>0) view.ajouteBouttonIrrigation();

    }

    private void controleDe(){
        Joueur j=model.getJoueurActuel();
        j.lanceDe();
        view.afficheDe(j.getBonusDe());
        if (j.getBonusDe()!=0) {
            if (j.getBonusDe() == 2) j.getActions()[2] = 5;
            if (j.getBonusDe() == 4) j.getActions()[2] = 6;
            if (j.getBonusDe() == 5) j.getActions()[2] = 7;
            if (j.getBonusDe() == 0) appliqueSelectionAction();
            if (j.getBonusDe() == 1) actionSoleil();
            if (j.getBonusDe() == 3) actionVent();
            if (j.getBonusDe()!=1 && j.getBonusDe()!=3) appliqueSelectionAction();
            j.setBonusDe(0);
        }
    }

    private void actionSoleil() {
        Joueur j=model.getJoueurActuel();
        for (int i=2; i<6; i++){
            if (i-1!=j.getActions()[0] && i-1!=j.getActions()[1]){
                final int action=i-1;
                Circle c = ((Circle)view.selectionAction.getChildren().get(i));
                c.setOnMouseClicked(mouseEvent -> {
                    c.setFill(Color.TURQUOISE);
                    j.getActions()[2]=action;
                    for (int k=2; k<6; k++) {
                        view.selectionAction.getChildren().get(k).setOnMouseClicked(null);
                    }
                    ((Button)view.selectionAction.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            appliqueSelectionAction();
                        }
                    });

                });
            }

        }
    }

    private void actionVent(){
        Joueur j=model.getJoueurActuel();
        for (int i=2; i<6; i++){
            if (i-1==j.getActions()[0] || i-1==j.getActions()[1]){
                final int action=i-1;
                Circle c = ((Circle)view.selectionAction.getChildren().get(i));
                c.setOnMouseClicked(mouseEvent -> {
                    for (int k=2; k<6; k++) {
                        Circle c2 = ((Circle)view.selectionAction.getChildren().get(k));
                        c2.setOnMouseClicked(null);
                        c2.setFill(Color.TRANSPARENT);
                    }

                    c.setFill(Color.MIDNIGHTBLUE);
                    j.getActions()[0]=action;
                    j.getActions()[1]=action;

                    ((Button)view.selectionAction.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            appliqueSelectionAction();
                        }
                    });

                });
            }

        }
    }


    private void activeBouttonFinDeTour() {
       // model.nextJoueur();
      //  activeSelectionAction();
    }


    private void activeSelectionAction() {
        Joueur j=model.getJoueurActuel();
        for (int i=2; i<6; i++){
            final int action=i-1;
            Circle c = ((Circle)view.selectionAction.getChildren().get(i));
            c.setFill(Color.TRANSPARENT);
            c.setOnMouseClicked(mouseEvent -> {
                c.setOnMouseClicked(null);
                c.setFill(Color.TURQUOISE);
                if (j.getActions()[0]==0) j.getActions()[0]=action;
                else j.getActions()[1]=action;
                if (j.getActions()[0]!=0 && j.getActions()[1]!=0){
                    for (int k=2; k<6; k++) {
                        view.selectionAction.getChildren().get(k).setOnMouseClicked(null);
                    }
                    ((Button)view.selectionAction.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            controleDe();
                        }
                    });
                }
            });
        }
    }

    private void activeParcelleAmenageable(int amenagement) {
        for (Node n : view.plateau.getChildren()){
            if (n instanceof VueParcelle ){
                for (Parcelle p : model.getPlateau().getParcellesSansAmenagement()){
                    if (((VueParcelle) n).getP()==p){
                        n.setOnMouseClicked(mouseEvent -> {
                            ((VueParcelle) n).addAmenagement(amenagement);
                            desactiveMouseEvent();
                            appliqueSelectionAction();
                        });
                    }
                }
            }
        }
    }

    // affiche les positions possible pour la parcelle et lorsqu'un position est selectionné ajoute la parcelle a cette endroit
    private void demandePos(Parcelle parcelle) {
        view.affichePosPossible();
        for (Node n : view.positionPossible.getChildren()){
            n.setOnMouseClicked(mouseEvent -> {
                model.getPlateau().addParcelle(parcelle, (int)n.getLayoutX(),(int)n.getLayoutY());
                view.plateau.getChildren().remove(view.positionPossible);
                view.afficheParcelle(parcelle);
                appliqueSelectionAction();
            });
        }
    }

    private void desactiveMouseEvent(){
        for (Node node : view.plateau.getChildren()) {
            if (node instanceof VueParcelle) {
                node.setOnMouseClicked(null);
            }
        }
    }

    private void supprimeAction(int i){
        view.enleveBouttonActions();
        if (j.getActions()[0]==i) j.getActions()[0]=0;
        else if (j.getActions()[1]==i) j.getActions()[1]=0;
        else j.getActions()[2]=0;

    }


    private void actionParcelle(){
        view.bParcelle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                supprimeAction(1);

                // On recupère les 3 premieres parcelles de la pile ou moins si il n'en reste plus asses
                Queue<Parcelle> pile=model.getPileParcelle();
                int max=0;
                if (pile.size()>=3) max=3;
                else max=pile.size();
                Parcelle[] parcelles= new Parcelle[max];
                for (int i=0; i<parcelles.length; i++) parcelles[i]=pile.poll();
                // On affiche la selection,
                view.affichageSelectionParcelle(parcelles);
                for (Node n : view.selectionParcelle.getChildren()){
                    //lorsque une des 3 est selectionné on renvoit les 2 autres dans la pile et on affiche les position possible pour la 3eme
                    n.setOnMouseClicked(mouseEvent -> {
                        view.plateau.getChildren().remove(view.selectionParcelle);
                        for (int i=0; i<parcelles.length; i++){
                            if (((VueParcelle)n).getP()!=parcelles[i] ){
                                pile.offer(parcelles[i]);
                            }
                            else {
                                demandePos(parcelles[i]);
                            }
                        }
                    });
                }
            }
        });
    }


    private void actionIrrigation(){
        view.irrigation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                supprimeAction(2);

                view.afficheIrrigationPossible();
                for (Node n : view.irrigationPossible.getChildren()){
                    n.setOnMouseClicked(mouseEvent -> {
                        for (LienParcelle l : model.getPlateau().getLienParcelles()){
                            if (n.getLayoutX()==l.getPos()[0] && n.getLayoutY()==l.getPos()[1]){
                                l.setIrriguee(true);
                                view.actualiseParcelle();
                            }
                        }
                        view.plateau.getChildren().remove(view.irrigationPossible);
                        view.afficheIrrigation(n);
                        Joueur j=model.getJoueurActuel();
                        j.setIrrigation(j.getIrrigation()-1);
                        appliqueSelectionAction();
                    });
                }
            }
        });
    }



    private void actionJardinier(){
        view.bJardinier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                supprimeAction(3);

                for (Node n : view.plateau.getChildren()){
                    if (n instanceof VueParcelle){
                        for (Parcelle p: model.getPlateau().getJardinier().deplacementsPossible()){
                            if (((VueParcelle)n).getP()==p){
                                n.setOnMouseClicked(mouseEvent -> {
                                    model.getPlateau().getJardinier().deplacement(p);
                                    view.deplaceJardinier((VueParcelle)n);
                                    desactiveMouseEvent();
                                    appliqueSelectionAction();
                                });
                            }
                        }
                    }
                }
            }
        });
    }


    private void actionPanda(){
        view.bPanda.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                supprimeAction(4);

                for (Node n : view.plateau.getChildren()){
                    if (n instanceof VueParcelle){
                        for (Parcelle p: model.getPlateau().getPanda().deplacementsPossible()){
                            if (((VueParcelle)n).getP()==p){
                                n.setOnMouseClicked(mouseEvent -> {
                                    model.getPlateau().getPanda().deplacement(p,model.getJoueurActuel());
                                    view.deplacePanda((VueParcelle)n);
                                    desactiveMouseEvent();
                                    appliqueSelectionAction();
                                });
                            }
                        }
                    }
                }
            }
        });
    }


    private void actionAmenagement(){
        view.bNuage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                supprimeAction(7);

                view.affichageSelectionAmenagement();
                int i=1;
                for (Node n : view.selectionAmenagement.getChildren()){
                    final int amenagement = i;
                    n.setOnMouseClicked(mouseEvent -> {
                        view.plateau.getChildren().remove(view.selectionAmenagement);
                        activeParcelleAmenageable(amenagement);
                    });
                    i++;
                }
            }
        });
    }

    private void actionPluie(){
        view.bPluie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                supprimeAction(5);

                for (Node n : view.plateau.getChildren()){
                    if (n instanceof VueParcelle){
                        VueParcelle vp=(VueParcelle)n;
                        for (Parcelle p : model.getPlateau().getParcellesIrriguees()){
                            if (p==vp.getP()){
                                vp.setOnMouseClicked(mouseEvent -> {
                                    vp.getP().pousseBambou();
                                    vp.actualiseNbBambou();
                                    appliqueSelectionAction();
                                });
                            }
                        }
                    }
                }
            }
        });
    }


    private void actionOrage(){
        view.bOrage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                supprimeAction(6);
                Panda panda=model.getPlateau().getPanda();

                for (Node n : view.plateau.getChildren()){
                    if (n instanceof VueParcelle){
                        VueParcelle vp=(VueParcelle)n;
                            if (panda.getPosition()!=vp.getP()){
                                vp.setOnMouseClicked(mouseEvent -> {
                                    panda.deplacement(vp.getP(), model.getJoueurActuel());
                                    view.deplacePanda(vp);
                                    appliqueSelectionAction();
                                });
                            }
                    }
                }
            }
        });
    }


    private void actionQuitter(){
        view.quitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
    }

    private void actionHelper(){
        view.helper.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage fenetreHelp = new Stage();
                Group group = new Group();
                Scene scene = new Scene(group,400,400);
                String aides = "";
                Label texte = new Label(aides);
                group.getChildren().add(texte);
                fenetreHelp.setScene(scene);
                fenetreHelp.show();
            }
        });
    }
}
