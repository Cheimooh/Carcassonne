package Jeu.Exception;

public class PiocheVideException extends Exception {
    public String getMessage() {
        return "La pioche ne contient plus de cartes !";
    }
}
