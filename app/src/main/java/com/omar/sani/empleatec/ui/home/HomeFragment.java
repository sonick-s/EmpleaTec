package com.omar.sani.empleatec.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.Publicacion;
import com.omar.sani.empleatec.R;
import com.omar.sani.empleatec.controlador.database.home.dbCrearPublicacion;

import java.util.List;

public class HomeFragment extends Fragment {

    private LinearLayout linearLayoutPublicaciones;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        linearLayoutPublicaciones = rootView.findViewById(R.id.linearPublicaciones);

        cargarPublicaciones();

        return rootView;
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
        for (dbCrearPublicacion.Publicacion publicacion : publicacionesList) {
            String description = publicacion.getDescription();
            String category = publicacion.getCategory();
            String imageUri = publicacion.getImageUrl(); // Asegúrate de obtener el Uri como String

            Fragment fragment = Publicacion.newInstance(description, category, Uri.parse(imageUri));
            getChildFragmentManager().beginTransaction()
                    .add(R.id.linearPublicaciones, fragment)
                    .commit();
        }
    }
}
