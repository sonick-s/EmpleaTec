package com.omar.sani.empleatec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.controlador.database.perfil.dbHabilidades;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubirHabilidadPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubirHabilidadPerfil extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Variables para los elementos UI
    private TextView skillsTitle;
    private EditText skillsInput;
    private Button skillsSaveButton;

    public SubirHabilidadPerfil() {
        // Required empty public constructor
    }

    public static SubirHabilidadPerfil newInstance(String param1, String param2) {
        SubirHabilidadPerfil fragment = new SubirHabilidadPerfil();
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
        View view = inflater.inflate(R.layout.fragment_subir_habilidad_perfil, container, false);

        // Inicializar las variables
        skillsTitle = view.findViewById(R.id.skills_title);
        skillsInput = view.findViewById(R.id.skills_input);
        skillsSaveButton = view.findViewById(R.id.skills_save_button);

        skillsSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habilidad = skillsInput.getText().toString().trim();

                if (validateInputs(habilidad)) {
                    dbHabilidades dbHabilidades = new dbHabilidades();
                    dbHabilidades.enviarDatosHabilidades(habilidad, view);
                }
            }
        });

        return view;
    }

    private boolean validateInputs(String habilidad) {
        if (habilidad.isEmpty()) {
            showToast("El campo de habilidad no puede estar vacío.");
            return false;
        }
        // Aquí puedes agregar más validaciones si es necesario

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
