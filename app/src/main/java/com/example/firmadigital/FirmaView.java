package com.example.firmadigital;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FirmaView extends View {

    private Paint paint;  // Objeto para definir el trazo
    private Path path;    // Ruta para almacenar la firma
    private Bitmap bitmap; // Bitmap donde se almacenará la firma
    private Canvas canvas; // Canvas para dibujar en el Bitmap

    public FirmaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        SharedPreferences preferencias = getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        int tamanyoTrazo = preferencias.getInt("tamaño_trazo", 8); // 8px por defecto

        paint = new Paint();
        paint.setAntiAlias(true);  // Suaviza los bordes del trazo
        paint.setColor(Color.BLACK); // Color del trazo negro
        paint.setStyle(Paint.Style.STROKE); // Modo de dibujo: solo contorno
        paint.setStrokeWidth(tamanyoTrazo); // Aplica el tamaño del trazo recuperado
        path = new Path(); // Inicializa el objeto que almacena la firma
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint); // Dibuja la firma en la vista
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y); // Inicia un nuevo trazo en la posición tocada
                invalidate(); // Redibuja la vista
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y); // Dibuja una línea desde el último punto hasta el actual
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setTamanyoTrazo(int tamanyo) {
        paint.setStrokeWidth(tamanyo); // Cambia el grosor del trazo
        invalidate(); // Redibuja la vista con el nuevo tamaño
    }

    public void borrarFirma() {
        path.reset(); // Borra los trazos almacenados
        invalidate();
    }

    public Bitmap getFirmaBitmap() {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawPath(path, paint);
        return bitmap;
    }
}