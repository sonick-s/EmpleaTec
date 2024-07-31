package com.omar.sani.empleatec.ui.home.verUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.R;

public class tarjetaPersentacion extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageView profileImage;
    private Button whatsappButton;
    private Button viewMoreButton;
    private TextView firstName;
    private TextView lastName;
    private TextView description;

    public tarjetaPersentacion() {
        // Required empty public constructor
    }

    public static tarjetaPersentacion newInstance(String param1, String param2) {
        tarjetaPersentacion fragment = new tarjetaPersentacion();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tarjeta_persentacion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar los componentes
        profileImage = view.findViewById(R.id.profile_image);
        whatsappButton = view.findViewById(R.id.whatsapp_button);
        viewMoreButton = view.findViewById(R.id.view_more_button);
        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        description = view.findViewById(R.id.description);

        // Aquí puedes configurar los valores de los componentes si es necesario
        // Por ejemplo:
        // firstName.setText("John");
        // lastName.setText("Doe");
        // description.setText("Software Developer");
    }
}
