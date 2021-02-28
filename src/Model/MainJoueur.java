package Model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainJoueur {
    private Carte[] cartes;
    Pane root;

    public MainJoueur(Carte[] init, Pane root) {
        this.cartes = init;
        this.root = root;
        ImageView[] iv = new ImageView[8];
        for(int i=0 ; i<8 ; i++) {
            iv[i] = new ImageView();
            iv[i].setId("mainJoueur"+i);
            iv[i].setTranslateX(68*i-238);
            iv[i].setTranslateY(350);
            iv[i].setImage(cartes[i].getImage());
            root.getChildren().add(iv[i]);
        }
    }

    public Carte[] getCartes() {
        return this.cartes;
    }

    public Carte getCarte(int i) {
        return this.cartes[i];
    }

    public void getEtat() {
        String resultat = "";
        for(Carte carte : cartes) {
            resultat += Integer.toString(carte.getValeur()) + carte.getCouleur() + " ";
        }
        System.out.println(resultat);
    }

    public void afficherMain() {
        trier();
        for(int i = 0; i < 8; i++) {
        ((ImageView) root.getChildren().get(i)).setImage(cartes[i].getImage());
        }

    }

    public void trier() {
        List<Carte>[] listedetri = new ArrayList[5];
        for(int i = 0 ; i < 5 ; i++) {
            listedetri[i] = new ArrayList<>();
        }
        for(Carte carte : cartes) {
            listedetri[carte.getId_couleur()].add(carte);
        }
        for(List<Carte> liste : listedetri) {
            if (liste.size() > 0) {
                Collections.sort(liste, (o1, o2) -> {
                    if(o2.getValeur() == o1.getValeur()) {
                        return 0;
                    }
                    else if (o2.getValeur() > o1.getValeur()) {
                        return -1;
                    }
                    return 1;
                });
            }
        }
        int indice = 0;
        for(List<Carte> liste : listedetri) {
            if(liste.size() > 0) {
                for(Carte carte : liste) {
                    //System.out.println("Carte " + carte + " placée à " + indice);
                    this.cartes[indice] = carte;
                    indice++;
                }
            }
        }
    }
}
