
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

import java.io.IOException;
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
        actionObjectifs();
        actionPluie();
        actionOrage();
        actionVerifObjectifs();
        activeSelectionAction();
        actionQuitter();
        actionHelper();
        nouvellePartie();
    }



    private void appliqueSelectionAction(){
        j=model.getJoueurActuel();
        Plateau p=model.getPlateau();
        boolean action=false;
        ((Button)view.selectionAction.getChildren().get(0)).setOnAction(null);


        for (int i=0; i<3; i++){
            if (j.getActions()[i]==1) {
                view.ajouteBouttonParcelle();
                action=true;
            }
            if (j.getActions()[i]==2) {
                j.setIrrigation(j.getIrrigation()+1);
                j.getActions()[i]=0;
            }
            if (j.getActions()[i]==3 && p.getParcelles().size()>1) {
                view.ajouteBouttonJardinier();
                action=true;
            }
            if (j.getActions()[i]==4 && p.getParcelles().size()>1) view.ajouteBouttonPanda();
            if (j.getActions()[i]==5 ){
                view.ajouteBouttonObjectifs();
                action=true;
            }
        }

        if (j.getIrrigation()>0 && p.getPositionIrrigationPossible().size()>0) view.ajouteBouttonIrrigation();

        if (j.getActions()[2] == 6 && p.getParcelles().size()>1){
            view.ajouteBouttonPluie();
            action=true;
        }
        if (j.getActions()[2] == 7 && p.getParcelles().size()>1) {
            view.ajouteBouttonOrage();
            action=true;
        }
        if (j.getActions()[2] == 8 && p.getParcellesSansAmenagement().size()>0) {
            view.ajouteBouttonNuage();
            action=true;
        }

        if ((j.getActions()[0]==0 && j.getActions()[1]==0 && j.getActions()[2]==0) || !action) {
            activeBouttonFinDeTour();
        }

    }

    private void controleDe(){
        Joueur j=model.getJoueurActuel();
        j.lanceDe();
        view.afficheDe(j.getBonusDe());
        if (j.getBonusDe()!=0) {
            if (j.getBonusDe() == 2) j.getActions()[2] = 6;
            if (j.getBonusDe() == 4) j.getActions()[2] = 7;
            if (j.getBonusDe() == 5) j.getActions()[2] = 8;
            if (j.getBonusDe() == 0) appliqueSelectionAction();
            if (j.getBonusDe() == 1) actionSoleil();
            if (j.getBonusDe() == 3) actionVent();
            if (j.getBonusDe()!=1 && j.getBonusDe()!=3) appliqueSelectionAction();
            j.setBonusDe(0);
        }
    }

    private void actionSoleil() {
        Joueur j=model.getJoueurActuel();
        for (int i=2; i<7; i++){
            if (i-1!=j.getActions()[0] && i-1!=j.getActions()[1]){
                final int action=i-1;
                Circle c = ((Circle)view.selectionAction.getChildren().get(i));
                c.setOnMouseClicked(mouseEvent -> {
                    c.setFill(Color.TURQUOISE);
                    j.getActions()[2]=action;
                    for (int k=2; k<7; k++) {
                        view.selectionAction.getChildren().get(k).setOnMouseClicked(null);
                    }
                    ((Button)view.selectionAction.getChildren().get(0)).setOnAction(actionEvent -> appliqueSelectionAction());

                });
            }

        }
    }

    private void actionVent(){
        Joueur j=model.getJoueurActuel();
        for (int i=2; i<7; i++){
            if (i-1==j.getActions()[0] || i-1==j.getActions()[1]){
                final int action=i-1;
                Circle c = ((Circle)view.selectionAction.getChildren().get(i));
                c.setOnMouseClicked(mouseEvent -> {
                    for (int k=2; k<7; k++) {
                        Circle c2 = ((Circle)view.selectionAction.getChildren().get(k));
                        c2.setOnMouseClicked(null);
                        c2.setFill(Color.TRANSPARENT);
                    }

                    c.setFill(Color.MIDNIGHTBLUE);
                    j.getActions()[0]=action;
                    j.getActions()[1]=action;
                });
            }

        }
        ((Button)view.selectionAction.getChildren().get(0)).setOnAction(actionEvent -> appliqueSelectionAction());

    }


    private void activeBouttonFinDeTour() {
        view.bFinTour.setOnAction(actionEvent -> {

            view.de.setVisible(false);
            model.nextJoueur();
            if (model.partieFini()) view.afficheGagnant();
            activeSelectionAction();
            view.enleveBouttonActions();
            view.afficheObjectifs();
            view.bFinTour.setOnAction(null);
        });
    }

    private void activeSelectionAction() {
        Joueur j=model.getJoueurActuel();
        for (int i=2; i<7; i++){
            final int action=i-1;
            Circle c = ((Circle)view.selectionAction.getChildren().get(i));
            c.setFill(Color.TRANSPARENT);
            c.setOnMouseClicked(mouseEvent -> {
                c.setOnMouseClicked(null);
                c.setFill(Color.TURQUOISE);
                if (j.getActions()[0]==0) j.getActions()[0]=action;
                else j.getActions()[1]=action;
                if (j.getActions()[0]!=0 && j.getActions()[1]!=0){
                    for (int k=2; k<7; k++) {
                        view.selectionAction.getChildren().get(k).setOnMouseClicked(null);
                    }
                    ((Button)view.selectionAction.getChildren().get(0)).setOnAction(actionEvent -> controleDe());
                }
            });
        }
    }

    private void activeParcelleAmenageable(int amenagement) {
        view.reduitOpacite();
        for (VueParcelle n : view.getVueParcelles()){
            for (Parcelle p : model.getPlateau().getParcellesSansAmenagement()){
                if ( n.getP()==p){
                    n.setOpacity(1);
                    n.setOnMouseClicked(mouseEvent -> {
                        view.remetOpacite();
                         n.addAmenagement(amenagement);
                        desactiveMouseEvent();
                        appliqueSelectionAction();
                    });
                }
            }
        }
    }

    // affiche les positions possible pour la parcelle et lorsqu'une position est selectionné ajoute la parcelle a cette endroit
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
        view.bParcelle.setOnAction(actionEvent -> {
            supprimeAction(1);

            // On recupère les 3 premieres parcelles de la pile ou moins si il n'en reste plus asses
            Queue<Parcelle> pile=model.getPileParcelle();
            int max;
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
                    for (Parcelle parcelle : parcelles) {
                        if (((VueParcelle) n).getP() != parcelle) {
                            pile.offer(parcelle);
                        } else {
                            demandePos(parcelle);
                        }
                    }
                });
            }
        });
    }


    private void actionIrrigation(){
        view.irrigation.setOnAction(actionEvent -> {
            supprimeAction(0);
            view.afficheIrrigationPossible();
            for (Node n : view.irrigationPossible.getChildren()){
                n.setOnMouseClicked(mouseEvent -> {
                    for (LienParcelle l : model.getPlateau().getLienParcelles()){
                        if (n.getLayoutX()==l.getPos()[0] && n.getLayoutY()==l.getPos()[1]){
                            l.setIrriguee();
                            view.actualiseParcelle();
                        }
                    }
                    view.plateau.getChildren().remove(view.irrigationPossible);
                    model.getJoueurActuel().setIrrigation(j.getIrrigation()-1);
                    view.afficheIrrigation(n);
                    appliqueSelectionAction();
                });
            }
        });
    }



    private void actionJardinier(){
        view.bJardinier.setOnAction(actionEvent -> {
            supprimeAction(3);
            view.reduitOpacite();

            for (VueParcelle n : view.getVueParcelles()){
                for (Parcelle p: model.getPlateau().getJardinier().deplacementsPossible()){
                    if (n.getP()==p){
                        n.setOpacity(1);
                        n.setOnMouseClicked(mouseEvent -> {
                            view.remetOpacite();
                            model.getPlateau().getJardinier().deplacement(p);
                            view.deplaceJardinier(n);
                            desactiveMouseEvent();
                            appliqueSelectionAction();
                        });
                    }
                }
            }
        });
    }


    private void actionPanda(){
        view.bPanda.setOnAction(actionEvent -> {
            supprimeAction(4);
            view.reduitOpacite();

            for (VueParcelle n : view.getVueParcelles()){
                for (Parcelle p: model.getPlateau().getPanda().deplacementsPossible()){
                    if (n.getP()==p){
                        n.setOpacity(1);
                        n.setOnMouseClicked(mouseEvent -> {
                            view.remetOpacite();
                            model.getPlateau().getPanda().deplacement(p,model.getJoueurActuel());
                            view.deplacePanda(n);
                            desactiveMouseEvent();
                            appliqueSelectionAction();
                        });
                    }
                }
            }

        });
    }


    private void actionObjectifs() {
        view.bObjectifs.setOnAction(actionEvent -> {
            supprimeAction(5);

            view.affichageSelectionObjectifs();
            int i=1;
            for (Node n : view.selectionObjectifs.getChildren()){
                final int pile = i;
                n.setOnMouseClicked(mouseEvent -> {
                    view.plateau.getChildren().remove(view.selectionAmenagement);
                    if (pile==1)  model.getJoueurActuel().piocheObjectif(model.getPileObjectifsPanda());
                    else if (pile==2) model.getJoueurActuel().piocheObjectif(model.getPileObjectifsJardinier());
                    else  model.getJoueurActuel().piocheObjectif(model.getPileObjectifsParcelle());
                    view.actualiseAjoutObjectif();
                    appliqueSelectionAction();
                });
                i++;
            }
        });
    }




    private void actionAmenagement(){
        view.bNuage.setOnAction(actionEvent -> {
            supprimeAction(8);

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
        });
    }

    private void actionPluie(){
        view.bPluie.setOnAction(actionEvent -> {
            supprimeAction(6);
            view.reduitOpacite();

            for (VueParcelle vp : view.getVueParcelles()){
                for (Parcelle p : model.getPlateau().getParcellesIrriguees()){
                    if (p==vp.getP()){
                        vp.setOpacity(1);
                        vp.setOnMouseClicked(mouseEvent -> {
                            view.remetOpacite();
                            vp.getP().pousseBambou();
                            vp.actualiseNbBambou();
                            desactiveMouseEvent();
                            appliqueSelectionAction();
                        });
                    }
                }
            }
        });
    }


    private void actionOrage(){
        view.bOrage.setOnAction(actionEvent -> {
            supprimeAction(7);
            view.reduitOpacite();
            Panda panda=model.getPlateau().getPanda();
            for (VueParcelle vp : view.getVueParcelles()){
                if (panda.getPosition()!=vp.getP()){
                    vp.setOpacity(1);
                    vp.setOnMouseClicked(mouseEvent -> {
                        view.remetOpacite();
                        panda.deplacement(vp.getP(), model.getJoueurActuel());
                        view.deplacePanda(vp);
                        appliqueSelectionAction();
                    });
                }
            }
        });
    }



    private void actionVerifObjectifs() {
        view.bVerifObjectifs.setOnAction(actionEvent -> {
            model.getJoueurActuel().verificationObjectifs(model);
            VueJoueur vj=view.vueJoueurs[model.getJoueurActuel().getNumJoueur()];
            vj.actualiseVueJoueur();
            vj.actualiseObjectifs();
            view.afficheObjectifs();
        });
    }


    private void actionQuitter(){
        view.quitter.setOnAction(event -> Platform.exit());
    }

    private void actionHelper(){
        view.helper.setOnAction(event -> {
            String fichierAOuvrir =  System.getProperty("user.dir")+"/src/image/Takenoko_rules_FR.pdf";
            String navigateur = "\"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe\"";
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(new String[]{navigateur,
                        fichierAOuvrir});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void nouvellePartie(){
        view.newPartie.setOnAction(event -> {
            Stage stage = (Stage) view.plateau.getScene().getWindow();
            RunPartie.newGame(stage, model.getNbJoueurs());
        });
    }
}
