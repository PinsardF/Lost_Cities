package sample;

import Model.*;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Création du menu principal
        Pane root = new StackPane();
        File file = new File("./src/media/menu.png");
        Image image = new Image(file.toURI().toString());
        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        primaryStage.setTitle("Les Cités Perdues");
        primaryStage.setScene(new Scene(root, 800, 800));

        //Créations des boutons
        ImageView singlePlayerButton = new ImageView();
        singlePlayerButton.setId("singlePlayerButton");
        singlePlayerButton.setTranslateX(0);
        singlePlayerButton.setTranslateY(-100);
        File singlePlayerFile = new File("./src/media/soloButton.png");
        Image singlePlayerImage = new Image(singlePlayerFile.toURI().toString());
        singlePlayerButton.setImage(singlePlayerImage);
        root.getChildren().add(singlePlayerButton);

        ImageView multiPlayerButton = new ImageView();
        multiPlayerButton.setId("multiPlayerButton");
        multiPlayerButton.setTranslateX(0);
        multiPlayerButton.setTranslateY(0);
        File multiPlayerFile = new File("./src/media/multiButton.png");
        Image multiPlayerImage = new Image(multiPlayerFile.toURI().toString());
        multiPlayerButton.setImage(multiPlayerImage);
        root.getChildren().add(multiPlayerButton);

        ImageView rulesButton = new ImageView();
        rulesButton.setId("rulesButton");
        rulesButton.setTranslateX(0);
        rulesButton.setTranslateY(100);
        File rulesFile = new File("./src/media/rulesButton.png");
        Image rulesImage = new Image(rulesFile.toURI().toString());
        rulesButton.setImage(rulesImage);
        root.getChildren().add(rulesButton);

        ImageView quitButton = new ImageView();
        quitButton.setId("quitButton");
        quitButton.setTranslateX(0);
        quitButton.setTranslateY(200);
        File quitFile = new File("./src/media/quitButton.png");
        Image quitImage = new Image(quitFile.toURI().toString());
        quitButton.setImage(quitImage);
        root.getChildren().add(quitButton);

        //Mise en place de l'événement "Cliquer sur un bouton"
        ((ImageView) root.getChildren().get(0)).setOnMouseClicked( event -> {
            play(primaryStage, 1);
        });
        ((ImageView) root.getChildren().get(1)).setOnMouseClicked( event -> {
            play(primaryStage, 2);
        });
        ((ImageView) root.getChildren().get(2)).setOnMouseClicked( event -> {
            regles(primaryStage);
        });
        ((ImageView) root.getChildren().get(3)).setOnMouseClicked( event -> {
            System.exit(0);
        });

        primaryStage.show();
    }

    public void regles(Stage primaryStage) {
        //Création du Background et de l'écran
        Pane root = new StackPane();
        File file = new File("./src/media/ecranRegles.png");
        Image image = new Image(file.toURI().toString());
        BackgroundImage myBI = new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        primaryStage.setTitle("Les Cités Perdues");
        primaryStage.setScene(new Scene(root, 800, 800));

        //Création du bouton Menu
        File menuFile = new File("./src/media/menubutton.png");
        Image menuImage = new Image(menuFile.toURI().toString());
        ImageView menuButton = new ImageView();
        menuButton.setImage(menuImage);
        menuButton.setTranslateX(-353);
        menuButton.setTranslateY(-375);
        menuButton.setId("menuButton");
        root.getChildren().add(menuButton);
        ((ImageView) root.lookup("#menuButton")).setOnMouseClicked(event -> {
            start(primaryStage);
        });
    }

    public void play(Stage primaryStage, int playersCount) {

        //Création du Background et de l'écran
        Pane root = new StackPane();
        File file = new File("./src/media/background.png");
        Image image = new Image(file.toURI().toString());
        BackgroundImage myBI = new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        primaryStage.setTitle("Les Cités Perdues");
        primaryStage.setScene(new Scene(root, 800, 800));

        //Création du bouton Menu
        File menuFile = new File("./src/media/menubutton.png");
        Image menuImage = new Image(menuFile.toURI().toString());
        ImageView menuButton = new ImageView();
        menuButton.setImage(menuImage);
        menuButton.setTranslateX(-353);
        menuButton.setTranslateY(-375);
        menuButton.setId("menuButton");
        root.getChildren().add(menuButton);
        ((ImageView) root.lookup("#menuButton")).setOnMouseClicked(event -> {
            start(primaryStage);
        });

        //Création des colonnes
        Colonne[] colonnes = new Colonne[5];
        for (int i = 0; i < 5; i++) {
            colonnes[i] = new Colonne((100 * i) - 200, 180, i, 1, root);
        }
        Colonne[] colonnesJ2 = new Colonne[5];
        if (playersCount == 2) {
            if (playersCount == 2) {
                for (int i = 0; i < 5; i++) {
                    colonnesJ2[i] = new Colonne((100 * i) - 200, -180, i, 2, root);
                }
            }
        }

        //Création des scores
        Score[] scores = new Score[5];
        for (int i = 0; i < 5; i++) {
            scores[i] = new Score((100 * i) - 200, 250, i, 1, root);
        }

        Score[] scoresJ2 = new Score[5];
        if (playersCount == 2) {
            for (int i = 0; i < 5; i++) {
                scoresJ2[i] = new Score((100 * i) - 200, -250, i, 2, root);
            }
        }
        //Création des Défausses
        Defausse[] defausses = new Defausse[5];
        for (int i = 0; i < 5; i++) {
            defausses[i] = new Defausse(100.0 * i - 200.0, 0.0, i, root);
        }

        //Création de la pioche et tirage des premières cartes
        Pioche pioche = new Pioche(root, -330, 0);
        CompteurPioche compteurPioche = new CompteurPioche(-330, 60, root, pioche.getNombreCartes());

        MainJoueur[] mainJoueurs = new MainJoueur[2];
        Carte[] tirage = new Carte[8];
        for (int i = 0; i < 8; i++) {
            tirage[i] = pioche.piocher();
        }
        MainJoueur mainJoueur1 = new MainJoueur(tirage, 1, root);
        mainJoueur1.trier();
        mainJoueur1.afficherMain();
        mainJoueurs[0] = mainJoueur1;

        Carte[] tirageJ2 = new Carte[8];
        if (playersCount == 1) {
            for (int i = 0; i < 8; i++) {
                tirageJ2[i] = new Carte(0, "none");
            }
        } else {
            tirageJ2 = new Carte[8];
            for (int i = 0; i < 8; i++) {
                tirageJ2[i] = pioche.piocher();
            }
        }
        MainJoueur mainJoueur2 = new MainJoueur(tirageJ2, 2, root);
        if (playersCount == 2) {
            mainJoueur2.trier();
            mainJoueur2.afficherMain();
            mainJoueurs[1] = mainJoueur2;
            retournerMain(root, mainJoueur2, false);
        }

        //On initialise correctement le compteur du nombre de cartes
        compteurPioche.update(pioche.getNombreCartes());

        //Chargement des curseurs
        Curseur[] curseursDefausse = new Curseur[6];
        for(int i = 0;i < 6; i++) {
            curseursDefausse[i] = new Curseur(root, i);
        }

        //Mise en place de l'événement "Cliquer sur une Colonne" J1
        for (int i = 0; i < 5; i++) {
            final int indice = i;
            ((ImageView) root.lookup("#colonne"+i+"j1")).setOnMouseClicked(event -> {
                //Si on est en phase "placer une carte" J1
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
                    ((ImageView) root.lookup("#mainJoueur" + mainJoueur1.getCarteSelectionneeId() + "j1")).setImage(imageVide);
                    mainJoueur1.afficherMain();
                    //On retire les curseurs
                    curseursDefausse[0].disparaitre(root);
                    curseursDefausse[1].disparaitre(root);
                    //On déplace la carte (Transition)
                    TranslateTransition carteTransition = new TranslateTransition();
                    carteTransition.setDuration(Duration.seconds(0.4));
                    carteTransition.setToX(colonnes[indice].getPositionX());
                    carteTransition.setToY(colonnes[indice].getPositionY());
                    double x = ((ImageView) root.lookup("#mainJoueur" + mainJoueur1.getCarteSelectionneeId() + "j1")).getTranslateX();
                    double y = ((ImageView) root.lookup("#mainJoueur" + mainJoueur1.getCarteSelectionneeId() + "j1")).getTranslateY();
                    ((ImageView) root.lookup("#curseur0")).setTranslateX(x);
                    ((ImageView) root.lookup("#curseur0")).setTranslateY(y);
                    ((ImageView) root.lookup("#curseur0")).setImage(carteSelectionnee.getImage());
                    carteTransition.setNode((ImageView) root.lookup("#curseur0"));
                    carteTransition.setOnFinished(transitionEvent -> {
                        curseursDefausse[0].disparaitre(root);
                        colonnes[indice].afficherColonne();
                        if (!pioche.estVide()) {
                            afficherCurseurs(root, defausses, curseursDefausse);
                        }
                        //Gestion de la fin du tour
                        if (playersCount == 1) {
                            if (pioche.estVide()) {
                                finDePartie(root, colonnes, primaryStage);
                            } else {
                                mainJoueur1.setEtat("pioche");
                            }
                        } else {
                            if (mainJoueur2.getFini()) {
                                finDePartie(root, colonnes, colonnesJ2, primaryStage);
                            } else if (pioche.estVide()) {
                                mainJoueur1.setFini(true);
                                mainJoueur1.setEtat("fin du tour");
                                mainJoueur2.setEtat("pose");
                                retournerMain(root, mainJoueur1, false);
                                retournerMain(root, mainJoueur2, true);
                            } else {
                                mainJoueur1.setEtat("pioche");
                            }
                        }
                    });
                    carteTransition.play();
                }
            });

        }

        //Mise en place de l'événement "Cliquer sur une Colonne" J2
        if (playersCount == 2) {
            for (int i = 0; i < 5; i++) {
                final int indice = i;
                ((ImageView) root.lookup("#colonne" + i + "j2")).setOnMouseClicked(event -> {
                    //Si on est en phase "placer une carte" J2
                    if (mainJoueur2.getCarteSelectionneeId() > -1 && mainJoueur2.getEtat() == "pose" &&
                            mainJoueur2.getCarteSelectionnee().getId_couleur() ==
                                    Integer.parseInt(((ImageView) event.getSource()).getId().substring(7, 8)) &&
                            mainJoueur2.getCarteSelectionnee().getValeur() >= colonnesJ2[indice].getDerniereValeur()) {
                        //On ajoute la carte à la colonne
                        Carte carteSelectionnee = mainJoueur2.getCarteSelectionnee();
                        colonnesJ2[indice].ajouterCarte(carteSelectionnee);
                        //On met à jour le score
                        scoresJ2[indice].updateScore(colonnesJ2[indice].getCartes());
                        //On retire la carte de la main
                        mainJoueur2.supprimerCarte(mainJoueur2.getCarteSelectionneeId());
                        //On fait disparaître la carte de la main
                        File carteFileVide = new File("./src/media/vide.png");
                        Image imageVide = new Image(carteFileVide.toURI().toString());
                        ((ImageView) root.lookup("#mainJoueur" + mainJoueur2.getCarteSelectionneeId() + "j2")).setImage(imageVide);
                        mainJoueur2.afficherMain();
                        //On retire les curseurs
                        curseursDefausse[0].disparaitre(root);
                        curseursDefausse[1].disparaitre(root);
                        //On déplace la carte (Transition)
                        TranslateTransition carteTransition = new TranslateTransition();
                        carteTransition.setDuration(Duration.seconds(0.4));
                        carteTransition.setToX(colonnesJ2[indice].getPositionX());
                        carteTransition.setToY(colonnesJ2[indice].getPositionY());
                        double x = ((ImageView) root.lookup("#mainJoueur" + mainJoueur2.getCarteSelectionneeId() + "j2")).getTranslateX();
                        double y = ((ImageView) root.lookup("#mainJoueur" + mainJoueur2.getCarteSelectionneeId() + "j2")).getTranslateY();
                        ((ImageView) root.lookup("#curseur0")).setTranslateX(x);
                        ((ImageView) root.lookup("#curseur0")).setTranslateY(y);
                        ((ImageView) root.lookup("#curseur0")).setImage(carteSelectionnee.getImage());
                        carteTransition.setNode((ImageView) root.lookup("#curseur0"));
                        carteTransition.setOnFinished(transitionEvent -> {
                            curseursDefausse[0].disparaitre(root);
                            colonnesJ2[indice].afficherColonne();
                            if (!pioche.estVide()) {
                                afficherCurseurs(root, defausses, curseursDefausse);
                            }
                            //Gestion de la fin du tour
                            if (mainJoueur1.getFini()) {
                                finDePartie(root, colonnes, colonnesJ2, primaryStage);
                            } else if (pioche.estVide()) {
                                mainJoueur2.setFini(true);
                                mainJoueur1.setEtat("pose");
                                mainJoueur2.setEtat("fin du tour");
                                retournerMain(root, mainJoueur2, false);
                                retournerMain(root, mainJoueur1, true);
                            } else {
                                mainJoueur2.setEtat("pioche");
                            }
                        });
                        carteTransition.play();
                    }
                });
            }
        }

        //Mise en place de l'événement "Cliquer sur une Défausse"
        for(int i = 0; i < 5; i++) {
            final int indice = i;
            ((ImageView) root.lookup("#defausse"+indice)).setOnMouseClicked(event -> {
                //Si on est en phase "placer une carte" J1
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
                    ((ImageView) root.lookup("#mainJoueur"+mainJoueur1.getCarteSelectionneeId()+"j1")).setImage(imageVide);
                    mainJoueur1.afficherMain();
                    //On retire les curseurs
                    curseursDefausse[0].disparaitre(root);
                    curseursDefausse[1].disparaitre(root);
                    //On déplace la carte (Transition)
                    TranslateTransition carteTransition = new TranslateTransition();
                    carteTransition.setDuration(Duration.seconds(0.4));
                    carteTransition.setToX(defausses[indice].getPositionX());
                    carteTransition.setToY(defausses[indice].getPositionY());
                    double x = ((ImageView) root.lookup("#mainJoueur"+mainJoueur1.getCarteSelectionneeId()+"j1")).getTranslateX();
                    double y = ((ImageView) root.lookup("#mainJoueur"+mainJoueur1.getCarteSelectionneeId()+"j1")).getTranslateY();
                    ((ImageView) root.lookup("#curseur0")).setTranslateX(x);
                    ((ImageView) root.lookup("#curseur0")).setTranslateY(y);
                    ((ImageView) root.lookup("#curseur0")).setImage(carteImage);
                    carteTransition.setNode((ImageView) root.lookup("#curseur0"));
                    carteTransition.setOnFinished(transitionEvent -> {
                        curseursDefausse[0].disparaitre(root);
                        defausses[indice].afficherDefausse();
                        if (!pioche.estVide()) {
                            afficherCurseurs(root, defausses, curseursDefausse);
                        }
                        if (playersCount == 1) {
                            if (pioche.estVide()) {
                                finDePartie(root, colonnes, primaryStage);
                            } else {
                                mainJoueur1.setEtat("pioche");
                            }
                        } else {
                            if (mainJoueur2.getFini()) {
                                finDePartie(root, colonnes, colonnesJ2, primaryStage);
                            } else if (pioche.estVide()) {
                                mainJoueur1.setFini(true);
                                mainJoueur2.setEtat("pose");
                                mainJoueur1.setEtat("fin du tour");
                                retournerMain(root, mainJoueur1, false);
                                retournerMain(root, mainJoueur2, true);
                            } else {
                                mainJoueur1.setEtat("pioche");
                            }
                        }
                    });
                    carteTransition.play();
                //Si on est en phase "piocher une carte" J1
                } else if(mainJoueur1.getEtat() == "pioche" && !defausses[indice].estVide()) {
                    mainJoueur1.setEtat("fin du tour");
                    //On récupère l'emplacement de la carte
                    double defausseX = defausses[indice].getPositionX();
                    double defausseY = defausses[indice].getPositionY();
                    Carte carteDeplacee = defausses[indice].piocher();
                    //On déplace la carte
                    piocherCarte(root, carteDeplacee, mainJoueur1, mainJoueur2,defausseX, defausseY, 1);
                    defausses[indice].afficherDefausse();

                    //On supprime les curseurs
                    File carteFileVide = new File("./src/media/vide.png");
                    Image imageVide = new Image(carteFileVide.toURI().toString());
                    for (int j = 0 ; j < 6 ; j++) {
                        ((ImageView) root.lookup("#curseur"+j)).setImage(imageVide);
                    }
                    //Gestion de la fin de tour
                    if (playersCount == 1) {
                        if (mainJoueur1.getFini()) {
                            finDePartie(root, colonnes, primaryStage);
                        } else if (pioche.estVide()) {
                            mainJoueur1.setFini(true);
                            mainJoueur1.setEtat("pose");
                        } else {
                            mainJoueur1.setEtat("pose");
                        }
                    } else {
                        mainJoueur1.setEtat("fini");
                        mainJoueur2.setEtat("pose");
                    }
                //Si on est en phase "placer une carte" J2
                } else if (mainJoueur2.getCarteSelectionneeId() > -1
                        && mainJoueur2.getEtat() == "pose" && mainJoueur2.getCarteSelectionnee().getId_couleur() ==
                        Integer.parseInt(((ImageView) event.getSource()).getId().substring(8, 9))) {
                    if (mainJoueur2.getFini()) {
                        finDePartie(root, colonnes, colonnesJ2, primaryStage);
                    } else {
                        //On récupère l'image de la carte
                        Image carteImage = (mainJoueur2.getCarteSelectionnee().getImage());
                        //On ajoute la carte à la défausse
                        defausses[indice].ajouterCarte(mainJoueur2.getCarteSelectionnee());
                        //On retire la carte de la main
                        mainJoueur2.supprimerCarte(mainJoueur2.getCarteSelectionneeId());
                        //On fait disparaître la carte de la main
                        File carteFileVide = new File("./src/media/vide.png");
                        Image imageVide = new Image(carteFileVide.toURI().toString());
                        ((ImageView) root.lookup("#mainJoueur" + mainJoueur2.getCarteSelectionneeId() + "j2")).setImage(imageVide);
                        mainJoueur2.afficherMain();
                        //On retire les curseurs
                        curseursDefausse[0].disparaitre(root);
                        curseursDefausse[1].disparaitre(root);
                        //On déplace la carte (Transition)
                        TranslateTransition carteTransition = new TranslateTransition();
                        carteTransition.setDuration(Duration.seconds(0.4));
                        carteTransition.setToX(defausses[indice].getPositionX());
                        carteTransition.setToY(defausses[indice].getPositionY());
                        double x = ((ImageView) root.lookup("#mainJoueur" + mainJoueur2.getCarteSelectionneeId() + "j2")).getTranslateX();
                        double y = ((ImageView) root.lookup("#mainJoueur" + mainJoueur2.getCarteSelectionneeId() + "j2")).getTranslateY();
                        ((ImageView) root.lookup("#curseur0")).setTranslateX(x);
                        ((ImageView) root.lookup("#curseur0")).setTranslateY(y);
                        ((ImageView) root.lookup("#curseur0")).setImage(carteImage);
                        carteTransition.setNode((ImageView) root.lookup("#curseur0"));
                        carteTransition.setOnFinished(transitionEvent -> {
                            curseursDefausse[0].disparaitre(root);
                            defausses[indice].afficherDefausse();
                            if (!pioche.estVide()) {
                                afficherCurseurs(root, defausses, curseursDefausse);
                            }
                            if (playersCount == 1) {
                                if (pioche.estVide()) {
                                    finDePartie(root, colonnes, primaryStage);
                                } else {
                                    mainJoueur1.setEtat("pioche");
                                }
                            } else {
                                if (mainJoueur1.getFini()) {
                                    finDePartie(root, colonnes, colonnesJ2, primaryStage);
                                } else if (pioche.estVide()) {
                                    mainJoueur2.setFini(true);
                                    mainJoueur1.setEtat("pose");
                                    mainJoueur2.setEtat("fin du tour");
                                    retournerMain(root, mainJoueur1, true);
                                    retournerMain(root, mainJoueur2, false);
                                } else {
                                    mainJoueur2.setEtat("pioche");
                                }
                            }
                        });
                        carteTransition.play();
                    }
                    //Si on est en phase "piocher une carte" J2
                } else if (mainJoueur2.getEtat() == "pioche" && !defausses[indice].estVide()) {
                    mainJoueur2.setEtat("fin du tour");
                    //On récupère l'emplacement de la carte
                    double defausseX = defausses[indice].getPositionX();
                    double defausseY = defausses[indice].getPositionY();
                    Carte carteDeplacee = defausses[indice].piocher();
                    //On déplace la carte
                    piocherCarte(root, carteDeplacee, mainJoueur2, mainJoueur1, defausseX, defausseY, 2);
                    defausses[indice].afficherDefausse();

                    //On supprime les curseur
                    File carteFileVide = new File("./src/media/vide.png");
                    Image imageVide = new Image(carteFileVide.toURI().toString());
                    for (int j = 0; j < 6; j++) {
                        ((ImageView) root.lookup("#curseur" + j)).setImage(imageVide);
                    }
                    //Gestion de la fin de tour
                    mainJoueur1.setEtat("pose");
                    mainJoueur2.setEtat("fini");
                }
            });
        }

        //Mise en place de l'événement "Cliquer sur une carte" J1
        for (int i = 0; i < 8; i++) {
            final int indice = i;
            ((ImageView) root.lookup("#mainJoueur"+indice+"j1")).setOnMouseClicked(event -> {
                //Si on est en phase "placer une carte"
                if(mainJoueur1.getEtat() == "pose") {
                    switch (event.getButton().name()) {
                        //CAS : Clic gauche sur une carte J1
                        case "PRIMARY":
                            //CAS : Une carte a déjà été cliquée précédemment
                            if (mainJoueur1.getCarteSelectionneeId() > -1) {
                                //CAS : La carte cliquée n'est pas la même que la précédente
                                if (mainJoueur1.getCarteSelectionneeId() !=
                                        Integer.parseInt(((ImageView) event.getSource()).getId().substring(10, 11))) {
                                    //On remet la carte précédente dans la main
                                    ((ImageView) root.lookup("#mainJoueur"+mainJoueur1.getCarteSelectionneeId()+"j1")).setTranslateY(350.0);
                                }
                            }
                            //Et on sort la nouvelle
                            String nouvelleCarteId = ((ImageView) event.getSource()).getId().substring(10, 11);
                            ((ImageView) root.lookup("#mainJoueur"+nouvelleCarteId+"j1")).setTranslateY(300.0);

                            //On récupère la couleur de la carte cliquée
                            Carte carte;
                            int couleur;
                            carte = mainJoueur1.getCarte(Integer.parseInt(nouvelleCarteId));
                            couleur = carte.getId_couleur();
                            //On retire les curseurs
                            curseursDefausse[0].disparaitre(root);
                            curseursDefausse[1].disparaitre(root);
                            //On place le curseur sur la bonne Défausse
                            curseursDefausse[0].afficher(root, root.lookup("#defausse"+couleur).getTranslateX(), root.lookup("#defausse"+couleur).getTranslateY());
                            //On place un curseur sur la bonne colonne
                            if (carte.getValeur() >= colonnes[carte.getId_couleur()].getDerniereValeur()) {
                                curseursDefausse[1].afficher(root, root.lookup("#colonne"+couleur+"j1").getTranslateX(), root.lookup("#colonne"+couleur+"j1").getTranslateY());
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
                                ((ImageView) root.lookup("#mainJoueur"+mainJoueur1.getCarteSelectionneeId()+"j1")).setTranslateY(350.0);
                                mainJoueur1.setCarteSelectionnee(-1);
                            }
                            break;
                    }
                //Si on est en phase "placer une carte" J2
                }
            });
        }

        //Mise en place de l'événement "Cliquer sur une carte" J2
        for (int i = 0; i < 8; i++) {
            final int indice = i;
            ((ImageView) root.lookup("#mainJoueur"+indice+"j2")).setOnMouseClicked(event -> {
                 if(mainJoueur2.getEtat() == "pose") {
                    switch (event.getButton().name()) {
                        //CAS : Clic gauche sur une carte
                        case "PRIMARY":
                            //CAS : Une carte a déjà été cliquée précédemment
                            if (mainJoueur2.getCarteSelectionneeId() > -1) {
                                //CAS : La carte cliquée n'est pas la même que la précédente
                                if (mainJoueur2.getCarteSelectionneeId() !=
                                        Integer.parseInt(((ImageView) event.getSource()).getId().substring(10, 11))) {
                                    //On remet la carte précédente dans la main
                                    ((ImageView) root.lookup("#mainJoueur"+mainJoueur2.getCarteSelectionneeId()+"j2")).setTranslateY(-350.0);
                                }
                            }
                            //Et on sort la nouvelle
                            String nouvelleCarteId = ((ImageView) event.getSource()).getId().substring(10, 11);
                            ((ImageView) root.lookup("#mainJoueur"+nouvelleCarteId+"j2")).setTranslateY(-300.0);

                            //On récupère la couleur de la carte cliquée
                            Carte carte;
                            int couleur;
                            carte = mainJoueur2.getCarte(Integer.parseInt(nouvelleCarteId));
                            couleur = carte.getId_couleur();
                            //On retire les curseurs
                            curseursDefausse[0].disparaitre(root);
                            curseursDefausse[1].disparaitre(root);
                            //On place le curseur sur la bonne Défausse
                            curseursDefausse[0].afficher(root, root.lookup("#defausse"+couleur).getTranslateX(), root.lookup("#defausse"+couleur).getTranslateY());
                            //On place un curseur sur la bonne colonne
                            if (carte.getValeur() >= colonnesJ2[carte.getId_couleur()].getDerniereValeur()) {
                                curseursDefausse[1].afficher(root, root.lookup("#colonne"+couleur+"j1").getTranslateX(), root.lookup("#colonne"+couleur+"j2").getTranslateY());
                            }
                            //On met mainJoueur2.carteSeléctionnéeId à la bonne valeur
                            mainJoueur2.setCarteSelectionnee(Integer.parseInt(((ImageView) event.getSource()).getId().substring(10, 11)));
                            break;

                        //CAS : Clic droit sur une carte
                        case "SECONDARY":
                            if (mainJoueur2.getCarteSelectionneeId() != -1) {
                                //On annule :  on retire le curseur
                                curseursDefausse[0].disparaitre(root);
                                curseursDefausse[1].disparaitre(root);
                                //Et on rentre la carte précédemment cliquée
                                ((ImageView) root.lookup("#mainJoueur"+mainJoueur2.getCarteSelectionneeId()+"j2")).setTranslateY(-350.0);
                                mainJoueur2.setCarteSelectionnee(-1);
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
        ((ImageView) root.lookup("#pioche")).setOnMouseClicked(event -> {
            if (mainJoueur1.getEtat() == "pioche") {
                mainJoueur1.setEtat("fin du tour");
                Carte cartePiochée = pioche.piocher();
                if (pioche.uneCarteRestante()) {
                    pioche.setDerniereCarte(true);
                    pioche.disparaitre(root);
                }
                piocherCarte(root, cartePiochée, mainJoueur1, mainJoueur2, pioche.getPositionX(), pioche.getPositionY(), 1);
                compteurPioche.update(pioche.getNombreCartes());
                File carteFileVide = new File("./src/media/vide.png");
                Image imageVide = new Image(carteFileVide.toURI().toString());
                for (int j = 0 ; j < 6 ; j++) {
                    ((ImageView) root.lookup("#curseur"+j)).setImage(imageVide);
                }
                //Gestion de la fin de tour
                if (playersCount == 1) {
                    if (mainJoueur1.getFini()) {
                        finDePartie(root, colonnes, primaryStage);
                    } else if (pioche.estVide()) {
                        mainJoueur1.setFini(true);
                        mainJoueur1.setEtat("pose");
                    } else {
                        mainJoueur1.setEtat("pose");
                    }
                }
                else {
                    mainJoueur1.setEtat("fini");
                    mainJoueur2.setEtat("pose");
                }
            } else if (mainJoueur2.getEtat() == "pioche") {
                mainJoueur2.setEtat("fin du tour");
                Carte cartePiochée = pioche.piocher();
                if (pioche.uneCarteRestante()) {
                    pioche.setDerniereCarte(true);
                    pioche.disparaitre(root);
                }
                piocherCarte(root, cartePiochée, mainJoueur2, mainJoueur1, pioche.getPositionX(), pioche.getPositionY(), 2);
                compteurPioche.update(pioche.getNombreCartes());
                File carteFileVide = new File("./src/media/vide.png");
                Image imageVide = new Image(carteFileVide.toURI().toString());
                for (int j = 0 ; j < 6 ; j++) {
                    ((ImageView) root.lookup("#curseur"+j)).setImage(imageVide);
                }
                //Gestion de la fin de tour
                mainJoueur1.setEtat("pose");
                mainJoueur2.setEtat("fini");
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

        //Etats de la main :
        //"pose"
        //"pioche"
        //"fin du tour" (déplacement des cartes dans la main)
        //"fini" (mode multijoueur)
        //"dernier tour" (mode multijoueur)

        //SUITE : Affichage nb cartes pioche, écran de fin de partie

        primaryStage.show();
    }

    public void finDePartie(Pane root, Colonne[] colonnes, Colonne[] colonnesJ2, Stage primaryStage) {
        //Vidage de l'écran
        File file = new File("./src/media/ecranVictoire.png");
        Image image = new Image(file.toURI().toString());
        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        root.getChildren().clear();

        //Affichage du texte
        Text scoreFinal = new Text();
        scoreFinal.setTranslateX(0);
        scoreFinal.setTranslateY(-100);
        scoreFinal.setStyle("-fx-font-weight: bold");
        scoreFinal.setFont(new Font(50));
        Text scoreJ1 = new Text();
        scoreJ1.setTranslateX(0);
        scoreJ1.setTranslateY(-30);
        scoreJ1.setFont(new Font(28));
        Text scoreJ2 = new Text();
        scoreJ2.setTranslateX(0);
        scoreJ2.setTranslateY(0);
        scoreJ2.setFont(new Font(28));
        Text gagnant = new Text();
        gagnant.setTranslateX(0);
        gagnant.setTranslateY(100);
        gagnant.setStyle("-fx-font-style: italic");
        gagnant.setFont(new Font(32));

        scoreFinal.setText("TABLEAU DES SCORES");
        int scoreTotalJ1 = 0;
        for (Colonne colonne : colonnes) {
            scoreTotalJ1 += colonne.getScore();
        }int scoreTotalJ2 = 0;
        for (Colonne colonne : colonnesJ2) {
            scoreTotalJ2 += colonne.getScore();
        }
        scoreJ1.setText("J1 : " + Integer.toString(scoreTotalJ1));
        scoreJ2.setText("J2 : " + Integer.toString(scoreTotalJ2));
        String messageGagnant = "";
        if (scoreTotalJ1 > scoreTotalJ2) {
            messageGagnant = "Le joueur 1 a gagné !";
        } else if (scoreTotalJ1 < scoreTotalJ2) {
            messageGagnant = "Le joueur 1 a gagné !";
        } else {
            messageGagnant = "La partie se termine sur une égalité !";
        }
        gagnant.setText(messageGagnant);
        root.getChildren().add(scoreFinal);
        root.getChildren().add(scoreJ1);
        root.getChildren().add(scoreJ2);
        root.getChildren().add(gagnant);

        //Bouton Retour vers le menu
        ImageView menuButton = new ImageView();
        menuButton.setId("menuButton");
        menuButton.setTranslateX(0);
        menuButton.setTranslateY(150);
        File menuFile = new File("./src/media/menuButton.png");
        Image menuImage = new Image(menuFile.toURI().toString());
        menuButton.setImage(menuImage);
        root.getChildren().add(menuButton);

        menuButton.setOnMouseClicked(event -> {
            start(primaryStage);
        });
    }

    public void finDePartie(Pane root, Colonne[] colonnes, Stage primaryStage) {
        //Vidage de l'écran
        File file = new File("./src/media/ecranVictoire.png");
        Image image = new Image(file.toURI().toString());
        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        root.getChildren().clear();

        //Affichage du texte
        Text scoreFinal = new Text();
        String message = "Score final : ";
        int scoreTotal = 0;
        for (Colonne colonne : colonnes) {
            scoreTotal += colonne.getScore();
        }
        message += scoreTotal;
        scoreFinal.setText(message);
        scoreFinal.setStyle("-fx-font-weight: bold");
        scoreFinal.setFont(new Font(40));
        scoreFinal.setTranslateY(-70);
        root.getChildren().add(scoreFinal);

        //Bouton Retour vers le menu
        ImageView menuButton = new ImageView();
        menuButton.setId("menuButton");
        menuButton.setTranslateX(0);
        menuButton.setTranslateY(100);
        File menuFile = new File("./src/media/menuButton.png");
        Image menuImage = new Image(menuFile.toURI().toString());
        menuButton.setImage(menuImage);
        root.getChildren().add(menuButton);

        menuButton.setOnMouseClicked(event -> {
            start(primaryStage);
        });
    }

    public void afficherCurseurs(Pane root, Defausse[] defausses, Curseur[] curseurs) {
        for(int i = 0; i < 5; i++) {
            if(!defausses[i].estVide()) {
                curseurs[i].afficher(root, 100*i - 200, 0);
            }
        }
        curseurs[5].afficher(root, -330, 0);
    }

    public void piocherCarte(Pane root, Carte carteDeplacee, MainJoueur mainJoueur, MainJoueur mainJoueurAutre, double defausseX, double defausseY, int joueur) {
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

            Carte[] nouvelleMain = new Carte[8];
            for (int h = 0; h < 8; h++) {
                nouvelleMain[h] = mainJoueur.getCarte(h);
            }

            //Maintenant on les déplace (visuellement et dans nouvelleMain)
            for (int k = 0 ; k < cartesADeplacer.size() ; k++) {
                nouvelleMain[cartesADeplacer.get(k) + (X / 68)] = mainJoueur.getCarte(cartesADeplacer.get(k));

                TranslateTransition mainTransition = new TranslateTransition();
                mainTransition.setDuration(Duration.seconds(0.2));
                mainTransition.setByX(X);
                if (joueur == 1) {
                    mainTransition.setNode(root.lookup("#mainJoueur"+cartesADeplacer.get(k)+"j1"));
                } else {
                    mainTransition.setNode(root.lookup("#mainJoueur"+cartesADeplacer.get(k)+"j2"));
                }
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
        ((ImageView) root.lookup("#transition")).setTranslateX(defausseX);
        ((ImageView) root.lookup("#transition")).setTranslateY(defausseY);
        //Et l'emplacement d'arrivée
        if (joueur == 1) {
            carteTransition.setToX(root.lookup("#mainJoueur" + indiceCarte + "j1").getTranslateX());
            carteTransition.setToY(root.lookup("#mainJoueur" + indiceCarte + "j1").getTranslateY());
        } else {
            carteTransition.setToX(root.lookup("#mainJoueur" + indiceCarte + "j2").getTranslateX());
            carteTransition.setToY(root.lookup("#mainJoueur" + indiceCarte + "j2").getTranslateY());
        }
        //On met l'image de la carte sur le node "transition"
        ((ImageView) root.lookup("#transition")).setImage(carteDeplacee.getImage());
        carteTransition.setNode(root.lookup("#transition"));
        carteTransition.setOnFinished(transitionEvent -> {
            //On retire le node transition
            root.lookup("#transition").setTranslateY(-150);
            File carteFileVide = new File("./src/media/vide.png");
            Image imageVide = new Image(carteFileVide.toURI().toString());
            ((ImageView) root.lookup("#transition")).setImage(imageVide);
            //On remet à zéro l'id de la carte sélectionnée de mainJoueur1
            mainJoueur.resetCarteSelectionee();
            mainJoueur.afficherMain();
            retournerMain(root, mainJoueur, false);
            retournerMain(root, mainJoueurAutre, true);

        });
        carteTransition.play();
        mainJoueur.setEtat("fin du tour");
    }

    public void retournerMain(Pane root, MainJoueur mainJoueur, Boolean cache) {
        //Si les cartes sont visibles on les cache
        if (!cache) {
            File carteFileVide = new File("./src/media/pioche.png");
            Image imageVide = new Image(carteFileVide.toURI().toString());
            for (int i = 0; i < 8; i++) {
                ((ImageView) root.lookup("#mainJoueur" + i + "j" + mainJoueur.getJoueurId())).setImage(imageVide);
            }
        //Sinon on les affiche
        } else {
            mainJoueur.afficherMain();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
