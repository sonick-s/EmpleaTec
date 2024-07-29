package com.omar.sani.empleatec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class perfilChat extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Variables para los elementos del layout
    private ImageView profileImage;
    private TextView usernameText;
    private TextView messageText;
    private ImageButton settingsButton; // Variable para el botón de configuración

    public perfilChat() {
        // Required empty public constructor
    }

    public static perfilChat newInstance(String param1, String param2) {
        perfilChat fragment = new perfilChat();
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
        View view = inflater.inflate(R.layout.fragment_perfil_chat, container, false);

        // Referenciar los elementos del layout
        profileImage = view.findViewById(R.id.imageViewProfile); // Asegúrate de que el ID sea correcto
        usernameText = view.findViewById(R.id.textViewUsername); // Asegúrate de que el ID sea correcto
        messageText = view.findViewById(R.id.textViewMessage); // Asegúrate de que el ID sea correcto
        settingsButton = view.findViewById(R.id.buttonSettings); // Referencia al botón de configuración

        // Configurar el OnClickListener para el botón de configuración
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes agregar la lógica para abrir la configuración
            }
        });

        return view;
    }
}
