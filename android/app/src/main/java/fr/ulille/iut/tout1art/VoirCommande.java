package fr.ulille.iut.tout1art;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;

public class VoirCommande extends AppCompatActivity {

    private LinearLayout layout_commande;
    private RequestQueue queue;
    private int idArtisan;
    private ArrayList<String> listeCommande;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_commande);
        this.layout_commande = (LinearLayout) findViewById(R.id.layout_commande);
        queue = Volley.newRequestQueue(VoirCommande.this);
        this.idArtisan = 1;
        this.listeCommande = new ArrayList<>();
        getCommande();
    }

    public void showJsonArrayResponseCommande(JSONArray response) {
        try {
            for (int i = 0; i < response.length() ; i++) {
                JSONObject obj = response.getJSONObject(i);
                System.out.println("NOM : "+obj.getString("nom"));
                if(obj.getInt("idArtisan") == idArtisan){
                    this.listeCommande.add(obj.getString("nom"));
                }
            }
            for(String s : this.listeCommande){
                System.out.println("dans la liste " + s);
                TextView text = new TextView(this);
                text.setText(s);
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                layout_commande.addView(text);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getCommande() {
        String uri = "http://10.0.2.2:8080/api/v1/produit";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,uri,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                showJsonArrayResponseCommande(response);
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
