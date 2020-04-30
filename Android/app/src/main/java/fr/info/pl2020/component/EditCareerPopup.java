package fr.info.pl2020.component;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.widget.EditText;

import fr.info.pl2020.R;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.model.Career;

public class EditCareerPopup {

    public EditCareerPopup(Context context, Career currentCareer, boolean redirectAfterEdit) {
        AlertDialog.Builder popUpBuilder = new AlertDialog.Builder(context);

        final Career career = currentCareer == null ? new Career() : currentCareer;
        final EditText namePopUp = new EditText(context);
        namePopUp.setText(R.string.default_career_name);
        namePopUp.setInputType(InputType.TYPE_CLASS_TEXT);

        final String[] items = {"Parcours Principal", "Rendre ce parcours public"};

        popUpBuilder.setTitle("Editer un Parcours");
        popUpBuilder.setView(namePopUp);

        popUpBuilder.setMultiChoiceItems(items, null,
                (dialog, which, isChecked) -> {
                    if (which == 0) {
                        career.setMainCareer(isChecked);
                    } else if (which == 1) {
                        career.setPublicCareer(isChecked);
                    }
                })
                .setPositiveButton(R.string.validate, (dialog, which) -> {
                    career.setName(namePopUp.getText().toString());
                    new CareerController().createCareer(context, career, redirectAfterEdit);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                });

        Dialog popUp = popUpBuilder.create();
        popUp.show();
    }
}
