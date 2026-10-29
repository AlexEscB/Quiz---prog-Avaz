package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import model.Integrante;
import view.FormularioIntegrante;

/**
 * Coordina las acciones del usuario entre la vista y el modelo.
 * Es el único que lee la vista, crea los objetos {@link Integrante} y mantiene
 * la lista de registros en memoria.
 *
 * @author Alejandro Escobar Barrios (20251020094)
 * @version 1.0
 */
public class IntegranteController {

    // ---------- Constantes ----------

    /**
     * Formato aceptado para el repositorio: https:// o http://, host github.com,
     * un usuario obligatorio y un repositorio opcional, con barra final opcional.
     * Se usa una expresión regular porque basta para validar el formato sin
     * librerías externas; no comprueba que la cuenta o el repositorio existan.
     */
    private static final Pattern PATRON_REPOSITORIO_GITHUB =
            Pattern.compile("^https?://github\\.com/[A-Za-z0-9._-]+(/[A-Za-z0-9._-]+)?/?$");

    // ---------- Atributos ----------

    /** Ventana con la que el controlador interactúa. */
    private final FormularioIntegrante vista;

    /** Registros almacenados en memoria durante la ejecución. */
    private final List<Integrante> registros = new ArrayList<>();

    // ---------- Constructor ----------

    /**
     * Conecta el controlador con la vista y registra los listeners de sus botones.
     *
     * @param vista formulario que el controlador va a manejar
     */
    public IntegranteController(FormularioIntegrante vista) {
        this.vista = vista;
        vista.agregarListenerRegistrar(e -> registrarIntegrante());
        vista.agregarListenerLimpiar(e -> vista.limpiarFormulario());
        vista.agregarListenerConsultar(e -> consultarRegistros());
    }

    // ---------- Métodos privados: acciones ----------

    /**
     * Ejecuta el flujo completo del botón Registrar: leer, crear, almacenar,
     * confirmar y limpiar el formulario.
     */
    private void registrarIntegrante() {
        // 1. Leer datos de la vista; se recortan para que los espacios sobrantes no cuenten como texto.
        String nombres = vista.getNombres().trim();
        String apellidos = vista.getApellidos().trim();
        String repositorio = vista.getRepositorio().trim();
        String tipo = vista.getTipoEstudiante();
        String proyecto = vista.getProyectoEducativo();

        // 2. Validar; si algo falla no se crea ni se guarda nada.
        String error = validarFormulario(nombres, apellidos, repositorio, tipo);
        if (error != null) {
            vista.mostrarError(error);
            return;
        }

        // 3. Crear el objeto y guardarlo en la lista.
        Integrante nuevo = new Integrante(nombres, apellidos, repositorio, tipo, proyecto);
        registros.add(nuevo);

        // 4. Confirmar al usuario y dejar el formulario listo para otro registro.
        vista.mostrarMensaje(construirMensajeConfirmacion(nuevo));
        vista.limpiarFormulario();
    }

    /**
     * Envía una copia de los registros a la vista. La copia evita que la tabla
     * modifique la lista interna del controlador.
     */
    private void consultarRegistros() {
        vista.cargarTabla(List.copyOf(registros));
        if (registros.isEmpty()) {
            vista.mostrarMensaje("Todavía no hay integrantes registrados.");
        }
    }

    /**
     * Revisa las reglas de negocio del formulario, en el orden en que aparecen los campos.
     *
     * @param nombres      nombres ya recortados
     * @param apellidos    apellidos ya recortados
     * @param repositorio  URL ya recortada
     * @param tipo         "Nuevo", "Antiguo" o {@code null} si no hay selección
     * @return mensaje con el primer error encontrado, o {@code null} si todo es válido
     */
    private String validarFormulario(String nombres, String apellidos,
                                     String repositorio, String tipo) {
        if (nombres.isEmpty()) {
            return "El campo Nombres es obligatorio.";
        }
        if (apellidos.isEmpty()) {
            return "El campo Apellidos es obligatorio.";
        }
        if (repositorio.isEmpty()) {
            return "El campo Repositorio es obligatorio.";
        }
        if (!PATRON_REPOSITORIO_GITHUB.matcher(repositorio).matches()) {
            return "El repositorio debe ser una URL de GitHub válida, "
                    + "por ejemplo: https://github.com/usuario/proyecto";
        }
        if (tipo == null) {
            return "Seleccione el tipo de estudiante (Nuevo o Antiguo).";
        }
        return null;
    }

    /**
     * Arma el texto que se muestra tras un registro exitoso.
     *
     * @param integrante registro recién guardado
     * @return mensaje con los datos esenciales del integrante
     */
    private String construirMensajeConfirmacion(Integrante integrante) {
        return "Integrante registrado correctamente:\n"
                + integrante.getNombres() + " " + integrante.getApellidos() + "\n"
                + "Tipo: " + integrante.getTipoEstudiante() + "\n"
                + "Proyecto: " + integrante.getProyectoEducativo();
    }
}
