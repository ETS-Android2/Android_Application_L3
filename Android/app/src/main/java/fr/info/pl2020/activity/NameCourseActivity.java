package fr.info.pl2020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.info.pl2020.R;

public class NameCourseActivity extends AppCompatActivity {

    private EditText nameInput;
    private TextView errorTextView;
    //private NameCourseController nameCoursController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namecourse);

        this.nameInput = findViewById(R.id.name);
        this.errorTextView = findViewById(R.id.errorName);
        hideErrorMessage();
    }

    public void validate(View view) {
        String name = this.nameInput.getText().toString();

        if (name.trim().isEmpty()) {
            this.displayErrorMessage(R.string.newcourse_missing_name);
            this.nameInput.requestFocus();
            return;
        }

        startActivity(new Intent(this, SemestersListActivity.class));
        //this.nameCoursController.authenticate();

    }

    private void hideErrorMessage() {
        TextView errorTextView = this.errorTextView;
        errorTextView.setText("");
        errorTextView.setVisibility(View.GONE);
    }

    private void displayErrorMessage(int resId) {
        this.errorTextView.setText(resId);
        this.errorTextView.setVisibility(View.VISIBLE);
    }

}
