package com.omar.sani.empleatec.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.Publicacion;
import com.omar.sani.empleatec.R;
import com.omar.sani.empleatec.controlador.database.home.dbCrearPublicacion;
import com.omar.sani.empleatec.controlador.database.login.dbUsuario;
import com.omar.sani.empleatec.crearPublicacion;

import java.util.List;

public class HomeFragment extends Fragment {

    private LinearLayout linearLayoutPublicaciones;
    private ProgressBar progressBar;
    private boolean isLoading = false; // Variable para controlar el estado de carga
    private dbUsuario usuario; // Declara la variable dbUsuario


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializar el contenedor para las publicaciones y la barra de progreso
        linearLayoutPublicaciones = rootView.findViewById(R.id.linearPublicaciones);
        progressBar = rootView.findViewById(R.id.progressBar);
        ScrollView scrollView = rootView.findViewById(R.id.scrollView);

        usuario = new dbUsuario();

        // Mostrar el fragmento crearPublicacion
        mostrarCrearPublicacion();

        // Cargar publicaciones
        cargarPublicaciones();

        // Agregar listener de desplazamiento
        scrollView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN && scrollView.getScrollY() <= 0) {
                // Comenzar a cargar solo si estamos en la parte superior
                isLoading = true;
                mostrarCarga();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                // Finalizar la carga al soltar el toque
                isLoading = false;
                progressBar.setVisibility(View.GONE);
            }
            return false; // Dejar que el evento continúe
        });

        return rootView;
    }

    private void mostrarCarga() {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.postDelayed(() -> {
                if (isLoading) {
                    progressBar.setVisibility(View.GONE);
                    actualizarPublicaciones();
                }
            }, 1000); // Ajusta el tiempo de carga como necesites
        }
    }

    private void mostrarCrearPublicacion() {
        Fragment crearPublicacionFragment = new crearPublicacion();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.crearPublicacionContainer, crearPublicacionFragment)
                .commit();
    }

    private void cargarPublicaciones() {
        dbCrearPublicacion db = new dbCrearPublicacion();
        db.obtenerPublicaciones(new dbCrearPublicacion.OnPublicacionesRetrievedListener() {
            @Override
            public void onSuccess(List<dbCrearPublicacion.Publicacion> publicacionesList) {
                mostrarPublicaciones(publicacionesList);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), "Error al obtener las publicaciones: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarPublicaciones(List<dbCrearPublicacion.Publicacion> publicacionesList) {
        // Obtener el nombre del usuario y la imagen de perfil
        dbUsuario db = new dbUsuario();
        db.obtenerDatosDeUsuarios(new dbUsuario.OnUsuariosLoadedListener() {
            @Override
            public void onSuccess(List<dbUsuario.Usuario> usuarios) {
                // Asumiendo que seleccionas el primer usuario en la lista; ajusta esto según sea necesario
                if (!usuarios.isEmpty()) {
                    dbUsuario.Usuario usuario = usuarios.get(0); // O usa otro método para seleccionar el usuario deseado
                    String username = usuario.getFirstName();
                    Uri userImageUri = usuario.getImageUrl();

                    for (dbCrearPublicacion.Publicacion publicacion : publicacionesList) {
                        String description = publicacion.getDescription();
                        String category = publicacion.getCategory();
                        String imageUri = publicacion.getImageUrl();

                        // Crear el fragmento con los parámetros necesarios
                        Fragment fragment = Publicacion.newInstance(description, category, Uri.parse(imageUri), username, userImageUri);
                        getChildFragmentManager().beginTransaction()
                                .add(R.id.linearPublicaciones, fragment)
                                .commit();
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "Error al obtener datos del usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void actualizarPublicaciones() {
        Toast.makeText(getContext(), "Actualizando publicaciones...", Toast.LENGTH_SHORT).show();
        cargarPublicaciones(); // Llama a cargarPublicaciones para refrescar
    }

}
