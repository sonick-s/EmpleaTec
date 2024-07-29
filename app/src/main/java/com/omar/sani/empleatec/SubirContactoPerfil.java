package com.omar.sani.empleatec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.controlador.database.perfil.dbContacto;

public class SubirContactoPerfil extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Variables para los elementos UI
    private TextView contactInfoTitle;
    private EditText userEmail;
    private EditText userPhone;
    private Button skillsSaveButton;

    public SubirContactoPerfil() {
        // Required empty public constructor
    }

    public static SubirContactoPerfil newInstance(String param1, String param2) {
        SubirContactoPerfil fragment = new SubirContactoPerfil();
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
        View view = inflater.inflate(R.layout.fragment_subir_contacto_perfil, container, false);

        // Inicializar las variables
        contactInfoTitle = view.findViewById(R.id.contact_info_title);
        userEmail = view.findViewById(R.id.user_email);
        userPhone = view.findViewById(R.id.user_phone);
        skillsSaveButton = view.findViewById(R.id.skills_save_button);

        skillsSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    String email = userEmail.getText().toString().trim();
                    String phone = userPhone.getText().toString().trim();

                    dbContacto dbContacto = new dbContacto();
                    dbContacto.enviarDatosContacto(email, phone, view);
                }
            }
        });

        return view;
    }

    private boolean validateFields() {
        String email = userEmail.getText().toString().trim();
        String phone = userPhone.getText().toString().trim();

        if (email.isEmpty()) {
            userEmail.setError("El email es obligatorio");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("Ingrese un email válido");
            return false;
        }
        if (phone.isEmpty()) {
            userPhone.setError("El teléfono es obligatorio");
            return false;
        }
        if (!phone.matches("\\d{10}")) { // Validar que el teléfono tenga 10 dígitos
            userPhone.setError("Ingrese un número de teléfono válido (10 dígitos)");
            return false;
        }

        return true;
    }
}
