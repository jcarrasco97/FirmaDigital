package com.example.firmadigital;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FirmaView firmaView; // Vista personalizada donde se dibuja la firma
    private Button btnBorrar, btnGuardar, btnConfig; // Botones de la interfaz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firmaView = findViewById(R.id.firmaView);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnConfig = findViewById(R.id.btnConfig);

        // Botón para borrar la firma
        btnBorrar.setOnClickListener(view -> firmaView.borrarFirma());

        // Botón para guardar la firma en almacenamiento externo
        btnGuardar.setOnClickListener(view -> {
            // Comprobamos si el usuario ha concedido permisos de escritura
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                guardarFirma();
            } else {
                // Si no tiene permisos, se los pedimos al usuario
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });

        // Botón para abrir la configuración
        btnConfig.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarga el tamaño del trazo cuando volvemos de la configuración
        SharedPreferences preferencias = getSharedPreferences("config", MODE_PRIVATE);
        int tamanyoTrazo = preferencias.getInt("tamanyo_trazo", 8); // 8px por defecto
        firmaView.setTamanyoTrazo(tamanyoTrazo);
    }

    //Método para guardar la firma en una imagen JPG
    private void guardarFirma() {
        // Obtiene la firma como un Bitmap
        Bitmap bitmap = firmaView.getFirmaBitmap();

        // Genera un nombre único usando la fecha y hora actual
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        File file = new File(Environment.getExternalStorageDirectory(), "firma_" + timeStamp + ".jpg");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            // Guarda el Bitmap como un archivo JPEG con calidad 100%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Toast.makeText(this, "Firma guardada en: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar la firma", Toast.LENGTH_SHORT).show();
        }
    }
}
