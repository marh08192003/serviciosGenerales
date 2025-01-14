package co.edu.uceva.serviciosGenerales.exception;

/**
 * Excepción para asignaciones duplicadas.
 */
public class DuplicateAssignmentException extends RuntimeException {
    public DuplicateAssignmentException(String message) {
        super(message);
    }
}
