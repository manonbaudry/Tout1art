package fr.ulille.iut.tout1art;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

public class BacklogArt extends AppCompatActivity {
    private Button gerer;
    Intent intentActu;
    Intent nextIntent;
    private RequestQueue queue;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_artisant); // modifier pour layout Artisan
        queue = Volley.newRequestQueue(this);
        intentActu = getIntent();
        gerer = (Button) findViewById(R.id.gerer_commande);
        id = (int) intentActu.getExtras().get("id");
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        // The returned result data is identified by requestCode.
        // The request code is specified in startActivityForResult(intent, REQUEST_CODE_1); method.

        if(resultCode == RESULT_OK){
            id = dataIntent.getIntExtra("id",1);
        }
    }


    public  void addProduit(View view){
        nextIntent = new Intent(this,AddProduit.class);
        nextIntent.putExtra("id",id);
        startActivity(nextIntent);
    }
    public void monCompte(View view){
        nextIntent = new Intent(this,MonCompte.class);
        nextIntent.putExtra("id",id);
        startActivity(nextIntent);
    }

    public void consulterProduit(View view){
        nextIntent = new Intent(this,ConsulterProduit.class);
        nextIntent.putExtra("id",id);
        startActivity(nextIntent);
    }

    public void voirCommande(View view){
        nextIntent = new Intent(this,VoirCommande.class);
        nextIntent.putExtra("id",id);
        startActivity(nextIntent);
    }
}
