package Jeu;

public class Appli {
    public static void main(String[] args) {
        Carcassonne carcassonne = new Carcassonne();
        new ControlGroup(carcassonne);
    }
}
