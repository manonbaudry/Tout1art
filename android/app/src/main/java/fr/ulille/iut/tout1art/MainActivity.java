package fr.ulille.iut.pizzaland;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import fr.ulille.iut.pizzaland.dto.PizzaShortDto;

public class MainActivity extends AppCompatActivity {
    private static String base_uri;
    public static final String VOLLEY_TAG = "TEST_PIZZALAND";
    public static final String LOG_TAG = "APPLI";
    private RequestQueue queue;

    private TextView tvDisplay;
    private CheckBox cbIutMain;
    private EditText etServeur;
    private CheckBox cbGetPizzas;
    private CheckBox cbPostIngredient;
    private CheckBox cbGetIngredient;
    private CheckBox cbGetIngredientsAsDto;
    private CheckBox cbGetOnePizzas;

    public static final String SERVER_KEY = "SERVEUR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(MainActivity.this);

        etServeur = findViewById(R.id.etServeur);
        cbIutMain = findViewById(R.id.cbIutMain);
        cbGetPizzas = findViewById(R.id.cbGetPizzas);
        cbGetIngredient = findViewById(R.id.cbGetIngredient);
        cbGetIngredient = findViewById(R.id.cbGetIngredient);
        cbPostIngredient = findViewById(R.id.cbGetIngredientsAsDto);
        cbGetOnePizzas = findViewById(R.id.cbGetOnePizza);
        cbGetIngredient = findViewById(R.id.cbGetIngredient);
        cbGetIngredientsAsDto = findViewById(R.id.cbGetIngredientsAsDto);
        tvDisplay = findViewById(R.id.tvDisplay);

        cbIutMain.setChecked(true);
    }

    private String getHostname() {
        String hostname = "";
        hostname += etServeur.getText().toString();
        if (cbIutMain.isChecked()) {
            hostname += getResources().getString(R.string.iut_domain);
        }
        return hostname;
    }

    private String getFullHostname() {
        String host = "";
        host += "http://";
        host += getHostname();
        host += ":" + String.valueOf(getResources().getInteger(R.integer.default_port));
        host += getResources().getString(R.string.default_base);
        return host;
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showStringResponse(String response) {
        tvDisplay.setText(response);
    }

    public void showJsonArrayResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length() ; i++) {
                JSONObject obj = response.getJSONObject(i);
                Log.d(LOG_TAG, obj.toString());
            }
            tvDisplay.setText(response.toString(4));
        } catch (Exception e) {
            tvDisplay.setText(getResources().getString(R.string.msg_jsonFormatError));
        }
    }

    public void showJsonObjectResponse(JSONObject response) {
        try {
            Log.d(LOG_TAG, response.toString(4));
            tvDisplay.setText(response.toString(4));
        } catch (JSONException e) {
            tvDisplay.setText(getResources().getString(R.string.msg_jsonFormatError));
        }
    }

    public void showPizzasResponse(PizzaShortDto[] response) {
        String display = "";
        for (PizzaShortDto pizza : response) {
            Log.d(LOG_TAG, pizza.toString());
            display += String.format("%2d : %-10s %-18s %6.2f %6.2f \n"
                    ,pizza.getId()
                    ,pizza.getNom()
                    ,pizza.getBase()
                    ,pizza.getPrix_petite()
                    ,pizza.getPrix_grande()
            );
        }
        tvDisplay.setText(display);
    }

    public void showError(VolleyError error) {
        Log.e(LOG_TAG, "Errored Request: " + error.toString());
        if (error.networkResponse == null) {
            tvDisplay.setText(getResources().getString(R.string.msg_networkError, error.toString()));
        } else {
            Cache.Entry header = HttpHeaderParser.parseCacheHeaders(error.networkResponse);
            String charset = HttpHeaderParser.parseCharset(header.responseHeaders);
            try {
                String content = new String(error.networkResponse.data, charset);
                tvDisplay.setText(getResources().getString(R.string.msg_requestError, content));
            } catch (UnsupportedEncodingException e) {
                Log.e(LOG_TAG, "(response) charset not supported");
                e.printStackTrace();
            }
        }
    }

    public void doGetIngredientsAsString(View view) {
        hideKeyboard();
        cbGetIngredient.setChecked(false);
        Log.d(LOG_TAG, "Send started");
        base_uri = getFullHostname();
        final String uri = base_uri + "/ingredients";
        Log.d(LOG_TAG, "Uri: " + uri);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(LOG_TAG, "Response received" + response);
                        showStringResponse(response);
                        cbGetIngredient.setChecked(true);
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MainActivity.this.showError(error);
                    }
                });
        stringRequest.setTag(VOLLEY_TAG);
        queue.add(stringRequest);
        Log.d(LOG_TAG, "Send done");
    }

    public void doGetPizzas(View view) {
        hideKeyboard();
        cbGetPizzas.setChecked(false);
        Log.d(LOG_TAG, "Send started");
        base_uri = getFullHostname();
        final String uri = base_uri + "/pizzas";
        Log.d(LOG_TAG, "Uri: " + uri);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        showJsonArrayResponse(response);
                        cbGetPizzas.setChecked(true);
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showError(error);
                    }
                });
        request.setTag(VOLLEY_TAG);
        queue.add(request);
    }

    public void doGetOnePizza(View view) {
        hideKeyboard();
        cbGetOnePizzas.setChecked(false);
        Log.d(LOG_TAG, "Send started");
        base_uri = getFullHostname();

        final String uri = base_uri + "/pizzas/2";
        Log.d(LOG_TAG, "Uri: " + uri);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showJsonObjectResponse(response);
                        cbGetOnePizzas.setChecked(true);
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MainActivity.this.showError(error);
                    }
                });
        request.setTag(VOLLEY_TAG);
        queue.add(request);
        Log.d(LOG_TAG, "Send done");
    }

    public void doPostIngredient(View view) {
        hideKeyboard();
        cbPostIngredient.setChecked(false);
        Log.d(LOG_TAG, "Send started");
        base_uri = getFullHostname();
        final String uri = base_uri + "/ingredients";
        Log.d(LOG_TAG, "Uri: " + uri);

        JSONObject jsonRequest;
        try {
            jsonRequest = new JSONObject("{'nom' : 'thon' }");

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    uri,
                    jsonRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            showJsonObjectResponse(response);
                            cbPostIngredient.setChecked(true);
                        }
                    },
                    new ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            MainActivity.this.showError(error);
                        }
                    });
            request.setTag(VOLLEY_TAG);
            queue.add(request);
            Log.d(LOG_TAG, "Send done");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing Json request content");
        }
    }

    public void getPizzasAsDto(View view) {
        hideKeyboard();
        cbGetIngredientsAsDto.setChecked(false);
        Log.d(LOG_TAG, "Send started");
        base_uri = getFullHostname();
        final String uri = base_uri + "/pizzas";
        Log.d(LOG_TAG, "Uri: " + uri);

        PizzasRequest pizzasRequest = new PizzasRequest(Request.Method.GET, uri,
                new Listener<PizzaShortDto[]>() {
                    @Override
                    public void onResponse(PizzaShortDto[] response) {
                        Log.d(LOG_TAG, "Response received" + response);
                        showPizzasResponse(response);
                        cbGetIngredientsAsDto.setChecked(true);
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MainActivity.this.showError(error);
                    }
                });
        pizzasRequest.setTag(VOLLEY_TAG);
        queue.add(pizzasRequest);
        Log.d(LOG_TAG, "Send done");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(VOLLEY_TAG);
        }
    }

    public void doCustom(View view) {
        Intent intent = new Intent(this, HttpDialogActivity.class);
        intent.putExtra(SERVER_KEY, getHostname());
        startActivity(intent);
    }
}
