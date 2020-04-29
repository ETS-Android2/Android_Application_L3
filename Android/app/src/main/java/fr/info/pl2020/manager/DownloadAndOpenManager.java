package fr.info.pl2020.manager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.commonsware.cwac.provider.StreamProvider;

import fr.info.pl2020.model.Career;

public class DownloadAndOpenManager {

    private static final String AUTHORITY = "fr.info.pl2020.fileprovider";
    private static final Uri PROVIDER = Uri.parse("content://" + AUTHORITY);

    public void downloadFile(Context context, String urn, String fileName, Career.ExportFormat format) {
        String url = HttpClientManager.BASE_URL + urn;
        Uri uri = Uri.parse(url);
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    Toast.makeText(context, "Parcours téléchargé", Toast.LENGTH_SHORT).show();
                    //openFile(context, downloadId); //TODO MARCHE PAS !!!
                }
            }
        }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.addRequestHeader("Authorization", new AuthenticationManager().getToken());
        request.setMimeType(format == Career.ExportFormat.PDF ? "application/pdf" : "text/plain")
                .setTitle(fileName)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    private void openFile(Context context, long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return;
        }

        int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
        String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
        String downloadMimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
        if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = PROVIDER.buildUpon().appendPath(StreamProvider.getUriPrefix(AUTHORITY)).appendPath(Uri.parse(downloadLocalUri).getPath()).build();
            intent.setDataAndType(uri, downloadMimeType);
            context.startActivity(intent);
        }
    }
}
