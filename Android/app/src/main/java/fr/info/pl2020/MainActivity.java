package fr.info.pl2020;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import constantes.EventEnum;
import fr.info.pl2020.Adapters.MessageAdapter;
import fr.info.pl2020.controllers.MessageSender;
import fr.info.pl2020.services.Connexion;
import metier.Identity;

public class MainActivity extends Activity {

    public static final String AUTOCONNECT = "AUTOCONNECT";

    private Identity identity;
    private boolean autoconnect = true;
    private MessageSender messageSender;
    private MessageAdapter messageAdapter;
    private List<String> listMessages;
    private Connexion connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listMessages = new ArrayList<>();
        this.messageAdapter = new MessageAdapter(this, listMessages);
        ((ListView) findViewById(R.id.list_messages)).setAdapter(messageAdapter);

        identity = new Identity("toto"); //TODO
        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (autoconnect) {
            this.connexion = new Connexion();
            this.connexion.addListener(EventEnum.RECEIVE, args -> {
                updateList();
                Log.d("Message reçu", args[0].toString());
                listMessages.add(args[0].toString());
            });
            messageAdapter.notifyDataSetChanged();
            this.connexion.connect();
            this.messageSender = new MessageSender(this.connexion);
            messageSender.sendIdentity(identity);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.connexion != null) {
            this.connexion.disconnect();
        }
        this.listMessages.clear();
        messageAdapter.notifyDataSetChanged();
    }

    public void sendMessage(View view) {
        if (this.connexion != null && this.connexion.isConnected()) {
            EditText input = findViewById(R.id.input_text);
            String msg = input.getText().toString();
            this.messageSender.sendMessage(msg);
            input.setText("");
            input.clearFocus();
        } else {
            Log.e("Main", "Impossible d'envoyer le message : non connecté au serveur");
        }
    }

    public void updateList() {
        runOnUiThread(() -> messageAdapter.notifyDataSetChanged());
    }
}
