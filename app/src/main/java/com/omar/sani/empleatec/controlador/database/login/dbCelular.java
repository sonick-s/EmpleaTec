package com.omar.sani.empleatec.controlador.database.login;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class dbCelular {

    private FirebaseFirestore db;

    public dbCelular() {
        db = FirebaseFirestore.getInstance();
    }

    public void enviarDatosCelular(String countryCode, String phoneNumber, View view) {
        Map<String, Object> phoneData = new HashMap<>();
        phoneData.put("countryCode", countryCode);
        phoneData.put("phoneNumber", phoneNumber);

        db.collection("celulares")
                .add(phoneData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(view.getContext(), "Datos del celular guardados", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al guardar los datos del celular", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public interface OnPhoneQueryListener {
        void onSuccess(String phoneNumber);
        void onFailure(Exception e);
    }

    public void obtenerNumeroTelefono(String userId, OnPhoneQueryListener listener) {
        db.collection("celulares")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                String phoneNumber = document.getString("phoneNumber");
                                listener.onSuccess(phoneNumber);
                                return;
                            }
                        } else {
                            listener.onFailure(new Exception("No se encontraron datos para el usuario: " + userId));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailure(e);
                    }
                });
    }
}
