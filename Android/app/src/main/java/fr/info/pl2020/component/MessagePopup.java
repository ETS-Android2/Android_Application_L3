package fr.info.pl2020.component;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.Nullable;

import fr.info.pl2020.util.FunctionsUtils;

public class MessagePopup {

    private AlertDialog.Builder alertDialog;

    public MessagePopup(Context context) {
        new MessagePopup(context, null);
    }

    public MessagePopup(Context context, @Nullable String message) {
        new MessagePopup(context, null, message);
    }

    public MessagePopup(Context context, @Nullable String title, @Nullable String message) {
        new MessagePopup(context, title, message, null);
    }

    public MessagePopup(Context context, @Nullable String title, @Nullable String message, @Nullable DialogInterface.OnClickListener onClickListener) {
        this.alertDialog = new AlertDialog.Builder(context);

        if (!FunctionsUtils.isNullOrBlank(title)) {
            alertDialog.setTitle(title);
        }

        if (!FunctionsUtils.isNullOrBlank(message)) {
            alertDialog.setMessage(message);
        }

        alertDialog.setNeutralButton("OK", onClickListener == null ? (dialog, which) -> {
        } : onClickListener);
        alertDialog.show();
    }
}
