package fr.ulille.iut.pizzaland.testrest.android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import static fr.ulille.iut.pizzaland.testrest.android.MainActivity.LOG_TAG;
import static fr.ulille.iut.pizzaland.testrest.android.MainActivity.VOLLEY_TAG;

public class HttpDialogActivity extends AppCompatActivity
        implements
        RequestFragment.InteractionListener {

    private final static String BUNDLE_KEY = "HTTP_DIALOG_BUNDLE";

    private static final int CUSTOM_PRESET = 0;
    private Data data;

    // Constant linked to order of resource for preset details
    private static final int IDX_CAPTION = 0;
    private static final int IDX_METHOD = 1;
    private static final int IDX_PATH = 2;
    private static final int IDX_QUERY = 3;
    private static final int IDX_CONTENT = 4;

    private RequestQueue queue;

    private EditText etHost;
    private EditText etPort;
    private EditText etBase;
    CheckBox cbIut;

    private RequestFragment reqF = (RequestFragment) getSupportFragmentManager().findFragmentById(R.id.requestFragment);
    private ResponseFragment resF = (ResponseFragment) getSupportFragmentManager().findFragmentById(R.id.responseFragment);

    TypedArray resourceArray;

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setHostname(String host) {
        final String domain = getResources().getString(R.string.iut_domain);
        if (host.endsWith(domain)) {
            host = host.substring(0, host.length() - domain.length());
            cbIut.setChecked(true);
            data.setHost(host, domain);
        } else {
            cbIut.setChecked(false);
            data.setHost(host, "");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_dialog);

        etHost = findViewById(R.id.etHost);
        etPort = findViewById(R.id.etPort);
        etBase = findViewById(R.id.etBase);
        cbIut = findViewById(R.id.cbIut);

        reqF = (RequestFragment) getSupportFragmentManager().findFragmentById(R.id.requestFragment);
        resF = (ResponseFragment) getSupportFragmentManager().findFragmentById(R.id.responseFragment);

        resourceArray = getResources().obtainTypedArray(R.array.preset_requests);

        etHost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    setHost(etHost.getText().toString());
                }
            }
        });
        etPort.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    setPort(Integer.valueOf(etPort.getText().toString()));
                }
            }
        });
        etBase.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    setBase(etHost.getText().toString());
                }
            }
        });

        queue = Volley.newRequestQueue(this);

        if (savedInstanceState != null) {
            data = (Data) savedInstanceState.getParcelable(BUNDLE_KEY);
        } else {
            data = new Data();
            data.setHost(getResources().getString(R.string.default_server), "");
            data.setPort(getResources().getInteger(R.integer.default_port));
            data.setBase(getResources().getString(R.string.default_base));
            data.setStatusCode(Data.STATUS_IDLE);
        }

        Intent intent = getIntent();
        if ((intent != null) && intent.hasExtra(MainActivity.SERVER_KEY)){
            this.setHostname(intent.getStringExtra(MainActivity.SERVER_KEY));
       }
        reqF.setData(data);
        resF.setData(data);

        hideKeyboard();
    }

    public void commitData() {
        data.setHost(etHost.getText().toString(), cbIut.isChecked() ? getResources().getString(R.string.iut_domain) : "");
        data.setPort(Integer.valueOf(etPort.getText().toString()));
        data.setBase(etBase.getText().toString());
        reqF.commitData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        commitData();
        outState.putParcelable(BUNDLE_KEY, data);
        super.onSaveInstanceState(outState);
    }

// Suppressed: redundant with saveInstanceState processing (onCreate)
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        data = (Data)  savedInstanceState.getParcelable(BUNDLE_KEY);
//    }

    @Override
    public void onStart() {
        super.onStart();
        updateData();
    }

    public void onAttach() {

    }

    public void onCnIutClick(View view) {
        commitData();
        setHost(etHost.getText().toString());
    }

    public void updateDomain(View view) {
        setHost(etHost.getText().toString());
        reqF.updateData();
    }

    private void updateData() {
        etHost.setText(data.getHost());
        etPort.setText(String.valueOf(data.getPort()));
        etBase.setText(data.getBase());
        reqF.updateData();
    }

    public TypedArray getPresetArray(int position) {
        return getResources().obtainTypedArray(resourceArray.getResourceId(position, 0));
    }

    private void choosePreset(int indexPreset) {
        if (data != null) {
            if (indexPreset == CUSTOM_PRESET) {
                data.clearPreset();
            } else {
                data.setPreset(getPresetArray(indexPreset), indexPreset);
            }
            reqF.updateData();
        }
    }

    public void doSend(View view) {
        this.commitData();
        this.doSend();
        resF.updateData();
    }

    @Override
    public void doSend() {
        hideKeyboard();
        commitData();
        Toast toast = Toast.makeText(this, "Request sent", Toast.LENGTH_SHORT);
        toast.show();
        Log.d(LOG_TAG, String.valueOf(data.getMethod()));
        Log.d(LOG_TAG, data.getUri());
        Log.d(LOG_TAG, data.getRequestContentString());
        Log.d(LOG_TAG, data.getQuery());
        GenericRequest pizzalandRequest = new GenericRequest(
            data.getMethod(),
            data.getUri(),
            data.getRequestContent(),
            new Response.Listener<PizzalandResponse>() {
                @Override
                public void onResponse(PizzalandResponse response) {
                    Log.d(LOG_TAG, response.toString());
                    data.setResponse(response);
                    resF.updateData();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(LOG_TAG, "Errored Request: " + error.toString());
                    data.setError(error);
                    resF.updateData();
                }
            });
        pizzalandRequest.setTag(MainActivity.VOLLEY_TAG);
        queue.add(pizzalandRequest);
        data.setStatusCode(Data.STATUS_WAITING);
        Log.d(LOG_TAG, "Send done");
    }

    @Override
    public void setHost(String host) {
        etHost.setText(host);
        data.setHost(host, cbIut.isChecked() ? getResources().getString(R.string.iut_domain) : "");
        reqF.updateData();
    }

    @Override
    public void setPort(int port) {
        etPort.setText(String.valueOf(port));
        data.setPort(port);
        updateData();
    }

    @Override
    public void setBase(String base) {
        etBase.setText(base);
        data.setBase(base);
        updateData();
    }

    @Override
    public void setMethod(int method) {
        data.setMethod(method);
        reqF.updateData();
    }

    @Override
    public void setPath(String path) {
        data.setPath(path);
        reqF.updateData();
    }

    @Override
    public void setQuery(String query) {
        data.setQuery(query);
        reqF.updateData();
    }

    @Override
    public void setRequestContent(String requestContent) {
        data.setRequestContentString(requestContent);
    }

    @Override
    public void setPreset(int position) {
        if (data != null) {
            data.setPreset(getPresetArray(position), position);
            reqF.updateData();
        }
    }

    @Override
    public void clearPreset() {
        if (data != null) {
            data.clearPreset();
            reqF.updateData();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(VOLLEY_TAG);
        }
    }

}
