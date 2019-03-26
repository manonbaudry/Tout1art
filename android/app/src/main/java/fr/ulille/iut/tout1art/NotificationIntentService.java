package fr.ulille.iut.tout1art;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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

import java.util.HashMap;

public class NotificationIntentService extends IntentService {
    private RequestQueue queue;
    private HashMap<Integer,Boolean> aEteNotifier;

    private MyNotification notification;
    public NotificationIntentService(){
        super("NotificationService");

        this.aEteNotifier = new HashMap<>();
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        // Normalement le traitement lourd se fait ici, comme télécharger un fichier
        // À ne pas faire mais dans cet exemple une attente de 5 secondes est
        // faite, ceci ne bloque pas le « thread UI » puisque nous sommes dans le       // « worker thread »

        while (true) {
            synchronized (this) {
                try {
                   Thread.sleep(5000);
                    notifyAndroid();

                } catch (Exception e) {
                }
            }
        }
    }
    public void showJsonArrayResponseCommande(JSONArray response) {
        String title_notification = "Notification de commande";
        String body_notification = "Vous avez des commandes en attentes de validation !";
        try {
            for (int i = 0; i < response.length() ; i++) {
                JSONObject obj = response.getJSONObject(i);
                if(!this.aEteNotifier.containsKey(obj.getInt("id"))){
                    if(obj.getString("statut").equalsIgnoreCase("en attente")){
                        this.aEteNotifier.put(obj.getInt("id"),false);
                    }
                }
            }

            for(Integer i : this.aEteNotifier.keySet()){
              //  System.out.println("ID commande : " + i + " notification : " + this.aEteNotifier.get(i));
                if(this.aEteNotifier.get(i) == false){
                    createNotification(title_notification,body_notification);
                    aEteNotifier.remove(i);
                    aEteNotifier.put(i,true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void notifyAndroid(){
        this.notification = new MyNotification(this);
        this.queue = Volley.newRequestQueue(this);
        getCommande();
    }
    private final void createNotification(String title, String body){
        Notification.Builder builder = notification.getChannelNotification(title,body);
        notification.getManager().notify(12,builder.build());
    }
    public void getCommande() {
        String ip = getString(R.string.ip);
        String uri = "http://"+ip+"/api/v1/com";
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
}
