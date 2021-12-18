package Model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CompteurPioche {
    private int x;
    private int y;
    private String text;
    Pane root;

    public CompteurPioche(int x, int y, Pane root, int nbCartes) {
        this.root = root;
        Text txt = new Text();
        txt.setTranslateX(x);
        txt.setTranslateY(y);
        txt.setId("compteurPioche");
        this.text = "";
        txt.setText(text);
        txt.setStyle("-fx-font-weight: bold");
        txt.setFill(Color.WHITE);
        txt.setFont(new Font(13));
        root.getChildren().add(txt);
        this.update(nbCartes);
    }

    public void update(int nbCartes) {
        this.updateText(nbCartes);
        ((Text) root.lookup("#compteurPioche")).setText(text);
    }

    public void updateText(int nbCartes) {
        System.out.println(nbCartes);
        if (nbCartes >= 2) {
            this.text = "Il reste " + nbCartes + " cartes";
        } else if (nbCartes == 1) {
            this.text = "Il reste 1 carte";
        } else if (nbCartes == 0) {
            this.text = "Il ne reste plus de carte";
        } else {
            System.out.println("Il y a un problème avec le nombre de cartes dans la pioche");
        }
    }
}
