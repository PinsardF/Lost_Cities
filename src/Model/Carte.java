package Model;

import javafx.scene.image.Image;

import java.io.File;

public class Carte {
    private int valeur;
    private String couleur;
    private int id_couleur;
    private Image image;

    public Carte(int valeur, String couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
        String url = Integer.toString(valeur) + couleur + ".png";
        //String url = Integer.toString(valeur) + "jaune.png";
        File carteFile = new File("./src/media/" + url);
        this.image = new Image(carteFile.toURI().toString());
        switch(couleur) {
            case "rouge":
                this.id_couleur = 0;
                break;
            case "vert":
                this.id_couleur = 1;
                break;
            case "jaune":
                this.id_couleur = 2;
                break;
            case "blanc":
                this.id_couleur = 3;
                break;
            case "bleu":
                this.id_couleur = 4;
                break;
        }
    }

    public int getValeur() {
        return valeur;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getId_couleur() { return id_couleur; }

    public Image getImage() {
        return image;
    }
}
