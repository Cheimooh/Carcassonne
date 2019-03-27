package Jeu;

import Jeu.MultiJoueur.Controller.ControlMouseInfos;
import Jeu.MultiJoueur.Controller.ControlMouse;
import Jeu.MultiJoueur.Model.*;
import Jeu.MultiJoueur.View.PlaceDispo;
import Jeu.MultiJoueur.View.PopUpPartisan;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class MenuReseau extends Parent {
    private Stage primaryStage;
    private int nombreJoueur;
    private SocketJoueur socketJoueur;
    private List<Joueur> listJoueurs;
    private List<Color> listColorJoueursReseau;
    private String nomJoueur;

    /*
    * Elements affichage fenetre instanciation du jeu (connexion au serveur + choix couleur+nom + salonAttente
    * */

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
    final Image IMAGE_VALIDER = new Image("Jeu/valider.png", 50,50,true,true);

    private Color couleurJoueurTmp;
    private List<HBox> listHBoxElement;
    private VBox generals;
    private HBox hBoxTitres;
    private HBox hBoxButtons;
    private boolean nomJoueursCorrect;

    // variable pour le jeu

    private ThreadSalonAttente salonAttente;
    private Map<Point, CartePosee> pointCarteMap;
    private List<Point> listPointDispo;
    private List<Point> listPointOccupe;
    private List<Carte> defausse;

    private Carte carteCourante;
    private String nomJoueurCourant;

    /*
    * Affichage fenetre Jeu après startPartie
    * */

    private GraphicsContext graphicsContext;
    private int[] tabDefausseCarte;
    private GraphicsContext graphicsContextInfos;
    private Canvas canvasInfos;
    private PlaceDispo placeDispo;
    int width = 1000;
    int height = 700;
    private ControlMouse controlMouse;
    private ControlMouseInfos controlMouseInfos;

    public MenuReseau(Stage primaryStage) {
        placeDispo = new PlaceDispo();
        nombreJoueur = 0;
        listJoueurs = new ArrayList<>();
        listColorJoueursReseau = new ArrayList<>();
        listHBoxElement = new ArrayList<>();
        nomJoueursCorrect = true;
        this.primaryStage = primaryStage;
        jeuInternet();
    }

    public void jeuInternet() {
        try {
            System.out.println("test");
            //Socket sock = new Socket("86.77.97.239", 3333);
            Socket sock = new Socket("localhost", 3333);
            ObjectOutputStream oo = new ObjectOutputStream(sock.getOutputStream());
            ObjectInputStream oi = new ObjectInputStream(sock.getInputStream());

            socketJoueur = new SocketJoueur(sock, oi, oo);

            nombreJoueur = (Integer) oi.readObject();
            for (int i = 0; i < nombreJoueur; i++) {
                Joueur joueur = (Joueur) oi.readObject();
                listJoueurs.add(joueur);
                listColorJoueursReseau.add(Menu.tradStringToColors(joueur.getCouleur()));
            }
            inscriptionJoueurReseau();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connexion Serveur");

            alert.setContentText("Connexion au serveur échoué");
            alert.showAndWait();
            System.exit(0);
        } catch (ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Version Jeu");

            alert.setContentText("Vous n'avez pas la bonne version du jeu");
            alert.showAndWait();
            System.exit(0);
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
        TextField tNom = new TextField();
        tNom.setPromptText("Entrez un nom");

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
            nomJoueur = tNom.getText();
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
        if(!(nomJoueur.length() > 0 && nomJoueur.length() <= 16)){
            nomJoueursCorrect = false;
        }
        if(couleurJoueurTmp == null){
            nomJoueursCorrect = false;
        }
        if(!nomJoueursCorrect){
            inscriptionJoueurReseau();
        }
        else{
            listJoueurs.add(new Joueur(nomJoueur, tradColorsToString(couleurJoueurTmp)));
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

        b_quitter.setOnAction(event -> {
            try {
                socketJoueur.getOo().writeObject("j'envoie");
                socketJoueur.getOo().writeObject("quitter");
            } catch (IOException e) {
                e.printStackTrace();
            }
            salonAttente.arreter();
            System.exit(0);
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
                hBox.getChildren().add(new ImageView(IMAGE_VALIDER));
            }
            listHBoxElement.add(hBox);
        }
        generals.getChildren().add(hBoxTitres);

        for (HBox aListHBoxElement : listHBoxElement) {
            generals.getChildren().add(aListHBoxElement);
        }
       generals.getChildren().add(hBoxButtons);

        salonAttente = new ThreadSalonAttente(this);

        Scene scene = new Scene(generals, 500, 500);
        primaryStage.setScene(scene);
    }

    public void actualiser() {
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
                hBox.getChildren().add(new ImageView(IMAGE_VALIDER));
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

    public void startPartie(){
        pointCarteMap = new HashMap<>();
        listPointDispo = new ArrayList<>();
        listPointOccupe = new ArrayList<>();
        defausse = new ArrayList<>();
        initialiser();
    }

    private void initialiser() {
        actualiserPoserCarte();
        ObjectInputStream oi = socketJoueur.getOi();
        try {
            /*On récupère la defausse*/
            int tailleDefausse = oi.readInt();
            for (int i = 0; i < tailleDefausse; i++) {
                defausse.add((Carte)oi.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        afficherFenetreJeu();
    }

    private void afficherFenetreJeu() {
        Group root = new Group();
        PopUpPartisan popUpPartisan = new PopUpPartisan(primaryStage);
        //voir barre infos pour affichage

        Canvas canvas = new Canvas(143*50, 143*50);
        controlMouse = new ControlMouse(this);
        canvas.setOnMouseClicked(controlMouse);
        graphicsContext = canvas.getGraphicsContext2D();

        //Le fond
        Image image = new Image("Jeu/fond2.jpg");
        graphicsContext.drawImage(image,0,100,width,height);
        image = new Image("Jeu/fond.jpg");
        graphicsContext.drawImage(image,0,0,width,100);

        //barreInfos
        barreInfos();

        actualiserPlateau();

        root.getChildren().addAll(canvas, canvasInfos);
        Platform.runLater(() -> primaryStage.setScene(new Scene(root, width, height, Color.LIGHTGREY) ));
    }

    private void barreInfos() {
        tabDefausseCarte = new int[]{750, 35, 180, 30};
        canvasInfos = new Canvas(1000, 100);
        controlMouseInfos = new ControlMouseInfos(controlMouse, tabDefausseCarte);
        canvasInfos.setOnMouseClicked(controlMouseInfos);
        graphicsContextInfos = canvasInfos.getGraphicsContext2D();
        graphicsContextInfos.setStroke(Color.color(0.2,0.2,0.2));
    }

    /*
     * Permet de générer une fenêtre pop-up d'erreur
     */
    public void afficheErreur(String erreur, String title){
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);

            alert.setContentText(erreur);
            alert.showAndWait();
        });
    }

    private void drawBouton(String texte, double x, int y, int largeur, int hauteur) {
        graphicsContextInfos.fillRoundRect(x,y, largeur,hauteur,20,20);
        graphicsContextInfos.strokeText(texte, x+20,y+17);
    }

    /*
     * Dessine la ligne séparatrice entre la fenêtre de jeu et la barre d'canvasInfos
     */
    private void drawLigneSeparatrice() {
        graphicsContextInfos.moveTo(0,height);
        graphicsContextInfos.lineTo(width,height);
        graphicsContextInfos.stroke();
    }

    public void actualiserPoserCarte(){
        ObjectInputStream oi = socketJoueur.getOi();
        try {
            /*On récupère la map point carte*/
            int tailleMap = oi.readInt();
            for (int i = 0; i < tailleMap; i++) {
                Point point = (Point) oi.readObject();
                CartePosee cartePosee = (CartePosee) oi.readObject();
                pointCarteMap.put(point,cartePosee);
            }

            /*On récupère la liste des points disponible*/
            int tailleListePointDispo = oi.readInt();
            System.out.println("taille: " + tailleListePointDispo);
            for (int i = 0; i < tailleListePointDispo; i++) {
                Point point = (Point) oi.readObject();
                listPointDispo.add(point);
            }

            /*On récupère la liste des points occuper*/
            int tailleListePointOccupe = oi.readInt();
            for (int i = 0; i < tailleListePointOccupe; i++) {
                Point point = (Point) oi.readObject();
                listPointDispo.add(point);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     * Permet de placer une carte sur la fenêtre de jeu
     */
    public void actualiserPlateau(){
        for (HashMap.Entry<Point, CartePosee> entry : pointCarteMap.entrySet())
        {
            Point point = entry.getKey();
            CartePosee carteAPosee = entry.getValue();
            Image image = new Image(carteAPosee.getImageCarte());
            graphicsContext.drawImage(image, point.getX()*50,point.getY()*50, 50, 50);
        }

        for (int i = 0; i < listPointDispo.size(); i++) {
            graphicsContext.drawImage(placeDispo.getImagePlus(),listPointDispo.get(i).getX()*50, listPointDispo.get(i).getY()*50, 50, 50);
        }
    }

    public void actualiserDefausse() {
        ObjectInputStream oi = socketJoueur.getOi();
        try {
            /*On récupère la defausse*/
            int tailleDefausse = oi.readInt();
            for (int i = 0; i < tailleDefausse; i++) {
                defausse.add((Carte)oi.readObject());
            }
            carteCourante = (Carte) oi.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     * Dessine la barre d'canvasInfos lorsque le joueur doit poser une carte
     */
    public void actualiserTourSuivant(){
        controlMouseInfos.setMode(0);
        graphicsContextInfos.clearRect(0,0,width,100);
        graphicsContextInfos.setFill(Color.BLACK);
        drawLigneSeparatrice();

        String s;

        graphicsContextInfos.drawImage(new Image(carteCourante.getPath()), (width/2.), 30, 50, 50);

        s = "Joueur: " + nomJoueurCourant;

        String defausse = "Defausser ma carte";
        String voirDefausse = "Défausse";

        graphicsContextInfos.setFill(Color.color(0.98,0.694, 0.627));

        //Affichage du "bouton" pour voir la défausse
        drawBouton(voirDefausse, width/7., 35, 100, 30);
        //Affichage du "bouton" pour défausser une carte
        drawBouton(defausse, tabDefausseCarte[0], tabDefausseCarte[1], tabDefausseCarte[2], tabDefausseCarte[3]);

        if(nomJoueur.equals(nomJoueurCourant)) graphicsContextInfos.setStroke(Color.RED);

        graphicsContextInfos.strokeText(s, (width/2.), 15);

        graphicsContextInfos.setStroke(Color.BLACK);
    }

    public SocketJoueur getSocketJoueur() { return socketJoueur; }

    public List<Joueur> getListJoueurs() { return listJoueurs; }

    public void setNombreJoueur(int nombreJoueur) { this.nombreJoueur = nombreJoueur; }

    public ControlMouse getControlMouse() { return controlMouse; }

    public Carte getCarteCourante() { return carteCourante; }

    public void setCarteCourante(Carte carteCourante) { this.carteCourante = carteCourante; }

    public String getNomJoueurCourant() { return nomJoueurCourant; }

    public void setNomJoueurCourant(String nomJoueurCourant) { this.nomJoueurCourant = nomJoueurCourant; }

    public String getNomJoueur() { return nomJoueur; }
}
