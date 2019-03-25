package fr.ulille.iut.tout1art;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FicheCommandeAccepte extends AppCompatActivity {

    private TextView num_commande,prix,description,nom_produit;
    private RequestQueue queue;
    private int idArtisan;
    private int idCommande;
    private int idProduit;
    private EditText delai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_commande_accepte);
        queue = Volley.newRequestQueue(this);
        this.nom_produit = (TextView) findViewById(R.id.fiche_nom_produit_accepte);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        Intent intentActu = getIntent();
        idArtisan = (int) intentActu.getExtras().get("id");
        if(b != null){
            this.nom_produit.setText((String) b.get("NOM_PRODUIT"));
            this.idCommande = (Integer) b.get("ID_COMMANDE");
        }
        this.num_commande = (TextView) findViewById(R.id.num_commande_accepte);
        this.prix = (TextView) findViewById(R.id.fiche_prix_accepte);
        this.description = (TextView) findViewById(R.id.fiche_description_accepte);

        this.delai = (EditText) findViewById(R.id.delai_edit);
        this.num_commande.setText("Numéro commande : " + idCommande);
        getProduit();

    }

    public void showJsonArrayResponseProduit(JSONArray response) {
        try {
            for (int i = 0; i < response.length() ; i++) {
                JSONObject obj = response.getJSONObject(i);
                if(obj.getInt("idArtisan") == idArtisan){
                    if(obj.getString("nom").equals(this.nom_produit.getText().toString())) {
                        this.delai.setHint(obj.getString("delai"));
                        this.description.setText(obj.getString("description") + ".");
                        this.prix.setText(obj.getString("prix") + " €.");
                        this.idProduit = obj.getInt("id");
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

    public void modifierDelai(View view){
        String uri = "http://10.0.2.2:8080/api/v1/produit/";
        uri+=idProduit;
        JSONObject obj=null;

        try {
            obj = new JSONObject();
            obj.put("delai", this.delai.getText());
        } catch(Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, uri,
                obj,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        System.out.println("REUSSITE");
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        System.out.println(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(putRequest);


        Toast.makeText(getApplicationContext(),"La modification a été prise en compte",Toast.LENGTH_SHORT);
    }
}
