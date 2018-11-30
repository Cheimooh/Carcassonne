package Jeu;

import Jeu.Model.Carcassonne;
import Jeu.View.FenetreJeu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Appli extends Application {

    Carcassonne carcassonne;
    Stage primaryStage;
    int nombreJoueur;
    int nombreJoueur2 = 0;
    String[] tabNomjoueurs;
    Color[] tabColorJoueurs;
    boolean nomJoueursCorrect = true;
    boolean colorJoueursCorrect = true;

    //les boutton radio de la fenetre de selection des couleurs
    RadioButton t_rouge = new RadioButton();
    RadioButton t_vert = new RadioButton();
    RadioButton t_chartreuse = new RadioButton();
    RadioButton t_bleu = new RadioButton();
    RadioButton t_rose = new RadioButton();
    RadioButton t_violet = new RadioButton();
    RadioButton t_jaune = new RadioButton();
    RadioButton t_bleuClaire = new RadioButton();

    @Override
    public void start(Stage primaryStage){
        carcassonne = new Carcassonne();
        primaryStage.setTitle("Carcassonne");
        this.primaryStage = primaryStage;
        askNbJoueurs();
    }

    private void askNbJoueurs(){
        Label l_NombreJoueurs = new Label("Nombre de joueur : ");

        int[] options = new int[]{2,3,4,5};
        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setPromptText("Choississez le nombre de joueurs");
        for (int i = 0; i < options.length; i++) {
            comboBox.getItems().add(options[i]);
        }
        Button b_suivant = new Button("Suivant");
        b_suivant.setOnAction(event -> initialiseNbjoueurs(comboBox.getSelectionModel().getSelectedItem()));
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(l_NombreJoueurs,comboBox,b_suivant);
        Scene scene = new Scene(vBox,500,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initialiseNbjoueurs(int selectedItem) {
        nombreJoueur = selectedItem;
        tabNomjoueurs = new String[nombreJoueur];
        tabColorJoueurs = new Color[nombreJoueur];
        askInfosJoueurs(nombreJoueur2, "", Color.GRAY);
    }

    private void askInfosJoueurs(int nombreJoueur22, String nomJoueur, Color color) {
        nombreJoueur2=nombreJoueur22;
        if (nombreJoueur2>0) {
            System.out.println("nombreJoueurs2 = "+nombreJoueur2);
                if (!verifNom(nomJoueur)) {
                    nomJoueursCorrect = false;
                }else{
                    System.out.println("nom du joueur: "+tabNomjoueurs[nombreJoueur2-1]);
                }
            tabNomjoueurs[nombreJoueur2 - 1] = nomJoueur;
            if (color != null) {
                tabColorJoueurs[nombreJoueur2 - 1] = color;
                if (!verifColor(color)) {
                    colorJoueursCorrect = false;
                }
            }else {
                colorJoueursCorrect = false;
            }
        }
        if (nombreJoueur2<1 && color == null)colorJoueursCorrect=false;
        if (nomJoueursCorrect==false || colorJoueursCorrect==false){
            nombreJoueur2--;
        }
        if (nombreJoueur2<nombreJoueur)afficherFenetreInfosJoueurs();
        else {
            afficherFenetreJeu();
        }
    }

    private boolean verifColor(Color tabColorJoueur) {
        if (nombreJoueur2-1>0) {
            for (int i = 0; i < nombreJoueur2 - 1; i++) {
                if (tabColorJoueur.equals(tabColorJoueurs[i])) return false;
            }
        }
        return true;
    }

    private boolean verifNom(String nomJoueur) {
        if (nomJoueur==null || nomJoueur.equals(""))return false;
        if (nombreJoueur2>1) {
            for (int i = 0; i < nombreJoueur2 - 1; i++) {
                if (nomJoueur.equals(tabNomjoueurs[i])) return false;
            }
        }
        return true;
    }

    private void afficherFenetreInfosJoueurs() {
        Button b_suivant = new Button("suivant");
        Label erreurNom = new Label("Le nom doit être différent des noms déja données et non vide");
        erreurNom.setStyle("-fx-text-fill: RED");
        Label erreurCouleur = new Label("La couleur doit être différente des couleurs déja sélectionnée et non null");
        erreurCouleur.setStyle("-fx-text-fill: RED");
        Rectangle r_rouge = new Rectangle(30,30,Color.RED);
        Rectangle r_vert = new Rectangle(30,30,Color.GREEN);
        Rectangle r_chartreuse = new Rectangle(30,30,Color.CHARTREUSE);
        Rectangle r_bleu = new Rectangle(30,30,Color.BLUE);
        Rectangle r_rose = new Rectangle(30,30,Color.PINK);
        Rectangle r_violet = new Rectangle(30,30,Color.BLUEVIOLET);
        Rectangle r_jaune = new Rectangle(30,30,Color.YELLOW);
        Rectangle r_bleuClaire = new Rectangle(30,30,Color.LIGHTBLUE);
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        HBox bouttons = new HBox(10);
        bouttons.setAlignment(Pos.CENTER);
        HBox rectangle = new HBox(10);
        rectangle.setAlignment(Pos.CENTER);
        Label l_nomjoueur = new Label("Saisissez le nom du joueur");
        Label l_couleurJoueur = new Label("Selectionnez la couleur du joueur");
        TextField t_nomJoueur;
        if (nombreJoueur2 <= nombreJoueur && tabNomjoueurs[nombreJoueur2] != null){
            t_nomJoueur = new TextField(tabNomjoueurs[nombreJoueur2]);
            System.out.println("je t'ai mis l'ancien nom donnée");
        }else {
            t_nomJoueur = new TextField();
        }
        System.out.println("position nombreJoueur2 : "+nombreJoueur2);
        vBox.getChildren().addAll(l_nomjoueur,t_nomJoueur);
        ToggleGroup toggleGroup = new ToggleGroup();
        t_rouge.setToggleGroup(toggleGroup);
        t_vert.setToggleGroup(toggleGroup);
        t_chartreuse.setToggleGroup(toggleGroup);
        t_bleu.setToggleGroup(toggleGroup);
        t_rose.setToggleGroup(toggleGroup);
        t_violet.setToggleGroup(toggleGroup);
        t_jaune.setToggleGroup(toggleGroup);
        t_bleuClaire.setToggleGroup(toggleGroup);
        bouttons.getChildren().addAll(t_rouge,t_vert,t_chartreuse,t_bleu,t_rose,t_violet,t_jaune,t_bleuClaire);
        rectangle.getChildren().addAll(r_rouge,r_vert,r_chartreuse,r_bleu,r_rose,r_violet,r_jaune,r_bleuClaire);
        if (nomJoueursCorrect == false)vBox.getChildren().add(erreurNom);
        vBox.getChildren().add(l_couleurJoueur);
        if (colorJoueursCorrect==false)vBox.getChildren().add(erreurCouleur);
        vBox.getChildren().addAll(bouttons,rectangle,b_suivant);
        b_suivant.setOnAction(event -> askInfosJoueurs(nombreJoueur2+1, t_nomJoueur.getText(), recupColorsToggle(toggleGroup.getSelectedToggle())));
        nomJoueursCorrect= true;
        colorJoueursCorrect=true;
        Scene scene = new Scene(vBox, 500,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Color recupColorsToggle(Toggle toggle) {
        if (toggle != null) {
            if (toggle.equals(t_rouge)) return Color.RED;
            if (toggle.equals(t_vert)) return Color.GREEN;
            if (toggle.equals(t_chartreuse)) return Color.CHARTREUSE;
            if (toggle.equals(t_bleu)) return Color.BLUE;
            if (toggle.equals(t_rose)) return Color.PINK;
            if (toggle.equals(t_violet)) return Color.BLUEVIOLET;
            if (toggle.equals(t_jaune)) return Color.YELLOW;
            if (toggle.equals(t_bleuClaire)) return Color.LIGHTBLUE;
        }
        return null;

    }


    private void afficherFenetreJeu(){
        carcassonne.initialisationJoueurs(tabNomjoueurs,tabColorJoueurs);
        Group root = new Group();
        int WIDTH = 1000;
        int HEIGHT = 700;
        FenetreJeu fenetreJeu = new FenetreJeu(carcassonne, WIDTH, HEIGHT);
        root.getChildren().add(fenetreJeu);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT, Color.LIGHTGREY));
        primaryStage.show();
        carcassonne.jouer();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
