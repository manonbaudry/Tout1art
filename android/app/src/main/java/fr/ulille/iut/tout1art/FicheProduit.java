package fr.ulille.iut.tout1art;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class FicheProduit extends AppCompatActivity {

    private RequestQueue queue;
    String id_produit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_produit);
        queue = Volley.newRequestQueue(FicheProduit.this);

        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        Intent intent = getIntent();
        id_produit = (String) intent.getExtras().get("NOM_PRODUIT");
        afficher();

    }

    public void afficher(){
        String ip = getString(R.string.ip);
        String uri = "http://"+ip+"/api/v1/produit";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        showProd(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("erreur : "+error);
                    }
                });
        queue.add(request);
    }


    public void showProd(JSONArray response){
        String str = "";
        try {
            for (int i = 0; i < response.length() ; i++) {
                JSONObject obj = response.getJSONObject(i);
                if(obj.getString("nom").equals(id_produit)){
                    //str += obj.getInt("id" + "\n");

                    TextView nom = findViewById(R.id.NomProd);
                    nom.setText(obj.getString("nom"));

                    TextView cate = findViewById(R.id.catProd);
                    cate.setText(obj.getString("categorie"));

                    TextView sousCate = findViewById(R.id.soCatProd);
                    sousCate.setText(obj.getString("sousCategorie"));

                    TextView desc = findViewById(R.id.descProd);
                    desc.setText(obj.getString("description"));

                    TextView delai = findViewById(R.id.delaiProd);
                    delai.setText(obj.getString("delai"));

                }
            }

            //System.out.println("LE TXT "+str);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

