package model;

/**
 * Representa a un integrante registrado en un proyecto educativo.
 * Solo guarda y expone datos; no conoce Swing ni al controlador.
 *
 * @author Alejandro Escobar Barrios (20251020094)
 * @version 1.0
 */
public class Integrante {

    // ---------- Atributos ----------

    /** Nombres de pila del integrante. */
    private String nombres;

    /** Apellidos del integrante. */
    private String apellidos;

    /** URL del repositorio de GitHub con el trabajo del integrante. */
    private String repositorio;

    /** Tipo de estudiante: "Nuevo" o "Antiguo". */
    private String tipoEstudiante;

    /** Proyecto educativo al que pertenece el integrante. */
    private String proyectoEducativo;

    // ---------- Constructor ----------

    /**
     * Crea un integrante con todos sus datos.
     *
     * @param nombres           nombres del integrante
     * @param apellidos         apellidos del integrante
     * @param repositorio       URL del repositorio de GitHub
     * @param tipoEstudiante    "Nuevo" o "Antiguo"
     * @param proyectoEducativo nombre del proyecto educativo
     */
    public Integrante(String nombres, String apellidos, String repositorio,
                      String tipoEstudiante, String proyectoEducativo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.repositorio = repositorio;
        this.tipoEstudiante = tipoEstudiante;
        this.proyectoEducativo = proyectoEducativo;
    }

    // ---------- Métodos públicos: getters y setters ----------

    /**
     * Devuelve los nombres del integrante.
     *
     * @return nombres del integrante
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Cambia los nombres del integrante.
     *
     * @param nombres nuevos nombres
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Devuelve los apellidos del integrante.
     *
     * @return apellidos del integrante
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Cambia los apellidos del integrante.
     *
     * @param apellidos nuevos apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Devuelve la URL del repositorio.
     *
     * @return URL del repositorio de GitHub
     */
    public String getRepositorio() {
        return repositorio;
    }

    /**
     * Cambia la URL del repositorio.
     *
     * @param repositorio nueva URL del repositorio
     */
    public void setRepositorio(String repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Devuelve el tipo de estudiante.
     *
     * @return "Nuevo" o "Antiguo"
     */
    public String getTipoEstudiante() {
        return tipoEstudiante;
    }

    /**
     * Cambia el tipo de estudiante.
     *
     * @param tipoEstudiante "Nuevo" o "Antiguo"
     */
    public void setTipoEstudiante(String tipoEstudiante) {
        this.tipoEstudiante = tipoEstudiante;
    }

    /**
     * Devuelve el proyecto educativo.
     *
     * @return nombre del proyecto educativo
     */
    public String getProyectoEducativo() {
        return proyectoEducativo;
    }

    /**
     * Cambia el proyecto educativo.
     *
     * @param proyectoEducativo nombre del nuevo proyecto
     */
    public void setProyectoEducativo(String proyectoEducativo) {
        this.proyectoEducativo = proyectoEducativo;
    }

    /**
     * Devuelve una representación legible del integrante.
     *
     * @return texto con nombre completo, tipo, proyecto y repositorio
     */
    @Override
    public String toString() {
        return nombres + " " + apellidos + " (" + tipoEstudiante + ") - "
                + proyectoEducativo + " - " + repositorio;
    }
}
