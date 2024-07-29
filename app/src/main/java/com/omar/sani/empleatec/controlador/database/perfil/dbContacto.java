package com.omar.sani.empleatec.controlador.database.perfil;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.omar.sani.empleatec.controlador.ConfigGmail;
import com.omar.sani.empleatec.ui.gallery.ValidarContacto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dbContacto {

    private FirebaseFirestore db;
    private ValidarContacto validarContacto;

    public dbContacto() {
        db = FirebaseFirestore.getInstance();
        validarContacto = new ValidarContacto();
    }

    // Método para enviar datos a Firebase
    public void enviarDatosContacto(String email, String phone, View view) {
        Map<String, Object> contactData = new HashMap<>();
        contactData.put("TipoEmpresa", ConfigGmail.IdGmailEmpresa);
        contactData.put("TipoUsuario", ConfigGmail.IdGmailUsuario);
        contactData.put("email", email);
        contactData.put("phone", phone);

        db.collection("contactos")
                .add(contactData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(view.getContext(), "Contacto guardado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al guardar el contacto", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para obtener datos de Firebase
    public void obtenerDatosContacto(View view) {
        db.collection("contactos")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<ValidarContacto.Contacto> contactoList = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            ValidarContacto.Contacto contacto = new ValidarContacto.Contacto(
                                    document.getString("TipoEmpresa"),
                                    document.getString("TipoUsuario"),
                                    document.getString("email"),
                                    document.getString("phone")
                            );
                            contactoList.add(contacto);
                        }

                        // Guardar los datos en la instancia de ValidarContacto
                        if (validarContacto != null) {
                            validarContacto.setContactoList(contactoList);
                            validarContacto.notifyListener();
                        }

                        Toast.makeText(view.getContext(), "Datos de contacto obtenidos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al obtener los contactos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public ValidarContacto getValidarContacto() {
        return validarContacto;
    }
}
