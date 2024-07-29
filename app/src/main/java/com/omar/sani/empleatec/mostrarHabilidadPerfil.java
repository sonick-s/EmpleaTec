package com.omar.sani.empleatec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.controlador.database.perfil.dbHabilidades;
import com.omar.sani.empleatec.ui.gallery.ValidarHabilidades;
import com.omar.sani.empleatec.ui.gallery.ValidarHabilidades.Habilidad;

import java.util.List;

public class mostrarHabilidadPerfil extends Fragment implements ValidarHabilidades.HabilidadListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String param1;
    private String param2;

    private TextView skillsTitle;
    private TextView skill1;

    public static mostrarHabilidadPerfil newInstance(String param1, String param2) {
        mostrarHabilidadPerfil fragment = new mostrarHabilidadPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_habilidad_perfil, container, false);

        // Inicializar las vistas
        skillsTitle = view.findViewById(R.id.skills_title);
        skill1 = view.findViewById(R.id.skill_1);

        // Obtener argumentos
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
        }

        // Crear instancia de dbHabilidades y obtener la instancia de ValidarHabilidades
        dbHabilidades dbHabilidades = new dbHabilidades();
        ValidarHabilidades validarHabilidades = dbHabilidades.getValidarHabilidades();

        // Establecer este fragmento como el listener para recibir datos de habilidades
        validarHabilidades.setHabilidadListener(this);
        dbHabilidades.obtenerDatosHabilidades(view);

        return view;
    }

    @Override
    public void onHabilidadDataReceived(List<Habilidad> habilidadList) {
        if (habilidadList != null && !habilidadList.isEmpty()) {
            Habilidad habilidad = habilidadList.get(0); // Obtiene la primera habilidad para mostrar
            skillsTitle.setText("Habilidades"); // O ajusta el título si es necesario
            skill1.setText(habilidad.getHabilidad());
        }
    }
}
