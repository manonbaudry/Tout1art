package fr.ulille.iut.tout1art;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class FicheCommandeAccepte extends AppCompatActivity {

    private RequestQueue queue;
    private int idArtisan;
    private int idCommande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_commande_attente);
        queue = Volley.newRequestQueue(this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        Intent intentActu = getIntent();
        idArtisan = (int) intentActu.getExtras().get("id");
        if(b != null){
            this.idCommande = (Integer) b.get("ID_COMMANDE");
        }

    }
}
