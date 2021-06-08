package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Colonne {
    private List<Carte> cartes = new ArrayList<>();
    private int derniereValeur = -1;
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
        String url = "./src/media/vide.png";
        //String url = "./src/media/"+couleurId+"rouge.png";
        File carteFile = new File(url);
        Image image = new Image(carteFile.toURI().toString());
        iv.setImage(image);
        //Très important pour pouvoir cliquer sur une image invisible :
        iv.setPickOnBounds(true);
        root.getChildren().add(iv);
    }

    public void afficherColonne() {
        if (cartes.size() > 0) {
            ((ImageView) root.getChildren().get(couleurId)).setImage(cartes.get(cartes.size() - 1).getImage());
        } else {
            File carteFile = new File("./src/media/vide.png");
            Image image = new Image(carteFile.toURI().toString());
            ((ImageView) root.getChildren().get(couleurId)).setImage(image);
        }
    }

    public void ajouterCarte(Carte carte) {
        cartes.add(carte);
        this.derniereValeur = carte.getValeur();
    }

    public List<Carte> getCartes() {
        return cartes;
    }

    public int getDerniereValeur() {
        return  derniereValeur;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}
