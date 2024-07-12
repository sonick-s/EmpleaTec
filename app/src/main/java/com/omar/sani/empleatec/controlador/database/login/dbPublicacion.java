package com.omar.sani.empleatec.controlador.database.login;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class dbPublicacion {

    private FirebaseFirestore db;

    public dbPublicacion() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void obtenerPublicaciones(final OnPublicacionesLoadedListener listener) {
        db.collection("publicaciones")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Obtener datos de cada documento
                            String description = documentSnapshot.getString("description");
                            String category = documentSnapshot.getString("category");
                            String imageUrl = documentSnapshot.getString("imageUrl"); // Suponiendo que es la URL de la imagen

                            // Llamar al método onSuccess del listener para cada documento
                            if (listener != null) {
                                listener.onSuccess(description, category, Uri.parse(imageUrl));
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Manejar el error de obtener publicaciones
                        if (listener != null) {
                            listener.onFailure(e);
                        }
                    }
                });
    }

    public interface OnPublicacionesLoadedListener {
        void onSuccess(String description, String category, Uri imageUrl);
        void onFailure(Exception e);
    }

    // Puedes agregar más métodos según sea necesario para trabajar con los datos de las publicaciones
}
