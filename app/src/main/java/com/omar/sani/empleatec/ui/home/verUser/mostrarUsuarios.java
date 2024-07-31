package com.omar.sani.empleatec.ui.home.verUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.omar.sani.empleatec.R;

public class mostrarUsuarios extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public mostrarUsuarios() {
        // Required empty public constructor
    }

    public static mostrarUsuarios newInstance(String param1, String param2) {
        mostrarUsuarios fragment = new mostrarUsuarios();
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
        return inflater.inflate(R.layout.fragment_mostrar_usuarios, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Iniciar el fragmento tarjetaPersentacion dentro de mostrarUsuarios
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        tarjetaPersentacion tarjetaFragment = tarjetaPersentacion.newInstance(mParam1, mParam2);
        transaction.replace(R.id.fragment_mostrar_Usuarios, tarjetaFragment);
        transaction.commit();
    }
}
