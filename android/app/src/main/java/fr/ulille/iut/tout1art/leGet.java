
   BaseAccess base = new BaseAccess(this);

public void doGetEvenementsAsString(String path) {
        String uri = "http://10.0.2.2:8080/api/v1/myresource";
        Log.d(base.LOG_TAG, "Uri: " + uri);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                uri,//ton url ici
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        showEvent(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        base.showError(error);
                    }
                });

        arrayRequest.setTag(base.VOLLEY_TAG);
        base.queue.add(arrayRequest);
        Log.d(base.LOG_TAG, "Send done");
    }
