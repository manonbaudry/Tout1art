package fr.ulille.iut.tout1art;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BacklogArt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_artisant); // modifier pour layout Artisan
    }

    public void connect(View view){
        startActivity(new Intent(this,testCo.class));
    }

    public void monCompte(View view){
        startActivity(new Intent(this,MonCompte.class));
    }

    public void consulterProduit(View view){
        startActivity(new Intent(this,ConsulterProduit.class));
    }
}
