package fr.ulille.iut.pizzaland;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PizzalandResponse {
    private JSONObject jsonObject = null;
    private JSONArray jsonArray = null;
    private String string = null;
    private int statusCode;
    private String location;

    public PizzalandResponse(String string, int statusCode, String location) {
        this.statusCode = statusCode;
        this.location = location;
        try {
            jsonObject = new JSONObject(string);
        } catch (JSONException e) {
            try {
                jsonArray = new JSONArray(string);
            } catch (JSONException e1) {
                this.string = string;
            }
        }
    }

    public String toString() {
        try {
            if (jsonObject != null) {
                return jsonObject.toString(4);
            } else if (jsonArray != null) {
                return jsonArray.toString(4);
            } else {
                return string;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "** ERROR **";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getLocation() {
        return location;
    }
}
