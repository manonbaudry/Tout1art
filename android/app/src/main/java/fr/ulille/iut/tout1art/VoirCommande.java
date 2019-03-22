package fr.ulille.iut.tout1art;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class VoirCommande extends AppCompatActivity {

    private LinearLayout layout_commande;
    private LinearLayout layout_commande_attente;
    private RequestQueue queue,queueProduit;
    private int idArtisan;
    private ArrayList<String> listeCommande;
    private HashMap<Integer,String> produitNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_commande);
        this.layout_commande_attente = (LinearLayout) findViewById(R.id.layout_commande_attente);
        this.layout_commande = (LinearLayout) findViewById(R.id.layout_commande);
        queue = Volley.newRequestQueue(VoirCommande.this);
        queueProduit = Volley.newRequestQueue(VoirCommande.this);
        this.idArtisan = 1;
        this.listeCommande = new ArrayList<>();
        this.produitNom = new HashMap<>();

        getProduit();



    }

    public void showJsonArrayResponseProduit(JSONArray response) {

            try {
                for (int i = 0; i < response.length() ; i++) {
                    JSONObject obj = response.getJSONObject(i);
                    if(!produitNom.containsKey(obj.getInt("id"))){
                        produitNom.put(obj.getInt("id"),obj.getString("nom"));
                    }
                }
                getCommande();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    public void showJsonArrayResponseCommande(JSONArray response) {
        try {
            for (int i = 0; i < response.length() ; i++) {
                JSONObject obj = response.getJSONObject(i);
                if(obj.getInt("idArtisan") == idArtisan){
                    this.listeCommande.add(produitNom.get(obj.getInt("idProduit")));
                    System.out.println(listeCommande.size() + " SIZE");
                }


            }
            System.out.println("ICI");

            for(final String str : this.listeCommande){
                System.out.println("dans la liste " + str);
                Button text = new Button(this);
                text.setText(str);
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFicheCommande(str);
                    }
                });
                layout_commande_attente.addView(text);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showFicheCommande(String nomProduit) {
        Intent i = new Intent(this,FicheCommande.class);
        i.putExtra("NOM_PRODUIT",nomProduit);
        startActivity(i);
    }



    public void getCommande() {
        String uri = "http://10.0.2.2:8080/api/v1/com";
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
        queueProduit.add(request);
    }



}
