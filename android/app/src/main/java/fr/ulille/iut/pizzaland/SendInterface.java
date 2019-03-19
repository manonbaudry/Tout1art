package fr.ulille.iut.pizzaland;

import com.android.volley.Request;

public interface SendInterface {
    void send(String server, int port, String path, Request.Method method, String json);
}
