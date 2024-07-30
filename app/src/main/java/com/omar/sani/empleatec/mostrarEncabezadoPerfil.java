package com.omar.sani.empleatec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.omar.sani.empleatec.controlador.database.perfil.dbEncabezado;
import com.omar.sani.empleatec.ui.gallery.ValidarEncabezado;

import java.util.List;

public class mostrarEncabezadoPerfil extends Fragment implements ValidarEncabezado.EncabezadoListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String param1;
    private String param2;

    private String userName;
    private String userBio;
    private String profileImageUrl;

    private ImageView profilePicture;
    private Button uploadPictureButton;
    private Button subirExperienciaButton;
    private Button descargarCurriculumButton;
    private TextView userNameView;
    private TextView userBioView;

    public static mostrarEncabezadoPerfil newInstance(String param1, String param2) {
        mostrarEncabezadoPerfil fragment = new mostrarEncabezadoPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        subirExperienciaButton = view.findViewById(R.id.ButtonSubirExperiencia);
        descargarCurriculumButton = view.findViewById(R.id.ButtomDescargarCurriculum);
        userNameView = view.findViewById(R.id.user_name);
        userBioView = view.findViewById(R.id.user_bio);

        // Obtener argumentos
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
        }

        // Configurar los botones
        configurarBotones();

        // Crear instancia de dbEncabezado y obtener la instancia de ValidarEncabezado
        dbEncabezado dbEncabezado = new dbEncabezado();
        ValidarEncabezado validarEncabezado = dbEncabezado.getValidarEncabezado();

        // Establecer este fragmento como el listener para recibir datos de encabezado
        validarEncabezado.setEncabezadoListener(this);
        dbEncabezado.obtenerDatosEncabezado(view);

        return view;
    }

    private void configurarBotones() {
        uploadPictureButton.setOnClickListener(v -> {
            // Lógica para cambiar la foto de perfil
            Toast.makeText(getContext(), "Cambiar foto de perfil", Toast.LENGTH_SHORT).show();
        });

        subirExperienciaButton.setOnClickListener(v -> {
            // Mostrar el fragmento contenedorPublicaciones desde la parte inferior
            contenedorPublicaciones fragment = contenedorPublicaciones.newInstance(userName, userBio);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down);
            transaction.add(R.id.fragment_container_emergente, fragment); // Asegúrate de tener un contenedor en el layout
            transaction.addToBackStack(null); // Opcional: añade a la pila de retroceso
            transaction.commit();

            // Mostrar el contenedor emergente
            View emergenteView = getParentFragment().getView().findViewById(R.id.fragment_container_emergente);
            if (emergenteView != null) {
                emergenteView.setVisibility(View.VISIBLE);
            }
        });

        descargarCurriculumButton.setOnClickListener(v -> {
            // Lógica para descargar curriculum
            Toast.makeText(getContext(), "Descargar currículum", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onEncabezadoDataReceived(List<ValidarEncabezado.Encabezado> encabezadoList) {
        if (encabezadoList != null && !encabezadoList.isEmpty()) {
            ValidarEncabezado.Encabezado encabezado = encabezadoList.get(0); // Obtiene el primer encabezado para mostrar
            userNameView.setText(encabezado.getUserName());
            userBioView.setText(encabezado.getUserBio());
        }
    }
}
