package com.example.firmadigital;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ConfiguracionActivity extends AppCompatActivity {

    private SeekBar seekBarTamanyo; // Barra para seleccionar el tamaño del trazo
    private TextView textoTamanyo;  // Muestra el tamaño actual del trazo
    private Button btnGuardarConfig; // Botón para guardar la configuración
    private SharedPreferences preferencias; // Almacena las configuraciones del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        seekBarTamanyo = findViewById(R.id.seekBarTamanyo);
        textoTamanyo = findViewById(R.id.textoTamanyo);
        btnGuardarConfig = findViewById(R.id.btnGuardarConfig);

        preferencias = getSharedPreferences("config", MODE_PRIVATE);
        int tamanyoActual = preferencias.getInt("tamanyo_trazo", 8); // Valor por defecto 8px

        // Establece el tamaño actual en la barra y el texto
        seekBarTamanyo.setProgress(tamanyoActual);
        textoTamanyo.setText(tamanyoActual + " px");

        // Cambia el texto a medida que se mueve la barra
        seekBarTamanyo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textoTamanyo.setText(progress + " px"); // Muestra el nuevo tamaño del trazo
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Guarda el nuevo tamaño y volver
        btnGuardarConfig.setOnClickListener(view -> {
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putInt("tamanyo_trazo", seekBarTamanyo.getProgress());
            editor.apply();
            finish(); // Cierra la actividad y vuelve a la anterior
        });
    }
}
