package com.omar.sani.empleatec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omar.sani.empleatec.controlador.database.perfil.dbContacto;
import com.omar.sani.empleatec.ui.gallery.ValidarContacto;
import com.omar.sani.empleatec.ui.gallery.ValidarContacto.Contacto;

import java.util.List;

public class mostrarContactoPerfil extends Fragment implements ValidarContacto.ContactoListener {


    /* Este Frame va en Home Galery xml, pero fue sacado porque el encabezado ya muestra el contacto
    <FrameLayout
                android:id="@+id/fragment_container_contacto"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"/>
     */

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String param1;
    private String param2;

    private TextView emailContacto;
    private TextView telefonoContacto;

    public static mostrarContactoPerfil newInstance(String param1, String param2) {
        mostrarContactoPerfil fragment = new mostrarContactoPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_contacto_perfil, container, false);

        // Inicializa tus vistas aquí
        emailContacto = view.findViewById(R.id.user_email);
        telefonoContacto = view.findViewById(R.id.user_phone);

        // Obtener argumentos
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
        }

        // Crear instancia de dbContacto y obtener la instancia de ValidarContacto
        dbContacto dbContact = new dbContacto();
        ValidarContacto validarContacto = dbContact.getValidarContacto();

        // Establecer este fragmento como el listener para recibir datos de contacto
        validarContacto.setContactoListener(this);
        dbContact.obtenerDatosContacto(view);

        return view;
    }

    @Override
    public void onContactoDataReceived(List<Contacto> contactoList) {
        if (contactoList != null && !contactoList.isEmpty()) {
            Contacto contacto = contactoList.get(0); // Obtiene el primer contacto para mostrar
            emailContacto.setText("Correo Electrónico: " + contacto.getEmail());
            telefonoContacto.setText("Número de Teléfono: " + contacto.getPhone());
        }
    }
}
