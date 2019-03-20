package fr.ulille.iut.pizzaland;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static fr.ulille.iut.pizzaland.MainActivity.LOG_TAG;

public class Data implements Parcelable {
    // Status code Value
    static final int STATUS_IDLE = -1;
    static final int STATUS_WAITING = -2;
    public static int STATUS_NETWORK_ERROR = 999;
    public static int STATUS_INTERNAL_ERROR = 998;


    // Constant linked to order of resource for preset details
    private static final int IDX_CAPTION = 0;
    private static final int IDX_METHOD = 1;
    private static final int IDX_PATH = 2;
    private static final int IDX_QUERY = 3;
    private static final int IDX_CONTENT = 4;

    public final static int CUSTOM_PRESET = 0;
    public final String EMPTY_CONTENT_STRING = "";

    // Request properties Server level (Activity)
    private String host;
    private int port;
    private String base;

    // Request properties Request level (Fragment)
    private int method = Request.Method.GET;
    private String path = "";
    private String query = "";
    private JSONObject requestContent = null;

    // Response properties (actual response is one of jsonObject, jsonArray or string -- only one of these is not null)
    private JSONObject jsonObject = null;
    private JSONArray jsonArray = null;
    private String string = null;
    private String location = null;
    private int statusCode;

    // Preset property
    private int presetIndex;

    //
    public Data() {
    }

    public void setPreset(TypedArray presetResource, int index) {
        presetIndex = index;
        method = presetResource.getInteger(IDX_METHOD, Request.Method.GET);
        path = presetResource.getString(IDX_PATH);
        query = presetResource.getString(IDX_QUERY);
        setRequestContentString(presetResource.getString(IDX_CONTENT));
    }

    public void clearPreset() {
        presetIndex = CUSTOM_PRESET;
    }

    public void setRequestContentString(String string) {
        Log.d(LOG_TAG, "Content --> [" + string + "]");
        if ((string == null) || (string.equals(EMPTY_CONTENT_STRING))) {
            requestContent = null;
        } else {
            try {
                requestContent = new JSONObject(string);
            } catch (JSONException e) {
                requestContent = null;
            }
        }
    }

    public String getRequestContentString() {
        if (hasRequestContent()) {
            try {
                return requestContent.toString(4);
            } catch (JSONException e) {
                Log.e(LOG_TAG, requestContent.toString());
            }
            return "Invalid JSON requestContent";
        } else {
            return EMPTY_CONTENT_STRING;
        }
    }

    public boolean hasRequestContent() {
        return requestContent != null;
    }

    public String getResponseContent() {
        try {
            if (jsonObject != null) {
                return jsonObject.toString(4);
            } else if (jsonArray != null) {
                return jsonArray.toString(4);
            } else {
                return string;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "** ERROR **";
    }

    public void setResponseContent(String string) {
        try {
            jsonObject = new JSONObject(string);
        } catch (JSONException e) {
            try {
                jsonObject = null;
                jsonArray = new JSONArray(string);
            } catch (JSONException e1) {
                jsonArray = null;
                this.string = string;
            }
        }
    }

    public String getFullBase() {
        return String.format("http://%s:%d%s", host, port, base);
    }

    public String getUri() {
        return getFullBase() + path;
    }

    // Parcelable section

    public static Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {

        @Override
        public Data createFromParcel(Parcel parcel) {
            Data data = new Data(parcel);
            return data;
        }

        @Override
        public Data[] newArray(int i) {
            return new Data[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(host);
        parcel.writeInt(port);
        parcel.writeString(base);

        parcel.writeInt(method);
        parcel.writeString(path);
        parcel.writeString(query);
        parcel.writeString(getRequestContentString());

        // Response properties
        parcel.writeString(getResponseContent());
        parcel.writeInt(statusCode);
        parcel.writeInt(presetIndex);
    }

    private Data(Parcel in) {
        host = in.readString();
        port = in.readInt();
        base = in.readString();

        method = in.readInt();
        path = in.readString();
        query = in.readString();
        setRequestContentString(in.readString());

        setResponseContent(in.readString());
        statusCode = in.readInt();
        presetIndex = in.readInt();
    }

    // Getter and Setters section

    public String getHost() {
        return host;
    }

    public void setHost(String host, String iutDomain) {
        this.host = host + iutDomain;
    }

//    private void debug(String tag, String value) {
//        final int OFFSET = 5;
//        StackTraceElement[] trace  = Thread.getAllStackTraces().get(Thread.currentThread()) ;
//        Log.d(LOG_TAG, "******** " + tag);
//        Log.d(LOG_TAG, "Classe: " + trace[OFFSET+1].getClassName());
//        Log.d(LOG_TAG, "Method: " + trace[OFFSET+1].getMethodName());
//        Log.d(LOG_TAG, "Classe: " + trace[OFFSET].getClassName());
//        Log.d(LOG_TAG, "Method: " + trace[OFFSET].getMethodName());
//        Log.d(LOG_TAG, "Line: " + trace[OFFSET].getLineNumber());
//        Log.d(LOG_TAG, "Value: " + value);
//        Log.d(LOG_TAG, "--------------------------------------------");
//    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getPresetIndex() {
        return presetIndex;
    }

    public void setPresetIndex(int presetIndex) {
        this.presetIndex = presetIndex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public JSONObject getRequestContent() {
        return requestContent;
    }

    public void setResponse(PizzalandResponse response) {
        setResponseContent(response.toString());
        statusCode = response.getStatusCode();
        location = response.getLocation();
    }

    public void setError(VolleyError error) {
        String responseContent = null;
        setLocation(null);
        if (error.networkResponse == null) {
            responseContent = "Volley Error : " + error.toString();
            statusCode = STATUS_NETWORK_ERROR;
        } else {
            Cache.Entry header = HttpHeaderParser.parseCacheHeaders(error.networkResponse);
            String charset = HttpHeaderParser.parseCharset(header.responseHeaders);
            try {
                responseContent = new String(error.networkResponse.data, charset);
                statusCode = error.networkResponse.statusCode;
            } catch (UnsupportedEncodingException e) {
                responseContent = "Unsupported character encoding";
                statusCode = STATUS_INTERNAL_ERROR;
            }
        }
        setResponseContent(responseContent);
    }
}
