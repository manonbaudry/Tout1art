package fr.ulille.iut.tout1art;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }




    }

    public void onTouchScreen(View view) {

        startActivity(new Intent(this,LoginActivity.class));
    }

}

/*
    candidature via formulaire
    administrateur :
    gestionnaire des différents artisants avec statut différent ; en validation , validé, refusé
    gestion du compte de l'artisan : log avec mdp avec son statut
    proposer des produits via le back office , ---> nom produit, descriptif , image
    administrateur : gérer les demandes
    gestion du catalogue produit avec statut , en attente , mise en ligne..
    mise en ligne du produit
    création du compte pour le client : formulaire nom prénom mail login mdp..
    recherche par catégorie (v1) ranking produit (v2) géolocalisation (v2)
    gestion de panier , consultation , retirer un article , modifier article (v2) , on peut créer
    un panier sans s'identifier
    valider son panier
    choisir son adresse de livraison
    paiement --> pas à faire
    confirmation de la commande avec tout ce qui a été commandé.. où la livraison se fera , mail de
    confirmation au client



    ADMINISTRATEUR

    backoffice sur toutes les commandes qui sont passées (en attente par l'artisan , validé , expédié)
    gérer les réclamations (état : résolu..)
    statistiques : nb de commande , CA réalisé par produit/artisan

    ARTISAN

    y'a une commande je peux changer délai , mettre à jour le statut
    envoyer des informations à l'administrateur , l'avancé (v2)
    une fois fabriqué, administrateur informé


    CLIENT

    Réclamation




 */