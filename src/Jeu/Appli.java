package Jeu;

import Jeu.Model.Carcassonne;
import Jeu.View.FenetreJeu;
import Jeu.View.PopUpPartisan;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private RadioButton t_bleu = new RadioButton();
    private RadioButton t_rose = new RadioButton();
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
        Button quitter = new Button("Quitter");
        boutonsMenu.getChildren().addAll(jouer,quitter);
        jouer.setOnAction(event -> choixTypeJeu());
        quitter.setOnAction(event -> System.exit(0));
        Scene scene = new Scene(boutonsMenu, 350,300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void choixTypeJeu() {
        VBox boutonsMenu = new VBox(10);
        boutonsMenu.setAlignment(Pos.CENTER);
        Button internet = new Button("Internet");
        Button local = new Button("Local");
        boutonsMenu.getChildren().addAll(internet,local);
        internet.setOnAction(event -> jeuInternet());
        local.setOnAction(event -> askNbJoueurs());
        Scene scene = new Scene(boutonsMenu, 350,300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void jeuInternet() {
        instancierJoueur();
    }

    private void instancierJoueur() {
        VBox generalBox = new VBox(10);
        generalBox.setAlignment(Pos.CENTER);
        HBox HBname = new HBox(10);
        HBname.setAlignment(Pos.CENTER);
        Label lNom = new Label("Nom: ");
        TextField tNom = new TextField("Entrez votre nom");
        HBname.getChildren().addAll(lNom,tNom);
        Label lcolor = new Label("Couleur:");

        Button b_suivant = new Button("Suivant");
        b_suivant.setOnAction(event -> {transmitInfoServeur();});
        generalBox.getChildren().addAll(HBname,lcolor,b_suivant);
        Scene scene = new Scene(generalBox, 350,300);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void transmitInfoServeur() {
        salonAttente();
    }

    private void salonAttente() {
        VBox generals = new VBox(10);
        generals.setAlignment(Pos.CENTER);
        HBox hBoxElements = new HBox(15);
        hBoxElements.setAlignment(Pos.CENTER);
        VBox vBoxNoms = new VBox(10);
        vBoxNoms.setAlignment(Pos.CENTER);
        Label noms = new Label("Noms: ");
        vBoxNoms.getChildren().add(noms);

        VBox vBoxCouleurs = new VBox(10);
        vBoxCouleurs.setAlignment(Pos.CENTER);
        Label couleurs = new Label("Couleurs: ");
        vBoxCouleurs.getChildren().add(couleurs);

        VBox vBoxPrets = new VBox(10);
        vBoxPrets.setAlignment(Pos.CENTER);
        Label prets = new Label("Prêt: ");
        vBoxPrets.getChildren().add(prets);

        HBox hBoxButtons = new HBox(10);
        hBoxButtons.setAlignment(Pos.CENTER);
        Button b_quitter = new Button("Quitter");
        Button b_pret = new Button("Prêt !");
        hBoxButtons.getChildren().addAll(b_quitter,b_pret);

        hBoxElements.getChildren().addAll(vBoxNoms,vBoxCouleurs,vBoxPrets,hBoxButtons);
        generals.getChildren().addAll(hBoxElements,hBoxButtons);
        Scene scene = new Scene(generals, 500,500);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void askNbJoueurs(){
        Label l_NombreJoueurs = new Label("Nombre de joueur : ");

        int[] options = new int[]{2,3,4,5};
        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setPromptText("Choississez le nombre de joueurs");
        for (int option : options) {
            comboBox.getItems().add(option);
        }
        Button b_suivant = new Button("Suivant");
        b_suivant.setOnAction(event -> {if (comboBox.getSelectionModel().getSelectedItem() != null)initialiseNbjoueurs(comboBox.getSelectionModel().getSelectedItem());});
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
        if (nomJoueur.length() > 16)return false;
        return true;
    }

    private void afficherFenetreInfosJoueurs() {
        Button b_suivant = new Button("suivant");
        Button b_precedent = new Button("precedent");
        Label erreurNom = new Label("Le nom doit être:\n- différent des noms déja données\n- non vide\n- faire 16 lettres maximum");
        erreurNom.setStyle("-fx-text-fill: RED");
        Label erreurCouleur = new Label("La couleur doit être:\n- différente des couleurs déja sélectionnée\n- non null");
        erreurCouleur.setStyle("-fx-text-fill: RED");
        Rectangle r_rouge = new Rectangle(30,30,Color.RED);
        Rectangle r_bleu = new Rectangle(30,30,Color.BLUE);
        Rectangle r_rose = new Rectangle(30,30,Color.HOTPINK);
        Rectangle r_jaune = new Rectangle(30,30,Color.GOLD);
        Rectangle r_bleuClaire = new Rectangle(30,30,Color.DEEPSKYBLUE);
        if (colorJoueursCorrect){
            t_rouge.setSelected(false);
            t_bleu.setSelected(false);
            t_rose.setSelected(false);
            t_jaune.setSelected(false);
            t_bleuClaire.setSelected(false);
        }
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
        v_rouge.getChildren().addAll(t_rouge,r_rouge);
        v_bleu.getChildren().addAll(t_bleu,r_bleu);
        v_rose.getChildren().addAll(t_rose,r_rose);
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
        t_bleu.setToggleGroup(toggleGroup);
        t_rose.setToggleGroup(toggleGroup);
        t_jaune.setToggleGroup(toggleGroup);
        t_bleuClaire.setToggleGroup(toggleGroup);
        bouttons.getChildren().addAll(v_rouge,v_bleu,v_rose,v_jaune,v_bleuClaire);
            if (nomJoueursCorrect) {
                for (Color tabColorJoueur : tabColorJoueurs) {
                    if (tabColorJoueur != null && tabColorJoueur == Color.RED)
                        bouttons.getChildren().remove(v_rouge);
                    if (tabColorJoueur != null && tabColorJoueur == Color.BLUE)
                        bouttons.getChildren().remove(v_bleu);
                    if (tabColorJoueur != null && tabColorJoueur == Color.HOTPINK)
                        bouttons.getChildren().remove(v_rose);
                    if (tabColorJoueur != null && tabColorJoueur == Color.GOLD)
                        bouttons.getChildren().remove(v_jaune);
                    if (tabColorJoueur != null && tabColorJoueur == Color.DEEPSKYBLUE)
                        bouttons.getChildren().remove(v_bleuClaire);
                }
            }else{
                for (int i = 0; i <= nombreJoueur2-1; i++) {
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.RED)
                        bouttons.getChildren().remove(v_rouge);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.BLUE)
                        bouttons.getChildren().remove(v_bleu);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.HOTPINK)
                        bouttons.getChildren().remove(v_rose);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.GOLD)
                        bouttons.getChildren().remove(v_jaune);
                    if (tabColorJoueurs[i] != null && tabColorJoueurs[i] == Color.DEEPSKYBLUE)
                        bouttons.getChildren().remove(v_bleuClaire);
                }
            }

        if (!nomJoueursCorrect)vBox.getChildren().add(erreurNom);
        vBox.getChildren().add(l_couleurJoueur);
        if (!colorJoueursCorrect)vBox.getChildren().add(erreurCouleur);
        if (nombreJoueur2 == 0){
            HBox boutons = new HBox(3);
            boutons.getChildren().addAll(b_precedent,b_suivant);
            boutons.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(bouttons,boutons);
            b_precedent.setOnAction(event -> askNbJoueurs());
        }else{
            vBox.getChildren().addAll(bouttons,b_suivant);
        }

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
            if (toggle.equals(t_bleu)) return Color.BLUE;
            if (toggle.equals(t_rose)) return Color.HOTPINK;
            if (toggle.equals(t_jaune)) return Color.GOLD;
            if (toggle.equals(t_bleuClaire)) return Color.DEEPSKYBLUE;
        }
        return null;
    }


    private void afficherFenetreJeu(){
        carcassonne.initialisationJoueurs(tabNomjoueurs,tabColorJoueurs);
        Group root = new Group();
        int WIDTH = 1000;
        int HEIGHT = 700;
        PopUpPartisan popUpPartisan = new PopUpPartisan(primaryStage);
        FenetreJeu fenetreJeu = new FenetreJeu(carcassonne, WIDTH, HEIGHT, popUpPartisan);
        root.getChildren().add(fenetreJeu);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT, Color.LIGHTGREY));
        primaryStage.show();
        carcassonne.jouer();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
