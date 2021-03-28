package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;


public class Curseur {
    private double positionX;
    private double positionY;
    private int indice;

    public Curseur(Pane root, int indice) {
        positionX = 0;
        positionY = 0;
        this.indice = indice + 13;
        ImageView curseurImageView = new ImageView();
        curseurImageView.setId("curseur" + indice);
        root.getChildren().add(curseurImageView);
    }

    public void disparaitre(Pane root) {
        File file = new File("./src/media/vide.png");
        Image image = new Image(file.toURI().toString());
        ((ImageView) root.getChildren().get(indice)).setImage(image);
    }

    public void afficher(Pane root, double x, double y) {
        root.getChildren().get(indice).setTranslateX(x);
        root.getChildren().get(indice).setTranslateY(y);
        File file = new File("./src/media/curseur.png");
        Image image = new Image(file.toURI().toString());
        ((ImageView) root.getChildren().get(indice)).setImage(image);
    }
}
