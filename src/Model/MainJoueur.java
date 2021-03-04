package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainJoueur {
    private Carte[] cartes;
    Pane root;
    private int carteSelectionnee;

    public MainJoueur(Carte[] init, Pane root) {
        this.cartes = init;
        this.root = root;
        carteSelectionnee = -1;
        ImageView[] iv = new ImageView[8];
        for(int i=0 ; i<8 ; i++) {
            iv[i] = new ImageView();
            iv[i].setId("mainJoueur"+i);
            iv[i].setTranslateX(68*i-238);
            iv[i].setTranslateY(350);
            iv[i].setImage(cartes[i].getImage());/*
            iv[i].setOnMouseClicked(event -> {
                carteCliquee(Integer.parseInt(((ImageView) event.getSource()).getId().substring(10,11)));
            });*/
            root.getChildren().add(iv[i]);
        }
    }

    public Carte[] getCartes() {
        return this.cartes;
    }

    public Carte getCarte(int i) {
        return this.cartes[i];
    }

    public void getEtat() {
        String resultat = "";
        for(Carte carte : cartes) {
            resultat += Integer.toString(carte.getValeur()) + carte.getCouleur() + " ";
        }
        System.out.println(resultat);
    }

    public void carteCliquee(int indice) {
        /*int id = Integer.parseInt(((ImageView) event.getSource()).getId().substring(10,11));
        File carteFile = new File("./src/media/vide.png");
        Image image = new Image(carteFile.toURI().toString());
        ((ImageView) root.getChildren().get(id)).setImage(image);*/
        System.out.println("La carte " + indice + " a été cliquée");
        int couleur = cartes[indice].getId_couleur();
        double x = root.getChildren().get(couleur).getTranslateX();
        double y = root.getChildren().get(couleur).getTranslateY();
        ((ImageView) root.getChildren().get(13)).setTranslateX(x);
        ((ImageView) root.getChildren().get(13)).setTranslateY(y);
        File carteFile = new File("./src/media/curseur.png");
        Image image = new Image(carteFile.toURI().toString());
        ((ImageView) root.getChildren().get(13)).setImage(image);
    }

    public void afficherMain() {
        trier();
        for(int i = 0; i < 8; i++) {
        ((ImageView) root.getChildren().get(5 + i)).setImage(cartes[i].getImage());
        }

    }

    public void trier() {
        List<Carte>[] listedetri = new ArrayList[5];
        for(int i = 0 ; i < 5 ; i++) {
            listedetri[i] = new ArrayList<>();
        }
        for(Carte carte : cartes) {
            listedetri[carte.getId_couleur()].add(carte);
        }
        for(List<Carte> liste : listedetri) {
            if (liste.size() > 0) {
                Collections.sort(liste, (o1, o2) -> {
                    if(o2.getValeur() == o1.getValeur()) {
                        return 0;
                    }
                    else if (o2.getValeur() > o1.getValeur()) {
                        return -1;
                    }
                    return 1;
                });
            }
        }
        int indice = 0;
        for(List<Carte> liste : listedetri) {
            if(liste.size() > 0) {
                for(Carte carte : liste) {
                    //System.out.println("Carte " + carte + " placée à " + indice);
                    this.cartes[indice] = carte;
                    indice++;
                }
            }
        }
    }

    public int getCarteSelectionnee() {
        return carteSelectionnee;
    }

    public void setCarteSelectionnee(int valeur) {
        carteSelectionnee = valeur;
    }
}
