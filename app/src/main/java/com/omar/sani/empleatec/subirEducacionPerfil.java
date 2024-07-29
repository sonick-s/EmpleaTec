package com.omar.sani.empleatec;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.controlador.database.perfil.dbEducacion;

public class subirEducacionPerfil extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Variables para los elementos UI
    private TextView educationTitle;
    private EditText educationInstitution;
    private EditText educationDegree;
    private EditText educationYears;
    private TextView categoriaTitle;
    private Spinner projectCategorySpinner;
    private TextView pdfUploadTitle;
    private Button pdfUploadButton;
    private TextView pdfFileName;
    private Button educationSaveButton;

    private Uri pdfUri; // URI del PDF seleccionado

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

    public subirEducacionPerfil() {
        // Required empty public constructor
    }

    public static subirEducacionPerfil newInstance(String param1, String param2) {
        subirEducacionPerfil fragment = new subirEducacionPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subir_educacion_perfil, container, false);

        // Inicializar las variables
        educationTitle = view.findViewById(R.id.education_title);
        educationInstitution = view.findViewById(R.id.education_institution);
        educationDegree = view.findViewById(R.id.education_degree);
        educationYears = view.findViewById(R.id.education_years);
        categoriaTitle = view.findViewById(R.id.Categoria_title);
        projectCategorySpinner = view.findViewById(R.id.project_category_spinner);
        pdfUploadTitle = view.findViewById(R.id.pdf_upload_title);
        pdfUploadButton = view.findViewById(R.id.pdf_upload_button);
        pdfFileName = view.findViewById(R.id.pdf_file_name);
        educationSaveButton = view.findViewById(R.id.education_save_button);

        // Configurar el spinner con las subcategorías
        configureCategorySpinner();

        pdfUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdfDocument();
            }
        });

        educationSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    String institution = educationInstitution.getText().toString().trim();
                    String degree = educationDegree.getText().toString().trim();
                    String years = educationYears.getText().toString().trim();
                    String category = projectCategorySpinner.getSelectedItem().toString();

                    dbEducacion dbEducacion = new dbEducacion();
                    dbEducacion.enviarDatosEducacion(institution, degree, years, category, pdfUri, view);
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

    private void selectPdfDocument() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        pdfDocumentLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> pdfDocumentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    pdfUri = result.getData().getData();
                    pdfFileName.setText(pdfUri.getLastPathSegment());
                }
            }
    );

    private boolean validateFields() {
        if (educationInstitution.getText().toString().trim().isEmpty()) {
            educationInstitution.setError("Este campo es obligatorio");
            return false;
        }
        if (educationDegree.getText().toString().trim().isEmpty()) {
            educationDegree.setError("Este campo es obligatorio");
            return false;
        }
        if (educationYears.getText().toString().trim().isEmpty()) {
            educationYears.setError("Este campo es obligatorio");
            return false;
        }
        if (projectCategorySpinner.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Seleccione una categoría", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pdfUri == null) {
            Toast.makeText(getActivity(), "Por favor, suba un archivo PDF", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
