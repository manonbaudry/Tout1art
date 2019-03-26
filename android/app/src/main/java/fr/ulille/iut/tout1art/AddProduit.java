package fr.ulille.iut.tout1art;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Queue;

public class AddProduit extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produit);
        intent = getIntent();
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    public void send(View view){
        System.out.println("DEBUT");
        JSONObject produit = new JSONObject();

        TextView n = findViewById(R.id.nom);
        String nom = n.getText().toString();

        TextView cat = findViewById(R.id.categorie);
        String categorie = cat.getText().toString();

        TextView sous = findViewById(R.id.souscat);
        String img = "images/"+sous.getText().toString()+".jpeg";
        System.out.println("SOUSCAT : "+  img);

        TextView des = findViewById(R.id.description);
        String description = des.getText().toString();

        TextView p = findViewById(R.id.prix);
        String pr = p.getText().toString();
        double prix = Double.parseDouble(pr);

        TextView d = findViewById(R.id.delai);
        String del = d.getText().toString();
        int delai = Integer.parseInt(del);

        try {

            // crea JSON

            produit.put("nom", nom);
            produit.put("categorie",categorie.toLowerCase());
            produit.put("srcImage", img.toLowerCase());
            produit.put("description", description);
            produit.put("prix", prix);
            produit.put("delai", delai);
            produit.put("idArtisan", (int)intent.getExtras().get("id"));
            //produit.put("srcImage", "images/table.jpg");
            produit.put("commande", 0);
            produit.put("statut", "en attente");

            System.out.println(produit.toString());

            // POST
            RequestQueue queue = Volley.newRequestQueue(AddProduit.this);
            String uri = "http://10.0.2.2:8080/api/v1/produit";
            JSONObject jsonRequest;
            try {
                jsonRequest = produit;

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        uri,
                        jsonRequest,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println("post : " + response.toString());

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error);
                            }
                        });
                queue.add(request);
            } catch (Exception e) {
                System.out.println("erreur" + e.getMessage());
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println(nom);

    }
}
