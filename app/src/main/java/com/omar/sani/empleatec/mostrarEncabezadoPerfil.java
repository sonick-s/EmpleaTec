package com.omar.sani.empleatec;

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

import com.omar.sani.empleatec.controlador.database.perfil.dbEncabezado;
import com.omar.sani.empleatec.ui.gallery.ValidarEncabezado;

import java.util.List;

public class mostrarEncabezadoPerfil extends Fragment implements ValidarEncabezado.EncabezadoListener {

    private static final String ARG_USER_NAME = "userName";
    private static final String ARG_USER_BIO = "userBio";

    private String userName;
    private String userBio;

    private ImageView profilePicture;
    private Button uploadPictureButton;
    private TextView userNameView;
    private TextView userBioView;

    public static mostrarEncabezadoPerfil newInstance(String userName, String userBio) {
        mostrarEncabezadoPerfil fragment = new mostrarEncabezadoPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_USER_NAME, userName);
        args.putString(ARG_USER_BIO, userBio);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_encabezado_perfil, container, false);

        // Inicializa las vistas aquí
        profilePicture = view.findViewById(R.id.profile_picture);
        uploadPictureButton = view.findViewById(R.id.upload_picture_button);
        userNameView = view.findViewById(R.id.user_name);
        userBioView = view.findViewById(R.id.user_bio);

        // Obtener argumentos
        if (getArguments() != null) {
            userName = getArguments().getString(ARG_USER_NAME);
            userBio = getArguments().getString(ARG_USER_BIO);
        }

        // Crear instancia de dbEncabezado y obtener la instancia de ValidarEncabezado
        dbEncabezado dbEncabezado = new dbEncabezado();
        ValidarEncabezado validarEncabezado = dbEncabezado.getValidarEncabezado();

        // Establecer este fragmento como el listener para recibir datos de encabezado
        validarEncabezado.setEncabezadoListener(this);
        dbEncabezado.obtenerDatosEncabezado(view);

        return view;
    }

    @Override
    public void onEncabezadoDataReceived(List<ValidarEncabezado.Encabezado> encabezadoList) {
        if (encabezadoList != null && !encabezadoList.isEmpty()) {
            ValidarEncabezado.Encabezado encabezado = encabezadoList.get(0); // Obtiene el primer encabezado para mostrar
            userNameView.setText(encabezado.getUserName());
            userBioView.setText(encabezado.getUserBio());
            // Aquí puedes también manejar la carga de la imagen de perfil si es necesario
        }
    }
}
