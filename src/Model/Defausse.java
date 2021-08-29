package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Defausse {
    private List<Carte> cartes = new ArrayList<>();
    private double positionX;
    private double positionY;
    private int couleurId;
    Pane root;

    public Defausse(double x, double y, int couleurId, Pane root) {
        positionX = x;
        positionY = y;
        this.couleurId = couleurId;
        this.root = root;
        //cartes.add(new Carte(3,"vert"));
        ImageView iv = new ImageView();
        iv.setTranslateX(x);
        iv.setTranslateY(y);
        iv.setId("defausse" + couleurId);
        String url = "./src/media/defausse" + Integer.toString(couleurId) + ".png";
        File carteFile = new File(url);
        Image image = new Image(carteFile.toURI().toString());
        iv.setImage(image);
        root.getChildren().add(iv);
    }

    public void afficherDefausse() {
        if(cartes.size() > 0) {
            //((ImageView) root.getChildren().get(couleurId+10)).setImage(cartes.get(cartes.size() - 1).getImage());
            ((ImageView) root.lookup("#defausse"+couleurId)).setImage(cartes.get(cartes.size() - 1).getImage());
        } else {
            File carteFile = new File("./src/media/defausse" + couleurId + ".png");
            Image image = new Image(carteFile.toURI().toString());
            //((ImageView) root.getChildren().get(couleurId+10)).setImage(image);
            ((ImageView) root.lookup("#defausse"+couleurId)).setImage(image);
        }
    }

    public Carte piocher() {
        Carte derniereCarte = cartes.get(cartes.size() - 1);
        cartes.remove(cartes.size() - 1);
        return derniereCarte;
    }

    public void ajouterCarte(Carte carte) {
        cartes.add(carte);
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public Boolean estVide() {
        return cartes.size() < 1;
    }
}
