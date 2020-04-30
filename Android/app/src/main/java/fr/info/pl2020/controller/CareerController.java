package fr.info.pl2020.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import fr.info.pl2020.R;
import fr.info.pl2020.activity.SemestersListActivity;
import fr.info.pl2020.component.MessagePopup;
import fr.info.pl2020.manager.AuthenticationManager;
import fr.info.pl2020.model.Career;
import fr.info.pl2020.model.TeachingUnit;
import fr.info.pl2020.service.CareerService;
import fr.info.pl2020.store.CareerListStore;
import fr.info.pl2020.store.CareerStore;
import fr.info.pl2020.store.TeachingUnitListStore;

import static fr.info.pl2020.util.JsonModelConvert.jsonArrayToCareers;
import static fr.info.pl2020.util.JsonModelConvert.jsonObjectToCareer;

public class CareerController {

    private CareerService careerService = new CareerService();

    /**
     * Récupère le parcours correspondant à l'id envoyé en paramètre OU le parccours principal si id == 0
     */
    public void getCareer(Context context, int careerId, Runnable callback) {
        this.careerService.getCareerById(careerId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                CareerStore.setCurrentCareer(jsonObjectToCareer(response));

                if (callback != null) {
                    callback.run();
                }
            }

            /*
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            CareerListStore.clear();
                            List<Career> careers = jsonArrayToCareers(response);
                            for (Career career : careers) {
                                CareerListStore.addItem(career);
                            }

                            if (callback != null) {
                                callback.run();
                            }

                            /*
                            Map<Integer, List<TeachingUnit>> teachingUnitBySemester = TeachingUnitListStore.getTeachingUnitBySemester();

                            if (teachingUnitBySemester.size() != 0) {
                                List<Object> listItem = new ArrayList<>();
                                teachingUnitBySemester.forEach((semesterId, teachingUnits) -> {
                                    listItem.add("Semestre " + semesterId);
                                    listItem.addAll(teachingUnits);
                                });
                                ListView summaryCareerList = ((Activity) context).findViewById(R.id.summaryCareerList);
                                CareerSummaryAdapter adapter = new CareerSummaryAdapter(context, listItem);
                                summaryCareerList.setAdapter(adapter);
                            } else {
                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View v = inflater.inflate(R.layout.empty_career_summary_list, null);

                                LinearLayout linearLayout = ((Activity) context).findViewById(R.id.summaryCareer);
                                linearLayout.addView(v);
                            }*//*
            }
*/
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

    /**
     * Récupère l'ensemble des parcours de l'étudiant
     */
    public void getAllCareers(Context context, Runnable callback) {
        new CareerService().getAllCareers(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (statusCode == HttpStatus.SC_OK) {
                    CareerListStore.clear();
                    List<Career> careers = jsonArrayToCareers(response);
                    for (Career career : careers) {
                        CareerListStore.addItem(career);
                    }

                    if (callback != null) {
                        callback.run();
                    }
                } else {
                    Log.e("CAREER_SERVICE", "Erreur lors de la récupération des informations de la liste des parcours");
                    Toast.makeText(context, R.string.standard_exception, Toast.LENGTH_SHORT).show();
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

    public void createCareer(Context context, Career career, boolean redirectAfterEdit) {
        this.careerService.createCareer(career, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response.has("error")) {
                    new MessagePopup(context, "Erreur", response.optString("error"));
                } else {
                    new MessagePopup(context, "Le parcours a bien été créé.", null, (dialog, which) -> {
                        if (redirectAfterEdit) {
                            CareerStore.setCurrentCareer(jsonObjectToCareer(response));
                            Intent intent = new Intent(context, SemestersListActivity.class);
                            intent.putExtra(SemestersListActivity.ARG_CAREER_ID, CareerStore.getCurrentCareer().getId());
                            context.startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    new AuthenticationManager().callLoginActivity(context);
                } else if (statusCode == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
                    new MessagePopup(context, "Erreur", errorResponse.optString("error"));
                } else {
                    Log.e("CAREER", "Echec de la creation  d'un parcours", throwable);
                    Toast.makeText(context, R.string.server_connection_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteCareer(Context context, Career career){
        this.careerService.deleteCareer(career, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response.has("error")) {
                    new MessagePopup(context, "Erreur", response.optString("error"));
                } else {
                    new MessagePopup(context, "Le parcours a bien été Supprimé.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    new AuthenticationManager().callLoginActivity(context);
                } else if (statusCode == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
                    new MessagePopup(context, "Erreur", errorResponse.optString("error"));
                } else {
                    Log.e("CAREER", "Echec de la Suppression d'un parcours", throwable);
                    Toast.makeText(context, R.string.server_connection_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveCareer(Context context, int semesterId) {
        List<Integer> teachingUnitIdList = TeachingUnitListStore.TEACHING_UNITS.values().stream().filter(TeachingUnit::isSelected).map(TeachingUnit::getId).collect(Collectors.toList());
        this.careerService.saveCareer(teachingUnitIdList, semesterId, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response.has("error")) {
                    new MessagePopup(context, "Erreur", response.optString("error"));
                } else {
                    new MessagePopup(context, "Le parcours a bien été enregistré.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    new AuthenticationManager().callLoginActivity(context);
                } else if (statusCode == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
                    new MessagePopup(context, "Erreur", errorResponse.optString("error"));
                } else {
                    Log.e("CAREER", "Echec de l'enregistrement d'un parcours", throwable);
                    Toast.makeText(context, R.string.server_connection_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void downloadCareer(Context context, int careerId, Career.ExportFormat format) {
        this.careerService.exportCareer(context, careerId, format);
    }
}
