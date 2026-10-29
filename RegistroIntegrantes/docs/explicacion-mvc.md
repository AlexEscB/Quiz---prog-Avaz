# Explicación MVC – Registro de Integrantes

**Autor:** Alejandro Escobar Barrios – código 20251020094
**Asignatura:** Programación Avanzada

## 1. Cómo se relacionan las capas en este proyecto

El proyecto separa tres responsabilidades en paquetes distintos:

| Capa | Clase | Responsabilidad | Qué NO hace |
|---|---|---|---|
| **Modelo** | `model.Integrante` | Guarda los datos de un integrante (atributos privados, getters, setters, `toString`). | No conoce Swing ni al controlador. |
| **Vista** | `view.FormularioIntegrante` | Construye la ventana, lee lo que escribe el usuario y muestra mensajes y la tabla. | No valida, no crea `Integrante` y no guarda nada. |
| **Controlador** | `controller.IntegranteController` | Escucha los botones, valida, crea los objetos, mantiene el `ArrayList<Integrante>` y decide qué mostrar. | No dibuja componentes. |

`Main` solo crea la vista, crea el controlador pasándole la vista y muestra la ventana dentro de `SwingUtilities.invokeLater`.

La comunicación es en las dos direcciones, pero siempre por medio del controlador:

- **Vista → Controlador:** la vista expone `agregarListenerRegistrar(ActionListener)`, `agregarListenerConsultar(...)` y `agregarListenerLimpiar(...)`. El controlador se registra en ellos al construirse.
- **Controlador → Vista:** el controlador llama a métodos de la vista (`getNombres()`, `mostrarError(...)`, `cargarTabla(...)`, etc.).
- **Controlador → Modelo:** crea objetos `Integrante` y los guarda en la lista.

## 2. Flujo del botón "Registrar", paso a paso

1. El usuario pulsa **Registrar** en `FormularioIntegrante`.
2. El `ActionListener` que el controlador registró llama a `registrarIntegrante()`.
3. El controlador **lee** la vista: `getNombres()`, `getApellidos()`, `getRepositorio()`, `getTipoEstudiante()` y `getProyectoEducativo()`, y recorta con `trim()` los textos.
4. El controlador **valida** con `validarFormulario(...)`: campos obligatorios, formato de la URL de GitHub (expresión regular) y selección de tipo de estudiante.
5. Si hay un error, el controlador llama a `vista.mostrarError(mensaje)` y termina. No se crea ni se guarda nada.
6. Si todo es válido, el controlador crea `new Integrante(...)` y lo agrega a `registros` (un `ArrayList<Integrante>`).
7. El controlador llama a `vista.mostrarMensaje(...)` con los datos del nuevo integrante.
8. El controlador llama a `vista.limpiarFormulario()` para dejar la ventana lista para otro registro.

La vista nunca decide si un dato es válido: solo entrega texto y muestra el resultado que el controlador le indica.

## 3. Respuestas a las preguntas de reflexión

**1. ¿Cuál es la responsabilidad de la clase `Integrante` dentro del modelo?**
Representa un integrante del proyecto: almacena sus cinco datos (nombres, apellidos, repositorio, tipo de estudiante y proyecto educativo) en atributos privados, y permite leerlos y cambiarlos mediante getters y setters. Es el "sustantivo" del sistema. No sabe cómo se dibuja ni cómo se valida; por eso puede reutilizarse con otra interfaz (consola, web) sin cambios.

**2. ¿Por qué los eventos del botón Registrar deben coordinarse desde el controlador?**
Porque el registro involucra varias decisiones que no pertenecen a la vista: leer datos, validarlos, crear el objeto, guardarlo, confirmar y limpiar. Si esa secuencia estuviera dentro de la vista, la interfaz quedaría atada a las reglas de negocio y cualquier cambio en una validación obligaría a tocar Swing. Centralizarla en el controlador deja un único lugar donde se define el flujo, y como el controlador solo usa los métodos públicos de la vista, podría probarse con una vista simulada sin abrir ventanas.

**3. ¿Qué ventaja ofrece `ArrayList` frente a declarar una cantidad fija de variables para guardar integrantes?**
Un `ArrayList<Integrante>` crece automáticamente al agregar elementos, no obliga a saber de antemano cuántos integrantes habrá y permite recorrerlos con un solo bucle (la tabla de consulta lo hace con `for`). Declarar variables sueltas (`integrante1`, `integrante2`, ...) limitaría el sistema a un número fijo, duplicaría código para cada registro y haría imposible consultarlos de forma uniforme.

**4. ¿Cómo evita MVC que la lógica del sistema quede mezclada con el diseño de la interfaz?**
Al dividir responsabilidades: la vista solo sabe *mostrar* y *capturar* eventos; el modelo solo sabe *representar* datos; y el controlador es el único que une ambos. Así, las reglas (por ejemplo, que la URL debe ser de GitHub) viven en `IntegranteController` y no en los `JTextField` o `JButton`. Si se cambiara el diseño del formulario, las validaciones no se tocan, y si cambiaran las reglas, el diseño visual no se ve afectado.

## 4. Evidencia de ejecución

![Ejecución de la aplicación](captura-ejecucion.png)
