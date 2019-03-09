package Jeu.MultiJoueur.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class SocketJoueur implements Serializable {
    private Socket socket;
    private ObjectInputStream oi;
    private ObjectOutputStream oo;

    public SocketJoueur(Socket socket, ObjectInputStream oi, ObjectOutputStream oo) {
        this.socket = socket;
        this.oi = oi;
        this.oo = oo;
    }

    public Socket getSocket() { return socket; }

    public void quitter(){
        try {
            oo.close();
            oi.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectInputStream getOi() { return oi; }

    public ObjectOutputStream getOo() { return oo; }
}
