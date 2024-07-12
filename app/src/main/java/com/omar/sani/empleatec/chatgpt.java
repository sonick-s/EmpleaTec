package com.omar.sani.empleatec;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.omar.sani.empleatec.controlador.Gpt3Api;

import java.util.ArrayList;
import java.util.Locale;

public class chatgpt extends DialogFragment implements TextToSpeech.OnInitListener {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    private EditText editTextMessage;
    private Button buttonSend;
    private Button buttonVoz;
    private CheckBox checkboxAudio;
    private TextView textViewMessage;
    private Gpt3Api gpt3Api;
    private TextToSpeech textToSpeech;
    private boolean isVoiceEnabled;

    public chatgpt() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chatgpt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        editTextMessage = view.findViewById(R.id.edit_text_message);
        buttonSend = view.findViewById(R.id.button_send);
        buttonVoz = view.findViewById(R.id.button_voz);
        checkboxAudio = view.findViewById(R.id.checkbox_audio);
        textViewMessage = view.findViewById(R.id.text_view_message);

        // Initialize Gpt3Api and TextToSpeech
        gpt3Api = new Gpt3Api(getContext());
        textToSpeech = new TextToSpeech(getContext(), this);
        isVoiceEnabled = checkboxAudio.isChecked();

        // Set click listeners
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        buttonVoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognition();
            }
        });

        checkboxAudio.setOnCheckedChangeListener((buttonView, isChecked) -> isVoiceEnabled = isChecked);
    }

    private void sendMessage() {
        String message = editTextMessage.getText().toString().trim();
        if (!message.isEmpty()) {
            callChatGpt(message); // Llamar a la función que envía el mensaje a la API
        } else {
            Toast.makeText(getContext(), "Por favor ingrese un mensaje", Toast.LENGTH_SHORT).show();
        }
    }

    private void callChatGpt(String prompt) {
        gpt3Api.generateText(prompt, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Mostrar la respuesta de la API en textViewMessage
                textViewMessage.setText(response);
                if (isVoiceEnabled) {
                    playVoice(response); // Si está habilitado el audio, reproducir la respuesta en voz
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textViewMessage.setText("Error: " + error.getMessage());
            }
        });
    }

    private void playVoice(String message) {
        if (isVoiceEnabled && !message.isEmpty()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            }
        } else {
            Toast.makeText(getContext(), "La opción de audio no está seleccionada o no hay mensaje", Toast.LENGTH_SHORT).show();
        }
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora...");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(getContext(), "El reconocimiento de voz no es compatible con su dispositivo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == android.app.Activity.RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String recognizedText = result.get(0);
                editTextMessage.setText(recognizedText);
                callChatGpt(recognizedText); // Enviar texto reconocido a la API
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getContext(), "Lenguaje no soportado para TextToSpeech", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Inicialización de TextToSpeech fallida", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
