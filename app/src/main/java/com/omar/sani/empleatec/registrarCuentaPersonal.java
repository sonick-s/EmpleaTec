package com.omar.sani.empleatec;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.omar.sani.empleatec.controlador.database.login.dbUsuario;

public class registrarCuentaPersonal extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etDayOfBirth;
    private EditText etMonthOfBirth;
    private EditText etYearOfBirth;
    private EditText etEmail;
    private Button btnUploadPhoto;
    private ImageView ivUploadedPhoto;
    private Button btnNext;
    private Uri imageUri;

    private dbUsuario usuarioDatabase;

    private final ActivityResultLauncher<String> pickImage = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if (uri != null) {
            imageUri = uri;
            ivUploadedPhoto.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cuenta_personal);

        // Inicialización de las variables con los elementos del layout XML
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etDayOfBirth = findViewById(R.id.etDayOfBirth);
        etMonthOfBirth = findViewById(R.id.etMonthOfBirth);
        etYearOfBirth = findViewById(R.id.etYearOfBirth);
        etEmail = findViewById(R.id.etEmail);
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto);
        ivUploadedPhoto = findViewById(R.id.ivUploadedPhoto);
        btnNext = findViewById(R.id.btnNext);

        // Inicialización de la base de datos de usuario
        usuarioDatabase = new dbUsuario();

        btnUploadPhoto.setOnClickListener(view -> openGallery());

        btnNext.setOnClickListener(view -> {
            // Obtener los datos de los campos obligatorios
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String dayOfBirth = etDayOfBirth.getText().toString().trim();
            String monthOfBirth = etMonthOfBirth.getText().toString().trim();
            String yearOfBirth = etYearOfBirth.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            // Validar que todos los campos obligatorios estén llenos
            if (firstName.isEmpty() || lastName.isEmpty() || dayOfBirth.isEmpty() || monthOfBirth.isEmpty()
                    || yearOfBirth.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear la fecha de nacimiento combinando día, mes y año
            String dateOfBirth = dayOfBirth + "/" + monthOfBirth + "/" + yearOfBirth;

            // Enviar los datos a la base de datos
            usuarioDatabase.enviarDatosALaBaseDeDatos(firstName, lastName, dateOfBirth, email, imageUri, view);

            // Redirigir a la siguiente actividad y enviar datos a userPassword
            Intent intent = new Intent(registrarCuentaPersonal.this, registrarCelular.class);
            intent.putExtra("firstName", firstName);
            intent.putExtra("lastName", lastName);
            startActivity(intent);
        });
    }

    private void openGallery() {
        pickImage.launch("image/*");
    }
}
