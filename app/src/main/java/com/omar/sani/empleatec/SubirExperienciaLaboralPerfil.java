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

import com.omar.sani.empleatec.controlador.database.perfil.dbExperienciaLaboral;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubirExperienciaLaboralPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubirExperienciaLaboralPerfil extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Variables para los elementos UI
    private TextView workExperienceTitle;
    private EditText workExperienceCompany;
    private EditText workExperienceRole;
    private EditText workExperienceDuration;
    private EditText workExperienceDescription;
    private Button skillsSaveButton;

    public SubirExperienciaLaboralPerfil() {
        // Required empty public constructor
    }

    public static SubirExperienciaLaboralPerfil newInstance(String param1, String param2) {
        SubirExperienciaLaboralPerfil fragment = new SubirExperienciaLaboralPerfil();
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
        View view = inflater.inflate(R.layout.fragment_subir_experiencia_laboral_perfil, container, false);

        // Inicializar las variables
        workExperienceTitle = view.findViewById(R.id.work_experience_title);
        workExperienceCompany = view.findViewById(R.id.work_experience_company);
        workExperienceRole = view.findViewById(R.id.work_experience_role);
        workExperienceDuration = view.findViewById(R.id.work_experience_duration);
        workExperienceDescription = view.findViewById(R.id.work_experience_description);
        skillsSaveButton = view.findViewById(R.id.skills_save_button);

        skillsSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company = workExperienceCompany.getText().toString().trim();
                String role = workExperienceRole.getText().toString().trim();
                String duration = workExperienceDuration.getText().toString().trim();
                String description = workExperienceDescription.getText().toString().trim();

                if (validateInputs(company, role, duration, description)) {
                    dbExperienciaLaboral dbExperiencia = new dbExperienciaLaboral();
                    dbExperiencia.enviarDatosExperiencia(company, role, duration, description, view);
                }
            }
        });

        return view;
    }

    private boolean validateInputs(String company, String role, String duration, String description) {
        if (company.isEmpty()) {
            showToast("El campo de la empresa no puede estar vacío.");
            return false;
        }
        if (role.isEmpty()) {
            showToast("El campo del rol no puede estar vacío.");
            return false;
        }
        if (duration.isEmpty()) {
            showToast("El campo de la duración no puede estar vacío.");
            return false;
        }
        // Validación opcional para la descripción
        if (description.isEmpty()) {
            showToast("La descripción es opcional, pero si se proporciona, no puede estar vacía.");
            return true; // Permitir que se envíe aunque esté vacío
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
