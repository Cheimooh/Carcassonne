package Jeu;

import Jeu.Model.Carcassonne;
import Jeu.ModelServeur.Joueur;
import Jeu.ModelServeur.SocketJoueur;
import Jeu.View.FenetreJeu;
import Jeu.View.PopUpPartisan;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MenuReseau extends Parent {
    private Stage primaryStage;
    private int nombreJoueur;
    private SocketJoueur socketJoueur;
    private List<Joueur> listJoueurs;
    private List<Color> listColorJoueursReseau;
    private String nomJoueurTmpReseau;

    //les boutton radio de la fenetre de selection des couleurs
    private RadioButton t_rouge = new RadioButton();
    private RadioButton t_bleu = new RadioButton();
    private RadioButton t_rose = new RadioButton();
    private RadioButton t_jaune = new RadioButton();
    private RadioButton t_bleuClaire = new RadioButton();

    //les rectangles de la fenetre de selection des couleurs
    private Rectangle r_rouge = new Rectangle(30, 30, Color.RED);
    private Rectangle r_bleu = new Rectangle(30, 30, Color.BLUE);
    private Rectangle r_rose = new Rectangle(30, 30, Color.HOTPINK);
    private Rectangle r_jaune = new Rectangle(30, 30, Color.GOLD);
    private Rectangle r_bleuClaire = new Rectangle(30, 30, Color.DEEPSKYBLUE);
    final Image image = new Image("Jeu/valider.png", 50,50,true,true);

    private Color couleurJoueurTmp;
    private List<HBox> listHBoxElement;
    private VBox generals;
    private HBox hBoxTitres;
    private HBox hBoxButtons;
    private boolean nomJoueursCorrect;

    public MenuReseau(Stage primaryStage) {
        nombreJoueur = 0;
        listJoueurs = new ArrayList<>();
        listColorJoueursReseau = new ArrayList<>();
        listHBoxElement = new ArrayList<>();
        nomJoueursCorrect = true;
        this.primaryStage = primaryStage;
    }

    public void jeuInternet() {
        try {
            //Socket sock = new Socket("62.39.234.71", 3333);
            Socket sock = new Socket("localhost", 3333);
            ObjectOutputStream oo = new ObjectOutputStream(sock.getOutputStream());
            ObjectInputStream oi = new ObjectInputStream(sock.getInputStream());

            socketJoueur = new SocketJoueur(sock, oi, oo);

            nombreJoueur = oi.readInt();
            for (int i = 0; i < nombreJoueur; i++) {
                Joueur joueur = (Joueur) oi.readObject();
                listJoueurs.add(joueur);
                listColorJoueursReseau.add(Menu.tradStringToColors(joueur.getCouleur()));
            }
            inscriptionJoueurReseau();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void inscriptionJoueurReseau() {
        Label erreurNom = new Label("Ce nom est ou déja pris, ou null");
        erreurNom.setStyle("-fx-text-fill: RED");

        VBox generalBox = new VBox(10);
        generalBox.setAlignment(Pos.CENTER);

        HBox HBname = new HBox(10);
        HBname.setAlignment(Pos.CENTER);

        Label lNom = new Label("Nom: ");
        TextField tNom = new TextField("Entrez votre nom");

        HBname.getChildren().addAll(lNom, tNom);
        Label lcolor = new Label("Couleur:");

        VBox v_rouge = new VBox(3);
        v_rouge.setAlignment(Pos.CENTER);

        VBox v_bleu = new VBox(3);
        v_bleu.setAlignment(Pos.CENTER);

        VBox v_rose = new VBox(3);
        v_rose.setAlignment(Pos.CENTER);

        VBox v_jaune = new VBox(3);
        v_jaune.setAlignment(Pos.CENTER);

        VBox v_bleuClaire = new VBox(3);
        v_bleuClaire.setAlignment(Pos.CENTER);

        ToggleGroup toggleGroup = new ToggleGroup();
        t_rouge.setToggleGroup(toggleGroup);
        t_bleu.setToggleGroup(toggleGroup);
        t_rose.setToggleGroup(toggleGroup);
        t_jaune.setToggleGroup(toggleGroup);
        t_bleuClaire.setToggleGroup(toggleGroup);

        v_rouge.getChildren().addAll(t_rouge, r_rouge);
        v_bleu.getChildren().addAll(t_bleu, r_bleu);
        v_rose.getChildren().addAll(t_rose, r_rose);
        v_jaune.getChildren().addAll(t_jaune, r_jaune);
        v_bleuClaire.getChildren().addAll(t_bleuClaire, r_bleuClaire);

        HBox bouttons = new HBox(10);
        bouttons.setAlignment(Pos.CENTER);
        bouttons.getChildren().addAll(v_rouge, v_bleu, v_rose, v_jaune, v_bleuClaire);

        for (Color couleurJoueur : listColorJoueursReseau) {
            if (couleurJoueur != null && couleurJoueur == Color.DEEPSKYBLUE)
                bouttons.getChildren().remove(v_bleuClaire);
            if (couleurJoueur != null && couleurJoueur == Color.RED)
                bouttons.getChildren().remove(v_rouge);
            if (couleurJoueur != null && couleurJoueur == Color.BLUE)
                bouttons.getChildren().remove(v_bleu);
            if (couleurJoueur != null && couleurJoueur == Color.HOTPINK)
                bouttons.getChildren().remove(v_rose);
            if (couleurJoueur != null && couleurJoueur == Color.GOLD)
                bouttons.getChildren().remove(v_jaune);
        }

        Button b_suivant = new Button("Suivant");
        b_suivant.setOnAction(event -> {
            couleurJoueurTmp = recupColorsToggle(toggleGroup.getSelectedToggle());
            nomJoueurTmpReseau = tNom.getText();
            verifJoueurReseau();
        });

        generalBox.getChildren().add(HBname);
        if (!nomJoueursCorrect) {
            generalBox.getChildren().addAll(erreurNom, lcolor, bouttons, b_suivant);
            nomJoueursCorrect = true;
        } else {
            generalBox.getChildren().addAll(lcolor, bouttons, b_suivant);
        }

        Scene scene = new Scene(generalBox, 350, 300);
        primaryStage.setScene(scene);
    }

    private void verifJoueurReseau() {
        if(!(nomJoueurTmpReseau.length() > 0 && nomJoueurTmpReseau.length() <= 16)){
            nomJoueursCorrect = false;
        }
        if(couleurJoueurTmp == null){
            nomJoueursCorrect = false;
        }
        if(!nomJoueursCorrect){
            inscriptionJoueurReseau();
        }
        else{
            listJoueurs.add(new Joueur(nomJoueurTmpReseau, tradColorsToString(couleurJoueurTmp)));
            nombreJoueur++;
            transmitInfoServeur();
        }
    }

    private void transmitInfoServeur() {
        try {
            socketJoueur.getOo().writeObject(listJoueurs.get(nombreJoueur - 1));
            salonAttente();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String tradColorsToString(Color couleurs) {
        if (couleurs == Color.BLUE) return "blue";
        if (couleurs == Color.RED) return "red";
        if (couleurs == Color.GOLD) return "jaune";
        if (couleurs == Color.HOTPINK) return "rose";
        if (couleurs == Color.DEEPSKYBLUE) return "bleuClair";
        return null;
    }

    private void salonAttente() {
        generals = new VBox(10);
        generals.setAlignment(Pos.CENTER);

        hBoxTitres = new HBox(30);

        VBox vBoxNoms = new VBox(10);
        vBoxNoms.setAlignment(Pos.CENTER);

        hBoxTitres.setAlignment(Pos.CENTER);

        HBox elements = new HBox();
        elements.setAlignment(Pos.CENTER);


        VBox vBoxCouleurs = new VBox(10);
        vBoxCouleurs.setAlignment(Pos.CENTER);

        VBox vBoxPrets = new VBox(10);
        vBoxPrets.setAlignment(Pos.CENTER);

        hBoxButtons = new HBox(10);
        hBoxButtons.setAlignment(Pos.CENTER);
        Button b_quitter = new Button("Quitter");
        Button b_pret = new Button("Prêt !");
        b_pret.setOnAction(event -> {
            try {
                socketJoueur.getOo().writeObject("j'envoie");
                socketJoueur.getOo().writeObject("pret");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        hBoxButtons.getChildren().addAll(b_quitter, b_pret);

        HBox hBoxInfosJoueurs = new HBox(10);
        hBoxInfosJoueurs.setAlignment(Pos.CENTER);

        Label noms = new Label("Noms: ");
        hBoxTitres.getChildren().add(noms);

        Label couleurs = new Label("Couleurs: ");
        hBoxTitres.getChildren().add(couleurs);

        Label prets = new Label("Prêt: ");
        hBoxTitres.getChildren().add(prets);

        for (int i = 0; i < nombreJoueur; i++) {
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER);

            Label labelJoueur = new Label(listJoueurs.get(i).getNom());
            Rectangle colorJoueur = new Rectangle(30, 30, tradStringToColors(listJoueurs.get(i).getCouleur()));

            vBoxNoms.getChildren().add(labelJoueur);
            vBoxCouleurs.getChildren().add(colorJoueur);

            hBox.getChildren().add(labelJoueur);
            hBox.getChildren().add(colorJoueur);
            if (listJoueurs.get(i).isPret()){
                hBox.getChildren().add(new ImageView(image));
            }
            listHBoxElement.add(hBox);
        }
        generals.getChildren().add(hBoxTitres);

        for (HBox aListHBoxElement : listHBoxElement) {
            generals.getChildren().add(aListHBoxElement);
        }
       generals.getChildren().add(hBoxButtons);

        new ThreadSalonAttente(this);

        Scene scene = new Scene(generals, 500, 500);
        primaryStage.setScene(scene);
    }

    public void actualiser() {
        System.out.println("actualisation......");
        Platform.runLater(() ->  generals.getChildren().clear());
        listHBoxElement.clear();
        for (int i = 0; i < nombreJoueur; i++) {

            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER);

            Label labelJoueur = new Label(listJoueurs.get(i).getNom());
            Rectangle colorJoueur = new Rectangle(30, 30, tradStringToColors(listJoueurs.get(i).getCouleur()));

            hBox.getChildren().add(labelJoueur);
            hBox.getChildren().add(colorJoueur);
            System.out.println(listJoueurs.get(i).isPret());
            if (listJoueurs.get(i).isPret()){
                hBox.getChildren().add(new ImageView(image));
            }
            listHBoxElement.add(hBox);
        }
        Platform.runLater(() -> generals.getChildren().add(hBoxTitres));

        for (HBox aListHBoxElement : listHBoxElement) {
            Platform.runLater(() -> generals.getChildren().add(aListHBoxElement));
        }
        Platform.runLater(() -> generals.getChildren().add(hBoxButtons));
    }

    public static Color tradStringToColors(String couleur) {
        if (couleur.equals("blue")) return Color.BLUE;
        if (couleur.equals("red")) return Color.RED;
        if (couleur.equals("jaune")) return Color.GOLD;
        if (couleur.equals("bleuClair")) return Color.DEEPSKYBLUE;
        if (couleur.equals("rose")) return Color.HOTPINK;
        else return null;
    }

    private Color recupColorsToggle(Toggle toggle) {
        if (toggle != null) {
            if (toggle.equals(t_rouge)) return Color.RED;
            if (toggle.equals(t_bleu)) return Color.BLUE;
            if (toggle.equals(t_rose)) return Color.HOTPINK;
            if (toggle.equals(t_jaune)) return Color.GOLD;
            if (toggle.equals(t_bleuClaire)) return Color.DEEPSKYBLUE;
        }
        return null;
    }

    public SocketJoueur getSocketJoueur() {
        return socketJoueur;
    }

    public List<Joueur> getListJoueurs() {
        return listJoueurs;
    }

    public void setNombreJoueur(int nombreJoueur) {
        this.nombreJoueur = nombreJoueur;
    }

    public List<Color> getListColorJoueursReseau() {
        return listColorJoueursReseau;
    }

    public void setListColorJoueursReseau(ArrayList<Color> listColorJoueursReseau) {
        this.listColorJoueursReseau = listColorJoueursReseau;
    }
}
