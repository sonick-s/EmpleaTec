package com.omar.sani.empleatec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.controlador.database.perfil.dbEncabezado;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubirEncabezadoPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubirEncabezadoPerfil extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Variables para los elementos UI
    private ImageView profilePicture;
    private Button uploadPictureButton;
    private TextView userName;
    private TextView userBio;

    public SubirEncabezadoPerfil() {
        // Required empty public constructor
    }

    public static SubirEncabezadoPerfil newInstance(String param1, String param2) {
        SubirEncabezadoPerfil fragment = new SubirEncabezadoPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_subir_encabezado_perfil, container, false);

        // Inicializar las variables
        profilePicture = view.findViewById(R.id.profile_picture);
        uploadPictureButton = view.findViewById(R.id.upload_picture_button);
        userName = view.findViewById(R.id.user_name);
        userBio = view.findViewById(R.id.user_bio);

        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString().trim();
                String bio = userBio.getText().toString().trim();

                if (validateInputs(name, bio)) {
                    dbEncabezado dbEncabezado = new dbEncabezado();
                    dbEncabezado.enviarDatosEncabezado(name, bio, view);
                }
            }
        });

        return view;
    }

    private boolean validateInputs(String name, String bio) {
        if (name.isEmpty()) {
            showToast("El nombre no puede estar vacío.");
            return false;
        }
        if (bio.isEmpty()) {
            showToast("La biografía es opcional, pero si se proporciona, no puede estar vacía.");
            return true; // Permitir que se envíe aunque esté vacío
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
