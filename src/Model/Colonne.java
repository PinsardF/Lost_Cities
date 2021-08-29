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
    private int joueurId;
    Pane root;

    public Colonne(double x, double y, int couleurId, int joueurId, Pane root) {
        positionX = x;
        positionY = y;
        this.couleurId = couleurId;
        this.root = root;
        this.joueurId = joueurId;
        ImageView iv = new ImageView();
        iv.setTranslateX(x);
        iv.setTranslateY(y);
        iv.setId("colonne" + couleurId + "j" + joueurId);
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
            //((ImageView) root.getChildren().get(couleurId)).setImage(cartes.get(cartes.size() - 1).getImage());
            ((ImageView) root.lookup("#colonne"+couleurId+"j"+joueurId)).setImage(cartes.get(cartes.size() - 1).getImage());
        } else {
            File carteFile = new File("./src/media/vide.png");
            Image image = new Image(carteFile.toURI().toString());
            //((ImageView) root.getChildren().get(couleurId)).setImage(image);
            ((ImageView) root.lookup("#colonne"+couleurId+"j"+joueurId)).setImage(image);
        }
    }

    public void ajouterCarte(Carte carte) {
        cartes.add(carte);
        this.derniereValeur = carte.getValeur();
    }

    public int getScore() {
        //Si on n'a pas placé de carte, la colonne n'a aucune valeur
        if (cartes.size() == 0) {
            return 0;
        }
        int multiplicateur = 1;
        int valeur = -20;
        for(int i = 0; i < cartes.size(); i++) {
            if (cartes.get(i).getValeur() == 0) {
                multiplicateur++;
            } else {
                valeur += cartes.get(i).getValeur();
            }
        }
        return (valeur * multiplicateur);
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
