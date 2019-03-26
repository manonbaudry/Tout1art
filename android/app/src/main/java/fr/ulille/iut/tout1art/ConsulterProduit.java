package fr.ulille.iut.tout1art;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private int idArtisan;
    private ArrayList<String> nomProduit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_produit);
        queue = Volley.newRequestQueue(ConsulterProduit.this);
        layout = (LinearLayout) findViewById(R.id.scroller);
        this.nomProduit = new ArrayList<>();
        Intent intentActu = getIntent();
        idArtisan = (int) intentActu.getExtras().get("id");
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        getProduit();

    }

    public void showJsonArrayResponseProduit(JSONArray response) {
        try {
            for (int i = 0; i < response.length() ; i++) {
                JSONObject obj = response.getJSONObject(i);
                System.out.println("NOM : "+obj.getString("nom"));
                if(obj.getInt("idArtisan") == idArtisan){
                    if(!this.nomProduit.contains(obj.getString("nom"))){
                        this.nomProduit.add(obj.getString("nom"));
                    }
                }
            }
            for(final String s : this.nomProduit){
                System.out.println("dans la liste " + s);
                Button text = new Button(this);
                text.setBackgroundResource(R.drawable.button_bg_round_produit);
                text.setText(s);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showProduit(s);
                    }
                });
                layout.addView(text);

            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void showProduit(String prod){
        Intent i = new Intent(this,FicheProduit.class);
        i.putExtra("NOM_PRODUIT",prod);
        startActivity(i);

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
