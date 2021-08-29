package Model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class Score {
    private double positionX;
    private double positionY;
    private int couleurId;
    private int joueurId;
    private String text;
    Pane root;

    public Score(double x, double y, int couleur_id, int joueurId, Pane root) {
        this.root = root;
        positionX = x;
        positionY = y;
        this.joueurId = joueurId;
        Text txt = new Text();
        txt.setTranslateX(x);
        txt.setTranslateY(y);
        couleurId = couleur_id;
        txt.setId("score" + couleurId + "j" + joueurId);
        text = "";
        txt.setText(text);
        root.getChildren().add(txt);
    }

    public void updateScore(List<Carte> cartes) {
        int multiplicateur = 1;
        int valeur = -20;
        for(int i = 0; i < cartes.size(); i++) {
            if (cartes.get(i).getValeur() == 0) {
                multiplicateur++;
            } else {
                valeur += cartes.get(i).getValeur();
            }
        }
        text = "" + valeur;
        if (multiplicateur > 1) {
            text += " (X" + multiplicateur + ")";
        }
        //((Text) root.getChildren().get(5+couleurId)).setText(text);
        ((Text) root.lookup("#score"+couleurId+"j"+joueurId)).setText(text);
        if (valeur >= 0) {
            //((Text) root.getChildren().get(5+couleurId)).setFill(Color.BLACK);
            ((Text) root.lookup("#score"+couleurId+"j"+joueurId)).setFill(Color.BLACK);
        } else {
            //((Text) root.getChildren().get(5+couleurId)).setFill(Color.RED);
            ((Text) root.lookup("#score"+couleurId+"j"+joueurId)).setFill(Color.RED);
        }
    }

    public String getText() {
        return  this.text;
    }
}
