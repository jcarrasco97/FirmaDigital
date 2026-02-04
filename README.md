# FirmaDigital

Aplicación Android que permite al usuario **dibujar y guardar una firma digital** en su dispositivo móvil como imagen JPG. Incluye una interfaz sencilla para firmar, borrar, ajustar el grosor del trazo y guardar la firma en almacenamiento externo.

---

## Funcionalidades

- Dibujar una firma con el dedo.
- Cambiar el tamaño del trazo desde una pantalla de configuración.
- Borrar la firma actual.
- Guardar la firma como imagen `.jpg` en el almacenamiento del dispositivo.
- Persistencia de configuración usando `SharedPreferences`.

---

## Tecnologías utilizadas

- Java
- Android SDK
- Custom Views (`FirmaView`)
- `SharedPreferences` para ajustes de usuario
- Permisos dinámicos (`WRITE_EXTERNAL_STORAGE`)

---

## Estructura del proyecto

| Clase                     | Descripción |
|---------------------------|-------------|
| `MainActivity`            | Actividad principal. Muestra la vista de firma y botones para borrar, guardar o ir a configuración. |
| `FirmaView`               | Vista personalizada que permite al usuario dibujar con el dedo. Convierte la firma en un `Bitmap`. |
| `ConfiguracionActivity`   | Pantalla donde el usuario puede elegir el grosor del trazo mediante un `SeekBar`. |

---
