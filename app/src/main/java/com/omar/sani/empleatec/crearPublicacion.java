package com.omar.sani.empleatec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.controlador.database.home.dbCrearPublicacion;

import java.io.IOException;

public class crearPublicacion extends Fragment {

    private EditText editTextDescription;
    private Spinner spinnerCategory;
    private ImageView imageViewSelected;
    private Button buttonSelectImage;
    private Button buttonUploadPost;

    private Uri imageUri;
    private static final int SELECT_IMAGE_REQUEST = 1;

    // Lista de subcategorías
    String[] jobSubcategories = {
            "Administración y Servicios al Cliente",
            "Tecnología y Desarrollo",
            "Finanzas y Contabilidad",
            "Recursos Humanos",
            "Marketing y Ventas",
            "Ingeniería y Producción",
            "Salud y Medicina",
            "Legal y Asesoría",
            "Diseño y Medios",
            "Hostelería y Turismo",
            "Seguridad y Logística",
            "Educación y Formación",
            "Agricultura y Medio Ambiente",
            "Automoción y Transporte",
            "Construcción e Infraestructura",
            "Energía y Recursos Naturales",
            "Fabricación y Mantenimiento",
            "Investigación y Desarrollo",
            "Telecomunicaciones",
            "Arte y Entretenimiento",
            "Inmobiliaria",
            "Seguros y Gestión de Riesgos",
            "Ciencia y Tecnología",
            "Comercio y Distribución",
            "Alimentos y Bebidas",
            "Consultoría y Estrategia",
            "Deporte y Fitness",
            "Servicios Personales",
            "Trabajo Social y Comunitario",
            "Traducción e Interpretación"
    };

    public crearPublicacion() {
        // Constructor vacío, si no se requiere inicialización adicional
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_crear_publicacion, container, false);

        // Inicializar vistas
        editTextDescription = view.findViewById(R.id.editTextDescription);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        imageViewSelected = view.findViewById(R.id.imageViewSelected);
        buttonSelectImage = view.findViewById(R.id.buttonSelectImage);
        buttonUploadPost = view.findViewById(R.id.buttonUploadPost);

        // Configurar el spinner con las subcategorías
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, jobSubcategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Configurar listener para seleccionar una imagen
        buttonSelectImage.setOnClickListener(v -> selectImage());

        // Configurar listener para subir la publicación
        buttonUploadPost.setOnClickListener(v -> uploadPost());

        return view;
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                imageViewSelected.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadPost() {
        String description = editTextDescription.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        if (description.isEmpty() || imageUri == null) {
            Toast.makeText(requireActivity(), "Por favor selecciona una imagen y escribe una descripción", Toast.LENGTH_SHORT).show();
            return;
        }

        // Subir la publicación usando dbCrearPublicacion
        dbCrearPublicacion db = new dbCrearPublicacion();
        db.subirPublicacion(description, category, imageUri, new dbCrearPublicacion.OnPublicacionUploadListener() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                // Limpiar campos después de subir la publicación
                clearFields();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(requireActivity(), "Error al subir la publicación: " + errorMessage, Toast.LENGTH_SHORT).show();
                // Manejo de errores, si es necesario
            }
        });
    }

    private void clearFields() {
        editTextDescription.setText("");
        spinnerCategory.setSelection(0); // Resetear selección del spinner
        imageViewSelected.setImageResource(0);
        imageUri = null; // Resetear URI de la imagen

        // Actualizar la vista cargando nuevamente el fragmento
        reloadFragment();
    }

    private void reloadFragment() {
        getParentFragmentManager().beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }
}
