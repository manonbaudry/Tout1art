package fr.ulille.iut.pizzaland;

import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import fr.ulille.iut.pizzaland.dto.PizzaShortDto;

import static fr.ulille.iut.pizzaland.MainActivity.LOG_TAG;

public class PizzasRequest extends Request<PizzaShortDto[]> {
    private Response.Listener<PizzaShortDto[]> listener = null;
    private JSONObject content = null;

    public PizzasRequest(int method, String url, Response.Listener<PizzaShortDto[]> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<PizzaShortDto[]> parseNetworkResponse(NetworkResponse response) {
        JSONArray json;
        PizzaShortDto[] returned;
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            json = new JSONArray(jsonString);
            returned = new PizzaShortDto[json.length()];
            for (int i = 0; i < json.length() ; i++) {
                JSONObject pizzaJson = json.getJSONObject(i);
                PizzaShortDto pizza = new PizzaShortDto();
                pizza.setId(pizzaJson.getInt("id"));
                pizza.setNom(pizzaJson.getString("nom"));
                pizza.setBase(pizzaJson.getString("base"));
                pizza.setPrix_grande(Float.valueOf(pizzaJson.getString("prix_grande")));
                pizza.setPrix_petite(Float.valueOf(pizzaJson.getString("prix_petite")));
                returned[i] = pizza;
            }
            return Response.success(returned, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Character encoding not supported: " + HttpHeaderParser.parseCharset(response.headers));
            return Response.error(new VolleyError(response));
        } catch (JSONException e1) {
            Log.e(LOG_TAG, "Error in parsing Json");
            return Response.error(new VolleyError(response));
        }
    }

    @Override
    protected void deliverResponse(PizzaShortDto[] response) {
        listener.onResponse(response);
    }
}
