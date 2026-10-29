package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import model.Integrante;

/**
 * Ventana principal con el formulario de registro de integrantes.
 * Solo presenta datos y reenvía las acciones del usuario; no valida ni guarda nada.
 *
 * @author Alejandro Escobar Barrios (20251020094)
 * @version 1.0
 */
public class FormularioIntegrante extends JFrame {

    /** Versión de serialización de la ventana (JFrame es serializable). */
    private static final long serialVersionUID = 1L;

    // ---------- Constantes ----------

    /** Título de la ventana. */
    private static final String TITULO = "Registro de Integrantes";

    /** Ancho inicial de la ventana en píxeles. */
    private static final int ANCHO_VENTANA = 640;

    /** Alto inicial de la ventana en píxeles. */
    private static final int ALTO_VENTANA = 560;

    /** Encabezados de las columnas de la tabla de registros, en orden. */
    private static final String[] COLUMNAS_TABLA = {
        "Nombres", "Apellidos", "Repositorio", "Tipo", "Proyecto"
    };

    /** Alto preferido de la tabla de registros, en píxeles. */
    private static final int ALTO_TABLA = 220;

    /** Columnas de texto de los campos de entrada. */
    private static final int COLUMNAS_CAMPO = 28;

    /** Separación interna entre celdas del formulario, en píxeles. */
    private static final int MARGEN = 6;

    /** Opciones del combo de proyectos educativos. */
    private static final String[] PROYECTOS = {
        "Ingeniería de Sistemas",
        "Ingeniería Electrónica",
        "Ingeniería Industrial",
        "Ingeniería Civil"
    };

    // ---------- Atributos ----------

    /** Campo de texto para los nombres. */
    private final JTextField txtNombres = new JTextField(COLUMNAS_CAMPO);

    /** Campo de texto para los apellidos. */
    private final JTextField txtApellidos = new JTextField(COLUMNAS_CAMPO);

    /** Campo de texto para la URL del repositorio de GitHub. */
    private final JTextField txtRepositorio = new JTextField(COLUMNAS_CAMPO);

    /** Opción "Nuevo" del tipo de estudiante. */
    private final JRadioButton rbNuevo = new JRadioButton("Nuevo");

    /** Opción "Antiguo" del tipo de estudiante. */
    private final JRadioButton rbAntiguo = new JRadioButton("Antiguo");

    /** Agrupa los radio buttons para permitir una sola selección. */
    private final ButtonGroup grupoTipo = new ButtonGroup();

    /** Lista desplegable con los proyectos educativos. */
    private final JComboBox<String> cmbProyecto = new JComboBox<>(PROYECTOS);

    /** Botón que dispara el registro del integrante. */
    private final JButton btnRegistrar = new JButton("Registrar");

    /** Botón que muestra los registros almacenados. */
    private final JButton btnConsultar = new JButton("Consultar registros");

    /** Botón que vacía el formulario. */
    private final JButton btnLimpiar = new JButton("Limpiar");

    /** Modelo de la tabla; sus celdas no se pueden editar desde la vista. */
    private final DefaultTableModel modeloTabla =
            new DefaultTableModel(COLUMNAS_TABLA, 0) {
                private static final long serialVersionUID = 1L;

                /**
                 * Impide que el usuario edite las celdas de la tabla.
                 *
                 * @param fila    índice de la fila
                 * @param columna índice de la columna
                 * @return siempre {@code false}
                 */
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

    /** Tabla que muestra los integrantes registrados. */
    private final JTable tablaRegistros = new JTable(modeloTabla);

    // ---------- Constructor ----------

    /**
     * Crea la ventana y organiza sus componentes. No la muestra; eso lo
     * decide {@code Main}.
     */
    public FormularioIntegrante() {
        super(TITULO);
        grupoTipo.add(rbNuevo);
        grupoTipo.add(rbAntiguo);
        construirInterfaz();
    }

    // ---------- Métodos públicos: lectura de datos ----------

    /**
     * Devuelve el texto tal como lo escribió el usuario, sin recortar.
     *
     * @return contenido del campo nombres
     */
    public String getNombres() {
        return txtNombres.getText();
    }

    /**
     * Devuelve el texto tal como lo escribió el usuario, sin recortar.
     *
     * @return contenido del campo apellidos
     */
    public String getApellidos() {
        return txtApellidos.getText();
    }

    /**
     * Devuelve el texto tal como lo escribió el usuario, sin recortar.
     *
     * @return contenido del campo repositorio
     */
    public String getRepositorio() {
        return txtRepositorio.getText();
    }

    /**
     * Devuelve el tipo de estudiante seleccionado.
     *
     * @return "Nuevo", "Antiguo" o {@code null} si no hay selección
     */
    public String getTipoEstudiante() {
        if (rbNuevo.isSelected()) {
            return rbNuevo.getText();
        }
        if (rbAntiguo.isSelected()) {
            return rbAntiguo.getText();
        }
        return null;
    }

    /**
     * Devuelve el proyecto educativo elegido en el combo.
     *
     * @return nombre del proyecto seleccionado
     */
    public String getProyectoEducativo() {
        return (String) cmbProyecto.getSelectedItem();
    }

    // ---------- Métodos públicos: acciones sobre la ventana ----------

    /**
     * Restablece el formulario a su estado inicial: campos vacíos, sin tipo
     * seleccionado y primer proyecto del combo.
     */
    public void limpiarFormulario() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtRepositorio.setText("");
        grupoTipo.clearSelection();
        cmbProyecto.setSelectedIndex(0);
        txtNombres.requestFocusInWindow();
    }

    /**
     * Muestra un mensaje informativo al usuario.
     *
     * @param mensaje texto a mostrar
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, TITULO, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param mensaje texto del error a mostrar
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, TITULO, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Reemplaza el contenido de la tabla con los integrantes recibidos.
     * La vista solo convierte cada objeto en una fila; no filtra ni ordena.
     *
     * @param integrantes registros a mostrar, en el orden de la lista
     */
    public void cargarTabla(List<Integrante> integrantes) {
        modeloTabla.setRowCount(0);
        for (Integrante integrante : integrantes) {
            modeloTabla.addRow(new Object[] {
                integrante.getNombres(),
                integrante.getApellidos(),
                integrante.getRepositorio(),
                integrante.getTipoEstudiante(),
                integrante.getProyectoEducativo()
            });
        }
    }

    // ---------- Métodos públicos: conexión con el controlador ----------

    /**
     * Asigna la acción del botón Registrar.
     *
     * @param listener oyente que se ejecuta al pulsar el botón
     */
    public void agregarListenerRegistrar(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }

    /**
     * Asigna la acción del botón Consultar registros.
     *
     * @param listener oyente que se ejecuta al pulsar el botón
     */
    public void agregarListenerConsultar(ActionListener listener) {
        btnConsultar.addActionListener(listener);
    }

    /**
     * Asigna la acción del botón Limpiar.
     *
     * @param listener oyente que se ejecuta al pulsar el botón
     */
    public void agregarListenerLimpiar(ActionListener listener) {
        btnLimpiar.addActionListener(listener);
    }

    // ---------- Métodos privados: construcción de la interfaz ----------

    /**
     * Arma el panel del formulario y la barra de botones, y configura la ventana.
     */
    private void construirInterfaz() {
        JPanel raiz = new JPanel(new BorderLayout(MARGEN, MARGEN));
        raiz.setBorder(BorderFactory.createEmptyBorder(MARGEN * 2, MARGEN * 2, MARGEN * 2, MARGEN * 2));
        raiz.add(crearPanelFormulario(), BorderLayout.NORTH);
        raiz.add(crearPanelTabla(), BorderLayout.CENTER);
        raiz.add(crearPanelBotones(), BorderLayout.SOUTH);

        setContentPane(raiz);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(ANCHO_VENTANA, ALTO_VENTANA));
        setLocationRelativeTo(null);
    }

    /**
     * Crea el panel con etiquetas y campos, alineados en dos columnas.
     *
     * @return panel con el formulario
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(MARGEN, MARGEN, MARGEN, MARGEN);
        gbc.anchor = GridBagConstraints.WEST;

        agregarFila(panel, gbc, 0, "Nombres:", txtNombres);
        agregarFila(panel, gbc, 1, "Apellidos:", txtApellidos);
        agregarFila(panel, gbc, 2, "Repositorio (GitHub):", txtRepositorio);
        agregarFila(panel, gbc, 3, "Tipo de estudiante:", crearPanelTipo());
        agregarFila(panel, gbc, 4, "Proyecto educativo:", cmbProyecto);
        return panel;
    }

    /**
     * Envuelve la tabla en un scroll para que los registros largos sean navegables.
     *
     * @return panel con la tabla de registros
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Integrantes registrados"));
        tablaRegistros.setFillsViewportHeight(true);
        tablaRegistros.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll = new JScrollPane(tablaRegistros);
        scroll.setPreferredSize(new Dimension(ANCHO_VENTANA, ALTO_TABLA));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Agrupa los radio buttons de tipo de estudiante en una fila horizontal.
     *
     * @return panel con los radio buttons Nuevo y Antiguo
     */
    private JPanel crearPanelTipo() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.add(rbNuevo);
        panel.add(rbAntiguo);
        return panel;
    }

    /**
     * Crea la barra inferior con los botones de acción.
     *
     * @return panel con Registrar, Consultar y Limpiar
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, MARGEN * 2, 0));
        panel.add(btnRegistrar);
        panel.add(btnConsultar);
        panel.add(btnLimpiar);
        return panel;
    }

    /**
     * Agrega una fila de dos columnas: etiqueta a la izquierda y componente a la derecha.
     *
     * @param panel       panel contenedor con GridBagLayout
     * @param gbc         restricciones reutilizadas entre filas
     * @param fila        índice de la fila (desde 0)
     * @param textoEtiqueta texto de la etiqueta
     * @param componente  componente de entrada de la fila
     */
    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila,
                             String textoEtiqueta, Component componente) {
        JLabel etiqueta = new JLabel(textoEtiqueta, SwingConstants.RIGHT);

        gbc.gridy = fila;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(etiqueta, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(componente, gbc);
    }
}
