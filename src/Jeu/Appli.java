package Jeu;

import Jeu.Model.Carcassonne;
import Jeu.View.FenetreJeu;
import Jeu.View.PopUpPartisant;
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

    private Carcassonne carcassonne;
    private Stage primaryStage;
    private int nombreJoueur;
    private int nombreJoueur2 = 0;
    private String[] tabNomjoueurs;
    private Color[] tabColorJoueurs;
    private boolean nomJoueursCorrect = true;
    private boolean colorJoueursCorrect = true;

    //les boutton radio de la fenetre de selection des couleurs
    private RadioButton t_rouge = new RadioButton();
    private RadioButton t_vert = new RadioButton();
    private RadioButton t_chartreuse = new RadioButton();
    private RadioButton t_bleu = new RadioButton();
    private RadioButton t_rose = new RadioButton();
    private RadioButton t_violet = new RadioButton();
    private RadioButton t_jaune = new RadioButton();
    private RadioButton t_bleuClaire = new RadioButton();

    @Override
    public void start(Stage primaryStage){
        carcassonne = new Carcassonne();
        primaryStage.setTitle("Carcassonne");
        this.primaryStage = primaryStage;
        menuDepart();
    }

    private void menuDepart() {
        VBox boutonsMenu = new VBox(10);
        boutonsMenu.setAlignment(Pos.CENTER);
        Button jouer = new Button("Jouer");
        Button scores = new Button("Scores");
        Button reglage = new Button("Reglages");
        Button quitter = new Button("Quitter");
        boutonsMenu.getChildren().addAll(jouer,scores,reglage,quitter);
        jouer.setOnAction(event -> askNbJoueurs());
        quitter.setOnAction(event -> System.exit(0));
        Scene scene = new Scene(boutonsMenu, 350,300);
        primaryStage.setScene(scene);
        primaryStage.show();


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
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(l_NombreJoueurs,comboBox,b_suivant);
        Scene scene = new Scene(vBox,350,300);
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
                if (!verifNom(nomJoueur)) {
                    nomJoueursCorrect = false;
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
        if (!nomJoueursCorrect || !colorJoueursCorrect){
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
        Label erreurNom = new Label("Le nom doit être:\n- différent des noms déja données\n- non vide");
        erreurNom.setStyle("-fx-text-fill: RED");
        Label erreurCouleur = new Label("La couleur doit être:\n- différente des couleurs déja sélectionnée\n- non null");
        erreurCouleur.setStyle("-fx-text-fill: RED");
        Rectangle r_rouge = new Rectangle(30,30,Color.RED);
        Rectangle r_vert = new Rectangle(30,30,Color.GREEN);
        Rectangle r_chartreuse = new Rectangle(30,30,Color.CHARTREUSE);
        Rectangle r_bleu = new Rectangle(30,30,Color.BLUE);
        Rectangle r_rose = new Rectangle(30,30,Color.PINK);
        Rectangle r_violet = new Rectangle(30,30,Color.BLUEVIOLET);
        Rectangle r_jaune = new Rectangle(30,30,Color.YELLOW);
        Rectangle r_bleuClaire = new Rectangle(30,30,Color.LIGHTBLUE);
        if (colorJoueursCorrect == true){
            t_rouge.setSelected(false);
            t_vert.setSelected(false);
            t_chartreuse.setSelected(false);
            t_bleu.setSelected(false);
            t_rose.setSelected(false);
            t_violet.setSelected(false);
            t_jaune.setSelected(false);
            t_bleuClaire.setSelected(false);
        }
        VBox v_rouge = new VBox(5);
        v_rouge.setAlignment(Pos.CENTER);
        VBox v_vert = new VBox(5);
        v_vert.setAlignment(Pos.CENTER);
        VBox v_chartreuse = new VBox(5);
        v_chartreuse.setAlignment(Pos.CENTER);
        VBox v_bleu = new VBox(5);
        v_bleu.setAlignment(Pos.CENTER);
        VBox v_rose = new VBox(5);
        v_rose.setAlignment(Pos.CENTER);
        VBox v_violet = new VBox(5);
        v_violet.setAlignment(Pos.CENTER);
        VBox v_jaune = new VBox(5);
        v_jaune.setAlignment(Pos.CENTER);
        VBox v_bleuClaire = new VBox(5);
        v_bleuClaire.setAlignment(Pos.CENTER);
        v_rouge.getChildren().addAll(t_rouge,r_rouge);
        v_vert.getChildren().addAll(t_vert,r_vert);
        v_chartreuse.getChildren().addAll(t_chartreuse,r_chartreuse);
        v_bleu.getChildren().addAll(t_bleu,r_bleu);
        v_rose.getChildren().addAll(t_rose,r_rose);
        v_violet.getChildren().addAll(t_violet,r_violet);
        v_jaune.getChildren().addAll(t_jaune,r_jaune);
        v_bleuClaire.getChildren().addAll(t_bleuClaire,r_bleuClaire);
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        HBox bouttons = new HBox(10);
        bouttons.setAlignment(Pos.CENTER);
        HBox rectangle = new HBox(10);
        rectangle.setAlignment(Pos.CENTER);
        Label l_nomjoueur = new Label("Saisissez le nom du joueur "+(nombreJoueur2+1));
        Label l_couleurJoueur = new Label("Selectionnez la couleur du joueur "+(nombreJoueur2+1));
        TextField t_nomJoueur;
        if (nombreJoueur2 <= nombreJoueur && tabNomjoueurs[nombreJoueur2] != null){
            t_nomJoueur = new TextField(tabNomjoueurs[nombreJoueur2]);
        }else {
            t_nomJoueur = new TextField();
        }
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
        bouttons.getChildren().addAll(v_rouge,v_vert,v_chartreuse,v_bleu,v_rose,v_violet,v_jaune,v_bleuClaire);
        if (colorJoueursCorrect == true){
            if (nomJoueursCorrect == true) {
                for (int i = 0; i < tabColorJoueurs.length; i++) {
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.RED)
                        bouttons.getChildren().remove(v_rouge);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.GREEN)
                        bouttons.getChildren().remove(v_vert);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.CHARTREUSE)
                        bouttons.getChildren().remove(v_chartreuse);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.BLUE)
                        bouttons.getChildren().remove(v_bleu);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.PINK)
                        bouttons.getChildren().remove(v_rose);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.BLUEVIOLET)
                        bouttons.getChildren().remove(v_violet);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.YELLOW)
                        bouttons.getChildren().remove(v_jaune);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.LIGHTBLUE)
                        bouttons.getChildren().remove(v_bleuClaire);
                }
            }else{
                for (int i = 0; i <= nombreJoueur2-1; i++) {
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.RED)
                        bouttons.getChildren().remove(v_rouge);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.GREEN)
                        bouttons.getChildren().remove(v_vert);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.CHARTREUSE)
                        bouttons.getChildren().remove(v_chartreuse);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.BLUE)
                        bouttons.getChildren().remove(v_bleu);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.PINK)
                        bouttons.getChildren().remove(v_rose);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.BLUEVIOLET)
                        bouttons.getChildren().remove(v_violet);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.YELLOW)
                        bouttons.getChildren().remove(v_jaune);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.LIGHTBLUE)
                        bouttons.getChildren().remove(v_bleuClaire);
                }
            }
        }
        if (nomJoueursCorrect == false)vBox.getChildren().add(erreurNom);
        vBox.getChildren().add(l_couleurJoueur);
        if (colorJoueursCorrect==false)vBox.getChildren().add(erreurCouleur);
        vBox.getChildren().addAll(bouttons,b_suivant);
        b_suivant.setOnAction(event -> askInfosJoueurs(nombreJoueur2+1, t_nomJoueur.getText(), recupColorsToggle(toggleGroup.getSelectedToggle())));
        nomJoueursCorrect= true;
        colorJoueursCorrect=true;
        Scene scene = new Scene(vBox, 350,300);
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
        PopUpPartisant popUpPartisant = new PopUpPartisant(primaryStage);
        FenetreJeu fenetreJeu = new FenetreJeu(carcassonne, WIDTH, HEIGHT, popUpPartisant);
        root.getChildren().add(fenetreJeu);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT, Color.LIGHTGREY));
        primaryStage.show();
        carcassonne.jouer();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
