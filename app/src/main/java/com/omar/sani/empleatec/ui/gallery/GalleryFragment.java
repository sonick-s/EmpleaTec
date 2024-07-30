package com.omar.sani.empleatec.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.omar.sani.empleatec.R;
import com.omar.sani.empleatec.controlador.database.perfil.dbContacto;
import com.omar.sani.empleatec.controlador.database.perfil.dbEducacion;
import com.omar.sani.empleatec.controlador.database.perfil.dbEncabezado;
import com.omar.sani.empleatec.controlador.database.perfil.dbExperienciaLaboral;
import com.omar.sani.empleatec.controlador.database.perfil.dbHabilidades;
import com.omar.sani.empleatec.controlador.database.perfil.dbProyectos;
import com.omar.sani.empleatec.databinding.FragmentGalleryBinding;
import com.omar.sani.empleatec.mostrarContactoPerfil;
import com.omar.sani.empleatec.mostrarEducacionPerfil;
import com.omar.sani.empleatec.mostrarEncabezadoPerfil;
import com.omar.sani.empleatec.mostrarExperienciaLaboralPerfil;
import com.omar.sani.empleatec.mostrarHabilidadPerfil;
import com.omar.sani.empleatec.mostrarProyectosPerfil;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private dbContacto dbContacto;
    private dbEducacion dbEducacion;
    private dbExperienciaLaboral dbExperienciaLaboral;
    private dbHabilidades dbHabilidad;
    private dbProyectos dbProyectos;
    private dbEncabezado dbEncabezado;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar las instancias de las clases
        dbContacto = new dbContacto();
        dbEducacion = new dbEducacion();
        dbExperienciaLaboral = new dbExperienciaLaboral();
        dbHabilidad = new dbHabilidades();
        dbProyectos = new dbProyectos();
        dbEncabezado = new dbEncabezado();

        // Obtener datos de Firebase
        dbContacto.obtenerDatosContacto(root);
        dbEducacion.obtenerDatosEducacion(root);
        dbExperienciaLaboral.obtenerDatosExperiencia(root);
        dbHabilidad.obtenerDatosHabilidades(root);
        dbProyectos.obtenerDatosProyectos(root);
        dbEncabezado.obtenerDatosEncabezado(root);

        // Cargar los fragmentos
        loadMostrarExperienciaLaboralPerfilFragment("Param1Value", "Param2Value");
        loadMostrarContactoPerfilFragment("Param1Value", "Param2Value");
        loadMostrarEducacionPerfilFragment("Param1Value", "Param2Value");
        loadMostrarEncabezadoPerfilFragment("Param1Value", "Param2Value");
        loadMostrarHabilidadPerfilFragment("Param1Value", "Param2Value");
        loadMostrarProyectosPerfilFragment("Param1Value", "Param2Value");

        return root;
    }

    private void loadMostrarExperienciaLaboralPerfilFragment(String param1, String param2) {
        mostrarExperienciaLaboralPerfil fragment = mostrarExperienciaLaboralPerfil.newInstance(param1, param2);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_experiencia, fragment);
        transaction.commit();
    }

    private void loadMostrarContactoPerfilFragment(String param1, String param2) {
        mostrarContactoPerfil fragment = mostrarContactoPerfil.newInstance(param1, param2);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_contacto, fragment);
        transaction.commit();
    }

    private void loadMostrarEducacionPerfilFragment(String param1, String param2) {
        mostrarEducacionPerfil fragment = mostrarEducacionPerfil.newInstance(param1, param2);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_educacion, fragment);
        transaction.commit();
    }

    private void loadMostrarEncabezadoPerfilFragment(String param1, String param2) {
        mostrarEncabezadoPerfil fragment = mostrarEncabezadoPerfil.newInstance(param1, param2);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_encabezado, fragment);
        transaction.commit();
    }

    private void loadMostrarHabilidadPerfilFragment(String param1, String param2) {
        mostrarHabilidadPerfil fragment = mostrarHabilidadPerfil.newInstance(param1, param2);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_habilidad, fragment);
        transaction.commit();
    }

    private void loadMostrarProyectosPerfilFragment(String param1, String param2) {
        mostrarProyectosPerfil fragment = mostrarProyectosPerfil.newInstance(param1, param2);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_proyectos, fragment);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
