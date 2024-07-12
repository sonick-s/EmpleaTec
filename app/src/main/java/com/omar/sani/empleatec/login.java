package com.omar.sani.empleatec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.omar.sani.empleatec.databinding.ActivityLoginBinding;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;

    private ActivityLoginBinding binding;

    private EditText editTextCorreo, editTextContrasenia;
    private Button btnIngresar, btnGoogle, btnRegistrar;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Acceder a las vistas del layout
        editTextCorreo = findViewById(R.id.Correo);
        editTextContrasenia = findViewById(R.id.Contrasenia);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnGoogle = findViewById(R.id.btnGoogle);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Configurar Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Configurar acción al hacer clic en el botón Ingresar
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí va la lógica para autenticar con correo y contraseña
            }
        });

        // Configurar acción al hacer clic en el botón Google
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // Configurar acción al hacer clic en el botón Registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí se crea un Intent para abrir la actividad tipoCuenta
                Intent intent = new Intent(login.this, tipoCuenta.class);
                startActivity(intent);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Resultado de iniciar sesión con Google
        if (requestCode == RC_SIGN_IN) {
            try {
                GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Snackbar.make(binding.getRoot(), "Error al iniciar sesión con Google", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Envía los datos a Firestore después de la autenticación exitosa
                            enviarDatosAFirestore(user);
                        }
                        updateUI(user);
                    } else {
                        Snackbar.make(binding.getRoot(), "Error de autenticación", Snackbar.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void enviarDatosAFirestore(FirebaseUser user) {
        String username = user.getEmail(); // Obtener el correo del usuario

        // Crear un mapa de datos con la información del usuario
        Map<String, Object> credenciales = new HashMap<>();
        credenciales.put("Usuario", username);

        // Enviar los datos a Firestore
        db.collection("UserCredentials")
                .add(credenciales)
                .addOnSuccessListener(documentReference -> Snackbar.make(binding.getRoot(), "Credenciales enviadas correctamente a Firestore", Snackbar.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Snackbar.make(binding.getRoot(), "Error al enviar credenciales a Firestore", Snackbar.LENGTH_SHORT).show());
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Navegar a la actividad principal o actualizar la UI
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Snackbar.make(binding.getRoot(), "Inicio de sesión fallido", Snackbar.LENGTH_SHORT).show();
        }
    }
}
