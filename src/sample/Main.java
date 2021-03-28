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

        //Tirage des premières cartes
        Pioche pioche = new Pioche();
        Carte[] tirage1 = new Carte[8];
        for(int i = 0; i < 8; i++) {
            tirage1[i] = pioche.piocher();
        }
        MainJoueur mainJoueur1 = new MainJoueur(tirage1, root);
        mainJoueur1.trier();
        mainJoueur1.afficherMain();

        //Chargement des curseurs
        Curseur[] curseursDefausse = new Curseur[5];
        for(int i = 0;i < 5; i++) {
            curseursDefausse[i] = new Curseur(root, i);
        }
        Curseur curseurPioche = new Curseur(root, 5);

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
                    ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+5)).setImage(imageVide);
                    mainJoueur1.afficherMain();
                    //On retire le curseur
                    curseursDefausse[0].disparaitre(root);
                    //On déplace la carte (Transition)
                    TranslateTransition carteTransition = new TranslateTransition();
                    carteTransition.setDuration(Duration.seconds(0.4));
                    carteTransition.setToX(defausses[indice].getPositionX());
                    carteTransition.setToY(defausses[indice].getPositionY());
                    double x = ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+5)).getTranslateX();
                    double y = ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+5)).getTranslateY();
                    ((ImageView) root.getChildren().get(13)).setTranslateX(x);
                    ((ImageView) root.getChildren().get(13)).setTranslateY(y);
                    ((ImageView) root.getChildren().get(13)).setImage(carteImage);
                    carteTransition.setNode((ImageView) root.getChildren().get(13));
                    carteTransition.setOnFinished(transitionEvent -> {
                        curseursDefausse[0].disparaitre(root);
                        defausses[indice].afficherDefausse();
                        afficherCurseurs(root, defausses, curseursDefausse);
                        mainJoueur1.setEtat("pioche");
                    });
                    carteTransition.play();
                //Si on est en phase "piocher une carte"
                } else if(mainJoueur1.getEtat() == "pioche" && !defausses[indice].estVide()) {
                    //On récupère l'image de la carte
                    Carte carteDeplacee = defausses[indice].piocher();
                    defausses[indice].afficherDefausse();
                    //On retire le curseur
                    curseursDefausse[indice].disparaitre(root);
                    //Si besoin on déplace des cartes dans la main pour accueillir la nouvelle
                    int indiceCarte = mainJoueur1.ajouterCarte(carteDeplacee);
                    if (indiceCarte != mainJoueur1.getCarteSelectionneeId()) {
                        List<Integer> cartesADeplacer = new ArrayList<>();
                        int X = 0;
                        //Si la nouvelle carte est à mettre à gauche du trou...
                        if (indiceCarte < mainJoueur1.getCarteSelectionneeId()) {
                            X = 68;
                            //On choisit les cartes à déplacer
                            for (int j = indiceCarte ; j < mainJoueur1.getCarteSelectionneeId() ; j++) {
                                cartesADeplacer.add(j);
                            }
                        //Si elle doit être mise à droite du trou...
                        } else if (indiceCarte > mainJoueur1.getCarteSelectionneeId()) {
                            X = -68;
                            //On choisit les cartes à déplacer
                            for (int j = mainJoueur1.getCarteSelectionneeId() + 1 ; j < indiceCarte + 1 ; j++) {
                                cartesADeplacer.add(j);
                            }
                        }
                        //Maintenant on les déplace
                        for (int k = 0 ; k < cartesADeplacer.size() ; k++) {
                            TranslateTransition mainTransition = new TranslateTransition();
                            mainTransition.setDuration(Duration.seconds(0.2));
                            mainTransition.setByX(X);
                            mainTransition.setNode(root.getChildren().get(cartesADeplacer.get(k) + 5));
                            mainTransition.play();
                        }
                    }
                    //On déplace la carte (Transition)
                    TranslateTransition carteTransition = new TranslateTransition();
                    carteTransition.setDuration(Duration.seconds(0.4));
                    double x = defausses[indice].getPositionX();
                    double y = defausses[indice].getPositionY();
                    ((ImageView) root.getChildren().get(19)).setTranslateX(x);
                    ((ImageView) root.getChildren().get(19)).setTranslateY(y);
                    carteTransition.setToX(root.getChildren().get(indiceCarte+5).getTranslateX());
                    carteTransition.setToY(350);
                    ((ImageView) root.getChildren().get(19)).setImage(carteDeplacee.getImage());
                    carteTransition.setNode(root.getChildren().get(19));
                    carteTransition.play();
                    deplacerCartes(root, mainJoueur1, mainJoueur1.getCarteSelectionneeId(), indiceCarte);
                    mainJoueur1.resetCarteSelectionee();
                    mainJoueur1.setEtat("fin du tour");
                    //On retire les curseurs des défausses
                    File carteFileVide = new File("./src/media/vide.png");
                    Image imageVide = new Image(carteFileVide.toURI().toString());
                    for (int j = 0 ; j < 5 ; j++) {
                        ((ImageView) root.getChildren().get(j + 13)).setImage(imageVide);
                    }
                }
            });
        }

        //Mise en place de l'événement "Cliquer sur une carte"
        for(int i = 5; i < 13; i++) {
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
                                    ((ImageView) root.getChildren().get(5 + mainJoueur1.getCarteSelectionneeId())).setTranslateY(350.0);
                                }
                            }
                            //Et on sort la nouvelle
                            ((ImageView) root.getChildren().get((Integer.parseInt(((ImageView) event.getSource()).getId().substring(10, 11))) + 5)).setTranslateY(300.0);

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
                            ((ImageView) root.getChildren().get(5 + mainJoueur1.getCarteSelectionneeId())).setTranslateY(350.0);
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

        //Création de la pioche (visuellement)
        ImageView piocheImageView = new ImageView();
        File piocheFile = new File("./src/media/pioche.png");
        Image piocheImage = new Image(piocheFile.toURI().toString());
        piocheImageView.setImage(piocheImage);
        piocheImageView.setTranslateX(-330);
        piocheImageView.setTranslateY(0);
        piocheImageView.setId("pioche");
        root.getChildren().add(piocheImageView);

        //Mise en place de l'événement "Cliquer sur la pioche"
        ((ImageView) root.getChildren().get(20)).setOnMouseClicked(event -> {
            if (mainJoueur1.getEtat() == "pioche") {
                System.out.println("Pioche cliquée");
            }
        });

        //Index :
        //0-4 : Défausses rouge, verte, jaune, blanche, bleue
        //5-12 : MainJoueur
        //13-18 : curseurs
        //19 : Transition
        //20 : Pioche

        //Etats de la main :
        //"pose"
        //"pioche"
        //"fin du tour" (déplacement des cartes dans la main)

        //PRHOCHAINE ETAPE : piocher une carte /!\

        primaryStage.show();
    }

    public void afficherCurseurs(Pane root, Defausse[] defausses, Curseur[] curseurs) {
        for(int i = 0; i < 5; i++) {
            if(!defausses[i].estVide()) {
                curseurs[i].afficher(root, 100*i - 200, 0);
            }
        }
    }

    public void deplacerCartes(Pane root, MainJoueur mainJoueur, int indiceCarteJouee, int indiceNouvelleCarte) {
        //if(indiceCarteJouee < )
    }

    public static void main(String[] args) {
        launch(args);
    }
}
