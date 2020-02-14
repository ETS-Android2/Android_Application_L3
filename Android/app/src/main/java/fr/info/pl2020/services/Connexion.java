package fr.info.pl2020.services;

import android.util.Log;

import java.net.URISyntaxException;

import constantes.EventEnum;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import metier.ToJSON;

public class Connexion {

    private Socket socket;

    public Connexion() {
        try {
            this.socket = IO.socket("http://10.0.2.2:10101");
        } catch (URISyntaxException e) {
            Log.e("Socket", "Wrong URI", e);
        }
    }

    public void connect() {
        this.socket.connect();
    }

    public void disconnect() {
        if (this.socket != null) this.socket.disconnect();
    }

    public boolean isConnected() {
        return this.socket.connected();
    }

    public void sendEvent(EventEnum event, ToJSON msg) {
        this.socket.emit(event.toString(), msg);
    }

    public void send(EventEnum event) {
        this.socket.emit(event.toString());
    }

    public void addListener(EventEnum event, Emitter.Listener listener) {
        this.socket.on(event.toString(), listener);
    }
}
