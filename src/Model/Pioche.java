package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pioche {
    private List<Carte> cartes = new ArrayList<>();

    public Pioche() {
        String[] couleurs = {"jaune","bleu","blanc","rouge","vert"};
        for(String couleur : couleurs) {
            for(int i = 0; i < 3; i++) {
                this.cartes.add(new Carte(0,couleur));
            }
            for(int i = 0; i < 10; i++) {
                this.cartes.add(new Carte(i+1, couleur));
            }
        }
        Collections.shuffle(this.cartes);
    }

    public Carte piocher(){
        Carte carte = this.cartes.get(0);
        this.cartes.remove(0);
        return carte;
    }

    public void etat() {
        System.out.println("Longueur : " + cartes.size());
        System.out.println("Premières cartes : ");
        for(int i = 0; i < 8; i++) {
            System.out.println(cartes.get(i).getValeur()+cartes.get(i).getCouleur() + " ");
        }
    }
}
