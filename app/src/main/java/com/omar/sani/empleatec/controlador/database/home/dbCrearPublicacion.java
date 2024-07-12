package com.omar.sani.empleatec.controlador.database.home;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class dbCrearPublicacion {

    private static final String TAG = "dbCrearPublicacion";

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private List<Publicacion> listaPublicaciones; // Lista interna para almacenar las publicaciones

    public dbCrearPublicacion() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        listaPublicaciones = new ArrayList<>(); // Inicializar la lista de publicaciones
    }

    public void subirPublicacion(String description, String category, Uri imageUri, OnPublicacionUploadListener listener) {
        // Obtener una referencia única para la imagen en Firebase Storage
        StorageReference imageRef = storageRef.child("images/" + imageUri.getLastPathSegment());

        // Subir la imagen a Firebase Storage
        UploadTask uploadTask = imageRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Si la subida de la imagen es exitosa, obtener su URL de descarga
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                // Guardar los datos de la publicación en Firestore
                guardarPublicacionFirestore(description, category, imageUrl, listener);
            }).addOnFailureListener(e -> {
                Log.e(TAG, "Error al obtener la URL de la imagen", e);
                if (listener != null) {
                    listener.onFailure("Error al obtener la URL de la imagen: " + e.getMessage());
                }
            });
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error al subir la imagen", e);
            if (listener != null) {
                listener.onFailure("Error al subir la imagen: " + e.getMessage());
            }
        });
    }

    private void guardarPublicacionFirestore(String description, String category, String imageUrl, OnPublicacionUploadListener listener) {
        // Crear un nuevo objeto Publicacion
        Publicacion publicacion = new Publicacion(description, category, imageUrl);

        // Guardar la publicación en Firestore
        db.collection("publicaciones")
                .add(publicacion)
                .addOnSuccessListener(documentReference -> {
                    if (listener != null) {
                        listener.onSuccess("Publicación subida correctamente");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error al guardar la publicación en Firestore", e);
                    if (listener != null) {
                        listener.onFailure("Error al guardar la publicación: " + e.getMessage());
                    }
                });
    }

    public void obtenerPublicaciones(OnPublicacionesRetrievedListener listener) {
        db.collection("publicaciones")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listaPublicaciones.clear(); // Limpiar la lista antes de agregar nuevas publicaciones
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String description = document.getString("description");
                            String category = document.getString("category");
                            String imageUrl = document.getString("imageUrl");
                            Publicacion publicacion = new Publicacion(description, category, imageUrl);
                            listaPublicaciones.add(publicacion); // Agregar la publicación a la lista interna
                        }
                        if (listener != null) {
                            listener.onSuccess(listaPublicaciones); // Llamar al listener con la lista de publicaciones
                        }
                    } else {
                        Log.e(TAG, "Error al obtener las publicaciones", task.getException());
                        if (listener != null) {
                            listener.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    public interface OnPublicacionUploadListener {
        void onSuccess(String message);
        void onFailure(String errorMessage);
    }

    public interface OnPublicacionesRetrievedListener {
        void onSuccess(List<Publicacion> publicaciones);
        void onFailure(String errorMessage);
    }

    public static class Publicacion {
        private String description;
        private String category;
        private String imageUrl;

        public Publicacion(String description, String category, String imageUrl) {
            this.description = description;
            this.category = category;
            this.imageUrl = imageUrl;
        }

        public String getDescription() {
            return description;
        }

        public String getCategory() {
            return category;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}
