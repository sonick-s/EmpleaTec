package com.omar.sani.empleatec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Mensajeschats#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mensajeschats extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Variables para los elementos del layout
    private LinearLayout layoutMessagesContent;
    private LinearLayout layoutMessages;
    private TextView textViewMessage1;
    private TextView textViewMessageDos;

    public Mensajeschats() {
        // Required empty public constructor
    }

    public static Mensajeschats newInstance(String param1, String param2) {
        Mensajeschats fragment = new Mensajeschats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_mensajeschats, container, false);

        // Referenciar los elementos del layout
        layoutMessagesContent = view.findViewById(R.id.layoutMessagesContent);
        layoutMessages = view.findViewById(R.id.layoutMessages);
        textViewMessage1 = view.findViewById(R.id.textViewMessage1);
        textViewMessageDos = view.findViewById(R.id.textViewMessageDos);

        return view;
    }
}
