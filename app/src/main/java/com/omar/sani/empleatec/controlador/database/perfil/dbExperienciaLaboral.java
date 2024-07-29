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
import com.omar.sani.empleatec.ui.gallery.ValidarExperienciaLaboral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dbExperienciaLaboral {

    private FirebaseFirestore db;
    private ValidarExperienciaLaboral validarExperienciaLaboral;

    public dbExperienciaLaboral() {
        db = FirebaseFirestore.getInstance();
        validarExperienciaLaboral = new ValidarExperienciaLaboral();
    }

    // Método para enviar datos a Firebase
    public void enviarDatosExperiencia(String company, String role, String duration, String description, View view) {
        Map<String, Object> experienceData = new HashMap<>();
        experienceData.put("TipoEmpresa", ConfigGmail.IdGmailEmpresa);
        experienceData.put("TipoUsuario", ConfigGmail.IdGmailUsuario);
        experienceData.put("company", company);
        experienceData.put("role", role);
        experienceData.put("duration", duration);
        experienceData.put("description", description);

        db.collection("experiencia_laboral")
                .add(experienceData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(view.getContext(), "Experiencia laboral guardada", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al guardar la experiencia laboral", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para obtener datos de Firebase
    public void obtenerDatosExperiencia(View view) {
        db.collection("experiencia_laboral")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<ValidarExperienciaLaboral.Experiencia> experienciaList = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            ValidarExperienciaLaboral.Experiencia experiencia = new ValidarExperienciaLaboral.Experiencia(
                                    document.getString("TipoEmpresa"),
                                    document.getString("TipoUsuario"),
                                    document.getString("company"),
                                    document.getString("role"),
                                    document.getString("duration"),
                                    document.getString("description")
                            );
                            experienciaList.add(experiencia);
                        }

                        // Guardar los datos en la instancia de ValidarExperienciaLaboral
                        if (validarExperienciaLaboral != null) {
                            validarExperienciaLaboral.setExperienciaList(experienciaList);
                            validarExperienciaLaboral.notifyListener();
                        }

                        Toast.makeText(view.getContext(), "Datos de experiencia laboral obtenidos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al obtener la experiencia laboral", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public ValidarExperienciaLaboral getValidarExperienciaLaboral() {
        return validarExperienciaLaboral;
    }
}
