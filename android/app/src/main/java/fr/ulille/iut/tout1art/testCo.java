package fr.ulille.iut.tout1art;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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

public class testCo extends AppCompatActivity {
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_co);
        queue = Volley.newRequestQueue(testCo.this);
        String uri = "http://10.0.2.2:8080/api/v1/artisan";


        StringRequest request = new StringRequest(
                Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    TextView tx = findViewById(R.id.textView2);
                    @Override
                    public void onResponse(String response) {
                        tx.setText(response.toString());
                        System.out.println("LA REPONSE C EST LA : "+response+ " FIN");
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        queue.add(request);
    }

}
