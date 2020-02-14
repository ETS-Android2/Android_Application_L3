package fr.info.pl2020.controllers;

import android.util.Log;

import fr.info.pl2020.services.Connexion;

import constantes.EventEnum;
import metier.Message;
import metier.ToJSON;

public class MessageSender {

    private Connexion connexion;

    public MessageSender(Connexion connexion) {
        this.connexion = connexion;
    }

    public void sendIdentity(ToJSON identity) {
        this.connexion.sendEvent(EventEnum.CONNEXION, identity);
    }

    public void sendMessage(String msg) {
        Log.e("Main", "Demande d'envoi");
        this.connexion.sendEvent(EventEnum.SEND, new Message(msg));
    }
}
