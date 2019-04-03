package Jeu.MultiJoueur.Model;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ThreadReceptionClient {
    private Thread rejoindrePartie;
    private Carcassonne carcassonne;
    private SocketJoueur socketJoueur;
    private boolean isArreter;
    private int idList;

    public ThreadReceptionClient(Carcassonne carcassonne, SocketJoueur socketJoueur, int idList){
        isArreter = false;
        this.carcassonne = carcassonne;
        this.socketJoueur = socketJoueur;
        this.idList = idList;
        rejoindrePartie = new Thread(new ThreadReceptionClient.TraitementReception());
        rejoindrePartie.start();
    }

    private class TraitementReception implements Runnable{
        public void run(){
            ObjectInputStream oi = socketJoueur.getOi();
            do try {
                if ((oi.readObject()).equals("j'envoie")){
                    Object objetRecu = oi.readObject();
                    if(objetRecu instanceof String){
                        String string = (String) objetRecu;
                        if(string.equals("pret")){
                            if(carcassonne.getListJoueur().get(idList).isPret()){
                                carcassonne.getListJoueur().get(idList).setPret(false);
                                carcassonne.miseAJourJoueur();
                            }
                            else{
                                carcassonne.getListJoueur().get(idList).setPret(true);
                                carcassonne.miseAJourJoueur();
                            }
                        }
                        else if(string.equals("quitter")){
                            carcassonne.quitterClient(idList);
                            carcassonne.miseAJourJoueur();
                        }
                        else if(string.equals("defausse")){
                            if(carcassonne.isDefaussable()) carcassonne.defausser();
                            else envoieErreur("La carte peut être possé", "Défausse interdit");
                        }
                        else if(string.equals("poserCarte")){
                            Point position = (Point) oi.readObject();
                            carcassonne.setCarteCourante((Carte) oi.readObject());
                            Carte c = carcassonne.getCarteCourante();
                            System.out.println("Sud: " + c.getSud() + ", Ouest: " + c.getOuest() + ", Nord: " + c.getNord() + ", Est: " + c.getEst());
                            if (carcassonne.getListPointOccupe().contains(position)) {
                                envoieErreur("Une carte est déjà placée à cet endroit", "Placement de carte impossible");
                            } else if (carcassonne.isCarteAdjacente((int) position.getX(), (int) position.getY())) {
                                if (carcassonne.isPlacable((int) position.getX(), (int) position.getY())) {
                                    carcassonne.placerCarte(position);
                                }
                                else {
                                    envoieErreur("La carte ne coïncide pas avec la carte adjacente", "Placement de carte impossible");
                                }
                            }
                            else {
                                envoieErreur("La carte ne peut pas être placée à cet endroit", "Placement de carte impossible");
                            }
                        }
                        else if(string.equals("poserPartisant")){
                            int numZone = oi.readInt();
                            System.out.println("numZone: " + numZone);

                            if (numZone==-1){
                                envoieErreur("Votre partisan doit être placé sur une des positions affichées", "Placement de partisan impossible");
                            } else {
                                if (zonePasEncoreOccupee(numZone)) {
                                    carcassonne.placerPartisan(numZone);
                                } else {
                                    envoieErreur("Il y a un partisan dans la même zone que celle que vous avez choisie", "Placement de partisan impossible");
                                }
                            }
                        }
                        else if(string.equals("tourSuivant")){
                            carcassonne.joueurSuivant();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } while(!isArreter);
        }
    }

    private boolean zonePasEncoreOccupee(int numZone) {
        CartePosee c = carcassonne.getCarteCourantePosee();
        for (int i = 0; i < c.getZonesControlleesParLesPoints().length ; i++) {
            if (i==numZone){
                for (int j = 0; j < c.getZonesControlleesParLesPoints()[i].length ; j++) {
                    if (c.getZonesCouleurPartisan().containsKey(c.getZonesControlleesParLesPoints()[i][j])){
                        if(c.getZonesCouleurPartisan().get(c.getZonesControlleesParLesPoints()[i][j]) != carcassonne.getJoueurCourant().getCouleur()) {
                            return false;
                        }
                        else return true;
                    }
                }
            }
        }
        return true;
    }

    public void envoieErreur(String erreur, String titre){
        ObjectOutputStream oo = socketJoueur.getOo();
        try{
            oo.writeObject("erreur");
            oo.writeObject(titre);
            oo.writeObject(erreur);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void arreter(){
        isArreter = true;
    }

    public void setIdList(int idList) { this.idList = idList; }

    public int getIdList() { return idList; }
}
