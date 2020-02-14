package metier;

import org.json.JSONException;
import org.json.JSONObject;

public class Identity implements ToJSON {

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    private  String nom;

    public Identity() {
        this("nom par défaut");
    }
    public Identity(String nom) {
        this.nom = nom;
    }

    public String toString() {
        return this.getNom();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject identité = new JSONObject();
        try {
            identité.put("nom", getNom());
        } catch (JSONException e) {
            e.printStackTrace();}
        return identité;
    }
}
