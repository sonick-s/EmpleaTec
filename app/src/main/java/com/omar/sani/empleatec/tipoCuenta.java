package com.omar.sani.empleatec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class tipoCuenta extends AppCompatActivity {

    private TextView tvBusinessAccount, tvPersonalAccount;
    private Button btnBusinessAccount, btnPersonalAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_cuenta);

        // Acceder a las vistas del layout mediante findViewById
        tvBusinessAccount = findViewById(R.id.tvBusinessAccount);
        tvPersonalAccount = findViewById(R.id.tvPersonalAccount);
        btnBusinessAccount = findViewById(R.id.btnBusinessAccount);
        btnPersonalAccount = findViewById(R.id.btnPersonalAccount);

        // Configurar acción al hacer clic en el botón Crear Cuenta Empresarial
        btnBusinessAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir la actividad registrarEmpresa
                Intent intent = new Intent(tipoCuenta.this, registrarEmpresa.class);
                startActivity(intent);
            }
        });

        // Configurar acción al hacer clic en el botón Crear Cuenta Personal
        btnPersonalAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir la actividad registrarCuentaPersonal
                Intent intent = new Intent(tipoCuenta.this, registrarCuentaPersonal.class);
                startActivity(intent);
            }
        });
    }
}
