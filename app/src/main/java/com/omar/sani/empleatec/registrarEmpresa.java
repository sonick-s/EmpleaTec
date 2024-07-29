package com.omar.sani.empleatec;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.omar.sani.empleatec.controlador.ConfigGmail;
import com.omar.sani.empleatec.controlador.database.login.dbEmpresa;

public class registrarEmpresa extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextView tvTitleRegisterCompany;
    private TextView tvSearchCompany;
    private Spinner spinnerCompanySearch;
    private EditText etCompanyName;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etWebsite;
    private Button btnUploadImage;
    private ImageView ivUploadedImage;
    private EditText etMissionVision;
    private Button btnNext;

    private Uri imageUri;

    private dbEmpresa dbEmpresaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_empresa);

        // Inicialización de las variables con los elementos del layout XML
        tvTitleRegisterCompany = findViewById(R.id.tvTitleRegisterCompany);
        tvSearchCompany = findViewById(R.id.tvSearchCompany);
        spinnerCompanySearch = findViewById(R.id.spinnerCompanySearch);
        etCompanyName = findViewById(R.id.etCompanyName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etWebsite = findViewById(R.id.etWebsite);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        ivUploadedImage = findViewById(R.id.ivUploadedImage);
        etMissionVision = findViewById(R.id.etMissionVision);
        btnNext = findViewById(R.id.btnNext);

        dbEmpresaHelper = new dbEmpresa();

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()) {
                    String companyName = etCompanyName.getText().toString();
                    String phone = etPhone.getText().toString();
                    String email = etEmail.getText().toString();
                    String website = etWebsite.getText().toString();
                    String missionVision = etMissionVision.getText().toString();


                    ConfigGmail.IdGmailEmpresa = email;

                    if (imageUri != null) {
                        dbEmpresaHelper.subirImagenAFirebase(imageUri, new dbEmpresa.OnImageUploadListener() {
                            @Override
                            public void onSuccess(String imageUrl) {
                                dbEmpresaHelper.enviarDatosALaBaseDeDatos(companyName, phone, email, website, missionVision, imageUrl, view);
                                Intent intent = new Intent(registrarEmpresa.this, registrarCuentaPersonal.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Snackbar.make(view, "Error al subir la imagen: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        dbEmpresaHelper.enviarDatosALaBaseDeDatos(companyName, phone, email, website, missionVision, "", view);
                        Intent intent = new Intent(registrarEmpresa.this, registrarCuentaPersonal.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private boolean validarCampos() {
        boolean esValido = true;

        // Validar nombre de la empresa
        if (etCompanyName.getText().toString().trim().isEmpty()) {
            etCompanyName.setError("El nombre de la empresa es obligatorio");
            esValido = false;
        } else if (etCompanyName.getText().toString().trim().length() < 3) {
            etCompanyName.setError("El nombre de la empresa debe tener al menos 3 caracteres");
            esValido = false;
        }

        // Validar teléfono
        if (etPhone.getText().toString().trim().isEmpty()) {
            etPhone.setError("El número de teléfono es obligatorio");
            esValido = false;
        } else if (!Patterns.PHONE.matcher(etPhone.getText().toString().trim()).matches()) {
            etPhone.setError("Ingrese un número de teléfono válido");
            esValido = false;
        }

        // Validar email
        if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("El correo electrónico es obligatorio");
            esValido = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {
            etEmail.setError("Ingrese un correo electrónico válido");
            esValido = false;
        }

        // Validar sitio web (opcional)
        if (!etWebsite.getText().toString().trim().isEmpty() && !Patterns.WEB_URL.matcher(etWebsite.getText().toString().trim()).matches()) {
            etWebsite.setError("Ingrese una URL válida");
            esValido = false;
        }

        // Validar misión y visión
        if (etMissionVision.getText().toString().trim().isEmpty()) {
            etMissionVision.setError("La misión y visión son obligatorias");
            esValido = false;
        } else if (etMissionVision.getText().toString().trim().length() < 20) {
            etMissionVision.setError("La misión y visión deben tener al menos 20 caracteres");
            esValido = false;
        }

        return esValido;
    }

    private void abrirGaleria() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivUploadedImage.setImageURI(imageUri);
        }
    }
}