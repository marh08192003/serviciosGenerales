package co.edu.uceva.serviciosGenerales.exception;

/**
 * Excepción para recursos ya inactivos.
 */
public class ResourceAlreadyInactiveException extends RuntimeException {

    public ResourceAlreadyInactiveException(String message) {
        super(message);
    }
}
