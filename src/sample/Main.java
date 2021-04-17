package sample;

import Model.*;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Création du Background et de l'écran
        Pane root = new StackPane();
        File file = new File("./src/media/background.png");
        Image image = new Image(file.toURI().toString());
        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        primaryStage.setTitle("Les Cités Perdues");
        primaryStage.setScene(new Scene(root, 800, 800));

        //BLOC TRANSITION
        /*File filefile = new File("./src/media/2jaune.png");
        Image testImage = new Image(filefile.toURI().toString());
        ImageView imageView = new ImageView(testImage);
        root.getChildren().add(imageView);
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(2));
        transition.setToX(200);
        transition.setToY(200);
        transition.setNode(imageView);
        transition.setAutoReverse(true);
        transition.setCycleCount(3);
        transition.play();*/

        //Création des Défausses
        Defausse[] defausses = new Defausse[5];
        defausses[0] = new Defausse(-200.0,0.0,0,root);
        defausses[1] = new Defausse(-100.0,0.0,1,root);
        defausses[1].ajouterCarte(new Carte(3,"vert"));
        defausses[1].afficherDefausse();
        defausses[2] = new Defausse(0.0,0.0,2,root);
        defausses[3] = new Defausse(100.0,0.0,3,root);
        defausses[4] = new Defausse(200.0,0.0,4,root);

        //Création de la pioche et tirage des premières cartes
        Pioche pioche = new Pioche(root);
        Carte[] tirage1 = new Carte[8];
        for(int i = 0; i < 8; i++) {
            tirage1[i] = pioche.piocher();
        }
        MainJoueur mainJoueur1 = new MainJoueur(tirage1, root);
        mainJoueur1.trier();
        mainJoueur1.afficherMain();

        //Chargement des curseurs
        Curseur[] curseursDefausse = new Curseur[6];
        for(int i = 0;i < 6; i++) {
            curseursDefausse[i] = new Curseur(root, i);
        }

        //Mise en place de l'événement "Cliquer sur une Défausse"
        for(int i = 0; i < 5; i++) {
            final int indice = i;
            ((ImageView) root.getChildren().get(indice)).setOnMouseClicked(event -> {
                //Si on est en phase "placer une carte"
                if(mainJoueur1.getCarteSelectionneeId() > -1
                        && mainJoueur1.getEtat() == "pose" && mainJoueur1.getCarteSelectionnee().getId_couleur() ==
                        Integer.parseInt(((ImageView) event.getSource()).getId().substring(8,9))) {
                    //On récupère l'image de la carte
                    Image carteImage = (mainJoueur1.getCarteSelectionnee().getImage());
                    //On ajoute la carte à la défausse
                    defausses[indice].ajouterCarte(mainJoueur1.getCarteSelectionnee());
                    //On retire la carte de la main
                    mainJoueur1.supprimerCarte(mainJoueur1.getCarteSelectionneeId());
                    //On fait disparaître la carte de la main
                    File carteFileVide = new File("./src/media/vide.png");
                    Image imageVide = new Image(carteFileVide.toURI().toString());
                    ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+6)).setImage(imageVide);
                    mainJoueur1.afficherMain();
                    //On retire le curseur
                    curseursDefausse[0].disparaitre(root);
                    //On déplace la carte (Transition)
                    TranslateTransition carteTransition = new TranslateTransition();
                    carteTransition.setDuration(Duration.seconds(0.4));
                    carteTransition.setToX(defausses[indice].getPositionX());
                    carteTransition.setToY(defausses[indice].getPositionY());
                    double x = ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+6)).getTranslateX();
                    double y = ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+6)).getTranslateY();
                    ((ImageView) root.getChildren().get(14)).setTranslateX(x);
                    ((ImageView) root.getChildren().get(14)).setTranslateY(y);
                    ((ImageView) root.getChildren().get(14)).setImage(carteImage);
                    carteTransition.setNode((ImageView) root.getChildren().get(14));
                    carteTransition.setOnFinished(transitionEvent -> {
                        curseursDefausse[0].disparaitre(root);
                        defausses[indice].afficherDefausse();
                        afficherCurseurs(root, defausses, curseursDefausse);
                        mainJoueur1.setEtat("pioche");
                    });
                    carteTransition.play();
                //Si on est en phase "piocher une carte"
                } else if(mainJoueur1.getEtat() == "pioche" && !defausses[indice].estVide()) {
                    //On récupère l'emplacement de la carte
                    double defausseX = defausses[indice].getPositionX();
                    double defausseY = defausses[indice].getPositionY();
                    Carte carteDeplacee = defausses[indice].piocher();
                    piocherCarte(root, carteDeplacee, mainJoueur1, defausseX, defausseY);
                    defausses[indice].afficherDefausse();
                    File carteFileVide = new File("./src/media/vide.png");
                    Image imageVide = new Image(carteFileVide.toURI().toString());
                    for (int j = 0 ; j < 6 ; j++) {
                        ((ImageView) root.getChildren().get(j + 14)).setImage(imageVide);
                    }
                }
            });
        }

        //Mise en place de l'événement "Cliquer sur une carte"
        for(int i = 6; i < 14; i++) {
            final int indice = i;
            ((ImageView) root.getChildren().get(i)).setOnMouseClicked(event -> {
                //Si on est en phase "placer une carte"
                if(mainJoueur1.getEtat() == "pose") {
                    switch (event.getButton().name()) {
                        //CAS : Clic gauche sur une carte
                        case "PRIMARY":
                            //CAS : Une carte a déjà été cliquée précédemment
                            if (mainJoueur1.getCarteSelectionneeId() > -1) {
                                //CAS : La carte cliquée n'est pas la même que la précédente
                                if (mainJoueur1.getCarteSelectionneeId() !=
                                        Integer.parseInt(((ImageView) event.getSource()).getId().substring(10, 11))) {
                                    //On remet la carte précédente dans la main
                                    ((ImageView) root.getChildren().get(6 + mainJoueur1.getCarteSelectionneeId())).setTranslateY(350.0);
                                }
                            }
                            //Et on sort la nouvelle
                            ((ImageView) root.getChildren().get((Integer.parseInt(((ImageView) event.getSource()).getId().substring(10, 11))) + 6)).setTranslateY(300.0);

                            //On récupère la couleur de la carte cliquée
                            Carte carte;
                            int couleur;
                            double x, y;
                            carte = mainJoueur1.getCarte(Integer.parseInt(((ImageView) root.getChildren().get(indice)).getId().substring(10, 11)));
                            couleur = carte.getId_couleur();
                            //On place le curseur sur la bonne Défausse
                            curseursDefausse[0].afficher(root, root.getChildren().get(couleur).getTranslateX(), root.getChildren().get(couleur).getTranslateY());
                            //On met mainJoueur1.carteSeléctionnéeId à la bonne valeur
                            mainJoueur1.setCarteSelectionnee(Integer.parseInt(((ImageView) event.getSource()).getId().substring(10, 11)));
                            break;

                        //CAS : Clic droit sur une carte
                        case "SECONDARY":
                            //On annule :  on retire le curseur
                            curseursDefausse[0].disparaitre(root);
                            //Et on rentre la carte précédemment cliquée
                            ((ImageView) root.getChildren().get(6 + mainJoueur1.getCarteSelectionneeId())).setTranslateY(350.0);
                            mainJoueur1.setCarteSelectionnee(-1);
                            break;
                    }
                }
            });
        }

        ImageView transitionImage = new ImageView();
        transitionImage.setId("transition");
        /*File a = new File("./src/media/3jaune.png");
        Image b = new Image(a.toURI().toString());
        transitionImage.setImage(b);
        transitionImage.setTranslateX(0);
        transitionImage.setTranslateY(0);*/
        root.getChildren().add(transitionImage);

        //Mise en place de l'événement "Cliquer sur la pioche"
        ((ImageView) root.getChildren().get(5)).setOnMouseClicked(event -> {
            if (mainJoueur1.getEtat() == "pioche") {
                pioche.etat();
            }
        });

        //Index :
        //0-4 : Défausses rouge, verte, jaune, blanche, bleue
        //5 : Pioche
        //6-13 : MainJoueur
        //14-19 : curseurs
        //20 : Transition

        //Etats de la main :
        //"pose"
        //"pioche"
        //"fin du tour" (déplacement des cartes dans la main)

        primaryStage.show();
    }

    public void afficherCurseurs(Pane root, Defausse[] defausses, Curseur[] curseurs) {
        for(int i = 0; i < 5; i++) {
            if(!defausses[i].estVide()) {
                curseurs[i].afficher(root, 100*i - 200, 0);
            }
        }
        curseurs[0].afficher(root, -330, 0);
    }

    public void piocherCarte(Pane root, Carte carteDeplacee, MainJoueur mainJoueur, double defausseX, double defausseY) {
        int indiceCarte = mainJoueur.ajouterCarte(carteDeplacee);
        if (indiceCarte != mainJoueur.getCarteSelectionneeId()) {
            List<Integer> cartesADeplacer = new ArrayList<>();
            int X = 0;
            //Si la nouvelle carte est à mettre à gauche du trou...
            if (indiceCarte < mainJoueur.getCarteSelectionneeId()) {
                X = 68;
                //On choisit les cartes à déplacer
                for (int j = indiceCarte ; j < mainJoueur.getCarteSelectionneeId() ; j++) {
                    cartesADeplacer.add(j);
                }
                //Si elle doit être mise à droite du trou...
            } else if (indiceCarte > mainJoueur.getCarteSelectionneeId()) {
                X = -68;
                //On choisit les cartes à déplacer
                for (int j = mainJoueur.getCarteSelectionneeId() + 1 ; j < indiceCarte + 1 ; j++) {
                    cartesADeplacer.add(j);
                }
            }
            //Maintenant on les déplace
            for (int k = 0 ; k < cartesADeplacer.size() ; k++) {
                TranslateTransition mainTransition = new TranslateTransition();
                mainTransition.setDuration(Duration.seconds(0.2));
                mainTransition.setByX(X);
                mainTransition.setNode(root.getChildren().get(cartesADeplacer.get(k) + 6));
                mainTransition.play();
            }
        }
        //On déplace la carte
        TranslateTransition carteTransition = new TranslateTransition();
        carteTransition.setDuration(Duration.seconds(0.4));
        //On définit l'emplacement de départ...
        ((ImageView) root.getChildren().get(20)).setTranslateX(defausseX);
        ((ImageView) root.getChildren().get(20)).setTranslateY(defausseY);
        //Et l'emplacement d'arrivée
        carteTransition.setToX(root.getChildren().get(indiceCarte+6).getTranslateX());
        carteTransition.setToY(350);
        //On met l'image de la carte sur le node "transition"
        ((ImageView) root.getChildren().get(20)).setImage(carteDeplacee.getImage());
        carteTransition.setNode(root.getChildren().get(20));
        carteTransition.play();
        mainJoueur.resetCarteSelectionee();
        mainJoueur.setEtat("fin du tour");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
