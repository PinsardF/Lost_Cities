package sample;

import Model.*;
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

        //Création des colonnes
        Colonne[] colonnes = new Colonne[5];
        for (int i = 0; i < 5; i++) {
            colonnes[i] = new Colonne((100*i)-200, 180, i, root);
        }

        //Création des scores
        Score[] scores = new Score[5];
        for (int i = 0; i < 5; i++) {
            scores[i] = new Score((100*i)-200,250,i,root);
        }

        //Création des Défausses
        Defausse[] defausses = new Defausse[5];
        defausses[0] = new Defausse(-200.0,0.0,0,root);
        defausses[1] = new Defausse(-100.0,0.0,1,root);
        defausses[2] = new Defausse(0.0,0.0,2,root);
        defausses[3] = new Defausse(100.0,0.0,3,root);
        defausses[4] = new Defausse(200.0,0.0,4,root);
        defausses[2].ajouterCarte(new Carte(5,"jaune"));
        defausses[2].afficherDefausse();

        //Création de la pioche et tirage des premières cartes
        Pioche pioche = new Pioche(root, -330, 0);
        //BON
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

        //Mise en place de l'événement "Cliquer sur une Colonne"
        for (int i = 0; i < 5; i++) {
            final int indice = i;
            ((ImageView) root.getChildren().get(indice)).setOnMouseClicked(event -> {
                //Si on est en phase "placer une carte"
                if (mainJoueur1.getCarteSelectionneeId() > -1 && mainJoueur1.getEtat() == "pose" &&
                        mainJoueur1.getCarteSelectionnee().getId_couleur() ==
                        Integer.parseInt(((ImageView) event.getSource()).getId().substring(7,8)) &&
                        mainJoueur1.getCarteSelectionnee().getValeur() >= colonnes[indice].getDerniereValeur()) {
                    //On ajoute la carte à la colonne
                    Carte carteSelectionnee = mainJoueur1.getCarteSelectionnee();
                    colonnes[indice].ajouterCarte(carteSelectionnee);
                    //On met à jour le score
                    scores[indice].updateScore(colonnes[indice].getCartes());
                    //On retire la carte de la main
                    mainJoueur1.supprimerCarte(mainJoueur1.getCarteSelectionneeId());
                    //On fait disparaître la carte de la main
                    File carteFileVide = new File("./src/media/vide.png");
                    Image imageVide = new Image(carteFileVide.toURI().toString());
                    ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+16)).setImage(imageVide);
                    mainJoueur1.afficherMain();
                    //On retire les curseurs
                    curseursDefausse[0].disparaitre(root);
                    curseursDefausse[1].disparaitre(root);
                    //On déplace la carte (Transition)
                    TranslateTransition carteTransition = new TranslateTransition();
                    carteTransition.setDuration(Duration.seconds(0.4));
                    carteTransition.setToX(colonnes[indice].getPositionX());
                    carteTransition.setToY(colonnes[indice].getPositionY());
                    double x = ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+16)).getTranslateX();
                    double y = ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+16)).getTranslateY();
                    ((ImageView) root.getChildren().get(24)).setTranslateX(x);
                    ((ImageView) root.getChildren().get(24)).setTranslateY(y);
                    ((ImageView) root.getChildren().get(24)).setImage(carteSelectionnee.getImage());
                    carteTransition.setNode((ImageView) root.getChildren().get(24));
                    carteTransition.setOnFinished(transitionEvent -> {
                        curseursDefausse[0].disparaitre(root);
                        colonnes[indice].afficherColonne();
                        afficherCurseurs(root, defausses, curseursDefausse);
                        mainJoueur1.setEtat("pioche");
                    });
                    carteTransition.play();
                }
            });
        }

        //Mise en place de l'événement "Cliquer sur une Défausse"
        for(int i = 0; i < 5; i++) {
            final int indice = i;
            ((ImageView) root.getChildren().get(indice+10)).setOnMouseClicked(event -> {
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
                    ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+16)).setImage(imageVide);
                    mainJoueur1.afficherMain();
                    //On retire les curseurs
                    curseursDefausse[0].disparaitre(root);
                    curseursDefausse[1].disparaitre(root);
                    //On déplace la carte (Transition)
                    TranslateTransition carteTransition = new TranslateTransition();
                    carteTransition.setDuration(Duration.seconds(0.4));
                    carteTransition.setToX(defausses[indice].getPositionX());
                    carteTransition.setToY(defausses[indice].getPositionY());
                    //System.out.println(mainJoueur1.getCarteSelectionneeId());
                    double x = ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+16)).getTranslateX();
                    double y = ((ImageView) root.getChildren().get(mainJoueur1.getCarteSelectionneeId()+16)).getTranslateY();
                    ((ImageView) root.getChildren().get(24)).setTranslateX(x);
                    ((ImageView) root.getChildren().get(24)).setTranslateY(y);
                    ((ImageView) root.getChildren().get(24)).setImage(carteImage);
                    carteTransition.setNode((ImageView) root.getChildren().get(24));
                    carteTransition.setOnFinished(transitionEvent -> {
                        curseursDefausse[0].disparaitre(root);
                        defausses[indice].afficherDefausse();
                        afficherCurseurs(root, defausses, curseursDefausse);
                        mainJoueur1.setEtat("pioche");
                    });
                    carteTransition.play();
                //Si on est en phase "piocher une carte"
                } else if(mainJoueur1.getEtat() == "pioche" && !defausses[indice].estVide()) {
                    mainJoueur1.setEtat("fin du tour");
                    //On récupère l'emplacement de la carte
                    double defausseX = defausses[indice].getPositionX();
                    double defausseY = defausses[indice].getPositionY();
                    Carte carteDeplacee = defausses[indice].piocher();
                    //On déplace la carte
                    piocherCarte(root, carteDeplacee, mainJoueur1, defausseX, defausseY);
                    defausses[indice].afficherDefausse();

                    //On ajoute la carte à mainJoueur1

                    //On supprime les curseur
                    File carteFileVide = new File("./src/media/vide.png");
                    Image imageVide = new Image(carteFileVide.toURI().toString());
                    for (int j = 0 ; j < 6 ; j++) {
                        ((ImageView) root.getChildren().get(j + 24)).setImage(imageVide);
                    }
                    mainJoueur1.setEtat("pose");
                }
            });
        }

        //Mise en place de l'événement "Cliquer sur une carte"
        for(int i = 6; i < 14; i++) {
            final int indice = i;
            ((ImageView) root.getChildren().get(i+10)).setOnMouseClicked(event -> {
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
                                    ((ImageView) root.getChildren().get(16 + mainJoueur1.getCarteSelectionneeId())).setTranslateY(350.0);
                                }
                            }
                            //Et on sort la nouvelle
                            ((ImageView) root.getChildren().get((Integer.parseInt(((ImageView) event.getSource()).getId().substring(10, 11))) + 16)).setTranslateY(300.0);

                            //On récupère la couleur de la carte cliquée
                            Carte carte;
                            int couleur;
                            double x, y;
                            carte = mainJoueur1.getCarte(Integer.parseInt(((ImageView) root.getChildren().get(indice+10)).getId().substring(10, 11)));
                            couleur = carte.getId_couleur();
                            //On retire les curseurs
                            curseursDefausse[0].disparaitre(root);
                            curseursDefausse[1].disparaitre(root);
                            //On place le curseur sur la bonne Défausse
                            curseursDefausse[0].afficher(root, root.getChildren().get(couleur+10).getTranslateX(), root.getChildren().get(couleur+10).getTranslateY());
                            //On place un curseur sur la bonne colonne
                            if (carte.getValeur() >= colonnes[carte.getId_couleur()].getDerniereValeur()) {
                                curseursDefausse[1].afficher(root, root.getChildren().get(couleur).getTranslateX(), root.getChildren().get(couleur).getTranslateY());
                            }
                            //On met mainJoueur1.carteSeléctionnéeId à la bonne valeur
                            mainJoueur1.setCarteSelectionnee(Integer.parseInt(((ImageView) event.getSource()).getId().substring(10, 11)));
                            break;

                        //CAS : Clic droit sur une carte
                        case "SECONDARY":
                            if (mainJoueur1.getCarteSelectionneeId() != -1) {
                                //On annule :  on retire le curseur
                                curseursDefausse[0].disparaitre(root);
                                curseursDefausse[1].disparaitre(root);
                                //Et on rentre la carte précédemment cliquée
                                ((ImageView) root.getChildren().get(16 + mainJoueur1.getCarteSelectionneeId())).setTranslateY(350.0);
                                mainJoueur1.setCarteSelectionnee(-1);
                            }
                            break;
                    }
                }
            });
        }

        //Création du node transition
        ImageView transitionImage = new ImageView();
        transitionImage.setId("transition");
        root.getChildren().add(transitionImage);

        //Mise en place de l'événement "Cliquer sur la pioche"
        ((ImageView) root.getChildren().get(15)).setOnMouseClicked(event -> {
            if (mainJoueur1.getEtat() == "pioche") {
                mainJoueur1.setEtat("fin du tour");
                Carte cartePiochée = pioche.piocher();
                piocherCarte(root, cartePiochée, mainJoueur1, pioche.getPositionX(), pioche.getPositionY());
                File carteFileVide = new File("./src/media/vide.png");
                Image imageVide = new Image(carteFileVide.toURI().toString());
                for (int j = 0 ; j < 6 ; j++) {
                    ((ImageView) root.getChildren().get(j + 24)).setImage(imageVide);
                }
                mainJoueur1.setEtat("pose");
                //pioche.etat();
            }
        });



        //Index :
        //0-4 : Colonnes
        //5-9 : Scores (TextViews)
        //10-14 : Défausses rouge, verte, jaune, blanche, bleue
        //15 : Pioche
        //16-23 : MainJoueur
        //24-29 : curseurs
        //30 : Transition

        //0-4 : Défausses rouge, verte, jaune, blanche, bleue
        //5 : Pioche
        //6-13 : MainJoueur
        //14-19 : curseurs
        //20 : Transition

        //Etats de la main :
        //"pose"
        //"pioche"
        //"fin du tour" (déplacement des cartes dans la main)

        //PROCHAINE ETAPE : Gestion des colonnes

        primaryStage.show();
    }

    public void afficherCurseurs(Pane root, Defausse[] defausses, Curseur[] curseurs) {
        for(int i = 0; i < 5; i++) {
            if(!defausses[i].estVide()) {
                curseurs[i].afficher(root, 100*i - 200, 0);
            }
        }
        curseurs[5].afficher(root, -330, 0);
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

            /*
            System.out.println("MAINJOUEUR AVANT");
            for (Carte carte : mainJoueur.getCartes()) {
                System.out.println(carte.getValeur()+carte.getCouleur());
            }
            */

            Carte[] nouvelleMain = new Carte[8];
            for (int h = 0; h < 8; h++) {
                nouvelleMain[h] = mainJoueur.getCarte(h);
            }
            //System.out.println(indiceCarte);

            //Maintenant on les déplace (visuellement et dans nouvelleMain)
            for (int k = 0 ; k < cartesADeplacer.size() ; k++) {
                //System.out.println(cartesADeplacer.get(k));

                nouvelleMain[cartesADeplacer.get(k) + (X / 68)] = mainJoueur.getCarte(cartesADeplacer.get(k));

                TranslateTransition mainTransition = new TranslateTransition();
                mainTransition.setDuration(Duration.seconds(0.2));
                mainTransition.setByX(X);
                mainTransition.setNode(root.getChildren().get(cartesADeplacer.get(k) + 16));
                mainTransition.play();
            }
            //On ajoute à la bonne place la carte piochée
            nouvelleMain[indiceCarte] = carteDeplacee;
            //On met mainJoueur1.cartes à jour
            mainJoueur.setCartes(nouvelleMain);
        } else {
            //On ajoute à la bonne place la carte piochée
            mainJoueur.setCarte(carteDeplacee, indiceCarte);
        }
        //On déplace la carte
        TranslateTransition carteTransition = new TranslateTransition();
        carteTransition.setDuration(Duration.seconds(0.4));
        //On définit l'emplacement de départ...
        ((ImageView) root.getChildren().get(30)).setTranslateX(defausseX);
        ((ImageView) root.getChildren().get(30)).setTranslateY(defausseY);
        //Et l'emplacement d'arrivée
        carteTransition.setToX(root.getChildren().get(indiceCarte+16).getTranslateX());
        carteTransition.setToY(350);
        //On met l'image de la carte sur le node "transition"
        ((ImageView) root.getChildren().get(30)).setImage(carteDeplacee.getImage());
        carteTransition.setNode(root.getChildren().get(30));
        carteTransition.setOnFinished(transitionEvent -> {
            //On retire le node transition
            root.getChildren().get(30).setTranslateY(-150);
            File carteFileVide = new File("./src/media/vide.png");
            Image imageVide = new Image(carteFileVide.toURI().toString());
            ((ImageView) root.getChildren().get(30)).setImage(imageVide);
            //On remet à zéro l'id de la carte sélectionnée de mainJoueur1
            mainJoueur.resetCarteSelectionee();

            //Coucou Sako, si tu es un vrai stalker légendaire tu verras ça
            mainJoueur.afficherMain();

            /*
            for (int c = 6; c < 14; c++) {
                System.out.println(c+":"+root.getChildren().get(c+10).getTranslateX());
            }
            System.out.println(20+":"+root.getChildren().get(30).getTranslateX());
            */

        });
        carteTransition.play();
        mainJoueur.setEtat("fin du tour");

        /*
        System.out.println("MAINJOUEUR FIN PIOCHER");
        for (Carte carte : mainJoueur.getCartes()) {
            System.out.println(carte.getValeur()+carte.getCouleur());
        }
        */
    }

    public static void main(String[] args) {
        launch(args);
    }
}
