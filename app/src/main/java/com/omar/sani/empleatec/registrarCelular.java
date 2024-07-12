package com.omar.sani.empleatec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.omar.sani.empleatec.controlador.database.login.dbCelular;

public class registrarCelular extends AppCompatActivity {

    private Spinner etCountryCode;
    private EditText etPhoneNumber;
    private Button btnNext;
    private TextView tvTitleRegisterPhone;

    // Arrays de nombres y códigos de países
    private String[] countryNames = {
            "Selecciona un país", "Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Anguila",
            "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Aruba", "Australia",
            "Austria", "Azerbaiyán", "Bahamas", "Baréin", "Bangladés", "Barbados", "Bélgica", "Belice",
            "Benín", "Bermudas", "Bielorrusia", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil",
            "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde", "Camboya", "Camerún",
            "Canadá", "Chad", "Chile", "China", "Chipre", "Ciudad del Vaticano", "Colombia", "Comoras",
            "Congo", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba",
            "Curazao", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos",
            "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Etiopía", "Filipinas",
            "Finlandia", "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Gibraltar", "Granada",
            "Grecia", "Groenlandia", "Guadalupe", "Guam", "Guatemala", "Guayana Francesa", "Guernsey", "Guinea",
            "Guinea Ecuatorial", "Guinea-Bisáu", "Guyana", "Haití", "Honduras", "Hong Kong", "Hungría", "India",
            "Indonesia", "Irak", "Irán", "Irlanda", "Isla de Man", "Islandia", "Islas Caimán", "Islas Cook",
            "Islas Feroe", "Islas Malvinas", "Islas Marianas del Norte", "Islas Marshall", "Islas Salomón",
            "Islas Turcas y Caicos", "Islas Vírgenes Británicas", "Islas Vírgenes de los Estados Unidos", "Israel",
            "Italia", "Jamaica", "Japón", "Jersey", "Jordania", "Kazajistán", "Kenia", "Kirguistán", "Kiribati",
            "Kuwait", "Laos", "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania",
            "Luxemburgo", "Macao", "Macedonia del Norte", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí",
            "Malta", "Marruecos", "Martinica", "Mauricio", "Mauritania", "Mayotte", "México", "Micronesia",
            "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Montserrat", "Mozambique", "Myanmar", "Namibia",
            "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria", "Niue", "Noruega", "Nueva Caledonia", "Nueva Zelanda",
            "Omán", "Países Bajos", "Pakistán", "Palaos", "Palestina", "Panamá", "Papúa Nueva Guinea", "Paraguay",
            "Perú", "Polinesia Francesa", "Polonia", "Portugal", "Puerto Rico", "Qatar", "Reino Unido",
            "República Centroafricana", "República Checa", "República Democrática del Congo", "República Dominicana",
            "Reunión", "Ruanda", "Rumanía", "Rusia", "Sahara Occidental", "Samoa", "Samoa Americana", "San Bartolomé",
            "San Cristóbal y Nieves", "San Marino", "San Martín", "San Pedro y Miquelón", "San Vicente y las Granadinas",
            "Santa Elena", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona",
            "Singapur", "Sint Maarten", "Siria", "Somalia", "Sri Lanka", "Suazilandia", "Sudáfrica", "Sudán", "Sudán del Sur",
            "Suecia", "Suiza", "Surinam", "Tailandia", "Taiwán", "Tanzania", "Tayikistán", "Timor Oriental", "Togo",
            "Tokelau", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda",
            "Uruguay", "Uzbekistán", "Vanuatu", "Venezuela", "Vietnam", "Wallis y Futuna", "Yemen", "Yibuti", "Zambia", "Zimbabue"
    };

    private String[] countryCodes = {
            "", "+93", "+355", "+49", "+376", "+244", "+1-264", "+1-268", "+966", "+213", "+54", "+374", "+297",
            "+61", "+43", "+994", "+1-242", "+973", "+880", "+1-246", "+32", "+501", "+229", "+1-441", "+375",
            "+591", "+387", "+267", "+55", "+673", "+359", "+226", "+257", "+975", "+238", "+855", "+237", "+1",
            "+235", "+56", "+86", "+357", "+379", "+57", "+269", "+242", "+850", "+82", "+225", "+506", "+385",
            "+53", "+599", "+45", "+1-767", "+593", "+20", "+503", "+971", "+291", "+421", "+386", "+34", "+1",
            "+372", "+251", "+63", "+358", "+679", "+33", "+241", "+220", "+995", "+233", "+350", "+1-473",
            "+30", "+299", "+590", "+1-671", "+502", "+594", "+44-1481", "+224", "+240", "+245", "+592", "+509",
            "+504", "+852", "+36", "+91", "+62", "+964", "+98", "+353", "+44-1624", "+354", "+1-345", "+682",
            "+298", "+500", "+1-670", "+692", "+677", "+1-649", "+1-284", "+1-340", "+972", "+39", "+1-876",
            "+81", "+44-1534", "+962", "+7", "+254", "+996", "+686", "+965", "+856", "+266", "+371", "+961",
            "+231", "+218", "+423", "+370", "+352", "+853", "+389", "+261", "+60", "+265", "+960", "+223",
            "+356", "+212", "+596", "+230", "+222", "+262", "+52", "+691", "+373", "+377", "+976", "+382",
            "+1-664", "+258", "+95", "+264", "+674", "+977", "+505", "+227", "+234", "+683", "+47", "+687",
            "+64", "+968", "+31", "+92", "+680", "+970", "+507", "+675", "+595", "+51", "+689", "+48", "+351",
            "+1-787, +1-939", "+974", "+44", "+236", "+420", "+243", "+1-809, +1-829, +1-849", "+262", "+250",
            "+40", "+7", "+212", "+685", "+1-684", "+590", "+1-869", "+378", "+590", "+508", "+1-784", "+290",
            "+1-758", "+239", "+221", "+381", "+248", "+232", "+65", "+1-721", "+963", "+252", "+94", "+268",
            "+27", "+249", "+211", "+46", "+41", "+597", "+66", "+886", "+255", "+992", "+670", "+228",
            "+690", "+676", "+1-868", "+216", "+993", "+90", "+688", "+380", "+256", "+598", "+998", "+678",
            "+58", "+84", "+681", "+967", "+253", "+260", "+263"
    };


    // Variable para almacenar el número de teléfono completo
    private String fullPhoneNumber;

    // Instancia de la base de datos
    private dbCelular celularDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_celular);

        // Inicializar instancias de vistas
        etCountryCode = findViewById(R.id.etCountryCode);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnNext = findViewById(R.id.btnNext);
        tvTitleRegisterPhone = findViewById(R.id.tvTitleRegisterPhone);

        // Inicializar la instancia de la base de datos
        celularDatabase = new dbCelular();

        // Configurar el adaptador del Spinner con los nombres de los países
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etCountryCode.setAdapter(adapter);

        // Configurar el listener para seleccionar el código de país según el nombre
        etCountryCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountryCode = countryCodes[position];
                // Mostrar el código de país seleccionado (puedes usarlo como necesites)
                Toast.makeText(registrarCelular.this, "Código de país: " + selectedCountryCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada si no se selecciona nada
            }
        });

        // Configurar el listener para el botón Next
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el código de país seleccionado y el número de teléfono ingresado
                int selectedPosition = etCountryCode.getSelectedItemPosition();
                String countryCode = countryCodes[selectedPosition];
                String phoneNumber = etPhoneNumber.getText().toString();

                if (selectedPosition == 0 || phoneNumber.isEmpty()) {
                    Toast.makeText(registrarCelular.this, "Por favor, selecciona un país e ingresa tu número de teléfono", Toast.LENGTH_SHORT).show();
                } else {
                    // Concatenar el código de país y el número de teléfono
                    fullPhoneNumber = countryCode + phoneNumber;
                    // Mostrar el número de teléfono completo
                    Toast.makeText(registrarCelular.this, "Número de teléfono completo: " + fullPhoneNumber, Toast.LENGTH_SHORT).show();

                    // Guardar el número de teléfono en la base de datos
                    celularDatabase.enviarDatosCelular(countryCode, phoneNumber, v);

                    // Redirigir a la siguiente actividad
                    Intent intent = new Intent(registrarCelular.this, userPassword.class); // Cambia 'siguienteActividad' al nombre de la siguiente actividad
                    startActivity(intent);
                }
            }
        });
    }
}
