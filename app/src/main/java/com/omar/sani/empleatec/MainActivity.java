package com.omar.sani.empleatec;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.omar.sani.empleatec.databinding.ActivityMainBinding;
import com.omar.sani.empleatec.ui.home.HomeFragment;
import com.omar.sani.empleatec.ui.home.verUser.mostrarUsuarios;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        // Inicializar Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Verificar si el usuario está autenticado al iniciar MainActivity
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Si no hay usuario autenticado, redirigir a la pantalla de inicio de sesión
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish(); // Finalizar MainActivity para evitar que el usuario vuelva atrás
            return;
        }

        // Configurar el OnClickListener para el FAB
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mostrar el DialogFragment flotante
                chatgpt dialogFragment = new chatgpt();
                dialogFragment.show(getSupportFragmentManager(), "ChatGptDialogFragment");
            }
        });

        // Empleos button click listener
        binding.appBarMain.Empleos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear una instancia del fragmento HomeFragment
                HomeFragment fragment = new HomeFragment();
                // Iniciar una transacción de fragmentos
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // Reemplazar el fragmento actual en el contenedor con el nuevo fragmento
                transaction.replace(R.id.fragment_home_container, fragment);
                // Agregar la transacción a la pila de retroceso (opcional)
                transaction.addToBackStack(null);
                // Confirmar la transacción
                transaction.commit();
            }
        });

        // Usuarios button click listener
        binding.appBarMain.Usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear una instancia del fragmento mostrarUsuarios
                mostrarUsuarios fragment = new mostrarUsuarios();
                // Iniciar una transacción de fragmentos
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // Reemplazar el fragmento actual en el contenedor con el nuevo fragmento
                transaction.replace(R.id.fragment_home_container, fragment);
                // Agregar la transacción a la pila de retroceso (opcional)
                transaction.addToBackStack(null);
                // Confirmar la transacción
                transaction.commit();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configuración de la navegación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Configurar el encabezado del NavigationView
        configureNavigationHeader(currentUser, navigationView);
    }

    private void configureNavigationHeader(FirebaseUser currentUser, NavigationView navigationView) {
        View headerView = navigationView.getHeaderView(0);
        ImageView imageView = headerView.findViewById(R.id.imageView);
        TextView textViewTitle = headerView.findViewById(R.id.textView);
       // TextView textViewSubtitle = headerView.findViewById(R.id.textViewSubtitle); // Asegúrate de que este id es único en tu XML

        // Actualizar contenido del encabezado con datos del usuario
        if (currentUser != null) {
            String displayName = currentUser.getDisplayName(); // Nombre del usuario
            String photoUrl = currentUser.getPhotoUrl() != null ? currentUser.getPhotoUrl().toString() : null; // URL de la foto

            if (photoUrl != null) {
                Glide.with(this).load(photoUrl).into(imageView);
            }
            textViewTitle.setText(displayName != null ? displayName : "Usuario");
          //  textViewSubtitle.setText(currentUser.getEmail()); // Ejemplo de email
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
