package Jeu.MultiJoueur;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;

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
                            carcassonne.isDefaussable();
                        }
                        else if(string.equals("poserCarte")){
                            Point position = (Point) oi.readObject();
                            if (carcassonne.getListPointOccupe().contains(position)) {
                                // envoie erreur ("Une carte est déjà placée à cet endroit", "Placement de carte impossible")
                            } else if (carcassonne.isCarteAdjacente(position.x, position.y)) {
                                if (carcassonne.isPlacable(position.x, position.y)) {
                                    carcassonne.getCarteCourante().setPosition(position);
                                }
                                else {
                                    // envoie erreur ("La carte ne coïncide pas avec la carte adjacente", "Placement de carte impossible")
                                }
                            }
                            else {
                                // envoie erreur ("La carte ne peut pas être placée à cet endroit", "Placement de carte impossible")
                            }
                        }
                        else if(string.equals("poserPartisant")){
                            System.out.println("PoserPartisant");
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

    public void arreter(){
        isArreter = true;
    }

    public void setIdList(int idList) { this.idList = idList; }

    public int getIdList() { return idList; }
}
