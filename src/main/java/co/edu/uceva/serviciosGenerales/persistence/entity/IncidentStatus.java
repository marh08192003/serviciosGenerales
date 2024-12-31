package co.edu.uceva.serviciosGenerales.persistence.entity;

/**
 * Enum representing the status of an incident.
 * Possible values are:
 * - PENDING: The incident is reported but not yet addressed.
 * - IN_PROCESS: The incident is currently being addressed.
 * - RESOLVED: The incident has been resolved.
 */
public enum IncidentStatus {
    pendiente,
    en_proceso,
    resuelta
}
