package Model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Defausse {
    private List<Carte> cartes = new ArrayList<>();
    private int positionX;
    private int positionY;
    private int couleurId;
    Pane root;

    public Defausse(int x, int y, int couleurId, Pane root) {
        positionX = x;
        positionY = y;
        this.couleurId = couleurId;
        this.root = root;
    }

    public void afficherDefausse(Pane root) {
        if(cartes.size() > 0) {
            ImageView iv = new ImageView();
            iv.setImage(cartes.get(cartes.size() - 1).getImage());
            iv.setTranslateX(positionX);
            iv.setTranslateY(positionY);
            root.getChildren().add(iv);
        }
    }
}
