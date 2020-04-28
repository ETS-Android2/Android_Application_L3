package fr.info.pl2020.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import fr.info.pl2020.R;
import fr.info.pl2020.adapter.CareerListAdapter;
import fr.info.pl2020.manager.AuthenticationManager;
import fr.info.pl2020.model.Career;
import fr.info.pl2020.model.TeachingUnitListContent;
import fr.info.pl2020.service.CareerService;
import fr.info.pl2020.util.JsonModelConvert;

import static fr.info.pl2020.util.JsonModelConvert.jsonObjectToTeachingUnit;

public class CareerController {

    private CareerService careerService = new CareerService();

    public void getAllCareers(Context context, ListView listView){
        new CareerService().getAllCareer(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == HttpStatus.SC_OK) {
                    List<Career> careersList = JsonModelConvert.jsonArrayToCareer(response);
                    CareerListAdapter careerAdapter = new CareerListAdapter(context, careersList);
                    listView.setAdapter(careerAdapter);
                }
                else {
                    Log.e("CAREER_SERVICE", "Erreur lors de la récupération des informations de la liste des cariéres");
                    Toast.makeText(context, R.string.standard_exception, Toast.LENGTH_SHORT).show();
                    return;

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    new AuthenticationManager().callLoginActivity(context);
                } else {
                    Log.e("CAREER_SERVICE", "Echec de la récupération de la liste des parcours (Code: " + statusCode + ")", throwable);
                    Toast.makeText(context, R.string.server_connection_error, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    public void getCareer(Context context, Runnable callback) {
        getCareer(context, 0, callback);
    }

    public void getCareer(Context context, int semesterId, Runnable callback) {
        this.careerService.getCareer(semesterId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                callback.run();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject teachingUnit = response.getJSONObject(i);
                        int id = teachingUnit.getInt("id");
                        
                        if (!TeachingUnitListContent.TEACHING_UNITS.containsKey(id)) {
                            TeachingUnitListContent.addItem(jsonObjectToTeachingUnit(teachingUnit));
                        }
                        TeachingUnitListContent.TEACHING_UNITS.get(id).setSelected(true);
                    }

                    callback.run();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    new AuthenticationManager().callLoginActivity(context);
                } else {
                    Log.e("CAREER", "Echec de la récupération du parcours de l'étudiant (Code: " + statusCode + ")", throwable);
                    Toast.makeText(context, R.string.unexpected_http_status, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveCareer(Context context, int semesterId) {
        List<Integer> teachingUnitIdList = TeachingUnitListContent.TEACHING_UNITS.values().stream().filter(TeachingUnitListContent.TeachingUnit::isSelected).map(TeachingUnitListContent.TeachingUnit::getId).collect(Collectors.toList());
        this.careerService.saveCareer(teachingUnitIdList, semesterId, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                if (response.has("error")) {
                    alertDialog.setTitle("Erreur");
                    alertDialog.setMessage(response.optString("error"));
                } else {
                    alertDialog.setMessage("Le parcours a bien été enregistré.");
                }

                alertDialog.setNeutralButton("OK", (dialog, which) -> {
                });
                alertDialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    new AuthenticationManager().callLoginActivity(context);
                } else if (statusCode == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Erreur");
                    alertDialog.setMessage(errorResponse.optString("error"));
                    alertDialog.setNeutralButton("OK", (dialog, which) -> {
                    });
                    alertDialog.show();
                } else {
                    Log.e("CAREER", "Echec de l'enregistrement d'un parcours", throwable);
                    Toast.makeText(context, R.string.server_connection_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
