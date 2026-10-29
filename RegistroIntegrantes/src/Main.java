import javax.swing.SwingUtilities;

import controller.IntegranteController;
import view.FormularioIntegrante;

/**
 * Punto de entrada de la aplicación. Crea la vista, la conecta con el
 * controlador y muestra la ventana en el hilo de eventos de Swing.
 *
 * @author Alejandro Escobar Barrios (20251020094)
 * @version 1.0
 */
public class Main {

    /**
     * Inicia la aplicación. Swing exige crear y mostrar la interfaz en el
     * Event Dispatch Thread, por eso se usa {@link SwingUtilities#invokeLater}.
     *
     * @param args argumentos de línea de comandos; no se usan
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormularioIntegrante vista = new FormularioIntegrante();
            new IntegranteController(vista);
            vista.setVisible(true);
        });
    }
}
