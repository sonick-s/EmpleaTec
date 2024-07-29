package com.omar.sani.empleatec.controlador.database.perfil;

import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.omar.sani.empleatec.controlador.ConfigGmail;
import com.omar.sani.empleatec.ui.gallery.ValidarEducacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dbEducacion {

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private ValidarEducacion validarEducacion;

    public dbEducacion() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        validarEducacion = new ValidarEducacion();
    }

    // Método para enviar datos a Firebase
    public void enviarDatosEducacion(String institution, String degree, String years, String category, Uri pdfUri, View view) {
        if (pdfUri != null) {
            // Subir el archivo PDF a Firebase Storage
            StorageReference storageRef = storage.getReference();
            StorageReference pdfRef = storageRef.child("pdfs/" + pdfUri.getLastPathSegment());

            UploadTask uploadTask = pdfRef.putFile(pdfUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Obtener la URL de descarga del PDF
                    pdfRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Guardar los datos en Firestore
                            Map<String, Object> educationData = new HashMap<>();
                            educationData.put("TipoEmpresa", ConfigGmail.IdGmailEmpresa);
                            educationData.put("TipoUsuario", ConfigGmail.IdGmailUsuario);
                            educationData.put("institution", institution);
                            educationData.put("degree", degree);
                            educationData.put("years", years);
                            educationData.put("category", category);
                            educationData.put("pdfUrl", uri.toString());

                            db.collection("educacion")
                                    .add(educationData)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(view.getContext(), "Datos de educación guardados", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(view.getContext(), "Error al guardar los datos de educación", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Error al obtener la URL del PDF", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(view.getContext(), "Error al subir el PDF", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Si no hay PDF, simplemente guardar los datos sin el campo pdfUrl
            Map<String, Object> educationData = new HashMap<>();
            educationData.put("TipoEmpresa", ConfigGmail.IdGmailEmpresa);
            educationData.put("TipoUsuario", ConfigGmail.IdGmailUsuario);
            educationData.put("institution", institution);
            educationData.put("degree", degree);
            educationData.put("years", years);
            educationData.put("category", category);

            db.collection("educacion")
                    .add(educationData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(view.getContext(), "Datos de educación guardados", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Error al guardar los datos de educación", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Método para obtener datos de Firebase
    public void obtenerDatosEducacion(View view) {
        db.collection("educacion")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<ValidarEducacion.Educacion> educacionList = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            ValidarEducacion.Educacion educacion = new ValidarEducacion.Educacion(
                                    document.getString("TipoEmpresa"),
                                    document.getString("TipoUsuario"),
                                    document.getString("institution"),
                                    document.getString("degree"),
                                    document.getString("years"),
                                    document.getString("category")
                            );
                            educacionList.add(educacion);
                        }

                        // Guardar los datos en la instancia de ValidarEducacion
                        if (validarEducacion != null) {
                            validarEducacion.setEducacionList(educacionList);
                            validarEducacion.notifyListener();
                        }

                        Toast.makeText(view.getContext(), "Datos de educación obtenidos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Error al obtener los datos de educación", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public ValidarEducacion getValidarEducacion(){
        return validarEducacion;
    }
}
