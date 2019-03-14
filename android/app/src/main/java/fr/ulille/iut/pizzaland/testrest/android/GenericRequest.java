package fr.ulille.iut.pizzaland.testrest.android;

import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static fr.ulille.iut.pizzaland.testrest.android.MainActivity.LOG_TAG;

public class GenericRequest extends Request<PizzalandResponse> {
    private Response.Listener<PizzalandResponse> listener = null;
    private JSONObject content = null;

    public GenericRequest(int method, String url, @Nullable JSONObject content, Response.Listener<PizzalandResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, (Response.ErrorListener) errorListener);
        this.listener = listener;
        this.content = content;
    }

    @Override
    public String getBodyContentType() {
        if (content != null) {
            return "application/json; charset=utf-8";
        }
        return "";
    }

    @Override
    public byte[] getBody() {
        try {
            return content.toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "JSONObject encoding found to be incorrect ?? : " + content);
            return new byte[0];
        }
    }

    @Override
    protected Response<PizzalandResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            String location = response.headers.get("Location");
            return Response.success(new PizzalandResponse(json, response.statusCode, location), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Character encoding not supported: " + HttpHeaderParser.parseCharset(response.headers));
        }
        return Response.error(new VolleyError(response));
    }

    @Override
    protected void deliverResponse(PizzalandResponse response) {
        listener.onResponse(response);
    }
}
