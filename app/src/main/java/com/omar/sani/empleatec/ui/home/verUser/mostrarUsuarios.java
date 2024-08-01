package com.omar.sani.empleatec.ui.home.verUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.R;
import com.omar.sani.empleatec.controlador.database.login.dbUsuario;

import java.util.List;

public class mostrarUsuarios extends Fragment {

    private LinearLayout linearLayoutUsuarios;
    private ProgressBar progressBar;
    private boolean isLoading = false;

    public mostrarUsuarios() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mostrar_usuarios, container, false);

        linearLayoutUsuarios = rootView.findViewById(R.id.linearUsuarios);
        progressBar = rootView.findViewById(R.id.progressBarUsuarios);
        ScrollView scrollView = rootView.findViewById(R.id.scrollViewUsuarios);

        cargarUsuarios();

        scrollView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN && scrollView.getScrollY() <= 0) {
                isLoading = true;
                mostrarCarga();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                isLoading = false;
                progressBar.setVisibility(View.GONE);
            }
            return false;
        });

        return rootView;
    }

    private void mostrarCarga() {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.postDelayed(() -> {
                if (isLoading) {
                    progressBar.setVisibility(View.GONE);
                    actualizarUsuarios();
                }
            }, 1000);
        }
    }

    private void cargarUsuarios() {
        dbUsuario db = new dbUsuario();
        db.obtenerDatosDeUsuarios(new dbUsuario.OnUsuariosLoadedListener() {
            @Override
            public void onSuccess(List<dbUsuario.Usuario> usuariosList) {
                mostrarUsuarios(usuariosList);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "Error al obtener los usuarios: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarUsuarios(List<dbUsuario.Usuario> usuariosList) {
        for (dbUsuario.Usuario usuario : usuariosList) {
            String firstName = usuario.getFirstName();
            String lastName = usuario.getLastName();
            String description = ""; // Si tienes una descripción, ajusta según sea necesario
            String imageUri = usuario.getImageUrl().toString();

            Fragment fragment = tarjetaPersentacion.newInstance(firstName, lastName, description, imageUri);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.linearUsuarios, fragment)
                    .commit();
        }
    }

    private void actualizarUsuarios() {
        Toast.makeText(getContext(), "Actualizando usuarios...", Toast.LENGTH_SHORT).show();
        cargarUsuarios();
    }
}
