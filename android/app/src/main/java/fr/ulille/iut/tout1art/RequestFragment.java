package fr.ulille.iut.pizzaland;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static fr.ulille.iut.pizzaland.Data.CUSTOM_PRESET;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RequestFragment.InteractionListener} interface
 * to handle interaction events.
 * Use the {@link RequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestFragment extends Fragment {
    private static final String ARG_REQUEST = "request";

    private Data data;

    public void commitData() {
        data.setMethod(spMethod.getSelectedItemPosition());
        data.setPath(etPath.getText().toString());
        data.setRequestContentString(etContent.getText().toString());
        data.setQuery(etQuery.getText().toString());
    }

    public interface InteractionListener {
        void doSend();
        void setHost(String host);
        void setPort(int port);
        void setBase(String base);
        void setMethod(int method);
        void setPath(String path);
        void setQuery(String query);
        void setRequestContent(String requestContent);
        void setPreset(int position);
        void clearPreset();
        TypedArray getPresetArray(int position);
        void hideKeyboard();
    }

    private InteractionListener mListener;
    private TextView tvFullPath;
    private Spinner spMethod;
    private EditText etContent;
    private EditText etPath;
    private Spinner spPreset;
    private Button btSend;
    private EditText etQuery;

    private TextWatcher twContent;
    private TextWatcher twPath;

    public RequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestFragment newInstance() {
        return new RequestFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            mListener = (InteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        tvFullPath = getActivity().findViewById(R.id.tvFullPath);
        spMethod = getActivity().findViewById(R.id.spMethod);
        etContent = getActivity().findViewById(R.id.etContent);
        etPath = getActivity().findViewById(R.id.etPath);
        spPreset = getActivity().findViewById(R.id.spPreset);
        btSend = getActivity().findViewById(R.id.btSend);
        etQuery = getActivity().findViewById(R.id.etQuery);

        updateData();

        etPath.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (! b) { // Lost focus
                    String newPath = etPath.getText().toString();
                    if (! data.getPath().equals(newPath)) {
                        mListener.clearPreset();
                        mListener.setPath(newPath);
                    }
                }
            }
        });
        etQuery.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (! b) { // Lost focus
                    String newQuery = etQuery.getText().toString();
                    if (! data.getPath().equals(newQuery)) {
                        mListener.clearPreset();
                        mListener.setQuery(newQuery);
                    }
                }
            }
        });
        etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (! b) { // Lost focus
                    String newContent = etContent.getText().toString();
                    if (! data.getRequestContentString().equals(newContent)) {
                        mListener.clearPreset();
                        mListener.setRequestContent(newContent);
                    }
                    mListener.hideKeyboard();
                }
            }
        });
        spPreset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == CUSTOM_PRESET) {
                    mListener.clearPreset();
                } else {
                    mListener.setPreset(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mListener.clearPreset();
                mListener.setMethod(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.doSend();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        tvFullPath = null;
        spMethod = null;
        etContent = null;
        etPath = null;
    }

    public void setData(Data data) {
        this.data = data;
    }

    void updateData() {
        if (data != null) {
            tvFullPath.setText(data.getFullBase());
            spMethod.setSelection(data.getMethod());
            spPreset.setSelection(data.getPresetIndex());
            etQuery.setText(data.getQuery());
            if (data.hasRequestContent()) {
                etContent.setText(data.getRequestContentString());
                etContent.setVisibility(View.VISIBLE);
            } else {
                etContent.setVisibility(View.GONE);
            }
            etPath.setText(data.getPath());
        }
    }
}
