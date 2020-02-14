package metier;

import org.json.JSONException;
import org.json.JSONObject;

public class Message implements ToJSON {

    private String body;

    public Message(String body) {
        this.body = body;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject msg = new JSONObject();
        try {
            msg.put("body", this.body);
        } catch (JSONException e) {
            e.printStackTrace();}
        return msg;
    }

    @Override
    public String toString() {
        return this.body;
    }
}
