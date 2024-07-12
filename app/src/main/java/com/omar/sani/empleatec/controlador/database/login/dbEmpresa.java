package com.omar.sani.empleatec.controlador.database.login;

import android.net.Uri;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class dbEmpresa {
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    public dbEmpresa() {
        this.db = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    public void enviarDatosALaBaseDeDatos(String companyName, String phone, String email, String website, String missionVision, String imageUrl, View view) {
        // Crear un mapa de datos
        Map<String, Object> empresa = new HashMap<>();
        empresa.put("Nombre de la Empresa", companyName);
        empresa.put("Telefono Convencional", phone);
        empresa.put("Correo Electronico", email);
        if (!website.isEmpty()) {
            empresa.put("Url del sitio web", website);
        }
        if (!imageUrl.isEmpty()) {
            empresa.put("Imagen de la empresa", imageUrl);
        }
        empresa.put("Descripcion de la empresa", missionVision);

        // Enviar los datos a Firestore
        db.collection("empresas")
                .add(empresa)
                .addOnSuccessListener(documentReference -> Snackbar.make(view, "Datos enviados correctamente a Firestore", Snackbar.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Snackbar.make(view, "Error al enviar datos a Firestore", Snackbar.LENGTH_SHORT).show());
    }

    public void subirImagenAFirebase(Uri imageUri, final OnImageUploadListener listener) {
        if (imageUri != null) {
            StorageReference storageRef = storage.getReference().child("images/" + System.currentTimeMillis() + ".jpg");
            storageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> listener.onSuccess(uri.toString())))
                    .addOnFailureListener(listener::onFailure);
        } else {
            listener.onFailure(new Exception("La URI de la imagen es nula"));
        }
    }

    public interface OnImageUploadListener {
        void onSuccess(String imageUrl);
        void onFailure(Exception e);
    }
}
