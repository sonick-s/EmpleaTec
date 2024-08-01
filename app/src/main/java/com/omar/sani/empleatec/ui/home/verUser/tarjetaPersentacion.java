package com.omar.sani.empleatec.ui.home.verUser;

import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.omar.sani.empleatec.R;

public class tarjetaPersentacion extends Fragment {

    private static final String ARG_FIRST_NAME = "firstName";
    private static final String ARG_LAST_NAME = "lastName";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_IMAGE_URI = "imageUri";

    private String firstName;
    private String lastName;
    private String description;
    private String imageUri;

    private ImageView profileImage;
    private Button whatsappButton;
    private Button viewMoreButton;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView descriptionTextView;

    public tarjetaPersentacion() {
        // Required empty public constructor
    }

    public static tarjetaPersentacion newInstance(String firstName, String lastName, String description, String imageUri) {
        tarjetaPersentacion fragment = new tarjetaPersentacion();
        Bundle args = new Bundle();
        args.putString(ARG_FIRST_NAME, firstName);
        args.putString(ARG_LAST_NAME, lastName);
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_IMAGE_URI, imageUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            firstName = getArguments().getString(ARG_FIRST_NAME);
            lastName = getArguments().getString(ARG_LAST_NAME);
            description = getArguments().getString(ARG_DESCRIPTION);
            imageUri = getArguments().getString(ARG_IMAGE_URI);
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
        firstNameTextView = view.findViewById(R.id.first_name);
        lastNameTextView = view.findViewById(R.id.last_name);
        descriptionTextView = view.findViewById(R.id.description);

        // Configurar los valores de los componentes
        firstNameTextView.setText(firstName);
        lastNameTextView.setText(lastName);
        descriptionTextView.setText(description);

        if (imageUri != null) {
            Glide.with(this).load(Uri.parse(imageUri)).into(profileImage);
        }
    }
}
