package fr.ulille.iut.tout1art;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ConsulterProduit extends AppCompatActivity {

    private RequestQueue queue;
    private LinearLayout layout;
    private TextView text;
    private int idArtisan;
    private ArrayList<String> nomProduit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_produit);
        queue = Volley.newRequestQueue(ConsulterProduit.this);
        layout = (LinearLayout) findViewById(R.id.layout_consulter_produit);
        text = (TextView) findViewById(R.id.test);
        this.nomProduit = new ArrayList<>();
        this.idArtisan = 1;
        getProduit();
    }

    public void showJsonArrayResponseProduit(JSONArray response) {
        try {
            for (int i = 0; i < response.length() ; i++) {
                JSONObject obj = response.getJSONObject(i);
                System.out.println("NOM : "+obj.getString("nom"))
;                if(obj.getInt("idArtisan") == idArtisan){
                    this.nomProduit.add(obj.getString("nom"));
                }
            }
            String txt="";
            for(String s : this.nomProduit){
                System.out.println("dans la liste " + s);
                txt+=s+"\n";
            }
            text.setText(txt);
        } catch (Exception e) {
            text.setText(e.getMessage());
        }
    }



    public void getArtisan(){
        String uri = "http://10.0.2.2:8080/api/v1/artisan";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,uri,null,new Response.Listener<JSONArray>() {
            TextView tx = findViewById(R.id.textView2);
            @Override
            public void onResponse(JSONArray response) {
                //showJsonArrayResponse(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });

        queue.add(request);
    }

    public void getProduit(){
        String uri = "http://10.0.2.2:8080/api/v1/produit";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,uri,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                showJsonArrayResponseProduit(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                }
        });
        queue.add(request);
    }


}
