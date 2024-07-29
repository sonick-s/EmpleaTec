package com.omar.sani.empleatec.controlador.database.login;

import static com.omar.sani.empleatec.controlador.ConfigGmail.IdGmailEmpresa;
import static com.omar.sani.empleatec.controlador.ConfigGmail.IdGmailUsuario;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class dbUserPassword {
    private FirebaseFirestore db;

    public dbUserPassword() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void enviarCredencialesALaBaseDeDatos(String username, String password, String confirmPassword, View view) {
        // Verificar que la contraseña y la confirmación sean iguales
        if (!password.equals(confirmPassword)) {
            Snackbar.make(view, "La contraseña y la confirmación no coinciden", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Crear un mapa de datos
        Map<String, Object> credenciales = new HashMap<>();
        credenciales.put("TipoEmpresa", IdGmailEmpresa);
        credenciales.put("TipoUsuario", IdGmailUsuario);
        credenciales.put("Usuario", username);
        credenciales.put("Contraseña", password);
        credenciales.put("ValidacionContraseña", confirmPassword);

        // Enviar los datos a Firestore
        db.collection("UserPassword")
                .add(credenciales)
                .addOnSuccessListener(documentReference -> Snackbar.make(view, "Credenciales enviadas correctamente a Firestore", Snackbar.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Snackbar.make(view, "Error al enviar credenciales a Firestore", Snackbar.LENGTH_SHORT).show());
    }
}
