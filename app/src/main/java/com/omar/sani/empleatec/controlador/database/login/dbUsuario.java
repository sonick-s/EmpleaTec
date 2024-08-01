package com.omar.sani.empleatec.controlador.database.login;

import static com.omar.sani.empleatec.controlador.ConfigGmail.IdGmailEmpresa;
import static com.omar.sani.empleatec.controlador.ConfigGmail.IdGmailUsuario;

import android.net.Uri;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dbUsuario {
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    public dbUsuario() {
        this.db = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    public void enviarDatosALaBaseDeDatos(String firstName, String lastName, String dateOfBirth, String email, Uri imageUri, View view) {
        if (imageUri == null) {
            Snackbar.make(view, "Por favor, carga una imagen de perfil", Snackbar.LENGTH_SHORT).show();
            return;
        }

        subirImagenAFirebase(imageUri, new OnImageUploadListener() {
            @Override
            public void onSuccess(String imageUrl) {
                Map<String, Object> usuario = new HashMap<>();
                usuario.put("TipoEmpresa", IdGmailEmpresa);
                usuario.put("TipoUsuario", IdGmailUsuario);
                usuario.put("Primer Nombre", firstName);
                usuario.put("Apellido", lastName);
                usuario.put("Fecha de Nacimiento", dateOfBirth);
                usuario.put("Correo Electronico", email);
                usuario.put("Imagen de Perfil", imageUrl);

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

    private void subirImagenAFirebase(Uri imageUri, OnImageUploadListener listener) {
        if (imageUri == null) {
            if (listener != null) {
                listener.onFailure(new Exception("La URI de la imagen es nula"));
            }
            return;
        }

        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("profile_images/" + imageUri.getLastPathSegment());

        UploadTask uploadTask = imageRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            String imageUrl = uri.toString();
            if (listener != null) {
                listener.onSuccess(imageUrl);
            }
        })).addOnFailureListener(e -> {
            if (listener != null) {
                listener.onFailure(e);
            }
        });
    }

    public void obtenerDatosDeUsuarios(final OnUsuariosLoadedListener listener) {
        db.collection("usuarios")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Usuario> usuarios = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String tipoUsuario = documentSnapshot.getString("TipoUsuario");
                        String firstName = documentSnapshot.getString("Primer Nombre");
                        String lastName = documentSnapshot.getString("Apellido");
                        String dateOfBirth = documentSnapshot.getString("Fecha de Nacimiento");
                        String email = documentSnapshot.getString("Correo Electronico");
                        String imageUrl = documentSnapshot.getString("Imagen de Perfil");

                        Usuario usuario = new Usuario(tipoUsuario, firstName, lastName, dateOfBirth, email, Uri.parse(imageUrl));
                        usuarios.add(usuario);
                    }

                    if (listener != null) {
                        listener.onSuccess(usuarios);
                    }
                })
                .addOnFailureListener(e -> {
                    if (listener != null) {
                        listener.onFailure(e);
                    }
                });
    }

    public interface OnImageUploadListener {
        void onSuccess(String imageUrl);
        void onFailure(Exception e);
    }

    public interface OnUsuariosLoadedListener {
        void onSuccess(List<Usuario> usuarios);
        void onFailure(Exception e);
    }

    public static class Usuario {
        private String tipoUsuario;
        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String email;
        private Uri imageUrl;

        public Usuario(String tipoUsuario, String firstName, String lastName, String dateOfBirth, String email, Uri imageUrl) {
            this.tipoUsuario = tipoUsuario;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
            this.email = email;
            this.imageUrl = imageUrl;
        }

        public String getTipoUsuario() { return tipoUsuario; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getDateOfBirth() { return dateOfBirth; }
        public String getEmail() { return email; }
        public Uri getImageUrl() { return imageUrl; }
    }
}
