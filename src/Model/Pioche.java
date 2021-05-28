package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pioche {
    private List<Carte> cartes = new ArrayList<>();
    private double positionX;
    private double positionY;

    public Pioche(Pane root, double x, double y) {
        this.positionX = x;
        this.positionY = y;
        String[] couleurs = {"jaune","bleu","blanc","rouge","vert"};
        for(String couleur : couleurs) {
            for(int i = 0; i < 3; i++) {
                this.cartes.add(new Carte(0,couleur));
            }
            for(int i = 0; i < 10; i++) {
                this.cartes.add(new Carte(i+1, couleur));
            }
        }
        Collections.shuffle(this.cartes);
        ImageView piocheImageView = new ImageView();
        File piocheFile = new File("./src/media/pioche.png");
        Image piocheImage = new Image(piocheFile.toURI().toString());
        piocheImageView.setImage(piocheImage);
        piocheImageView.setTranslateX(-330);
        piocheImageView.setTranslateY(0);
        piocheImageView.setId("pioche");
        root.getChildren().add(piocheImageView);
    }

    public Carte piocher(){
        Carte carte = this.cartes.get(0);
        this.cartes.remove(0);
        return carte;
    }

    public void etat() {
        System.out.println("Longueur : " + cartes.size());
        System.out.println("Premières cartes : ");
        for(int i = 0; i < 8; i++) {
            System.out.println(cartes.get(i).getValeur()+cartes.get(i).getCouleur() + " ");
        }
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}
