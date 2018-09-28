package Jeu;

public class ControlGroup {
    private Carcassonne carcassonne;
    private Fenetre fenetre;
    private ControlButton controlButton;

    public ControlGroup(Carcassonne carcassonne) {
        this.carcassonne = carcassonne;
        this.fenetre = new Fenetre(carcassonne);
        this.controlButton = new ControlButton(carcassonne, fenetre);
    }
}
