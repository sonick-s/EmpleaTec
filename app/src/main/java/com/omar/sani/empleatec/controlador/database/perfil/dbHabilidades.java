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
import com.omar.sani.empleatec.ui.gallery.ValidarHabilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dbHabilidades {

    private FirebaseFirestore db;
    private ValidarHabilidades validarHabilidades;

    // Constructor sin parámetros
    public dbHabilidades() {
        this.db = FirebaseFirestore.getInstance();
        this.validarHabilidades = new ValidarHabilidades(); // Inicializar por defecto si es necesario
    }

    // Constructor que acepta una instancia de ValidarHabilidades
    public dbHabilidades(ValidarHabilidades validarHabilidades) {
        this.db = FirebaseFirestore.getInstance();
        this.validarHabilidades = validarHabilidades;
    }

    // Método para enviar datos a Firebase
    public void enviarDatosHabilidades(String habilidad, View view) {
        Map<String, Object> skillsData = new HashMap<>();
        skillsData.put("TipoEmpresa", ConfigGmail.IdGmailEmpresa);
        skillsData.put("TipoUsuario", ConfigGmail.IdGmailUsuario);
        skillsData.put("habilidad", habilidad);

        db.collection("habilidades")
                .add(skillsData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(view.getContext(), "Habilidad guardada", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al guardar la habilidad", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para obtener datos de Firebase
    public void obtenerDatosHabilidades(View view) {
        db.collection("habilidades")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<ValidarHabilidades.Habilidad> habilidadesList = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            ValidarHabilidades.Habilidad habilidad = new ValidarHabilidades.Habilidad(
                                    document.getString("TipoEmpresa"),
                                    document.getString("TipoUsuario"),
                                    document.getString("habilidad")
                            );
                            habilidadesList.add(habilidad);
                        }

                        // Guardar los datos en la instancia de ValidarHabilidades
                        if (validarHabilidades != null) {
                            validarHabilidades.setHabilidadList(habilidadesList);
                            validarHabilidades.notifyListener();
                        }

                        Toast.makeText(view.getContext(), "Datos de habilidades obtenidos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al obtener las habilidades", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public ValidarHabilidades getValidarHabilidades() {
        return validarHabilidades;
    }
}
