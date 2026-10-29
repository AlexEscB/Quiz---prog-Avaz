package controller;

import java.util.ArrayList;
import java.util.List;

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

        // 2. Crear el objeto y guardarlo en la lista.
        Integrante nuevo = new Integrante(nombres, apellidos, repositorio, tipo, proyecto);
        registros.add(nuevo);

        // 3. Confirmar al usuario y dejar el formulario listo para otro registro.
        vista.mostrarMensaje(construirMensajeConfirmacion(nuevo));
        vista.limpiarFormulario();
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
