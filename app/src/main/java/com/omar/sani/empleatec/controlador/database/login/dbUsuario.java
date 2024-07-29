package com.omar.sani.empleatec.controlador.database.login;

import static com.omar.sani.empleatec.controlador.ConfigGmail.IdGmailEmpresa;
import static com.omar.sani.empleatec.controlador.ConfigGmail.IdGmailUsuario;

import android.net.Uri;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class dbUsuario {
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    public dbUsuario() {
        this.db = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    public void enviarDatosALaBaseDeDatos(String firstName, String lastName, String dateOfBirth, String email, Uri imageUri, View view) {
        // Validar que la URI de la imagen no sea nula
        if (imageUri == null) {
            Snackbar.make(view, "Por favor, carga una imagen de perfil", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Subir la imagen a Firebase Storage
        subirImagenAFirebase(imageUri, new OnImageUploadListener() {
            @Override
            public void onSuccess(String imageUrl) {
                // Crear un mapa de datos con los campos del usuario
                Map<String, Object> usuario = new HashMap<>();
                usuario.put("TipoEmpresa", IdGmailEmpresa);
                usuario.put("TipoUsuario", IdGmailUsuario);
                usuario.put("Primer Nombre", firstName);
                usuario.put("Apellido", lastName);
                usuario.put("Fecha de Nacimiento", dateOfBirth);
                usuario.put("Correo Electronico", email);
                usuario.put("Imagen de Perfil", imageUrl);

                // Enviar los datos a Firestore
                db.collection("usuarios")
                        .add(usuario)
                        .addOnSuccessListener(documentReference -> Snackbar.make(view, "Datos enviados correctamente a Firestore", Snackbar.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Snackbar.make(view, "Error al enviar datos a Firestore", Snackbar.LENGTH_SHORT).show());
            }

            @Override
            public void onFailure(Exception e) {
                Snackbar.make(view, "Error al subir la imagen: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void subirImagenAFirebase(Uri imageUri, final OnImageUploadListener listener) {
        if (imageUri != null) {
            StorageReference storageRef = storage.getReference().child("images/" + System.currentTimeMillis() + ".jpg");
            storageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> listener.onSuccess(uri.toString())))
                    .addOnFailureListener(listener::onFailure);
        } else {
            listener.onFailure(new Exception("La URI de la imagen es nula"));
        }
    }

    public void obtenerDatosDeUsuarios(final OnUsuariosLoadedListener listener) {
        db.collection("usuarios")
                .get()
                .addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Obtener datos de cada documento
                            String tipoEmpresa = documentSnapshot.getString("TipoEmpresa");
                            String tipoUsuario = documentSnapshot.getString("TipoUsuario");
                            String firstName = documentSnapshot.getString("Primer Nombre");
                            String lastName = documentSnapshot.getString("Apellido");
                            String dateOfBirth = documentSnapshot.getString("Fecha de Nacimiento");
                            String email = documentSnapshot.getString("Correo Electronico");
                            String imageUrl = documentSnapshot.getString("Imagen de Perfil");

                            // Llamar al método onSuccess del listener para cada documento
                            if (listener != null) {
                                listener.onSuccess(tipoEmpresa, tipoUsuario, firstName, lastName, dateOfBirth, email, Uri.parse(imageUrl));
                            }
                        }
                    }
                })
                .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Manejar el error de obtener datos de usuarios
                        if (listener != null) {
                            listener.onFailure(e);
                        }
                    }
                });
    }

    public interface OnImageUploadListener {
        void onSuccess(String imageUrl);
        void onFailure(Exception e);
    }

    public interface OnUsuariosLoadedListener {
        void onSuccess(String tipoEmpresa, String tipoUsuario, String firstName, String lastName, String dateOfBirth, String email, Uri imageUrl);
        void onFailure(Exception e);
    }
}
