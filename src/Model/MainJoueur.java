package Model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainJoueur {
    private Carte[] cartes;
    Pane root;
    private int carteSelectionnee;
    private String etat;

    public MainJoueur(Carte[] init, Pane root) {
        this.etat = "pose";
        this.cartes = init;
        this.root = root;
        carteSelectionnee = -1;
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

    public void getEtatCartes() {
        String resultat = "";
        for(Carte carte : cartes) {
            resultat += Integer.toString(carte.getValeur()) + carte.getCouleur() + " ";
        }
        System.out.println(resultat);
    }

    public void afficherMain() {
        for(int i = 0; i < 8; i++) {
        ((ImageView) root.getChildren().get(6 + i)).setImage(cartes[i].getImage());
        }
    }

    public void trier() {
        List<Carte>[] listedetri = new ArrayList[5];
        //On initialise les listes pour chaque couleur
        for(int i = 0 ; i < 5 ; i++) {
            listedetri[i] = new ArrayList<>();
        }
        //On distribue les cartes dans les bonnes listes
        for(Carte carte : cartes) {
            listedetri[carte.getId_couleur()].add(carte);
        }
        //On trie chaque liste
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
        //On réunit les cartes
        for(List<Carte> liste : listedetri) {
            if(liste.size() > 0) {
                for(Carte carte : liste) {
                    this.cartes[indice] = carte;
                    indice++;
                }
            }
        }
    }

    public int ajouterCarte(Carte nouvelleCarte) {
        //System.out.println(carteSelectionnee);
        int indice;
        for(int i = 0; i < 8; i++) {
            //On récupère le bon indice
            if(cartes[i].getValeur() == -1) {
                indice = i + 1;
            } else {
                indice = i;
            }/*
            if(nouvelleCarte.getId_couleur() == cartes[indice].getId_couleur() &&
                nouvelleCarte.getValeur() == nouvelleCarte.getValeur()) {
                return indice;
            } else */
            //Si la nouvelle carte est plus grande que la carte considérée...
            if (nouvelleCarte.getId_couleur() < cartes[indice].getId_couleur() ||
                    nouvelleCarte.getId_couleur() == cartes[indice].getId_couleur()
                    && nouvelleCarte.getValeur() <= cartes[indice].getValeur()) {
                if(carteSelectionnee < indice) {
                    if(i == 0) {
                        return 0;
                    }
                    if (cartes[i].getValeur() == -1) {
                        return i;
                    }
                    return i - 1;
                }
                return i;
            }
        }
        System.out.println("problème");
        return 7;
    }

    public int getCarteSelectionneeId() {
        return carteSelectionnee;
    }

    public void resetCarteSelectionee() {
        carteSelectionnee = -1;
    }

    public Carte getCarteSelectionnee() {
        return cartes[carteSelectionnee];
    }

    public void setCarteSelectionnee(int valeur) {
        carteSelectionnee = valeur;
    }

    public void supprimerCarte(int indice) {
        cartes[indice] = new Carte(-1,"vide");
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
