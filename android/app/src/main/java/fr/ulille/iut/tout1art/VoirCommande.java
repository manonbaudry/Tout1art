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
    private int idCommande;
    private ArrayList<String> listeCommande;
    private ArrayList<String> listeCommandeAttente;
    private HashMap<Integer,String> produitNom;
    private HashMap<Integer,Integer> nomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_commande);
        this.layout_commande_attente = (LinearLayout) findViewById(R.id.layout_commande_attente);
        this.layout_commande = (LinearLayout) findViewById(R.id.layout_commande);
        queue = Volley.newRequestQueue(VoirCommande.this);
        queueProduit = Volley.newRequestQueue(VoirCommande.this);
        this.listeCommande = new ArrayList<>();
        this.listeCommandeAttente = new ArrayList<>();
        this.produitNom = new HashMap<>();
        this.nomId = new HashMap<>();
        Intent intentActu = getIntent();
        idArtisan = (int) intentActu.getExtras().get("id");
        getProduit();



    }

    public void showJsonArrayResponseProduit(JSONArray response) {

            try {
                for (int i = 0; i < response.length() ; i++) {
                    JSONObject obj = response.getJSONObject(i);
                    if(!produitNom.containsKey(obj.getInt("id"))) {
                        produitNom.put(obj.getInt("id"), obj.getString("nom"));
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
                    if(!this.nomId.containsKey(obj.get("id"))){
                        this.nomId.put(obj.getInt("id"),obj.getInt("idProduit"));
                    }
                    if(obj.getString("statut").equals("En attente")){
                        this.listeCommandeAttente.add(produitNom.get(obj.getInt("idProduit")));
                    } else if (obj.getString("statut").equals("refuser")){

                    } else {
                        this.listeCommande.add(produitNom.get(obj.getInt("idProduit")));
                    }

                }


            }

            for(final String str : this.listeCommandeAttente){
                System.out.println("dans la liste " + str);
                Button text = new Button(this);
                text.setText(str);
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                int idProduit=0;
                int idPut = 0;
                for(Integer i : this.produitNom.keySet()){
                    if(this.produitNom.get(i).equals(str)){
                        idProduit = i;
                    }
                }
                for(Integer i : this.nomId.keySet()){
                    if(this.nomId.get(i) == idProduit){
                        idPut = i;
                    }
                }
                final int id=idPut;
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFicheCommande(str,id);
                    }
                });
                layout_commande_attente.addView(text);
            }
            for(final String str : this.listeCommande){
                System.out.println("dans la liste " + str);
                Button text = new Button(this);
                text.setText(str);
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                int idPut=0;
                for(Integer i : this.produitNom.keySet()){
                    if(this.produitNom.get(i).equals(str)){
                        idPut = i;
                    }
                }
                final int id=idPut;
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFicheCommande(str,id);
                    }
                });
                layout_commande.addView(text);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showFicheCommande(String nomProduit,int id) {
        Intent i = new Intent(this,FicheCommande.class);
        i.putExtra("NOM_PRODUIT",nomProduit);
        i.putExtra("ID_COMMANDE",id);
        i.putExtra("id",this.idArtisan);
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
