package sample;

import Model.Carte;
import Model.Defausse;
import Model.MainJoueur;
import Model.Pioche;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //CREATION DU BACKGROUND ET DE L'ECRAN
        Pane root = new StackPane();
        File file = new File("./src/media/background.png");
        Image image = new Image(file.toURI().toString());
        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        primaryStage.setTitle("Les Cités Perdues");
        primaryStage.setScene(new Scene(root, 800, 800));

        /*TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(2));
        transition.setToX(200);
        transition.setToY(200);
        transition.setNode(circle);
        transition.setAutoReverse(true);
        transition.setCycleCount(3);
        transition.play();*/

        //CREATION DES DEFAUSSES

        /*Defausse defausseRouge = new Defausse(-200.0,0.0,0,root);
        Defausse defausseVerte = new Defausse(-100.0,0.0,1,root);
        Defausse defausseJaune = new Defausse(0.0,0.0,2,root);
        Defausse defausseBlanche = new Defausse(100.0,0.0,3,root);
        Defausse defausseBleue = new Defausse(200.0,0.0,4,root);*/
        Defausse[] defausses = new Defausse[5];
        defausses[0] = new Defausse(-200.0,0.0,0,root);
        defausses[1] = new Defausse(-100.0,0.0,1,root);
        defausses[2] = new Defausse(0.0,0.0,2,root);
        defausses[3] = new Defausse(100.0,0.0,3,root);
        defausses[4] = new Defausse(200.0,0.0,4,root);
        /*for(Defausse defausse : defausses) {
            defausse.afficherDefausse();
        }*/

        //TIRAGE DES PREMIERES CARTES
        Pioche pioche = new Pioche();
        Carte[] tirage1 = new Carte[8];
        for(int i = 0; i < 8; i++) {
            tirage1[i] = pioche.piocher();
        }
        MainJoueur mainJoueur1 = new MainJoueur(tirage1, root);
        mainJoueur1.afficherMain();

        //CHARGEMENT DES CURSEURS
        ImageView curseur1 = new ImageView();
        root.getChildren().add(curseur1);
        ImageView curseur2 = new ImageView();
        root.getChildren().add(curseur2);

        //MISE EN PLACE DE L'EVENEMENT "CLIQUER SUR UNE CARTE"
        for(int i = 5; i < 13; i++) {
            final int indice = i;
            ((ImageView) root.getChildren().get(i)).setOnMouseClicked(event -> {
                switch(event.getButton().name()) {
                    case "PRIMARY":
                        if(mainJoueur1.getCarteSelectionneeId() > -1) {
                            if(mainJoueur1.getCarteSelectionneeId() !=
                                    Integer.parseInt(((ImageView) event.getSource()).getId().substring(10,11))) {
                                //CAS : Carte déjà cliquée, nouvelle carte cliquée
                                //On remet la précédente dans la main
                                ((ImageView) root.getChildren().get(5 + mainJoueur1.getCarteSelectionneeId())).setTranslateY(350.0);
                            }
                        }
                        //Et on sort la nouvelle
                        ((ImageView) root.getChildren().get((Integer.parseInt(((ImageView) event.getSource()).getId().substring(10,11)))+5)).setTranslateY(300.0);

                        Carte carte;
                        int couleur;
                        double x, y;
                        carte = mainJoueur1.getCarte(Integer.parseInt(((ImageView) root.getChildren().get(indice)).getId().substring(10,11)));
                        couleur = carte.getId_couleur();
                        x = root.getChildren().get(couleur).getTranslateX();
                        y = root.getChildren().get(couleur).getTranslateY();
                        ((ImageView) root.getChildren().get(13)).setTranslateX(x);
                        ((ImageView) root.getChildren().get(13)).setTranslateY(y);
                        File carteFile = new File("./src/media/curseur.png");
                        Image carteImage = new Image(carteFile.toURI().toString());
                        ((ImageView) root.getChildren().get(13)).setImage(carteImage);
                        //On met mainJoueur1.carteSeléctionnée à la bonne valeur
                        mainJoueur1.setCarteSelectionnee(Integer.parseInt(((ImageView) event.getSource()).getId().substring(10,11)));
                        break;
                    case "SECONDARY" :
                        File carteFileSecondary = new File("./src/media/vide.png");
                        Image carteImageSecondary = new Image(carteFileSecondary.toURI().toString());
                        ((ImageView) root.getChildren().get(13)).setImage(carteImageSecondary);
                        ((ImageView) root.getChildren().get(5 + mainJoueur1.getCarteSelectionneeId())).setTranslateY(350.0);
                        mainJoueur1.setCarteSelectionnee(-1);
                        break;
                }
            });
        }

        //MISE EN PLACE DE L'EVENEMENT "CLIQUER SUR UNE DEFAUSSE"/
        for(int i = 0; i < 5; i++) {
            final int indice = i;
            ((ImageView) root.getChildren().get(indice)).setOnMouseClicked(event -> {
                //System.out.println(event.getSource());
                if(mainJoueur1.getCarteSelectionnee().getId_couleur() ==
                        Integer.parseInt(((ImageView) event.getSource()).getId().substring(8,9))) {
                    System.out.println("Carte déplacée");
                }
            });
        }
        //Index :
        //0-4 : Défausses rouge, verte, jaune, blanche, bleue
        //5-12 : MainJoueur
        //13-14 : curseurs

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
