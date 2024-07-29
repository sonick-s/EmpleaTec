package com.omar.sani.empleatec;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.controlador.database.perfil.dbProyectos;

public class SubirProyectosPerfil extends Fragment {

    private static final int IMAGE_PICK_CODE = 1000;

    private TextView projectsTitle;
    private EditText projectNameInput;
    private EditText projectDescriptionInput;
    private EditText projectUrlInput;
    private Spinner projectCategorySpinner;
    private ImageView projectImageView;
    private Button projectImageButton;
    private Button projectSaveButton;

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

    public SubirProyectosPerfil() {
        // Constructor vacío requerido
    }

    public static SubirProyectosPerfil newInstance(String param1, String param2) {
        SubirProyectosPerfil fragment = new SubirProyectosPerfil();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subir_proyectos_perfil, container, false);

        projectsTitle = view.findViewById(R.id.projects_title);
        projectNameInput = view.findViewById(R.id.project_name_input);
        projectDescriptionInput = view.findViewById(R.id.project_description_input);
        projectUrlInput = view.findViewById(R.id.project_url_input);
        projectCategorySpinner = view.findViewById(R.id.project_category_spinner);
        projectImageView = view.findViewById(R.id.project_image_view);
        projectImageButton = view.findViewById(R.id.project_image_button);
        projectSaveButton = view.findViewById(R.id.project_save_button);

        configureCategorySpinner();

        projectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        projectSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    String nombre = projectNameInput.getText().toString().trim();
                    String descripcion = projectDescriptionInput.getText().toString().trim();
                    String url = projectUrlInput.getText().toString().trim(); // URL opcional
                    String categoria = projectCategorySpinner.getSelectedItem().toString();

                    dbProyectos dbProyectos = new dbProyectos();
                    dbProyectos.enviarDatosProyectos(nombre, descripcion, url, categoria, view);
                }
            }
        });

        return view;
    }

    private void configureCategorySpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, jobSubcategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectCategorySpinner.setAdapter(adapter);
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == getActivity().RESULT_OK) {
            Uri imageUri = data.getData();
            projectImageView.setImageURI(imageUri); // Mostrar la imagen seleccionada
            // Aquí puedes manejar la carga de la imagen si lo deseas
        }
    }

    private boolean validateInputs() {
        if (projectNameInput.getText().toString().trim().isEmpty()) {
            showToast("El nombre del proyecto no puede estar vacío.");
            return false;
        }
        if (projectDescriptionInput.getText().toString().trim().isEmpty()) {
            showToast("La descripción del proyecto no puede estar vacía.");
            return false;
        }
        // La validación de la URL es opcional

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
