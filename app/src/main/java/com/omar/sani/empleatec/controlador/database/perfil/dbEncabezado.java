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
import com.omar.sani.empleatec.ui.gallery.ValidarEncabezado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dbEncabezado {

    private FirebaseFirestore db;
    private ValidarEncabezado validarEncabezado;

    public dbEncabezado() {
        db = FirebaseFirestore.getInstance();
        validarEncabezado = new ValidarEncabezado();
    }

    // Método para enviar datos a Firebase
    public void enviarDatosEncabezado(String userName, String userBio, View view) {
        Map<String, Object> headerData = new HashMap<>();
        headerData.put("TipoEmpresa", ConfigGmail.IdGmailEmpresa);
        headerData.put("TipoUsuario", ConfigGmail.IdGmailUsuario);
        headerData.put("userName", userName);
        headerData.put("userBio", userBio);

        db.collection("encabezados")
                .add(headerData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(view.getContext(), "Datos del encabezado guardados", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al guardar los datos del encabezado", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para obtener datos de Firebase
    public void obtenerDatosEncabezado(View view) {
        db.collection("encabezados")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<ValidarEncabezado.Encabezado> encabezadoList = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            ValidarEncabezado.Encabezado encabezado = new ValidarEncabezado.Encabezado(
                                    document.getString("TipoEmpresa"),
                                    document.getString("TipoUsuario"),
                                    document.getString("userName"),
                                    document.getString("userBio"),
                                    document.getId()
                            );
                            encabezadoList.add(encabezado);
                        }

                        // Guardar los datos en la instancia de ValidarEncabezado
                        if (validarEncabezado != null) {
                            validarEncabezado.setEncabezadoList(encabezadoList);
                            validarEncabezado.notifyListener();
                        }

                        Toast.makeText(view.getContext(), "Datos del encabezado obtenidos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al obtener los datos del encabezado", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public ValidarEncabezado getValidarEncabezado() {
        return validarEncabezado;
    }
}
