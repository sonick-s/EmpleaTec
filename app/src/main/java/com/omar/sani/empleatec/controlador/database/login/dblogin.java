package com.omar.sani.empleatec.controlador.database.login;

import com.google.firebase.firestore.FirebaseFirestore;

public class dblogin {

    private FirebaseFirestore db;

    public dblogin() {
        db = FirebaseFirestore.getInstance();
    }

}
