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
import com.omar.sani.empleatec.ui.gallery.ValidarProyectos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dbProyectos {

    private FirebaseFirestore db;
    private ValidarProyectos validarProyectos;

    // Constructor sin parámetros
    public dbProyectos() {
        this.db = FirebaseFirestore.getInstance();
        this.validarProyectos = new ValidarProyectos(); // O inicializar con un valor predeterminado si es necesario
    }

    // Constructor que acepta una instancia de ValidarProyectos
    public dbProyectos(ValidarProyectos validarProyectos) {
        this.db = FirebaseFirestore.getInstance();
        this.validarProyectos = validarProyectos;
    }

    // Método para enviar datos a Firebase
    public void enviarDatosProyectos(String nombre, String descripcion, String url, String categoria, View view) {
        Map<String, Object> projectData = new HashMap<>();
        projectData.put("TipoEmpresa", ConfigGmail.IdGmailEmpresa);
        projectData.put("TipoUsuario", ConfigGmail.IdGmailUsuario);
        projectData.put("nombre", nombre);
        projectData.put("descripcion", descripcion);
        projectData.put("url", url);
        projectData.put("categoria", categoria);

        db.collection("proyectos")
                .add(projectData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(view.getContext(), "Proyecto guardado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al guardar el proyecto", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para obtener datos de Firebase
    public void obtenerDatosProyectos(View view) {
        db.collection("proyectos")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<ValidarProyectos.Proyecto> proyectosList = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            ValidarProyectos.Proyecto proyecto = new ValidarProyectos.Proyecto(
                                    document.getString("TipoEmpresa"),
                                    document.getString("TipoUsuario"),
                                    document.getString("nombre"),
                                    document.getString("descripcion"),
                                    document.getString("url"),
                                    document.getString("categoria")
                            );
                            proyectosList.add(proyecto);
                        }

                        // Guardar los datos en la instancia de ValidarProyectos
                        if (validarProyectos != null) {
                            validarProyectos.setProyectoList(proyectosList);
                            validarProyectos.notifyListener();
                        }

                        Toast.makeText(view.getContext(), "Datos de proyectos obtenidos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al obtener los proyectos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public ValidarProyectos getValidarProyectos() {
        return validarProyectos;
    }
}
