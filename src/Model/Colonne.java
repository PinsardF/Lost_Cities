package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Colonne {
    private List<Carte> cartes = new ArrayList<>();
    private double positionX;
    private double positionY;
    private int couleurId;
    Pane root;

    public Colonne(double x, double y, int couleurId, Pane root) {
        positionX = x;
        positionY = y;
        this.couleurId = couleurId;
        this.root = root;
        ImageView iv = new ImageView();
        iv.setTranslateX(x);
        iv.setTranslateY(y);
        iv.setId("colonne" + couleurId);
        String url = "./src/media/1rouge.png";
        File carteFile = new File(url);
        Image image = new Image(carteFile.toURI().toString());
        iv.setImage(image);
        root.getChildren().add(iv);
    }
}
