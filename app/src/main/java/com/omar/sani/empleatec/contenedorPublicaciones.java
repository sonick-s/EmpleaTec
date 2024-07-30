package com.omar.sani.empleatec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class contenedorPublicaciones extends Fragment {

    // FrameLayouts in the layout
    private FrameLayout subirContacto;
    private FrameLayout subirEducacion;
    private FrameLayout subirEncabezado;
    private FrameLayout subirExperienciaLaboral;
    private FrameLayout subirHabilidad;
    private FrameLayout subirProyecto;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public contenedorPublicaciones() {
        // Required empty public constructor
    }

    public static contenedorPublicaciones newInstance(String param1, String param2) {
        contenedorPublicaciones fragment = new contenedorPublicaciones();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contenedor_publicaciones, container, false);

        // Initialize the FrameLayouts
        subirContacto = view.findViewById(R.id.subirContacto);
        subirEducacion = view.findViewById(R.id.subirEducacion);
        subirEncabezado = view.findViewById(R.id.subirEncabezado);
        subirExperienciaLaboral = view.findViewById(R.id.subirExperienciaLaboral);
        subirHabilidad = view.findViewById(R.id.subirHabilidad);
        subirProyecto = view.findViewById(R.id.subirProyecto);

        // Create and replace the fragments
        replaceFragment(R.id.subirContacto, SubirContactoPerfil.newInstance(mParam1, mParam2));
        replaceFragment(R.id.subirEducacion, SubirEducacionPerfil.newInstance(mParam1, mParam2));
        replaceFragment(R.id.subirEncabezado, SubirEncabezadoPerfil.newInstance(mParam1, mParam2));
        replaceFragment(R.id.subirExperienciaLaboral, SubirExperienciaLaboralPerfil.newInstance(mParam1, mParam2));
        replaceFragment(R.id.subirHabilidad, SubirHabilidadPerfil.newInstance(mParam1, mParam2));
        replaceFragment(R.id.subirProyecto, SubirProyectosPerfil.newInstance(mParam1, mParam2));

        return view;
    }

    private void replaceFragment(int frameLayoutId, Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(frameLayoutId, fragment);
        transaction.commit();
    }
}
