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

import com.omar.sani.empleatec.controlador.database.perfil.dbEducacion;
import com.omar.sani.empleatec.ui.gallery.ValidarEducacion;
import com.omar.sani.empleatec.ui.gallery.ValidarEducacion.Educacion;

import java.util.List;

public class mostrarEducacionPerfil extends Fragment implements ValidarEducacion.EducacionListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String param1;
    private String param2;

    private TextView educationTitle;
    private ImageView iconInstitution;
    private TextView educationInstitution;
    private ImageView iconDegree;
    private TextView educationDegree;
    private ImageView iconDuration;
    private TextView educationYears;
    private ImageView iconCategory;
    private TextView categoryTitle;

    public static mostrarEducacionPerfil newInstance(String param1, String param2) {
        mostrarEducacionPerfil fragment = new mostrarEducacionPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_educacion_perfil, container, false);

        // Inicializa tus vistas aquí
        educationTitle = view.findViewById(R.id.education_title);
        iconInstitution = view.findViewById(R.id.icon_institution);
        educationInstitution = view.findViewById(R.id.education_institution);
        iconDegree = view.findViewById(R.id.icon_degree);
        educationDegree = view.findViewById(R.id.education_degree);
        iconDuration = view.findViewById(R.id.icon_duration);
        educationYears = view.findViewById(R.id.education_years);
        iconCategory = view.findViewById(R.id.icon_category);
        categoryTitle = view.findViewById(R.id.Categoria_title);

        // Obtener argumentos
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
        }

        // Crear instancia de dbEducacion y obtener la instancia de ValidarEducacion
        dbEducacion dbEduc = new dbEducacion();
        ValidarEducacion validarEduc = dbEduc.getValidarEducacion();

        // Establecer este fragmento como el listener para recibir datos de educación
        validarEduc.setEducacionListener(this);
        dbEduc.obtenerDatosEducacion(view);

        return view;
    }

    @Override
    public void onEducacionDataReceived(List<Educacion> educacionList) {
        if (educacionList != null && !educacionList.isEmpty()) {
            Educacion educacion = educacionList.get(0); // Obtiene la primera educación para mostrar
            educationInstitution.setText(educacion.getInstitution());
            educationDegree.setText(educacion.getDegree());
            educationYears.setText(educacion.getYears());
            categoryTitle.setText(educacion.getCategory());
        }
    }
}
