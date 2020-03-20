package fr.info.pl2020.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import fr.info.pl2020.R;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Button button = (Button) findViewById(R.id.button4);
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Appeler une URL web
                String url = "http://univ-cotedazur.fr/";
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(url) );
                startActivity(intent);
            }
        });
    }

}
