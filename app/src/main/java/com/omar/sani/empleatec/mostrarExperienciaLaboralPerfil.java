package com.omar.sani.empleatec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.controlador.database.perfil.dbExperienciaLaboral;
import com.omar.sani.empleatec.ui.gallery.ValidarExperienciaLaboral;
import com.omar.sani.empleatec.ui.gallery.ValidarExperienciaLaboral.Experiencia;

import java.util.List;

public class mostrarExperienciaLaboralPerfil extends Fragment implements ValidarExperienciaLaboral.ExperienciaListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String param1;
    private String param2;

    private TextView workExperienceTitle;
    private ImageView iconCompany;
    private TextView workExperienceCompany;
    private ImageView iconRole;
    private TextView workExperienceRole;
    private ImageView iconDuration;
    private TextView workExperienceDuration;
    private ImageView iconDescription;
    private TextView workExperienceDescription;

    public static mostrarExperienciaLaboralPerfil newInstance(String param1, String param2) {
        mostrarExperienciaLaboralPerfil fragment = new mostrarExperienciaLaboralPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_experiencia_laboral_perfil, container, false);

        // Inicializa tus vistas aquí
        workExperienceTitle = view.findViewById(R.id.work_experience_title);
        iconCompany = view.findViewById(R.id.icon_company);
        workExperienceCompany = view.findViewById(R.id.work_experience_company);
        iconRole = view.findViewById(R.id.icon_role);
        workExperienceRole = view.findViewById(R.id.work_experience_role);
        iconDuration = view.findViewById(R.id.icon_duration);
        workExperienceDuration = view.findViewById(R.id.work_experience_duration);
        iconDescription = view.findViewById(R.id.icon_description);
        workExperienceDescription = view.findViewById(R.id.work_experience_description);

        // Obtener argumentos
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
        }

        // Crear instancia de dbExperienciaLaboral y obtener la instancia de ValidarExperienciaLaboral
        dbExperienciaLaboral dbExperiencia = new dbExperienciaLaboral();
        ValidarExperienciaLaboral validarExperiencia = dbExperiencia.getValidarExperienciaLaboral();

        // Establecer este fragmento como el listener para recibir datos de experiencia laboral
        validarExperiencia.setExperienciaListener(this);
        dbExperiencia.obtenerDatosExperiencia(view);

        return view;
    }

    @Override
    public void onExperienciaDataReceived(List<Experiencia> experienciaList) {
        if (experienciaList != null && !experienciaList.isEmpty()) {
            Experiencia experiencia = experienciaList.get(0); // Obtiene la primera experiencia para mostrar
            workExperienceCompany.setText(experiencia.getCompany());
            workExperienceRole.setText(experiencia.getRole());
            workExperienceDuration.setText(experiencia.getDuration());
            workExperienceDescription.setText(experiencia.getDescription());
        }
    }
}
