package fr.info.pl2020.util;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.info.pl2020.model.TeachingUnitListContent.TeachingUnit;

public class JsonModelConvert {

    private JsonModelConvert() {}

    public static List<TeachingUnit> jsonArrayToTeachingUnits(@Nullable JSONArray jsonArray) {
        if (jsonArray == null) {
            return new ArrayList<>();
        }
        List<TeachingUnit> teachingUnits = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            teachingUnits.add(jsonObjectToTeachingUnit(jsonArray.optJSONObject(i)));
        }

        return teachingUnits;
    }

    public static TeachingUnit jsonObjectToTeachingUnit(@Nullable JSONObject jsonObject) {
        if (jsonObject == null)
            return null;

        return new TeachingUnit(
                jsonObject.optInt("id"),
                jsonObject.optString("name"),
                jsonObject.optString("code"),
                jsonObject.optString("description"),
                jsonObject.optInt("semester"),
                jsonObject.optString("category"));
    }
}
