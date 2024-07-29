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

import com.omar.sani.empleatec.controlador.database.perfil.dbProyectos;
import com.omar.sani.empleatec.ui.gallery.ValidarProyectos;
import com.omar.sani.empleatec.ui.gallery.ValidarProyectos.Proyecto;

import java.util.List;

public class mostrarProyectosPerfil extends Fragment implements ValidarProyectos.ProyectoListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String param1;
    private String param2;

    private TextView projectsTitle;
    private TextView projectName;
    private TextView projectDescription;
    private TextView projectUrl;
    private TextView projectCategory;
    private ImageView projectImage;

    public static mostrarProyectosPerfil newInstance(String param1, String param2) {
        mostrarProyectosPerfil fragment = new mostrarProyectosPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_proyectos_perfil, container, false);

        // Inicializar las variables con los elementos del layout
        projectsTitle = view.findViewById(R.id.projects_title);
        projectName = view.findViewById(R.id.project_name);
        projectDescription = view.findViewById(R.id.project_description);
        projectUrl = view.findViewById(R.id.project_url);
        projectCategory = view.findViewById(R.id.project_category);
        projectImage = view.findViewById(R.id.project_image);

        // Obtener argumentos
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
        }

        // Crear instancia de dbProyectos y obtener la instancia de ValidarProyectos
        dbProyectos dbProyectos = new dbProyectos();
        ValidarProyectos validarProyectos = dbProyectos.getValidarProyectos();

        // Establecer este fragmento como el listener para recibir datos de proyectos
        validarProyectos.setProyectoListener(this);
        dbProyectos.obtenerDatosProyectos(view);

        return view;
    }

    @Override
    public void onProyectoDataReceived(List<Proyecto> proyectoList) {
        if (proyectoList != null && !proyectoList.isEmpty()) {
            Proyecto proyecto = proyectoList.get(0); // Obtiene el primer proyecto para mostrar
            projectName.setText(proyecto. getNombre());
            projectDescription.setText(proyecto.getDescripcion());
            projectUrl.setText(proyecto. getUrl());
            projectCategory.setText(proyecto.getCategoria());
            // Aquí puedes añadir lógica para cargar la imagen del proyecto
            // Por ejemplo, usando una biblioteca como Glide o Picasso para cargar la imagen
        }
    }
}
