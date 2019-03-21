package fr.ulille.iut.tout1art;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class testCo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_co);
        TextView t = findViewById(R.id.textView2);
        String s = String.format("http://%s:%d%s", "10.0.2.2", 8080, "/api/v1/myresource");
        t.setText(s);
        try {
            URL url = new URL("http://localhost:80/preouverture_java/liste.xml");
            URLConnection urlCon = url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.connect();
            t.setText("reussi");
        } catch (Exception e) {
            t.setText("erreur");
            //t.setText(e.getMessage());
        }
    }

}
