package com.omar.sani.empleatec.controlador.database.chats;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class dbRealTimeDB {
    private DatabaseReference databaseReference;

    public dbRealTimeDB() {
        // Inicializa la referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    // Método para agregar un nuevo chat
    public void addChat(String chatId, Chat chat) {
        databaseReference.child("chats").child(chatId).setValue(chat)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("dbRealTimeDB", "Chat agregado exitosamente.");
                        } else {
                            Log.e("dbRealTimeDB", "Error al agregar chat: " + task.getException().getMessage());
                        }
                    }
                });
    }

    // Método para leer datos de un chat específico
    public void readChat(String chatId) {
        databaseReference.child("chats").child(chatId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Chat chat = task.getResult().getValue(Chat.class);
                    if (chat != null) {
                        // Procesa el objeto 'chat' como desees
                        Log.d("dbRealTimeDB", "Chat leído exitosamente: " + chat.message);
                    } else {
                        Log.e("dbRealTimeDB", "No se encontró el chat.");
                    }
                } else {
                    Log.e("dbRealTimeDB", "Error al leer el chat: " + task.getException().getMessage());
                }
            }
        });
    }

    // Clase Chat (ejemplo básico)
    public static class Chat {
        public String sender;
        public String message;

        public Chat() {
            // Constructor vacío requerido para Firebase
        }

        public Chat(String sender, String message) {
            this.sender = sender;
            this.message = message;
        }
    }
}
