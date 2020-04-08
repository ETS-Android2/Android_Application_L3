package fr.info.pl2020.service;

import androidx.annotation.Nullable;

import com.loopj.android.http.AsyncHttpResponseHandler;

import fr.info.pl2020.manager.HttpClientManager;
import fr.info.pl2020.util.FunctionsUtils;

public class TeachingUnitService {

    private final String urn = "/teachingUnit";

    /**
     * Récupère la liste de toutes les UE
     * @param semesterId filtre par le numéro du semestre si != 0
     * @param name filtre par le nom si != null
     */
    public void getAll(int semesterId, @Nullable String name, AsyncHttpResponseHandler responseHandler) {
        String currentUrn = this.urn + "?semester=" + semesterId;
        if (!FunctionsUtils.isNullOrBlank(name)) {
            currentUrn += "&name=" + name;
        }
        HttpClientManager.get(currentUrn, true, responseHandler);
    }

    /**
     * Récupère une UE précise à partir de son id
     * @param teachingUnitId id de l'UE à récuperer
     */
    public void getOne(int teachingUnitId, AsyncHttpResponseHandler responseHandler) {
        HttpClientManager.get(this.urn + "/" + teachingUnitId, true, responseHandler);
    }
}
