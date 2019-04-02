package Jeu.MultiJoueur.Controller;

import Jeu.MultiJoueur.View.MenuReseau;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ControlMousePartisan implements EventHandler<MouseEvent> {
    private MenuReseau menuReseau;

    public ControlMousePartisan(MenuReseau menuReseau){
        this.menuReseau = menuReseau;
    }

    /*
     * S'active si l'on clique sur un endroit de la fenêtre de jeu
     */
    @Override
    public void handle(MouseEvent event) {
        verifPlacerPartisan(event);
    }

    /*
     * Si l'on doit placer un partisan sur la fenêtre, on appelle cette fonction
     */
    private void verifPlacerPartisan(MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        // Si l'on clique pas sur une zone indiquée
        int numZone = getZonePlacementPartisan(x, y);
        System.out.println("numZone: " + numZone);
        ObjectOutputStream oo = menuReseau.getSocketJoueur().getOo();
        try {
            oo.writeObject("j'envoie");
            oo.writeObject("poserPartisant");
            oo.writeInt(numZone);
            oo.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Récupère la zone où l'on clique
     */
    private int getZonePlacementPartisan(int x, int y) {
        int numZone=-1;
        for (int i = 0; i < menuReseau.getCarteCourante().getPositionsCoordonnees().size() ; i++) {
            double xPartisan = menuReseau.getCarteCourante().getPositionsCoordonnees().get(i).getX()*10;
            double yPartisan = menuReseau.getCarteCourante().getPositionsCoordonnees().get(i).getY()*10;
            // Si l'on clique dans les cercles
            if(x>xPartisan-10 && x<xPartisan+10 && y>yPartisan-10 && y<yPartisan+10){
                numZone=i;
            }
        }
        return numZone;
    }
}
