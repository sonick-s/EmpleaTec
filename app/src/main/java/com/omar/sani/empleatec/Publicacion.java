package com.omar.sani.empleatec;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class Publicacion extends Fragment {

    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_CATEGORY = "category";
    private static final String ARG_IMAGE_URI = "imageUri";
    private static final String ARG_USERNAME = "username";
    private static final String ARG_USER_IMAGE_URI = "userImageUri";

    private String description;
    private String category;
    private Uri imageUri;
    private String username;
    private Uri userImageUri;

    private ImageView imageViewProfile;
    private TextView textViewDescription;
    private TextView textViewUsername;
    private TextView textViewCategoria;
    private ImageView videoViewMain;
    private ImageButton buttonLike;
    private ImageButton buttonSendMessage;
    private ImageButton buttonShare;

    public Publicacion() {
        // Required empty public constructor
    }

    public static Publicacion newInstance(String description, String category, Uri imageUri, String username, Uri userImageUri) {
        Publicacion fragment = new Publicacion();
        Bundle args = new Bundle();
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_CATEGORY, category);
        args.putParcelable(ARG_IMAGE_URI, imageUri);
        args.putString(ARG_USERNAME, username);
        args.putParcelable(ARG_USER_IMAGE_URI, userImageUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            description = getArguments().getString(ARG_DESCRIPTION);
            category = getArguments().getString(ARG_CATEGORY);
            imageUri = getArguments().getParcelable(ARG_IMAGE_URI);
            username = getArguments().getString(ARG_USERNAME);
            userImageUri = getArguments().getParcelable(ARG_USER_IMAGE_URI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_publicacion, container, false);

        imageViewProfile = rootView.findViewById(R.id.imageViewProfile);
        textViewDescription = rootView.findViewById(R.id.textViewDescription);
        textViewUsername = rootView.findViewById(R.id.textViewUsername);
        textViewCategoria = rootView.findViewById(R.id.textViewCategoria);
        videoViewMain = rootView.findViewById(R.id.videoViewMain);
        buttonLike = rootView.findViewById(R.id.buttonLike);
        buttonSendMessage = rootView.findViewById(R.id.buttonSendMessage);
        buttonShare = rootView.findViewById(R.id.buttonShare);

        // Configurar los datos de la publicación
        textViewDescription.setText(description);
        textViewCategoria.setText(category);
        textViewUsername.setText(username);
        if (imageUri != null) {
            Glide.with(this).load(imageUri).centerCrop().into(videoViewMain);
        }

        // Configurar la imagen del perfil del usuario
        if (userImageUri != null) {
            Glide.with(this).load(userImageUri).circleCrop().into(imageViewProfile);
        }

        // Configurar la funcionalidad de despliegue de descripción
        textViewDescription.setMaxLines(3);
        textViewDescription.setEllipsize(android.text.TextUtils.TruncateAt.END);

        textViewDescription.setOnClickListener(new View.OnClickListener() {
            private boolean isExpanded = false;

            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    textViewDescription.setMaxLines(3);
                    textViewDescription.setEllipsize(android.text.TextUtils.TruncateAt.END);
                } else {
                    textViewDescription.setMaxLines(Integer.MAX_VALUE);
                    textViewDescription.setEllipsize(null);
                }
                isExpanded = !isExpanded;
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
