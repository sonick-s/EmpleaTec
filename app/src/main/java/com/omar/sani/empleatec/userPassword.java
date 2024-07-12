package com.omar.sani.empleatec;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.omar.sani.empleatec.controlador.database.login.dbUserPassword;

import java.util.Random;

public class userPassword extends AppCompatActivity {

    // Declaración de variables
    private TextView tvTitle;
    private TextView tvUsernameDisplay;
    private EditText etPassword;
    private EditText etValidatePassword;
    private CheckBox cbShowPassword;
    private Button btnIngresar;

    // Variables para almacenar los datos de registrarCuentaPersonal
    private String firstName;
    private String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_password);

        // Inicialización de las variables con los elementos del layout
        tvTitle = findViewById(R.id.tvTitle);
        tvUsernameDisplay = findViewById(R.id.tvUsernameDisplay);
        etPassword = findViewById(R.id.etPassword);
        etValidatePassword = findViewById(R.id.etValidatePassword);
        cbShowPassword = findViewById(R.id.cbShowPassword);
        btnIngresar = findViewById(R.id.btnIngresar);

        // Obtener los datos enviados desde registrarCuentaPersonal
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            firstName = extras.getString("firstName", "");
            lastName = extras.getString("lastName", "");

            // Generar nombre de usuario de forma aleatoria usando permutación
            String username = generateUsername(firstName, lastName);

            // Mostrar el nombre de usuario en el TextView correspondiente
            tvUsernameDisplay.setText(username);
        }

        // Configurar el CheckBox para mostrar/ocultar la contraseña
        cbShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Mostrar la contraseña
                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                etValidatePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                // Ocultar la contraseña
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etValidatePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        // Configurar el botón para enviar los datos a Firestore
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tvUsernameDisplay.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etValidatePassword.getText().toString().trim();

                // Instanciar la clase dbUserPassword y enviar los datos a Firestore
                dbUserPassword db = new dbUserPassword();
                db.enviarCredencialesALaBaseDeDatos(username, password, confirmPassword, v);
                // Crear un Intent para abrir la actividad registrarCuentaPersonal
                Intent intent = new Intent(userPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Función para generar un nombre de usuario aleatorio
    private String generateUsername(String firstName, String lastName) {
        // Obtener los tres primeros caracteres del nombre
        String firstThreeChars = firstName.substring(0, Math.min(firstName.length(), 3)).toLowerCase();

        // Generar tres números aleatorios
        Random random = new Random();
        StringBuilder randomNumberString = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            randomNumberString.append(random.nextInt(10));
        }

        // Concatenar los tres números aleatorios al final de los tres primeros caracteres del nombre
        String username = firstThreeChars + randomNumberString.toString() + lastName.toLowerCase();

        return username;
    }
}