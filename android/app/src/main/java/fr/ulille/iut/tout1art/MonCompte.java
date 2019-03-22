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


public class MonCompte extends AppCompatActivity {
    int id;
    private RequestQueue queue;
    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_compte);
        queue = Volley.newRequestQueue(MonCompte.this);
        id = 1;
        getArtisan();

    }

    public void getArtisan(){
        String uri = "http://10.0.2.2:8080/api/v1/artisan";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        showArt(response);
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

    public void showArt(JSONArray response){
        String str = "";
        try {
            for (int i = 0; i < response.length() ; i++) {
                JSONObject obj = response.getJSONObject(i);
                System.out.println("NOM : "+obj.getString("nom"));
                if(obj.getInt("id") == id){
                    //str += obj.getInt("id" + "\n");
                    TextView mail = findViewById(R.id.caseMail);
                    mail.setText(obj.getString("mail"));
                    TextView mdp = findViewById(R.id.caseMdp);
                    mdp.setText(obj.getString("mdp"));
                    TextView nom = findViewById(R.id.caseNom);
                    nom.setText(obj.getString("nom"));
                    TextView prenom = findViewById(R.id.casePrenom);
                    prenom.setText(obj.getString("prenom"));
                    TextView tel = findViewById(R.id.caseTelephone);
                    tel.setText(obj.getString("tel"));
                    TextView addresse = findViewById(R.id.caseAdresse);
                    addresse.setText(obj.getString("adresse"));
                    //str += obj.getString("nom" ) + "nom: \n";
                    //str += obj.getString("prenom") + "prenom: \n";
                    //str += obj.getString("adresse") + "adresse: \n";
                    //str += obj.getString("mail") + "mail: \n";
                    //str += obj.getString("tel") + "tel: \n";

                }
            }

            System.out.println("LE TXT "+str);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    ida
    login
    nom
    prenom
    adresse
    mail
    tel
     */
}
