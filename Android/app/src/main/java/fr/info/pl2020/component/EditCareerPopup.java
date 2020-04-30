package fr.info.pl2020.component;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.widget.EditText;

import fr.info.pl2020.R;
import fr.info.pl2020.activity.SemestersListActivity;
import fr.info.pl2020.controller.CareerController;
import fr.info.pl2020.model.Career;

public class EditCareerPopup {

    public EditCareerPopup(Context context, Career currentCareer, boolean create) {
        AlertDialog.Builder popUpBuilder = new AlertDialog.Builder(context);

        final Career career = currentCareer == null ? new Career() : currentCareer;
        final EditText namePopUp = new EditText(context);
        namePopUp.setHint(R.string.default_career_name);
        namePopUp.setInputType(InputType.TYPE_CLASS_TEXT);
        namePopUp.setText(career.getName());

        final String[] items = {"Parcours Principal", "Rendre ce parcours public"};
        final boolean[] checkedItems = {career.isMainCareer(), career.isPublicCareer()};

        popUpBuilder.setTitle("Editer un Parcours");
        popUpBuilder.setView(namePopUp);

        popUpBuilder.setMultiChoiceItems(items, checkedItems,
                (dialog, which, isChecked) -> {
                    if (which == 0) {
                        career.setMainCareer(isChecked);
                    } else if (which == 1) {
                        career.setPublicCareer(isChecked);
                    }
                })
                .setPositiveButton(R.string.validate, (dialog, which) -> {
                    career.setName(namePopUp.getText().toString());
                    if (create) {
                        new CareerController().createCareer(context, career);
                    } else {
                        new CareerController().editCareer(context, career);
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {

                });

        if (!create) {
            popUpBuilder.setNeutralButton(R.string.edit_career, (dialog, which) -> {
                Intent intent = new Intent(context, SemestersListActivity.class);
                intent.putExtra(SemestersListActivity.ARG_CAREER_ID, career.getId());
                context.startActivity(intent);
            });
        }

        Dialog popUp = popUpBuilder.create();
        popUp.show();
    }
}
