package fr.ulille.iut.tout1art;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FicheCommande extends AppCompatActivity {

    private TextView num_commande,prix,description,nom_produit,delai;
    private int idArtisan;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_commande);
        this.num_commande = (TextView) findViewById(R.id.num_commande);
        this.prix = (TextView) findViewById(R.id.fiche_prix);
        this.description = (TextView) findViewById(R.id.fiche_description);
        this.nom_produit = (TextView) findViewById(R.id.fiche_nom_produit);
        this.delai = (TextView) findViewById(R.id.fiche_delai);
        queue = Volley.newRequestQueue(FicheCommande.this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        this.idArtisan = 1;
        if(b != null){
            this.nom_produit.setText((String) b.get("NOM_PRODUIT"));
        }
        this.num_commande.setText("Numéro commande : ");
        getProduit();

    }

    public void showJsonArrayResponseProduit(JSONArray response) {
        try {
            for (int i = 0; i < response.length() ; i++) {
                JSONObject obj = response.getJSONObject(i);
                System.out.println("NOM : "+obj.getString("nom"));
                if(obj.getInt("idArtisan") == idArtisan){
                    System.out.println(obj.getString("nom") + "<-- obj getString nom"  +  this.nom_produit.getText().toString());
                    if(obj.getString("nom").equals(this.nom_produit.getText().toString())) {
                        System.out.println("JE TRAITE " + obj.getString("nom"));
                        this.description.setText(obj.getString("description") + ".");
                        this.prix.setText(obj.getString("prix") + " €.");
                        this.delai.setText(obj.getString("delai")+ " semaines.");
                    } else {
                        System.out.println("ICI LAG");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getProduit() {
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

    public void refuser(View view) {
        String uri = "http://10.0.2.2:8080/api/v1/com/";
        uri+="2?statut=refuser";
        StringRequest putRequest = new StringRequest(
                Request.Method.PUT,
                uri,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        System.out.println("REUSSITE");
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                       System.out.println(error);
                       error.printStackTrace();
                    }
                }
        ) {
            
        };
        queue.add(putRequest);
    }

    public void accepter(View view){

    }


}
