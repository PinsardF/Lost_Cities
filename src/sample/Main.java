package sample;

import Model.Carte;
import Model.Defausse;
import Model.MainJoueur;
import Model.Pioche;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //CREATION DU BACKGROUND ET DE L'ECRAN
        Pane root = new StackPane();
        File file = new File("./src/media/background3.png");
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

        //TIRAGE DES PREMIERES CARTES
        Pioche pioche = new Pioche();
        Carte[] tirage1 = new Carte[8];
        for(int i = 0; i < 8; i++) {
            tirage1[i] = pioche.piocher();
        }
        MainJoueur mainJoueur1 = new MainJoueur(tirage1, root);
        mainJoueur1.afficherMain();

        //Index :
        //0-7 : mainJoueur
        //8-12 : Defausse rouge, verte, jaune, blanche, bleue

        Defausse defausseRouge = new Defausse(-200.0,0.0,0,root);
        Defausse defausseVerte = new Defausse(-100.0,0.0,1,root);
        Defausse defausseJaune = new Defausse(0.0,0.0,2,root);
        Defausse defausseBlanche = new Defausse(100.0,0.0,3,root);
        Defausse defausseBleue = new Defausse(200.0,0.0,4,root);
        defausseRouge.afficherDefausse();
        defausseVerte.afficherDefausse();
        defausseJaune.afficherDefausse();
        defausseBlanche.afficherDefausse();
        defausseBleue.afficherDefausse();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
