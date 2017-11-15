
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

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

        activeBouttonsActions();

        actionQuitter();
        actionHelper();
    }

    private void activeBouttonsActions() {
        j=model.getJoueurActuel();
        if (j.getAction1()==0 && j.getAction2()==0) activeSelectionAction();
        if (j.getAction1()==1 || j.getAction2()==1) view.ajouteBouttonParcelle();
        if (j.getAction1()==2 || j.getAction2()==2) view.ajouteBouttonIrrigation();
        if (j.getAction1()==3 || j.getAction2()==3) view.ajouteBouttonJardinier();
        if (j.getAction1()==4 || j.getAction2()==4) view.ajouteBouttonPanda();
    }

    private void activeSelectionAction() {
        for (int i=1; i<5; i++){
            final int action=i;
            Circle c = ((Circle)view.selectionAction.getChildren().get(i));
            c.setFill(Color.TRANSPARENT);
            c.setOnMouseClicked(mouseEvent -> {
                c.setOnMouseClicked(null);
                c.setFill(Color.TURQUOISE);
                if (j.getAction1()==0) j.setAction1(action);
                else j.setAction2(action);
                if (j.getAction1()!=0 && j.getAction2()!=0){
                    for (int k=1; k<5; k++) {
                        view.selectionAction.getChildren().get(k).setOnMouseClicked(null);
                    }
                    ((Button)view.selectionAction.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            activeBouttonsActions();
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
                activeBouttonsActions();
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
        if (j.getAction1()==i) j.setAction1(0);
        else if (j.getAction2()==i) j.setAction2(0);

    }


    private void actionParcelle(){
        view.button.setOnAction(new EventHandler<ActionEvent>() {
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
                        activeBouttonsActions();
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
                                    activeBouttonsActions();
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
                                    model.getPlateau().getPanda().deplacement(p);
                                    view.deplacePanda((VueParcelle)n);
                                    desactiveMouseEvent();
                                    activeBouttonsActions();
                                });
                            }
                        }
                    }
                }
            }
        });
    }


    private void actionAmenagement(){
        view.bAmenagement.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
